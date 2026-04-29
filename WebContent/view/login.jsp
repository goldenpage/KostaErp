<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
    <h1>로그인</h1>

    <form method="post" action="${pageContext.request.contextPath}/controller?cmd=loginAction">
        <div>
            <div>사업자번호</div>
            <input type="text" name="bId" placeholder="사업자번호 입력">
        </div>

        <div>
            <div>비밀번호</div>
            <input type="password" name="pw" placeholder="비밀번호 입력">
        </div>

        <input type="submit" value="로그인">
    </form>

    <form method="post" action="${pageContext.request.contextPath}/controller?cmd=addUserUIAction">
        <input type="submit" value="회원가입">
    </form>

    <form method="post" action="${pageContext.request.contextPath}/controller?cmd=searchPw">
        <input type="submit" value="비밀번호찾기">
    </form>
</body>
</html>