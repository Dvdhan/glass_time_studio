<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>내 예약 살펴보기</title>
    <style>
        fieldset{
            width: 34em;
            margin: auto;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        #myOrder_table{
          margin: auto;
          text-align: center;
          width: 34em;
          border: 1px solid white;
          border-collapse: collapse;
        }
        #myOrder_table td{
          text-align: center;
          border: 1px solid white;
          font-size: 27px;
        }
        #myOrder_table td:hover {
            cursor: pointer;
            color: yellow;
        }
    </style>
</head>
<body>
    <%@ include file="../../navigation.jsp" %>
    <form id="lecture_form" name="form">
        <fieldset>
          <legend>내 구매내역</legend>

          <table id="myOrder_table"></table>

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
    var apiEndPoint = "${apiEndPoint}";
    currentPage = pageNumber;
    var memberId = "${param.memberId}";
    console.log('Loading Page Number: ' + pageNumber + ' with sort order: '+sortOrder);
    fetch(apiEndPoint+'/order/all?page=' + pageNumber + '&size=10')
    .then(response => response.json())
    .then(data => {
        console.log('Received data: '+data);
        let orders = data.data;
        // 정렬 조건
        switch (sortBy) {
            case 'id':
                orders.sort((a, b) => sortOrder === 'desc' ? b.order_Id - a.order_Id : a.order_Id - b.order_Id);
                break;
            case 'orderStatus':
                orders.sort((a, b) => {
                    if (sortOrder === 'asc') {
                        return a.orderStatus.localeCompare(b.orderStatus);
                    } else {
                        return b.orderStatus.localeCompare(a.orderStatus);
                    }
                });
                break;
        }
        updateTable(orders);
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
    orderStatus: 'asc',
};

function toggleSortOrder(currentOrder) {
    return currentOrder === 'asc' ? 'desc' : 'asc';
}
function updateTable(orders) {
    var memberId = "${param.memberId}";
    const table = document.querySelector("#myOrder_table");
    if (orders.length === 0 || !orders) {
        table.innerHTML = "<tr><td colspan='8'>생성된 주문이 없습니다.</td></tr>";
        return;
    }
    let rows = `<tr>
                <td id="sortById">[주문 번호]</td>
                <td>[주문 날짜]</td>
                <td>[제품 이름]</td>
                <td>[구매 수량]</td>
                <td>[결제 금액]</td>
                <td>[주문자 성함]</td>
                <td>[주문 배송지]</td>
                <td id="sortByStatus">[주문 상태]</td>
                </tr>`;
    orders.forEach(order => {
        const createdAt = new Date(order.created_at).toLocaleString();
        // 공백 기준으로 나눔.
        const dateParts = createdAt.split(' ');
        // 첫번째, 두번째, 세번째 조합하여 yyyy.mm.dd 형태로 생성
        const newDate = dateParts.slice(0, 3).join(' ');
        rows +=
          "<tr>"+
          "<td><a href='/order/"+order.orderId+"'>" + order.orderId + "</a></td>" +
          "<td><a href='/order/"+order.orderId+"'>" + newDate + "</a></td>" +
          "<td><a href='/order/"+order.orderId+"'>" + order.productName + "</a></td>" +
          "<td><a href='/order/"+order.orderId+"'>" + order.productQuantity + "</a></td>" +
          "<td><a href='/order/"+order.orderId+"'>" + order.orderPayment + "</a></td>" +
          "<td><a href='/order/"+order.orderId+"'>" + order.memberName + "</a></td>" +
          "<td><a href='/order/"+order.orderId+"'>" + order.address + "</a></td>" +
          "<td><a href='/order/"+order.orderId+"'>" + order.orderStatus + "</a></td>" +
          "</tr>";
    });
    table.innerHTML = rows;
    bindTableHeaderClicks();
}
function bindTableHeaderClicks() {
    document.getElementById('sortById').addEventListener('click', () => loadPage(currentPage, sortStates['id'] = toggleSortOrder(sortStates['id']), 'id'));
    document.getElementById('sortByStatus').addEventListener('click', () => loadPage(currentPage, sortStates['orderStatus'] = toggleSortOrder(sortStates['orderStatus']), 'orderStatus'));
}
</script>
</body>
</html>