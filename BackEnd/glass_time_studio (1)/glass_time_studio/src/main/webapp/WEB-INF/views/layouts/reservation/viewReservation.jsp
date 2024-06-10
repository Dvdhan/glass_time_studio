<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>수업 리스트</title>
    <style>
        fieldset{
            width: 35em;
            margin: auto;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        table{
          margin: auto;
          text-align: center;
          width: 35em;
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

          <table id="unfixed_booking_table"></table>

          <div id="search_area" style="margin-top: 1em;">
          <input type="text" id="keyword1" name="keyword1" onkeypress="handleKeyPress1(event)">
          <button type="button" onClick="search_keyword1()">검색하기(예약자 이름)</button>
          </div>

          <div id="pagination1" style="margin-top: 1em;"></div>
        </fieldset>
        <br>
        <fieldset>
          <legend>[확정] 클래스 예약 리스트</legend>

          <table id="fixed_booking_table"></table>

          <div id="search_area" style="margin-top: 1em;">
          <input type="text" id="keyword2" name="keyword2" onkeypress="handleKeyPress2(event)">
          <button type="button" onClick="search_keyword2()">검색하기(예약자 이름)</button>
          </div>

          <div id="pagination2" style="margin-top: 1em;"></div>
        </fieldset>
    </form>





<script>
// 페이지 로드 시 첫 번째 페이지 로드
document.addEventListener('DOMContentLoaded', function() {
    loadPage(1);
});

let currentPage = 1;

function loadPage(pageNumber, sortOrder = 'asc', sortBy = 'id') {
    var apiEndPoint = "${apiEndPoint}";
    currentPage = pageNumber;
    console.log('Loading Page Number: '+pageNumber + ' with sort order: '+sortOrder);
    fetch(apiEndPoint+'/Booking/all?page='+pageNumber+'&size=10')
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
    const pagination1 = document.getElementById('pagination1');
    const pagination2 = document.getElementById('pagination2');
    pagination1.innerHTML = ''; // 기존 페이징 버튼 제거
    pagination2.innerHTML = ''; // 기존 페이징 버튼 제거
    for (let i = 1; i <= totalPages; i++) {
        const button1 = document.createElement('button');
        button1.style.margin = '2px';
        button1.innerText = i;
        button1.className = (i === currentPage) ? 'active' : ''; // 현재 페이지를 표시
        button1.onclick = () => loadPage(i);
        pagination1.appendChild(button1);

        const button2 = document.createElement('button');
        button2.style.margin = '2px';
        button2.innerText = i;
        button2.className = (i === currentPage) ? 'active' : ''; // 현재 페이지를 표시
        button2.onclick = () => loadPage(i);
        pagination2.appendChild(button2);
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
    const unfixed_table = document.querySelector("#unfixed_booking_table");
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
        unfixed_table.innerHTML = "<tr><td colspan='7'>미확정 클래스 예약이 없습니다.</td></tr>";
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
        fixed_table.innerHTML = "<tr><td colspan='7'>확정 클래스 예약이 없습니다.</td></tr>";
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

function search_keyword1(){
    var apiEndPoint = "${apiEndPoint}";
    let keyword1 = document.getElementById('keyword1').value;
    console.log("검색어 ", keyword1);

    if(!keyword1.trim()) {
        alert('검색어를 입력해 주세요');
        return;
    }

    const encodedKeyword1 = encodeURIComponent(keyword1);
    fetch(apiEndPoint+"/Booking/search_N?keyword="+encodedKeyword1)
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
        const no_fixed_table = document.querySelector("#unfixed_booking_table");
        let rows = "<tr><td>[예약 번호]</td><td>[예약 신청 날짜]</td><td>[신청 시간]</td><td>[수업 이름]</td><td>[예약자 이름]</td><td>[예약 상태]</td><td>[예약 생성 날짜]</td></tr>";

        if(bookings.length <= 0) {
            no_fixed_table.innerHTML = "<tr><td colspan='7'> 검색된 결과가 없습니다.</td></tr>";
        } else {
            bookings.forEach(booking => {
                const createdAt = new Date(booking.created_at).toLocaleString();
                rows +=
                  "<tr>"+
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.bookingId + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.requestDate + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.requestTime + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.lectureName + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.bookerName + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + createdAt + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.status + "</a></td>"+
                  "</tr>";
            });
            no_fixed_table.innerHTML = rows;
        }
    })
    .catch(error => {
        console.error('Error during fetch operation: ', error);
        alert('검색 중 오류가 발생했습니다.');
    });
}
function search_keyword2(){
    var apiEndPoint = "${apiEndPoint}";
    let keyword2 = document.getElementById('keyword2').value;
    console.log("검색어 ", keyword2);

    if(!keyword2.trim()) {
        alert('검색어를 입력해 주세요');
        return;
    }

    const encodedKeyword2 = encodeURIComponent(keyword2);
    fetch(apiEndPoint+"/Booking/search_Y?keyword="+encodedKeyword2)
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
        const ok_fixed_table = document.querySelector("#fixed_booking_table");
        let rows = "<tr><td>[예약 번호]</td><td>[예약 신청 날짜]</td><td>[신청 시간]</td><td>[수업 이름]</td><td>[예약자 이름]</td><td>[예약 상태]</td><td>[예약 생성 날짜]</td></tr>";

        if(bookings.length <= 0) {
            ok_fixed_table.innerHTML = "<tr><td colspan='7'> 검색된 결과가 없습니다.</td></tr>";
        } else {
            bookings.forEach(booking => {
                const createdAt = new Date(booking.created_at).toLocaleString();
                rows +=
                  "<tr>"+
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.bookingId + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.requestDate + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.requestTime + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.lectureName + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.bookerName + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + createdAt + "</a></td>" +
                  "<td><a href='/Booking/"+booking.bookingId+"'>" + booking.status + "</a></td>"+
                  "</tr>";
            });
            ok_fixed_table.innerHTML = rows;
        }
    })
    .catch(error => {
        console.error('Error during fetch operation: ', error);
        alert('검색 중 오류가 발생했습니다.');
    });
}

function handleKeyPress1(event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // 기본 제출 동작을 방지
        search_keyword1();
    }
}
function handleKeyPress2(event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // 기본 제출 동작을 방지
        search_keyword2();
    }
}
</script>
</body>
</html>