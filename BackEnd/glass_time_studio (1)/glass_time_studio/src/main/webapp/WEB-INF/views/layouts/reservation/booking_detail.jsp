<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        #confirm_btn{
            margin-top: 1em;
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
            <span>예약자 아이디</span>
            <span>${booking.memberId}</span>
            </div>

            <div class="class_attribute">
            <span>예약자 휴대폰 번호</span>
            <span>${booking.mobile}</span>
            </div>

            <div class="class_attribute">
            <span>예약자 요청사항</span>
            <span>${booking.requestMessage}</span>
            </div>

            <div class="class_attribute">
            <span>예약상태</span>
            <span>${booking.status}</span>
            </div>

            <div id="confirm_btn">
            <button type="button" onclick="confirm_booking()">예약 확정</button>
            <button type="button" onclick="cancel_booking()">예약 취소</button>
            </div>
        </fieldset>
    </form>


<script>
function confirm_booking() {
    var bookingId = "${booking.bookingId}";
    if(confirm('정말로 예약을 확정하시겠습니까 ?')){
        fetch('/Booking/confirm/'+bookingId, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) throw new Error ('예약 확정 실패');
            return response.json();
        })
        .then(data => {
            alert(data.message);
            window.location.href = '/viewReservation';
        })
        .catch(error => {
            console.error('Error: ', error);
            alert('예약 확정 중 문제가 발생하였습니다.');
        });
    }
}
function cancel_booking() {
    var bookingId = "${booking.bookingId}";
    if(confirm('정말로 예약을 취소하시겠습니까 ?')){
        fetch('/Booking/cancel/'+bookingId, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) throw new Error ('예약 취소 실패');
            return response.json();
        })
        .then(data => {
            alert(data.message);
            window.location.href = '/viewReservation';
        })
        .catch(error => {
            console.error('Error: ', error);
            alert('예약 취소 중 문제가 발생하였습니다.');
        });
    }
}
</script>
</body>
</html>