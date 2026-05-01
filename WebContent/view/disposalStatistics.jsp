<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="kr">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>폐기통계</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

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

			<jsp:include page="common/sideMenu.jsp" />

		</section>
		<div class="main">
			<div>
				<jsp:include page="common/userName.jsp" />
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
					<div>폐기사유비율</div>
					<canvas id="reasonChart"></canvas>
				</div>

				<div class="disposal_solid_liquid">
					<div>날짜별 폐기량</div>
					<canvas id="dailyDisposalChart"></canvas>
				</div>


			</div>
		</div>
	</div>
	<script>
	const dailyLabels = ["04-01", "04-02", "04-03", "04-04"];
	const solidData = [3, 5, 2, 7];
	const liquidData = [1, 2, 4, 3];

	new Chart(document.querySelector("#dailyDisposalChart"), {
	    type: "line",
	    data: {
	        labels: dailyLabels,
	        datasets: [
	            {
	                label: "고체",
	                data: solidData,
	                borderDash: [6, 4]
	            },
	            {
	                label: "액체",
	                data: liquidData,
	                borderDash: [6, 4]
	            }
	        ]
	    }
	});

	const reasonData = {
	    labels: ["유통기한", "파손", "재고초과"],
	    datasets: [{
	        data: [40, 35, 25]
	    }]
	};

	new Chart(document.querySelector("#reasonChart"), {
	    type: "doughnut",
	    data: reasonData
	});

	</script>
</body>

</html>