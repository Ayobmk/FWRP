<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Sign Up</title>

<!-- Font Icon -->
<link rel="stylesheet"
	href="fonts/material-icon/css/material-design-iconic-font.min.css">

<!-- Main css -->
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status") %>">
	<div class="main">

		<!-- Sign up form -->
		<section class="signup">
			<div class="container">
				<div class="signup-content">
					<div class="signup-form">
						<h2 class="form-title">Sign up</h2>
					
						<form method="post" action="RegistrationServlet" class="register-form"
							id="register-form">
							<div class="form-group">
								<label for="name"><i class="zmdi zmdi-account material-icons-name"></i></label> <input
									type="text" name="name" id="name" placeholder="Your Name" "/>
							</div>
							

							
							
							<div class="form-group">
								<label for="email"><i class="zmdi zmdi-email"></i></label> 
								<input type="email" name="email" id="email" placeholder="Your Email" />
							</div>
							
							<div class="form-group">
								<label for="pass"><i class="zmdi zmdi-lock"></i></label> 
								<input type="password" name="pass" id="pass" placeholder="Password" />
							</div>
							
							<div class="form-group">
								<label for="re-pass"><i class="zmdi zmdi-lock-outline"></i></label>
								<input type="password" name="re_pass" id="re_pass" placeholder="Repeat your password" />
							</div>
							
							<div class="form-group">
								<label for="contact"><i class="zmdi zmdi-lock-outline"></i></label>
								<input type="text" name="contact" id="contact" placeholder="Contact no" />
							</div>
							
							<!--  <div class="form-group">
								<label for="type"><i class="zmdi zmdi-account material-icons-name"></i></label> 
								<input type="text" name="type" id="type" placeholder="Your Type" required="required"/>
							</div>
							
								<div class="form-group">
								<label for="province"><i class="zmdi zmdi-account material-icons-name"></i></label> 
								<input type="text" name="province" id="province" placeholder="Your Province" required="required"/>
							</div>
							
							-->
							<!-- -------------------------------- -->
							<div class="form-group">
							<label for="type"><i class="zmdi zmdi-nature-people"></i></label>
								<select name="type" id="type"  >
									<option value="" disabled selected>Select Your Type</option>
									<option value="Customer">Customer</option>
	                				<option value="Retailer">Retailer</option>
	                				<option value="Charity">Charitable Organizations</option>
								</select>
							</div>
							
							<div class="form-group">
							<label for="province"><i class="zmdi zmdi-pin"></i></label>
								<select name="province" id="province" placeholder="Your Province" >
									<option value="" disabled selected>Select Your Province</option>
									<option value="ON">Ontario</option>
	                				<option value="QC">Quebec</option>
	                				<option value="AB">Alberta</option>
	                				<option value="MB">Manitoba</option>
	                				<option value="NS">Nova Scotia</option>
	                				<option value="NB">New Brunswick</option>
	                				<option value="BC">British Columbia</option>
	                				<option value="PE">Prince Edward Island</option>
	                				<option value="SK">Saskatchewan</option>
	                				<option value="NL">Newfoundland and Labrador</option>
	                				
								</select>
							</div>
							<!-- ----------------------------------- -->
							
							<div class="form-group form-button">
								<input type="submit" name="signup" id="signup" class="form-submit" value="Register" />
							</div>
						</form>
					</div>
					<div class="signup-image">
						<figure>
							<img src="images/signup-image.jpg" alt="sing up image">
						</figure>
						<a href="login.jsp" class="signup-image-link">I am already member</a>
					</div>
				</div>
			</div>
		</section>


	</div>
	<!-- JS -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="js/main.js"></script>
	
	<!-- Show the allert for creating account successfully -->
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<link rel="stylesheet" href="alert/dist/sweetalert.css">	

<script type="text/javascript">

	var status = document.getElementById("status").value;
	if(status == "success"){
		swal("Congrats","Account Created Successfully","success");
	}
	if(status == "invalidName"){
		swal("Congrats","Please Enter Your Name","error");
	}
	if(status == "invalidEmail"){
		swal("Congrats","Please Enter Your Email","error");
	}
	if(status == "invalidPassword"){
		swal("Congrats","Please Enter Your Password","error");
	}
	if(status == "invalidComfirmPassword"){
		swal("Congrats","Please Confirm Your Password","error");
	}
	if(status == "invalidMatchPassword"){
		swal("Congrats","Password Do Not Match","error");
	}
	if(status == "invalidPhoneNumber"){
		swal("Congrats","Please Enter Your Phone Number","error");
	}
	if(status == "invalidPhoneNumberLenght"){
		swal("Congrats","Please Enter 10 Digits","error");
	}
	if(status == "invalidType"){
		swal("Congrats","Please Choose Your Type","error");
	}
	if(status == "invalidProvince"){
		swal("Congrats","Please Choose Your Province","error");
	}
</script>

</body>
</html>