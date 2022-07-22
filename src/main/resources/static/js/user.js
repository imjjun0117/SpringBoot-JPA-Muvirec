/**
 * 사용자 관련 자바스크립트
 */
let index = {

	init: function() {
		$("#username").on("keyup", () => {
			if ($("#username").val() != '') {
				this.dupCheck();
			} else {
				$("#dupCheck").html("");
			}
		});//keydown
		$("#btn-save").on("click", () => {
			if (this.isEnabled()) {
				this.save();
			}
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
			alert("아이디가 중복됩니다.");
			return false;
		}
		if ($("#confirmPassHid").val() == 1) {
			alert("비밀번호가 일치하지 않습니다.");
			return false;
		}
		if ($("#username").val() == '') {
			alert("이름을 입력해주세요");
			$("#username").focus();
			return false;
		}//end if
		if ($("#password").val() == '') {
			alert("비밀번호를 입력해주세요");
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
				location.href = "/auth/loginForm";
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
			$("#dupCheckId").val(resp.data);
			if (resp.data == 1) {
				$("#dupCheck").html("중복되는 이름입니다.");
				$("#dupCheck").attr('color', 'red');
			} else {
				$("#dupCheck").html("사용할 수 있는 이름입니다.");
				$("#dupCheck").attr('color', 'green');
			}
			console.log(resp.data);
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