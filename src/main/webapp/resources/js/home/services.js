(function(angular) {
    var RegistrationFactory = function($http) {
        var registrationService = {};


        return registrationService;
    };
    RegistrationFactory.$inject = ['$http'];

    angular.module("HomeApp.services").factory("Registration", RegistrationFactory);
}(angular));