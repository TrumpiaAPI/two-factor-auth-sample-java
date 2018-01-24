<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<!--
	two-factor auth-sample-java
 -->
<head>
<!-- Bootstrap framework -->
<script src="https://code.jquery.com/jquery-3.2.1.min.js"
	integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
	crossorigin="anonymous"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=Cp1252">

<title>Authentication Page</title>

</head>
<body>
	<div class="container">
		
		<center>
			<h2>Two-factor Authentication Page</h2>
			<p>Use the form below to verify your mobile number.</p>
		</center>
		
		<form class="form-horizontal" action = "request" method="post">
			<div class="form-group">
				<label for="mobileNumber" class="col-sm-2 control-label">Mobile
					Number</label><span class="error">
				</span>
				<div class="col-sm-10">
					<input type="text" name="mobileNumber" class="form-control"
						id="mobileNumber" pattern="^\d{10}$" placeholder="5554443333"
						required>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
				</div>
			</div>
			<div>
				<center>
					<b>Summary Terms & Conditions:</b> By clicking submit, you agree to
					receive a text message to authenticate the phone number provided.
					Message and data rates may apply. Text STOP to opt out. For help,
					Text HELP.
				</center>
			</div>
		</form>
	</div>
</body>
</html>
