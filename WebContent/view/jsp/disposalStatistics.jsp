<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">

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
	height: 100vh;
}

.sideMenu {
	height: 100vh;
	width: 300px;
}

.main {
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

}

.disposal_price ul {
	display: flex;
	gap: 8px;
}

.content_item .disposal_ratio {
	
}

.content_item .disposal_solid_liquid {
	
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

table {
	border-collapse: collapse;
	margin-top: 10px;
}

th, td {
	border: 1px solid #999;
	padding: 8px 12px;
	text-align: center;
}

.chart_box {
	width: 360px;
	height: 300px;
}

canvas {
	width: 360px !important;
	height: 300px !important;
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

			<h1>폐기통계</h1>

			<div class="content_item">
				<div class="disposal_price">
					<div>폐기율: ${disposalRate}%</div>

					<div>
						총 폐기금액:
						<fmt:formatNumber value="${totalDisposalPrice}" pattern="#,###" />
						원
					</div>

					<div>
						<div>폐기품목 Top3</div>

						<table>
							<thead>
								<tr>
									<th>번호</th>
									<th>식자재명</th>
									<th>폐기횟수</th>
									<th>총 폐기금액</th>
								</tr>
							</thead>

							<tbody>
								<c:forEach var="item" items="${top3List}" varStatus="status">
									<tr>
										<td>${status.count}</td>
										<td>${item.foodMaterialName}</td>
										<td>${item.disposalCount}</td>
										<td>
											<fmt:formatNumber value="${item.totalDisposalPrice}" pattern="#,###" />
											원
										</td>
									</tr>
								</c:forEach>

								<c:if test="${empty top3List}">
									<tr>
										<td colspan="4">폐기 데이터가 없습니다.</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>

				<div class="disposal_ratio">
					<div>폐기사유 비율</div>
					<div class="chart_box">
						<canvas id="reasonChart"></canvas>
					</div>
				</div>

				<div class="disposal_solid_liquid">
					<div>날짜별 폐기량</div>
					<div class="chart_box">
						<canvas id="dailyDisposalChart"></canvas>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="application/json" id="dailyLabelsJson">${dailyLabelsJson}</script>
	<script type="application/json" id="dailyDatasetsJson">${dailyDatasetsJson}</script>
	<script type="application/json" id="reasonLabelsJson">${reasonLabelsJson}</script>
	<script type="application/json" id="reasonValuesJson">${reasonValuesJson}</script>

	<script>
	function readJson(id) {
		const text = document.getElementById(id).textContent.trim();
		return JSON.parse(text || "[]");
	}

	const dailyLabels = readJson("dailyLabelsJson");
	const dailyDatasets = readJson("dailyDatasetsJson");
	const reasonLabels = readJson("reasonLabelsJson");
	const reasonValues = readJson("reasonValuesJson");

	new Chart(document.querySelector("#dailyDisposalChart"), {
		type: "line",
		data: {
			labels: dailyLabels,
			datasets: dailyDatasets
		}
	});

	new Chart(document.querySelector("#reasonChart"), {
		type: "doughnut",
		data: {
			labels: reasonLabels,
			datasets: [{
				data: reasonValues
			}]
		}
	});
	</script>
</body>

</html>
