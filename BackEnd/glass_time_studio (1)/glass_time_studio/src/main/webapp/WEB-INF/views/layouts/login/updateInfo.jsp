<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>마이 페이지 수정</title>
    <link rel="stylesheet" href="/css/background_black.css">
    <style>
    fieldset{
      width: 40%;
      margin: auto;
    }
    </style>
</head>
<body>
  <%@ include file="../../navigation.jsp" %>
    <form action="/member/update" method="post">
        <fieldset>
            <legend>정보 수정</legend>
            <p>이름: ${param.name}</p>
            <p>이메일: <input type="email" name="email" id="email" value="${param.email}"></p>
            <p>휴대폰 번호: <input type="text" name="mobile" id="mobile" value="${param.mobile}"></p>
            <p>생일: ${param.birthday}</p>
            <input type="hidden" name="memberId" id="memberId" value="${param.memberId}">
            <div style="text-align: right;">
              <button type="submit" onClick="alertMessage();">저장하기</button>
            </div>
        </fieldset>
    </form>


    <script>
        function alertMessage() {
            alert("변경사항을 적용하였습니다.");
        }
    </script>
</body>
</html>