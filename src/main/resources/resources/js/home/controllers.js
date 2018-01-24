(function(angular) {
    var HomeCtrl = function($scope, User, Dashboard, Notification, $interval, $location) {
        $scope.dashboard = {};
        $scope.selectedDashboard = null;
        $scope.favoriteDashboard = null;
        $scope.notificationsCount = 0;
        var routes = ['/', '/profile', '/notifications', '/task'];

        User.retrieveUser(username).then(function (res) {
            $scope.selectedDashboard = res.data.favoriteDashboard;
            $scope.favoriteDashboard = res.data.favoriteDashboard;
            if (!$scope.selectedDashboard) {
                Dashboard.retrieveDashboardsForUser().then(function (response) {
                    if (response.data) {
                        $scope.selectedDashboard = response.data[0].id;
                    }
                });
            }
        });

        var checkNotifications = function () {
            Notification.checkNotifications(username).then(function (res) {
                $scope.notificationsCount = res.data;
            }).catch(function (reason) {
                console.log('Unable to check notifications: ' + (reason.data ? reason.data : 'Unknown error.'));
            });
        };

        $scope.$on('$locationChangeStart', function($event, next) {
            $scope.currentRoute = next;
            checkNotifications();
        });

        $scope.$on('changeDashboard', function (e, id) {
            $scope.selectedDashboard = id;
        });

        $scope.$on('notificationConsumed', function () {
            checkNotifications();
        });

        $scope.logout = function () {
            window.location.href = contextPath + '/logout';
        };

        $scope.goToPrevious = function () {
            var idx = routes.indexOf($location.path()) - 1;
            if (idx < 0) {
                idx = 0;
            }
            $location.path(routes[idx]);
        };

        $scope.goToNext = function () {
            var idx = routes.indexOf($location.path()) + 1;
            if (idx > routes.length) {
                idx = routes.length;
            }
            $location.path(routes[idx]);
        };

        $interval(function() {
            checkNotifications();
        }, 20000);
    };
    HomeCtrl.$inject = ['$scope', 'User', 'Dashboard', 'Notification', '$interval', '$location'];

    var DashboardCtrl = function($scope, Dashboard, Task, $uibModal, gettextCatalog) {
        $scope.loading = true;
        $scope.tasks = [];
        var refreshTasks = function () {
            Task.retrieveTasks($scope.dashboard.id).then(function (response) {
                $scope.tasks = response.data;
                $scope.loading = false;
            });
        };

        var refreshDashboard = function () {
            Dashboard.retrieveDashboard($scope.selectedDashboard).then(function (response) {
                if (response.data) {
                    $scope.dashboard = response.data;
                    refreshTasks();
                }
            });
        };

        $scope.$watch('selectedDashboard', function (value) {
            if (value) {
                refreshDashboard();
            }
        }, true);

        $scope.validTask = function (task) {
            var modalInstance = $uibModal.open({
                template: '<div>' +
                '   <div class="modal-header">' +
                '       <h3 class="modal-title" translate>Did someone helped you in this task?</h3>' +
                '   </div>' +
                '   <div class="modal-body">' +
                '       <select style="width: 100%;" multiple="true" ng-model="$ctrl.achievers" ng-options="u as u for u in $ctrl._users"></select>' +
                '   </div>' +
                '   <div class="modal-footer">' +
                '       <button class="btn btn-primary" type="button" ng-click="$ctrl.ok()" translate>Submit</button>' +
                '       <button class="btn btn-danger" type="button" ng-click="$ctrl.cancel()" translate>Cancel</button>' +
                '   </div>' +
                '</div>',
                controller: ValidTaskCtrl,
                controllerAs: '$ctrl',
                resolve: {
                    _users: function () {
                        return $scope.dashboard.users.filter(function (u) {
                            return u !== username;
                        });
                    }
                }
            });

            modalInstance.result.then(function (achievers) {
                achievers.unshift(username);
                Task.validTask(task.id, achievers).then(function () {
                    refreshTasks();
                });
            });
        };

        $scope.deleteTask = function (task) {
            if (confirm(gettextCatalog.getString('Are you sure that you want to delete this task?'))) {
                Task.deleteTask(task.id).then(function () {
                    refreshTasks();
                });
            }
        };

        $scope.editTask = function (task) {
            var modalInstance = $uibModal.open({
                template: '<div>' +
                '   <div class="modal-header">' +
                '       <h3 class="modal-title" translate>Edit task</h3>' +
                '   </div>' +
                '   <div class="modal-body">' +
                '       <task-form task="$ctrl.task"></task-form>' +
                '   </div>' +
                '   <div class="modal-footer">' +
                '       <button class="btn btn-primary" type="button" ng-click="$ctrl.ok()" translate>Submit</button>' +
                '       <button class="btn btn-danger" type="button" ng-click="$ctrl.cancel()" translate>Cancel</button>' +
                '   </div>' +
                '</div>',
                controller: EditTaskCtrl,
                controllerAs: '$ctrl',
                resolve: {
                    task: function () {
                        return task;
                    }
                }
            });

            modalInstance.result.then(function (task) {
                 Task.updateTask(task).then(function () {
                     refreshTasks();
                 });
            });
        };

        $scope.favoriteMe = function () {
            Dashboard.favoriteMe($scope.selectedDashboard).then(function() {
                $scope.favoriteDashboard = $scope.selectedDashboard;
            });
        };
    };
    DashboardCtrl.$inject = ['$scope', 'Dashboard', 'Task', '$uibModal', 'gettextCatalog'];

    var ValidTaskCtrl = function($scope, $uibModalInstance, _users) {
        var $ctrl = this;
        $ctrl._users = _users;
        $ctrl.achievers = [];

        $ctrl.ok = function () {
            $uibModalInstance.close($ctrl.achievers);
        };

        $ctrl.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    };
    ValidTaskCtrl.$inject = ['$scope', '$uibModalInstance', '_users'];

    var EditTaskCtrl = function($scope, $uibModalInstance, task) {
        var $ctrl = this;
        $ctrl.task = task;

        $ctrl.ok = function () {
            $uibModalInstance.close($ctrl.task);
        };

        $ctrl.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    };
    EditTaskCtrl.$inject = ['$scope', '$uibModalInstance', 'task'];

    var ProfileCtrl = function($scope, User, Loot) {
        $scope.loading = true;
        $scope.userUpdate = false;
        Loot.retrieveItemTypes().then(function (res) {
            $scope.types = res.data.filter(function (type) {
                return type !== 'other';
            });
        });
        var refreshLoots = function () {
            Loot.retrieveLoots(username).then(function (res) {
                $scope.rewards = res.data.filter(function (item) {
                    return item.repeatable === true;
                });
                $scope.unlocked = res.data.filter(function (item) {
                    return item.repeatable === false;
                });
                Loot.retrieveItems().then(function (res) {
                    $scope.items = res.data.filter(function (item) {
                        return item.repeatable === false;
                    });
                });
            });
        };
        User.retrieveUser(username).then(function (res) {
            $scope.user = res.data;
            $scope.loading = false;
            User.getNextLevelCap(username, $scope.user.level).then(function (res) {
                $scope.expToNextLevel = res.data;
                $scope.percentExpToNextLevel = ($scope.user.currentExp * 100) / $scope.expToNextLevel;
            });
            refreshLoots();
        });

        $scope.retrieveClass = function (type) {
            var _class = '';
            switch (type) {
                case 'background':
                    _class = 'fa-paint-brush';
                    break;
                case 'hair':
                    _class = 'fa-scissors';
                    break;
                case 'head':
                    _class = 'fa-user';
                    break;
                case 'eye':
                    _class = 'fa-eye';
                    break;
                case 'gadget':
                    _class = 'fa-star';
                    break;
                case 'clothes':
                    _class = 'fa-tags';
                    break;
                case 'face':
                    _class = 'fa-smile-o';
                    break;
            }
            return _class;
        };

        var init = false;
        $scope.$watch('user.avatar', function (value) {
           if (value && init === true) {
               $scope.userUpdate = true;
           } else if (value) {
               init = true;
           }
        }, true);

        $scope.updateUser = function () {
            User.saveUserAvatar($scope.user.username, $scope.user.avatar).then(function () {
               $scope.userUpdate = false;
            }).catch(function (reason) {
               console.log(reason);
            });
        };

        $scope.received = function (reward) {
            Loot.received(reward).then(function () {
                refreshLoots();
            }) ;
        };
    };
    ProfileCtrl.$inject = ['$scope', 'User', 'Loot'];

    var NotificationsCtrl = function($scope, Notification, Task, Dashboard) {
        $scope.loading = true;
        var retrieveNotifications = function () {
            Notification.retrieveNotifications(username).then(function (res) {
                $scope.loading = false;
                $scope.notifications = res.data;
            });
        };
        retrieveNotifications();

        $scope.consume = function (notification) {
            if (notification.read === 0) {
                Notification.consumeNotification(notification.id).then(function () {
                    retrieveNotifications();
                    $scope.$emit('notificationConsumed');
                });
            }
        };

        $scope.validTask = function (notification) {
            Task.confirmValidTask(notification.taskId).then(function () {
                retrieveNotifications();
                $scope.$emit('notificationConsumed');
            });
        };

        $scope.joinGame = function (notification) {
            Dashboard.joinDashboard(notification.id, notification.extra['dashboardId']).then(function () {
                retrieveNotifications();
                $scope.$emit('notificationConsumed');
            });
        };
    };
    NotificationsCtrl.$inject = ['$scope', 'Notification', 'Task', 'Dashboard'];

    var CreateTaskCtrl = function($scope, Task) {
        $scope.task = {};
        $scope.success = false;
        var refId = $scope.selectedDashboard;

        $scope.createTask = function () {
            $scope.success = false;
            $scope.task.status = 'todo';
            $scope.task.dashboardId = refId;
            Task.createTask($scope.task).then(function () {
                $scope.task = {};
                $scope.success = true;
            });
        };
    };
    CreateTaskCtrl.$inject = ['$scope', 'Task'];

    angular.module("HomeApp.controllers").controller("HomeCtrl", HomeCtrl);
    angular.module("HomeApp.controllers").controller("DashboardCtrl", DashboardCtrl);
    angular.module("HomeApp.controllers").controller("ProfileCtrl", ProfileCtrl);
    angular.module("HomeApp.controllers").controller("NotificationsCtrl", NotificationsCtrl);
    angular.module("HomeApp.controllers").controller("CreateTaskCtrl", CreateTaskCtrl);
}(angular));