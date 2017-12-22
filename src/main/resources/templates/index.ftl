<#assign user = principal />
<#assign contextPath = path />
<!doctype html>
<html>
<head>
    <title>Hello Angular</title>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" />
</head>

<body>
<script type="text/javascript">
    var contextPath = '${contextPath}';
</script>
<div class="container">
    <h1>Greeting</h1>
</div>
<script type="text/javascript" src="webjars/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/angularjs/1.6.6/angular.min.js"></script>
</body>
</html>