<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="David.glass_time_studio.domain.booking.dto.BookingDto" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>예약 수정</title>
    <style>
        fieldset{
            width: 30em;
            margin: auto;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .class_attribute{
            width: 14em;
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 0.5em;
        }
        #update_btn {
            margin-top: 1em;
        }
        .button{
          border: 1px solid white;
          padding-left: 3px;
          padding-right: 3px;
          display: inline-block;
          margin-left: auto;
          margin: 5px;
        }
        #talktalk{
            color: white;
            background-color: black;
            border: 1px solid white;
            margin-top: 0.5em;
        }

    </style>
</head>
<body>
    <%@ include file="../../navigation.jsp" %>
    <form>
        <fieldset>
            <legend>예약 수정</legend>
            <div class="class_attribute">
            <span>수업 이름</span>
            <span>${booking.lectureName}</span>
            </div>

            <div class="class_attribute">
            <span>예약 희망 날짜</span>
            <span>${booking.requestDate}</span>
            </div>

            <div class="class_attribute">
            <span>예약 희망 시간</span>
            <span>${booking.requestTime}</span>
            </div>

            <div class="class_attribute">
            <span>예약자명</span>
            <span>${booking.bookerName}</span>
            </div>

            <div class="class_attribute">
            <span>예약자 휴대폰 번호</span>
            <span>${booking.mobile}</span>
            </div>

            <div class="class_attribute">
            <span>예약 인원 수</span>
            <span>${booking.peopleNumber}</span>
            </div>

            <div class="class_attribute">
            <span>예약자 요청사항</span>
            <span>${booking.requestMessage}</span>
            </div>

            <div class="class_attribute">
            <span>예약상태</span>
            <span>${booking.status}</span>
            </div>

            <%
                BookingDto.Response booking = (BookingDto.Response) request.getAttribute("booking");
                if( !"Y".equals(booking.getStatus())) {
            %>
                <div id="update_btn">
                    <a class="button" href="/update_myBooking_detail?bookingId=${booking.bookingId}&lectureName=${booking.lectureName}&requestDate=${booking.requestDate}&requestTime=${booking.requestTime}&bookerName=${booking.bookerName}&mobile=${booking.mobile}&peopleNumber=${booking.peopleNumber}&requestMessage=${booking.requestMessage}">예약 정보 수정하기</a>
                    <a class="button" onclick="deleteBooking()">예약 취소하기</a>
                </div>
            <%
                } else {
            %>
                <div id="update_btn">
                    <a class="button">예약이 확정되어 변경이 어렵습니다.<br>
                    아래 네이버 톡톡 문의하기를 이용해주세요.</a>
                  <div id="asking">
                    <button id="talktalk"><a target="_blank" href="https://talk.naver.com/ct/w4fpms?frm=pblog#nafullscreen">네이버 톡톡 문의하기</a></button>
                  </div>
                </div>
            <%
                }
            %>
        </fieldset>
    </form>
<script>
function deleteBooking(){
    let memberId = "${member.memberId}";
    let bookingId = "${booking.bookingId}";

    if(confirm('정말로 예약을 취소하시겠습니까 ?')){
        fetch('/Booking/' + memberId + '/'+bookingId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) throw new Error ('삭제 실패');
            return response.json();
        })
        .then(data => {
            alert(data.message);
            window.location.href = '/myBooking?memberId='+memberId;
        })
        .catch(error => {
            console.error('Error: ', error);
            alert('삭제 중 문제가 발생했습니다.');
        });
    }
}
</script>
</body>
</html>