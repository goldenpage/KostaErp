<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원가입</title>
</head>
<body>
	<h1>회원가입</h1>
	<form method="post" action="controller?cmd=addUserAction">
		<div>
			<div>사업자번호</div>
			<input type="text" name="bId" placeholder="사업자번호">
		</div>
		<div>
			<div>상호명</div>
			<input type="text" name="storeName" placeholder="상호명">
		</div>
		<div>
			<div>성명</div>
			<input type="text" name="name" placeholder="성명">
		</div>
		<div>
			<div>이메일</div>
			<input type="email" name="email" placeholder="이메일">
		</div>
		<div>
			<div>휴대폰인증</div>
			<input type="text" name="phone" placeholder="휴대폰번호">
		</div>
		<div>
			<div>비밀번호</div>
			<input type="password" name="pw" placeholder="비밀번호">
		</div>
		<div>
			<div>비밀번호 확인</div>
			<input type="password" name="pw_" placeholder="비밀번호 재입력">
		</div>

		<div>
			<div>업태 종류</div>
			<select name="storeType">
			</select>
			<select name="storeCategory">
			</select>
		</div>
		<div>소상공인 증명 서류 제출</div>
		<input type="file" name="signFile">
		<div>
			<div>
				<input type="checkbox" name="signDate">
				<label for="agreeIncome">소득 확인을 위한 개인정보 동의 약관 필수</label>
			</div>
			<div>
				<input type="checkbox" name="agreementDate">
				<label for="agreementDate">개인정보 수집 동의 필수</label>
			</div>
			<div>
				<input type="checkbox" name="marketingDate">
				<label for="marketingDate">마케팅 정보 메일, SMS 수신 동의</label>
			</div>
		</div>
		<input type="submit" value="회원가입">
	</form>
	<button type="button" onclick="location.href='controller?cmd=loginUI'">취소</button>
</body>
</html>