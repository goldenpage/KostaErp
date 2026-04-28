<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
</head>

<body>
	<h1>로그인</h1>
	<form method="post" action="controller?cmd=loginAction">
		<div>
			<div>사업자번호</div>
			<input type="text" placeholder="사업자번호 입력">
		</div>
		<div>
			<div>비밀번호</div>
			<input type="password" placeholder="비밀번호 입력">
		</div>
		<input type="submit" value="로그인">
	</form>

	<form method="post" action="controller?cmd=addUserUI">
		<input type="submit" value="회원가입">
	</form>

	<form method="post" action="controller?cmd=searchPwUI">
		<input type="submit" value="비밀번호찾기">
	</form>
</body>
</html>