<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 변경</title>
    <style>
        /* 1. 바디 초기화 (동일 톤앤매너) */
        body {
            font-family: 'Malgun Gothic', dotum, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
        }

        /* 2. 비밀번호 변경 박스 */
        .change-box {
            max-width: 450px; 
            margin: 100px auto; 
            background-color: #ffffff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            box-sizing: border-box;
        }

        .main-title {
            text-align: center;
            margin-bottom: 20px;
            margin-top: 0;
        }

        /* 안내 문구 */
        .description {
            text-align: center; 
            font-size: 13px; 
            color: #666; 
            margin-bottom: 30px;
            line-height: 1.5;
        }

        /* 3. 입력 구역 간격 및 제목 */
        .form-group {
            margin-bottom: 20px;
        }

        .section-title {
            font-weight: bold;
            margin-bottom: 8px;
            font-size: 15px;
        }

        /* 4. 입력칸 클래스 */
        .inputbutton {
            width: 100%;
            padding: 12px;
            border: 1px solid #cccccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
        }

        /* 5. 변경 완료 버튼 클래스 */
        .submitbutton {
            width: 100%;
            padding: 15px;
            margin-top: 10px;
            background-color: #333333;
            color: #ffffff;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .submitbutton:hover {
            background-color: #000000;
        }

        /* 비밀번호 보안 안내 텍스트 */
        .password-hint {
            font-size: 12px;
            color: #888;
            margin-top: 5px;
        }
    </style>
</head>
<body>

    <form action="" class="change-box">
        <h1 class="main-title">비밀번호 변경</h1>

        <p class="description">
            안전한 계정 사용을 위해 <br>
            새로운 비밀번호를 설정해 주세요.
        </p>

        <div class="form-group">
            <div class="section-title">새 비밀번호</div>
            <input type="password" class="inputbutton" placeholder="새 비밀번호를 입력하세요" required>
            <div class="password-hint">※ 영문, 숫자, 특수문자 혼합 8~16자</div>
        </div>

        <div class="form-group">
            <div class="section-title">새 비밀번호 확인</div>
            <input type="password" class="inputbutton" placeholder="새 비밀번호를 한 번 더 입력하세요" required>
        </div>

        <button type="submit" class="submitbutton">비밀번호 변경 완료</button>

    </form>

</body>
</html>