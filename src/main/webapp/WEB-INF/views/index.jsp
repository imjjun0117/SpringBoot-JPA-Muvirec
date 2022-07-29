<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="layout/header.jsp"%>
<style>
a:hover {
  cursor: pointer;
}
</style>
<!-- Header-->
<header class="bg-dark py-5">
	<div class="d-flex container px-4 px-lg-5 justify-content-center">
		<iframe width="640" height="360" 
		src="https://www.youtube.com/embed/${videoId}?rel=0&amp;autoplay=1&mute=1&amp;playlist=${videoId}&loop=1" frameborder="0"> 
		</iframe>
	</div>
</header>
<!-- Section-->
<section class="bg-dark py-5" >
	<div class="d-flex justify-content-center">
		<input id="search" type="text" class="form-control rounded-pill" placeholder="Search" style="width: 600px; height: 50px;" />
	</div>
	<div id="item" class="container px-4 px-lg-5 mt-5">
		<div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center" id="content-item">
			<c:forEach var="post" items="${posts.content }">
				<div class="col mb-5">
					<div class="card h-100">
						<!-- 썸네일-->
						<img class="card-img-top" src="https://img.youtube.com/vi/${post.videoId}/mqdefault.jpg" alt="..." />
						<div class="card-body p-4">
							<div class="text-center">
								<!-- 제목-->
								<h6 class="fw-bolder">
									<a href="/posts/${post.id}" style="text-decoration: none; color: #000000;"><c:out value="${post.title}"/></a>
								</h6>
								<!--가수-->
								<c:out value="${post.singer }"/> 
							</div>
						</div>
						<!-- 해시태그 -->
						 <div class="card-footer d-flex justify-content-center p-3 pt-0 border-top-0 bg-transparent">
							<font color="blue" size="2"><c:out value="${post.tag }"/></font>
						</div> 
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</section>
<div class="bg-dark py-3">
	<div id="pageNavigation">
		<ul class="pagination justify-content-center" id="pagination">
			<c:if test="${startPage gt 5 }"><!-- 페이지 블록보다 사이즈가 클 경우 이전 버튼 출력 -->
				<li><a style="margin-right: 10px; text-decoration: none;" class="text-secondary page-item"
					onclick ="index.search('${startPage-6}','')">> 이전 </a></li>
					</c:if>
				<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
					<c:choose>
						<c:when test="${i eq posts.number+1}">
							<li><a style="margin-right: 10px;" class="text-secondary"> <c:out value="${i}" />
							</a></li>
						</c:when>
						<c:otherwise>
							<li>
								<a style="margin-right: 10px; text-decoration: none;" class="text-secondary" onclick ="index.search('${i-1}','')"> 
									<c:out value="${i}" />
								</a>
							</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${endPage lt posts.totalPages}">
					<li>
						<a style="margin-right: 10px; text-decoration: none;" 
						class="text-secondary" onclick ="index.search('${startPage+4}','')"> 
							다음 
						</a>
					</li>
				</c:if>
		</ul>
	</div>
</div>
 <script src="/js/search.js"></script>
<jsp:include page="layout/footer.jsp" />
