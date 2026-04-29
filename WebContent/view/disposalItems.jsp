<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

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

        .content_item .category {
            display: flex;
            width: 75%;
            gap: 16px;
            align-items: center;
            border: 3px solid;
        }

        .content_item .list_container {
            display: flex;
            justify-content: center;
            height: 100%;
            border: 3px solid red;
            font-size: 14px;
            line-height: 14px;
        }

        .content_item table {
            border-spacing: 20px;

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
        <section class="sideMenu">

          <jsp:include page="common/sideMenu.jsp" />

        </section>
        <div class="main">
            <div>
               <jsp:include page="common/userName.jsp" />
            </div>
            <h2>폐기품목 확인</h2>
            <div class="content_item">
                <div>
                    <ul class="category">

                        <li><input type="text" placeholder="검색"> <button>검색하기</button></li>
                        <li>전체</li>
                        <select name="" id="">
                            <option value="">채소</option>
                            <option value="">정육</option>
                            <option value="">유제품</option>
                            <option value="">가공품</option>
                            <option value="">음료/주류</option>
                            <option value="">양념</option>
                            <option value="">양념</option>
                        </select>
                        <li>적용하기</li>
                    </ul>
                </div>

                <div>
                    <table class="list_container">
                        <tr class="list_item">
                            <th>번호</th>
                            <th>식자재명</th>
                            <th>카테고리</th>
                            <th>폐기량</th>
                            <th>총폐기용량(단위:g)</th>
                            <th>총폐기가격(단위:원)</th>
                            <th>구입처</th>
                            <th>매입날짜</th>
                            <th>유통기한</th>
                            <th>품목유형</th>
                            <th>폐기일</th>
                            <th>폐기사유</th>
                            <th>폐기수정</th>
                            <th>전체선택</th>
                            <th>삭제하기</th>



                        </tr>
                        <tr>
                            <td>5</td>
                            <td>돼지고기</td>
                            <td>정육</td>
                            <td>5</td>
                            <td>200g</td>
                            <td>5,000</td>
                            <td>최고정육점</td>
                            <td>2026-04-19</td>
                            <td>2026-04-27</td>
                            <td>고체</td>
                            <td>2026-04-22</td>

                            <td>
                                <select>
                                    <option value="">변질</option>
                                    <option value="">파손</option>
                                    <option value="">유통기한지남</option>
                                    <option value="">재고과다</option>
                                    <option value="">기타</option>

                                </select>
                            </td>
                            <td>폐기수정</td>
                            <td><input type="checkbox" name="select_item" id=""></td>



                        </tr>
                        <tr>
                            <td>4</td>
                            <td>단무지</td>
                            <td>가공품</td>
                            <td>50</td>
                            <td>200g</td>
                            <td>2,000</td>
                            <td>건강푸드</td>
                            <td>2026-04-19</td>
                            <td>2026-04-25</td>
                            <td>고체</td>
                            <td>2026-04-21</td>

                            <td>
                                <select>
                                    <option value="">변질</option>
                                    <option value="">파손</option>
                                    <option value="">유통기한지남</option>
                                    <option value="">재고과다</option>
                                    <option value="">기타</option>

                                </select>
                            </td>
                            <td>폐기수정</td>
                            <td><input type="checkbox" name="select_item" id=""></td>



                        </tr>
                        <tr>
                            <td>3</td>
                            <td>상추</td>
                            <td>채소</td>
                            <td>10</td>
                            <td>50g</td>
                            <td>2,000</td>
                            <td>상혁이네야채가게</td>
                            <td>2026-04-17</td>
                            <td>2026-04-25</td>
                            <td>고체</td>
                            <td>2026-04-21</td>

                            <td>
                                <select>
                                    <option value="">변질</option>
                                    <option value="">파손</option>
                                    <option value="">유통기한지남</option>
                                    <option value="">재고과다</option>
                                    <option value="">기타</option>

                                </select>
                            </td>
                            <td>폐기수정</td>
                            <td><input type="checkbox" name="select_item" id=""></td>



                        </tr>
                        <tr>
                            <td>2</td>
                            <td>김치</td>
                            <td>가공품</td>
                            <td>5</td>
                            <td>200g</td>
                            <td>4,000</td>
                            <td>건강푸드</td>
                            <td>2026-04-15</td>
                            <td>2026-04-30</td>
                            <td>고체</td>
                            <td>2026-04-20</td>

                            <td>
                                <select>
                                    <option value="">변질</option>
                                    <option value="">파손</option>
                                    <option value="">유통기한지남</option>
                                    <option value="">재고과다</option>
                                    <option value="">기타</option>

                                </select>
                            </td>
                            <td>폐기수정</td>
                            <td><input type="checkbox" name="select_item" id=""></td>



                        </tr>
                        <tr>
                            <td>1</td>
                            <td>단무지</td>
                            <td>가공품</td>
                            <td>10</td>
                            <td>200g</td>
                            <td>1,000</td>
                            <td>건강푸드</td>
                            <td>2026-04-14</td>
                            <td>2026-04-25</td>
                            <td>고체</td>
                            <td>2026-04-20</td>

                            <td>
                                <select>
                                    <option value="">변질</option>
                                    <option value="">파손</option>
                                    <option value="">유통기한지남</option>
                                    <option value="">재고과다</option>
                                    <option value="">기타</option>

                                </select>
                            </td>
                            <td>폐기수정</td>
                            <td><input type="checkbox" name="select_item" id=""></td>



                        </tr>
                    </table>

                    <div>
                        <ul class="page">
                            <li>&lt</li>
                            <li>1</li>
                            <li>2</li>
                            <li>3</li>
                            <li>4</li>
                            <li>5</li>
                            <li>&gt</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>