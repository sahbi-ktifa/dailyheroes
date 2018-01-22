<#assign contextPath = path />
<#assign language = language />
<!doctype html>
<html>
<head>
    <title>Daily Heroes</title>
    <meta charset="UTF-8">
    <meta name="description" content="Daily Heroes: Quests for the house! is a multiplayer game to play with your family and/or your friends. Do tasks, earn experience and get rewards every tasks or levels accomplished.">
    <meta name="keywords" content="Daily Heroes,quests,tasks,game,multiplayer,family">
    <meta name="author" content="Sahbi KTIFA, Alexandre Gauthier">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0">

    <link rel="apple-touch-icon" sizes="180x180" href="/favicon/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/favicon/favicon-16x16.png">
    <link rel="manifest" href="/favicon/manifest.json">
    <link rel="mask-icon" href="/favicon/safari-pinned-tab.svg" color="#5bbad5">
    <meta name="theme-color" content="#ffffff">

    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="webjars/font-awesome/4.7.0/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="/css/login.css" />
</head>

<body>
<script type="text/javascript">
    var contextPath = window.location.origin + '${contextPath}';
    var language = '${language}';
</script>
<div class="container">
    <img src="/img/logo1.png"/>
    <div class="row">
        <div class="col-md-6 col-md-offset-3" ng-app="LoginApp" ng-cloak ng-controller="LoginCtrl">
                 <#if error??>
                    <div class="notif alert alert-danger" ng-if="!success" translate>Unknown user or incorrect password.</div>
                 </#if>
            <div class="notif alert alert-danger" ng-if="error" ng-click="clearError()">{{error | translate}}</div>
            <div class="notif alert alert-success" ng-if="success" ng-click="clearSuccess()">{{success | translate}}</div>
            <div class="panel panel-login">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-6" ng-click="registerTab = false">
                            <a href="#" ng-class="{'active': !registerTab  || registerTab === false}" translate>Join game</a>
                        </div>
                        <div class="col-xs-6" ng-click="registerTab = true">
                            <a href="#" ng-class="{'active': registerTab === true}" translate>Create a game</a>
                        </div>
                    </div>
                    <hr>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form name="loginForm" role="form" action="/login" method="post" ng-if="!registerTab">
                                <div class="form-group">
                                    <input type="text" name="username" tabindex="1" class="form-control" placeholder="{{'Username' | translate}}" required="required">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" tabindex="2" class="form-control" placeholder="{{'Password' | translate}}" required="required">
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="login-submit" ng-disabled="loginForm.$invalid" id="login-submit" tabindex="4"
                                                   class="form-control btn btn-login" value="{{'Log In' | translate}}">
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <form name="registerForm" role="form" ng-if="registerTab" ng-controller="RegistrationCtrl">
                                <div class="form-group">
                                    <input type="text" name="player1" tabindex="1" class="form-control" ng-model="form.player1" ng-minlength="4"
                                           placeholder="{{'Enter player 1 name' | translate}}" required="required" ng-model-options="{ allowInvalid: true}">
                                    <user-check user="form.player1"></user-check>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="player2" tabindex="2" class="form-control" ng-model="form.player2" ng-minlength="4"
                                           placeholder="{{'Enter player 2 name' | translate}}" required="required" ng-model-options="{ allowInvalid: true}">
                                    <user-check user="form.player2"></user-check>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="dashboard" tabindex="3" class="form-control" ng-model="form.dashboard" ng-minlength="1"
                                           placeholder="{{'Enter dashboard name' | translate}}" required="required">
                                </div>
                                <div class="form-group">
                                    <input type="password" ng-model="form.password" name="password" tabindex="4"
                                           class="form-control" placeholder="{{'Password' | translate}}" ng-minlength="6" required="required">
                                    <div ng-if="form.password.length > 0 && form.password.length < 6" class="check-info"><i class="fa fa-info"></i> <span translate>Password should be at least 6 characters long.</span></div>
                                </div>
                                <div class="form-group">
                                    <input type="password" ng-model="form.confirmPassword" name="confirm-password" id="confirm-password"
                                           tabindex="5" class="form-control" placeholder="{{'Confirm Password' | translate}}" required="required">
                                    <div ng-if="form.confirmPassword.length > 0 && form.password !== form.confirmPassword " class="check-info"><i class="fa fa-info"></i> <span translate>Passwords do not match.</span></div>
                                </div>

                                <div ng-if="form.extraPlayers.length > 0">
                                    <div class="form-group" ng-repeat="player in form.extraPlayers track by $index">
                                        <input type="text" class="form-control" tabindex="$index + 6" ng-model="player.value" ng-minlength="4" placeholder="{{'Enter extra player name' | translate}}">
                                        <user-check user="player.value"></user-check>
                                    </div>
                                </div>
                                <div class="extra-adder" ng-if="form.extraPlayers.length < 4" ng-click="addPlayer()"><i class="fa fa-plus"></i> <span translate>Add an extra player.</span></div>

                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="button" ng-click="register()" ng-disabled="registerForm.$invalid || (form.password !== form.confirmPassword || form.player1 === form.player2)"
                                                   name="register-submit" id="register-submit" tabindex="5" class="form-control btn btn-register" value="{{'Register' | translate}}">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="webjars/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/angularjs/1.6.6/angular.min.js"></script>
<script type="text/javascript" src="webjars/angularjs/1.6.6/angular-resource.min.js"></script>
<script type="text/javascript" src="webjars/angular-gettext/2.2.1/dist/angular-gettext.min.js"></script>
<script type="text/javascript" src="/js/login/app.js"></script>
<script type="text/javascript" src="/js/login/services.js"></script>
<script type="text/javascript" src="/js/login/directives.js"></script>
<script type="text/javascript" src="/js/login/controllers.js"></script>
<script type="text/javascript" src="/js/login/translations.js"></script>
</body>
</html>