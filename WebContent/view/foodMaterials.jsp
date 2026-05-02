<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty currentPage}">
	<c:set var="currentPage" value="1" />
</c:if>
<c:if test="${empty totalPage}">
	<c:set var="totalPage" value="1" />
</c:if>
<c:if test="${empty sortType}">
	<c:set var="sortType" value="idDesc" />
</c:if>

<!DOCTYPE html>
<html lang="kr">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>식자재 조회</title>

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
			<ul>
				<li>식자재관리</li>
				<li>식자재입력</li>
				<li>메뉴입력</li>
				<li><a
					href="${pageContext.request.contextPath}/controller?cmd=foodMaterialAction">
						식자재조회 </a></li>
				<li><a
					href="${pageContext.request.contextPath}/controller?cmd=menuAction">
						메뉴상세조회 </a></li>
			</ul>

			<ul>
				<li>폐기관리</li>
				<li>폐기품목확인</li>
			</ul>

			<ul>
				<li>통계</li>
				<li>매출통계</li>
				<li>지출통계</li>
				<li>폐기통계</li>
			</ul>
		</section>

		<div class="main">
			<div>
				<ul class="profile">
					<td>김상혁님</td>
					<td>알림</td>
				</ul>
			</div>

			<h1>식자재 확인</h1>

			<div class="content_item">
				<div>
					<ul class="category">
						<li><input type="text" id="searchKeyword" placeholder="검색">
							<button type="button" id="searchBtn">검색하기</button></li>

						<select id="categorySelect">
							<option value="">전체</option>
							<option value="채소">채소</option>
							<option value="정육">정육</option>
							<option value="유제품">유제품</option>
							<option value="가공식품">가공식품</option>
							<option value="음료/주류">음료/주류</option>
							<option value="양념">양념</option>
							<option value="곡류">곡류</option>
							<option value="해산물">해산물</option>
						</select>


						<!-- <button type="button" id="resetBtn">초기화</button> -->
					</ul>
				</div>

				<div>
					<form method="post"
						action="${pageContext.request.contextPath}/controller?cmd=deleteFoodMaterialAction">

						<table class="list_container">

							<tr class="list_item">
								<th><c:choose>
										<c:when test="${sortType == 'idAsc'}">
											<a
												href="${pageContext.request.contextPath}/controller?cmd=foodMaterialAction&sortType=idDesc&page=1">
												번호 ▲ </a>
										</c:when>
										<c:otherwise>
											<a
												href="${pageContext.request.contextPath}/controller?cmd=foodMaterialAction&sortType=idAsc&page=1">
												번호 ▼ </a>
										</c:otherwise>
									</c:choose></th>

								<th>식자재명</th>
								<th>카테고리</th>
								<th>전체수량</th>
								<th>전체용량(단위:g)</th>
								<th>매입가격(단위:원)</th>
								<th>구입처</th>
								<th>매입날짜</th>

								<th><c:choose>
										<c:when test="${sortType == 'expAsc'}">
											<a
												href="${pageContext.request.contextPath}/controller?cmd=foodMaterialAction&sortType=expDesc&page=1">
												유통기한 ▲ </a>
										</c:when>
										<c:otherwise>
											<a
												href="${pageContext.request.contextPath}/controller?cmd=foodMaterialAction&sortType=expAsc&page=1">
												유통기한 ▼ </a>
										</c:otherwise>
									</c:choose></th>

								<th>품목유형</th>
								<th>폐기수정</th>
								<th>
									<button type="button" id="selectAllBtn">전체선택</button>
								</th>
								<th>
									<button type="submit"
										onclick="return confirm('선택한 식자재를 삭제하시겠습니까?');">삭제하기
									</button>
								</th>
							</tr>

							<c:forEach items="${foodList}" var="food" varStatus="status">
								<tr class="foodRow">
									<td>${food.foodMaterialId}</td>
									<td>${food.foodMaterialName}</td>
									<td>${food.foodCategory}</td>
									<td>${food.foodMaterialCount}</td>
									<td>${food.foodMaterialCountAll}</td>
									<td>${food.foodMaterialPrice}</td>
									<td>${food.vender}</td>
									<td>${food.incomeDate}</td>
									<td>${food.expirationDate}</td>
									<td>${food.foodMaterialType}</td>
									<td>폐기수정</td>
									<td><input type="checkbox" name="foodMaterialId"
										value="${food.foodMaterialId}"></td>
									<td>선택 후 삭제</td>
								</tr>
							</c:forEach>

							<c:if test="${empty foodList}">
								<tr class="emptyRow">
									<td colspan="13">조회된 식자재가 없습니다.</td>
								</tr>
							</c:if>
						</table>
					</form>

					<div>
						<ul class="page">
							<c:if test="${currentPage > 1}">
								<li><a
									href="${pageContext.request.contextPath}/controller?cmd=foodMaterialAction&sortType=${sortType}&page=${currentPage - 1}">
										&lt; </a></li>
							</c:if>

							<c:forEach begin="1" end="${totalPage}" var="i">
								<li><a
									href="${pageContext.request.contextPath}/controller?cmd=foodMaterialAction&sortType=${sortType}&page=${i}">
										<c:choose>
											<c:when test="${i == currentPage}">
												<strong>${i}</strong>
											</c:when>
											<c:otherwise>
            ${i}
           </c:otherwise>
										</c:choose>
								</a></li>
							</c:forEach>

							<c:if test="${currentPage < totalPage}">
								<li><a
									href="${pageContext.request.contextPath}/controller?cmd=foodMaterialAction&sortType=${sortType}&page=${currentPage + 1}">
										&gt; </a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

<script>
	window.onload = function() {
		document.getElementById("searchBtn").onclick = function() {
			searchFood();
		};

		document.getElementById("categorySelect").onchange = function() {
			categoryAjax();
		};

		document.getElementById("selectAllBtn").onclick = function() {
			selectAllFoodMaterial();
		};

		document.getElementById("searchKeyword").onkeydown = function(event) {
			if (event.key === "Enter") {
				searchFood();
			}
		};
	};

	function searchFood() {
		var keyword = document.getElementById("searchKeyword").value.trim();
		var category = document.getElementById("categorySelect").value;
		var rows = document.querySelectorAll(".foodRow");

		rows.forEach(function(row) {
			var cells = row.querySelectorAll("td");

			var foodName = cells[1].textContent.trim();
			var foodCategory = cells[2].textContent.trim();

			var keywordOk = true;
			var categoryOk = true;

			if (keyword !== "") {
				keywordOk = foodName.indexOf(keyword) !== -1;
			}

			if (category !== "") {
				categoryOk = foodCategory === category;
			}

			if (keywordOk && categoryOk) {
				row.style.display = "";
			} else {
				row.style.display = "none";
			}
		});
	}

	function categoryAjax() {
		searchFood();

		var xhr = new XMLHttpRequest();

		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				console.log("카테고리 비동기 요청 완료");
			}
		};

		xhr.open("GET", "${pageContext.request.contextPath}/controller?cmd=foodMaterialAction&sortType=${sortType}&page=${currentPage}", true);
		xhr.send();
	}

	function selectAllFoodMaterial() {
		var checkboxes = document.querySelectorAll("input[name='foodMaterialId']");
		var allChecked = true;

		checkboxes.forEach(function(checkbox) {
			var row = checkbox.closest("tr");

			if (row.style.display !== "none" && checkbox.checked == false) {
				allChecked = false;
			}
		});

		checkboxes.forEach(function(checkbox) {
			var row = checkbox.closest("tr");

			if (row.style.display !== "none") {
				checkbox.checked = !allChecked;
			}
		});
	}
</script>

</body>
</html>