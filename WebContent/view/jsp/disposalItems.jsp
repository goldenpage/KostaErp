<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- [AJAX 요청 분기 처리] --%>
<c:if test="${param.ajax eq 'true'}">
	<table id="ajaxTableData">
		<tbody>
			<c:forEach var="d" items="${list}">
				<tr>
					<td class="center">${fn:substring(d.disposalId, 3, 6)}</td>
					<td class="center">${d.foodMaterialName}</td>
					<td class="center">${d.foodCategory}</td>
					<td class="center">${d.foodMaterialType}</td>
					<td class="right">${d.disposalCountAll}</td>
					<td class="right"><fmt:formatNumber value="${d.disposalPrice}" /></td>
					<td class="center">${d.disposalDate}</td>
					<td class="actionCell">
						<div
							style="display: flex; gap: 5px; align-items: center; justify-content: center;">

							<select id="reason_${d.disposalId}">
								<option value="D" ${d.reason eq '변질' ? 'selected' : ''}>변질</option>
								<option value="B" ${d.reason eq '파손' ? 'selected' : ''}>파손</option>
								<option value="E" ${d.reason eq '유통기한만료' ? 'selected' : ''}>유통기한지남</option>
								<option value="BETC" ${d.reason eq '기타' ? 'selected' : ''}>기타</option>
							</select>
							<button type="button" onclick="updateReason('${d.disposalId}')">확인</button>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div id="ajaxPagingData">
		<c:if test="${currentPage > 1}">
			<a href="javascript:void(0);" onclick="loadData(${currentPage - 1})">이전</a>
		</c:if>
		<c:forEach var="i" begin="1" end="${totalPages}">
			<a href="javascript:void(0);" onclick="loadData(${i})"
				class="${i == currentPage ? 'active' : ''}">${i}</a>
		</c:forEach>
		<c:if test="${currentPage < totalPages}">
			<a href="javascript:void(0);" onclick="loadData(${currentPage + 1})">다음</a>
		</c:if>
	</div>

	<% if("true".equals(request.getParameter("ajax"))) return; %>
</c:if>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>폐기품목 관리</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/disposalItems.css">

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
			<h2>폐기품목 확인</h2>
			<div class="content_item">
				<ul class="category">
					<li>카테고리</li>
					<li><select id="categoryFilter" onchange="loadData(1)">
							<option>전체</option>
							<option>채소</option>
							<option>정육</option>
							<option>가공식품</option>
							<option>유제품</option>
							<option>양념</option>
					</select></li>
					<li>사유</li>
					<li><select id="reasonFilter" onchange="loadData(1)">
							<option>전체</option>
							<option>변질</option>
							<option>파손</option>
							<option>유통기한만료</option>
							<option>기타</option>
					</select></li>
					<li><button onclick="resetFilter()">초기화</button></li>
				</ul>

				<table class="list_container">
					<thead>
						<tr>
							<th>번호
								<button type="button" onclick="sortTable(0, this)">▼</button>
							</th>
							<th>식자재명
								<button type="button" onclick="sortTable(1, this)">▼</button>
							</th>
							<th>카테고리
								<button type="button" onclick="sortTable(2, this)">▼</button>
							</th>
							<th>유형
								<button type="button" onclick="sortTable(3, this)">▼</button>
							</th>
							<th>총폐기용량(g)
								<button type="button" onclick="sortTable(4, this)">▼</button>
							</th>
							<th>총폐기가격(원)
								<button type="button" onclick="sortTable(5, this)">▼</button>
							</th>
							<th>폐기일
								<button type="button" onclick="sortTable(6, this)">▼</button>
							</th>
							<th>사유</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="d" items="${list}">
							<tr>
								<td class="center">${fn:substring(d.disposalId,3,6)}</td>
								<td class="center">${d.foodMaterialName}</td>
								<td class="center">${d.foodCategory}</td>
								<td class="center">${d.foodMaterialType}</td>
								<td class="right">${d.disposalCountAll}</td>
								<td class="right"><fmt:formatNumber
										value="${d.disposalPrice}" /></td>
								<td class="center">${d.disposalDate}</td>
								<td class="actionCell">
									<div
										style="display: flex; gap: 5px; align-items: center; justify-content: center;">
										<select id="reason_${d.disposalId}">
											<option value="D" ${d.reason eq '변질' ? 'selected' : ''}>변질</option>
											<option value="B" ${d.reason eq '파손' ? 'selected' : ''}>파손</option>
											<option value="E" ${d.reason eq '유통기한만료' ? 'selected' : ''}>유통기한지남</option>
											<option value="BETC" ${d.reason eq '기타' ? 'selected' : ''}>기타</option>
										</select>
										<button type="button"
											onclick="updateReason('${d.disposalId}')">확인</button>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<div class="page">
					<c:if test="${currentPage > 1}">
						<a href="javascript:void(0);"
							onclick="loadData(${currentPage - 1})">이전</a>
					</c:if>
					<c:forEach var="i" begin="1" end="${totalPages}">
						<a href="javascript:void(0);" onclick="loadData(${i})"
							class="${i == currentPage ? 'active' : ''}">${i}</a>
					</c:forEach>
					<c:if test="${currentPage < totalPages}">
						<a href="javascript:void(0);"
							onclick="loadData(${currentPage + 1})">다음</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/view/js/disposalItems.js"></script>
</body>
</html>