<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>폐기품목 관리</title>

<style>
ul{list-style:none;margin:0;padding:0;}
body{margin:0;padding:0;}

.container{
    display:flex;
    min-height:100vh;
    border:3px solid #000;
}

.sideMenu{
    width:260px;
    border-right:1px solid #000;
    padding:20px;
}

.sideMenu ul{margin-bottom:25px;}
.sideMenu li{margin-bottom:8px;}

.main{
    flex:1;
    padding:20px;
}

.profile{
    display:flex;
    justify-content:flex-end;
    gap:10px;
    margin-bottom:20px;
}

.content_item{
    border:2px solid #2f49ff;
    padding:15px;
}

.category{
    display:flex;
    align-items:center;
    gap:10px;
    border:2px solid #000;
    padding:10px;
    margin-bottom:20px;
}

.list_container{
    width:100%;
    border-collapse:collapse;
    border:2px solid red;
}

.list_container th,
.list_container td{
    border-bottom:1px solid #ddd;
    padding:10px 8px;
}

.list_container th{
    background:#f7f7f7;
}

.center{text-align:center;}
.right{text-align:right;}

.actionCell{text-align:center;}

.page{
    margin-top:20px;
    display:flex;
    justify-content:center;
    gap:8px;
}

.page a{
    padding:6px 12px;
    border:1px solid #999;
    text-decoration:none;
    color:#000;
}

.page strong{
    padding:6px 12px;
    border:1px solid #000;
    background:#eee;
}

button{cursor:pointer;}
</style>
</head>

<body>

<c:set var="pageSize" value="5"/>
<c:set var="currentPage" value="${empty param.page ? 1 : param.page}" />
<c:set var="startRow" value="${(currentPage-1) * pageSize}" />
<c:set var="endRow" value="${startRow + pageSize}" />
<c:set var="totalCount" value="${fn:length(list)}" />
<c:set var="pageCount" value="${(totalCount + pageSize - 1) / pageSize}" />

<div class="container">

<section class="sideMenu">
    <ul>
        <li><button onclick="location.href='controller?cmd=foodMaterialInputUI'">식자재입력</button></li>
        <li><button onclick="location.href='controller?cmd=menuInputUI'">메뉴입력</button></li>
        <li><button onclick="location.href='controller?cmd=foodMaterialUI'">식자재조회</button></li>
        <li><button onclick="location.href='controller?cmd=menuDetailUI'">메뉴상세조회</button></li>
    </ul>

    <ul>
        <li><button onclick="location.href='controller?cmd=disposalUI'">폐기품목확인</button></li>
    </ul>

    <ul>
        <li><button onclick="location.href='controller?cmd=revenueStatisticsUI'">매출통계</button></li>
        <li><button onclick="location.href='controller?cmd=expendStatisticsUI'">지출통계</button></li>
        <li><button onclick="location.href='controller?cmd=disposalStatisticsUI'">폐기통계</button></li>
    </ul>
</section>

<div class="main">

    <ul class="profile">
        <li>김상혁님</li>
        <li><button onclick="location.href='controller?cmd=notificationUI'">알림</button></li>
    </ul>

    <h2>폐기품목 확인</h2>

    <div class="content_item">

        <table class="list_container">
            <thead>
                <tr>
                    <th>번호</th>
                    <th>식자재명</th>
                    <th>카테고리</th>
                    <th>총폐기용량(g)</th>
                    <th>총폐기가격(원)</th>
                    <th>폐기일</th>
                    <th>사유</th>
                    <th>확인</th>
                </tr>
            </thead>

            <tbody>

            <c:forEach var="d" items="${list}" varStatus="st">

                <c:if test="${st.index >= startRow && st.index < endRow}">
                    <tr>
                        <td class="center">
                            ${fn:substring(d.disposalId,3,6)}
                        </td>

                        <td class="center">
                            ${d.foodMaterialName}
                        </td>

                        <td class="center">
                            ${d.foodCategory}
                        </td>

                        <td class="right">
                            ${d.disposalCountAll}
                        </td>

                        <td class="right">
                            <fmt:formatNumber value="${d.disposalPrice}" />
                        </td>

                        <td class="center">
                            ${d.disposalDate}
                        </td>

                        <td class="actionCell">
                            <select>
                                <option selected>${d.reason}</option>
                                <option>변질</option>
                                <option>파손</option>
                                <option>유통기한지남</option>
                                <option>기타</option>
                            </select>
                        </td>

                        <td class="actionCell">
                            <button>확인</button>
                        </td>
                    </tr>
                </c:if>

            </c:forEach>

            </tbody>
        </table>

        <!-- 페이지 번호 -->
        <div class="page">

            <c:forEach begin="1" end="${pageCount}" var="p">

                <c:choose>
                    <c:when test="${p == currentPage}">
                        <strong>${p}</strong>
                    </c:when>

                    <c:otherwise>
                        <a href="controller?cmd=disposalUI&page=${p}">
                            ${p}
                        </a>
                    </c:otherwise>
                </c:choose>

            </c:forEach>

        </div>

    </div>

</div>

</div>

</body>
</html>