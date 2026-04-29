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
ul{
    list-style:none;
    margin:0;
    padding:0;
}

body{
    margin:0;
    padding:0;
}

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

.sideMenu ul{
    margin-bottom:25px;
}

.sideMenu li{
    margin-bottom:8px;
}

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

.center{
    text-align:center;
}

.right{
    text-align:right;
}

/* select + button 중앙정렬 */
.actionCell{
    text-align:center;
}

.actionCell select,
.actionCell button{
    display:inline-block;
    vertical-align:middle;
}

button{
    cursor:pointer;
}
</style>

<script>
function filterData(){
    var keyword = document.getElementById("searchInput").value.toLowerCase();
    var category = document.getElementById("categoryFilter").value;
    var reason = document.getElementById("reasonFilter").value;

    var rows = document.querySelectorAll("tbody tr");

    for(var i=0; i<rows.length; i++){
        var row = rows[i];

        var name = row.cells[1].innerText.toLowerCase();
        var cat = row.cells[2].innerText;
        var rsn = row.querySelector("select").value;

        var show = true;

        if(keyword && name.indexOf(keyword) === -1) show = false;
        if(category !== "전체" && cat !== category) show = false;
        if(reason !== "전체" && rsn !== reason) show = false;

        row.style.display = show ? "" : "none";
    }
}

function resetFilter(){
    document.getElementById("searchInput").value = "";
    document.getElementById("categoryFilter").value = "전체";
    document.getElementById("reasonFilter").value = "전체";
    filterData();
}

function confirmDisposal(btn){
    var row = btn.parentNode.parentNode;
    row.remove();
}
</script>
</head>

<body>

<div class="container">

    <!-- 사이드 메뉴 -->
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

    <!-- 메인 -->
    <div class="main">

        <ul class="profile">
            <li>김상혁님</li>
            <li><button onclick="location.href='controller?cmd=notificationUI'">알림</button></li>
        </ul>

        <h2>폐기품목 확인</h2>

        <div class="content_item">

            <!-- 검색 -->
            <ul class="category">
                <li>
                    <input type="text" id="searchInput" onkeyup="filterData()" placeholder="검색">
                </li>

                <li>카테고리</li>
                <li>
                    <select id="categoryFilter" onchange="filterData()">
                        <option>전체</option>
                        <option>채소</option>
                        <option>정육</option>
                        <option>가공품</option>
                        <option>유제품</option>
                        <option>양념</option>
                    </select>
                </li>

                <li>사유</li>
                <li>
                    <select id="reasonFilter" onchange="filterData()">
                        <option>전체</option>
                        <option>변질</option>
                        <option>파손</option>
                        <option>유통기한지남</option>
                        <option>기타</option>
                    </select>
                </li>

                <li>
                    <button onclick="resetFilter()">초기화</button>
                </li>
            </ul>

            <!-- 목록 -->
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
                    <c:forEach var="d" items="${list}">
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
                                <button onclick="confirmDisposal(this)">확인</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>

    </div>

</div>

</body>
</html>