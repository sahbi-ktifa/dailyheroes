(function(angular) {
    var RegistrationFactory = function($http) {
        var registrationService = {};

        registrationService.doRegister = function(data) {
            var players = [data.player1, data.player2];
            if (data.extraPlayers.length > 0) {
                for (var idx in data.extraPlayers) {
                    if (data.extraPlayers[idx].value.length > 0) {
                        players.push(data.extraPlayers[idx].value);
                    }
                }
            }
            return $http.post(contextPath + '/register', {password: data.password, playerNames: players, dashboardName: data.dashboard});
        };

        registrationService.checkUser = function(username) {
            return $http.get(contextPath + '/register/' + username + '/exist');
        };

        return registrationService;
    };
    RegistrationFactory.$inject = ['$http'];

    angular.module("LoginApp.services").factory("Registration", RegistrationFactory);
}(angular));