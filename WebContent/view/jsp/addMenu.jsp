<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="kr">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>메뉴 입력</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/view/css/addMenu.css">
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
			<h1>메뉴 입력</h1>
			<c:if test="${not empty errorMessage}">
				<div style="color: red; margin: 0 10px;">${errorMessage}</div>
			</c:if>
			<c:if test="${not empty successMessage}">
				<div style="color: green; margin: 0 10px;">${successMessage}</div>
			</c:if>

			<div class="content_item">
				<div class="content_left">

					<div class="input_section">
						<div class="input_row">
							<div class="category_buttons">
								<label>메뉴 카테고리 추가</label> <input type="text"
									id="getMenuCategory" placeholder="카테고리 입력">
								<button type="button" onclick="addCategoryAjax()">추가</button>
							</div>
						</div>

						<div class="input_row">
							<div class="category_buttons" id="categoryArea">
								<c:forEach var="category" items="${categoryList}">
									<span
										style="display: inline-flex; align-items: center; gap: 2px;">
										<button type="button" onclick="selectCategory(this)"
											data-category-id="${category.menuCategoryId}">${category.menuCategory}</button>
										<button type="button" class="remove_btn"
											onclick="deleteCategoryAjax('${category.menuCategory}', this)">&#10005;</button>
									</span>
								</c:forEach>
							</div>
						</div>
						<div id="categoryMsg" style="font-size: 13px; margin-bottom: 8px;"></div>
					</div>

					<form method="post"
						action="${pageContext.request.contextPath}/controller?cmd=addMenuAction"
						class="addMenu">
						<input type="hidden" id="selectedCategoryId" name="_dummy"
							value="">

						<div class="input_section">
							<div class="input_row">
								<label>메뉴명 입력 *</label> <input type="text" id="inputMenuName"
									placeholder="치즈김밥">
							</div>

							<div class="input_fields">
								<label>메뉴 가격 *</label> <input type="number" id="inputMenuPrice"
									placeholder="4000" min="0"> 원
							</div>
						</div>

						<div class="ingredient_section">
							<h3>사용 식자재 추가 *</h3>

							<div class="ingredient_add_row">
								<label>식자재</label> <select id="inputIngredientSelect">
									<option value="">-- 선택 --</option>
									<c:forEach var="fm" items="${foodMaterialList}">
										<option value="${fm.foodMaterialId}"
											data-name="${fm.foodMaterialName}">${fm.foodMaterialName}
											(${fm.foodCategory})</option>
									</c:forEach>
								</select> <label>수량</label> <input type="number"
									id="inputIngredientAmount" placeholder="50" min="1"> <span>g</span>
								<button type="button" onclick="addIngredient()">+ 추가하기</button>
							</div>

							<div class="ingredient_table">
								<table id="ingredientTable">
									<thead>
										<tr>
											<th>이름</th>
											<th>수량(g)</th>
											<th>삭제</th>
										</tr>
									</thead>
									<tbody id="ingredientBody">
										<tr>
											<td colspan="3" class="empty_msg">추가된 식자재가 없습니다</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>

						<div class="register_btn">
							<button type="button" onclick="addMenuToList()">추가</button>
						</div>

						<div id="hiddenFields"></div>
					</form>

				</div>

				<div class="content_right">
					<h3>등록할 메뉴 목록</h3>

					<div class="right_top">
						<table id="registerMenuTable" style="width: 100%">
							<thead>
								<tr>
									<th>메뉴명</th>
									<th>카테고리</th>
									<th>가격</th>
									<th>삭제</th>
								</tr>
							</thead>
							<tbody id="registerMenuBody">
								<tr>
									<td colspan="4" class="empty_msg">추가된 메뉴가 없습니다</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="right_bottom">
                        <div class="register_final_btn">
                            <button type="button" onclick="registerAllMenus()">메뉴 등록</button>
                        </div>
                        <h4>&#9660; 사용 식자재 (<span id="selectedMenuName">메뉴를 선택하세요</span>)</h4>
                        <table class="ingredient_detail_table">
                            <thead>
                                <tr>
                                    <th>이름</th>
                                    <th>수량(g)</th>
                                </tr>
                            </thead>
                            <tbody id="ingredientDetailBody">
                                <tr>
                                    <td colspan="2" class="empty_msg">-</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
				</div>
			</div>
		</div>
	</div>
	<script>
		var contextPath = '${pageContext.request.contextPath}';
	</script>
	<script src="${pageContext.request.contextPath}/view/js/addMenu.js"></script>
</body>

</html>
