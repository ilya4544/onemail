<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Sending email</title>
</head>
<body>
<h1>Send an email</h1>
<form action="http://95.85.46.247:8080/shipntrip/signuporg" method="post" enctype="multipart/form-data">
    <strong>Login:</strong><input type="text" name="login" />
    <br>
    <strong>Password:</strong><input type="text" name="pass" />
    <br>
    <strong>Description:</strong><input type="text" name="description" />
    <br/>
    <input type="submit" />
</form>
</body>
</html>