<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Manager Page</title>

  <style>
    #container{
      width: 35em;
      margin: auto;
      text-align: center;
    }
    #container button {
      background-color: black;
      color: white;
      border: 1px solid white;
    }
    #container fieldset{
      width: 20em;
      margin:auto;
    }
    #container a {
      font-size: 1.5em;
    }

  </style>
  
  <script src="https://cdn.ckeditor.com/4.14.0/standard/ckeditor.js"></script>
  <link rel="stylesheet" href="../../css/background_black.css">
</head>

<body>
  <%@ include file="../../navigation.jsp" %>

  <div id="container">
    <fieldset>
      <p style="font-size: 3em;">관리자 페이지</p>
      <button style="display: block; margin:20px auto;" type="button"><a href="#">주문 관리</a></button>
      <button style="display: block; margin:20px auto;" type="button"><a href="#">클래스 예약 확인</a></button>
      <button style="display: block; margin:20px auto;" type="button"><a href="#">판매용 제품 관리</a></button>
      <button style="display: block; margin:20px auto;" type="button"><a href="#">공지사항 관리</a></button>
      <button style="display: block; margin:20px auto;" type="button"><a href="#">매출 확인</a></button>
      <button style="display: block; margin:20px auto;" type="button"><a href="#">회원 관리</a></button>
      <br><br><br>
    </fieldset>
  </div>

</body>
</html>