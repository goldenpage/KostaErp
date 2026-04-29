<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<title>폐기품목 관리</title>
<style>
ul { list-style: none; }

.container {
    display: flex;
    gap: 30px;
    border: 5px solid;
    height: 100vh;
}

.sideMenu {
    height: 100vh;
    border: 1px solid;
    width: 300px;
}

.main {
    border: 1px solid;
    width: 100%;
}

.profile {
    display: flex;
    justify-content: end;
}

.content_item {
    border: 3px solid blue;
    height: calc(100vh - 150px);
    overflow: hidden;
}

.content_item .category {
    display: flex;
    width: 75%;
    gap: 16px;
    align-items: center;
    border: 3px solid;
}

.content_item .list_container {
    width: 100%;
    max-height: 100%;
    overflow-y: auto;
    border: 3px solid red;
    font-size: 14px;
}

.content_item table {
    width: 100%;
    border-spacing: 20px;
}

.page {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 16px;
}

.center {
  text-align: center;
}

.center select,
.center button {
  display: inline-block;
}
</style>
<script>
//필터
function filterData() {
    var keyword = document.getElementById("searchInput").value.toLowerCase();
    var category = document.getElementById("categoryFilter").value;
    var reasonFilter = document.getElementById("reasonFilter").value;
    var rows = document.querySelectorAll(".list_container tbody tr");
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        var name = row.cells[1].innerText.toLowerCase();
        var cat = row.cells[2].innerText;
        var reason = row.querySelector("select").value;
        var show = true;
        if (keyword && name.indexOf(keyword) === -1) show = false;
        if (category !== "전체" && cat !== category) show = false;
        if (reasonFilter !== "전체" && reason !== reasonFilter) show = false;
        row.style.display = show ? "" : "none";
    }
}

//필터링 초기화
function resetFilter() {
    document.getElementById("searchInput").value = "";
    document.getElementById("categoryFilter").value = "전체";
    document.getElementById("reasonFilter").value = "전체";
    var rows = document.querySelectorAll(".list_container tbody tr");
    for (var i = 0; i < rows.length; i++) {
        rows[i].style.display = "";
    }
}

//알림 이동
function goNotification() {
    location.href = "controller?cmd=notificationUI";
}

//확인 버튼
function confirmDisposal(button) {
    var row = button.parentNode.parentNode;
    var data = {
        name: row.cells[1].innerText,
        category: row.cells[2].innerText,
        totalAmount: row.cells[4].innerText,
        reason: row.querySelector("select").value,
        date: row.cells[10].innerText
    };
    var list = JSON.parse(sessionStorage.getItem("noticeList")) || [];
    list.push(data);
    sessionStorage.setItem("noticeList", JSON.stringify(list));
    row.remove();
}
</script>
</head>
<body>
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
            <ul class="category">
                <li>
                    <input type="text" id="searchInput" onkeyup="filterData()" placeholder="검색">
                </li>
                <li>카테고리</li>
                <select id="categoryFilter" onchange="filterData()">
                    <option>전체</option>
                    <option>채소</option>
                    <option>정육</option>
                    <option>가공품</option>
                </select>
                <li>사유</li>
                <select id="reasonFilter" onchange="filterData()">
                    <option>전체</option>
                    <option>변질</option>
                    <option>파손</option>
                    <option>유통기한지남</option>
                </select>
                <button onclick="resetFilter()">초기화</button>
            </ul>
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
                        <td style="text-align:center;">${fn:substring(d.disposalId, 3,6)}</td>
                        <td style="text-align:center;">${d.foodMaterialName}</td>
                        <td style="text-align:center;">${d.foodCategory}</td>
                        <td style="text-align:right;">${d.disposalCountAll}</td>
                        <td style="text-align:right;"><fmt:formatNumber value="${d.disposalPrice}" /></td>
                        <td style="text-align:center;">${d.disposalDate}</td>
                        <td class="center">
                            <select>
                                <option selected>${d.reason}</option>
                                <option>변질</option>
                                <option>파손</option>
                                <option>유통기한지남</option>
                                <option>기타</option>
                            </select>
                        </td>
                        <td class="center">
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