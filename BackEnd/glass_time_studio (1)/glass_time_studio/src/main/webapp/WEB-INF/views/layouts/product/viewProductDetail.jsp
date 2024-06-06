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
        width: 14em;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin: auto;
        margin-top: 0.5em;
    }
    .img{
        width: 14em;
        height: 6em;
        margin: auto;
        display: block;
        margin-top: 0.5em;
    }
    #product_word{
        width: 14em;
        margin: auto;
        margin-top: 10px;
    }
  </style>
</head>
<body>
  <%@ include file="../../navigation.jsp" %>

  <form id="createProductForm">
    <fieldset>
      <legend>
        <h1>&nbsp;&nbsp;판매 제품&nbsp;&nbsp;</h1>
      </legend>

      <div class="class_attribute">
      <span>제품 이름</span>
      <span>${product.productName}</span>
      </div>

      <div class="class_attribute">
      <span>제품 가격</span>
      <span>${product.productPrice}</span>
      </div>

      <div class="class_attribute">
      <span>제품 재고량</span>
      <span>${product.productQuantity}</span>
      </div>

      <div class="class_attribute">
      <span>제품 상태</span>
      <span>${product.productStatus}</span>
      </div>

      <div class="class_attribute">
      <span>제품 설명</span>
      </div>
      <div id="product_word">
          <span>${product.productDescription}</span>
      </div>

      <div class="class_attribute">
      <span>제품 대표 사진</span>
      </div>
      <img class="img" src="${product.mainPhotoUrl}">

    <div id="productPhotoUrls">
      <%
        ProductDto.Response product = (ProductDto.Response) request.getAttribute("product");
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
                    <span>제품 사진 <%= i %></span>
                </div>
                <img class="img" src="<%= url %>">
            <% }
        }
      %>
    </div>

      <div class="send">
        <button onclick="patchProduct()" type="button">제품 수정하기</button>
        <button onclick="deleteProduct()" type="button">제품 삭제하기</button>
      </div>
    </fieldset>
    <input type="hidden" name="productId" id="productId" value="${product.productId}">
  </form>


  <script>

    function patchProduct(){
        let productId = "${product.productId}";
        if(confirm('등록된 제품 정보를 수정하시겠습니까?')){
            window.location.href = `/patchProduct/${productId}`; // 페이지 리다이렉션
        }
    }

    function deleteProduct(){
        let productId = "${product.productId}";
        if(confirm('등록된 제품을 삭제하시겠습니까?')){
            fetch('/product/' + productId, {
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
                window.location.href = '/productManager';
            })
            .catch(error => {
                console.error('Error: ', error);
                alert('삭제 중 문제가 발생했습니다.');
            });
        }
    }
  </script>
</body>
</html>