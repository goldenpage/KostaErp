<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>폐기품목 관리</title>

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

.sidebar {
	width: 250px;
	border: 1px solid;
	padding: 10px;
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

.content {
	width: 100%;
	padding: 20px;
}

table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	border: 1px solid black;
	padding: 8px;
	text-align: center;
}

.page {
	margin-top: 20px;
	text-align: center;
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
			<h2>폐기품목 확인</h2>

			
			<div>
				<input type="text" placeholder="검색">
				<button>검색하기</button>

				<select id="categoryFilter" onchange="onChangeCategory()">
					<option value="전체">전체</option>
					<option value="채소">채소</option>
					<option value="정육">정육</option>
					<option value="가공품">가공품</option>
					<option value="유제품">유제품</option>
					<option value="양념">양념</option>
				</select>

				<button>적용하기</button>
				<button>초기화</button>
			</div>

			<br>

			<!-- ✅ 테이블 -->
			<table>
				<thead>
					<tr>
						<th>번호</th>
						<th>식자재명</th>
						<th>카테고리</th>
						<th>유형</th>
						<th>전체용량(g)</th>
						<th>매입가격</th>
						<th>구입처</th>
						<th>매입날짜</th>
						<th>유통기한</th>
						<th>품목유형</th>
						<th>폐기수정</th>
						<th>전체선택</th>
						<th>삭제하기</th>
					</tr>
				</thead>

				<tbody id="tbody">
					<c:forEach var="d" items="${list}">
						<tr>
							<td>${fn:substring(d.disposalId,3,6)}</td>
							<td>${d.foodMaterialName}</td>
							<td>${d.foodCategory}</td>
							<td>${d.foodMaterialType}</td>
							<td>${d.disposalCountAll}g</td>
							<td><fmt:formatNumber value="${d.disposalPrice}" /></td>
							<td>${d.disposalDate}</td>
							<td>${d.reason}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<div class="page">
				<a href="controller?cmd=disposalUI&page=1">&lt;</a> <a
					href="controller?cmd=disposalUI&page=1">1</a> <a
					href="controller?cmd=disposalUI&page=2">2</a> <a
					href="controller?cmd=disposalUI&page=3">3</a> <a
					href="controller?cmd=disposalUI&page=4">4</a> <a
					href="controller?cmd=disposalUI&page=5">5</a> <a
					href="controller?cmd=disposalUI&page=2">&gt;</a>
			</div>

		</div>

	</div>
<script>
	function onChangeCategory() {

		var category = document.getElementById("categoryFilter").value;

		var xhr = new XMLHttpRequest();

		xhr.open("GET", "controller?cmd=disposalAction&category="
				+ encodeURIComponent(category), true);

		xhr.onreadystatechange = function() {

			if (xhr.readyState === 4 && xhr.status === 200) {

				var list = JSON.parse(xhr.responseText);
				renderTable(list);
			}
		};

		xhr.send();
	}

	function renderTable(list) {

		var tbody = document.getElementById("tbody");
		var html = "";

		if (list.length === 0) {
			html = "<tr><td colspan='13'>데이터 없음</td></tr>";
		} else {

			for (var i = 0; i < list.length; i++) {

				var d = list[i];

				html += "<tr>" + "<td>" + d.disposalId.substring(3, 6)
						+ "</td>" + "<td>" + d.foodMaterialName + "</td>"
						+ "<td>" + d.foodCategory + "</td>" + "<td>"
						+ (d.foodMaterialType || '-') + "</td>" + "<td>"
						+ d.disposalCountAll + "g</td>" + "<td>"
						+ Number(d.disposalPrice).toLocaleString() + "</td>"
						+ "<td>" + (d.purchasePlace || '-') + "</td>" + "<td>"
						+ (d.purchaseDate || '-') + "</td>" + "<td>"
						+ (d.expirationDate || '-') + "</td>" + "<td>"
						+ (d.foodType || '-') + "</td>" + "<td>폐기수정</td>"
						+ "<td><input type='checkbox'></td>" + "<td>삭제</td>"
						+ "</tr>";
			}
		}

		tbody.innerHTML = html;
	}
</script>
</body>
</html>