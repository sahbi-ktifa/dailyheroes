(function(angular) {
    var RegistrationFactory = function($http) {
        var registrationService = {};

        registrationService.doRegister = function(data) {
            return $http.post(contextPath + '/register', {password: data.password, playerNames:[data.player1, data.player2]});
        };

        return registrationService;
    };
    RegistrationFactory.$inject = ['$http'];

    angular.module("LoginApp.services").factory("Registration", RegistrationFactory);
}(angular));