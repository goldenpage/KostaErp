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
    xhr.open("POST", contextPath + "/controller?cmd=addMenuCategoryAction", true);
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
    xhr.open("POST", contextPath + "/controller?cmd=deleteMenuCategoryAction", true);
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