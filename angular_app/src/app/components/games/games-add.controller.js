(function(){
    angular
    .module('app')
    .component('gamesAdd', {
      templateUrl: 'app/components/games/games-add.html',
      controller: GamesAddController,
      controllerAs: 'gamesAddCtrl'
    });
  
    function GamesAddController(GamesService, $scope, $http, $state) {
        var vm = this;

        vm.addGame = addGame;

        function addGame() {
            GamesService.addGame(
                $scope.form.form_fields[0].field_value,
                $scope.form.form_fields[1].field_value,
                $scope.form.form_fields[2].field_value,
                $scope.form.form_fields[3].field_value,
                $scope.form.form_fields[4].field_value
            )
        }

        $scope.form = {
            "form_id": 1,
            "form_name": "Games added",
            "form_fields": [
              {
                "field_id": 1,
                "field_title": "Title",
                "field_type": "textfield",
                "field_value": "",
                "field_required": true,
                "field_disabled": false
              },
              {
                "field_id": 2,
                "field_title": "Version",
                "field_type": "textfield",
                "field_value": "",
                "field_required": true,
                "field_disabled": false
              },
              {
                "field_id": 3,
                "field_title": "Screenshot_Url",
                "field_type": "textfield",
                "field_value": "",
                "field_required": false,
                "field_disabled": false
              },
              {
                "field_id": 4,
                "field_title": "Icon_url",
                "field_type": "textfield",
                "field_value": "",
                "field_required": false,
                "field_disabled": false
              },
              {
                "field_id": 5,
                "field_title": "Description",
                "field_type": "textfield",
                "field_value": "",
                "field_required": true,
                "field_disabled": false
              }
            ],
            "submitted": false
          };
        
    }
})();