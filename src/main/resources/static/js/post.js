/**
 * 사용자 관련 자바스크립트
 */
let index = {

	init: function() {
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
		})

	},//init	
	isEnabled: function() {
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
			videoId: this.extractVideoID($("#videoId").val())
		};
		$.ajax({
			url: "/posts",
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
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		$.ajax({
			url: "/users",
			data: JSON.stringify(data),
			type: "PUT",
			contentType: "application/json; charser=UTF-8",
			dataType: "JSON"
		}).done(function(resp) {
			if (resp.data == 1) {
				alert("회원수정이 완료되었습니다.");
				location.href = "/";
			} else {
				alert("회원수정을 실패하였습니다.");
			}
		}).fail(function(error) {
			alert("회원수정 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
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
			url: `/posts/${data.postId}/replys`,
			data: JSON.stringify(data),
			type: "POST",
			contentType: "application/json; charser=UTF-8",
			dataType: "JSON"
		}).done(function(resp) {
			if (resp.data == 1) {
				alert("댓글등록이 완료되었습니다.");
				//location.href=`/posts/${data.postId}`;
				getCommentList();
			} else {
				alert("댓글등록을 실패하였습니다.");
			}
		}).fail(function(error) {
			alert("댓글등록 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
		});
	}//replySave
}//index
function getCommentList() {
	//동영상을 끊기지 않고 댓글을 갱신하기 위해서 json 데이터를 받아 비동기 화면 전환을 구현
	let = postId = $("#postId").val();
	let userId = $("#userId").val();
	$.ajax({
		url: `/posts/${postId}/replys`,
		type: "GET",
		contentType: "application/json; charser=UTF-8",
		dataType: "JSON"
	}).done(function(json) {
		var html = "";
		if(json==""){
			html = "<font color='white'>댓글이 존재하지 않습니다.</font>";	
		}else{
			json.forEach(function(reply, idx) {
				html += "<div class='d-flex py-2 text-white'><div class='flex-shrink-0'>";
				html += "<img class='rounded-circle' src='../image/Profile-PNG-Clipart.png' style='width:50px;'>";
				html += "</div><div class='ms-3'>";
				html += "<div class='fw-bold'>" + reply.user.username + " &nbsp;<font size='2'>";
				html += reply.createDate;
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
}
function replyDelete(replyId) {
	$.ajax({
		url: `/replys/${replyId}`,
		type: "DELETE",
		contentType: "application/json; charser=UTF-8",
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
}
index.init();