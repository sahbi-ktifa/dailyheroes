(function(angular) {
    var userPresentation = function (User) {
        return {
            restrict: 'E',
            replace: true,
            template: '<div class="user-container">' +
            '   <div class="user-avatar">' +
            '       <avatar-displayer avatar-config="user.avatar"></avatar-displayer>' +
            '   </div>' +
            '   <div class="user-info">' +
            '       <span>{{user.username}}</span>' +
            '       <span> - </span>' +
            '       <span translate>Level</span><span> {{user.level}}</span>' +
            '   </div>' +
            '</div>',
            link: function (scope, element, attrs) {
                var username = attrs.username;
                User.retrieveUser(username).then(function (res) {
                    scope.user = res.data;
                });
            }
        };
    };
    userPresentation.$inject = ['User'];

    var taskForm = function (Task) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                task: '='
            },
            templateUrl: 'partials/task-form.html',
            link: function (scope) {
                scope.edit = scope.task.id && scope.task.id.length > 0;
                Task.retrieveTaskCategories().then(function (res) {
                    scope.categories = res.data;
                });
            }
        };
    };
    taskForm.$inject = ['Task'];

    var categoryIcon = function () {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                ref: '='
            },
            template: '<span><i ng-if="ref && icon" class="fa {{class}}"></i></span>',
            link: function (scope) {
                scope.class = '';
                switch (scope.ref) {
                    case 'fun':
                        scope.icon = true;
                        scope.class = 'fa-futbol-o';
                        break;
                    case 'DIY':
                        scope.icon = true;
                        scope.class = 'fa-briefcase';
                        break;
                    case 'administrative':
                        scope.icon = true;
                        scope.class = 'fa-file-text';
                        break;
                    case 'cleaning':
                        scope.icon = true;
                        scope.class = 'fa-bath';
                        break;
                }
            }
        };
    };

    var avatarItemSelector = function () {
        return {
            restrict: 'E',
            replace: true,
            scope: {
              userRef: '=',
              items: '='
            },
            template: '<div class="avatar-selector">' +
            '   <ul>' +
            '       <li class="avatar-element" ng-if="elements.length > 0" ' +
            '           ng-repeat="element in elements" ' +
            '           ng-class="{\'active\':((!userRef.avatar || !userRef.avatar[element.itemType]) && element.itemId === \'empty\') || userRef.avatar[element.itemType] === element.itemId}"' +
            '           ng-click="chooseItem(element)">' +
            '           <span><img ng-src="/resources/img/avatar/{{element.itemType}}/{{element.itemId}}.png" title="{{element.itemName | translate}}"/></span>' +
            '       </li>' +
            '   </ul>' +
            '</div>',
            link: function (scope, element, attrs) {
                var refType = attrs.type;
                scope.$watch('items', function (items) {
                    if (items) {
                        scope.elements = scope.items.filter(function (i) {
                            return i.itemType === refType;
                        });
                        scope.elements.unshift({itemId:'empty', itemName:'None', itemType: refType, repeatable: false});
                    }
                });

                scope.chooseItem = function (element) {
                    if (!scope.userRef.avatar) {
                        scope.userRef.avatar = {};
                    }
                    scope.userRef.avatar[refType] = element.itemId;
                };
            }
        };
    };

    var avatarDisplayer = function () {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                avatarConfig: '=',
                avatarId: '='
            },
            template: '<div class="avatar-displayer">' +
            '   <div ng-if="hasPreview">' +
            '       <img class="avatar-background" ng-if="avatarConfig[\'background\']" ng-src="/resources/img/avatar/background/{{avatarConfig[\'background\']}}.png"/>' +
            '       <img class="avatar-head" ng-if="avatarConfig[\'head\']" ng-src="/resources/img/avatar/head/{{avatarConfig[\'head\']}}.png"/>' +
            '       <img class="avatar-hair" ng-if="avatarConfig[\'hair\']" ng-src="/resources/img/avatar/hair/{{avatarConfig[\'hair\']}}.png"/>' +
            '       <img class="avatar-gadget" ng-if="avatarConfig[\'gadget\']" ng-src="/resources/img/avatar/gadget/{{avatarConfig[\'gadget\']}}.png"/>' +
            '       <img class="avatar-face" ng-if="avatarConfig[\'face\']" ng-src="/resources/img/avatar/face/{{avatarConfig[\'face\']}}.png"/>' +
            '       <img class="avatar-clothes" ng-if="avatarConfig[\'clothes\']" ng-src="/resources/img/avatar/clothes/{{avatarConfig[\'clothes\']}}.png"/>' +
            '   </div>' +
            '   <div ng-if="!hasPreview">' +
            '       <img src="/resources/img/user.png"/>' +
            '   </div>' +
            '</div>',
            link: function (scope) {
                scope.hasPreview = false;
                scope.$watch('avatarConfig', function (value) {
                    if (value) {
                       scope.hasPreview = true;
                    }
                });
            }
        };
    };

    angular.module("HomeApp.directives").directive("userPresentation", userPresentation);
    angular.module("HomeApp.directives").directive("taskForm", taskForm);
    angular.module("HomeApp.directives").directive("categoryIcon", categoryIcon);
    angular.module("HomeApp.directives").directive("avatarItemSelector", avatarItemSelector);
    angular.module("HomeApp.directives").directive("avatarDisplayer", avatarDisplayer);
}(angular));