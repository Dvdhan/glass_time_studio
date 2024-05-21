<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>내 예약 살펴보기</title>
    <style>
        fieldset{
            width: 33em;
            margin: auto;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        #myBooking_table{
          margin: auto;
          text-align: center;
          width: 33em;
          border: 1px solid white;
          border-collapse: collapse;
        }
        #myBooking_table td{
          text-align: center;
          border: 1px solid white;
        }
        #myBooking_table td:hover {
            cursor: pointer;
            color: yellow;
        }
    </style>
</head>
<body>
    <%@ include file="../../navigation.jsp" %>
    <form id="lecture_form" name="form">
        <fieldset>
          <legend>내 예약 살펴보기</legend>

          <table id="myBooking_table"></table>

          <div id="pagination" style="margin-top: 1em;"></div>
        </fieldset>
    </form>





<script>
// 페이지 로드 시 첫 번째 페이지 로드
document.addEventListener('DOMContentLoaded', function() {
    loadPage(1);
});

let currentPage = 1;

function loadPage(pageNumber, sortOrder = 'asc', sortBy = 'id') {
    currentPage = pageNumber;
    var memberId = "${param.memberId}";
    console.log('Loading Page Number: ' + pageNumber + ' with sort order: '+sortOrder);
    fetch('http://localhost:8080/Booking/all/' + memberId + '?page=' + pageNumber + '&size=10')
    .then(response => response.json())
    .then(data => {
        console.log('Received data: '+data);
        let bookings = data.data;
        // 정렬 조건
        switch (sortBy) {
            case 'id':
                bookings.sort((a, b) => sortOrder === 'desc' ? b.booking_Id - a.booking_Id : a.booking_Id - b.booking_Id);
                break;
            case 'status':
                bookings.sort((a, b) => {
                    if (sortOrder === 'asc') {
                        return a.status.localeCompare(b.status);
                    } else {
                        return b.status.localeCompare(a.status);
                    }
                });
                break;
        }
        updateTable(bookings);
        createPagination(data.pageinfo.totalPages, pageNumber);
    })
    .catch(error => console.error('Failed to load page data: ', error));
}

// 페이지네이션 버튼 생성
function createPagination(totalPages, currentPage) {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = ''; // 기존 페이징 버튼 제거
    for (let i = 1; i <= totalPages; i++) {
        const button = document.createElement('button');
        button.style.margin = '2px';
        button.innerText = i;
        button.className = (i === currentPage) ? 'active' : ''; // 현재 페이지를 표시
        button.onclick = () => loadPage(i);
        pagination.appendChild(button);
    }
}

let sortStates = {
    id: 'asc',
    status: 'asc',
};

function toggleSortOrder(currentOrder) {
    return currentOrder === 'asc' ? 'desc' : 'asc';
}
function updateTable(bookings) {
    const table = document.querySelector("#myBooking_table");
    if (bookings.length === 0) {
        table.innerHTML = "<tr><td colspan='7'>생성한 클래스 예약이 없습니다.</td></tr>";
        return;
    }
    let rows = `<tr>
                <td id="sortById">[예약 번호]</td>
                <td>[희망 날짜]</td>
                <td>[희망 시간]</td>
                <td>[수업 이름]</td>
                <td>[예약자 이름]</td>
                <td>[예약 생성 날짜]</td>
                <td id="sortByStatus">[예약 상태]</td>
                </tr>`;
    bookings.forEach(booking => {
        const createdAt = new Date(booking.created_at).toLocaleString();
        rows +=
          "<tr>"+
          "<td><a href='/Booking/myBooking/"+booking.bookingId+"'>" + booking.bookingId + "</a></td>" +
          "<td><a href='/Booking/myBooking/"+booking.bookingId+"'>" + booking.requestDate + "</a></td>" +
          "<td><a href='/Booking/myBooking/"+booking.bookingId+"'>" + booking.requestTime + "</a></td>" +
          "<td><a href='/Booking/myBooking/"+booking.bookingId+"'>" + booking.lectureName + "</a></td>" +
          "<td><a href='/Booking/myBooking/"+booking.bookingId+"'>" + booking.bookerName + "</a></td>" +
          "<td><a href='/Booking/myBooking/"+booking.bookingId+"'>" + createdAt + "</a></td>" +
          "<td><a href='/Booking/myBooking/"+booking.bookingId+"'>" + booking.status + "</a></td>"
          "</tr>";
    });
    table.innerHTML = rows;
    bindTableHeaderClicks();
}
function bindTableHeaderClicks() {
    document.getElementById('sortById').addEventListener('click', () => loadPage(currentPage, sortStates['id'] = toggleSortOrder(sortStates['id']), 'id'));
    document.getElementById('sortByStatus').addEventListener('click', () => loadPage(currentPage, sortStates['status'] = toggleSortOrder(sortStates['status']), 'status'));
}
</script>
</body>
</html>