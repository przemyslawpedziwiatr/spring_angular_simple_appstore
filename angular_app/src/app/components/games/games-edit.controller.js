(function () {
    angular
        .module('app')
        .component('gamesEdit', {
            templateUrl: 'app/components/games/games-detail.html',
            controller: GamesEditController,
            controllerAs: 'gamesEditCtrl'
        });

    function GamesEditController(GamesService, $rootScope, $scope, 
        $http, $state, $stateParams) {
        var vm = this;

        vm.getGame = getGame;
        vm.updateGame = updateGame;
        $scope.form = {};

        (function init() {
            vm.getGame();
        })();

        function getGame() {
            GamesService.getGameById($stateParams.gameId).then(
                function success(response) {
                    $scope.form = {
                        "title": response.data.title,
                        "version": response.data.version,
                        "description": response.data.description,
                        "screenshot": response.data.screenshot,
                        "icon": response.data.icon
                    };
                }
            );
        }

        $scope.cancel = function () {
            $state.go('games');
        }

        function updateGame() {
            var icon_new = "";
            var screenshot_new = "";
            var file_new = "";

            if (!$scope.form.icon_new) {
                icon_new = $scope.form.icon;
            } else {
                icon_new = ['data:',
                    $scope.form.icon_new.filetype,
                    ';base64, ',
                    $scope.form.icon_new.base64].join('');
            }

            if (!$scope.form.screenshot_new) {
                screenshot_new = $scope.form.screenshot;
            } else {
                screenshot_new = ['data:',
                    $scope.form.screenshot_new.filetype,
                    ';base64, ',
                    $scope.form.screenshot_new.base64].join('');
            }

            if (!$scope.form.file_new) {
                file_new = $scope.form.file;
            } else {
                file_new = ['data:',
                    $scope.form.file_new.filetype,
                    ';base64, ',
                    $scope.form.file_new.base64].join('');
            }

            GamesService.updateGame(
                $stateParams.gameId,
                $scope.form.title,
                $scope.form.version,
                $scope.form.description,
                icon_new,
                screenshot_new,
                file_new
            ).then(function () {
                $rootScope.$emit("RefreshGames", {});
            });

            $state.go('games');
        }


    }
})();