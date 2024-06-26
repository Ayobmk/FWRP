<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Login Form </title>

<!-- Font Icon -->
<link rel="stylesheet"
	href="fonts/material-icon/css/material-design-iconic-font.min.css">

<!-- Main css -->
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status") %>">

	<div class="main">

		<!-- Sing in  Form -->
		<section class="sign-in">
			<div class="container">
				<div class="signin-content">
					<div class="signin-image">
						<a href="registration.jsp" class="signup-image-link">Create an account</a>
					</div>

					<div class="signin-form">
						<h2 class="form-title">Sign in</h2>
						
						<form method="post" action="LoginServlet" class="register-form"
							id="login-form">
							
							<div class="form-group">
								<label for="username"><i class="zmdi zmdi-account material-icons-name"></i></label> 
								<input type="text" name="username" id="username" placeholder="Your Name" required="required"/>
							</div>
							
							<div class="form-group">
								<label for="password"><i class="zmdi zmdi-lock"></i></label> 
								<input type="password" name="password" id="password" placeholder="Password" required="required"/>
							</div>
													
							<div class="form-group form-button">
								<input type="submit" name="signin" id="signin"
									class="form-submit" value="Log in" />
							</div>
							
						</form>
					</div>
				</div>
			</div>
		</section>

	</div>

	<!-- JS -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="js/main.js"></script>
	
	<!-- Show the alert for creating account successfully -->
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<link rel="stylesheet" href="alert/dist/sweetalert.css">	

	<script type="text/javascript">
	
		var status = document.getElementById("status").value;
		if(status == "failed"){
			swal("Sorry","Wrong Username or Password","failed");
		}
		
		var status = document.getElementById("status").value;
		if(status == "invalidEmail"){
			swal("Sorry","Please Enter Your Username","failed");
		}
		
		var status = document.getElementById("status").value;
		if(status == "invalidPassword"){
			swal("Sorry","Please Enter Your Password","failed");
		}
	</script>
</body>
</html>