(function () {
  angular
    .module('app')
    .component('gamesAdd', {
      templateUrl: 'app/components/games/games-add.html',
      controller: GamesAddController,
      controllerAs: 'gamesAddCtrl'
    });

  function GamesAddController(GamesService, $scope, $rootScope, $http, $state) {
    var vm = this;

    vm.addGame = addGame;
    $scope.form = {};

    function addGame() {
      GamesService.addGame(
        $scope.form.title,
        $scope.form.version,
        $scope.form.description,
        ['data:', $scope.form.icon.filetype, ';base64, ', $scope.form.icon.base64].join(''),
        ['data:', $scope.form.screenshot.filetype, ';base64, ', $scope.form.screenshot.base64].join(''),
        ['data:', $scope.form.file.filetype, ';base64, ', $scope.form.file.base64].join('')
      ).then(function () {
        $rootScope.$emit("RefreshGames", {});
      })
    }

    function goBack() {

    }


  }
})();