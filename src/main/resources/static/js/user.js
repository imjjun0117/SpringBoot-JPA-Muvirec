/**
 * 사용자 관련 자바스크립트
 */
let index = {

	init: function() {
		$("#username").on("keyup", () => {
			var nameCheck = /^(?=.*[a-zA-Z])(?=.*[0-9]).{5,15}$/;
			if (!nameCheck.test($("#username").val())) {
				$("#dupCheck").html("이름은 영문+숫자 5~15자 입력입니다.");
				$("#dupCheck").attr('color', 'red');
				$("#dupCheckId").val("1");
			} else if ($("#username").val() != '') {
				this.dupCheck();
			} else {
				$("#dupCheck").html("");
			}
		});//keydown
		$("#btn-save").on("click", () => {
			if (this.isEnabled()) {
				if (confirm("회원가입을 하시겠습니까?")) {
					this.save();
				}
			}
		});//click
		$("#btn-delete").on("click", () => {
			if (confirm("정말로 삭제하시겠습니까?")) {
				this.delete();
			}//end if
		});//click
		$("#btn-login").on("click", () => {
			//로그인 시 유효성 검사
			if ($("#username-login").val() == '') {
				alert("이름을 입력해주세요");
				$("#username-login").focus();
				return;
			}//end if
			if ($("#password").val() == '') {
				alert("비밀번호를 입력해주세요");
				$("#password").focus();
				return;
			}//end if
			$("#loginForm").submit();
		});//click
		$("#btn-update").on("click", () => {
			if (this.isEnabled()) {
				this.update();
			}//end if
		});//click
		$("#confirmPassword").on("blur", () => {
			this.confirmPass();
		})

	},//init	
	isEnabled: function() {

		if ($("#dupCheckId").val() == 1) {
			alert("아이디를 확인해주세요");
			return false;
		}
		if ($("#confirmPassHid").val() == 1) {
			alert("비밀번호가 일치하지 않습니다.");
			return false;
		}
		//비밀번호 영문자+숫자+특수조합(8~25자리 입력) 정규식
		var pwdCheck = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
	
		
		if (!pwdCheck.test($("#password").val())) {
			alert("비밀번호는 영문자+숫자+특수문자 조합으로 8~25자리 사용해야 합니다.");
			pwd.focus();
			return false;
		};
	
		if ($("#password").val().length > 50) {
			alert("비밀번호 글자수 50글자를 초과했습니다.");
			$("#password").focus();
			return false;
		}//end if
		if ($("#confirmPassword").val() == '') {
			alert("비밀번호를 확인해주세요");
			$("#confirmPassword").focus();
			return false;
		}//end if
		if ($("#email").val() == '') {
			alert("이메일을 입력해주세요");
			$("#email").focus();
			return false;
		}//end if
		if ($("#email").val().length > 40) {
			alert("이메일 글자수 40글자를 초과했습니다.");
			$("#email").focus();
			return false;
		}//end if
		var text = $("#email").val();
		var regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
		if (!regEmail.test(text)) {
			alert('입력된 값은 이메일이 아닙니다.');
			return false;
		}
		return true;
	},//가입, 수정 유효성 검사
	save: function() {
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		$.ajax({
			url: "/auth/joinProc",
			data: JSON.stringify(data),
			type: "POST",
			contentType: "application/json; charser=UTF-8",
			dataType: "JSON"
		}).done(function(resp) {
			if (resp.data == 1) {
				alert("회원가입이 완료되었습니다.");
				location.href = "/auth/login-form";
			} else {
				alert("회원가입을 실패하였습니다.");
			}
		}).fail(function(error) {
			alert("회원가입 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
		});
	},//save

	dupCheck: function() {
		let username = $("#username").val();
		$.ajax({
			url: "/auth/joinProc/dup",
			data: username,
			type: "POST",
			contentType: "application/json; charser=UTF-8",
			dataType: "JSON"
		}).done(function(resp) {
			var checkNum = resp.data;
			if ($("#hidUsername").val() == $("#username").val()) {
				$("#dupCheck").html("");
				checkNum = 0;
			} else if (resp.data == 1) {
				$("#dupCheck").html("중복되는 이름입니다.");
				$("#dupCheck").attr('color', 'red');
			} else {
				$("#dupCheck").html("사용할 수 있는 이름입니다.");
				$("#dupCheck").attr('color', 'green');
			}
			$("#dupCheckId").val(checkNum);

		}).fail(function(error) {
			$("#dupCheck").html("오류가 발생했습니다");
			$("#dupCheck").attr('color', 'red');
		});
	},//dupChecl

	update: function() {
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		$.ajax({
			url: "/api/users/",
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

	delete: function() {
		let data = {
			id: $("#id").val(),
		};
		$.ajax({
			url: "/api/users/",
			data: JSON.stringify(data),
			type: "DELETE",
			contentType: "application/json; charser=UTF-8",
			dataType: "JSON"
		}).done(function(resp) {
			if (resp.data == 1) {
				alert("회원삭제가 완료되었습니다.");
				location.href = "/";
			} else {
				alert("회원삭제를 실패하였습니다.");
			}
		}).fail(function(error) {
			alert("회원삭제 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
		});
	},//update

	confirmPass: function() {
		if ($("#password").val() == $("#confirmPassword").val()) {
			$("#confirmPass").html("비밀번호가 일치합니다.");
			$("#confirmPass").attr('color', 'green');
			$("#confirmPassHid").val(0);
		} else {
			$("#confirmPass").html("비밀번호가 일치하지 않습니다.");
			$("#confirmPass").attr('color', 'red');
			$("#confirmPassHid").val(1);
		}//end else

	}//confirmPass
}//index
index.init();