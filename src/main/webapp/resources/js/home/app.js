(function(angular) {
    angular.module("HomeApp.controllers", []);
    angular.module("HomeApp.services", []);
    angular.module("HomeApp", ["ngResource", "HomeApp.controllers", "HomeApp.services", "ngRoute"]).config(function($routeProvider, $locationProvider) {
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