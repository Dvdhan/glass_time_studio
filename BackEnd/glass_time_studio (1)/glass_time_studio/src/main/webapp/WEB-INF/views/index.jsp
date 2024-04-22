<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Main Page</title>

  <style>
    #slideshow {
      display: flex;
      width: 35em;
      height: 400px;
      margin: auto;
      overflow: hidden;
    }
    #slideshow img{
      max-width: 100%;
      max-height: 100%;
      object-fit: contain;
    }
    #slide_1, #slide_2{
      width: 50%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
    }
    #welcome{
      margin: auto;
      width: 35em;
    }
  </style>

  <script src="https://cdn.ckeditor.com/4.14.0/standard/ckeditor.js"></script>
  <link rel="stylesheet" href="/css/background_black.css">
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

  <div id="slideshow">
    <div id="slide_1">
      <img src="/images/glass_butterfly.jpg" alt="" id="img1">
    </div>
    <div id="slide_2">
      <img src="/images/glass_christmas_ornament.jpg" alt="" id="img2">
    </div>
  </div>
  <br><br>

  <div id="welcome">
    <fieldset style="margin:auto; text-align: center; width: 30em;">
      - 색다른 취미 제안, 힐링의 시간으로 추천 <br>
      - 당신의 취향대로 처음부터 끝까지 다 만들수 있다.
    </fieldset>
  </div>
<br>
  <div style="margin: auto; text-align: center; width: 10em;">
    <button>
      <a href="/member/login">네이버 로그인</a>
    </button>
  </div>
  


  <script>
    const images = [
      '/images/glass_butterfly.jpg',
      '/images/glass_christmas_ornament.jpg',
      '/images/glass_christmas_star.jpg',
      '/images/glass_hot_air_balloon.jpg',
      '/images/glass_personal_color_mirror.jpg',
      '/images/glass_plant.png',
      '/images/glass_rose.jpg',
      '/images/glass_santa.jpg',
      '/images/glass_star.jpg',
      '/images/glass_sunflower.jpg',
      '/images/glass_whale_butterfly.jpg',
      '/images/glass_whale.jpg'
    ];

    let currentIndex = 0;

    function changeImage() {
      document.getElementById('img1').src = images[currentIndex % images.length];
      document.getElementById('img2').src = images[(currentIndex+1) % images.length];
      currentIndex += 2;
    }
    changeImage();
    setInterval(changeImage, 3000);
  </script>
</body>
</html>
