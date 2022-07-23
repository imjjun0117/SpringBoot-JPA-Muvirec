<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp"%>
<!-- Header-->
<header class="bg-dark py-5">
<c:if test="${principal.user.id eq post.user.id}">
<div class="d-flex justify-content-end pb-2">
	<div class="d-flex ml-5 w-25 px-3" >
		<button class="btn btn-outline-danger d-flex mx-3" id="btn-delete">삭제</button>
		<a class="btn btn-outline-secondary" href="/posts/${post.id }/update-form">수정</a>
	</div>
</div>
</c:if>
	<div class="d-flex container px-4 px-lg-5 justify-content-center">
		<iframe width="640" height="360" src="https://www.youtube.com/embed/${post.videoId }?
		rel=0&amp;autoplay=1&mute=1&amp;playlist=${post.videoId}&loop=1" frameborder="0"> </iframe>
	</div>
	<div class="d-flex justify-content-start">
	<div class="d-flex justify-content-end w-50 mt-2" style="padding-right:160px;">
	<font color="white" size="2">조회수 ${post.view}회 <fmt:formatDate pattern="yyyy-MM-dd" value="${post.createTime }"/></font>
	</div>
	</div>
	<br>
	<input type="hidden" id="postId" value="${post.id}"/>
	<input type="hidden" id="userId" value="${principal.user.id}"/>
	<div class="d-flex justify-content-center text-warning mb-1">
		<div class="bi-star-fill"></div>
		<div class="bi-star-fill"></div>
		<div class="bi-star-fill"></div>
		<div class="bi-star-fill"></div>
		<div class="bi-star-fill"></div>
	</div>
	<div class="container d-flex justify-content-center text-white">
		<h1>${post.title}</h1>
	</div>
</header>
<!-- Section-->
<section class="bg-dark py-2">
	<div class="container d-flex justify-content-center text-center text-white">
		가수이름 : ${post.singer } <br> 작성자 : ${post.user.username } <br /> <br> <br> ${post.description }<br />
	</div>
	<hr style="color: #FFFFFF;" />
</section>
<section class=" bg-dark">
	<div class="card bg-dark" style="border:0px;">
		<div class="card-body" >
			<!-- Comment form-->
			<div class="d-flex mb-4 justify-content-center">
				<textarea class="form-control" id="content" rows="2" placeholder="Comment" style="width:60%; border: 0 auto;"></textarea>
				<button id="btn-reply-save" class="btn btn-secondary">댓글 등록</button>
			</div>
			<div id="commentArea" class=" mb-4"  style="margin-left:350px;">
			<c:choose>
			<c:when test="${not empty post.replys }">
				<c:forEach var="reply" items="${post.replys }">
			<div class="d-flex py-2 text-white">
				<!-- Parent comment-->
				<div class="flex-shrink-0">
					<img class="rounded-circle" src="../image/Profile-PNG-Clipart.png" style="width:50px;">
				</div>
				<div class="ms-3">
					<div class="fw-bold">${reply.user.username } &nbsp;<font size="2">
					<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${reply.createDate}"/> 
					<c:if test="${principal.user.username eq reply.user.username }">
						<button class="btn btn-success btn-sm" onclick="replyDelete('${reply.id}')">삭제</button>
					</c:if>
					</font>
					</div>
					<div class="d-flex justify-content-between">
					${reply.content} 
					</div>
				</div>
			</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
			<font color='white'>댓글이 존재하지 않습니다.</font>
			</c:otherwise>
			</c:choose>
			</div>
		</div>
	</div>
</section>
<script src="../js/post.js"></script>
<%@include file="../layout/footer.jsp"%>
