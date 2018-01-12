<div class="profile-container" ng-swipe-left="goToNext()" ng-swipe-right="goToPrevious()">
    <div class="loading" ng-if="loading">
        <i class="fa fa-refresh fa-spin fa-5x fa-fw"></i>
        <span class="sr-only">Loading...</span>
    </div>
    <div class="profile-information" ng-if="!loading">
        <h3 translate>My profile</h3>
        <div class="col-md-3 avatar">
            <h5>Avatar</h5>
            <avatar-displayer avatar-config="user.avatar"></avatar-displayer>
        </div>
        <div class="col-md-9 info">
            <div>
                <h5>{{user.username}}</h5>
                <div class="current-level"><span translate>Level</span> {{user.level}}</div>
                <div class="next-level"><span translate>Level</span> {{user.level + 1}}</div>
                <uib-progressbar class="progress-striped active" max="100" value="percentExpToNextLevel">
                    <span style="color:white; white-space:nowrap;">{{user.currentExp}} / {{expToNextLevel}}</span>
                </uib-progressbar>
            </div>
            <div class="item-container">
                <h5>Items</h5>
                <button type="button" class="btn btn-primary" ng-disabled="!userUpdate" ng-click="updateUser()">
                    <i class="fa fa-save"></i> <span translate>Save</span>
                </button>
                <ul>
                    <uib-tabset>
                        <uib-tab index="$index" ng-repeat="type in types">
                            <uib-tab-heading>
                                <i class="fa {{retrieveClass(type)}}" aria-hidden="true"></i>
                                <span>{{type | translate}}</span>
                            </uib-tab-heading>
                            <avatar-item-selector type="{{type}}" user-ref="user" items="items" unlocked="unlocked"></avatar-item-selector>
                        </uib-tab>
                    </uib-tabset>
                </ul>
            </div>
        </div>
        <div class="reward-container col-md-12">
            <h5 translate>Gifts and rewards</h5>
            <ul>
                <li ng-if="rewards && rewards.length > 0" ng-repeat="reward in rewards | orderBy:'-rewardDate'">
                    <div class="col-md-10">
                        <i class="fa fa-gift fa-2x reward" aria-hidden="true"> </i>
                        <span>{{reward.itemName | translate}}</span>
                        <span style="font-style: italic;" ng-if="reward.received === true && reward.rewardDate">({{'Received on' | translate}} {{reward.rewardDate | date:'dd/MM/yyyy'}})</span>
                    </div>
                    <div class="col-md-2">
                        <button type="button" class="btn btn-primary" ng-if="reward.received !== true" ng-click="received(reward)" translate>Got it</button>
                    </div>
                </li>
                <li ng-if="rewards && rewards.length === 0" class="no-reward">
                    <span translate>No gift or reward unlocked, keep playing to do so.</span>
                </li>
            </ul>
        </div>
    </div>
</div>