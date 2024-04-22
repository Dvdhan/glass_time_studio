<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Products</title>
  <link rel="stylesheet" href="../../css/background_black.css">

  <style>
    #products{
      display: grid;
      width: 40em;
      grid-template-columns: repeat(3, 30%);
      justify-content: center;
      gap: 10px 10px;
      margin: auto;
    }
    .buttons{
      margin-top: 2em;
      text-align: center;
    }
    fieldset img{
      width: 100%;
      height: 600px;
      object-fit: contain;
    }
    p{
      text-align: center;
    }
    .detail{
      color: black;
    }
    .detail:hover{
      color: blue;
    }
    fieldset{
      height: 28em;
    }
  </style>
</head>
<body>
  <div id="menu">
    <div id="logo">
      <img src="/images/logo.png" alt="">
      <h1 id="title" style="padding-left: 1em;">유리하는 시간 glass_time_studio</h1>
    </div>
    <ul>
      <li><a href="/main">홈으로</a></li>
      <li><a href="/announcement">이벤트/공지사항</a></li>
      <li><a href="/class">클래스</a></li>
      <li><a href="/product">제품 구매하기</a></li>
      <li><a href="/review">수강생 후기</a></li>
    </ul>
  </div>
  <div id="products">
    <fieldset>
      <img src="/images/glass_sunflower.jpg" alt="">
      <p>제품명 : <span id="s_product_title">해바라기 화병 세트</span></p>
      <p>가격 : <span id="s_product_price">210,000원</span></p>
      <p>재고 : <span id="s_product_quantity">5</span></p>
      <div class="buttons">
        <button><a class="detail" href="/product_detail">상세보기</a></button>
        <button><a class="detail"  target="_blank" href="https://smartstore.naver.com/glasstime/products/9846789288">구매하기</a></button>
        <button><a class="detail" href="#">장바구니 담기</a></button>
      </div>
    </fieldset>

    <fieldset>
      <img src="/images/glass_sunflower.jpg" alt="">
      <p>제품명 : <span id="s_product_title">해바라기 화병 세트</span></p>
      <p>가격 : <span id="s_product_price">210,000원</span></p>
      <p>재고 : <span id="s_product_quantity">5</span></p>
      <div class="buttons">
        <button><a class="detail" href="/product_detail">상세보기</a></button>
        <button><a class="detail"  target="_blank" href="https://smartstore.naver.com/glasstime/products/9846789288">구매하기</a></button>
        <button><a class="detail" href="#">장바구니 담기</a></button>
      </div>
    </fieldset>

    <fieldset>
      <img src="/images/glass_sunflower.jpg" alt="">
      <p>제품명 : <span id="s_product_title">해바라기 화병 세트</span></p>
      <p>가격 : <span id="s_product_price">210,000원</span></p>
      <p>재고 : <span id="s_product_quantity">5</span></p>
      <div class="buttons">
        <button><a class="detail" href="/product_detail">상세보기</a></button>
        <button><a class="detail"  target="_blank" href="https://smartstore.naver.com/glasstime/products/9846789288">구매하기</a></button>
        <button><a class="detail" href="#">장바구니 담기</a></button>
      </div>
    </fieldset>

    <fieldset>
      <img src="/images/glass_sunflower.jpg" alt="">
      <p>제품명 : <span id="s_product_title">해바라기 화병 세트</span></p>
      <p>가격 : <span id="s_product_price">210,000원</span></p>
      <p>재고 : <span id="s_product_quantity">5</span></p>
      <div class="buttons">
        <button><a class="detail" href="/product_detail">상세보기</a></button>
        <button><a class="detail"  target="_blank" href="https://smartstore.naver.com/glasstime/products/9846789288">구매하기</a></button>
        <button><a class="detail" href="#">장바구니 담기</a></button>
      </div>
    </fieldset>

    <fieldset>
      <img src="/images/glass_sunflower.jpg" alt="">
      <p>제품명 : <span id="s_product_title">해바라기 화병 세트</span></p>
      <p>가격 : <span id="s_product_price">210,000원</span></p>
      <p>재고 : <span id="s_product_quantity">5</span></p>
      <div class="buttons">
        <button><a class="detail" href="/product_detail">상세보기</a></button>
        <button><a class="detail"  target="_blank" href="https://smartstore.naver.com/glasstime/products/9846789288">구매하기</a></button>
        <button><a class="detail" href="#">장바구니 담기</a></button>
      </div>
    </fieldset>

    <fieldset>
      <img src="/images/glass_sunflower.jpg" alt="">
      <p>제품명 : <span id="s_product_title">해바라기 화병 세트</span></p>
      <p>가격 : <span id="s_product_price">210,000원</span></p>
      <p>재고 : <span id="s_product_quantity">5</span></p>
      <div class="buttons">
        <button><a class="detail" href="/product_detail">상세보기</a></button>
        <button><a class="detail"  target="_blank" href="https://smartstore.naver.com/glasstime/products/9846789288">구매하기</a></button>
        <button><a class="detail" href="#">장바구니 담기</a></button>
      </div>
    </fieldset>

  </div>

</body>
</html>