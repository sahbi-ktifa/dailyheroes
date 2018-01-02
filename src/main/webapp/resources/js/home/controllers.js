(function(angular) {
    var HomeCtrl = function($scope, Notification) {
        $scope.dashboard = {};
        $scope.notificationsCount = 0;
        var checkNotifications = function () {
            Notification.checkNotifications(username).then(function (res) {
                $scope.notificationsCount = res.data;
            });
        };

        $scope.$on('$locationChangeStart', function($event, next) {
            $scope.currentRoute = next;
            checkNotifications();
        });

        $scope.$on('notificationConsumed', function () {
            checkNotifications();
        });

        $scope.logout = function () {
            window.location.href = contextPath + '/logout';
        };
    };
    HomeCtrl.$inject = ['$scope', 'Notification'];

    var DashboardCtrl = function($scope, Dashboard, Task, $uibModal) {
        $scope.loading = true;
        $scope.tasks = [];
        var refreshTasks = function () {
            Task.retrieveTasks($scope.dashboard.id).then(function (response) {
                $scope.tasks = response.data;
                $scope.loading = false;
            });
        };

        Dashboard.retrieveDashboardForUser(username).then(function (response) {
            $scope.dashboard = response.data;
            refreshTasks();
        });

        $scope.validTask = function (task) {
            if (confirm('Are you sure that you performed this task?')) {
                Task.validTask(task.id).then(function () {
                    refreshTasks();
                });
            }
        };

        $scope.deleteTask = function (task) {
            if (confirm('Are you sure that you want to delete this task?')) {
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
                '       <button class="btn btn-primary" type="button" ng-click="$ctrl.ok()" translate>Submit</button>\n' +
                '       <button class="btn btn-danger" type="button" ng-click="$ctrl.cancel()" translate>Cancel</button>\n' +
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
    };
    DashboardCtrl.$inject = ['$scope', 'Dashboard', 'Task', '$uibModal'];

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
        User.retrieveUser(username).then(function (res) {
            $scope.user = res.data;
            $scope.loading = false;
            User.getNextLevelCap(username, $scope.user.level).then(function (res) {
                $scope.expToNextLevel = res.data;
                $scope.percentExpToNextLevel = ($scope.user.currentExp * 100) / $scope.expToNextLevel;
            });
            Loot.retrieveLoots(username).then(function (res) {
                $scope.rewards = res.data.filter(function (item) {
                    return item.repeatable === true;
                });
                $scope.items = res.data.filter(function (item) {
                    return item.repeatable === false;
                });
            });
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
            });
        };
    };
    ProfileCtrl.$inject = ['$scope', 'User', 'Loot'];

    var NotificationsCtrl = function($scope, Notification, Task) {
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
    };
    NotificationsCtrl.$inject = ['$scope', 'Notification', 'Task'];

    var CreateTaskCtrl = function($scope, Task, Dashboard) {
        $scope.task = {};
        $scope.success = false;
        var refId = null;

        Dashboard.retrieveDashboardForUser(username).then(function (response) {
            refId = response.data.id;
        });

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
    CreateTaskCtrl.$inject = ['$scope', 'Task', 'Dashboard'];

    angular.module("HomeApp.controllers").controller("HomeCtrl", HomeCtrl);
    angular.module("HomeApp.controllers").controller("DashboardCtrl", DashboardCtrl);
    angular.module("HomeApp.controllers").controller("ProfileCtrl", ProfileCtrl);
    angular.module("HomeApp.controllers").controller("NotificationsCtrl", NotificationsCtrl);
    angular.module("HomeApp.controllers").controller("CreateTaskCtrl", CreateTaskCtrl);
}(angular));