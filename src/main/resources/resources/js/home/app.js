(function(angular) {
    angular.module("HomeApp.controllers", []);
    angular.module("HomeApp.services", []);
    angular.module("HomeApp.directives", []);
    angular.module("HomeApp", ["ngResource", "HomeApp.controllers", "HomeApp.services", "HomeApp.directives", "ngRoute", 'ui.bootstrap', 'gettext', 'ngTouch']).config(function($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'partials/dashboard.html',
                controller: 'DashboardCtrl'
            })
            .when('/profile', {
                templateUrl: 'partials/profile.html',
                controller: 'ProfileCtrl'
            })
            .when('/notifications', {
                templateUrl: 'partials/notifications.html',
                controller: 'NotificationsCtrl'
            })
            .when('/task', {
                templateUrl: 'partials/create-task.html',
                controller: 'CreateTaskCtrl'
            })
            .otherwise({redirectTo: '/'});
    });
}(angular));

/***
 * Mobile menu hack
 * */
$('.nav a').on('click', function(){
    if ($('.navbar-collapse').width() < 990) {
        $('.navbar-toggle').click();
    }
});