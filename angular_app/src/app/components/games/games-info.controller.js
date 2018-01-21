(function () {
  angular
    .module('app')
    .component('gamesInfo', {
      templateUrl: 'app/components/games/games-info.html',
      controller: GamesInfoController,
      controllerAs: 'info'
    });

  function GamesInfoController(GamesService, $http, $state, $stateParams, $rootScope) {
    var vm = this;

    vm.game = {};
    vm.editGame = editGame;
    vm.removeGame = removeGame;
    
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
        $rootScope.$emit("RefreshGames", {});
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