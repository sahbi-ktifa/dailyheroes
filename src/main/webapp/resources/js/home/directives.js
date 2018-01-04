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
            template: '<span><i ng-if="ref && icon" class="fa {{class}} fa-2x"></i></span>',
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
            '   <div ng-repeat="subType in subTypes">' +
            '       <b ng-if="subType.length > 0">{{subType | translate}}</b>' +
            '       <ul>' +
            '           <li class="avatar-element" ng-if="elements.length > 0" ' +
            '               ng-repeat="element in elements | filter:subType" ' +
            '               ng-class="{\'active\':((!userRef.avatar || (!userRef.avatar[element.itemType] && !userRef.avatar[element.itemType + \'-\' + element.subType])) && element.itemId.startsWith(\'empty\'))' +
            '                    || userRef.avatar[element.itemType] === element.itemId || userRef.avatar[element.itemType + \'-\' + element.subType] === element.itemId}"' +
            '               ng-click="chooseItem(element)">' +
            '               <span>' +
            '                   <img ng-if="element.itemId.indexOf(\'-\') > -1" ng-src="/resources/img/avatar/{{element.itemType}}/{{element.itemId.substr(0, element.itemId.indexOf(\'-\'))}}.{{element.itemId.indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}" title="{{element.itemName | translate}}"/>' +
            '                   <img ng-if="element.itemId.indexOf(\'-\') === -1" ng-src="/resources/img/avatar/{{element.itemType}}/{{element.itemId}}.{{element.itemId.indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}" title="{{element.itemName | translate}}"/>' +
            '               </span>' +
            '           </li>' +
            '       </ul>' +
            '   </div>' +
            '</div>',
            link: function (scope, element, attrs) {
                var refType = attrs.type;
                scope.$watch('items', function (items) {
                    if (items) {
                        scope.subTypes = [];
                        scope.elements = scope.items.filter(function (i) {
                            return i.itemType === refType;
                        });
                        var empties = [];
                        scope.elements.forEach(function (e) {
                            if (e.subType && scope.subTypes.indexOf(e.subType) < 0) {
                                scope.subTypes.push(e.subType);
                                empties.push({itemId:'empty-' + e.subType, itemName:'None', itemType: refType, subType: e.subType, repeatable: false})
                            }
                        });
                        if (scope.subTypes.length === 0) {
                            scope.subTypes.push('');
                        }
                        if (empties.length === 0) {
                            empties.push({itemId:'empty', itemName:'None', itemType: refType, subType: '', repeatable: false})
                        }
                        for (var idx in empties) {
                            scope.elements.unshift(empties[idx]);
                        }
                    }
                });

                scope.chooseItem = function (element) {
                    if (!scope.userRef.avatar) {
                        scope.userRef.avatar = {};
                    }
                    if (element.itemId.startsWith('empty-')) {
                        scope.userRef.avatar[refType + '-' + element.subType] = null;
                    } else if (element.itemId.startsWith('empty')) {
                        scope.userRef.avatar[refType] = null;
                    } else if (element.subType) {
                        scope.userRef.avatar[refType + '-' + element.subType] = element.itemId;
                    } else {
                        scope.userRef.avatar[refType] = element.itemId;
                    }
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
            '       <img class="avatar-background" ng-if="avatarConfig[\'background\']" ng-src="/resources/img/avatar/background/{{avatarConfig[\'background\']}}.{{avatarConfig[\'background\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-head" ng-if="avatarConfig[\'head\']" ng-src="/resources/img/avatar/head/{{avatarConfig[\'head\']}}.{{avatarConfig[\'head\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-hair" ng-if="avatarConfig[\'hair\']" ng-src="/resources/img/avatar/hair/{{avatarConfig[\'hair\']}}.{{avatarConfig[\'hair\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-gadget" ng-if="avatarConfig[\'gadget-gadget1\']" ng-src="/resources/img/avatar/gadget/{{avatarConfig[\'gadget-gadget1\']}}.{{avatarConfig[\'gadget-gadget1\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-gadget" ng-if="avatarConfig[\'gadget-gadget2\']" ng-src="/resources/img/avatar/gadget/{{avatarConfig[\'gadget-gadget2\']}}.{{avatarConfig[\'gadget-gadget2\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-face" ng-if="avatarConfig[\'face-beard\']" ng-src="/resources/img/avatar/face/{{avatarConfig[\'face-beard\']}}.{{avatarConfig[\'face-beard\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-face" ng-if="avatarConfig[\'face-eyes\']" ng-src="/resources/img/avatar/face/{{avatarConfig[\'face-eyes\']}}.{{avatarConfig[\'face-eyes\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-face" ng-if="avatarConfig[\'face-lips\']" ng-src="/resources/img/avatar/face/{{avatarConfig[\'face-lips\']}}.{{avatarConfig[\'face-lips\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-clothes" ng-if="avatarConfig[\'clothes\']" ng-src="/resources/img/avatar/clothes/{{avatarConfig[\'clothes\']}}.{{avatarConfig[\'clothes\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
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