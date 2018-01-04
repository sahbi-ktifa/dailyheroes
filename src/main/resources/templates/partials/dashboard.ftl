<div class="dashboard-container">
    <div class="loading" ng-if="loading">
        <i class="fa fa-refresh fa-spin fa-5x fa-fw"></i>
        <span class="sr-only">Loading...</span>
    </div>
    <dashboard-selector ng-if="!loading"></dashboard-selector>
    <div class="dashboard-users" ng-if="!loading">
        <h3 translate>Players list</h3>
        <ul>
            <li ng-repeat="user in dashboard.users | orderBy">
                <user-presentation username="{{user}}"></user-presentation>
            </li>
        </ul>
    </div>
    <div class="dashboard-tasks" ng-if="!loading">
        <h3 translate>Tasks list</h3>
        <ul>
            <li ng-repeat="task in tasks">
                <div class="col-md-5">
                    <category-icon ref="task.category"></category-icon>
                    <span>{{task.name}}</span>
                    <span uib-rating ng-model="task.complexity" read-only="true" aria-labelledby="default-rating"></span>
                    <span ng-if="task.dueDate"> - {{task.dueDate | date:'dd/MM/yyyy'}} <i ng-if="task.redundancy" class="fa fa-refresh"></i></span>
                </div>
                <div class="col-md-4">
                    <span class="notes">{{task.notes}}</span>
                </div>
                <div class="col-md-3">
                    <button type="button" class="btn btn-primary" ng-click="validTask(task)">
                        <i class="fa fa-check mr-2"></i> <span translate>I did it</span>
                    </button>
                    <button type="button" class="btn btn-default" ng-click="editTask(task)">
                        <i class="fa fa-pencil mr-2"></i> <span translate>Edit</span>
                    </button>
                    <button type="button" class="btn btn-danger" ng-click="deleteTask(task)">
                        <i class="fa fa-remove mr-2"></i>
                    </button>
                </div>
            </li>
            <li ng-if="tasks.length === 0" translate>No tasks available. Add one!</li>
        </ul>
    </div>
</div>