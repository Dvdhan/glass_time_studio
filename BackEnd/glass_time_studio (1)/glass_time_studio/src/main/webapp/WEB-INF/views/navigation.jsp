<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Menu</title>
    <link rel="stylesheet" href="/css/background_black.css">
</head>

<body>
<div id="menu">
  <div id="logo">
    <img src="/images/logo.png" alt="유리하는 시간 로고">
    <h1 id="title">유리하는 시간 glass_time_studio</h1>
  </div>
  <ul>
    <li><a href="/main">홈으로</a></li>
    <li><a href="/announcement">이벤트/공지사항</a></li>
    <li><a href="/class">클래스</a></li>
    <li><a href="/product">제품 구매하기</a></li>
    <li><a href="/review">수강생 후기</a></li>
    <% if (request.getAttribute("isLoggedIn") != null && (Boolean) request.getAttribute("isLoggedIn")) { %>
        <% if ((Boolean) request.getAttribute("isAdmin")) { %>
          <div id="after_login">
            <li style="border: 1px solid black;"><a href=""></a></li>
            <li style="border: 1px solid black;"><a href=""></a></li>
            <li><a href="/manager">관리자 페이지</a></li>
            <li><a href="/mypage">마이페이지</a></li>
            <li><a href="/member/logout">로그 아웃</a></li>
          </div>
        <% } else { %>
              <div id="after_login">
                <li style="border: 1px solid black;"><a href=""></a></li>
                <li style="border: 1px solid black;"><a href=""></a></li>
                <li style="border: 1px solid black;"><a href=""></a></li>
                <li><a href="/mypage">마이페이지</a></li>
                <li><a href="/member/logout">로그 아웃</a></li>
              </div>
        <% } %>
    <% } %>
  </ul>
</div>

</body>
</html>