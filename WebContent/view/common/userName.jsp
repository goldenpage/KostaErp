<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<style>
.profile {
	display: flex;
	justify-content: end;
	gap: 12px;
}

li {
	cursor: pointer;
}

li:hover{
	opacity:0.5
}



</style>
<script>
	function goBack() {
		history.back();
	}
</script>
</head>
<body>

	<ul class="profile">
		
		<li><span>${info.name}님</span></li>
		<li onclick = "location.href='${pageContext.request.contextPath}/controller?cmd=logoutAction'">로그아웃</li>
		<li onclick="location.href='controller?cmd=notificationUI'">알림</li>
		<li onclick="goBack()">뒤로가기</button>
	</ul>


</body>
</html>