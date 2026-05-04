<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>지출통계</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<style>
ul {
	list-style: none;
}

.container {
	display: flex;
	gap: 30px;
	
	min-height: 100vh;
	height: auto;
}

.sideMenu {
	height: 100vh;
	width: 300px;
}

.main {
	
	width: calc(100% - 330px);
	min-width: 0;
	padding: 20px;
	overflow-x: hidden;
}

.main > table {
	margin-top: 20px;
}

.profile {
	display: flex;
	justify-content: end;
}

.top_area {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 20px;
}

.summary {
	margin-bottom: 20px;
	padding: 14px 16px;
	font-size: 20px;
	font-weight: bold;
	
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

.chart_area {
	display: flex;
	gap: 24px;
	width: 100%;
	padding: 16px;
	box-sizing: border-box;
	margin-bottom: 24px;
	background-color: #f8fafc;
	border: 1px solid #e2e8f0;
	border-radius: 6px;
}

.chart_box {
	width: 50%;
	height: 320px;
	padding: 14px;
	box-sizing: border-box;
	background-color: white;
	border: 1px solid #cbd5e1;
	border-radius: 6px;
}

.chart_box h3 {
	margin-top: 0;
	padding-bottom: 8px;
	border-bottom: 1px solid #e2e8f0;
}

.chart_box canvas {
	width: 100% !important;
	height: 260px !important;
}

table {
	width: 100%;
	border-collapse: collapse;
	border-spacing: 0;
	margin-top: 20px;
	background-color: white;
	font-size: 14px;
	line-height: 1.4;
}

th,
td {
	border: 1px solid #cbd5e1;
	padding: 10px 12px;
	text-align: center;
	vertical-align: middle;
}

th {
	background-color: #f1f5f9;
	font-weight: bold;
	color: #1e293b;
}

tbody tr:hover {
	background-color: #f8fafc;
}
</style>
</head>
<body>
	<div class="container">
		<section >
			<jsp:include page="../common/sideMenu.jsp" />
		</section>

		<div class="main">
			<jsp:include page="../common/userName.jsp" />

			<div class="top_area">
				<h1>지출통계</h1>

				<form method="get"
					action="${pageContext.request.contextPath}/controller">
					<input type="hidden" name="cmd" value="usedStatisticsUIAction">
					<input type="month" name="month" value="${selectedMonth}">
					<button type="submit">조회</button>
				</form>
			</div>

			<div class="summary">
				${selectedMonth} 총 지출액:
				<fmt:formatNumber value="${totalExpense}" pattern="#,###" />
				원
			</div>

			<div class="chart_area">
				<div class="chart_box">
					<h3>식자재 지출 순위</h3>
					<canvas id="rankChart"></canvas>
				</div>

				<div class="chart_box">
					<h3>최근 6개월 총 지출액</h3>
					<canvas id="monthlyChart"></canvas>
				</div>
			</div>

			<table>
				<thead>
					<tr>
						<th>순위</th>
						<th>식자재명</th>
						<th>평균 단가</th>
						<th>수량</th>
						<th>총 지출액</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${rankList}">
						<tr>
							<td>${item.ranking}</td>
							<td>${item.foodMaterialName}</td>
							<td><fmt:formatNumber value="${item.foodMaterialPrice}"
									pattern="#,###" />원</td>
							<td>${item.foodMaterialCount}</td>
							<td><fmt:formatNumber value="${item.totalExpense}"
									pattern="#,###" />원</td>
						</tr>
					</c:forEach>

					<c:if test="${empty rankList}">
						<tr>
							<td colspan="5">지출 데이터가 없습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>

	<script type="application/json" id="rankLabelsJson">${rankLabelsJson}</script>
	<script type="application/json" id="rankValuesJson">${rankValuesJson}</script>
	<script type="application/json" id="monthlyLabelsJson">${monthlyLabelsJson}</script>
	<script type="application/json" id="monthlyValuesJson">${monthlyValuesJson}</script>

	<script>
		function readJson(id) {
			var text = document.getElementById(id).textContent.trim();
			return JSON.parse(text || "[]");
		}

		var rankLabels = readJson("rankLabelsJson");
		var rankValues = readJson("rankValuesJson");
		var monthlyLabels = readJson("monthlyLabelsJson");
		var monthlyValues = readJson("monthlyValuesJson");

		new Chart(document.getElementById("rankChart"), {
			type : "bar",
			data : {
				labels : rankLabels,
				datasets : [ {
					label : "지출액",
					data : rankValues,
					backgroundColor : "#2563eb"
				} ]
			},
			options : {
				indexAxis : "y",
				responsive : true,
				maintainAspectRatio : false
			}
		});

		new Chart(document.getElementById("monthlyChart"), {
			type : "bar",
			data : {
				labels : monthlyLabels,
				datasets : [ {
					label : "월별 총 지출액",
					data : monthlyValues,
					backgroundColor : "#16a34a"
				} ]
			},
			options : {
				responsive : true,
				maintainAspectRatio : false
			}
		});
	</script>
</body>
</html>
