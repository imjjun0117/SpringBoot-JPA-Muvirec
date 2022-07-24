<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp"%>
<header class="bg-dark py-5">
	<div class="container px-4 px-lg-5 my-0">
		<div class="text-center text-white">
			<h1 class="display-4 fw-bolder">
				<a href="/" style="text-decoration: none; color: #FFFFFF">My Posts</a>
			</h1>
		</div>
	</div>
</header>
<!-- Section-->
<section class="bg-dark py-5">
	<div class="container px-4 px-lg-5 mt-5">
		<div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
			<c:choose>
				<c:when test="${not empty posts }">
					<c:forEach var="post" items="${posts.content}">
						<div class="col mb-5">
							<div class="card h-100">
								<!-- Product image-->
								<img class="card-img-top" src="https://img.youtube.com/vi/${post.videoId}/mqdefault.jpg" alt="..." />
								<!-- Product details-->
								<div class="card-body p-4">
									<div class="text-center">
										<!-- Product name-->
										<h6 class="fw-bolder">
											<a href="/posts/${post.id}" style="text-decoration: none; color: #000000;">${post.title}</a>
										</h6>
										<!-- Product reviews-->
										<div class="d-flex justify-content-center small text-warning mb-1">
											<div class="bi-star-fill"></div>
											<div class="bi-star-fill"></div>
											<div class="bi-star-fill"></div>
											<div class="bi-star-fill"></div>
											<div class="bi-star-fill"></div>
										</div>
										<!-- Product price-->
										${post.singer }

									</div>
								</div>
								<!-- Product actions-->
								<div class="card-footer p-3 pt-0 border-top-0 bg-transparent">
									<font color="blue" size="2">#KanyeWest #RUNAWAY #Rigth Now</font>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<font color="white">포스트가 존재하지 않습니다 <a href="/posts/add-form">작성</a>
					</font>
				</c:otherwise>
			</c:choose>

		</div>
	</div>
</section>
<div class="bg-dark py-3">
	<div id="pageNavigation">
		<ul class="pagination justify-content-center">
			<c:if test="${startPage gt 5 }"><!-- 페이지 블록보다 사이즈가 클 경우 이전 버튼 출력 -->
				<li><a style="margin-right: 10px; text-decoration: none;" class="text-secondary page-item"
					href="/users/my-post?page=${startPage-6}"> 이전 </a></li>
					</c:if>
				<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
					<c:choose>
						<c:when test="${i eq posts.number+1}">
							<li><a style="margin-right: 10px;" class="text-secondary" href="#void"> <c:out value="${i}" />
							</a></li>
						</c:when>
						<c:otherwise>
							<li><a style="margin-right: 10px; text-decoration: none;" class="text-secondary" id="pNum" href="/users/my-post?page=${i-1}"> <c:out value="${i}" />
							</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${endPage lt posts.totalPages}">
					<li><a style="margin-right: 10px; text-decoration: none;" class="text-secondary" href="/users/my-post?page=${startPage+4}"> 다음 </a></li>
				</c:if>
		</ul>
	</div>
</div>
<%@include file="../layout/footer.jsp"%>
