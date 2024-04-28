<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>마이 페이지</title>
    <link rel="stylesheet" href="/css/background_black.css">
    <style>
    fieldset{
      width: 40%;
      margin: auto;
      text-align: left;
    }

    #button{
      border: 1px solid white;
      padding-left: 3px;
      padding-right: 3px;
      display: inline-block;
      margin-left: auto;
    }
    </style>
</head>
<body>
  <%@ include file="../../navigation.jsp" %>

    <fieldset>
        <legend>내 정보 마이페이지</legend>
        <p>이름: ${member.getMemberName()}</p>
        <p>이메일: ${member.getEmail()}</p>
        <p>휴대폰 번호: ${member.getMobile()}</p>
        <p>생일: ${member.getBirthday()}</p>
        <input type="hidden" name="memberId" id="memberId" value="${member.getMemberId()}">
        <div style="text-align: right;">
          <a id="button" href="/updateInfo?name=${member.getMemberName()}&email=${member.getEmail()}&mobile=${member.getMobile()}&birthday=${member.getBirthday()}&memberId=${member.getMemberId()}">수정하기</a>
        </div>
    </fieldset>
</body>
</html>