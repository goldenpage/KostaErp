<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>매출통계</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<style>
ul {
	list-style: none;
}

.container {
	display: flex;
	gap: 30px;
	min-height: 100vh;
}

.sideMenu {
	height: 100vh;

	width: 300px;
}

.main {
	border: 1px solid;
	width: calc(100% - 330px);
	min-width: 0;
	padding: 20px;
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
	font-size: 20px;
	font-weight: bold;
}

.chart_area {
	display: flex;
	gap: 24px;
	width: 100%;
	margin-bottom: 24px;
}

.chart_box {
	width: 50%;
	height: 320px;
}

.chart_box canvas {
	width: 100% !important;
	height: 260px !important;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 10px;
}

th, td {
	border: 1px solid #999;
	padding: 8px 12px;
	text-align: center;
}

th {
	background-color: #f3f4f6;
}

button {
	cursor: pointer;
}



.chart_area {
	padding: 16px;
	box-sizing: border-box;
	background-color: #f8fafc;
	border: 1px solid #e2e8f0;
	border-radius: 6px;
}

.chart_box {
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

.main > table {
	margin-top: 20px;
	background-color: white;
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
				<h1>매출통계</h1>

				<form method="get"
					action="${pageContext.request.contextPath}/controller">
					<input type="hidden" name="cmd" value="revenueStatisticsUIAction">
					<input type="month" name="month" value="${selectedMonth}">
					<button type="submit">조회</button>
				</form>
			</div>

			<div class="summary">
				${selectedMonth} 총 매출:
				<fmt:formatNumber value="${totalRevenue}" pattern="#,###" />
				원
			</div>

			<div class="chart_area">
				<div class="chart_box">
					<h3>메뉴별 매출 순위</h3>
					<canvas id="rankChart"></canvas>
				</div>

				<div class="chart_box">
					<h3>최근 6개월 매출</h3>
					<canvas id="monthlyChart"></canvas>
				</div>
			</div>

			<table>
				<thead>
					<tr>
						<th>순위</th>
						<th>메뉴명</th>
						<th>단가</th>
						<th>판매수량</th>
						<th>총매출</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="item" items="${rankList}">
						<tr>
							<td>${item.ranking}</td>
							<td>${item.menuName}</td>
							<td>
								<fmt:formatNumber value="${item.menuPrice}" pattern="#,###" />
								원
							</td>
							<td>${item.totalSaleCount}</td>
							<td>
								<fmt:formatNumber value="${item.totalSalesAmount}" pattern="#,###" />
								원
							</td>
						</tr>
					</c:forEach>

					<c:if test="${empty rankList}">
						<tr>
							<td colspan="5">매출 데이터가 없습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>

	<script type="application/json" id="rankLabelsJson">${rankLabelsJson}</script>
	<script type="application/json" id="rankSalesAmountJson">${rankSalesAmountJson}</script>
	<script type="application/json" id="rankSaleCountJson">${rankSaleCountJson}</script>
	<script type="application/json" id="monthlyLabelsJson">${monthlyLabelsJson}</script>
	<script type="application/json" id="monthlyRevenueJson">${monthlyRevenueJson}</script>

	<script>
		function readJson(id) {
			var text = document.getElementById(id).textContent.trim();
			return JSON.parse(text || "[]");
		}

		function won(value) {
			return Number(value).toLocaleString() + "원";
		}

		var rankLabels = readJson("rankLabelsJson");
		var rankSalesAmount = readJson("rankSalesAmountJson");
		var rankSaleCount = readJson("rankSaleCountJson");
		var monthlyLabels = readJson("monthlyLabelsJson");
		var monthlyRevenue = readJson("monthlyRevenueJson");

		new Chart(document.getElementById("rankChart"), {
			type : "bar",
			data : {
				labels : rankLabels,
				datasets : [ {
					label : "매출",
					data : rankSalesAmount,
					backgroundColor : "#2563eb"
				} ]
			},
			options : {
				indexAxis : "y",
				responsive : true,
				maintainAspectRatio : false,
				plugins : {
					legend : {
						display : false
					},
					tooltip : {
						callbacks : {
							label : function(context) {
								var index = context.dataIndex;
								return "매출: " + won(context.raw) + " / 판매수량: "
										+ rankSaleCount[index];
							}
						}
					}
				},
				scales : {
					x : {
						ticks : {
							callback : function(value) {
								return won(value);
							}
						}
					}
				}
			}
		});

		new Chart(document.getElementById("monthlyChart"), {
			type : "bar",
			data : {
				labels : monthlyLabels,
				datasets : [ {
					label : "월별 매출",
					data : monthlyRevenue,
					backgroundColor : "#16a34a"
				} ]
			},
			options : {
				responsive : true,
				maintainAspectRatio : false,
				plugins : {
					legend : {
						display : false
					},
					tooltip : {
						callbacks : {
							label : function(context) {
								return "매출: " + won(context.raw);
							}
						}
					}
				},
				scales : {
					y : {
						ticks : {
							callback : function(value) {
								return won(value);
							}
						}
					}
				}
			}
		});
	</script>
</body>
</html>
