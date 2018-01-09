(function () {
  angular
    .module('app')
    .component('games', {
      templateUrl: 'app/components/games/games.html',
      controller: GamesController,
      controllerAs: 'gamesCtrl'
    });

  function GamesController(GamesService, $http, $rootScope, $state, $route, $routeParams) {
    var vm = this;

    vm.getGames = getGames;
    vm.removeGame = removeGame;
    vm.addGame = addGame;
    vm.editGame = editGame;
    vm.games;

    (function init() {
      vm.getGames();
    })();

    function getGames() {
      GamesService.getGames().then(function (response) {
        vm.games = response.data;
      });
    }

    $rootScope.$on('RefreshGames', function() {
      vm.getGames();
    });

    function removeGame(id) {
      GamesService.removeGame(id).then(function success() {
        vm.getGames();
      })
    }

    function editGame(id) {
      $state.go('gamesEdit', { gameId: id });
    }

    function addGame() {
      $state.go('gamesAdd');
    }
  }
})();