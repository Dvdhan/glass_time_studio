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
      width: 35em;
    }
    #add{
      display: inline-block;
      border: 1px solid white;
      margin-top: 1em;
      padding : 10px;
      margin-left: 0.8em;
    }
    #announcement_btns{
      text-align: center;
    }
    #cont{
      margin: auto;
      width: 30em;

    }
    #cont_table{
      margin: auto;
      text-align: center;
      width: 28em;
    }
    #cont_table td{
      text-align: center;
    }
    #announcement_btn_reverse{
      width: 33em;
      margin: auto;
      margin-top: 1em;
      text-align: right;
    }
    #aesc_order_btn, #desc_order_btn{
      background-color: black;
      border: 1px solid white;
      color: white;
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
      <div id="cont">
        <table id="cont_table">
        </table>
      </div>

      <div id="announcement_btn_reverse">
        <button id="aesc_order_btn" onClick="aesc_btn()" onMouseOver="aesc_effect_btn_on()" onMouseOut="aesc_effect_btn_off()">과거순으로 보기</button>
        <button id="desc_order_btn" onClick="desc_btn()" onMouseOver="desc_effect_btn_on()" onMouseOut="desc_effect_btn_off()">최신순으로 보기</button>
      </div>
    </fieldset>
  </div>

    <% if(request.getAttribute("member") != null && (Boolean) request.getAttribute("isAdmin")) { %>
      <div id="announcement_btns">
          <a href="/announcement/write" id="add">공지사항 작성하기</a>
      </div>
    <% } %>

<script>
document.addEventListener('DOMContentLoaded', function() {
  fetch('http://localhost:8080/Announcement/all?page=1&size=10')
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      console.log("응답:", data); // 데이터 확인을 위한 로그
      const announcements = data.data;
      const table = document.querySelector("#cont_table");
      let rows = '';
      if(announcements.length <= 0) {
        table.innerHTML = "<tr><td>작성된 이벤트/공지사항이 없습니다.</td></tr>";
      } else{
      announcements.forEach(announcement => {
        const createdAt = new Date(announcement.created_at).toLocaleString();
        console.log("공지번호: ", announcement.announcement_Id);
        console.log("공지제목: ", announcement.announcement_Title);
        console.log("작성일: ", createdAt);
        rows +=
          "<tr>"+
          "<td><a href='/Announcement/"+announcement.announcement_Id+"'>[공지번호]: " + announcement.announcement_Id + "</a></td>" +
          "<td><a href='/Announcement/"+announcement.announcement_Id+"'>[이벤트/공지 제목]: " + announcement.announcement_Title + "</a></td>" +
          "<td><a href='/Announcement/"+announcement.announcement_Id+"'>[작성일]: " + createdAt + "</a></td>"+
          "</tr>";
          console.log("rows: ", rows);
        });
        table.innerHTML = rows; // 모든 행을 한 번에 테이블에 추가
      }
    })
    .catch(error => {
      console.error('Error loading the announcements:', error);
    });
});

function aesc_btn(){
  fetch('http://localhost:8080/Announcement/all?page=1&size=10')
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      console.log("응답:", data); // 데이터 확인을 위한 로그
      const announcements = data.data;
      const table = document.querySelector("#cont_table");
      let rows = '';
      if(announcements.length <= 0) {
        table.innerHTML = "<tr><td>작성된 이벤트/공지사항이 없습니다.</td></tr>";
      } else{
      announcements.forEach(announcement => {
        const createdAt = new Date(announcement.created_at).toLocaleString();
        console.log("공지번호: ", announcement.announcement_Id);
        console.log("공지제목: ", announcement.announcement_Title);
        console.log("작성일: ", createdAt);
        rows +=
          "<tr>"+
          "<td><a href='/Announcement/"+announcement.announcement_Id+"'>[공지번호]: " + announcement.announcement_Id + "</a></td>" +
          "<td><a href='/Announcement/"+announcement.announcement_Id+"'>[이벤트/공지 제목]: " + announcement.announcement_Title + "</a></td>" +
          "<td><a href='/Announcement/"+announcement.announcement_Id+"'>[작성일]: " + createdAt + "</a></td>"+
          "</tr>";
          console.log("rows: ", rows);
        });
        table.innerHTML = rows; // 모든 행을 한 번에 테이블에 추가
      }
    })
    .catch(error => {
      console.error('Error loading the announcements:', error);
    });
}

function desc_btn(){
  fetch('http://localhost:8080/Announcement/all?page=1&size=10')
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      console.log("응답:", data); // 데이터 확인을 위한 로그
      const announcements = data.data.reverse();
      const table = document.querySelector("#cont_table");
      let rows = '';
      if(announcements.length <= 0) {
        table.innerHTML = "<tr><td>작성된 이벤트/공지사항이 없습니다.</td></tr>";
      } else{
      announcements.forEach(announcement => {
        const createdAt = new Date(announcement.created_at).toLocaleString();
        console.log("공지번호: ", announcement.announcement_Id);
        console.log("공지제목: ", announcement.announcement_Title);
        console.log("작성일: ", createdAt);
        rows +=
          "<tr>"+
          "<td><a href='/Announcement/"+announcement.announcement_Id+"'>[공지번호]: " + announcement.announcement_Id + "</a></td>" +
          "<td><a href='/Announcement/"+announcement.announcement_Id+"'>[이벤트/공지 제목]: " + announcement.announcement_Title + "</a></td>" +
          "<td><a href='/Announcement/"+announcement.announcement_Id+"'>[작성일]: " + createdAt + "</a></td>"+
          "</tr>";
          console.log("rows: ", rows);
        });
        table.innerHTML = rows; // 모든 행을 한 번에 테이블에 추가
      }
    })
    .catch(error => {
      console.error('Error loading the announcements:', error);
    });
}

function aesc_effect_btn_on() {
    let target = document.getElementById("aesc_order_btn");
    target.style.color="yellow";
    target.style.textDecoration="underline";
    target.style.cursor="pointer";
}
function aesc_effect_btn_off() {
    let target = document.getElementById("aesc_order_btn");
    target.style.color="white";
    target.style.textDecoration="none";
}
function desc_effect_btn_on() {
    let target = document.getElementById("desc_order_btn");
    target.style.color="yellow";
    target.style.textDecoration="underline";
    target.style.cursor="pointer";
}
function desc_effect_btn_off() {
    let target = document.getElementById("desc_order_btn");
    target.style.color="white";
    target.style.textDecoration="none";
}
</script>
</body>
</html>