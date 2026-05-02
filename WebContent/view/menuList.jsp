<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
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

.menu_list {
	border: 3px solid;
	padding: 10px;
	margin: 10px;
}

.content_right {
	width: 300px;
	border: 3px solid green;
	padding: 10px;
	margin: 10px;
}

table {
	border-spacing: 24px;
}

a {
	text-decoration: none;
	color: black;
}

.message {
	color: blue;
	font-weight: bold;
}

.errorMessage {
	color: red;
	font-weight: bold;
}

button {
	cursor: pointer;
}
</style>
</head>

<body>

	<div class="message">${message}</div>
	<div class="errorMessage">${errorMessage}</div>


	<div class="container">
		 <section class="sideMenu"> 
			<jsp:include page="common/sideMenu.jsp" />
		 </section>

		<div class="main">
			<div>
				<jsp:include page="common/userName.jsp" />
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

							<c:forEach items="${menuList}" var="menu">
								<tr>
									<td><c:choose>
											<c:when test="${menu.menuId == selectedMenuId}">
												<input type="radio" name="menuSelect" checked="checked">
											</c:when>
											<c:otherwise>
												<input type="radio" name="menuSelect">
											</c:otherwise>
										</c:choose></td>

									<td><a
										href="${pageContext.request.contextPath}/controller?cmd=menuAction&menuId=${menu.menuId}">
											${menu.menuName} </a></td>

									<td>${menu.menuPrice}</td>
								</tr>
							</c:forEach>

							<c:if test="${empty menuList}">
								<tr>
									<td colspan="3">조회된 메뉴가 없습니다.</td>
								</tr>
							</c:if>
						</table>
					</div>
				</div>

				<div class="content_right">
					<h3>사용하는 식자재</h3>

					<table id="ingredientDetail">
						<tr>
							<th>이름</th>
							<th>수량</th>
							<th>가격</th>
							<th>사용원가</th>
						</tr>

						<c:forEach items="${detailList}" var="detail">
							<tr>
								<td>${detail.foodMaterialName}</td>
								<td>${detail.usedCount}</td>
								<td>${detail.foodMaterialPrice}</td>
								<td>${detail.usedPrice}</td>
							</tr>
						</c:forEach>

						<c:if test="${empty detailList}">
							<tr>
								<td colspan="4">선택된 메뉴의 식자재 정보가 없습니다.</td>
							</tr>
						</c:if>
					</table>

					<div style="margin-top: 20px;">
						<form method="post"
							action="${pageContext.request.contextPath}/controller?cmd=salesAction">
							<input type="hidden" name="menuId" value="${selectedMenuId}">
							판매수량: <input type="number" name="saleCount" value="1" min="1">
							<input type="submit" value="판매 처리">
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		function selectMenu(rowEl, menuName, ingredients) {
			var rows = document.querySelectorAll(".menu_list tr");

			rows.forEach(function(tr) {
				tr.classList.remove("active");
			});

			rowEl.classList.add("active");

			var checkboxes = document
					.querySelectorAll(".menu_list input[type='checkbox']");

			checkboxes.forEach(function(checkbox) {
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

			ingredients.forEach(function(item) {
				var newRow = document.createElement("tr");

				newRow.innerHTML = "<td>" + item[0] + "</td>" + "<td>"
						+ item[1] + "</td>";

				table.appendChild(newRow);
			});
		}
	</script>
</body>
</html>

