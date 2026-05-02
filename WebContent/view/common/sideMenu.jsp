<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<style>
.sideMenu> ul {
	list-style: none;
}

.sideMenu>ul>li {
	cursor: pointer;
	font-size: 16px;
	font-weight: 500
}

.sideMenu>ul>li:hover {
	opacity: 0.5
}
</style>
</head>
<body>

	<div class="sideMenu">

		<ul>
			<li>식자재관리</li>
			<li onclick="location.href='controller?cmd=addFoodMaterialUIAction'">식자재입력</li>
			<li onclick="location.href='controller?cmd=addMenuUIAction'">메뉴입력</li>
			<li onclick="location.href='controller?cmd=foodMaterialUIAction'">식자재조회</li>
			<li onclick="location.href='controller?cmd=menuUIAction'">메뉴상세조회</li>
		</ul>
		<ul>
			<li>폐기관리</li>
			<li><a
				href="${pageContext.request.contextPath}/controller?cmd=disposalItemsUIAction">폐기품목확인</a></li>
		</ul>
		<ul>
			<li>통계</li>
			<li
				onclick="location.href='controller?cmd=revenueStatisticsUIAction'">매출통계</li>
			<li
				onclick="location.href='controller?cmd=usedStatisticsUIAction'">지출통계</li>
			<li
				onclick="location.href='controller?cmd=disposalStatisticUIAction'">폐기통계</li>
		</ul>
	</div>


</body>
</html>