<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="David.glass_time_studio.domain.member.entity.Member" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>클래스 후기 작성하기</title>
    <script src="https://cdn.ckeditor.com/4.14.0/standard/ckeditor.js"></script>
</head>
<style>
    #body{
      width: 35em;
      margin: auto;
      text-align: center;
    }
    #body input{
      display: block;
      text-align: left;
    }
    #event_select, #event_title, #event_content {
      margin: 10px auto; /* 상하 간격을 주고, 좌우로 자동 마진을 주어 중앙 정렬 */
      width: 30em; /* 너비를 일관되게 설정 */
    }
    #event_content textarea {
      width: 100%; /* 입력 필드와 텍스트 영역이 각 div의 전체 너비를 차지하도록 설정 */
    }
    #event_content{
      margin-top: 1em;
    }
    .an_head{
      display: block;
      text-align: left;
    }


</style>
<body>
  <%@ include file="../../navigation.jsp" %>

  <% Member member = (Member) request.getAttribute("member"); %>
  <form name="form" onsubmit="saveit(event)">
      <div id="body">
        <fieldset>
          <legend>클래스 후기 게시글 작성</legend>

          <div id="event_select">
            <span class="an_head">참여했던 수업</span>
            <select id="myBookingName">
                <option value="">-- 후기 작성할 수업 선택 --</option>
            </select>
          </div>

          <div id="event_title">
            <span class="an_head">후기 게시글 제목</span>
            <input type="text" id="review_Title" name="review_Title" maxlength="30">
          </div>

          <div id="event_content">
            <span class="an_head">후기 게시글 내용</span>
            <textarea id="review_Content" name="review_Content" cols="30" rows="5"></textarea>
          </div>

          <button type="submit">저장</button>
        </fieldset>
      </div>
  </form>

  <script>
  var memberId = "${member.memberId}";
  var lectureIdMap = {};

  document.addEventListener('DOMContentLoaded', function() {
      loadMyBooking(memberId);
  });

function loadMyBooking(memberId){
    var apiEndPoint = "${apiEndPoint}";
    fetch(apiEndPoint + '/Booking/member/' + memberId, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok){
            return response.json().then(error => {
                throw new Error(error.message);
            })
        }
        return response.json();
    })
    .then(data => {
        const selectBox = document.getElementById('myBookingName');
        data.forEach(booking => {
            const option = document.createElement('option');
            option.value = booking.bookingId;
            option.textContent = booking.lectureName + " - " + booking.requestDate + " - " + booking.requestTime;
            selectBox.appendChild(option);
            lectureIdMap[booking.bookingId] = {
                lectureId: booking.lectureId,
                lectureName: booking.lectureName
            };
            // alert(JSON.stringify(lectureIdMap));
        });
    })
    .catch(error => {
        console.error('Error : ', error);
        alert(error.message);
    })
}

  	CKEDITOR.replace('review_Content');

    function saveit(event) {
        event.preventDefault();
        var bookingId = document.getElementById('myBookingName').value;
        if (!bookingId){
            alert('후기를 작성할 수업을 골라주세요');
            return;
        }

        var title = document.getElementById('review_Title').value;
        var content = CKEDITOR.instances.review_Content.getData();

        var lectureId = lectureIdMap[bookingId].lectureId;
        //alert('lectureId: '+lectureId);
        var lectureName = lectureIdMap[bookingId].lectureName;
        //alert('lectureName: '+lectureName);

        if (!title) {
            alert('후기 게시글 제목을 입력해주세요');
            return;
        }else if (!content){
            alert('후기 게시글 내용을 입력해주세요');
            return;
        }

        fetch('/review', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                memberId: memberId,
                bookingId: bookingId,
                lecture_Id: lectureId,
                lecture_Name: lectureName,
                title: title,
                content: content
            })
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => {
                    throw new Error(error.message);
                })
            }
            return response.json();
        })
        .then(data => {
            console.log("서버 응답:", data);
            alert('게시글이 작성이 완료되었습니다.');
            window.location.href = '/review';
        })
        .catch(error => {
            console.error('문제가 발생했습니다:', error);
            alert(error.message);
        });
    }
  </script>
</body>
</html>