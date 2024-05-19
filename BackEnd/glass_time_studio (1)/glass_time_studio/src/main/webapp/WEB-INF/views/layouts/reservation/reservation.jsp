<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="David.glass_time_studio.domain.lecture.entity.Lecture" %>
<%@ page import="David.glass_time_studio.domain.member.entity.Member" %>

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
  <%@ include file="../../navigation.jsp" %>

  <form id="reservation_form">
    <fieldset>
      <legend>
        <h1>&nbsp;&nbsp;예약하기&nbsp;&nbsp;</h1>
      </legend>

      <div id="class_type">
        <div class="head">
          클래스 종류
        </div>

        <div class="body">
          <select name="lectureId" id="class_type_select">
            <option value="none">선택</option>
            <%
              List<Lecture> lectures = (List<Lecture>) request.getAttribute("lectures");
              if (lectures != null) {
                for (Lecture lecture : lectures) {
                  out.print("<option value=\"" + lecture.getLecture_Id() + "\">" + lecture.getLecture_Name() + "</option>");
                }
              }
            %>
          </select>
        </div>
      </div>

      <div id="class_date">
        <div class="head">
          요청 날짜
        </div>

        <div class="body">
          <input type="date" name="requestDate" id="rsvn_date_select"></input>
        </div>
      </div>

      <div id="class_time">
        <div class="head">
          요청 시간
        </div>

        <div class="body">
          <select name="requestTime" id="rsvn_time_select">
            <option value="none">선택</option>
            <option value="1100">[첫시작] 11:00 ~</option>
            <option value="1500">[마지막] 15:00 ~</option>
          </select>
        </div>
      </div>

      <div id="class_ppl">
        <div class="head">
          참여 인원 수
        </div>

        <div class="body">
          <input type="number" name="peopleNumber" id="rsvn_ppl_num">
        </div>
      </div>

      <div id="class_ppl">
        <div class="head">
          예약자 성함
        </div>

        <div class="body">
          <input type="text" name="bookerName" id="rsvn_booker_name">
        </div>
      </div>

      <div id="class_phone">
        <div class="head">
          휴대폰 번호
        </div>
        
        <div class="body">
          <input type="text" name="mobile" id="rsvn_ppl_ph" placeholder=" - 제외, 휴대폰 번호만 입력해주세요.">
        </div>
      </div>

      <div id="class_message">
        <div class="head">
          메세지
        </div>

        <div class="body">
          <textarea cols="30" rows="5" id="rsvn_message" name="requestMessage"></textarea>
        </div>
      </div>

      <div class="send">
        <!-- <button type="submit">예약 요청하기</button> -->
        <button onclick="rsvnBtn();" type="button">예약 요청하기</button>
      </div>
    </fieldset>


    <%
        Member member = (Member) request.getAttribute("member");
        if (member != null && member.getMemberId() != null) {
    %>
        <input type="hidden" id="memberId" name="memberId" value="${member.memberId}">
    <%
        } else {
    %>
        <input type="hidden" id="memberId" name="memberId" value="">
    <%
        }
    %>
  </form>
  

  <script>
    function rsvnBtn(){
      let lectureId = document.querySelector('#class_type_select').value;
      let requestDate = document.querySelector('#rsvn_date_select').value;
      let requestTime = document.querySelector('#rsvn_time_select').value;
      let bookerName = document.querySelector('#rsvn_booker_name').value;
      let peopleNumber = document.querySelector('#rsvn_ppl_num').value;
      let requestMessage = document.querySelector('#rsvn_message').value;
      let mobile = document.querySelector('#rsvn_ppl_ph').value;
      let memberId = document.querySelector('#memberId').value;

      let today = new Date();
      today.setHours(0, 0, 0, 0);
      let selectedDate = new Date(requestDate);
      selectedDate.setHours(0, 0, 0, 0);

      if(lectureId === "none"){
        alert('클래스 종류를 선택해주세요');
      }else if(!requestDate || requestDate < today){
        alert('유효한 요청 날짜를 선택해주세요(오늘 이후의 날짜)');
      }else if(requestTime ==="none"){
        alert('요청 시간을 선택해주세요');
      }else if(!peopleNumber){
        alert('참여 인원을 선택해주세요');
      }else if(!mobile){
        alert('휴대폰 번호를 입력해주세요');
      }else if(mobile.length >11 || mobile.length <11){
        alert('휴대폰 번호를 확인해주세요.');
      }else if(!requestMessage){
        alert('메세지를 작성해주세요'); 
      }else if(!memberId){
        alert('수업 예약을 위해서는 로그인이 필수 입니다.\n로그인 이후 다시 요청해주세요');
      }
      else {
        console.log({
          lectureId: parseInt(lectureId),
          requestDate: requestDate,
          requestTime: requestTime,
          peopleNumber: parseInt(peopleNumber),
          bookerName: bookerName,
          requestMessage: requestMessage,
          mobile: mobile,
          memberId: parseInt(memberId)
        });
        fetch('/Booking', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              lectureId: parseInt(lectureId),
              requestDate: requestDate,
              requestTime: requestTime,
              peopleNumber: parseInt(peopleNumber),
              bookerName: bookerName,
              requestMessage: requestMessage,
              mobile: mobile,
              memberId: parseInt(memberId)
            })
        })
        .then(response => {
            if(response.ok){
                return response.json();
            }
            throw new Error('Network response was not ok');
        })
        .then(data => {
            console.log("서버 응답: ", data);
            alert('예약 요청이 완료되었습니다.\n일정 확인 후 연락드리겠습니다.');
            window.location.href = "/main";
        })
        .catch(error => {
            console.error('문제가 발생했습니다: ', error);
            alert('예약 요청 중 오류가 발생했습니다. 다시 시도해주세요.');
        })
      }
    }
  </script>
</body>
</html>