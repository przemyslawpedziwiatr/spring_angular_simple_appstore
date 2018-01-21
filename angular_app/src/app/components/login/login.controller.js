(function () {
  angular
    .module('app')
    .component('login', {
      templateUrl: 'app/components/login/login.html',
      controller: LoginController,
      controllerAs: 'loginCtrl'
    });

  function LoginController(UserService, $cookies) {
    var vm = this;

    vm.name = '';
    vm.pass = '';
    vm.help = '';

    vm.buttonLogin = function ($event) {
      if ($event.key == 'Enter') {
        vm.login();
      }
    }
    
    vm.login = function () {
      if(vm.name.length > 0 || vm.pass.length > 0) {
        UserService.getUser(vm.name, vm.pass, function () {

        }, function () {
          vm.help = 'Wrong username or password!';
        });  
      }
    }

    vm.isLoggedIn = function () {
      return UserService.isLoggedIn();
    }

    vm.login();
 
  }
})();
