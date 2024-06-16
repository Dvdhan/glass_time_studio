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
    .img{
        width: 25em;
        height: 15em;
        margin: auto;
        display: block;
        margin-top: 0.5em;
    }
    #product_word{
        width: 25em;
        margin: auto;
        margin-top: 10px;
        margin-bottom: 1em;
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
      <span>${order.orderStatus}</span>
      </div>

      <div class="send">
        <button onclick="patchOrder()" type="button">주문 수정하기</button>
        <button onclick="deleteOrder()" type="button">주문 취소하기</button>
      </div>
    </fieldset>
    <input type="hidden" name="productId" id="productId" value="${product.productId}">
  </form>


  <script>

    function patchOrder(){
        let productId = "${product.productId}";

        if(confirm('주문 정보를 수정하시겠습니까?')){
            window.location.href = `/patchOrder/${orderId}`; // 페이지 리다이렉션
        }
    }

    function deleteOrder(){
        let orderId = "${order.orderId}";
        let memberId = "${member.memberId}";
        if(confirm('주문을 취소하시겠습니까?')){
            fetch('/order/' + memberId + '/' + orderId, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                if (!response.ok) throw new Error ('삭제 실패');
                return response.json();
            })
            .then(data => {
                alert(data.message);
                window.location.href = '/myOrders?memberId='+memberId;
            })
            .catch(error => {
                console.error('Error: ', error);
                alert('삭제 중 문제가 발생했습니다.');
            });
        }
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