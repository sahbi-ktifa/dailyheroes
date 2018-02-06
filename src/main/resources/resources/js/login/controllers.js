(function(angular) {
    var LoginController = function($scope) {
        $scope.error = undefined;
        $scope.success = undefined;
        if (localStorage.getItem('first-visit')) {
            $scope.selectedTab = 'login';
        } else {
            $scope.selectedTab = 'infos';
            localStorage.setItem('first-visit', String(new Date()))
        }

        $scope.clearSuccess = function () {
            $scope.success = undefined;
        };

        $scope.clearError = function () {
            $scope.error = undefined;
        };

        $scope.$on('successOccurred', function (event, arg) {
            $scope.success = arg;
            $scope.selectedTab = 'login';
        });

        $scope.$on('errorOccurred', function (event, arg) {
            $scope.error = arg;
        });
    };

    var RegistrationController = function($scope, Registration) {
        $scope.form = {
            player1: undefined,
            player2: undefined,
            extraPlayers: [],
            password: undefined,
            confirmPassword: undefined
        };

        $scope.addPlayer = function () {
            if ($scope.form.extraPlayers.length < 6) {
                $scope.form.extraPlayers.push({value:''});
            }
        };

        $scope.register = function() {
            Registration.doRegister($scope.form).then(function() {
                for (var prop in $scope.form) {
                    if($scope.form.hasOwnProperty(prop)) {
                        $scope.form[prop] = undefined;
                    }
                }
                $scope.form.extraPlayers = [];
                $scope.$emit('successOccurred', 'Registration complete, you can now log in.');
            }, function (reason) {
                console.log(reason);
                $scope.$emit('errorOccurred', 'An error occured, please try again later.');
            });
        };
    };
    RegistrationController.$inject = ['$scope', 'Registration'];

    angular.module("LoginApp.controllers").controller("LoginCtrl", LoginController);
    angular.module("LoginApp.controllers").controller("RegistrationCtrl", RegistrationController);
}(angular));