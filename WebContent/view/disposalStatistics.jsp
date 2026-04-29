<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

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
            display: flex;
            width: 100%;
            height: 100%;
            padding: 12px 0px;
            justify-content: space-between;
            border: 3px solid;

        }

        .content_item .disposal_price {

            border: 3px solid blue;
        }

        .disposal_price ul {
            display: flex;
            gap: 8px;
        }

        .content_item .disposal_ratio {
            border: 3px solid red;
        }

        .content_item .disposal_solid_liquid {
            border: 3px solid green;
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
                    <td>김상혁님</td>
                    <td>알림</td>
                </ul>
            </div>
            <h1>폐기통계</h1>
            <div class="content_item">
                <div class="disposal_price">
                    <div>4월 폐기율</div>
                    <div>4월 총폐기금액</div>
                    <div>
                        <div>4월 폐기품목 Top3</div>
                        <ul>
                            <li>번호</li>
                            <li>폐기식자재명</li>
                            <li>카테고리</li>
                            <li>폐기총용량</li>
                            <li>폐기총가격</li>
                        </ul>
                        <ul>
                            <li>1</li>
                            <li>우유</li>
                            <li>유제품</li>
                            <li>3000g</li>
                            <li>15,000</li>
                        </ul>
                    </div>
                </div>
                <div class="disposal_ratio">
                    <div>폐기사유비율 그래프</div>
                    <div>폐기사유비율</div>
                </div>
                <div class="disposal_solid_liquid">
                    <div>날짜별폐기량 그래프(고체)</div>
                    <div>날짜별폐기량 그래프(액체)</div>
                </div>


            </div>
        </div>
    </div>
</body>

</html>