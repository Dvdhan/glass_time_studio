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
      <span>${order.productQuantity}</span>
      </div>

      <div class="class_attribute">
      <span>[주문자 이름]</span>
      <span>${order.memberName}</span>
      </div>

      <div class="class_attribute">
      <span>[주문자 연락처]</span>
      <span>${order.mobile}</span>
      </div>

      <div class="class_attribute">
      <span>[배송 주소]</span>
      <span>${order.address}</span>
      </div>

      <div class="class_attribute">
      <span>[결제 금액]</span>
      <span>${order.orderPayment} KRW</span>
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
      <select id="orderStatus">
        <option value="${order.orderStatus}">${order.orderStatus}</option>
        <option value="ORDERED">1. ORDERED: 결제 완료</option>
        <option value="CHECKING">2. CHECKING: 주문서 확인</option>
        <option value="CONTACT">3. CONTACT: 주문 확인 연락</option>
        <option value="MAKING">4. MAKING: 제작</option>
        <option value="SHIPPING">5. SHIPPING: 배송</option>
        <option value="DELIVERED">6. DELIVERED: 배송 완료</option>
        <option value="CANCELED">7. CANCELED: 주문 취소</option>
      </select>
      </div>

      <div class="send">
        <button onclick="patchOrder()" type="button">주문 수정하기</button>
      </div>
    </fieldset>
    <input type="hidden" name="productId" id="productId" value="${product.productId}">
  </form>


  <script>
    function patchOrder(){
        var memberId = "${member.memberId}";
        let productId = "${product.productId}";
        var apiEndPoint = "${apiEndPoint}";
        var orderId = "${order.orderId}";

        var orderStatus = document.getElementById('orderStatus').value;

        fetch(apiEndPoint+"/order/updateStatusForManager/"+orderId, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
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
            alert('주문 정보가 수정되었습니다.');
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

        document.getElementById('created_At').textContent = formatDateTime(createdAt);
        document.getElementById('modified_At').textContent = formatDateTime(modifiedAt);
    });

    function formatDateTime(dateTime) {
        const [date, time] = dateTime.split('T');
        const [hours, minutes, seconds] = time.split('.')[0].split(':');
        return date+' '+hours+':'+minutes+':'+seconds;
    }

  </script>
</body>
</html>