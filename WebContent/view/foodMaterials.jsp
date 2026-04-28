<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>식자재 확인</title>

    <style>
        ul {
            list-style: none;
        }

        .container {
            display: flex;
            gap: 30px;
            border: 5px solid;
            height: 100vh;
            min-width: 1000px;
        }

        .sideMenu {
            height: 100vh;
            border: 1px solid;
            width: 300px;
            min-width: 300px;
            flex-shrink: 0;
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
            height: 100%;
            align-items: center;
        }

        .category {
            display: flex;
            width: 75%;
            gap: 16px;
            align-items: center;
            border: 3px solid;
        }

        .list_container {
            width: 100%;
            border: 3px solid red;
            font-size: 14px;
            line-height: 14px;
        }

        table {
            border-spacing: 24px;
        }

        .page {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 16px;
            width: 100%;
            font-size: 18px;
            line-height: 18px;
        }
    </style>
</head>

<body>
    <div class="container">
        <section class="sideMenu">
            <ul>
                <li>메뉴조회</li>
                <li><button type="button" onclick="location.href='controller?cmd=foodMaterialInputUI'">식자재입력</button></li>
                <li><button type="button" onclick="location.href='controller?cmd=menuInputUI'">메뉴입력</button></li>
                <li><button type="button" onclick="location.href='controller?cmd=foodMaterialUI'">식자재조회</button></li>
                <li><button type="button" onclick="location.href='controller?cmd=menuDetailUI'">메뉴상세조회</button></li>
            </ul>
            <ul>
                <li>폐기관리</li>
                <li><button type="button" onclick="location.href='controller?cmd=disposalUI'">폐기품목확인</button></li>
            </ul>
            <ul>
                <li>통계</li>
                <li><button type="button" onclick="location.href='controller?cmd=revenueStatisticsUI'">매출통계</button></li>
                <li><button type="button" onclick="location.href='controller?cmd=expendStatisticsUI'">지출통계</button></li>
                <li><button type="button" onclick="location.href='controller?cmd=disposalStatisticsUI'">폐기통계</button></li>
            </ul>
        </section>

        <div class="main">
            <div>
                <ul class="profile">
                    <li><span>${loginUser.name}님</span></li>
                    <li><span>알림</span></li>
                </ul>
            </div>
            <h1>식자재 확인</h1>
            <div class="content_item">
                <div>
                    <ul class="category">
                        <li>
                            <input type="text" id="searchName" placeholder="식자재명 검색">
                            <button onclick="searchFood()">검색하기</button>
                        </li>
                        <li>전체</li>
                        <select id="categorySelect">
                            <option value="">전체</option>
                            <option value="채소">채소</option>
                            <option value="정육">정육</option>
                            <option value="유제품">유제품</option>
                            <option value="가공품">가공품</option>
                            <option value="음료/주류">음료/주류</option>
                            <option value="양념">양념</option>
                        </select>
                        <li style="cursor:pointer" onclick="searchFood()">적용하기</li>
                        <button onclick="resetFood()">초기화</button>
                        <button type="button" onclick="location.href='controller?cmd=foodMaterialUIAction&sortType=idDesc&page=1'">번호순</button>
                        <button type="button" onclick="location.href='controller?cmd=foodMaterialUIAction&sortType=expAsc&page=1'">유통기한순</button>
                    </ul>
                </div>

                <div>
                    <table class="list_container">
                        <thead>
                            <tr class="list_item">
                                <th>번호</th>
                                <th>식자재명</th>
                                <th>카테고리</th>
                                <th>전체수량</th>
                                <th>전체용량(단위:g)</th>
                                <th>매입가격(단위:원)</th>
                                <th>구입처</th>
                                <th>매입날짜</th>
                                <th>유통기한</th>
                                <th>품목유형</th>
                                <th>폐기수정</th>
                                <th>
                                    <input type="checkbox" id="selectAll" onclick="toggleSelectAll(this)">전체선택
                                </th>
                                <th style="cursor:pointer" onclick="deleteSelected()">삭제하기</th>
                            </tr>
                        </thead>
                        <tbody id="tableBody">
                            <c:forEach var="vo" items="${foodList}" varStatus="status">
                                <tr>
                                    <td>${(currentPage - 1) * 5 + status.count}</td>
                                    <td>${vo.foodMaterialName}</td>
                                    <td>${vo.foodCategory}</td>
                                    <td>${vo.foodMaterialCount}</td>
                                    <td>${vo.foodMaterialCountAll}g</td>
                                    <td>${vo.foodMaterialPrice}</td>
                                    <td>${vo.vender}</td>
                                    <td>${vo.incomeDate}</td>
                                    <td>${vo.expirationDate}</td>
                                    <td>${vo.foodMaterialType}</td>
                                    <td>
                                        <button type="button" onclick="location.href='controller?cmd=disposalUIAction&foodMaterialId=${vo.foodMaterialId}'">폐기수정</button>
                                    </td>
                                    <td>
                                        <input type="checkbox" name="select_item" value="${vo.foodMaterialId}">
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div>
                        <ul class="page">
                            <c:if test="${currentPage > 1}">
                                <li style="cursor:pointer">
                                    <a href="controller?cmd=foodMaterialUIAction&sortType=${sortType}&page=${currentPage - 1}">&lt;</a>
                                </li>
                            </c:if>

                            <c:forEach begin="1" end="${totalPage}" var="p">
                                <li style="cursor:pointer">
                                    <a href="controller?cmd=foodMaterialUIAction&sortType=${sortType}&page=${p}"
                                       style="${p == currentPage ? 'font-weight:bold' : ''}">${p}</a>
                                </li>
                            </c:forEach>

                            <c:if test="${currentPage < totalPage}">
                                <li style="cursor:pointer">
                                    <a href="controller?cmd=foodMaterialUIAction&sortType=${sortType}&page=${currentPage + 1}">&gt;</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        var allRows = [];
        var viewRows = [];

        window.onload = function () {
            var tbody = document.getElementById("tableBody");
            allRows = Array.from(tbody.querySelectorAll("tr"));
            viewRows = allRows.slice();
        };

        function searchFood() {
            var keyword  = document.getElementById("searchName").value.trim();
            var category = document.getElementById("categorySelect").value;

            viewRows = allRows.filter(function (row) {
                var cells        = row.querySelectorAll("td");
                var foodName     = cells[1].textContent.trim();
                var foodCategory = cells[2].textContent.trim();

                var keywordOk  = keyword  === "" || foodName.indexOf(keyword) !== -1;
                var categoryOk = category === "" || foodCategory === category;

                return keywordOk && categoryOk;
            });

            allRows.forEach(function (row) { row.style.display = "none"; });
            viewRows.forEach(function (row) { row.style.display = ""; });
        }

        function resetFood() {
            document.getElementById("searchName").value = "";
            document.getElementById("categorySelect").selectedIndex = 0;
            allRows.forEach(function (row) { row.style.display = ""; });
            viewRows = allRows.slice();
        }

        function toggleSelectAll(selectAllCheckbox) {
            allRows.forEach(function (row) {
                if (row.style.display !== "none") {
                    var checkbox = row.querySelector("input[name='select_item']");
                    if (checkbox) checkbox.checked = selectAllCheckbox.checked;
                }
            });
        }

        function deleteSelected() {
            var checkedList = document.querySelectorAll("input[name='select_item']:checked");

            if (checkedList.length === 0) {
                alert("삭제할 식자재를 선택해주세요.");
                return;
            }

            if (!confirm("선택한 식자재를 삭제하시겠습니까?")) return;

            var ids = [];
            checkedList.forEach(function (checkbox) {
                ids.push(checkbox.value);
            });

            location.href = "controller?cmd=foodMaterialDeleteAction&ids=" + ids.join(",");
        }
    </script>
</body>

</html>

