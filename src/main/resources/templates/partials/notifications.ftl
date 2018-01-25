<div class="notifications-container" ng-swipe-left="goToNext()" ng-swipe-right="goToPrevious()">
    <div class="loading" ng-if="loading">
        <i class="fa fa-refresh fa-spin fa-5x fa-fw"></i>
        <span class="sr-only">Loading...</span>
    </div>
    <div class="notifications-list">
        <h3 translate>Notifications list</h3>
        <ul>
            <li ng-repeat="notification in notifications | orderBy:'-creationDate'">
                <div class="col-md-12" ng-class="{'unread': notification.read === 0,
                 'col-md-10': notification.requireValidation === true && notification.validated !== true}"
                     ng-click="consume(notification)">
                    <i ng-if="notification.extra.icon" class="fa {{notification.extra.icon}}"></i>
                    <span>{{notification.creationDate | date:'dd/MM/yyyy'}}</span>
                    <span> - </span>
                    <b ng-if="notification.from">{{notification.from}} </b>
                    <span>{{notification.message | translate}}</span>
                    <i ng-if="notification.suffix"> {{notification.suffix | translate}}!</i>
                </div>
                <div class="validation col-md-2" ng-if="notification.requireValidation === true && notification.validated !== true">
                    <button ng-if="notification.taskId" type="button" class="btn btn-primary" ng-click="validTask(notification)">
                        <i class="fa fa-check mr-2"></i> <span translate>Task is done</span>
                    </button>
                    <button ng-if="!notification.taskId" type="button" class="btn btn-primary" ng-click="joinGame(notification)">
                        <i class="fa fa-check mr-2"></i> <span translate>Join game</span>
                    </button>
                </div>
            </li>
        </ul>
    </div>
</div>