<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Login</title>
<link href="/css/styles.css" rel="stylesheet" />
<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>

<!-- jQuery CDN -->
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
</head>
<header class="bg-dark py-3">
	<div class="container px-4 px-lg-5 my-0">
		<div class="text-center text-white">
			<h1 class="display-4 fw-bolder"><a href="/" style="text-decoration:none; color:#FFFFFF">Muvirec</a></h1>
		</div>
	</div>
</header>
<body class="bg-dark">
	<div id="layoutAuthentication">
		<div id="layoutAuthentication_content">
			<main>
				<div class="container">
					<div class="row justify-content-center">
						<div class="col-lg-5">
							<div class="card shadow-lg border-0 rounded-lg mt-5">
								<div class="card-header">
									<h3 class="text-center font-weight-light my-4">Join</h3>
								</div>
								<div class="card-body" style="margin-top: 20px;">
										<div class="form-floating mb-3">
											<input class="form-control" id="username" type="text" name="username" /> <label for="inputEmail">User Name &nbsp;<font id="dupCheck" size="2" ></font></label>
										</div>
										
										<input type="hidden" id="dupCheckId" value="1"/>
										<div class="form-floating mb-3">
											<input class="form-control" id="password" type="password" name="password" /> <label for="inputPassword">Password &nbsp;<font id="confirmPass" size="2"></font></label>
										</div>
										<input type="hidden" id="confirmPassHid"/>
										<div class="form-floating mb-3">
											<input class="form-control" id="confirmPassword" type="password" /> <label for="inputPassword">Confirm Password</label>
										</div>
										<div class="form-floating mb-3">
											<input class="form-control" id="email" type="email" name="email" /> <label for="inputPassword">email</label>
										</div>
									<!--<div class="form-check mb-3"></div>  -->
										<div class="justify-content-between mt-4 mb-0" style="text-align: center">
											<button class="btn btn-primary" id="btn-save" style="width: 90%;">가입완료</button> 
										</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</main>
		</div>
	</div>
<script src="/js/user.js"></script>
<jsp:include page="../layout/footer.jsp" />