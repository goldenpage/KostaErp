<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="kr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메뉴 조회</title>

    <style>
        ul {
            list-style: none;
        }

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
            height: 100%;
            display: flex;
            gap: 16px;
        }

        .content_left {
            flex: 1;
        }

        .content_right {
            width: 300px;
            border: 3px solid green;
            padding: 10px;
        }

        .content_right h3 {
            margin-top: 0;
        }

        .content_right table {
            border-spacing: 10px;
            font-size: 14px;
            width: 100%;
        }

        .menu_list {
            border: 3px solid;
            padding: 10px;
            margin: 10px;
        }

        .menu_list table {
            border-spacing: 16px;
            width: 100%;
            font-size: 14px;
        }

        .menu_list tr {
            cursor: pointer;
        }

        .menu_list tr:hover {
            background-color: #f0f0f0;
        }

        .menu_list tr.active {
            background-color: #ddd;
        }

        table {
            border-spacing: 24px;
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
                    <td>김상혁님</td>
                    <td>알림</td>
                </ul>
            </div>
            <h1>메뉴 조회</h1>
            <div class="content_item">

                <div class="content_left">
                    <div class="menu_list">
                        <table>
                            <tr>
                                <th></th>
                                <th>판매가능 메뉴</th>
                                <th>가격(원)</th>
                            </tr>
                            <tr onclick="selectMenu(this, '참치김밥', [['참치','50g'],['밥','150g'],['단무지','30g'],['당근','20g'],['김','10g']])" class="active">
                                <td><input type="checkbox" checked></td>
                                <td>참치김밥</td>
                                <td>4,500</td>
                            </tr>
                            <tr onclick="selectMenu(this, '치즈김밥', [['치즈','40g'],['밥','150g'],['단무지','30g'],['당근','20g'],['김','10g']])">
                                <td><input type="checkbox"></td>
                                <td>치즈김밥</td>
                                <td>4,000</td>
                            </tr>
                            <tr onclick="selectMenu(this, '기본김밥', [['밥','150g'],['단무지','30g'],['당근','20g'],['김','10g'],['계란','50g']])">
                                <td><input type="checkbox"></td>
                                <td>기본김밥</td>
                                <td>3,500</td>
                            </tr>
                        </table>
                    </div>
                </div>

                <div class="content_right">
                    <h3>사용하는 식자재</h3>
                    <table id="ingredientDetail">
                        <tr>
                            <th>이름</th>
                            <th>수량</th>
                        </tr>
                        <tr>
                            <td>참치</td>
                            <td>50g</td>
                        </tr>
                        <tr>
                            <td>밥</td>
                            <td>150g</td>
                        </tr>
                        <tr>
                            <td>단무지</td>
                            <td>30g</td>
                        </tr>
                        <tr>
                            <td>당근</td>
                            <td>20g</td>
                        </tr>
                        <tr>
                            <td>김</td>
                            <td>10g</td>
                        </tr>
                    </table>
                </div>

            </div>
        </div>
    </div>

<script>
    function selectMenu(rowEl, menuName, ingredients) {
        var rows = document.querySelectorAll(".menu_list tr");

        rows.forEach(function (tr) {
            tr.classList.remove("active");
        });

        rowEl.classList.add("active");

        var checkboxes = document.querySelectorAll(".menu_list input[type='checkbox']");

        checkboxes.forEach(function (checkbox) {
            checkbox.checked = false;
        });

        var currentCheckbox = rowEl.querySelector("input[type='checkbox']");

        if (currentCheckbox !== null) {
            currentCheckbox.checked = true;
        }

        drawIngredientTable(ingredients);
    }

    function drawIngredientTable(ingredients) {
        var table = document.getElementById("ingredientDetail");

        while (table.rows.length > 1) {
            table.deleteRow(1);
        }

        ingredients.forEach(function (item) {
            var newRow = document.createElement("tr");

            newRow.innerHTML =
                "<td>" + item[0] + "</td>" +
                "<td>" + item[1] + "</td>";

            table.appendChild(newRow);
        });
    }
</script>
</body>

</html>
