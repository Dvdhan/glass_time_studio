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
    .button{
      border: 1px solid white;
      padding-left: 3px;
      padding-right: 3px;
      display: inline-block;
      margin-left: auto;
      margin: 5px;
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
        <a class="button" href="/myBooking?memberId=${member.getMemberId()}">내 클래스 예약 살펴보기</a><br>
        <a class="button" onclick="seeMyPurchase()">구매 상품 살펴보기</a><br>
        <a class="button" href="/myAllBasket">장바구니</button></a><br>
        <a class="button" href="/updateInfo?name=${member.getMemberName()}&email=${member.getEmail()}&mobile=${member.getMobile()}&birthday=${member.getBirthday()}&memberId=${member.getMemberId()}">개인정보 수정하기</a>
        </div>
    </fieldset>

<script>
    function seeMyPurchase() {
        alert('내 구매 상품 보기');
        return false;
    }
</script>
</body>
</html>