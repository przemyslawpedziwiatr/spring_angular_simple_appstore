(function () {
  angular
    .module('app')
    .service('UserService', UserService);

  function UserService($http, $cookies) {
    this.getUser = getUser;
    this.isLoggedIn = isLoggedIn;
    let loggedIn = false;

    function getUser(name, password, onLogin, onFail) {
      if ($cookies.get('login') !== 'true') {
        requestUser(name, password, onLogin, onFail);
      } else {
        loggedIn = true;
      }
    }

    function requestUser(name, password, onLogin, onFail) {
      $http({
        method: 'GET',
        url: 'http://localhost:8080/users/' + name + '/' + password
      }).then(function success(response) {
        loggedIn = response.data;
        if (loggedIn === true) {
			addLoginCookie();

          if (onLogin) onLogin();
        } else {
          if (onFail) onFail();
        }
      }, function fail() {
        loggedIn = false;
        if (onFail) onFail();
      });
    }

    function isLoggedIn() {
      return loggedIn;
	}
	
	function addLoginCookie() {
		let expiryTime = new Date();
		expiryTime.setDate(expiryTime.getDate() + 0.02);

		$cookies.put('login', 'true', {
		  expires: expiryTime
		});
	}

  }
})();
