<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Manager Page</title>

  <style>
    #container{
      width: 35em;
      margin: auto;
      text-align: center;
    }
    #container button {
      margin-top: 2em;
      background-color: black;
      color: white;
      border: 1px solid white;
    }
    #container fieldset{
      width: 30em;
      margin:auto;
    }
    #container a {
      font-size: 1em;
    }
    #product_table{
      margin: auto;
      text-align: center;
      width: 30em;
      border: 1px solid white;
      border-collapse: collapse;
    }
    #product_table td{
      text-align: center;
      border: 1px solid white;
    }
    #product_table td:hover {
        cursor: pointer;
        color: yellow;
    }

  </style>

  <link rel="stylesheet" href="../../css/background_black.css">
</head>

<body>
  <%@ include file="../../navigation.jsp" %>

  <div id="container">
    <fieldset>
      <legend>판매용 제품 관리</legend>

      <table id="product_table"></table>

      <button type="button"><a href="/createProduct">제품 등록하기</a></button>

      <div id="pagination" style="margin-top: 1em;"></div>
    </fieldset>
  </div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    loadPage(1);
});

function loadPage(pageNumber, sortOrder = 'asc', sortBy = 'id') {
    currentPage = pageNumber;
    var apiEndPoint = "${apiEndPoint}";
    fetch(apiEndPoint+'/product/all?page='+pageNumber+'&size=10')
    .then(response => response.json())
    .then(data => {
        console.log('Recieved data: '+data);
        let products = data.data;
        // 정렬 조건
        switch (sortBy) {
            case 'id':
                products.sort((a, b) => sortOrder === 'desc' ? b.productId - a.productId : a.productId - b.productId);
                break;
            case 'name':
                products.sort((a, b) => {
                    if (sortOrder === 'asc') {
                        return a.productName.localeCompare(b.productName);
                    } else {
                        return b.productName.localeCompare(a.productName);
                    }
                });
                break;
            case 'status':
                products.sort((a, b) => {
                    if (sortOrder === 'asc') {
                        return a.productStatus.localeCompare(b.productStatus);
                    } else {
                        return b.productStatus.localeCompare(a.productStatus);
                    }
                });
                break;
        }
        updateTable(products);
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
    name: 'asc',
};
function toggleSortOrder(currentOrder) {
    return currentOrder === 'asc' ? 'desc' : 'asc';
}

function updateTable(products) {
    const product_table = document.querySelector("#product_table");
    if (products.length === 0) {
        product_table.innerHTML = "<tr><td colspan='5'>등록된 제품이 없습니다.</td></tr>";
        return;
    }
    let rows = `<tr>
                  <td id="sortById">[제품 번호]</td>
                  <td id="sortByName">[제품 이름]</td>
                  <td>[제품 가격]</td>
                  <td>[제품 수량]</td>
                  <td id="sortByStatus">[제품 상태]</td>
                  </tr>`;
        products.forEach(product => {
            rows +=
              "<tr>"+
              "<td><a href='/product/"+product.productId+"'>" + product.productId + "</a></td>" +
              "<td><a href='/product/"+product.productId+"'>" + product.productName + "</a></td>" +
              "<td><a href='/product/"+product.productId+"'>" + product.productPrice + "</a></td>" +
              "<td><a href='/product/"+product.productId+"'>" + product.productQuantity + "</a></td>" +
              "<td><a href='/product/"+product.productId+"'>" + product.productStatus + "</a></td>" +
              "</tr>";
        });
        product_table.innerHTML = rows;
        bindTableHeaderClicks();
}
function bindTableHeaderClicks() {
    document.getElementById('sortById').addEventListener('click', () => loadPage(currentPage, sortStates['id'] = toggleSortOrder(sortStates['id']), 'id'));
    document.getElementById('sortByName').addEventListener('click', () => loadPage(currentPage, sortStates['name'] = toggleSortOrder(sortStates['name']), 'name'));
    document.getElementById('sortByStatus').addEventListener('click', () => loadPage(currentPage, sortStates['status'] = toggleSortOrder(sortStates['status']), 'status'));
}
</script>
</body>
</html>