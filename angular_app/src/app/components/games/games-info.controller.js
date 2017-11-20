(function () {
  angular
    .module('app')
    .component('gamesInfo', {
      templateUrl: 'app/components/games/games-info.html',
      controller: GamesInfoController,
      controllerAs: 'info'
    });

  function GamesInfoController(GamesService, $http, $state, $stateParams) {
    var vm = this;

    vm.game = {};

    (function init() {
      getGame($stateParams.gameId);
    })();

    function getGame(id) {
      GamesService.getGameById(id).then(function (response) {
        vm.game = response.data;
      });
    }

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