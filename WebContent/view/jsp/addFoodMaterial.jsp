<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>식자재 입력</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/addFoodMaterial.css">
</head>
<body>
    <div class="container">
        <section class="sideMenu">
          <jsp:include page="../common/sideMenu.jsp" />
        </section>

        <div class="main">
        <form method="post" action="${pageContext.request.contextPath}/controller?cmd=addFoodMaterialAction" class="addFood">
            <div>
                <jsp:include page="../common/userName.jsp" />
            </div>
            <h1>식자재 입력</h1>
            <c:if test="${not empty errorMessage}">
                <div style="color:red; margin: 0 10px;">${errorMessage}</div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div style="color:green; margin: 0 10px;">${successMessage}</div>
            </c:if>
            <div class="content_item">

                <div class="content_left">
                    <div class="input_section">

                        <div class="input_row">
                            
                            <div class="category_buttons">
                                <label>카테고리 추가</label>
                                <input type="text" id="getfoodCategory" name="foodCategory" placeholder="카테고리 입력">
                                <button type="button" onclick="addCategoryAjax()">추가하기</button>
                            </div>
                            
                        </div>

                        <div class="input_row">
                            <div class="category_buttons" id="categoryArea">
                                <c:forEach var="category" items="${categoryList}">
                                    <span style="display:inline-flex; align-items:center; gap:2px;">
                                        <span style="display:inline-flex; align-items:center; gap:2px;">
                                        	<button type="button" onclick="selectCategory(this)" data-category-id="${category.foodCategoryId}">${category.foodCategory}</button>
                                        	<button type="button" class="remove_btn" onclick="deleteCategoryAjax('${category.foodCategory}', this)">&#10005;</button>
                                    	</span>
                                    </span>
                                </c:forEach>
                            </div>
                        </div>
                        <div id="categoryMsg" style="font-size:13px; margin-bottom:8px;"></div>

                        
                        <input type="hidden" id="selectedCategoryId" value="">

                        <div class="input_row">
                            <label>식자재명 입력 *</label>
                            <input id="foodMaterialName" placeholder="단무지">
                        </div>

                        <div class="input_fields">
                            <label>전체수량 *</label>
                            <input type="number" id="foodMaterialCount" placeholder="5" min="0">

                            <label>전체수량(단위:g) *</label>
                            <input type="number" id="foodMaterialCountAll" placeholder="1500" min="0">
                            <select id="inputUnit">
                                <option value="g">g</option>
                                <option value="kg">kg</option>
                                <option value="ml">ml</option>
                                <option value="L">L</option>
                            </select>
                        </div>

                        <div class="input_fields">
                            <label>가격 *</label>
                            <input type="number" id="foodMaterialPrice" placeholder="10000" min="0">

                            <label>타입 *</label>
                            <select id="foodMaterialType">
							    <option value="">선택</option>
							    <option value="고체">고체</option>
							    <option value="액체">액체</option>
							    <option value="기타">기타</option>
							</select>

                            <label>구입처 *</label>
                            <input type="text" id="vender" placeholder="하나로마트">
                        </div>

                        <div class="input_fields">
                            <label>매입일자</label>
                            <input type="date" id="incomeDate">
                            <span class="auto_date_hint">※ 미입력 시 오늘 날짜 자동 설정</span>

                            <label>유통기한 *</label>
                            <input type="date" id="expirationDate">
                            <span>&#10003;</span>
                        </div>

                        <div class="register_btn">
                            <button type="button" onclick="addToList()">추가</button>
                        </div>
                    </div>

                    <div class="search_section">
                        <h3>기존 식자재 찾기</h3>
                        <div class="search_row">
                            <label>검색</label>
                            <input type="text" id="searchInput" placeholder="단무지"
                                onkeydown="if(event.key==='Enter') searchMaterial()">
                            <button type="button" onclick="searchMaterial()">검색</button>
                        </div>
                        <div class="search_row">
                            <span>기존에 등록된 식자재 목록</span>
                        </div>
                        <div>
                            <table class="list_container">
                                <thead>
                                    <tr class="list_item">
                                        <th>이름</th>
                                        <th>카테고리</th>
                                        <th>구입처</th>
                                        <th>타입</th>
                                        <th>추가</th>
                                    </tr>
                                </thead>
                                <tbody id="searchResultBody">
                                    <tr><td colspan="5" class="empty_msg">검색어를 입력하세요</td></tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="content_right">
                    <h3>등록할 식자재 목록</h3>
                    <table id="registerTable">
                        <thead>
                            <tr>
                                <th>식자재명</th>
                                <th>카테고리</th>
                                <th>수량</th>
                                <th>용량</th>
                                <th>가격</th>
                                <th>삭제</th>
                            </tr>
                        </thead>
                        <tbody id="registerBody">
                            <tr><td colspan="6" class="empty_msg">추가된 식자재가 없습니다</td></tr>
                        </tbody>
                    </table>
                    <div id="hiddenFields"></div>
                    <div class="register_final_btn">
                        <button type="submit" class="submitbutton" onclick="return registerAll()">식자재 등록</button>
                    </div>
                </div>

            </div>
            </form>
        </div>
    </div>
    <script>
        var contextPath = '${pageContext.request.contextPath}';
    </script>
    <script src="${pageContext.request.contextPath}/view/js/addFoodMaterial.js"></script>
</body>
</html>