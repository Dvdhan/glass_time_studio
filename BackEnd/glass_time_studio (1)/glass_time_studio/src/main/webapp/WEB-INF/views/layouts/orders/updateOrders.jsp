<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="David.glass_time_studio.domain.lecture.entity.Lecture" %>
<%@ page import="David.glass_time_studio.domain.member.entity.Member" %>
<%@ page import="David.glass_time_studio.domain.product.entity.Product" %>
<%@ page import="David.glass_time_studio.domain.product.dto.ProductDto" %>

<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
  <link rel="stylesheet" href="/css/background_black.css">

  <style>
    fieldset{
      width: 28em;
      margin: auto;
    }
    #productName, #productDescription, #productPrice, #productStatus, #productQuantity, #productMainPhotoUrl, #productPhotoUrl_1,#productPhotoUrl_2,#productPhotoUrl_3,#productPhotoUrl_4,#productPhotoUrl_5,#productPhotoUrl_6, #productPhotoUrl_7,#productPhotoUrl_8,#productPhotoUrl_9,#productPhotoUrl_10 {
      display: flex;
      padding: 10px;
    }
    .head{
      width: 30%;
      text-align: left;
    }
    .body{
      grid-area: body;
      width: 100%;
      text-align: left;
    }
    .body select, .body input, .body textarea{
      width: 70%;
      text-align: left;
    }
    .send{
      display: flex;
      justify-content: center;
      margin: 0.5em;
    }
    button{
        margin: 0.5em;
    }
    .class_attribute{
        width: 25em;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin: auto;
        margin-top: 0.5em;
    }
    #address_input{
        width: 15em;
    }
  </style>
</head>
<body>
  <%@ include file="../../navigation.jsp" %>

  <form id="orderForm">
    <fieldset>
      <legend>
        <h1>&nbsp;&nbsp;내 주문 정보&nbsp;&nbsp;</h1>
      </legend>

      <div class="class_attribute">
      <span>[주문 제품 이름]</span>
      <span>${order.productName}</span>
      </div>

      <div class="class_attribute">
      <span>[주문 제품 가격]</span>
      <span>${order.productPrice}</span>
      </div>

      <div class="class_attribute">
      <span>[주문 수량]</span>
      <input id="quantity" type="number" value="${order.productQuantity}">
      </div>

      <div class="class_attribute">
      <span>[주문자 이름]</span>
      <input id="memberName" type="text" value="${order.memberName}">
      </div>

      <div class="class_attribute">
      <span>[주문자 연락처]</span>
      <input id="mobile" type="text" value="${order.mobile}">
      </div>

      <div class="class_attribute">
      <span>[배송 주소]</span>
      <input id="address_input" type="text" value="${order.address}">
      </div>

      <div class="class_attribute">
      <span>[결제 금액]</span>
      <span id="orderPayment"></span>
      </div>

      <div class="class_attribute">
      <span>[주문 생성 날짜]</span>
      <span id="created_At">${order.created_At}</span>
      </div>

      <div class="class_attribute">
      <span>[주문 수정 날짜]</span>
      <span id="modified_At">${order.modified_At}</span>
      </div>

      <div class="class_attribute">
      <span>[주문 상태]</span>
      <span>${order.orderStatus}</span>
      </div>

      <div class="send">
        <button onclick="patchOrder()" type="button">주문 수정하기</button>
      </div>
    </fieldset>
    <input type="hidden" name="productId" id="productId" value="${product.productId}">
  </form>


  <script>
    function patchOrder(){
        let productId = "${product.productId}";
        var apiEndPoint = "${apiEndPoint}";
        var orderId = "${order.orderId}";
        var memberId = "${order.memberId}";
        var orderStatus = "${order.orderStatus}";
        console.log('주문 상태: '+orderStatus);

        var productQuantity = document.getElementById('quantity').value;
        var memberName = document.getElementById('memberName').value;
        console.log('회원이름: '+memberName);
        var mobile = document.getElementById('mobile').value;
        var address = document.getElementById('address_input').value;

        fetch(apiEndPoint+"/order/updateStatus/"+orderId, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                memberId: memberId,
                productQuantity: productQuantity,
                memberName: memberName,
                mobile: mobile,
                address: address,
                orderStatus: orderStatus
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
            console.log('서버 응답: ', data);
            if(!data.message){
                alert('수정이 완료되었습니다.');
            }else {
                alert(data.message);
            }
            window.location.href = "/myOrders?memberId="+memberId;
        })
        .catch(error => {
            console.error('문제가 발생했습니다: ',error);
            alert(error.message);
        });
    }

    document.addEventListener('DOMContentLoaded', function() {
        const createdAt = "${order.created_At}";
        console.log('createdAt: '+createdAt);
        const modifiedAt = "${order.modified_At}";
        console.log('modifiedAt: '+modifiedAt);

        const productQuantityInput = document.getElementById('quantity');
        const productPrice = "${order.productPrice}";

        productQuantityInput.addEventListener('input', function(){
            const productQuantity = productQuantityInput.value;
            document.getElementById('orderPayment').textContent = calculate(productPrice, productQuantity) + ' KRW';
        });
        document.getElementById('orderPayment').textContent = "${order.orderPayment}" + " KRW";
        document.getElementById('created_At').textContent = formatDateTime(createdAt);
        document.getElementById('modified_At').textContent = formatDateTime(modifiedAt);
    });

    function formatDateTime(dateTime) {
        const [date, time] = dateTime.split('T');
        const [hours, minutes, seconds] = time.split('.')[0].split(':');
        return date+' '+hours+':'+minutes+':'+seconds;
    }
    function calculate(productPrice, productQuantity){
        console.log('가격: '+productPrice);
        console.log('수량: '+productQuantity);

        const newPrice = productPrice * productQuantity;

        return newPrice;
    }

  </script>
</body>
</html>