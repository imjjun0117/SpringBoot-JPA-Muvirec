<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp"%>
<!-- Header-->
<link href="../css/star.css" rel="stylesheet" />
<header class="bg-dark py-5">
	<c:if test="${principal.user.id eq post.user.id or principal.user.role eq 'ADMIN'}">
		<div class="d-flex justify-content-end pb-2">
			<div class="d-flex ml-5 w-25 px-3">
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
		<div class="d-flex justify-content-end w-50 mt-2" style="padding-right: 160px;">
			<font color="white" size="2">조회수 ${post.view}회 <fmt:formatDate pattern="yyyy-MM-dd" value="${post.createTime }" /></font>
		</div>
	</div>
	<br> <input type="hidden" id="postId" value="${post.id}" /> <input type="hidden" id="userId" value="${principal.user.id}" />

	<div class="container d-flex justify-content-center text-white">
		<h1>${post.title}</h1>
	</div>
</header>
<!-- Section-->
<section class="bg-dark py-2 text-white">
	<div class="container d-flex justify-content-center text-center ">
		가수이름 : ${post.singer } <br> 작성자 : ${post.user.username } <br />
	</div>
	<div class="d-flex justify-content-center ">
		평점 :
		<c:choose>
		<c:when test="${avgRating eq 0.0 }">
			아직 등록된 평점이 없습니다!
		</c:when>
		<c:otherwise>
		<div class="star-ratings">
			<div class="star-ratings-fill space-x-2 text-lg" style="width:${avgRating*20-1.5}%">
				<span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
			</div>
			<div class="star-ratings-base space-x-2 text-lg">
				<span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
			</div>
		</div>
		(${avgRating})
		</c:otherwise>
		</c:choose>

	</div>
	<div class="d-flex justify-content-center">
	<br /> <br /> ${post.description }<br />
	</div>
</section>
<section class=" bg-dark">
	<ul class="nav nav-tabs d-flex justify-content-center w-100 pt-5" id="myTab" role="tablist">
		<li class="nav-item" role="presentation">
			<button class="nav-link active" id="reply-tab" data-bs-toggle="tab" data-bs-target="#reply-tab-pane" type="button" role="tab" aria-controls="reply-tab-pane" aria-selected="true">댓글</button>
		</li>
		<li class="nav-item" role="presentation">
			<button class="nav-link" id="rating-tab" data-bs-toggle="tab" data-bs-target="#rating-tab-pane" type="button" role="tab" aria-controls="rating-tab-pane" aria-selected="false">평점 주기</button>
		</li>
	</ul>
	<div class="tab-content" id="myTabContent">
		<div class="tab-pane fade show active" id="reply-tab-pane" role="tabpanel" aria-labelledby="home-tab" tabindex="0">
			<!-- Reply -->
			<div class="card bg-dark" style="border: 0px;">
				<div class="card-body">
					<!-- Comment form-->
					<div class="d-flex mb-4 justify-content-center">
						<textarea class="form-control" id="content" rows="2" placeholder="Comment" style="width: 60%; border: 0 auto;"></textarea>
						<button id="btn-reply-save" class="btn btn-secondary">댓글 등록</button>
					</div>
					<div id="commentArea" class=" mb-4" style="margin-left: 350px;">
						<c:choose>
							<c:when test="${not empty post.replys }">
								<c:forEach var="reply" items="${post.replys }">
									<div class="d-flex py-2 text-white">
										<!-- Parent comment-->
										<div class="flex-shrink-0">
											<img class="rounded-circle" src="../image/Profile-PNG-Clipart.png" style="width: 50px;">
										</div>
										<div class="ms-3">
											<div class="fw-bold">${reply.user.username }
												&nbsp;<font size="2"> <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${reply.createDate}" /> 
												<c:if test="${principal.user.username eq reply.user.username or principal.user.role eq 'ADMIN'}">
														<button class="btn btn-success btn-sm" onclick="replyDelete('${reply.id}')">삭제</button>
												</c:if>
												</font>
											</div>
											<div class="d-flex justify-content-between">${reply.content}</div>
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
			<!-- Reply -->
		</div>
		<div class="tab-pane fade" id="rating-tab-pane" role="tabpanel" aria-labelledby="rating-tab" tabindex="0">
			<c:choose>
				<c:when test="${not empty rating}">
					<div class="d-flex justify-content-center bg-dark mt-5">
						<div class="rating">
							<div class="text-bold" style="text-align: center">
								<font color="white" id="rating-title">내 평점</font>
							</div>
							<fieldset>
								<c:forEach var="i" begin="1" end="${rating.rating}">
									<label for="rate" class="my-rating">★</label>
								</c:forEach>
							</fieldset>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="d-flex justify-content-center bg-dark mt-5">
						<form class="rating" method="post" action="/api/posts/${post.id}/ratings">
							<div class="text-bold" style="text-align: center">
								<font color="white" id="rating-title">평점을 입력해주세요</font>
							</div>
							<fieldset>
								<input type="radio" name="rating" value="5" id="rate1"><label for="rate1">★</label> <input type="radio" name="rating" value="4" id="rate2"><label for="rate2">★</label> <input
									type="radio" name="rating" value="3" id="rate3"><label for="rate3">★</label> <input type="radio" name="rating" value="2" id="rate4"><label for="rate4">★</label> <input
									type="radio" name="rating" value="1" id="rate5"><label for="rate5">★</label>
							</fieldset>
						</form>
					</div>
					<div class="d-flex justify-content-center mt-2" id="btn-rating-div">
						<button class="btn btn-success" id="btn-rating">평점주기</button>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

</section>
<script src="../js/post.js"></script>

<%@include file="../layout/footer.jsp"%>
