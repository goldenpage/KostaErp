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
}
</style>
<script>
function goBack(){
    history.back();
}
</script>
</head>
<body>

	<ul class="profile">
		<li><span>${info.name}님</span></li>
    <button onclick="location.href='controller?cmd=notificationUI'">알림</button>
        <button onclick="goBack()">뒤로가기</button>
	</ul>

</body>
</html>