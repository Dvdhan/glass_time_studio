<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
  <link rel="stylesheet" href="../../css/background_black.css">

  <style>
    fieldset{
      width: 40%;
      margin: auto;
    }
    #class_type, #class_date, #class_time, #class_ppl, #class_message, #class_phone{
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
      width: 80%;
      text-align: left;
    }
    .send{
      display: flex;
      justify-content: flex-end;
      margin-right: 15%;
      padding: 10px 0;
    }
    
  </style>
</head>
<body>
  <div id="menu">
    <div id="logo">
      <img src="../images/logo.png" alt="">
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

  <form id="reservation_form" action="#">
    <fieldset>
      <legend>
        <h1>&nbsp;&nbsp;예약하기&nbsp;&nbsp;</h1>
      </legend>

      <div id="class_type">
        <div class="head">
          클래스 종류 : 
        </div>

        <div class="body">
          <select name="class_type_select" id="class_type_select">
            <option value="none">선택</option>
            <option value="oneday">1. 원데이 클래스</option>
            <option value="hobby">2. 취미반 클래스</option>
          </select>
        </div>
      </div>

      <div id="class_date">
        <div class="head">
          요청 날짜 :
        </div>

        <div class="body">
          <input type="date" name="rsvn_date_select" id="rsvn_date_select"></input>
        </div>
      </div>

      <div id="class_time">
        <div class="head">
          요청 시간 :
        </div>

        <div class="body">
          <select name="rsvn_time_select" id="rsvn_time_select">
            <option value="none">선택</option>
            <option value="1100">[첫시작] 11:00 ~</option>
            <option value="1500">[마지막] 15:00 ~</option>
          </select>
        </div>
      </div>

      <div id="class_ppl">
        <div class="head">
          참여 인원 : 
        </div>

        <div class="body">
          <input type="number" name="rsvn_ppl_num" id="rsvn_ppl_num">
        </div>
      </div>

      <div id="class_phone">
        <div class="head">
          휴대폰 번호 :
        </div>
        
        <div class="body">
          <input type="number" name="rsvn_ppl_ph" id="rsvn_ppl_ph" placeholder=" - 제외, 휴대폰 번호만 입력해주세요.">
        </div>
      </div>

      <div id="class_message">
        <div class="head">
          메세지 : 
        </div>

        <div class="body">
          <textarea cols="30" rows="5" id="rsvn_message"></textarea>
        </div>
      </div>

      <div class="send">
        <!-- <button type="submit">예약 요청하기</button> -->
        <button onclick="rsvnBtn();" type="button">예약 요청하기</button>
      </div>
      
    </fieldset>
  </form>
  

  <script>
    function rsvnBtn(){
      let classType = document.querySelector('#class_type_select').value;
      let classDate = document.querySelector('#rsvn_date_select').value;
      let classTime = document.querySelector('#rsvn_time_select').value;
      let pplNum = document.querySelector('#rsvn_ppl_num').value;
      let classMessage = document.querySelector('#rsvn_message').value;
      let class_phone = document.querySelector('#rsvn_ppl_ph').value;

      if(classType === "none"){
        alert('클래스 종류를 선택해주세요');
      }else if(!classDate){
        alert('요청 날짜를 선택해주세요');
      }else if(classTime ==="none"){
        alert('요청 시간을 선택해주세요');
      }else if(!pplNum){
        alert('참여 인원을 선택해주세요');
      }else if(!class_phone){
        alert('휴대폰 번호를 입력해주세요');
      }else if(class_phone.length >11 || class_phone.length <11){
        alert('휴대폰 번호를 확인해주세요.');
      }else if(!classMessage){
        alert('메세지를 작성해주세요'); 
      }else {
        alert('예약 요청이 완료되었습니다.\n일정 확인 후 연락드리겠습니다.');
        document.getElementById('reservation_form').submit();
        window.location.href = "../../index.html";
      }
    }
  </script>
</body>
</html>