<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<!-- 시큐리티 로그인 세션 값 설정 -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<html lang="en">
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Muvirec-Music Video Recommendation</title>
<!-- Favicon-->
<link rel="icon" type="/image/x-icon" href="/assets/favicon.ico" />
<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="/css/styles.css" rel="stylesheet" />
<!-- jQuery CDN -->
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
</head>
<body>
	<!-- Navigation-->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container px-4 px-lg-5">
			<a class="navbar-brand" href="/"><i class="bi bi-headphones"></i> Muvirec</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
				<c:if test="${not empty principal}">
					<li class="nav-item"><a class="nav-link" href="/post/addForm">Write</a></li>
					<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">My Page</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="/user/updateForm">User Edit</a></li>
							<li><a class="dropdown-item" href="#">My Post</a></li>
						</ul>
					</li>
				</c:if>
				</ul>
				<ul class="navbar-nav">
					<c:choose>
						<c:when test="${not empty principal}">
						<!-- /logout uri를 통해 스프링 시큐리티가 자동으로 로그아웃 처리를 해줌 -->
							<li class="nav-item"><a class="btn btn-outline-dark" style="width: 100px;" href="/logout">Logout</a></li>
						</c:when>
						<c:otherwise>
							<li class="nav-item"><a class="btn btn-outline-primary" style="width: 100px; margin-right: 10px;" href="/auth/loginForm"> Login <!-- <span class="badge bg-dark text-white ms-1 rounded-pill">0</span> -->
							</a></li>
							<li class="nav-item"><a class="btn btn-outline-dark" style="width: 100px;" href="/auth/joinForm">Join</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>