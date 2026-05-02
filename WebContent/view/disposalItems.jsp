<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- [AJAX 요청 분기 처리] --%>
<c:if test="${param.ajax eq 'true'}">
    <table id="ajaxTableData">
        <tbody>
            <c:forEach var="d" items="${list}">
                <tr>
                    <td class="center">${fn:substring(d.disposalId, 3, 6)}</td>
                    <td class="center">${d.foodMaterialName}</td>
                    <td class="center">${d.foodCategory}</td>
                    <td class="center">${d.foodMaterialType}</td>
                    <td class="right">${d.disposalCountAll}</td>
                    <td class="right"><fmt:formatNumber value="${d.disposalPrice}" /></td>
                    <td class="center">${d.disposalDate}</td>
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

    <div id="ajaxPagingData">
        <c:if test="${currentPage > 1}">
            <a href="javascript:void(0);" onclick="loadData(${currentPage - 1})">이전</a>
        </c:if>
        <c:forEach var="i" begin="1" end="${totalPages}">
            <a href="javascript:void(0);" onclick="loadData(${i})"
                class="${i == currentPage ? 'active' : ''}">${i}</a>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <a href="javascript:void(0);" onclick="loadData(${currentPage + 1})">다음</a>
        </c:if>
    </div>

    <% if("true".equals(request.getParameter("ajax"))) return; %>
</c:if>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>폐기품목 관리</title>
<style>
    ul { list-style: none; margin: 0; padding: 0; }
    body { margin: 0; padding: 0; }
    .container { display: flex; min-height: 100vh; border: 3px solid #000; }
    .sideMenu { width: 260px; border-right: 1px solid #000; padding: 20px; }
    .main { flex: 1; padding: 20px; }
    .content_item { border: 2px solid #2f49ff; padding: 15px; }
    .category { display: flex; align-items: center; gap: 10px; border: 2px solid #000; padding: 10px; margin-bottom: 20px; }
    .list_container { width: 100%; border-collapse: collapse; border: 2px solid red; }
    .list_container th, .list_container td { border-bottom: 1px solid #ddd; padding: 10px 8px; }
    .list_container th { background: #f7f7f7; }
    .center { text-align: center; }
    .right { text-align: right; }
    .page { margin-top: 20px; text-align: center; }
    .page a { margin: 0 5px; text-decoration: none; padding: 5px 10px; border: 1px solid #ddd; cursor: pointer; }
    .page a.active { background-color: #2f49ff; color: white; border-color: #2f49ff; }
</style>
<script>
function loadData(page) {
    const category = document.getElementById("categoryFilter").value;
    const reason = document.getElementById("reasonFilter").value;
    const p = page || 1;

    const url = "controller?cmd=disposalUIAction&ajax=true&category=" 
                + encodeURIComponent(category) + "&reason=" + encodeURIComponent(reason) + "&page=" + p;

    const xhr = new XMLHttpRequest();
    xhr.open("GET", url, true);
    xhr.withCredentials = true; 
    
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const tempDiv = document.createElement("div");
            tempDiv.innerHTML = xhr.responseText;

            // 1. 테이블 본문 교체 
            const newRows = tempDiv.querySelector("#ajaxTableData tbody");
            if(newRows) {
                document.querySelector(".list_container tbody").innerHTML = newRows.innerHTML;
            }

            // 2. 페이징 데이터 교체
            const newPaging = tempDiv.querySelector("#ajaxPagingData");
            if(newPaging) {
                document.querySelector(".page").innerHTML = newPaging.innerHTML;
            }
        }
    };
    xhr.send();
}

// 정렬 로직
var sortDirectionMap = {};
function sortTable(n, btn) {
    var table = document.querySelector(".list_container tbody");
    var rows = Array.from(table.querySelectorAll("tr"));
    sortDirectionMap[n] = !sortDirectionMap[n];
    var asc = sortDirectionMap[n];

    rows.sort(function(a, b) {
        var A = a.cells[n].innerText.trim();
        var B = b.cells[n].innerText.trim();
        if (!isNaN(A.replace(/,/g, "")) && !isNaN(B.replace(/,/g, ""))) {
            A = Number(A.replace(/,/g, ""));
            B = Number(B.replace(/,/g, ""));
        }
        return A < B ? (asc ? -1 : 1) : (A > B ? (asc ? 1 : -1) : 0);
    });
    rows.forEach(function(row){ table.appendChild(row); });
    updateIcons(btn, asc);
}

function updateIcons(activeBtn, asc) {
    var buttons = document.querySelectorAll(".list_container th button");
    buttons.forEach(function(b) {
        b.innerText = "▼";
    });
    activeBtn.innerText = asc ? "▲" : "▼";
}

function resetFilter(){
    document.getElementById("categoryFilter").value = "전체";
    document.getElementById("reasonFilter").value = "전체";
    loadData(1);
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
                    <li>카테고리</li>
                    <li>
                        <select id="categoryFilter" onchange="loadData(1)">
                            <option>전체</option>
                            <option>채소</option>
                            <option>정육</option>
                            <option>가공식품</option>
                            <option>유제품</option>
                            <option>양념</option>
                        </select>
                    </li>
                    <li>사유</li>
                    <li>
                        <select id="reasonFilter" onchange="loadData(1)">
                            <option>전체</option>
                            <option>변질</option>
                            <option>파손</option>
                            <option>유통기한만료</option>
                            <option>기타</option>
                        </select>
                    </li>
                    <li><button onclick="resetFilter()">초기화</button></li>
                </ul>

                <table class="list_container">
                    <thead>
                        <tr>
                            <th>번호 <button type="button" onclick="sortTable(0, this)">▼</button></th>
                            <th>식자재명 <button type="button" onclick="sortTable(1, this)">▼</button></th>
                            <th>카테고리 <button type="button" onclick="sortTable(2, this)">▼</button></th>
                            <th>유형 <button type="button" onclick="sortTable(3, this)">▼</button></th>
                            <th>총폐기용량(g) <button type="button" onclick="sortTable(4, this)">▼</button></th>
                            <th>총폐기가격(원) <button type="button" onclick="sortTable(5, this)">▼</button></th>
                            <th>폐기일 <button type="button" onclick="sortTable(6, this)">▼</button></th>
                            <th>사유</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="d" items="${list}">
                            <tr>
                                <td class="center">${fn:substring(d.disposalId,3,6)}</td>
                                <td class="center">${d.foodMaterialName}</td>
                                <td class="center">${d.foodCategory}</td>
                                <td class="center">${d.foodMaterialType}</td>
                                <td class="right">${d.disposalCountAll}</td>
                                <td class="right"><fmt:formatNumber value="${d.disposalPrice}" /></td>
                                <td class="center">${d.disposalDate}</td>
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
                    <c:if test="${currentPage > 1}">
                        <a href="javascript:void(0);" onclick="loadData(${currentPage - 1})">이전</a>
                    </c:if>
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <a href="javascript:void(0);" onclick="loadData(${i})"
                            class="${i == currentPage ? 'active' : ''}">${i}</a>
                    </c:forEach>
                    <c:if test="${currentPage < totalPages}">
                        <a href="javascript:void(0);" onclick="loadData(${currentPage + 1})">다음</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</body>
</html>