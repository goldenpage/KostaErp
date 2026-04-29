<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<style>
    ul {
            list-style: none;
        }


</style>
</head>
<body>
   
            <ul>
                <li>메뉴조회</li>
                <li><button type="button" onclick="location.href='controller?cmd=addFoodMaterialUI'">식자재입력</button></li>
                <li><button type="button" onclick="location.href='controller?cmd=addMenuUI'">메뉴입력</button></li>
                <li><a href="${pageContext.request.contextPath}/controller?cmd=foodMaterialUIAction">식자재조회</a></li>
                <li><a href="${pageContext.request.contextPath}/controller?cmd=menuUIAction">메뉴상세조회</a></li>
            </ul>
            <ul>
                <li>폐기관리</li>
                <li><button type="button" onclick="location.href='controller?cmd=disposalItemsUI'">폐기품목확인</button></li>
            </ul>
            <ul>
                <li>통계</li>
                <li><button type="button" onclick="location.href='controller?cmd=revenueStatisticsUIAction'">매출통계</button></li>
                <li><button type="button" onclick="location.href='controller?cmd=usedStatisticsUIAction'">지출통계</button></li>
                <li><button type="button" onclick="location.href='controller?cmd=disposalStatisticUIAction'">폐기통계</button></li>
            </ul>
     
</body>
</html>