<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kr">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
</head>
<style>
.signup-box {
	max-width: 500px;
	margin: 50px auto;
	background-color: #ffffff;
	padding: 30px 40px;
	border-radius: 10px;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
	box-sizing: border-box;
}

/* 각 입력 구역(div)을 감싸는 클래스 */
.form-group {
	margin-bottom: 20px;
}

/* 메인 타이틀 (회원가입) */
.main-title {
	text-align: center;
	margin-bottom: 30px;
}

/* =========================================
   2. 텍스트 및 기본 입력칸
   ========================================= */
/* 각 항목의 제목 */
.section-title {
	font-weight: bold;
	margin-bottom: 8px;
	font-size: 15px;
}

/* 기본 입력 박스 (이메일, 비밀번호 등) */
.inputbutton {
	width: 100%;
	padding: 12px;
	margin-top: 5px;
	border: 1px solid #ccc;
	border-radius: 5px;
	box-sizing: border-box;
}

/* =========================================
   3. 휴대폰 인증 영역
   ========================================= */
.phone-auth-group {
	display: flex;
	gap: 8px;
	margin-top: 5px;
}

.phone-input {
	flex: 1;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 5px;
	box-sizing: border-box;
}

.auth-send-btn {
	width: 100px;
	padding: 10px;
	background-color: #666666;
	color: white;
	border: none;
	border-radius: 5px;
	font-weight: bold;
	cursor: pointer;
	flex-shrink: 0;
	white-space: nowrap;
	transition: background-color 0.3s;
}

.auth-send-btn:hover {
	background-color: #555555;
}

/* =========================================
   4. 선택 및 체크 영역
   ========================================= */
/* 라디오 버튼, 셀렉트 박스 가로 정렬 */
.flex-row {
	display: flex;
	gap: 15px;
	align-items: center;
	margin-top: 5px;
}

/* 체크박스/라디오 버튼 글씨 클릭용 클래스 */
.click-label {
	cursor: pointer;
}

/* 드롭다운 박스 */
.store-select {
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 4px;
}

/* 체크박스 세로 정렬 */
.checkbox-group {
	display: flex;
	flex-direction: column;
	gap: 10px;
	margin-top: 5px;
}

/* =========================================
   5. 하단 버튼 영역
   ========================================= */
/* 버튼들을 묶어주는 그룹 */
.btn-group {
	margin-top: 30px;
}

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
</style>
<body>
	<form method="post"
		action="${pageContext.request.contextPath}/controller?cmd=addUserAction"
		onsubmit="return checkPassword()" class="signup-box">

		<h1 class="main-title">회원가입</h1>

		<div class="form-group">
			<div class="section-title">사업자번호</div>
			<input type="text" id="bId" name="bId" class="inputbutton" maxlength=10
				minlength=10 placeholder="사업자번호 입력해주세요" required>
		</div>

		<div class="form-group">
			<div class="section-title">상호명</div>
			<input type="text" name="storeName" class="inputbutton"
				placeholder="상호명 입력해주세요" required>
		</div>

		<div class="form-group">
			<div class="section-title">성명</div>
			<input type="text" name="name" class="inputbutton"
				placeholder="성명 입력해주세요" required>
		</div>

		<div class="form-group">
			<div class="section-title">이메일</div>
			<input name="email" type="email" class="inputbutton"
				placeholder="이메일 입력해주세요" required>
		</div>

		<div class="form-group">
			<div class="section-title">휴대폰인증</div>
			<div class="phone-auth-group">
				<input type="text" id="phone" name="phone" class="phone-input"
					placeholder="휴대폰 번호 입력 (숫자만)" required>
				<button type="button" id="sendBtn" class="auth-send-btn"
					onclick="sendPhoneCode()">인증발송</button>
			</div>
			<div id="phoneMessage">${phoneMessage}</div>
		</div>

		<div class="form-group">
			<input type="text" id="phoneCode" name="phoneCode"
				class="phone-input" placeholder="인증번호 입력">

			<button type="button" class="auth-send-btn"
				onclick="verifyPhoneCode()">확인</button>
		</div>

		<div class="form-group">
			<div class="section-title">비밀번호</div>
			<input name="pw" type="password" class="inputbutton"
				placeholder="비밀번호 입력해주세요" required>
		</div>

		<div class="form-group">
			<div class="section-title">비밀번호 확인</div>
			<input name="pwConfirm" type="password" class="inputbutton"
				placeholder="비밀번호 한 번 더 입력해주세요" required>
			<div>${errorMessage}</div>
		</div>

		<div class="form-group">
			<div class="section-title">사업자 유형 선택</div>
			<div class="flex-row">
				<label class="click-label"> 
				<input type="radio" name="businessType" value="간이과세자"> 간이과세자
				</label> <label class="click-label"> 
				<input type="radio" name="businessType" value="일반과세자"> 일반과세자
				</label>
			</div>
		</div>

		<div class="form-group">
			<div class="section-title">업태 종류</div>
			<div class="flex-row">
				<select name="storeType" class="store-select">
					<option value="일반음식점">일반음식점</option>
					<option value="휴게음식점">휴게음식점</option>

				</select> <select name="storeCategory" class="store-select">
					<option value="한식">한식</option>
					<option value="중식">중식</option>
					<option value="일식">일식</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<div class="section-title">소상공인 증명</div>
			<div>
				<button type="button" onclick="checkBid()">인증하기</button>
				<div id="businessMessage">${businessMessage}</div>
			</div>
		</div>

		<div class="form-group">
			<div class="checkbox-group">
				<label class="click-label"> <input name="signAgree"
					type="checkbox" name="" required> 소득 확인을 위한 개인정보 동의 약관 필수
				</label> <label class="click-label"> <input name="agreementAgree"
					type="checkbox" name="" required>개인정보 수집 동의 필수
				</label> <label class="click-label"> <input name="marketingAgree"
					type="checkbox" name=""> 마케팅 정보 메일, SMS 수신 동의
				</label>
			</div>
		</div>

		<div class="btn-group">
			<button type="submit" class="submitbutton">회원가입 완료</button>
			<button type="button" class="submitbutton"
				onclick="location.href='${pageContext.request.contextPath}/controller?cmd=loginUIAction'">기존
				계정으로 로그인</button>
		</div>
	</form>
	<script>
		function checkPassword() {
			const
			pw = document.querySelector('input[name="pw"]').value;
			const
			pwConfirm = document.querySelector('input[name="pwConfirm"]').value;

			if (pw !== pwConfirm) {
				alert("비밀번호가 일치하지 않습니다.");
				return false;
			}

			return true;
		}

		function sendPhoneCode() {
			const
			phone = document.querySelector("#phone").value

			if (!phone) {
				alert("휴대폰 번호를 입력해주세요")
				return;
			}
			const xhr = new XMLHttpRequest();
			const url = '${pageContext.request.contextPath}/controller?cmd=phoneSendAction&phone='+ encodeURIComponent(phone);
			
			xhr.open('GET', url, true);
			xhr.onreadystatechange = function () {
		        if (xhr.readyState === 4) {
		            if (xhr.status === 200) {
		                document.querySelector("#phoneMessage").innerText = xhr.responseText;
		            } else {
		                alert("인증번호 발송 중 오류가 발생했습니다.");
		            }
		        }
		    };

		    xhr.send();

		}

		function verifyPhoneCode() {
			const
			phone = document.querySelector("#phone").value;
			const
			phoneCode = document.querySelector("#phoneCode").value;

			 const xhr = new XMLHttpRequest();
			    const url = '${pageContext.request.contextPath}/controller?cmd=phoneVerifyAction&phone='
			        + encodeURIComponent(phone)
			        + '&phoneCode='
			        + encodeURIComponent(phoneCode);

			    xhr.open("GET", url, true);

			    xhr.onreadystatechange = function () {
			        if (xhr.readyState === 4) {
			            if (xhr.status === 200) {
			                document.querySelector("#phoneMessage").innerText = xhr.responseText;
			            } else {
			                alert("인증번호 확인 중 오류가 발생했습니다.");
			            }
			        }
			    };

			    xhr.send();
		}

		function checkBid() {
		    const bId = document.querySelector("#bId").value;

		    if (!bId || bId.length !== 10) {
		        alert("사업자번호 10자리를 입력해주세요.");
		        return;
		    }

		    const xhr = new XMLHttpRequest();
		    const url = '${pageContext.request.contextPath}/controller?cmd=idCheckAction&bId='
		        + encodeURIComponent(bId);

		    xhr.open("GET", url, true);

		    xhr.onreadystatechange = function () {
		        if (xhr.readyState === 4) {
		            if (xhr.status === 200) {
		                document.querySelector("#businessMessage").innerText = xhr.responseText;
		            } else {
		                alert("사업자번호 인증 중 오류가 발생했습니다.");
		            }
		        }
		    };

		    xhr.send();
		}
	</script>
</body>

</html>
