<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN">
<html>
<head>
<!-- Bootstrap framework -->
<script src="https://code.jquery.com/jquery-3.2.1.min.js"
	integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
	crossorigin="anonymous"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=Cp1252">

<title>Verification Page</title>

</head>
<body>
	<div class="container">
		<center>
			<h2>Two-factor Verification Page</h2>
			<p>Please enter the verification code.</p>
		</center>
		<form class="form-horizontal" method="post" action="verify" />
		<div class="form-group">
			<label for="verifyCode" class="col-sm-2 control-label">Verification
				Code</label><span class="error"></span>
			<div class="col-sm-10">
				<input type="text" name="verifyCode" class="form-control"
					id="verifyCode" required>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
			</div>
		</div>
		<div>
			<center>
				<b>Summary Terms & Conditions:</b> Message and data rates may apply.
				Text STOP to opt out. For help, Text HELP.
			</center>
		</div>
		</form>
	</div>
</body>