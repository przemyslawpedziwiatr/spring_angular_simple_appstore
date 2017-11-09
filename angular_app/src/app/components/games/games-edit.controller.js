(function(){
    angular
    .module('app')
    .component('gamesEdit', {
      templateUrl: 'app/components/games/games-detail.html',
      controller: GamesEditController,
      controllerAs: 'gamesEditCtrl'
    });
  
    function GamesEditController(GamesService, $scope, $http, $state, $stateParams) {
        var vm = this;

        vm.getGame = getGame;
        vm.updateGame = updateGame;
        vm.game = {};

        $scope.form;

        (function init() {
            vm.getGame();
            $scope.form = getForm();
        })();

        function getGame() {
            GamesService.getGameById($stateParams.gameId).then(
                function success(response){
                    vm.game = response.data;
                    $scope.form = getForm();
                }
            );
        }

        $scope.cancel = function() {
            $state.go('games');
        }

        function updateGame() {
            GamesService.updateGame(
                $stateParams.gameId,
                $scope.form.form_fields[0].field_value,
                $scope.form.form_fields[1].field_value,
                $scope.form.form_fields[2].field_value,
                $scope.form.form_fields[3].field_value,
                $scope.form.form_fields[4].field_value
            )

            // 
            $state.go('games');
        }
   
        $scope.updateFields = function(){
            $scope.form = getForm();
        }

        function getForm() {
            return {
            "form_id": 1,
            "form_name": "Games added",
            "form_fields": [
              {
                "field_id": 1,
                "field_title": "Title",
                "field_type": "textfield",
                "field_value": vm.game.title,
                "field_required": true,
                "field_disabled": false
              },
              {
                "field_id": 2,
                "field_title": "Version",
                "field_type": "textfield",
                "field_value": vm.game.version,
                "field_required": true,
                "field_disabled": false
              },
              {
                "field_id": 3,
                "field_title": "Screenshot_Url",
                "field_type": "textfield",
                "field_value": vm.game.screenshot,
                "field_required": false,
                "field_disabled": false
              },
              {
                "field_id": 4,
                "field_title": "Icon_url",
                "field_type": "textfield",
                "field_value": vm.game.icon_url,
                "field_required": false,
                "field_disabled": false
              },
              {
                "field_id": 5,
                "field_title": "Description",
                "field_type": "textfield",
                "field_value": vm.game.description,
                "field_required": true,
                "field_disabled": false
              }
            ],
            "submitted": false
          };
        }
    }
})();