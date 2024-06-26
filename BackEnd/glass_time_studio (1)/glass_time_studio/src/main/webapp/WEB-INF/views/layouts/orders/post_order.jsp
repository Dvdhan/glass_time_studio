<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="David.glass_time_studio.domain.product.entity.Product" %>
<%@ page import="David.glass_time_studio.domain.member.entity.Member" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>제품 구매 페이지</title>
  <link rel="stylesheet" href="/css/background_black.css">

  <style>
    .head_text{
      font-size: 0.8em;
    }
    fieldset{
      position: relative;
      margin: auto;
      text-align: left;
      width: 32em;
    }
    #logo2{
      position: absolute;
      right: 0;
      bottom: 0;
      padding-right: 10px;
      padding-bottom: 10px;
    }
    #progress{
      border-collapse: collapse;
      border: 1px solid white;
      margin: auto;
    }
    #progress td, th{
      border: 1px solid white;
      padding: 0 10px;
      text-align: left;
    }
    .class_attribute{
        width: 25em;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin: auto;
        margin-top: 0.5em;
    }
    .img{
        width: 25em;
        height: 15em;
        margin: auto;
        display: block;
        margin-top: 0.5em;
    }
    button{
        margin: 0.5em;
    }
    #product_explanation{
        width: 35em;
        margin: auto;
    }
    .detail{
      color: black;
    }
    .input_area{
        width: 15em;
    }
    .clickButtons{
        cursor: pointer;
    }
  </style>
</head>
<body>
  <%@ include file="../../navigation.jsp" %>

  <div style="text-align: center;">
    <h1>글라스 제품</h1>
    <br><br><span style="font-size: 1.3em;">✔ 구매 전, 꼭 읽어주세요.</span><br><br>
    <fieldset><br>
      '유리하는 시간'의 제품과 함께 해주셔서 진심으로 감사 드립니다.<br>
      <br>
      정성스레 만들어진 '유리하는 시간'의 제품이 고객님 곁에서 온전한 상태로<br>
      오래오래 빛나길 바라는 마음으로 아래 주의사항과 안내사항을 전달합니다.<br>
      <br>
      [스테인드글라스 테품 취급 시 주의사항]<br>
      1. 조각 낸 유리를 납땜으로 결합한 상태이므로 급격한 온도변화나 순간적인 충격에 변형 또는 파손이 있을 수 있습니다.<br>
      2. 공기에 노출된 땜, 금속 부자재는 시간이 지나면서 산화현상이 일어나 변색이 생길 수 있습니다.<br>
      3. 제품에 물과의 접촉을 최소화 해주시고, 물이 닿았다면 최대한 빨리 물기를 제거해주시기 바랍니다.<br>
      4. 스테인드글라스 제품은 무연납을 사용했다 하더라도 식기류로는 절대 사용할 수 없습니다.<br>
      <br>
      [유리 핸드메이드 제품 취급 시 안내사항]<br>
      1. 같은 유리를 사용한 제품이라 하더라도 유리 공정상 생기는 색상, 무늬, 재질에 차이가 있습니다.<br>
      2. 같은 제품이라 하더라도 크기, 모양, 색상, 무늬, 재질, 마감, 광이 일정하지 않을 수 있습니다.
      <img id="logo2" src="/images/logo.png" alt="">
    </fieldset>

    <br><br><br><div class="bar"></div><br>
    <br><br><span style="font-size: 1.3em;">✔ 주문 진행 과정</span><br><br>
    유리하는 시간, 스토어의 모든 제품의 과정은 아래 내용으로 동일합니다.<br><br>

    <table id="progress">
      <thead>
        <tr>
          <th>진행과정</th>
          <th>내용</th>
          <th>취소 및 환불</th>
        </tr>
      </thead>

      <tbody>
        <tr>
          <td class="head_text">① 고객님 주문</td>
          <td class="head_text">고객님께서 원하시는 제품을 구매(결제)합니다.</td>
          <td>가능</td>
        </tr>

        <tr>
          <td class="head_text">② 주문서 확인</td>
          <td class="head_text">유리하는 시간에서 주문 의뢰가 들어온 내용을 확인합니다.</td>
          <td>가능</td>
        </tr>

        <tr>
          <td class="head_text">③ 확인 연락</td>
          <td class="head_text">요구사항, 변경요청, 제작소요기간을 계산하여 유리하는 시간에서 고객님께 연락을 드립니다.</td>
          <td>가능</td>
        </tr>

        <tr>
          <td class="head_text">④ 제작</td>
          <td class="head_text">제작이 진행됩니다. (난이도에 따라 최대 2주까지 소요될 수 있습니다.)</td>
          <td>*불가능</td>
        </tr>

        <tr>
          <td class="head_text">⑤ 배송</td>
          <td class="head_text">제작이 완료되면 당일 또는 익일에 배송 접수를 합니다.</td>
          <td>*불가능</td>
        </tr>

        <tr>
          <td class="head_text">⑥ 피드백</td>
          <td class="head_text">고객님의 만족도 및 피드백, 개선사항을 체크하기 위해 유리하는 시간에서 고객님께 연락을 드립니다.</td>
          <td>*불량 및 파손 시, 가능</td>
        </tr>
      </tbody>
    </table>

    <br><br><br><div class="bar"></div><br>
    <br><div id="product_explanation">
        <fieldset>
            <legend>
            <h1>&nbsp;&nbsp;제품 구매하기&nbsp;&nbsp;</h1>
            </legend>

            <div class="class_attribute">
            <span>[제품 대표 사진]</span>
            </div>
            <img class="img" src="${product.mainPhotoUrl}">
            <% Member member = (Member) request.getAttribute("member"); %>

            <div id="productPhotoUrls">
                <%
                Product product = (Product) request.getAttribute("product");
                for (int i = 1; i <= 10; i++) {
                    String url = null;
                    switch (i) {
                        case 1: url = product.getPhotoUrl_1(); break;
                        case 2: url = product.getPhotoUrl_2(); break;
                        case 3: url = product.getPhotoUrl_3(); break;
                        case 4: url = product.getPhotoUrl_4(); break;
                        case 5: url = product.getPhotoUrl_5(); break;
                        case 6: url = product.getPhotoUrl_6(); break;
                        case 7: url = product.getPhotoUrl_7(); break;
                        case 8: url = product.getPhotoUrl_8(); break;
                        case 9: url = product.getPhotoUrl_9(); break;
                        case 10: url = product.getPhotoUrl_10(); break;
                    }
                    if (url != null) { %>
                        <div class="class_attribute">
                            <span>[제품 사진 <%= i %>]</span>
                        </div>
                        <img class="img" src="<%= url %>">
                    <% }
                }
                %>
            </div>
            <br><br>
            <div class="class_attribute">
            <span>[제품명]</span>
            <span>${product.productName}</span>
            </div>

            <div class="class_attribute">
            <span>[구매자 성함]</span>
            <input type="text" value="${member.memberName}" id="buyerName" name="buyerName">
            </div>

            <div class="class_attribute">
            <span>[휴대폰 번호]</span>
            <input type="text" value="${member.mobile}" id="buyerMobile" name="buyerMobile">
            </div>

            <div class="class_attribute">
            <span>[제품 가격]</span>
            <span>${product.productPrice} KRW</span>
            </div>

            <div class="class_attribute">
            <span>[구매 수량]&nbsp;&nbsp;*현재 재고량 : ${product.productQuantity}</span>
            <input type="number" id="quantity" name="quantity">
            </div>

            <div class="class_attribute">
            <span>[배송 주소]</span>
            <input class="input_area" type="text" id="address" name="address">
            </div>
            <br>
        </fieldset>
    </div>
        <button class="clickButtons" onMouseover="changeColor(this)" onMouseout="rollbackColor(this)" onclick="purchase()">제품 구매하기</button>
    </div><br>

<script>

function purchase() {
    var memberId = "${member.memberId}";
    var productId = "${product.productId}";
    var memberName = document.getElementById('buyerName').value;
    var mobile = document.getElementById('buyerMobile').value;
    var address = document.getElementById('address').value;
    var productQuantity = document.getElementById('quantity').value;
    var productName = "${product.productName}";
    var apiEndPoint = "${apiEndPoint}";
    var productPrice = "${product.productPrice}";
    var orderPayment =  productQuantity * productPrice;

    if(!memberName){
        alert('구매자 성함이 누락되었습니다.');
        return false;
    }else if(!mobile){
        alert('휴대폰 번호를 확인해주세요.');
        return false;
    }else if(!productQuantity){
         alert('구매 수량 정보가 누락되었습니다.');
         return false;
     }else if(!address){
        alert('배송지 주소가 누락되었습니다.');
        return false;
    }

    if(!confirm('주문 정보 확인 부탁드립니다.\n\n제품명 : '+productName+'\n구매자 성함: '+memberName+'\n휴대폰 번호 : '+mobile+'\n구매 수량 : '+productQuantity+'\n배송 주소 : '+address + '\n최종 결제 금액 : '+orderPayment + ' KRW')){
        return false;
    }

    console.log('memberId: '+memberId);
    console.log('productId: '+productId);
    console.log('memberName: '+memberName);
    console.log('mobile: '+mobile);
    console.log('address: '+address);
    console.log('productQuantity: '+productQuantity);

    fetch(apiEndPoint + "/order", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            memberId: memberId,
            productId: productId,
            memberName: memberName,
            mobile: mobile,
            address: address,
            productQuantity: productQuantity,
            orderPayment: orderPayment
        })
    })
    .then(response => {
        if(!response.ok){
            return response.json().then(error => {
                throw new Error(error.message);
            })
        }
        return response.json();
    })
    .then(data => {
        alert('구매가 완료되었습니다.\n주문 확인 후 '+mobile+' 으로 연락드리겠습니다.\n감사합니다.');
        window.location.href="/productList";
    })
    .catch(error => {
        alert(error.message);
    });
}

function changeColor(button) {
  button.style.color = 'blue';
}
function rollbackColor(button) {
  button.style.color = 'black';
}

</script>
</body>
</html>