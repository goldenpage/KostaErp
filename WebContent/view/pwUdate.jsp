<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>비밀번호 변경</title>
<style>
/* 1. 바디 초기화 (동일 톤앤매너) */
body {
	font-family: 'Malgun Gothic', dotum, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f8f9fa;
}

/* 2. 비밀번호 변경 박스 */
.change-box {
	max-width: 450px;
	margin: 100px auto;
	background-color: #ffffff;
	padding: 40px;
	border-radius: 10px;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
	box-sizing: border-box;
}

.main-title {
	text-align: center;
	margin-bottom: 20px;
	margin-top: 0;
}

/* 안내 문구 */
.description {
	text-align: center;
	font-size: 13px;
	color: #666;
	margin-bottom: 30px;
	line-height: 1.5;
}

/* 3. 입력 구역 간격 및 제목 */
.form-group {
	margin-bottom: 20px;
}

.section-title {
	font-weight: bold;
	margin-bottom: 8px;
	font-size: 15px;
}

/* 4. 입력칸 클래스 */
.inputbutton {
	width: 100%;
	padding: 12px;
	border: 1px solid #cccccc;
	border-radius: 5px;
	box-sizing: border-box;
	font-size: 14px;
}

.phone-auth-group {
	display: flex;
	gap: 8px;
}

.phone-input {
	flex: 1;
	padding: 12px;
	border: 1px solid #cccccc;
	border-radius: 5px;
	box-sizing: border-box;
	font-size: 14px;
}

.auth-send-btn {
	width: 100px;
	padding: 10px;
	background-color: #666666;
	color: #ffffff;
	border: none;
	border-radius: 5px;
	font-weight: bold;
	cursor: pointer;
	flex-shrink: 0;
	white-space: nowrap;
}

.auth-send-btn:hover {
	background-color: #555555;
}

.message {
	font-size: 13px;
	color: #333333;
	margin-top: 6px;
	min-height: 18px;
}

.error-message {
	font-size: 13px;
	color: #d00;
	margin-bottom: 12px;
}

/* 5. 변경 완료 버튼 클래스 */
.submitbutton {
	width: 100%;
	padding: 15px;
	margin-top: 10px;
	background-color: #333333;
	color: #ffffff;
	border: none;
	border-radius: 5px;
	font-size: 16px;
	font-weight: bold;
	cursor: pointer;
	transition: background-color 0.3s;
}

.submitbutton:hover {
	background-color: #000000;
}

/* 비밀번호 보안 안내 텍스트 */
.password-hint {
	font-size: 12px;
	color: #888;
	margin-top: 5px;
}
</style>
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
