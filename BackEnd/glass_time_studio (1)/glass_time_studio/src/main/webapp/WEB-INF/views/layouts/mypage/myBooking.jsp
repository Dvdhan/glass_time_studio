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
        #update_btn {
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

            <div id="update_btn">
                <button>예약 정보 수정하기</button>
            </div>
        </fieldset>
    </form>


<script>

</script>
</body>
</html>