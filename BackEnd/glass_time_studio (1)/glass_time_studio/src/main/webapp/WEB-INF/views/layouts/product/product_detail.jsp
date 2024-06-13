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
    #product_word{
        width: 25em;
        margin: auto;
        margin-top: 10px;
        margin-bottom: 1em;
    }
    .detail{
      color: black;
    }
  </style>
</head>
<body>
  <%@ include file="../../navigation.jsp" %>

  <div style="text-align: center;">

    <br><div id="product_explanation">
        <fieldset>
            <legend>
            <h1>&nbsp;&nbsp;제품 상세 설명&nbsp;&nbsp;</h1>
            </legend>

            <div class="class_attribute">
            <span>[제품 이름]</span>
            <span>${product.productName}</span>
            </div>

            <div class="class_attribute">
            <span>[제품 가격]</span>
            <span>${product.productPrice}</span>
            </div>

            <div class="class_attribute">
            <span>[제품 재고량]</span>
            <span>${product.productQuantity}</span>
            </div>

            <div class="class_attribute">
            <span>[제품 상태]</span>
            <span>${product.productStatus}</span>
            </div>

            <div class="class_attribute">
            <span>[제품 설명]</span>
            </div>
            <div id="product_word">
                <br>
                <span>${product.productDescription}</span>
            </div>

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
        </fieldset>
    </div>
          <% if(product.getProductQuantity() == 0 || product.getProductStatus() == "SOLD-OUT") { %>
          <button><a class='detail' onclick="showAlert()">제품 구매하기</a></button>
          <% } else { %>
          <button><a class='detail' href='/post_order/${product.productId}'>제품 구매하기</a></button>
          <% } %>
        <button onclick="addBasket('${member.memberId}', '${product.productId}')" type="button">장바구니 추가</button>
    </div><br>

<script>
function showAlert(){
    alert('제품의 재고가 없습니다.\n준비되는데로 찾아뵙겠습니다!');
}

function addBasket(memberId, productId){
    var apiEndPoint = "${apiEndPoint}";
    fetch(apiEndPoint+"/basket",{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            memberId: memberId,
            productId: productId
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
        console.log("서버 응답: ", data);
        alert('장바구니 추가 완료되었습니다.');
        window.location.href = "/product_detail/"+productId;
    })
    .catch(error => {
        console.error('문제가 발생했습니다: ', error);
        alert(error.message);
    });
}

</script>
</body>
</html>