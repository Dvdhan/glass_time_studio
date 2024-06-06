<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="David.glass_time_studio.domain.lecture.entity.Lecture" %>
<%@ page import="David.glass_time_studio.domain.member.entity.Member" %>
<%@ page import="David.glass_time_studio.domain.product.entity.Product" %>

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
      justify-content: flex-end;
      margin-right: 15%;
      padding: 10px 0;
    }
    .img{
        width: 15em;
        height: 6em;
        margin-bottom: 0.5em;
        display: block;
    }
    input[type="file"] {
        font-size: 30px;
    }
  </style>
</head>
<body>
  <%@ include file="../../navigation.jsp" %>

  <form id="createProductForm">
    <fieldset>
      <legend>
        <h1>&nbsp;&nbsp;판매 제품 수정하기&nbsp;&nbsp;</h1>
      </legend>

      <div id="productName">
        <div class="head">
          제품 이름
        </div>
        <div class="body">
          <input type="text" name="productName_" id="productName_" value="${product.productName}">
        </div>
      </div>

      <div id="productDescription">
        <div class="head">
          제품 설명
        </div>
        <div class="body">
          <input type="text" name="productDescription_" id="productDescription_" value="${product.productDescription}">
        </div>
      </div>

      <div id="productPrice">
        <div class="head">
          제품 가격
        </div>
        <div class="body">
          <input type="text" name="productPrice_" id="productPrice_" value="${product.productPrice}">
        </div>
      </div>

      <div id="productQuantity">
        <div class="head">
          제품 재고량
        </div>
        <div class="body">
          <input type="number" name="productQuantity_" id="productQuantity_" value="${product.productQuantity}">
        </div>
      </div>

      <div id="productStatus">
        <div class="head">
          판매 상태
        </div>
        <div class="body">
            <select id="productStatus_" name="productStatus_">
              <option value="${product.productStatus}">${product.productStatus}</option>
              <option value="AVAILABLE">AVAILABLE</option>
              <option value="SOLD-OUT">SOLD-OUT</option>
            </select>
        </div>
      </div>

      <div id="productMainPhotoUrl">
        <div class="head">
          제품 대표 사진
        </div>
        <div class="body">
          <img class="img" src = "${product.mainPhotoUrl}">
          변경할 <input type="file" name="productMainPhotoUrl_" id="productMainPhotoUrl_" value="${product.mainPhotoUrl}">
        </div>
      </div>

      <%
      Product product = (Product) request.getAttribute("product");
      if(product != null){
          for(int i = 1; i <= 10; i++) {
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
              <div id="productPhotoUrl_<%=i%>">
                  <div class="head">
                      제품 사진 <%=i%>
                  </div>
                  <div class="body">
                      <img class="img" src = "<%= url %>">
                      변경할 <input type="file" name="productPhotoUrl_<%=i%>_" id="productPhotoUrl_<%=i%>_">
                  </div>
              </div>
              <% } else { %>
              <div id="productPhotoUrl_<%=i%>">
                  <div class="head">
                      제품 사진 <%=i%>
                  </div>
                  <div class="body">
                      <input type="file" name="productPhotoUrl_<%=i%>_" id="productPhotoUrl_<%=i%>_">
                  </div>
              </div>
              <% }
          }
      } %>

      <div class="send">
        <button onclick="updateProduct()" type="button">제품 수정하기</button>
      </div>
    </fieldset>
  </form>


  <script>
async function updateProduct() {
    let productId = "${product.productId}";
    const formData = new FormData();

     // 첨부된 메인 사진 파일 처리 (<input type=files>는 FileList 반환함
    const mainPhotoInput = document.getElementById('productMainPhotoUrl_');

    // files는 반환된 FileList를 나타내고 [0]은 첫번째 요소.
    const mainPhoto = mainPhotoInput.files[0];
    console.log('Main Photo Input: ', mainPhotoInput);
    console.log('Main Photo: ', mainPhoto);

    // 사진이 첨부되었는지 판단하는 변수
    let hasPhotosToUpload = false;

    if (mainPhoto) {
        formData.append('files', mainPhoto);

        // 사진이 첨부되었으므로, true로 변환
        hasPhotosToUpload = true;
    } else {
        console.warn('Main Photo not selected');
    }

    for (let i = 1; i <= 10; i++) {
        const photoInput = document.getElementById('productPhotoUrl_' + i + '_');
        const photo = photoInput ? photoInput.files[0] : null;
        console.log('Photo Input ' + i + ':', photoInput);
        console.log('Photo ' + i + ':', photo);

        if (photo) {
            formData.append('files', photo);

            // 사진이 첨부되었으므로, true로 변환
            hasPhotosToUpload = true;
        } else {
            console.warn('Photo ' + i + ' not selected');
        }
    }

    let urls = '';

    // 첨부된 사진이 있을 경우,
    if (hasPhotosToUpload) {
        // 클라우드에 파일 업로드 요청
        const response = await fetch('/cloud/photo/uploads', {
            method: 'POST',
            body: formData
        });
        // 응답이 성공일 경우
        if (response.ok) {
            urls = await response.json();
            console.log("Uploaded Urls:", urls);
        } else {
            console.error('Upload failed:', response.statusText);
            alert('파일 업로드 중 오류가 발생했습니다. 다시 시도해주세요.');
            return;
        }
    }

    // 업로드된 url을 포함한 제품 데이터를 생성
    const productData = {
        productName: document.getElementById('productName_').value,
        productDescription: document.getElementById('productDescription_').value,
        productPrice: document.getElementById('productPrice_').value,
        productQuantity: document.getElementById('productQuantity_').value,
        productStatus: document.getElementById('productStatus_').value,
        mainPhotoUrl: urls[0] || null,
        photoUrl_1: urls[1] || null,
        photoUrl_2: urls[2] || null,
        photoUrl_3: urls[3] || null,
        photoUrl_4: urls[4] || null,
        photoUrl_5: urls[5] || null,
        photoUrl_6: urls[6] || null,
        photoUrl_7: urls[7] || null,
        photoUrl_8: urls[8] || null,
        photoUrl_9: urls[9] || null,
        photoUrl_10: urls[10] || null
    };
    // 제품 정보 저장 요청
    const saveResponse = await fetch('/product/' + productId, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(productData)
    });
    if (saveResponse.ok) {
        alert('제품 수정이 완료되었습니다.');
        window.location.href = "/productManager";
    } else {
        console.error('Error saving product: ', saveResponse.statusText);
        alert('제품 수정 중 오류가 발생했습니다. 다시 시도해주세요.');
    }
}

  </script>
</body>
</html>