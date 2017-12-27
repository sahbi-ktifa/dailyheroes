<div class="notifications-container">
    <div class="loading" ng-if="loading">
        <i class="fa fa-refresh fa-spin fa-5x fa-fw"></i>
        <span class="sr-only">Loading...</span>
    </div>
    <div class="notifications-list">
        <h3>Notifications</h3>
        <ul>
            <li ng-repeat="notification in notifications | orderBy:'-creationDate'">
                <div ng-class="{'unread': notification.read === 0,
                 'col-md-10': notification.requireValidation === true && notification.validated === false,
                 'col-md-12': notification.requireValidation !== true && notification.validated === false}"
                     ng-click="consume(notification)">
                    <span>{{notification.creationDate | date:'dd/MM/yyyy'}} - {{notification.message}}</span>
                </div>
                <div class="validation col-md-2" ng-if="notification.requireValidation === true && notification.validated === false">
                    <button type="button" class="btn btn-primary" ng-click="validTask(notification)">
                        <i class="fa fa-check mr-2"></i> Task is done.
                    </button>
                </div>
            </li>
        </ul>
    </div>
</div>