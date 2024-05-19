<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>수업 리스트</title>
    <style>
        fieldset{
            width: 32em;
            margin: auto;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        table{
          margin: auto;
          text-align: center;
          width: 32em;
          border: 1px solid white;
          border-collapse: collapse;
        }
        table td{
          text-align: center;
          border: 1px solid white;
        }
        table td:hover {
            cursor: pointer;
            color: yellow;
        }
    </style>
</head>
<body>
    <%@ include file="../../navigation.jsp" %>
    <form id="lecture_form" name="form">
        <fieldset>
          <legend>[미확정] 클래스 예약 리스트</legend>

          <table id="lecture_table"></table>

          <div id="search_area" style="margin-top: 1em;">
          <input type="text" id="keyword" name="keyword" onkeypress="handleKeyPress(event)">
          <button type="button" onClick="search_keyword()">검색하기(예약자 이름)</button>
          </div>

          <div id="pagination" style="margin-top: 1em;"></div>
        </fieldset>
        <br>
        <fieldset>
          <legend>[확정] 클래스 예약 리스트</legend>

          <table id="fixed_booking_table"></table>

          <div id="search_area" style="margin-top: 1em;">
          <input type="text" id="keyword" name="keyword" onkeypress="handleKeyPress(event)">
          <button type="button" onClick="search_keyword()">검색하기(예약자 이름)</button>
          </div>

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
    console.log('Loading Page Number: '+pageNumber + ' with sort order: '+sortOrder);
    fetch('http://localhost:8080/Booking/all?page='+pageNumber+'&size=10')
    .then(response => response.json())
    .then(data => {
        console.log('Received data: '+data);
        let bookings = data.data;
        // 정렬 조건
        switch (sortBy) {
            case 'id':
                bookings.sort((a, b) => sortOrder === 'desc' ? b.bookingId - a.bookingId : a.bookingId - b.bookingId);
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
    const unfixed_table = document.querySelector("#lecture_table");
    const fixed_table = document.querySelector("#fixed_booking_table");

    let unfixedBookings = [];
    let fixedBookings = [];

    bookings.forEach(booking => {
        if (booking.status === "N") {
            unfixedBookings.push(booking);
        } else if (booking.status === "Y") {
            fixedBookings.push(booking);
        }
    });


    if (unfixedBookings.length === 0) {
        unfixed_table.innerHTML = "<tr><td colspan='7'>생성된 클래스 예약이 없습니다.</td></tr>";
    } else {
        let rows_N = `<tr>
                      <td id="sortById">[예약 번호]</td>
                      <td>[희망 날짜]</td>
                      <td>[희망 시간]</td>
                      <td>[수업 이름]</td>
                      <td>[예약자 이름]</td>
                      <td>[예약 생성 날짜]</td>
                      <td id="sortByStatus">[예약 상태]</td>
                      </tr>`;
        unfixedBookings.forEach(booking => {
            const createdAt = new Date(booking.created_at).toLocaleString();
            rows_N +=
              "<tr>"+
              "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.bookingId + "</a></td>" +
              "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.requestDate + "</a></td>" +
              "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.requestTime + "</a></td>" +
              "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.lectureName + "</a></td>" +
              "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.bookerName + "</a></td>" +
              "<td><a href='/Booking/"+booking.bookingId+"'>" + createdAt + "</a></td>" +
              "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.status + "</a></td>"
              "</tr>";
        });
        unfixed_table.innerHTML = rows_N;
    }

    if (fixedBookings.length === 0) {
        fixed_table.innerHTML = "<tr><td colspan='7'>생성된 클래스 예약이 없습니다.</td></tr>";
    } else {
        let rows_Y = `<tr>
                      <td id="sortById">[예약 번호]</td>
                      <td>[희망 날짜]</td>
                      <td>[희망 시간]</td>
                      <td>[수업 이름]</td>
                      <td>[예약자 이름]</td>
                      <td>[예약 생성 날짜]</td>
                      <td id="sortByStatus">[예약 상태]</td>
                      </tr>`;
        fixedBookings.forEach(booking => {
            const createdAt = new Date(booking.created_at).toLocaleString();
            rows_Y +=
              "<tr>"+
              "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.bookingId + "</a></td>" +
              "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.requestDate + "</a></td>" +
              "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.requestTime + "</a></td>" +
              "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.lectureName + "</a></td>" +
              "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.bookerName + "</a></td>" +
              "<td><a href='/Booking/"+booking.bookingId+"'>" + createdAt + "</a></td>" +
              "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.status + "</a></td>"
              "</tr>";
        });
        fixed_table.innerHTML = rows_Y;
    }
    bindTableHeaderClicks();
}
function bindTableHeaderClicks() {
    document.getElementById('sortById').addEventListener('click', () => loadPage(currentPage, sortStates['id'] = toggleSortOrder(sortStates['id']), 'id'));
    document.getElementById('sortByStatus').addEventListener('click', () => loadPage(currentPage, sortStates['status'] = toggleSortOrder(sortStates['status']), 'status'));
}

function search_keyword(){
    let keyword = document.getElementById('keyword').value;
    console.log("검색어 ", keyword);

    if(!keyword.trim()) {
        alert('검색어를 입력해 주세요');
        return;
    }

    const encodedKeyword = encodeURIComponent(keyword);
    fetch("http://localhost:8080/Booking/search?keyword="+encodedKeyword)
    .then(response => {
        if (!response.ok) {
            throw new Error('검색 결과를 가져오는데 실패했습니다.');
        }
        return response.json();
    })
    .then(data => {
        console.log('data: '+data);
        if(!data || data.length === 0) {
            alert('검색된 결과가 없습니다');
            return;
        }

        const bookings = data;
        const table = document.querySelector("#lecture_table");
        let rows = "<tr><td>[예약 번호]</td><td>[예약 신청 날짜]</td><td>[신청 시간]</td><td>[수업 이름]</td><td>[예약자 이름]</td><td>[예약 상태]</td><td>[예약 생성 날짜]</td></tr>";

        if(bookings.length <= 0) {
            table.innerHTML = "<tr><td colspan='7'> 검색된 결과가 없습니다.</td></tr>";
        } else {
            bookings.forEach(booking => {
                rows +=
                  "<tr>"+
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.bookingId + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.requestDate + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.requestTime + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.lectureName + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.bookerName + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + createdAt + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.status + "</a></td>"
                  "</tr>";
            });
            table.innerHTML = rows;
        }
    })
    .catch(error => {
        console.error('Error during fetch operation: ', error);
        alert('검색 중 오류가 발생했습니다.');
    });
}

function handleKeyPress(event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // 기본 제출 동작을 방지
        search_keyword();
    }
}

function submitForm() {
    let class_name = document.getElementById('class_title_input').value;
    let class_period = document.getElementById('class_period_input').value;
    let class_cost = document.getElementById('class_cost_input').value.replace(/,/g, '');
    let class_description = document.getElementById('class_description_input').value;
    let class_status = document.getElementById('class_status_value').value;

    if(class_name === "none" || !class_name){
        alert('수업 이름은 필수값 입니다.');
        return false;
    }else if(class_status ==="none" || !class_status){
        alert('수업 상태는 필수 값 입니다.');
        return false;
    }

    fetch('/lecture', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            lecture_Name: class_name,
            lecture_Period: class_period,
            lecture_Price: parseInt(class_cost),
            lecture_Description: class_description,
            status: class_status
        })
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        }
        throw new Error('Network response was not ok');
    })
    .then(data => {
        console.log("서버 응답: ", data);
        alert('클래스 추가 완료');
        window.location.href="/class";
    })
    .catch(error => {
        console.error('문제가 발생했습니다: ', error);
    });
}

</script>
</body>
</html>