angular
  .module('app')
  .config(routesConfig);

/** @ngInject */
function routesConfig($stateProvider, $urlRouterProvider, $locationProvider) {
  $locationProvider.html5Mode(true).hashPrefix('!');
  $urlRouterProvider.otherwise('/');

  $stateProvider
    .state('games', {
      url: '/',
      component: 'games'
    })
    .state('gamesAdd', {
      url: '/games-add',
      component: 'gamesAdd'
    })
    .state('gamesEdit', {
      url: '/games/{gameId}',
      component: 'gamesEdit',
      params: {
        id : null
      }
    })

}
