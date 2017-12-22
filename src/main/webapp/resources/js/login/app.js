(function(angular) {
    angular.module("LoginApp.controllers", []);
    angular.module("LoginApp.services", []);
    angular.module("LoginApp", ["ngResource", "LoginApp.controllers", "LoginApp.services"]);
}(angular));