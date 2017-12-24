(function(angular) {
    var HomeCtrl = function($scope) {

    };
    HomeCtrl.$inject = ['$scope'];

    var DashboardCtrl = function($scope) {

    };
    DashboardCtrl.$inject = ['$scope'];

    var ProfileCtrl = function($scope) {

    };
    ProfileCtrl.$inject = ['$scope'];

    var NotificationsCtrl = function($scope) {

    };
    NotificationsCtrl.$inject = ['$scope'];

    var CreateTaskCtrl = function($scope) {

    };
    CreateTaskCtrl.$inject = ['$scope'];

    angular.module("HomeApp.controllers").controller("HomeCtrl", HomeCtrl);
    angular.module("HomeApp.controllers").controller("DashboardCtrl", DashboardCtrl);
    angular.module("HomeApp.controllers").controller("ProfileCtrl", ProfileCtrl);
    angular.module("HomeApp.controllers").controller("NotificationsCtrl", NotificationsCtrl);
    angular.module("HomeApp.controllers").controller("CreateTaskCtrl", CreateTaskCtrl);
}(angular));