(function(angular) {
    var userPresentation = function (User) {
        return {
            restrict: 'E',
            replace: true,
            template: '<div class="user-container">' +
            '   <div class="user-avatar">' +
            '       <img src="/resources/img/user.png"/>' +
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
              avatar: '=',
              items: '='
            },
            template: '<div class="avatar-selector">' +
            '   <ul>' +
            '       <li class="avatar-element" ng-if="elements.length > 0" ng-repeat="element in elements" ng-class="{\'active\':((!user.avatar || !user.avatar[element.itemType]) && element.itemId === \'empty\') || user.avatar[element.itemType] === element.itemId}">' +
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
            }
        };
    };

    angular.module("HomeApp.directives").directive("userPresentation", userPresentation);
    angular.module("HomeApp.directives").directive("taskForm", taskForm);
    angular.module("HomeApp.directives").directive("categoryIcon", categoryIcon);
    angular.module("HomeApp.directives").directive("avatarItemSelector", avatarItemSelector);
}(angular));