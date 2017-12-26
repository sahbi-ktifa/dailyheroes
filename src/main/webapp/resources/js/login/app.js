(function(angular) {
    angular.module("LoginApp.controllers", []);
    angular.module("LoginApp.services", []);
    angular.module("LoginApp.directives", []);
    angular.module("LoginApp", ["ngResource", "LoginApp.controllers", "LoginApp.services", "LoginApp.directives"]);
}(angular));