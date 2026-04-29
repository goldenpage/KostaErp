<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>알림 페이지</title>

<style>
ul { list-style: none; }

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
    height: 100vh;
    overflow: hidden;
}

.profile {
    display: flex;
    justify-content: end;
}

.content_item {
    border: 3px solid blue;
    height: calc(100vh - 230px);
    overflow: hidden;
}

.content_item .list_container {
    display: block;
    width: 100%;
    max-height: 100%;
    overflow-y: auto;
    border: 3px solid red;
    font-size: 14px;
    line-height: 14px;
}

.content_item table {
    width: 100%;
    border-spacing: 20px;
}

.summary_box {
    display: flex;
    gap: 25px;
    padding: 15px;
    border: 2px solid #333;
    margin-bottom: 15px;
    font-size: 15px;
    font-weight: bold;
    flex-wrap: wrap;
}

button { cursor: pointer; }
</style>

<script>
function goBack(){
    history.back();
}
</script>

</head>

<body>

<div class="container">

    <!-- 사이드 메뉴 -->
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

    <!-- 메인 -->
    <div class="main">

        <ul class="profile">
            <li>김상혁님</li>
            <li><button type="button" onclick="location.href='controller?cmd=disposalUI'" >뒤로가기</button></li>
        </ul>

        <h2>폐기 알림</h2>

        <div class="summary_box">
            <span>유통기간 경과상품수 : <b id="expiredCount">0</b></span>
            <span>총 폐기재고량 : 고체 <b id="solidTotal">0</b> / 액체 <b id="liquidTotal">0</b></span>
            <span>최대 경과일 : <b id="maxOverDay">0</b>일</span>
            <span>현재날짜 : <b id="todayDate"></b></span>
        </div>

        <div class="content_item">

            <table class="list_container">

                <tr>
                    <th>식자재명</th>
                    <th>카테고리</th>
                    <th>총 폐기 용량</th>
                    <th>사유</th>
                    <th>폐기날짜</th>
                    <th><button onclick="clearNotice()">알림 전체 삭제</button></th>
                </tr>

                <tbody id="list"></tbody>

            </table>

        </div>

    </div>
</div>

<script>
// sessionStorage 기반 (나중에 DB로 교체 예정)

var list = JSON.parse(sessionStorage.getItem("noticeList")) || [];
var tbody = document.getElementById("list");

var expiredCount = 0;
var solidTotal = 0;
var liquidTotal = 0;
var maxOverDay = 0;

list.forEach(function(item){

    var tr = document.createElement("tr");

    tr.innerHTML =
        "<td>" + item.name + "</td>" +
        "<td>" + item.category + "</td>" +
        "<td>" + item.totalAmount + "</td>" +
        "<td>" + item.reason + "</td>" +
        "<td>" + item.date + "</td>";

    tbody.appendChild(tr);

    var num = parseFloat(item.totalAmount);

    if(item.type == "고체"){
        solidTotal += num;
    } else if(item.type == "액체"){
        liquidTotal += num;
    }

    var expire = new Date(item.expireDate);
    var dispose = new Date(item.date);

    if(dispose > expire){
        expiredCount++;

        var diff = Math.floor(
            (dispose - expire) / (1000 * 60 * 60 * 24)
        );

        if(diff > maxOverDay){
            maxOverDay = diff;
        }
    }
});

// 화면 출력
document.getElementById("expiredCount").innerText = expiredCount;
document.getElementById("solidTotal").innerText = solidTotal + "g";
document.getElementById("liquidTotal").innerText = liquidTotal + "L";
document.getElementById("maxOverDay").innerText = maxOverDay;
document.getElementById("todayDate").innerText =
    new Date().toLocaleDateString('sv-SE');

// 전체 삭제
function clearNotice(){

    sessionStorage.removeItem("noticeList");

    document.getElementById("list").innerHTML = "";

    document.getElementById("expiredCount").innerText = 0;
    document.getElementById("solidTotal").innerText = "0g";
    document.getElementById("liquidTotal").innerText = "0L";
    document.getElementById("maxOverDay").innerText = 0;

    document.getElementById("todayDate").innerText =
        new Date().toLocaleDateString('sv-SE');

    alert("알림이 삭제되었습니다.");
}
</script>

</body>
</html>