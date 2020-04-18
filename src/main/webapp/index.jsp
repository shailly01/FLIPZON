<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>FLIPZON</title>
		<link
			href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
			rel="stylesheet"
			integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
			crossorigin="anonymous"
		/>

		<script
			src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
			integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
			crossorigin="anonymous"
		></script>
	</head>

	<body class="">
		<div
			class="row justify-content-md-center"
			style="margin-top: 50px; padding: 3em;"
		>
			<form action="user/login" method="post">
				<h2 class="col-sm-12 mb-3">Login form</h2>
				<div class="col-sm-12 text-center mb-3">
					<input
						name="username"
						size="30"
						required="required"
						placeholder="Username"
					/>
				</div>

				<div class="col-sm-12 text-center mb-3">
					<input
						type="password"
						name="password"
						size="30"
						required="required"
						placeholder="Password"
					/>
				</div>
				<div class="row justify-content-md-center container">
					<div class="col-sm-6">
						<button type="submit" value="Login" class="btn btn-success">
							Login
						</button>
					</div>

					<div class="col-sm-6">
                                            
						<a href="user/register.htm">
							<button type="button" class="btn btn-info">Sign Up</button>
					
						</a>
					</div>
				</div>
			</form>
		</div>
	</body>
</html>
