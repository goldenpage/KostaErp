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
<link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/disposalStatistics.css">

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

.main table {
	width: 100%;
	border-collapse: collapse;
	border-spacing: 0;
	margin-top: 12px;
	background-color: white;
	font-size: 14px;
	line-height: 1.4;
}

.main table th, .main table td {
	border: 1px solid #cbd5e1;
	padding: 10px 12px;
	text-align: center;
	vertical-align: middle;
}

.main table th {
	background-color: #f1f5f9;
	font-weight: bold;
	color: #1e293b;
}

.main table tbody tr:hover {
	background-color: #f8fafc;
}

.main table td[colspan] {
	padding: 24px;
	color: #64748b;
}

.disposal_price table {
	min-width: 420px;
}

.profile {
	display: flex;
	justify-content: end;
}

.content_item {
	display: flex;
	gap: 16px;
	width: 100%;
	padding: 16px;
	box-sizing: border-box;
	align-items: flex-start;
	background-color: #f8fafc;
	border: 1px solid #e2e8f0;
}

.content_item>div {
	flex: 1;
	min-width: 0;
	padding: 16px;
	box-sizing: border-box;
	background-color: white;
	border: 1px solid #cbd5e1;
	border-radius: 6px;
}

.disposal_price ul {
	display: flex;
	gap: 8px;
}

.disposal_price>div:first-child, .disposal_ratio>div:first-child,
	.disposal_solid_liquid>div:first-child {
	margin-bottom: 12px;
	padding-bottom: 8px;
	border-bottom: 1px solid #e2e8f0;
	font-weight: bold;
	color: #1e293b;
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
	width: 100%;
	height: 300px;
}

canvas {
	width: 100% !important;
	height: 300px !important;
}
</style>
</head>

<body>
	<div class="container">
		<section>
			<jsp:include page="../common/sideMenu.jsp" />
		</section>

		<div class="main">
			<div>
				<jsp:include page="../common/userName.jsp" />
			</div>

			<div class="top_area">
				<h1>폐기통계</h1>

				<form method="get"
					action="${pageContext.request.contextPath}/controller">
					<input type="hidden" name="cmd" value="disposalStatisticUIAction">
					<input type="month" name="month" value="${selectedMonth}">
					<button type="submit">조회</button>
				</form>
			</div>

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
										<td><fmt:formatNumber value="${item.totalDisposalPrice}"
												pattern="#,###" /> 원</td>
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
			const
			text = document.getElementById(id).textContent.trim();
			return JSON.parse(text || "[]");
		}

		const
		dailyLabels = readJson("dailyLabelsJson");
		const
		dailyDatasets = readJson("dailyDatasetsJson");
		const
		reasonLabels = readJson("reasonLabelsJson");
		const
		reasonValues = readJson("reasonValuesJson");

		new Chart(document.querySelector("#dailyDisposalChart"), {
			type : "line",
			data : {
				labels : dailyLabels,
				datasets : dailyDatasets
			}
		});

		new Chart(document.querySelector("#reasonChart"), {
			type : "doughnut",
			data : {
				labels : reasonLabels,
				datasets : [ {
					data : reasonValues
				} ]
			}
		});
	</script>
</body>

</html>
