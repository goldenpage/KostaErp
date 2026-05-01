<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>식자재 입력</title>
    <style>
        ul { list-style: none; }

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
            display: flex;
            gap: 16px;
        }

        .content_left {
            flex: 1;
        }

        .content_right {
            width: 400px;
            border: 3px solid green;
            padding: 10px;
            display: flex;
            flex-direction: column;
        }

        .content_right h3 { margin-top: 0; }

        .content_right table {
            border-spacing: 10px;
            font-size: 14px;
            width: 100%;
        }

        .content_right .register_final_btn {
            display: flex;
            justify-content: center;
            padding: 10px;
        }

        .input_section {
            border: 3px solid;
            padding: 16px;
            margin: 10px;
        }

        .input_row {
            display: flex;
            gap: 16px;
            align-items: center;
            margin-bottom: 12px;
        }

        .input_row label { min-width: 100px; }

        .category_buttons {
            display: flex;
            gap: 8px;
            flex-wrap: wrap;
            align-items: center;
        }

        .category_buttons button { padding: 4px 12px; cursor: pointer; }

        .category_buttons button.selected {
            background-color: #ccc;
            font-weight: bold;
        }

        .input_fields {
            display: flex;
            gap: 16px;
            align-items: center;
            flex-wrap: wrap;
            margin-top: 10px;
        }

        .input_fields label { min-width: 80px; }

        .input_fields input,
        .input_fields select { width: 120px; }

        .register_btn {
            margin-top: 12px;
            display: flex;
            justify-content: center;
        }

        .search_section {
            border: 3px solid;
            padding: 10px;
            margin: 10px;
        }

        .search_row {
            display: flex;
            gap: 16px;
            align-items: center;
            margin-bottom: 10px;
        }

        .list_container {
            display: flex;
            justify-content: center;
            border: 3px solid red;
            font-size: 14px;
            line-height: 14px;
            width: 100%;
        }

        table { border-spacing: 24px; }

        .page {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 16px;
            width: 100%;
            font-size: 18px;
            line-height: 18px;
        }

        .remove_btn {
            cursor: pointer;
            color: red;
        }

        .auto_date_hint {
            font-size: 11px;
            color: #999;
        }

        .empty_msg {
            text-align: center;
            color: #aaa;
            font-size: 13px;
        }
    </style>
</head>
<body>
    <div class="container">
        <section class="sideMenu">
          <jsp:include page="common/sideMenu.jsp" />
        </section>

        <div class="main">
        <form method="post" action="${pageContext.request.contextPath}/controller?cmd=addFoodMaterialAction" class="addFood">
            <div>
                <jsp:include page="common/userName.jsp" />
            </div>
            <h1>식자재 입력</h1>
            <c:if test="${not empty errorMessage}">
                <div style="color:red; margin: 0 10px;">${errorMessage}</div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div style="color:green; margin: 0 10px;">${successMessage}</div>
            </c:if>
            <div class="content_item">

                <div class="content_left">
                    <div class="input_section">

                        <div class="input_row">
                            
                            <div class="category_buttons">
                                <label>카테고리 추가</label>
                                <input type="text" id="getfoodCategory" name="foodCategory" placeholder="카테고리 입력">
                                <button type="button" onclick="addCategoryAjax()">추가하기</button>
                                <div id="test"></div>
                            </div>
                            
                        </div>

                        <div class="input_row">
                            <div class="category_buttons" id="categoryArea">
                                <c:forEach var="category" items="${categoryList}">
                                    <span style="display:inline-flex; align-items:center; gap:2px;">
                                        <form method="post" action="${pageContext.request.contextPath}/controller?cmd=deleteFoodCategoryAction" style="display:inline;">
                                            <input type="hidden" name="foodCategory" value="${category.foodCategory}">
                                            <button type="button" onclick="selectCategory(this)" data-category-id="${category.foodCategoryId}">${category.foodCategory}</button>
                                            <button type="submit" class="remove_btn" onclick="return confirm('${category.foodCategory} 카테고리를 삭제하시겠습니까?')">&#10005;</button>
                                        </form>
                                    </span>
                                </c:forEach>
                            </div>
                        </div>

                        
                        <input type="hidden" id="selectedCategoryId" value="">

                        <div class="input_row">
                            <label>식자재명 입력 *</label>
                            <input id="foodMaterialName" placeholder="단무지">
                        </div>

                        <div class="input_fields">
                            <label>전체수량 *</label>
                            <input type="number" id="foodMaterialCount" placeholder="5" min="0">

                            <label>전체수량(단위:g) *</label>
                            <input type="number" id="foodMaterialCountAll" placeholder="1500" min="0">
                            <select id="inputUnit">
                                <option value="g">g</option>
                                <option value="kg">kg</option>
                                <option value="ml">ml</option>
                                <option value="L">L</option>
                            </select>
                        </div>

                        <div class="input_fields">
                            <label>가격 *</label>
                            <input type="number" id="foodMaterialPrice" placeholder="10000" min="0">

                            <label>타입 *</label>
                            <input type="text" id="foodMaterialType" placeholder="고체">

                            <label>구입처 *</label>
                            <input type="text" id="vender" placeholder="하나로마트">
                        </div>

                        <div class="input_fields">
                            <label>매입일자</label>
                            <input type="date" id="incomeDate">
                            <span class="auto_date_hint">※ 미입력 시 오늘 날짜 자동 설정</span>

                            <label>유통기한 *</label>
                            <input type="date" id="expirationDate">
                            <span>&#10003;</span>
                        </div>

                        <div class="register_btn">
                            <button type="button" onclick="addToList()">추가</button>
                        </div>
                    </div>

                    <div class="search_section">
                        <h3>기존 식자재 찾기</h3>
                        <div class="search_row">
                            <label>검색</label>
                            <input type="text" id="searchInput" placeholder="단무지"
                                onkeydown="if(event.key==='Enter') searchMaterial()">
                            <button type="button" onclick="searchMaterial()">검색</button>
                        </div>
                        <div class="search_row">
                            <span>기존에 등록된 식자재 목록</span>
                        </div>
                        <div>
                            <table class="list_container">
                                <thead>
                                    <tr class="list_item">
                                        <th>이름</th>
                                        <th>카테고리</th>
                                        <th>구입처</th>
                                        <th>추가</th>
                                    </tr>
                                </thead>
                                <tbody id="searchResultBody">
                                    <tr><td colspan="4" class="empty_msg">검색어를 입력하세요</td></tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="content_right">
                    <h3>등록할 식자재 목록</h3>
                    <table id="registerTable">
                        <thead>
                            <tr>
                                <th>식자재명</th>
                                <th>카테고리</th>
                                <th>수량</th>
                                <th>용량</th>
                                <th>가격</th>
                                <th>삭제</th>
                            </tr>
                        </thead>
                        <tbody id="registerBody">
                            <tr><td colspan="6" class="empty_msg">추가된 식자재가 없습니다</td></tr>
                        </tbody>
                    </table>
                    <div id="hiddenFields"></div>
                    <div class="register_final_btn">
                        <button type="submit" class="submitbutton" onclick="return registerAll()">식자재 등록</button>
                    </div>
                </div>

            </div>
            </form>
        </div>
    </div>

    <script>
        function today() {
            return new Date().toISOString().substring(0, 10);
        }

        document.getElementById('incomeDate').value = today();

        function selectCategory(btn) {
            document.querySelectorAll('#categoryArea button').forEach(function(b) {
                b.classList.remove('selected');
            });
            btn.classList.add('selected');
            console.log(btn.getAttribute('data-category-id'))
            document.getElementById('selectedCategoryId').value = btn.getAttribute('data-category-id');
            
        }

        function getSelectedCategoryName() {
            var sel = document.querySelector('#categoryArea button.selected');
            return sel ? sel.textContent.trim() : '';
        }

        function addCategoryButton(foodCategoryId, foodCategory) {
            var area = document.getElementById('categoryArea');

            var btn = document.createElement('button');
            btn.type = 'button';
           
            btn.setAttribute('data-category-id', foodCategoryId);
            btn.textContent = foodCategory;
            btn.onclick = function () {
                selectCategory(this);
            };

            area.appendChild(btn);
            selectCategory(btn);
        }
        
        function addCategoryAjax() {
            var input = document.getElementById('getfoodCategory');
            var categoryName = input.value.trim();
            var msg = document.getElementById('test');

            if (!categoryName) {
                alert('카테고리명을 입력해주세요.');
                return;
            }

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "${pageContext.request.contextPath}/controller?cmd=addFoodCategoryAction", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                        var res = xhr.responseText;
                        var parts = res.split("|");

                        var result = parts[0];
                        var value = parts[1];

                        if (result === "success") {
                            msg.innerText = "카테고리가 추가되었습니다.";
                            addCategoryButton(value,value);
                            input.value = '';
                        } else {
                            msg.innerText = value;
                        }
                    } else {
                        msg.innerText = "카테고리 추가 중 오류가 발생했습니다.";
                    }
            };

            xhr.send("foodCategory=" + encodeURIComponent(categoryName));
        }

        
        var pendingList = [];

        function addToList() {
            var foodMaterialName = document.getElementById('foodMaterialName').value.trim();
            var foodCategory_Id = document.getElementById('selectedCategoryId').value;
            var foodCategoryName = getSelectedCategoryName();
            var foodMaterialCount = document.getElementById('foodMaterialCount').value;
            var foodMaterialCountAll = document.getElementById('foodMaterialCountAll').value;
            var unit = document.getElementById('inputUnit').value;
            var foodMaterialPrice = document.getElementById('foodMaterialPrice').value;
            var foodMaterialType = document.getElementById('foodMaterialType').value.trim();
            var vender = document.getElementById('vender').value.trim();
            var incomeDate = document.getElementById('incomeDate').value;
            var expirationDate = document.getElementById('expirationDate').value;

            if (!incomeDate) incomeDate = today();

            if (!foodMaterialName) { alert('식자재명을 입력해주세요.'); return; }
            if (!foodCategory_Id) { alert('카테고리를 선택해주세요.'); return; }
            if (!foodMaterialCount || Number(foodMaterialCount) < 0) { alert('전체수량을 올바르게 입력해주세요.'); return; }
            if (!foodMaterialCountAll || Number(foodMaterialCountAll) < 0) { alert('식자재 용량을 올바르게 입력해주세요.'); return; }
            if (!foodMaterialPrice || Number(foodMaterialPrice) < 0) { alert('가격을 올바르게 입력해주세요.'); return; }
            if (!foodMaterialType) { alert('타입을 입력해주세요.'); return; }
            if (!vender) { alert('구입처를 입력해주세요.'); return; }
            if (!expirationDate) { alert('유통기한을 입력해주세요.'); return; }
            if (expirationDate < incomeDate) { alert('유통기한이 매입일자보다 이전입니다.'); return; }

            pendingList.push({
                foodMaterialName: foodMaterialName,
                foodCategory_Id: foodCategory_Id,
                foodCategoryName: foodCategoryName,
                foodMaterialCount: foodMaterialCount,
                foodMaterialCountAll: foodMaterialCountAll,
                unit: unit,
                foodMaterialPrice: foodMaterialPrice,
                foodMaterialType: foodMaterialType,
                vender: vender,
                incomeDate: incomeDate,
                expirationDate: expirationDate
            });

            renderPendingList();
            clearInputs();
        }

        function renderPendingList() {
            var body = document.getElementById('registerBody');
            body.innerHTML = '';

            if (pendingList.length === 0) {
                body.innerHTML = '<tr><td colspan="6" class="empty_msg">추가된 식자재가 없습니다</td></tr>';
                return;
            }

            pendingList.forEach(function(item, idx) {
                var tr = document.createElement('tr');
                tr.innerHTML =
                    '<td>' + item.foodMaterialName + '</td>' +
                    '<td>' + item.foodCategoryName + '</td>' +
                    '<td>' + item.foodMaterialCount + '</td>' +
                    '<td>' + item.foodMaterialCountAll + item.unit + '</td>' +
                    '<td>' + Number(item.foodMaterialPrice).toLocaleString() + '원</td>' +
                    '<td><span class="remove_btn" data-index="' + idx + '" onclick="removeRow(this)">&#10005;</span></td>';
                body.appendChild(tr);
            });
        }

        function removeRow(el) {
            var idx = Number(el.getAttribute('data-index'));
            pendingList.splice(idx, 1);
            renderPendingList();
        }

        function rebuildHiddenFields() {
            var container = document.getElementById('hiddenFields');
            container.innerHTML = '';
            pendingList.forEach(function(item) {
                var fields = [
                    ['foodMaterialName', item.foodMaterialName],
                    ['foodCategory_Id', item.foodCategory_Id],
                    ['foodMaterialCount', item.foodMaterialCount],
                    ['foodMaterialCountAll', item.foodMaterialCountAll],
                    ['foodMaterialPrice', item.foodMaterialPrice],
                    ['foodMaterialType', item.foodMaterialType],
                    ['vender', item.vender],
                    ['incomeDate', item.incomeDate],
                    ['expirationDate', item.expirationDate]
                ];
                fields.forEach(function(f) {
                    var input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = f[0];
                    input.value = f[1];
                    container.appendChild(input);
                });
            });
        }

        function clearInputs() {
            document.getElementById('foodMaterialName').value = '';
            document.getElementById('foodMaterialCount').value = '';
            document.getElementById('foodMaterialCountAll').value = '';
            document.getElementById('inputUnit').selectedIndex = 0;
            document.getElementById('foodMaterialPrice').value = '';
            document.getElementById('foodMaterialType').value = '';
            document.getElementById('vender').value = '';
            document.getElementById('expirationDate').value = '';
            document.getElementById('incomeDate').value = today();
            document.getElementById('selectedCategoryId').value = '';
            document.querySelectorAll('#categoryArea button').forEach(function(b) {
                b.classList.remove('selected');
            });
        }

        function searchMaterial() {
            var keyword = document.getElementById('searchInput').value.trim();
            var body = document.getElementById('searchResultBody');

            if (!keyword) {
                body.innerHTML = '<tr><td colspan="4" class="empty_msg">검색어를 입력하세요</td></tr>';
                return;
            }

            if (typeof results === 'undefined' || results.length === 0) {
                body.innerHTML = '<tr><td colspan="4" class="empty_msg">검색 결과가 없습니다</td></tr>';
                return;
            }

            body.innerHTML = results.map(function(m) {
                return '<tr>' +
                    '<td>' + m.foodMaterialName + '</td>' +
                    '<td>' + m.foodCategory + '</td>' +
                    '<td>' + m.vender + '</td>' +
                    '<td><button type="button" onclick=\'fillFromSearch(' + JSON.stringify(m) + ')\'>&#8853;</button></td>' +
                    '</tr>';
            }).join('');
        }

        function fillFromSearch(data) {
            document.getElementById('foodMaterialName').value = data.foodMaterialName;
            document.getElementById('foodMaterialCount').value = data.foodMaterialCount;
            document.getElementById('foodMaterialCountAll').value = data.foodMaterialCountAll;
            document.getElementById('vender').value = data.vender;
            document.getElementById('foodMaterialPrice').value = data.foodMaterialPrice;

            var catBtns = document.querySelectorAll('#categoryArea button');
            var matched = false;
            catBtns.forEach(function(btn) {
                btn.classList.remove('selected');
                if (btn.getAttribute('data-category-id') === data.foodCategory_Id) {
                    btn.classList.add('selected');
                    document.getElementById('selectedCategoryId').value = data.foodCategory_Id;
                    matched = true;
                }
            });

            if (!matched) {
                document.getElementById('inputCategoryName').value = data.foodCategory;
                addCategory();
            }

            alert('"' + data.foodMaterialName + '" 정보를 불러왔습니다. 날짜를 확인 후 추가해주세요.');
        }

        function registerAll() {
            if (pendingList.length === 0) {
                alert('등록할 식자재가 없습니다.');
                return false;
            }
            rebuildHiddenFields();
            return true;
        }
    </script>
</body>
</html>
