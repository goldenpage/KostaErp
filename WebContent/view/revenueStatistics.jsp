<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
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
	border: 3px solid blue;
	height: 100%;
	align-items: center;
}

.category {
	display: flex;
	width: 75%;
	gap: 16px;
	align-items: center;
	border: 3px solid;
}

.list_container {
	display: flex;
	justify-content: center;
	height: 100%;
	border: 3px solid red;
	font-size: 14px;
	line-height: 14px;
}

table {
	border-spacing: 24px;
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
		<section class="sideMenu"> <jsp:include
			page="common/sideMenu.jsp" /> </section>
		<div class="main">
			<div>
				<jsp:include page="common/userName.jsp" />
			</div>

			<div>매출순위</div>
			<div>
				<canvas id = "revenueChart"></canvas>
			</div>

		</div>
	</div>
	<script>
	const revenueData = {
		    labels: ["참치김밥", "김밥", "땡초김밥"],
		    datasets: [{
		    	label: "매출",
		        data: [15000, 20000, 30000]
		    }]
		};

		new Chart(document.querySelector("#revenueChart"), {
		    type: "bar",
		    data: revenueData,
		    options: {
		    	indexAxis: 'y'
		    }
		});
	</script>
</body>
</html>