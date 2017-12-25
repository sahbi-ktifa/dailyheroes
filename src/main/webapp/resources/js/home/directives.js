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
            '       <span>Level {{user.level}}</span>' +
            '       <span> - </span>' +
            '       <span>{{user.username}}</span>' +
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

    var taskForm = function (Task, Dashboard) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                task: '='
            },
            templateUrl: 'partials/task-form.html',
            link: function (scope) {
                Task.retrieveTaskCategories().then(function (res) {
                    scope.categories = res.data;
                });
                Dashboard.retrieveDashboardForUser(username).then(function (response) {
                    scope.task.dashboardId = response.data.id;
                });
            }
        };
    };
    taskForm.$inject = ['Task', 'Dashboard'];

    angular.module("HomeApp.directives").directive("userPresentation", userPresentation);
    angular.module("HomeApp.directives").directive("taskForm", taskForm);
}(angular));