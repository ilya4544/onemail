<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Sending email</title>
</head>
<body>
<h1>Send an email</h1>
<form action="http://95.85.46.247:8080/shipntrip/sendMail" method="post" enctype="multipart/form-data">
    <strong>To:</strong><input type="text" name="to" />
    <br>
    <strong>From:</strong><input type="text" name="from" />
    <br>
    <strong>Your message:</strong><input type="text" name="data" />
    <br/>
    <strong>Your file:</strong><input type="file" name="file" />
    <br/>
    <input type="submit" />
</form>
</body>
</html>