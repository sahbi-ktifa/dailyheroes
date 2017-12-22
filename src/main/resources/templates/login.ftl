<#assign contextPath = path />
<!doctype html>
<html>
<head>
    <title>ToDo RPG - The game</title>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="/resources/css/login.css" />
</head>

<body>
<script type="text/javascript">
    var contextPath = window.location.origin + '${contextPath}';
</script>
<div class="container">
    <div class="container">
        <h1>ToDo RPG</h1>
        <div class="row">
            <div class="col-md-6 col-md-offset-3" ng-app="LoginApp" ng-controller="LoginCtrl">
                 <#if error??>
                    <div class="notif alert alert-danger">Nom d'utilisateur inconnu, mot de passe incorrect ou l'utilisateur n'a pas été validé.</div>
                 </#if>
                <div class="notif alert alert-danger" ng-if="error" ng-click="clearError()">{{error}}</div>
                <div class="notif alert alert-success" ng-if="success" ng-click="clearSuccess()">{{success}}</div>
                <div class="panel panel-login">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-6" ng-click="registerTab = false">
                                <a href="#" ng-class="{'active': !registerTab  || registerTab === false}">Join game</a>
                            </div>
                            <div class="col-xs-6" ng-click="registerTab = true">
                                <a href="#" ng-class="{'active': registerTab === true}">Create a game</a>
                            </div>
                        </div>
                        <hr>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <form name="loginForm" role="form" action="/login" method="post" ng-if="!registerTab">
                                    <div class="form-group">
                                        <input type="text" name="username" tabindex="1" class="form-control" placeholder="Username" required="required">
                                    </div>
                                    <div class="form-group">
                                        <input type="password" name="password" tabindex="2" class="form-control" placeholder="Password" required="required">
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-sm-6 col-sm-offset-3">
                                                <input type="submit" name="login-submit" ng-disabled="loginForm.$invalid" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Log In">
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <form name="registerForm" role="form" ng-if="registerTab" ng-controller="RegistrationCtrl">
                                    <div class="form-group">
                                        <input type="text" name="player1" tabindex="1" class="form-control" ng-model="form.player1" ng-minlength="4" placeholder="Enter player 1 name" required="required">
                                    </div>
                                    <div class="form-group">
                                        <input type="text" name="player2" tabindex="2" class="form-control" ng-model="form.player2" ng-minlength="4" placeholder="Enter player 2 name" required="required">
                                    </div>
                                    <div class="form-group">
                                        <input type="password" ng-model="form.password" name="password" tabindex="3" class="form-control" placeholder="Password" ng-minlength="6" required="required">
                                    </div>
                                    <div class="form-group">
                                        <input type="password" ng-model="form.confirmPassword" name="confirm-password" id="confirm-password" tabindex="4" class="form-control" placeholder="Confirm Password" required="required">
                                    </div>

                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-sm-6 col-sm-offset-3">
                                                <input type="button" ng-click="register()" ng-disabled="registerForm.$invalid || (form.password !== form.confirmPassword || form.player1 === form.player2)"
                                                       name="register-submit" id="register-submit" tabindex="5" class="form-control btn btn-register" value="Register Now">
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
</div>
<script type="text/javascript" src="webjars/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/angularjs/1.6.6/angular.min.js"></script>
<script type="text/javascript" src="webjars/angularjs/1.6.6/angular-resource.min.js"></script>
<script type="text/javascript" src="/resources/js/login/app.js"></script>
<script type="text/javascript" src="/resources/js/login/services.js"></script>
<script type="text/javascript" src="/resources/js/login/controllers.js"></script>
</body>
</html>