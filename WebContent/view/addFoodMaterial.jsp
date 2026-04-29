<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <ul>
                <li><button type="button" onclick="location.href='controller?cmd=menuUIAction'">메뉴조회</button></li>
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
            <h1>식자재 입력</h1>
            <div class="content_item">

                <div class="content_left">
                    <div class="input_section">

                        <div class="input_row">
                            <label>식자재명 입력 *</label>
                            <input type="text" id="inputName" placeholder="단무지">
                        </div>

                        <div class="input_row">
                            <div class="category_buttons">
                                <label>카테고리 추가</label>
                                <input type="text" id="inputCategoryName" placeholder="카테고리 입력">
                                <button type="button" onclick="addCategory()">추가하기</button>
                            </div>
                        </div>

                        <div class="input_row">
                            <div class="category_buttons" id="categoryArea">
                                <button type="button" onclick="selectCategory(this)">유제품</button>
                                <button type="button" onclick="selectCategory(this)">재료</button>
                                <button type="button" onclick="selectCategory(this)">밀</button>
                                <button type="button" onclick="selectCategory(this)">과일</button>
                                <button type="button" onclick="selectCategory(this)" class="selected">가공식품</button>
                                <button type="button" onclick="selectCategory(this)">기타(E)</button>
                            </div>
                        </div>

                        <div class="input_fields">
                            <label>전체수량 *</label>
                            <input type="number" id="inputQuantity" placeholder="5" min="0">

                            <label>전체수량(단위:g) *</label>
                            <input type="text" id="inputWeight" placeholder="1.5">
                            <select id="inputUnit">
                                <option value="g">g</option>
                                <option value="kg">kg</option>
                                <option value="ml">ml</option>
                                <option value="L">L</option>
                            </select>
                        </div>

                        <div class="input_fields">
                            <label>가격 *</label>
                            <input type="number" id="inputPrice" placeholder="10000" min="0">

                            <label>유통기한 *</label>
                            <input type="date" id="inputExpiry">
                            <span>&#10003;</span>
                        </div>

                        <div class="input_fields">
                            <label>매입일자</label>
                            <input type="date" id="inputPurchase">
                            <span class="auto_date_hint">※ 미입력 시 오늘 날짜 자동 설정</span>
                        </div>

                        <div class="input_fields">
                            <label>구입처 *</label>
                            <input type="text" id="inputVender" placeholder="하나로마트">
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
                    <div class="register_final_btn">
                        <button type="button" onclick="registerAll()">등록</button>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <script>
        const dummyMaterials = [
            { name: '단무지', category: '가공식품', vender: '웰빙푸드', quantity: 5,  weight: 1.5, unit: 'kg', price: 10000 },
            { name: '단무지', category: '가공식품', vender: '건강푸드', quantity: 3,  weight: 2.0, unit: 'kg', price: 12000 },
            { name: '당근',   category: '재료',     vender: '농협마트', quantity: 10, weight: 500, unit: 'g',  price: 3000  },
            { name: '우유',   category: '유제품',   vender: '서울우유', quantity: 6,  weight: 1,   unit: 'L',  price: 5000  },
            { name: '밀가루', category: '밀',       vender: '대한제분', quantity: 10, weight: 1,   unit: 'kg', price: 4500  },
        ];

        function today() {
            return new Date().toISOString().substring(0, 10);
        }

        document.getElementById('inputExpiry').value = today();

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
            var name = document.getElementById('inputCategoryName').value.trim();
            if (!name) { alert('카테고리명을 입력해주세요.'); return; }

            var existing = Array.from(document.querySelectorAll('#categoryArea button'))
                .map(function(b) { return b.textContent.trim(); });
            if (existing.indexOf(name) !== -1) { alert('이미 존재하는 카테고리입니다.'); return; }

            var btn = document.createElement('button');
            btn.type = 'button';
            btn.textContent = name;
            btn.onclick = function() { selectCategory(this); };
            document.getElementById('categoryArea').appendChild(btn);
            document.getElementById('inputCategoryName').value = '';
            selectCategory(btn);
        }

        function addToList() {
            var name     = document.getElementById('inputName').value.trim();
            var category = getSelectedCategory();
            var quantity = document.getElementById('inputQuantity').value;
            var weight   = document.getElementById('inputWeight').value;
            var unit     = document.getElementById('inputUnit').value;
            var price    = document.getElementById('inputPrice').value;
            var expiry   = document.getElementById('inputExpiry').value;
            var vender   = document.getElementById('inputVender').value.trim();

            var purchase = document.getElementById('inputPurchase').value;
            if (!purchase) purchase = today();

            if (!name)                             { alert('식자재명을 입력해주세요.'); return; }
            if (!category)                         { alert('카테고리를 선택해주세요.'); return; }
            if (!quantity || Number(quantity) < 0) { alert('전체수량을 올바르게 입력해주세요.'); return; }
            if (!weight  || Number(weight) < 0)    { alert('식자재 용량을 올바르게 입력해주세요.'); return; }
            if (!price   || Number(price) < 0)     { alert('가격을 올바르게 입력해주세요.'); return; }
            if (!expiry)                           { alert('유통기한을 입력해주세요.'); return; }
            if (!vender)                           { alert('구입처를 입력해주세요.'); return; }

            if (expiry < purchase) {
                alert('유통기한이 매입일자보다 이전입니다. 확인해주세요.');
                return;
            }

            var body = document.getElementById('registerBody');
            var emptyRow = body.querySelector('td[colspan]');
            if (emptyRow) emptyRow.closest('tr').remove();

            var tr = document.createElement('tr');
            tr.innerHTML =
                '<td>' + name + '</td>' +
                '<td>' + category + '</td>' +
                '<td>' + quantity + '</td>' +
                '<td>' + weight + unit + '</td>' +
                '<td>' + Number(price).toLocaleString() + '원</td>' +
                '<td><span class="remove_btn" onclick="removeRow(this)">&#10005;</span></td>';
            body.appendChild(tr);

            document.getElementById('inputName').value = '';
            document.getElementById('inputQuantity').value = '';
            document.getElementById('inputWeight').value = '';
            document.getElementById('inputUnit').selectedIndex = 0;
            document.getElementById('inputPrice').value = '';
            document.getElementById('inputExpiry').value = today();
            document.getElementById('inputPurchase').value = '';
            document.getElementById('inputVender').value = '';
        }

        function removeRow(el) {
            var body = document.getElementById('registerBody');
            el.closest('tr').remove();
            if (body.querySelectorAll('tr').length === 0) {
                body.innerHTML = '<tr><td colspan="6" class="empty_msg">추가된 식자재가 없습니다</td></tr>';
            }
        }

        function searchMaterial() {
            var keyword = document.getElementById('searchInput').value.trim();
            var body = document.getElementById('searchResultBody');

            if (!keyword) {
                body.innerHTML = '<tr><td colspan="4" class="empty_msg">검색어를 입력하세요</td></tr>';
                return;
            }

            var results = dummyMaterials.filter(function(m) {
                return m.name.indexOf(keyword) !== -1 || m.category.indexOf(keyword) !== -1;
            });

            if (results.length === 0) {
                body.innerHTML = '<tr><td colspan="4" class="empty_msg">검색 결과가 없습니다</td></tr>';
                return;
            }

            body.innerHTML = results.map(function(m) {
                return '<tr>' +
                    '<td>' + m.name + '</td>' +
                    '<td>' + m.category + '</td>' +
                    '<td>' + m.vender + '</td>' +
                    '<td><button type="button" onclick=\'fillFromSearch(' + JSON.stringify(m) + ')\'>&#8853;</button></td>' +
                    '</tr>';
            }).join('');
        }

        function fillFromSearch(data) {
            document.getElementById('inputName').value     = data.name;
            document.getElementById('inputQuantity').value = data.quantity;
            document.getElementById('inputWeight').value   = data.weight;
            document.getElementById('inputVender').value   = data.vender;
            document.getElementById('inputPrice').value    = data.price;

            var unitSelect = document.getElementById('inputUnit');
            Array.from(unitSelect.options).forEach(function(opt, i) {
                if (opt.value === data.unit) unitSelect.selectedIndex = i;
            });

            var catBtns = document.querySelectorAll('#categoryArea button');
            var matched = false;
            catBtns.forEach(function(btn) {
                btn.classList.remove('selected');
                if (btn.textContent.trim() === data.category) {
                    btn.classList.add('selected');
                    matched = true;
                }
            });

            if (!matched) {
                document.getElementById('inputCategoryName').value = data.category;
                addCategory();
            }

            alert('"' + data.name + '" 정보를 불러왔습니다. 날짜를 확인 후 추가해주세요.');
        }

        function registerAll() {
            var rows = Array.from(document.querySelectorAll('#registerBody tr'))
                .filter(function(r) { return !r.querySelector('td[colspan]'); });

            if (rows.length === 0) {
                alert('등록할 식자재가 없습니다.');
                return;
            }

            alert(rows.length + '개의 식자재가 등록되었습니다.');

            document.getElementById('registerBody').innerHTML =
                '<tr><td colspan="6" class="empty_msg">추가된 식자재가 없습니다</td></tr>';
        }
    </script>
</body>
</html>
