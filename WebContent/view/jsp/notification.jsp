<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>알림 페이지</title>

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

button {
	cursor: pointer;
}
</style>

</head>

<body>

	<div class="container">

		<!-- 사이드 메뉴 -->
		<section class="sideMenu">
			<jsp:include page="../common/sideMenu.jsp" />
		</section>

		<!-- 메인 -->
		<div class="main">

			<div>
				<jsp:include page="../common/userName.jsp" />
			</div>

			<h2>알림 내역</h2>

			<!-- 요약 -->
			<div class="summary_box">
				<span>유통기간 경과 상품 수 : <b>${expiredCount} 개</b></span> <span>
					총폐기 재고량 : 고체 <b> <c:choose>
							<c:when test="${solidTotal >= 1000}">
								<fmt:formatNumber value="${solidTotal / 1000.0}"
									maxFractionDigits="2" /> kg
			</c:when>
							<c:otherwise>
				${solidTotal} g
			</c:otherwise>
						</c:choose>
				</b> / 액체 <b> <c:choose>
							<c:when test="${liquidTotal >= 1000}">
								<fmt:formatNumber value="${liquidTotal / 1000.0}"
									maxFractionDigits="2" /> L
			</c:when>
							<c:otherwise>
				${liquidTotal} mL
			</c:otherwise>
						</c:choose>
				</b>
				</span> <span>최대 경과일 : <b>${maxOverDay}</b>일
				</span> <span> 현재 날짜 : <b><fmt:formatDate
							value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd" /></b>
				</span>
			</div>

			<!-- 리스트 -->
			<div class="content_item">

				<table class="list_container">

					<tr>
						<th>식자재명</th>
						<th>카테고리</th>
						<th>폐기용량</th>
						<th>폐기물품유형</th>
						<th>유통기한</th>
					</tr>

					<tbody>
						<c:forEach var="n" items="${list}">
							<tr>
								<td>${n.foodMaterialName}</td>
								<td>${n.foodCategory}</td>
								<td><c:if test="${n.disposalCountAll > 0}">
									${n.disposalCountAll}
								</c:if></td>
								<td>${n.foodMaterialType}</td>
								<td><fmt:formatDate value="${n.expireDate}"
										pattern="yyyy-MM-dd" /></td>
							</tr>
						</c:forEach>
					</tbody>

				</table>

			</div>

		</div>
	</div>

</body>
</html>