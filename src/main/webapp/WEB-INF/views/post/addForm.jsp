<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp" %>	
<!-- <script src="https://unpkg.com/@yaireo/tagify"></script>
<script src="https://unpkg.com/@yaireo/tagify/dist/tagify.polyfills.min.js"></script>
<link href="https://unpkg.com/@yaireo/tagify/dist/tagify.css" rel="stylesheet" type="text/css" />
<script>
    const input = document.querySelector('input[name=basic]');
    let tagify = new Tagify(input); // initialize Tagify
    
    // 태그가 추가되면 이벤트 발생
    tagify.on('add', function() {
      console.log(tagify.value); // 입력된 태그 정보 객체
    })
</script> -->
	<div class="bg-dark py-5">
		<div id="layoutAuthentication">
			<div id="layoutAuthentication_content">
				<main>
					<div class="container">
						<div class="row justify-content-center">
							<div class="col-lg-5">
								<div class="card shadow-lg border-0 rounded-lg mt-5">
									<div class="card-header">
										<h3 class="text-center font-weight-light my-4">Post Write</h3>
									</div>
									<div class="card-body" style="margin-top: 20px;">
										<div class="form-floating mb-3">
											<input class="form-control" id="singer" type="text" name="singer" /> <label for="inputSinger">Singer</label>
										</div>
										<div class="form-floating mb-3">
											<input class="form-control" id="title" type="text" name="title" /> <label for="inputTitle">Title</label>
										</div>
										<input type="hidden" id="id"/>
										<div class="form-floating mb-3">
											<textarea class="form-control" id="description" name="description" rows="10" style="height:150px;"></textarea><label for="inputDescription">Description</label>
										</div>
									<!-- 	<div class="form-floating mb-3">
											<input class="form-control" name='basic' id="tag" type="text" /> <label for="inputTag">Tag</label>
										</div>  -->
										<div class="form-floating mb-3">
											<input class="form-control" id="videoId" type="text" name="videoId" /> <label for="inputUrl">URL</label>
										</div>
										<!--<div class="form-check mb-3"></div>  -->
										<div class="justify-content-between mt-4 mb-0" style="text-align: center">
											<button class="btn btn-primary" id="btn-save" style="width: 400px;">작성완료</button>
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
	<script src="/js/post.js"></script>
	<%@include file="../layout/footer.jsp" %>