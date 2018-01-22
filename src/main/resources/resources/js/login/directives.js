(function(angular) {
    var userCheck = function (Registration) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                user: '='
            },
            template: '<span class="user-check">' +
            '   <div ng-if="user && user.length > 3 && !isUnique" class="check-warning"><i class="fa fa-exclamation"></i> <span translate>WARNING, user already exists and will be added to your game.</span></div>' +
            '   <div ng-if="user && user.length < 4" class="check-info"><i class="fa fa-info"></i> <span translate>Username should be at least 4 characters long.</span></div>' +
            '</span>',
            link: function (scope) {
                scope.isUnique = false;

                scope.$watch('user', function(username){
                    if (username && username.length > 3) {
                        Registration.checkUser(username).then(function () {
                            scope.isUnique = true;
                        }).catch(function () {
                            scope.isUnique = false;
                        });
                    }
                });
            }
        };
    };
    userCheck.$inject = ['Registration'];

    angular.module("LoginApp.directives").directive("userCheck", userCheck);
}(angular));