<div class="profile-container">
    <div class="loading" ng-if="loading">
        <i class="fa fa-refresh fa-spin fa-5x fa-fw"></i>
        <span class="sr-only">Loading...</span>
    </div>
    <div class="profile-information" ng-if="!loading">
        <h3 translate>My profile</h3>
        <div class="col-md-4 avatar">

        </div>
        <div class="col-md-8 info">
            <div>
                <b>{{user.username}}</b>
                <div class="current-level"><span translate>Level</span> {{user.level}}</div>
                <div class="next-level"><span translate>Level</span> {{user.level + 1}}</div>
                <uib-progressbar class="progress-striped active" max="100" value="percentExpToNextLevel">
                    <span style="color:white; white-space:nowrap;">{{user.currentExp}} / {{expToNextLevel}}</span>
                </uib-progressbar>
            </div>
        </div>
    </div>
</div>