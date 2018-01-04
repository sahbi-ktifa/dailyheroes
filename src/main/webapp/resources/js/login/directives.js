(function(angular) {
    var userCheck = function (Registration) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                user: '='
            },
            template: '<span class="user-check">' +
            '   <span ng-if="user && !isUnique"><i class="fa fa-times"></i> <span translate>Username already exists.</span></span>' +
            '   <input type="text" required="required" ng-if="user && !isUnique" ng-model="value" style="display: none"/>' +
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