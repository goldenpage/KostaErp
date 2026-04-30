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

.page{
    margin-top:20px;
    text-align:center;
}

.page a{
    margin:0 5px;
    text-decoration:none;
    padding:5px 10px;
    border:1px solid #ddd;
}
</style>

<script>
var originalRows = [];
window.onload = function() {
    originalRows = Array.from(document.querySelectorAll("tbody tr"));
}

function filterData(){

    var keyword = document.getElementById("searchInput").value.toLowerCase();
    var category = document.getElementById("categoryFilter").value;
    var reason = document.getElementById("reasonFilter").value;

    var rows = document.querySelectorAll("tbody tr");

    for(var i=0; i<rows.length; i++){
        rows[i].style.display = "";
    }

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

var sortDirectionMap = {};
function sortTable(n, btn) {
    var table = document.querySelector(".list_container tbody");
    var rows = Array.from(table.querySelectorAll("tr"));
    if (sortDirectionMap[n] === undefined) {
        sortDirectionMap[n] = true; 
    } else {
        sortDirectionMap[n] = !sortDirectionMap[n];
    }
    var asc = sortDirectionMap[n];
    rows.sort(function(a, b) {
        var A = a.cells[n].innerText.trim();
        var B = b.cells[n].innerText.trim();
        if (!isNaN(A) && !isNaN(B)) {
            A = Number(A.replace(/,/g, ""));
            B = Number(B.replace(/,/g, ""));
        }
        if (!isNaN(Date.parse(A)) && !isNaN(Date.parse(B))) {
            A = new Date(A);
            B = new Date(B);
        }
        if (A < B) return asc ? -1 : 1;
        if (A > B) return asc ? 1 : -1;
        return 0;
    });
    rows.forEach(function(row){
        table.appendChild(row);
    });
    updateIcons(btn, asc);
}

function updateIcons(activeBtn, asc) {
    var buttons = document.querySelectorAll(".list_container th button");
    for (var i = 0; i < buttons.length; i++) {
        buttons[i].innerText = "▼";
    }
    activeBtn.innerText = asc ? "▲" : "▼";
}
</script>
</head>

<body>

<div class="container">
    <section class="sideMenu">
          <jsp:include page="common/sideMenu.jsp" />
        </section>

        <div class="main">
            <div>
                <jsp:include page="common/userName.jsp" />
            </div>

        <h2>폐기품목 확인</h2>

        <div class="content_item">
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
                        <option>유통기한만료</option>
                        <option>기타</option>
                    </select>
                </li>

                <li>
                    <button onclick="resetFilter()">초기화</button>
                </li>
            </ul>

            <table class="list_container">
                <thead>
                    <tr>
                        <th>번호<button type="button" onclick="sortTable(0, this)">▼</button></th>
						<th>식자재명<button type="button" onclick="sortTable(1, this)">▼</button></th>
						<th>카테고리<button type="button" onclick="sortTable(2, this)">▼</button></th>
						<th>유형<button type="button" onclick="sortTable(3, this)">▼</button></th>
						<th>총폐기용량(g)<button type="button" onclick="sortTable(4, this)">▼</button></th>
						<th>총폐기가격(원)<button type="button" onclick="sortTable(5, this)">▼</button></th>
						<th>폐기일<button type="button" onclick="sortTable(6, this)">▼</button></th>
                        <th>사유</th>
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
                            <td class="center">
                                ${d.foodMaterialType}
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
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="page">

    <c:set var="page" value="${currentPage}" />

    <c:if test="${page > 1}">
        <a href="controller?cmd=disposalUI&page=${page-1}">이전</a>
    </c:if>

    <c:forEach begin="1" end="5" var="i">
        <a href="controller?cmd=disposalUI&page=${i}">${i}</a>
    </c:forEach>

    <a href="controller?cmd=disposalUI&page=${page+1}">다음</a>

</div>

        </div>

    </div>

</div>

</body>
</html>