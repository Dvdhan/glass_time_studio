<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Event/Announcement</title>
  <link rel="stylesheet" href="../../css/background_black.css">
  <style>
    #post-list, #post-list li{
      list-style: none;
      text-align: center;
      margin: auto;
      width: 80%;
    }
    #add, #edit, #delete {
      display: inline-block;
      border: 1px solid white;
      margin-top: 1em;
      padding : 10px;
      margin-left: 0.8em;
    }
    #announcement_btns{
      text-align: center;
    }
  </style>
</head>
<body>
  <%@ include file="../../navigation.jsp" %>
  <div id="post-list">
    <fieldset>
      <legend>
        <h2>&nbsp;&nbsp;이벤트/공지사항 목록&nbsp;&nbsp;</h2>
      </legend>
      <div>
          <ul>
            <li><a href="#">공지사항 1</a></li>
            <li><a href="#">공지사항 2</a></li>
            <li><a href="#">공지사항 3</a></li>
          </ul>
      </div>
    </fieldset>
  </div>

    <% if(request.getAttribute("member") != null && (Boolean) request.getAttribute("isAdmin")) { %>
      <div id="announcement_btns">
          <a href="" id="add">공지사항 작성하기</a>
          <a href="" id="edit">공지사항 수정하기</a>
          <a href="" id="delete">공지사항 삭제하기</a>
      </div>
    <% } %>

</body>
</html>