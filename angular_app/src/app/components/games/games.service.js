(function(){
    angular
    .module('app')
    .service('GamesService', GamesService);

    function GamesService($http) {
        this.getGames = getGames;
        this.addGame = addGame;
        this.removeGame = removeGame;
        this.getGameById = getGameById;
        this.updateGame = updateGame;
        
        function addGame(title, version, screenshot_url, icon_url, description){
            $http.defaults.headers.post["Content-Type"] = "application/json";

            return $http({
                method: 'POST',
                url: 'http://localhost:8080/games',
                data: JSON.stringify({ 
                    "title" : title,
                    "version" : version,
                    "screenshot_url" : screenshot_url,
                    "icon_url" : icon_url,
                    "description" : description
                })
            });
        }

        function updateGame(id, title, version, screenshot_url, icon_url, description){
            $http.defaults.headers.post["Content-Type"] = "application/json";

            return $http({
                method: 'PUT',
                url: 'http://localhost:8080/games/'+id,
                data: JSON.stringify({ 
                    "title" : title,
                    "version" : version,
                    "screenshot_url" : screenshot_url,
                    "icon_url" : icon_url,
                    "description" : description
                })
            });
        }

        function getGames(){
            return $http({
                method: 'GET',
                url: 'http://localhost:8080/games'
            });
        }

        function getGameById(id) {
            return $http({
                method: 'GET',
                url: 'http://localhost:8080/games/'+id,
            });
        }

        function removeGame(id){
            return $http({
                method: 'DELETE',
                url: 'http://localhost:8080/games',
                params: { id : id }
            });
        }

    }
})();