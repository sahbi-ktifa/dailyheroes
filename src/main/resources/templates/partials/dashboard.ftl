<div class="dashboard-container">
    <div class="loading" ng-if="loading">
        <i class="fa fa-refresh fa-spin fa-5x fa-fw"></i>
        <span class="sr-only">Loading...</span>
    </div>
    <div class="dashboard-users" ng-if="!loading">
        <h3>Players</h3>
        <ul>
            <li ng-repeat="user in dashboard.users | orderBy:'level'">
                <user-presentation username="{{user}}"></user-presentation>
            </li>
        </ul>
    </div>
    <div class="dashboard-tasks" ng-if="!loading">
        <h3>Tasks list</h3>
        <ul>
            <li ng-repeat="task in tasks">
                <span>{{task.name}}</span>
            </li>
            <li ng-if="tasks.length === 0">No tasks available. Add one !</li>
        </ul>
    </div>
</div>