<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>비밀번호 변경</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/pwUdate.css">
</head>
<body>

	<form method="post"
		action="${pageContext.request.contextPath}/controller?cmd=pwUpdateAction"
		class="change-box" onsubmit="return checkPw()">
		<h1 class="main-title">비밀번호 변경</h1>

		<p class="description">
			안전한 계정 사용을 위해 <br> 새로운 비밀번호를 설정해 주세요.
		</p>
		<div class="form-group">
			<div class="section-title">아이디</div>
			<input type="text" id="bId" name="bId" class="inputbutton"
				placeholder="아이디를 입력해주세요" required>
		</div>

		<div class="form-group">
			<div class="section-title">이름</div>
			<input type="text" id="name" name="name" class="inputbutton"
				placeholder="이름을 입력해주세요" required>
		</div>

		<div class="form-group">
			<div class="section-title">휴대폰 번호</div>
			<div class="phone-auth-group">
				<input type="text" id="phone" name="phone" class="phone-input"
					placeholder="휴대폰 번호를 입력해주세요" maxlength="11" required>
				<button type="button" class="auth-send-btn" onclick="sendPwPhoneCode()">인증발송</button>
			</div>
			<div id="phoneMessage" class="message"></div>
		</div>

		<div class="form-group">
			<div class="phone-auth-group">
				<input type="text" id="phoneCode" name="phoneCode" class="phone-input"
					placeholder="인증번호 입력">
				<button type="button" class="auth-send-btn" onclick="verifyPwPhoneCode()">확인</button>
			</div>
		</div>

		<div class="form-group">
			<div class="section-title">새 비밀번호</div>
			<input id="pw" name="pw" type="password" class="inputbutton"
				placeholder="새 비밀번호를 입력하세요" required>
			<div class="password-hint">※ 영문, 숫자, 특수문자 혼합 4~8자</div>
		</div>

		<div class="form-group">
			<div class="section-title">새 비밀번호 확인</div>
			<input  id="pwConfirm" name="pwConfirm" type="password" class="inputbutton"
				placeholder="새 비밀번호를 한 번 더 입력하세요" required>
		</div>

		<div class="error-message">${error}</div>
		<button type="submit" class="submitbutton">비밀번호 변경 완료</button>

	</form>

	<script>
		let pwPhoneVerified = false;

		function sendPwPhoneCode() {
			const bId = document.querySelector("#bId").value.trim();
			const name = document.querySelector("#name").value.trim();
			const phone = document.querySelector("#phone").value.trim();

			pwPhoneVerified = false;

			if (!bId || !name || !phone) {
				alert("아이디, 이름, 휴대폰 번호를 모두 입력해주세요.");
				return;
			}

			const xhr = new XMLHttpRequest();
			const url = '${pageContext.request.contextPath}/controller?cmd=pwPhoneSendAction';
			const params = 'bId=' + encodeURIComponent(bId)
				+ '&name=' + encodeURIComponent(name)
				+ '&phone=' + encodeURIComponent(phone);

			xhr.open("POST", url, true);
			xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			xhr.onreadystatechange = function () {
				if (xhr.readyState === 4) {
					if (xhr.status === 200) {
						document.querySelector("#phoneMessage").innerText = xhr.responseText;
					} else {
						alert("인증번호 발송 중 오류가 발생했습니다.");
					}
				}
			};

			xhr.send(params);
		}

		function verifyPwPhoneCode() {
			const phone = document.querySelector("#phone").value.trim();
			const phoneCode = document.querySelector("#phoneCode").value.trim();

			if (!phoneCode) {
				alert("인증번호를 입력해주세요.");
				return;
			}

			const xhr = new XMLHttpRequest();
			const url = '${pageContext.request.contextPath}/controller?cmd=pwPhoneVerifyAction';
			const params = 'phone=' + encodeURIComponent(phone)
				+ '&phoneCode=' + encodeURIComponent(phoneCode);

			xhr.open("POST", url, true);
			xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			xhr.onreadystatechange = function () {
				if (xhr.readyState === 4) {
					if (xhr.status === 200) {
						document.querySelector("#phoneMessage").innerText = xhr.responseText;
						pwPhoneVerified = xhr.responseText.indexOf("완료") > -1;
					} else {
						alert("인증번호 확인 중 오류가 발생했습니다.");
					}
				}
			};

			xhr.send(params);
		}

		function checkPw() {
			const pw = document.querySelector("#pw").value;
			const pwConfirm = document.querySelector("#pwConfirm").value;

			if (!pwPhoneVerified) {
				alert("휴대폰 인증을 먼저 완료해주세요.");
				return false;
			}

			if (pw !== pwConfirm) {
				alert("비밀번호가 일치하지 않습니다.");
				return false;
			}

			return true;
		}

		document.querySelector("#bId").addEventListener("input", function () {
			pwPhoneVerified = false;
		});
		document.querySelector("#name").addEventListener("input", function () {
			pwPhoneVerified = false;
		});
		document.querySelector("#phone").addEventListener("input", function () {
			pwPhoneVerified = false;
		});
	</script>
</body>
</html>
