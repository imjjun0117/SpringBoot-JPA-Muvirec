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
									<h3 class="text-center font-weight-light my-4">Login</h3>
								</div>
								<div class="card-body" style="margin-top: 20px;">
								<form action="/auth/loginProc" method="post" id="loginForm"><!--Spring Security 사용을 위해 submit으로 전송  -->
										<div class="form-floating mb-3">
											<input class="form-control" id="username-login" type="text" name="username" value="joony" /> <label for="inputEmail">User Name</label>
										</div>
										<div class="form-floating mb-3">
											<input class="form-control" id="password" type="password" name="password" value="1111" /> <label for="inputPassword">Password</label>
										</div>
										<!-- 	<div class="form-check mb-3"></div> -->
								</form>
										<div class="justify-content-between mt-4 mb-0" style="text-align: center">
											<button class="btn btn-primary" id="btn-login" style="width: 400px;">로그인</button> 
											<a class="btn btn-primary mt-2" style="width: 400px;">카카오 로그인</a> 
										</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</main>
		</div>
		<jsp:include page="../layout/footer.jsp" />
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
	<script src="/js/user.js"></script>
</body>
</html>