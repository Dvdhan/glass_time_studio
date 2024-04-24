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
    #naver_login{
      background-color: black;
      border: 1px solid white;
      padding: 0.3em;
    }
  </style>

  <script src="https://cdn.ckeditor.com/4.14.0/standard/ckeditor.js"></script>
</head>

<body>
  <%@ include file="navigation.jsp" %>

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
  <% if (request.getAttribute("isLoggedIn") == null || !(Boolean) request.getAttribute("isLoggedIn")) { %>
      <div style="margin: auto; text-align: center; width: 10em;">
          <a href="/member/login" id="naver_login">네이버 로그인</a>
      </div>
  <% } %>

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
