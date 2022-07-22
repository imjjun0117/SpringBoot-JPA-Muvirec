
<%@include file="layout/header.jsp"%>
<!-- Header-->
<header class="bg-dark py-5">
	<div class="d-flex container px-4 px-lg-5 justify-content-center">
		<!--   <div class="text-center text-white">
                    <h1 class="display-4 fw-bolder">Shop in style</h1>
                    <p class="lead fw-normal text-white-50 mb-0">With this shop hompeage template</p>
                </div> -->
		<iframe width="640" height="360" 
		src="https://www.youtube.com/embed/qAsHVwl-MU4?rel=0&amp;autoplay=1&mute=1&amp;playlist=qAsHVwl-MU4&loop=1" frameborder="0"> 
		</iframe>
	</div>
</header>
<!-- Section-->
<section class="bg-dark py-5" >
	<div class="d-flex justify-content-center">
		<input type="text" class="form-control rounded-pill" placeholder="Search" style="width: 600px; height: 50px;" />
	</div>
	<div class="container px-4 px-lg-5 mt-5">
		<div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
			<c:forEach var="post" items="${postList }">
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

		</div>
	</div>
</section>
<jsp:include page="layout/footer.jsp" />
