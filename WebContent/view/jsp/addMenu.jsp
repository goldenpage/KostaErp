<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메뉴 입력</title>

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
            display: flex;
            gap: 16px;
        }

        .content_left {
            flex: 1;
            overflow-y: auto;
        }

        .content_right {
            width: 400px;
            border: 3px solid green;
            padding: 10px;
            display: flex;
            flex-direction: column;
        }

        .content_right h3 {
            margin-top: 0;
        }

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

        .input_row label {
            min-width: 120px;
        }

        .category_buttons {
            display: flex;
            gap: 8px;
            flex-wrap: wrap;
            align-items: center;
        }

        .category_buttons button {
            padding: 4px 12px;
            cursor: pointer;
        }

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

        .input_fields label {
            min-width: 80px;
        }

        .input_fields input {
            width: 120px;
        }

        .register_btn {
            margin-top: 12px;
            display: flex;
            justify-content: center;
        }

        .ingredient_section {
            border: 3px solid;
            padding: 16px;
            margin: 10px;
        }

        .ingredient_add_row {
            display: flex;
            gap: 16px;
            align-items: center;
            margin-bottom: 12px;
        }

        .ingredient_add_row label {
            min-width: 50px;
        }

        .ingredient_add_row input {
            width: 100px;
        }

        .ingredient_table {
            border: 3px solid red;
            font-size: 14px;
            line-height: 14px;
            width: 100%;
        }

        .ingredient_table table {
            border-spacing: 16px;
            width: 100%;
        }

        table {
            border-spacing: 24px;
        }

        .remove_btn {
            cursor: pointer;
            color: red;
        }

        .right_top {
            flex: 1;
            overflow-y: auto;
        }

        .right_bottom {
            border-top: 2px solid #ccc;
            padding-top: 8px;
            margin-top: 8px;
        }

        .right_bottom h4 {
            margin: 0 0 6px 0;
            font-size: 13px;
            color: #555;
        }

        .ingredient_detail_table {
            width: 100%;
            border-collapse: collapse;
            font-size: 13px;
        }

        .ingredient_detail_table th {
            background: #f4f4f4;
            padding: 4px 8px;
            border: 1px solid #ddd;
            text-align: center;
        }

        .ingredient_detail_table td {
            padding: 4px 8px;
            border: 1px solid #ddd;
            text-align: center;
        }

        .empty_msg {
            text-align: center;
            color: #aaa;
            font-size: 13px;
        }

        #registerMenuTable tbody tr {
            cursor: pointer;
        }
        #registerMenuTable tbody tr:hover td {
            background: #f0f7ff;
        }
        #registerMenuTable tbody tr.selected_row td {
            background: #d6eaff;
        }
    </style>
</head>

<body>
    <div class="container">
        <section class="sideMenu">
            <jsp:include page="../common/sideMenu.jsp" />
        </section>

        <div class="main">
            <div>
              <jsp:include page="../common/userName.jsp" />
            </div>
            <h1>메뉴 입력</h1>
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
                                <label>메뉴 카테고리 추가</label>
                                <input type="text" id="getMenuCategory" placeholder="카테고리 입력">
                                <button type="button" onclick="addCategoryAjax()">추가</button>
                            </div>
                        </div>

                        <div class="input_row">
                            <div class="category_buttons" id="categoryArea">
                                <c:forEach var="category" items="${categoryList}">
                                    <span style="display:inline-flex; align-items:center; gap:2px;">
                                        <button type="button" onclick="selectCategory(this)" data-category-id="${category.menuCategoryId}">${category.menuCategory}</button>
                                        <button type="button" class="remove_btn" onclick="deleteCategoryAjax('${category.menuCategory}', this)">&#10005;</button>
                                    </span>
                                </c:forEach>
                            </div>
                        </div>
                        <div id="categoryMsg" style="font-size:13px; margin-bottom:8px;"></div>
                    </div>

                    <form method="post" action="${pageContext.request.contextPath}/controller?cmd=addMenuAction" class="addMenu">
                        <input type="hidden" id="selectedCategoryId" name="_dummy" value="">

                        <div class="input_section">
                            <div class="input_row">
                                <label>메뉴명 입력 *</label>
                                <input type="text" id="inputMenuName" placeholder="치즈김밥">
                            </div>

                            <div class="input_fields">
                                <label>메뉴 가격 *</label>
                                <input type="number" id="inputMenuPrice" placeholder="4000" min="0"> 원
                            </div>
                        </div>

                        <div class="ingredient_section">
                            <h3>사용 식자재 추가 *</h3>

                            <div class="ingredient_add_row">
                                <label>식자재</label>
                                <select id="inputIngredientSelect">
                                    <option value="">-- 선택 --</option>
                                    <c:forEach var="fm" items="${foodMaterialList}">
                                        <option value="${fm.foodMaterialId}" data-name="${fm.foodMaterialName}">${fm.foodMaterialName} (${fm.foodCategory})</option>
                                    </c:forEach>
                                </select>
                                <label>수량</label>
                                <input type="number" id="inputIngredientAmount" placeholder="50" min="1">
                                <span>g</span>
                                <button type="button" onclick="addIngredient()">+ 추가하기</button>
                            </div>

                            <div class="ingredient_table">
                                <table id="ingredientTable">
                                    <thead>
                                        <tr>
                                            <th>이름</th>
                                            <th>수량(g)</th>
                                            <th>삭제</th>
                                        </tr>
                                    </thead>
                                    <tbody id="ingredientBody">
                                        <tr><td colspan="3" class="empty_msg">추가된 식자재가 없습니다</td></tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="register_btn">
                            <button type="button" onclick="addMenuToList()">추가</button>
                        </div>

                        <div id="hiddenFields"></div>
                    </form>

                </div>

                <div class="content_right">
                    <h3>등록할 메뉴 목록</h3>

                    <div class="right_top">
                        <table id="registerMenuTable" style="width:100%">
                            <thead>
                                <tr>
                                    <th>메뉴명</th>
                                    <th>카테고리</th>
                                    <th>가격</th>
                                    <th>삭제</th>
                                </tr>
                            </thead>
                            <tbody id="registerMenuBody">
                                <tr><td colspan="4" class="empty_msg">추가된 메뉴가 없습니다</td></tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="right_bottom">
                        <h4>&#9660; 사용 식자재 (<span id="selectedMenuName">메뉴를 선택하세요</span>)</h4>
                        <table class="ingredient_detail_table">
                            <thead>
                                <tr>
                                    <th>이름</th>
                                    <th>수량(g)</th>
                                </tr>
                            </thead>
                            <tbody id="ingredientDetailBody">
                                <tr><td colspan="2" class="empty_msg">-</td></tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="register_final_btn">
                        <button type="button" onclick="registerAllMenus()">등록</button>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <script>
        var menuDataStore = [];

        function addCategoryAjax() {
            var input = document.getElementById('getMenuCategory');
            var categoryName = input.value.trim();
            var msg = document.getElementById('categoryMsg');

            if (!categoryName) {
                alert('카테고리명을 입력해주세요.');
                return;
            }

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "${pageContext.request.contextPath}/controller?cmd=addMenuCategoryAction", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var parts = xhr.responseText.split("|");
                    var result = parts[0];
                    var value = parts[1];
                    var getId = parts[2];

                    if (result === "success") {
                        msg.style.color = 'green';
                        msg.innerText = '카테고리가 추가되었습니다.';
                        
                        var span = document.createElement('span');
                        span.style.cssText = 'display:inline-flex; align-items:center; gap:2px;';
                        var selectBtn = document.createElement('button');
                        selectBtn.type = 'button';
                        selectBtn.textContent = getId;
                        selectBtn.setAttribute('data-category-id', value);
                        selectBtn.onclick = function() { selectCategory(this); };
                        
                        var delBtn = document.createElement('button');
                        delBtn.type = 'button';
                        delBtn.className = 'remove_btn';
                        delBtn.innerHTML = '&#10005;';
                        delBtn.onclick = function() { deleteCategoryAjax(value, this); };
                        span.appendChild(selectBtn);
                        span.appendChild(delBtn);
                        document.getElementById('categoryArea').appendChild(span);
                        input.value = '';
                    } else {
                        msg.style.color = 'red';
                        msg.innerText = value;
                    }
                } else if (xhr.readyState === 4) {
                    msg.style.color = 'red';
                    msg.innerText = '카테고리 추가 중 오류가 발생했습니다.';
                }
            };

            xhr.send("menuCategory=" + encodeURIComponent(categoryName));
        }
        
        function deleteCategoryAjax(menuCategory, delBtn) {
            if (!confirm(menuCategory + ' 카테고리를 삭제하시겠습니까?')) return;

            var msg = document.getElementById('categoryMsg');
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "${pageContext.request.contextPath}/controller?cmd=deleteMenuCategoryAction", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var parts = xhr.responseText.split("|");
                    var result = parts[0];
                    var value = parts[1];

                    if (result === "success") {
                        msg.style.color = 'green';
                        msg.innerText = value;
                        var span = delBtn.closest('span');
                        var selectedId = document.getElementById('selectedCategoryId').value;
                        var selectBtn = span.querySelector('button:not(.remove_btn)');
                        if (selectBtn && selectBtn.getAttribute('data-category-id') === selectedId) {
                            document.getElementById('selectedCategoryId').value = '';
                        }
                        span.remove();
                    } else {
                        msg.style.color = 'red';
                        msg.innerText = value;
                    }
                } else if (xhr.readyState === 4) {
                    msg.style.color = 'red';
                    msg.innerText = '카테고리 삭제 중 오류가 발생했습니다.';
                }
            };

            xhr.send("menuCategory=" + encodeURIComponent(menuCategory));
        }
        
        function selectCategory(btn) {
            document.querySelectorAll('#categoryArea button[data-category-id]').forEach(function(b) {
                b.classList.remove('selected');
            });
            btn.classList.add('selected');
            document.getElementById('selectedCategoryId').value = btn.getAttribute('data-category-id');
        }

        function getSelectedCategoryName() {
            var sel = document.querySelector('#categoryArea button.selected');
            return sel ? sel.textContent.trim() : '';
        }

        function addIngredient() {
            var select           = document.getElementById('inputIngredientSelect');
            var foodMaterialId   = select.value;
            var foodMaterialName = select.options[select.selectedIndex].getAttribute('data-name');
            var usedCount        = document.getElementById('inputIngredientAmount').value;

            if (!foodMaterialId)                      { alert('식자재를 선택해주세요.'); return; }
            if (!usedCount || Number(usedCount) <= 0) { alert('수량을 올바르게 입력해주세요.'); return; }

            var body = document.getElementById('ingredientBody');
            var emptyRow = body.querySelector('td[colspan]');
            if (emptyRow) emptyRow.closest('tr').remove();

            var tr = document.createElement('tr');
            tr.setAttribute('data-food-material-id', foodMaterialId);
            tr.setAttribute('data-used-count', usedCount);
            tr.innerHTML =
                '<td>' + foodMaterialName + '</td>' +
                '<td>' + Number(usedCount).toLocaleString() + 'g</td>' +
                '<td><span class="remove_btn" onclick="removeIngredientRow(this)">&#8854;</span></td>';
            body.appendChild(tr);

            select.selectedIndex = 0;
            document.getElementById('inputIngredientAmount').value = '';
        }

        function removeIngredientRow(el) {
            var body = document.getElementById('ingredientBody');
            el.closest('tr').remove();
            if (body.querySelectorAll('tr').length === 0) {
                body.innerHTML = '<tr><td colspan="3" class="empty_msg">추가된 식자재가 없습니다</td></tr>';
            }
        }

        function getIngredientList() {
            var rows = document.querySelectorAll('#ingredientBody tr[data-food-material-id]');
            var list = [];
            rows.forEach(function(row) {
                list.push({
                    foodMaterialId:   row.getAttribute('data-food-material-id'),
                    foodMaterialName: row.querySelectorAll('td')[0].textContent,
                    usedCount:        row.getAttribute('data-used-count')
                });
            });
            return list;
        }

        function addMenuToList() {
            var menuName       = document.getElementById('inputMenuName').value.trim();
            var menuCategoryId = document.getElementById('selectedCategoryId').value;
            var menuCategory   = getSelectedCategoryName();
            var menuPrice      = document.getElementById('inputMenuPrice').value;
            var ingredients    = getIngredientList();

            if (!menuName)                           { alert('메뉴명을 입력해주세요.'); return; }
            if (!menuCategoryId)                     { alert('카테고리를 선택해주세요.'); return; }
            if (!menuPrice || Number(menuPrice) < 0) { alert('메뉴 가격을 올바르게 입력해주세요.'); return; }
            if (ingredients.length === 0)            { alert('사용 식자재를 추가해주세요.'); return; }

            var isDuplicate = menuDataStore.some(function(m) { return m.menuName === menuName; });
            if (isDuplicate) { alert('"' + menuName + '"은 이미 등록된 메뉴입니다.'); return; }

            menuDataStore.push({
                menuName:       menuName,
                menuCategoryId: menuCategoryId,
                menuCategory:   menuCategory,
                menuPrice:      menuPrice,
                ingredients:    ingredients
            });

            renderMenuList();

            document.getElementById('inputMenuName').value = '';
            document.getElementById('inputMenuPrice').value = '';
            document.getElementById('selectedCategoryId').value = '';
            document.querySelectorAll('#categoryArea button[data-category-id]').forEach(function(b) {
                b.classList.remove('selected');
            });
            document.getElementById('ingredientBody').innerHTML =
                '<tr><td colspan="3" class="empty_msg">추가된 식자재가 없습니다</td></tr>';
        }

        function renderMenuList() {
            var body = document.getElementById('registerMenuBody');
            body.innerHTML = '';

            if (menuDataStore.length === 0) {
                body.innerHTML = '<tr><td colspan="4" class="empty_msg">추가된 메뉴가 없습니다</td></tr>';
                return;
            }

            menuDataStore.forEach(function(menu) {
                var tr = document.createElement('tr');
                tr.setAttribute('data-menu-name', menu.menuName);
                tr.innerHTML =
                    '<td>' + menu.menuName + '</td>' +
                    '<td>' + menu.menuCategory + '</td>' +
                    '<td>' + Number(menu.menuPrice).toLocaleString() + '원</td>' +
                    '<td><span class="remove_btn" onclick="removeMenuRow(this)">&#10005;</span></td>';
                tr.addEventListener('click', function(e) {
                    if (e.target.classList.contains('remove_btn')) return;
                    showIngredientDetail(menu.menuName);
                    document.querySelectorAll('#registerMenuBody tr').forEach(function(r) {
                        r.classList.remove('selected_row');
                    });
                    this.classList.add('selected_row');
                });
                body.appendChild(tr);
            });
        }

        function removeMenuRow(el) {
            var menuName = el.closest('tr').getAttribute('data-menu-name');
            var idx = menuDataStore.findIndex(function(m) { return m.menuName === menuName; });
            if (idx !== -1) menuDataStore.splice(idx, 1);
            renderMenuList();
            document.getElementById('selectedMenuName').textContent = '메뉴를 선택하세요';
            document.getElementById('ingredientDetailBody').innerHTML =
                '<tr><td colspan="2" class="empty_msg">-</td></tr>';
        }

        function showIngredientDetail(menuName) {
            var menu = menuDataStore.find(function(m) { return m.menuName === menuName; });
            if (!menu) return;
            document.getElementById('selectedMenuName').textContent = menuName;
            var body = document.getElementById('ingredientDetailBody');
            body.innerHTML = menu.ingredients.map(function(ing) {
                return '<tr><td>' + ing.foodMaterialName + '</td><td>' + ing.usedCount + 'g</td></tr>';
            }).join('');
        }

        function rebuildHiddenFields() {
            var container = document.getElementById('hiddenFields');
            container.innerHTML = '';

            menuDataStore.forEach(function(menu) {
                var fields = [
                    ['menuName',            menu.menuName],
                    ['menuCategoryId',      menu.menuCategoryId],
                    ['menuPrice',           menu.menuPrice],
                    ['menuIngredientCount', menu.ingredients.length]
                ];
                fields.forEach(function(f) {
                    var input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = f[0];
                    input.value = f[1];
                    container.appendChild(input);
                });

                menu.ingredients.forEach(function(ing) {
                    var idInput = document.createElement('input');
                    idInput.type = 'hidden';
                    idInput.name = 'foodMaterial_Id';
                    idInput.value = ing.foodMaterialId;
                    container.appendChild(idInput);

                    var countInput = document.createElement('input');
                    countInput.type = 'hidden';
                    countInput.name = 'usedCount';
                    countInput.value = ing.usedCount;
                    container.appendChild(countInput);
                });
            });
        }

        function registerAllMenus() {
            if (menuDataStore.length === 0) {
                alert('등록할 메뉴가 없습니다.');
                return;
            }
            rebuildHiddenFields();
            document.querySelector('form.addMenu').submit();
        }
    </script>
</body>

</html>
