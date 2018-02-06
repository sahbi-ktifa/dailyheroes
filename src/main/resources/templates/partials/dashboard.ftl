<div class="dashboard-container">
    <div class="loading" ng-if="loading">
        <i class="fa fa-refresh fa-spin fa-5x fa-fw"></i>
        <span class="sr-only">Loading...</span>
    </div>
    <dashboard-selector ng-if="!loading"></dashboard-selector>
    <div class="dashboard-users" ng-if="!loading">
        <h3>
            <span>{{dashboard.name}}</span>
            <i ng-if="favoriteDashboard && favoriteDashboard === selectedDashboard" title="{{'Favorite dashboard' | translate}}" class="fa fa-star yellow"></i>
            <i ng-if="favoriteDashboard && favoriteDashboard !== selectedDashboard" title="{{'Choose as my favorite dashboard' | translate}}" class="fa fa-star-o" ng-click="favoriteMe()"></i>
        </h3>
        <ul>
            <li ng-repeat="user in dashboard.users">
                <user-presentation username="{{user}}"></user-presentation>
                <reward-alerter ng-if="user != username" username="{{user}}"></reward-alerter>
            </li>
        </ul>
    </div>
    <div class="dashboard-tasks" ng-if="!loading" ng-swipe-left="goToNext()" ng-swipe-right="goToPrevious()">
        <h3 translate>Tasks list</h3>
        <div class="filter-container">
            <i class="fa fa-filter fa-lg" ng-click="showFilter = !showFilter" ng-class="{'active':filtered.length > 0}"></i>
            <select ng-show="showFilter" class="form-control" ng-model="filtered" ng-options="c as c | translate for c in categories"></select>
        </div>
        <ul>
            <li ng-repeat="task in tasks | filter:{category:filtered} | orderBy:'dueDate'" ng-class="{'delayed':isDelayed(task)}">
                <div class="col-md-5 summary">
                    <category-icon ref="task.category"></category-icon>
                    <span uib-rating class="rating" ng-model="task.complexity" read-only="true" aria-labelledby="default-rating"></span>
                    <span ng-if="task.dueDate"> ({{task.dueDate | date:'dd/MM/yy'}}) <i ng-if="task.redundancy" class="fa fa-refresh"></i></span><br/>
                    <span>{{task.name}}</span>
                </div>
                <div class="col-md-4">
                    <span class="notes">{{task.notes}}</span>
                </div>
                <div class="col-md-3 actions">
                    <button type="button" class="btn btn-primary" ng-click="validTask(task)">
                        <i class="fa fa-check mr-2"></i>
                    </button>
                    <button type="button" class="btn btn-default" ng-click="editTask(task)">
                        <i class="fa fa-pencil mr-2"></i>
                    </button>
                    <button type="button" class="btn btn-danger" ng-click="deleteTask(task)">
                        <i class="fa fa-remove mr-2"></i>
                    </button>
                </div>
            </li>
            <li ng-if="tasks.length === 0 || (tasks | filter:{category:filtered}).length === 0" translate>No tasks available. Add one!</li>
        </ul>
    </div>
</div>