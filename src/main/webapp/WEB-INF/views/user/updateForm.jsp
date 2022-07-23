<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp" %>	
	<div class="bg-dark py-5">
		<div id="layoutAuthentication">
			<div id="layoutAuthentication_content">
				<main>
					<div class="container">
						<div class="row justify-content-center">
							<div class="col-lg-5">
								<div class="card shadow-lg border-0 rounded-lg mt-5">
									<div class="card-header">
										<h3 class="text-center font-weight-light my-4">User Edit</h3>
									</div>
									<div class="card-body" style="margin-top: 20px;">
										<div class="form-floating mb-3">
											<input type="hidden" id="hidUsername" value="${principal.user.username }"/>
											<input class="form-control" id="username" type="text" name="username" value="${principal.user.username }"/> <label for="inputUsername">User Name &nbsp;<font id="dupCheck" size="2"></font></label>
										</div>

										<input type="hidden" id="id" value="${principal.user.id }"/>
										<div class="form-floating mb-3">
											<input class="form-control" id="password" type="password" name="password" /> <label for="inputPassword">Password &nbsp;<font id="confirmPass" size="2"></font></label>
										</div>
										<div class="form-floating mb-3">
											<input class="form-control" id="confirmPassword" type="password" /> <label for="inputPassword">Confirm Password</label>
										</div>
										<div class="form-floating mb-3">
											<input class="form-control" id="email" type="email" name="email" value="${principal.user.email }" /> <label for="inputEmail">email</label>
										</div>
										<!--<div class="form-check mb-3"></div>  -->
										<div class="justify-content-between mt-4 mb-0" style="text-align: center">
											<button class="btn btn-success" id="btn-update" style="width: 400px;">수정완료</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</main>
			</div>
		</div>
	</div>
	<script src="/js/user.js"></script>
	<%@include file="../layout/footer.jsp" %>