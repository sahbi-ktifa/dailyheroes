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

        return taskService;
    };
    TaskFactory.$inject = ['$http'];

    var UserFactory = function($http) {
        var userService = {};

        userService.retrieveUser = function(username) {
            return $http.get(contextPath + '/user/' + username);
        };

        return userService;
    };
    UserFactory.$inject = ['$http'];

    angular.module("HomeApp.services").factory("Dashboard", DashboardFactory);
    angular.module("HomeApp.services").factory("Task", TaskFactory);
    angular.module("HomeApp.services").factory("User", UserFactory);
}(angular));