(function(angular) {
    var DashboardFactory = function($http) {
        var dashboardService = {};

        dashboardService.retrieveDashboardForUser = function(username) {
            return $http.get(contextPath + '/dashboard/' + username);
        };

        return dashboardService;
    };
    DashboardFactory.$inject = ['$http'];

    var TaskFactory = function($http) {
        var taskService = {};

        taskService.retrieveTasks = function(dashboardId) {
            return $http.get(contextPath + '/task/' + dashboardId);
        };

        taskService.retrieveTaskCategories = function() {
            return $http.get(contextPath + '/task/info/categories');
        };

        taskService.retrieveTaskRedundancy = function() {
            return $http.get(contextPath + '/task/info/redundancy');
        };

        taskService.createTask = function (task) {
            if (!task.complexity) {
                task.complexity = 0;
            }
            return $http.post(contextPath + '/task', task);
        };

        taskService.updateTask = function (task) {
            return $http.post(contextPath + '/task/' + task.id, task);
        };

        taskService.deleteTask = function (taskId) {
            return $http.delete(contextPath + '/task/' + taskId);
        };

        taskService.validTask = function (taskId) {
            return $http.post(contextPath + '/task/' + taskId + '/valid');
        };

        taskService.confirmValidTask = function (taskId) {
            return $http.post(contextPath + '/task/' + taskId + '/validated');
        };

        return taskService;
    };
    TaskFactory.$inject = ['$http'];

    var UserFactory = function($http) {
        var userService = {};

        userService.retrieveUser = function(username) {
            return $http.get(contextPath + '/user/' + username);
        };

        userService.getNextLevelCap = function(username, userLevel) {
            return $http.get(contextPath + '/user/' + username + '/' + userLevel);
        };

        userService.saveUserAvatar = function(username, avatar) {
            return $http.post(contextPath + '/user/' + username + '/avatar', avatar);
        };

        return userService;
    };
    UserFactory.$inject = ['$http'];

    var NotificationFactory = function($http) {
        var notificationService = {};

        notificationService.checkNotifications = function(username) {
            return $http.get(contextPath + '/notification/' + username + '/check');
        };

        notificationService.retrieveNotifications = function(username) {
            return $http.get(contextPath + '/notification/' + username);
        };

        notificationService.consumeNotification = function(notificationId) {
            return $http.post(contextPath + '/notification/' + notificationId);
        };

        return notificationService;
    };
    NotificationFactory.$inject = ['$http'];

    var LootFactory = function($http) {
        var lootService = {};

        lootService.retrieveItemTypes = function() {
            return $http.get(contextPath + '/loot');
        };

        lootService.retrieveLoots = function(username) {
            return $http.get(contextPath + '/loot/' + username);
        };

        lootService.received = function(loot) {
            return $http.post(contextPath + '/loot/' + loot.lootId);
        };

        return lootService;
    };
    LootFactory.$inject = ['$http'];

    angular.module("HomeApp.services").factory("Dashboard", DashboardFactory);
    angular.module("HomeApp.services").factory("Task", TaskFactory);
    angular.module("HomeApp.services").factory("User", UserFactory);
    angular.module("HomeApp.services").factory("Notification", NotificationFactory);
    angular.module("HomeApp.services").factory("Loot", LootFactory);
}(angular));