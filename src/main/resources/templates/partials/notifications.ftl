<div class="notifications-container">
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
                    <span>{{notification.creationDate | date:'dd/MM/yyyy'}} - {{notification.message}}</span>
                </div>
                <div class="validation col-md-2" ng-if="notification.requireValidation === true && notification.validated !== true">
                    <button type="button" class="btn btn-primary" ng-click="validTask(notification)">
                        <i class="fa fa-check mr-2"></i> <span translate>Task is done</span>
                    </button>
                </div>
            </li>
        </ul>
    </div>
</div>