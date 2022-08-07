/**
 * 
 */
let index ={
	init:function(){
		$("#search").on("keydown",(e)=>{
			if(e.key === "Enter"|| e.which == 32){
				if($("#search").val()==""){
					alert("검색어를 입력해주세요");
					$("#search").focus();
					return;
				}//end if
				this.search("0",$("#search").val());
				window.scrollTo({ left: 0, top: 650, behavior: "smooth" });
			}//end if
		});
	},//init
	search:function(pageNum,keyword){
	$.ajax({
		url: `/auth/api/posts?keyword=${keyword}&page=${pageNum}`,
		type: "GET",
		contentType: "application/json;charset=UTF-8",
		dataType: "JSON"
	}).done(function(json) {
		var html = "";
		var pagination = "";
		if (json.content == "") {
			html = "<font color='white'>검색항목이 존재하지 않습니다</font>";
		} else {
			json.content.forEach(function(post, idx) { // 받아온 데이터로 화면 출력
				html += "<div class='col mb-5'><div class='card h-100'><img class='card-img-top' src='https://img.youtube.com/vi/";
				html += post.videoId+"/mqdefault.jpg'/>";
				html += "<div class='card-body p-4'><div class='text-center'><h6 class='fw-bolder'>";
				html +=	"<a href='/posts/"+post.id+"' style='text-decoration: none; color: #000000;'>"+post.title+"</a></h6>";
				html +=	post.singer+"</div></div><div class='card-footer d-flex justify-content-center p-3 pt-0 border-top-0 bg-transparent'>";
				if(post.tag != null){
					html +=	"<font color='blue' size='2'>"+post.tag+"</font>";
				}//end if
				html+="</div></div></div>";
			});
			
			//페이지 번호 화면전환
			var startPage = Math.floor((json.pageable.pageNumber)/5)*5+1; // 보여줄 페이지 수 5개일 때 시작 페이지 
			//끝 페이지가 전체 페이지 수보다 클 경우 첫페이지+4 아니면 전체 페이지를 반환
			var endPage = (startPage+4 > json.totalPages)? json.totalPages : startPage+4; 
			if(startPage > 5){ // 시작 페이지가 5보다 클 경우 이전 버튼 생성
				pagination += "<li><a style='margin-right: 10px; text-decoration: none;' class='text-secondary page-item'";
				pagination += "onclick=\"index.search('"+(startPage-6)+"','"+keyword+"')\"> 이전 </a></li>"	
			}//end if
			for(var i=startPage; i < endPage+1; i++){
				if(json.number == (i-1)){ //현재 번호와 같을 경우 페이지 이동없고 밑줄 표시
				pagination += "<li><a style='margin-right: 10px;' class='text-secondary'>"+i+"</a></li>";
				}else{
				pagination += "<li><a style='margin-right: 10px;text-decoration: none;' class='text-secondary'"  ;
				pagination += "onclick=\"index.search('"+(i-1)+"','"+keyword+"')\" >"+i+"</a></li>";
				}//end else
			}//end form
			if(endPage < json.totalPages){//끝 페이지가 전체 페이지보다 작을 경우 다음 버튼 생성
				pagination += "<li><a style='margin-right: 10px; text-decoration: none;'class='text-secondary' onclick=\"index.search('"+(startPage+4)+"','"+keyword+"')\">"; 
				pagination += "다음 </a></li>";
			}//end if
		}
		$("#content-item").html(html);
		$("#pagination").html(pagination);
		
	}).fail(function(request, status, error) {
		alert("문제가 발생했습니다. 잠시 후 다시 시도해주세요");
	});
	}
	
}//index
index.init();