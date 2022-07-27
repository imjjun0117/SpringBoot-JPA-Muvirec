/**
 * 사용자 관련 자바스크립트
 */
let index = {
	
	init: function() {
		$("#btn-rating").on("click", ()=>{
			if(confirm("한번 평점을 주시면 수정할 수 없습니다. 평점등록 하시겠습니까?")){
				if(!$("input[name=rating]:checked").val()){
					alert("평점을 입력해주세요!");
					return;
				}//end if
				this.ratingSave();
			}//end if
		});//click
		$("#btn-save").on("click", () => {
			if (this.isEnabled()) {
				this.save();
			}//end if 
		});//click
		$("#btn-update").on("click", () => {
			if (this.isEnabled()) {
				this.update();
			}//end if
		});//click
		$("#confirmPassword").on("blur", () => {
			this.confirmPass();
		});//blur
		$("#btn-reply-save").on("click", () => {
			this.replySave();
		});//click
		$("#btn-delete").on("click", () => {
			if (confirm("포스트를 삭제하시겠습니까?")) {
				this.delete();
			}
		});//click

	},//init	
	isEnabled: function() {
		var hashTag = $("#hashTag").val();
		if(hashTag != ""){
			if(hashTag.indexOf('#')==-1 || hashTag.length > 300 || hashTag.indexOf(" ")!=-1){
				alert("해시태그는 '#'을 포함시키고 공백없이 300자 밑으로 작성하셔야 합니다.")
				return false;
			}//end if
		}//end if
		if ($("#singer").val() == '') {
			alert("가수를 입력해주세요");
			$("#singer").focus();
			return false;
		}//end if
		if ($("#title").val() == '') {
			alert("제목을 입력해주세요");
			$("#title").focus();
			return false;
		}//end if
		if ($("#description").val() == '') {
			alert("곡 소개를 확인해주세요");
			$("#description").focus();
			return false;
		}//end if
		if ($("#videoId").val() == '') {
			alert("URL을 입력해주세요");
			$("#videoId").focus();
			return false;
		}//end if
		return true;
	},//가입, 수정 유효성 검사
	save: function() {
		let data = {
			singer: $("#singer").val(),
			title: $("#title").val(),
			description: $("#description").val(),
			videoId: this.extractVideoID($("#videoId").val()),
			tag:$("#hashTag").val()
		};
		$.ajax({
			url: "/api/posts",
			data: JSON.stringify(data),
			type: "POST",
			contentType: "application/json; charser=UTF-8",
			dataType: "JSON"
		}).done(function(resp) {
			if (resp.data == 1) {
				alert("포스팅이 완료되었습니다.");
				location.href = "/";
			} else {
				alert("포스팅을 실패하였습니다.");
			}
		}).fail(function(error) {
			alert("포스팅 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
		});
	},//save
	update: function() {
		let id= $("#postId").val();
		let data = {
			singer: $("#singer").val(),
			title: $("#title").val(),
			description: $("#description").val(),
			videoId: this.extractVideoID($("#videoId").val()),
			tag:$("#hashTag").val()
		};
		$.ajax({
			url: `/api/posts/${id}`,
			data: JSON.stringify(data),
			type: "PUT",
			contentType: "application/json; charser=UTF-8",
			dataType: "JSON"
		}).done(function(resp) {
			if (resp.data == 1) {
				alert("포스트 수정이 완료되었습니다.");
				location.href = `/posts/${id}`;
			} else {
				alert("포스트 수정을 실패하였습니다.");
			}
		}).fail(function(error) {
			alert("포스트 수정 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
		});
	},//update
	extractVideoID: function(url) {
		//정규식을 통해 유효성과 ID 추출
		var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
		var match = url.match(regExp);
		if (match && match[7].length == 11) {
			return match[7];
		} else {
			alert("유효하지 않은 링크입니다.");
		}//end else
	},//extractVideoID

	replySave: function() {
		let data = {
			userId: $("#userId").val(),
			content: $("#content").val(),
			postId: $("#postId").val()
		};
		$.ajax({
			url: `/api/posts/${data.postId}/replys`,
			data: JSON.stringify(data),
			type: "POST",
			contentType: "application/json; charser=UTF-8",
			dataType: "JSON"
		}).done(function(resp) {
			if (resp.data == 1) {
				alert("댓글등록이 완료되었습니다.");
				getCommentList();
			} else {
				alert("댓글등록을 실패하였습니다.");
			}
		}).fail(function(error) {
			alert("댓글등록 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
		});
	},//replySave
	
	ratingSave: function() {
		let data = {
			userId: $("#userId").val(),
			rating: $("input[name=rating]:checked").val(),
			postId: $("#postId").val()
		};
		$.ajax({
			url: `/api/posts/${data.postId}/ratings`,
			data: JSON.stringify(data),
			type: "POST",
			contentType: "application/json; charser=UTF-8",
			dataType: "JSON"
		}).done(function(resp) {
			if (resp.data == 1) {
				alert("평점등록이 완료되었습니다.");
				$("input[name=rating]").attr("disabled",true);
				$("#btn-rating-div").html("");
				$("#rating-title").html("내 평점");
			} else {
				alert("평점등록을 실패하였습니다.");
			}
		}).fail(function(error) {
			alert("평점등록 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
		});
	},//replySave

	delete: function() {
		let postId = $("#postId").val();
		$.ajax({
			url: `/api/posts/${postId}`,
			type: "DELETE",
			contentType: "application/json; charser=UTF-8",
			dataType: "JSON"
		}).done(function(resp) {
			if (resp.data == 1) {
				alert("포스트 삭제가 완료되었습니다. 메인 페이지로 이동합니다.");
				location.href="/";
			} else {
				alert("포스트 삭제를 실패하였습니다.");
			}
		}).fail(function(error) {
			alert("삭제 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
		});
	}

}//index
function getCommentList() {
	//동영상을 끊기지 않고 댓글을 갱신하기 위해서 json 데이터를 받아 비동기 화면 전환을 구현
	let postId = $("#postId").val();
	let userId = $("#userId").val();
	$.ajax({
		url: `/api/posts/${postId}/replys`,
		type: "GET",
		contentType: "application/json; charser=UTF-8",
		dataType: "JSON"
	}).done(function(json) {
		var html = "";
		if (json == "") {
			html = "<font color='white'>댓글이 존재하지 않습니다.</font>";
		} else {
			json.forEach(function(reply, idx) { // 받아온 데이터로 화면 출력
				html += "<div class='d-flex py-2 text-white'><div class='flex-shrink-0'>";
				html += "<img class='rounded-circle' src='../image/Profile-PNG-Clipart.png' style='width:50px;'>";
				html += "</div><div class='ms-3'>";
				html += "<div class='fw-bold'>" + reply.user.username + " &nbsp;<font size='2'>";
				html += dateFormat(new Date(reply.createDate));
				if (userId == reply.user.id) {
					html += " <button class='btn btn-success btn-sm' onclick='replyDelete(" + reply.id + ")'>삭제</button>";
				}//end if
				html += "</c:if></font></div>" + reply.content + "</div>"
				html += "</div>";
			});
		}
		$("#commentArea").html(html);
		$("#content").val("");
	}).fail(function(error) {
		alert(" 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
	});
}//getCommentList
function replyDelete(replyId) {
	if (!confirm("댓글을 삭제하시겠습니까?")) {
		return;
	}
	$.ajax({
		url: `/api/replys/${replyId}`,
		type: "DELETE",
		contentType: "application/json;charset=UTF-8",
		dataType: "JSON"
	}).done(function(resp) {
		if (resp.data == 1) {
			alert("댓글삭제가 완료되었습니다.");
			//location.href=`/posts/${data.postId}`;
			getCommentList();
		} else {
			alert("댓글삭제를 실패하였습니다.");
		}
	}).fail(function(error) {
		alert("댓글삭제 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
	});
}//replyDelete
function dateFormat(date) {
	//자바스크립트 날짜 포맷을 변경하기 위한 함수 선언
        let month = date.getMonth() + 1;
        let day = date.getDate();
        let hour = date.getHours();
        let minute = date.getMinutes();

        month = month >= 10 ? month : '0' + month;
        day = day >= 10 ? day : '0' + day;
        hour = hour >= 10 ? hour : '0' + hour;
        minute = minute >= 10 ? minute : '0' + minute;

        return date.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute;
}//dateFormat

// tag 안에 있는 값을 array type 으로 만들어서 넘긴다.
function marginTag() {
	return Object.values(tag).filter(function(word) {
		return word !== "";
	});
}//marginTag
// 입력한 값을 태그로 생성한다.
function addTag(value) {
	tag[counter] = value;
	counter++; // del-btn 의 고유 id 가 된다.
}//addTag
index.init();
