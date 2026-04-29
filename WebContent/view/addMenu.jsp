<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

            <jsp:include page="common/sideMenu.jsp" />

        </section>

        <div class="main">
            <div>
              <jsp:include page="common/userName.jsp" />
            </div>
            <h1>메뉴 입력</h1>
            <div class="content_item">

                <div class="content_left">
                    <div class="input_section">
                        <div class="input_row">
                            <label>메뉴명 입력 *</label>
                            <input type="text" id="inputMenuName" placeholder="치즈김밥">
                        </div>

                        <div class="input_row">
                            <label>메뉴 카테고리 추가</label>
                            <input type="text" id="inputMenuCategory" placeholder="카테고리 입력">
                            <button type="button" onclick="addCategory()">추가</button>
                        </div>

                        <div class="input_row">
                            <div class="category_buttons" id="categoryArea">
                                <button type="button" onclick="selectCategory(this)">일식</button>
                                <button type="button" onclick="selectCategory(this)" class="selected">김밥</button>
                                <button type="button" onclick="selectCategory(this)">기타</button>
                            </div>
                        </div>

                        <div class="input_fields">
                            <label>메뉴 가격 *</label>
                            <input type="number" id="inputMenuPrice" placeholder="4000" min="0"> 원
                        </div>
                    </div>

                    <div class="ingredient_section">
                        <h3>사용 식자재 추가 *</h3>

                        <div class="ingredient_add_row">
                            <label>이름</label>
                            <input type="text" id="inputIngredientName" placeholder="김">
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

        function selectCategory(btn) {
            document.querySelectorAll('#categoryArea button').forEach(function(b) {
                b.classList.remove('selected');
            });
            btn.classList.add('selected');
        }

        function getSelectedCategory() {
            var sel = document.querySelector('#categoryArea button.selected');
            return sel ? sel.textContent.trim() : '';
        }

        function addCategory() {
            var name = document.getElementById('inputMenuCategory').value.trim();
            if (!name) { alert('카테고리명을 입력해주세요.'); return; }

            var existing = Array.from(document.querySelectorAll('#categoryArea button'))
                .map(function(b) { return b.textContent.trim(); });
            if (existing.indexOf(name) !== -1) { alert('이미 존재하는 카테고리입니다.'); return; }

            var btn = document.createElement('button');
            btn.type = 'button';
            btn.textContent = name;
            btn.onclick = function() { selectCategory(this); };
            document.getElementById('categoryArea').appendChild(btn);
            document.getElementById('inputMenuCategory').value = '';
            selectCategory(btn);
        }

        function addIngredient() {
            var name   = document.getElementById('inputIngredientName').value.trim();
            var amount = document.getElementById('inputIngredientAmount').value;

            if (!name || !amount || Number(amount) <= 0) {
                alert('식자재 이름과 수량을 정확히 입력해주세요.');
                return;
            }

            var body = document.getElementById('ingredientBody');
            var emptyRow = body.querySelector('td[colspan]');
            if (emptyRow) emptyRow.closest('tr').remove();

            var tr = document.createElement('tr');
            tr.innerHTML =
                '<td>' + name + '</td>' +
                '<td>' + Number(amount).toLocaleString() + 'g</td>' +
                '<td><span class="remove_btn" onclick="removeIngredientRow(this)">&#8854;</span></td>';
            body.appendChild(tr);

            document.getElementById('inputIngredientName').value = '';
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
            var rows = document.querySelectorAll('#ingredientBody tr');
            var list = [];
            rows.forEach(function(row) {
                var cells = row.querySelectorAll('td');
                if (cells.length >= 2 && !cells[0].getAttribute('colspan')) {
                    list.push({ name: cells[0].textContent, amount: cells[1].textContent });
                }
            });
            return list;
        }

        function addMenuToList() {
            var menuName     = document.getElementById('inputMenuName').value.trim();
            var menuCategory = getSelectedCategory();
            var menuPrice    = document.getElementById('inputMenuPrice').value;
            var ingredients  = getIngredientList();

            if (!menuName)                           { alert('메뉴명을 입력해주세요.'); return; }
            if (!menuCategory)                       { alert('카테고리를 선택해주세요.'); return; }
            if (!menuPrice || Number(menuPrice) < 0) { alert('메뉴 가격을 올바르게 입력해주세요.'); return; }
            if (ingredients.length === 0)            { alert('사용 식자재를 추가해주세요.'); return; }

            var isDuplicate = menuDataStore.some(function(m) { return m.name === menuName; });
            if (isDuplicate) {
                alert('"' + menuName + '"은 이미 등록된 메뉴입니다.');
                return;
            }

            menuDataStore.push({ name: menuName, category: menuCategory, price: menuPrice, ingredients: ingredients });

            var body = document.getElementById('registerMenuBody');
            var emptyRow = body.querySelector('td[colspan]');
            if (emptyRow) emptyRow.closest('tr').remove();

            var tr = document.createElement('tr');
            tr.dataset.menuName = menuName;
            tr.innerHTML =
                '<td>' + menuName + '</td>' +
                '<td>' + menuCategory + '</td>' +
                '<td>' + Number(menuPrice).toLocaleString() + '원</td>' +
                '<td><span class="remove_btn" onclick="removeMenuRow(this)">&#10005;</span></td>';

            tr.addEventListener('click', function(e) {
                if (e.target.classList.contains('remove_btn')) return;
                showIngredientDetail(menuName);
                document.querySelectorAll('#registerMenuBody tr').forEach(function(r) {
                    r.classList.remove('selected_row');
                });
                this.classList.add('selected_row');
            });
            body.appendChild(tr);

            document.getElementById('inputMenuName').value = '';
            document.getElementById('inputMenuPrice').value = '';
            document.getElementById('ingredientBody').innerHTML =
                '<tr><td colspan="3" class="empty_msg">추가된 식자재가 없습니다</td></tr>';
        }

        function removeMenuRow(el) {
            var body = document.getElementById('registerMenuBody');
            var tr = el.closest('tr');
            var menuName = tr.dataset.menuName;

            var idx = menuDataStore.findIndex(function(m) { return m.name === menuName; });
            if (idx !== -1) menuDataStore.splice(idx, 1);

            tr.remove();

            if (body.querySelectorAll('tr').length === 0) {
                body.innerHTML = '<tr><td colspan="4" class="empty_msg">추가된 메뉴가 없습니다</td></tr>';
                document.getElementById('selectedMenuName').textContent = '메뉴를 선택하세요';
                document.getElementById('ingredientDetailBody').innerHTML =
                    '<tr><td colspan="2" class="empty_msg">-</td></tr>';
            }
        }

        function showIngredientDetail(menuName) {
            var menu = menuDataStore.find(function(m) { return m.name === menuName; });
            if (!menu) return;

            document.getElementById('selectedMenuName').textContent = menuName;
            var body = document.getElementById('ingredientDetailBody');
            body.innerHTML = menu.ingredients.map(function(ing) {
                return '<tr><td>' + ing.name + '</td><td>' + ing.amount + '</td></tr>';
            }).join('');
        }

        function registerAllMenus() {
            var rows = Array.from(document.querySelectorAll('#registerMenuBody tr'))
                .filter(function(r) { return !r.querySelector('td[colspan]'); });

            if (rows.length === 0) {
                alert('등록할 메뉴가 없습니다.');
                return;
            }

            alert(rows.length + '개의 메뉴가 등록되었습니다.');

            menuDataStore.length = 0;
            document.getElementById('registerMenuBody').innerHTML =
                '<tr><td colspan="4" class="empty_msg">추가된 메뉴가 없습니다</td></tr>';
            document.getElementById('selectedMenuName').textContent = '메뉴를 선택하세요';
            document.getElementById('ingredientDetailBody').innerHTML =
                '<tr><td colspan="2" class="empty_msg">-</td></tr>';
        }
    </script>
</body>

</html>
