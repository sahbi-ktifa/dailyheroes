(function(angular) {
    var userPresentation = function (User) {
        return {
            restrict: 'E',
            replace: true,
            template: '<div class="user-container">' +
            '   <div class="user-avatar">' +
            '       <avatar-displayer avatar-config="refUser.avatar"></avatar-displayer>' +
            '   </div>' +
            '   <div class="user-info">' +
            '       <span>{{refUser.username}}</span>' +
            '       <span> - </span>' +
            '       <span translate>Level</span><span> {{refUser.level}}</span>' +
            '   </div>' +
            '</div>',
            link: function (scope, element, attrs) {
                var username = attrs.username;
                User.retrieveUser(username).then(function (res) {
                    scope.refUser = res.data;
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
              items: '=',
              unlocked: '='
            },
            template: '<div class="avatar-selector">' +
            '   <div ng-repeat="subType in subTypes">' +
            '       <b ng-if="subType.length > 0">{{subType | translate}}</b>' +
            '       <ul>' +
            '           <li class="avatar-element {{element.itemType}} {{element.subType}} {{element.rarity}}" ng-if="elements.length > 0"' +
            '               ng-repeat="element in elements | filter:subType | orderBy:[\'-locked\',\'availableAt\',\'rarity\']" ' +
            '               ng-class="{\'active\':((!userRef.avatar || (!userRef.avatar[element.itemType] && !userRef.avatar[element.itemType + \'-\' + element.subType])) && element.itemId.startsWith(\'empty\'))' +
            '                    || userRef.avatar[element.itemType] === element.itemId || userRef.avatar[element.itemType + \'-\' + element.subType] === element.itemId,' +
        '                       \'locked\':element.locked === true}"' +
            '               ng-click="chooseItem(element)">' +
            '               <span>' +
            '                   <img ng-if="element.itemId.indexOf(\'-\') > -1" ng-src="/img/avatar/{{element.itemType}}/{{element.itemId.substr(0, element.itemId.indexOf(\'-\'))}}.{{element.itemId.indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}" title="{{element.itemName | translate}}"/>' +
            '                   <img ng-if="element.itemId.indexOf(\'-\') === -1" ng-src="/img/avatar/{{element.itemType}}/{{element.itemId}}.{{element.itemId.indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}" title="{{element.itemName | translate}}"/>' +
            '                   <span class="badge btn-warning" ng-if="element.locked">{{element.availableAt}}</span>' +
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
                        scope.elements = scope.items.filter(function (element) {
                            if (scope.unlocked.filter(function (i) {
                                    return i.itemId === element.itemId;
                                }).length === 0) {
                                element.locked = true;
                            }
                            return element.itemType === refType;
                        });
                        var empties = [];
                        scope.elements.forEach(function (e) {
                            if (e.subType && scope.subTypes.indexOf(e.subType) < 0) {
                                scope.subTypes.push(e.subType);
                                empties.push({itemId:'empty-' + e.subType, itemName:'None', itemType: refType, subType: e.subType, repeatable: false, availableAt: 0, rarity: 0})
                            }
                        });
                        if (scope.subTypes.length === 0) {
                            scope.subTypes.push('');
                        }
                        if (empties.length === 0) {
                            empties.push({itemId:'empty', itemName:'None', itemType: refType, subType: '', repeatable: false, availableAt: 0, rarity: 0})
                        }
                        for (var idx in empties) {
                            scope.elements.unshift(empties[idx]);
                        }
                    }
                });

                scope.chooseItem = function (element) {
                    if (element.locked === true) {
                        return;
                    }
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
            '       <img class="avatar-background" ng-if="avatarConfig[\'background\']" ng-src="/img/avatar/background/{{avatarConfig[\'background\']}}.{{avatarConfig[\'background\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-head" ng-if="avatarConfig[\'head\']" ng-src="/img/avatar/head/{{avatarConfig[\'head\']}}.{{avatarConfig[\'head\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-hair" ng-if="avatarConfig[\'hair\']" ng-src="/img/avatar/hair/{{avatarConfig[\'hair\']}}.{{avatarConfig[\'hair\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-gadget" ng-if="avatarConfig[\'gadget-gadget1\']" ng-src="/img/avatar/gadget/{{avatarConfig[\'gadget-gadget1\']}}.{{avatarConfig[\'gadget-gadget1\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-gadget" ng-if="avatarConfig[\'gadget-gadget2\']" ng-src="/img/avatar/gadget/{{avatarConfig[\'gadget-gadget2\']}}.{{avatarConfig[\'gadget-gadget2\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-face" ng-if="avatarConfig[\'face-beard\']" ng-src="/img/avatar/face/{{avatarConfig[\'face-beard\']}}.{{avatarConfig[\'face-beard\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-face" ng-if="avatarConfig[\'face-eyes\']" ng-src="/img/avatar/face/{{avatarConfig[\'face-eyes\']}}.{{avatarConfig[\'face-eyes\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-face" ng-if="avatarConfig[\'face-lips\']" ng-src="/img/avatar/face/{{avatarConfig[\'face-lips\']}}.{{avatarConfig[\'face-lips\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '       <img class="avatar-clothes" ng-if="avatarConfig[\'clothes\']" ng-src="/img/avatar/clothes/{{avatarConfig[\'clothes\']}}.{{avatarConfig[\'clothes\'].indexOf(\'animated\') > -1 ? \'gif\' : \'png\'}}"/>' +
            '   </div>' +
            '   <div ng-if="!hasPreview">' +
            '       <img src="/img/user.png"/>' +
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

    var dashboardSelector = function (Dashboard) {
        return {
            restrict: 'E',
            replace: true,
            template: '<div class="dashboard-selector" ng-show="availableDashboards.length > 0">' +
            '       <label translate>Change dashboard:</label>' +
            '       <select class="form-control" ng-change="changeDashboard()" ng-model="currentDashboard" ng-options="d.id as d.name for d in availableDashboards"></select>' +
            '   </div>',
            link: function (scope) {
                scope.availableDashboards = [];
                scope.$watch('dashboard', function (dashboard) {
                    if (dashboard) {
                        scope.currentDashboard = scope.dashboard.id;
                        Dashboard.retrieveDashboardsForUser().then(function (res) {
                            scope.availableDashboards = res.data.filter(function (d) {
                                return d.id !== dashboard.id;
                            });
                        });
                    }
                });

                scope.changeDashboard = function () {
                    if (scope.currentDashboard) {
                        scope.$emit('changeDashboard', scope.currentDashboard);
                    }
                };
            }
        };
    };
    dashboardSelector.$inject = ['Dashboard'];

    var version = function ($http) {
        return {
            restrict: 'E',
            replace: true,
            scope: {},
            template: '<span style="font-style: italic;color: lightgrey;">{{version}}</span>',
            link: function (scope) {
                $http.get(contextPath + '/info').then(function (res) {
                    scope.version = res.data.build ? res.data.build.version : 'SNAPSHOT';
                });
            }
        };
    };
    version.$inject = ['$http'];

    angular.module("HomeApp.directives").directive("userPresentation", userPresentation);
    angular.module("HomeApp.directives").directive("taskForm", taskForm);
    angular.module("HomeApp.directives").directive("categoryIcon", categoryIcon);
    angular.module("HomeApp.directives").directive("avatarItemSelector", avatarItemSelector);
    angular.module("HomeApp.directives").directive("avatarDisplayer", avatarDisplayer);
    angular.module("HomeApp.directives").directive("dashboardSelector", dashboardSelector);
    angular.module("HomeApp.directives").directive("version", version);
}(angular));