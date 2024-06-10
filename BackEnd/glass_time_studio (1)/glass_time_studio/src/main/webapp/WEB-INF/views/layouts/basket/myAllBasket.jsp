<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="David.glass_time_studio.domain.member.entity.Member" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>내 장바구니 내역</title>
  <link rel="stylesheet" href="../../css/background_black.css">

  <style>
    #empty_letter{
        width: 10em;
        margin: auto;
        padding: 10px;
    }
    #product_list{
      display: grid;
      width: 34em;
      grid-template-columns: repeat(3, 30%);
      justify-content: center;
      gap: 5px 5px;
      margin: auto;
    }
    .buttons{
      margin-top: 1em;
      text-align: center;
    }
    fieldset img{
      width: 100%;
      height: 350px;
      object-fit: contain;
      margin-bottom: 0.7em;
    }
    p{
      text-align: center;
    }
    .detail{
      color: black;
    }
    .detail:hover{
      color: blue;
    }
    fieldset{
      height: 27em;
    }
    #pagination {
        text-align: center;
        margin: auto;
        width: 1em;
    }
    button{
        margin: 10px;
    }
    #search_area{
        margin: auto;
        width: 20em;
        text-align: center;
        margin-bottom: 2em;
    }
    .exp{
        display: block;
        margin-top: 20px;
    }
  </style>
</head>
<body>
  <%@ include file="../../navigation.jsp" %>
  <div id="product_list"></div>
  <div id="empty_letter"></div>
  <div id="pagination"></div>

  <% Member member = (Member) request.getAttribute("member"); %>

  <div id="search_area" style="margin-top: 1em;">
  <input type="text" id="keyword" name="keyword" onkeypress="handleKeyPress(event)">
  <button type="button" onClick="search_keyword()">검색하기</button>
  </div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    loadPage(1);
});

function loadPage(pageNumber, sortOrder = 'asc', sortBy = 'id') {
    currentPage = pageNumber;
    var apiEndPoint = "${apiEndPoint}";
    fetch(apiEndPoint+'/basket/all/${member.memberId}?page='+pageNumber+'&size=9')
    .then(response => {
        return response.json();
    })
    .then(data => {
        // 응답의 message 부분.
        let baskets = data.data;
        if(data.message != null && data.message == "찾으시는 장바구니 내역이 없습니다."){
            updateDiv(baskets, apiEndPoint);
        }
        else {
            updateDiv(baskets, apiEndPoint);
            createPagination(data.pageinfo.totalPages, pageNumber);
        }
    })
    .catch(error => {
        console.error('Failed to load page data: ', error);
    });
}
function updateDiv(baskets, apiEndPoint) {
    console.log('baskets: '+JSON.stringify(baskets));

    const product_div = document.querySelector("#product_list");
    const empty_letter = document.querySelector("#empty_letter");
    if (!baskets || baskets.length === 0) {
        product_div.innerHTML = '';
        empty_letter.innerHTML = "<h1>장바구니 내역이 없습니다.</h1>";
        return;
    }

    empty_letter.innerHTML = ''; // 장바구니 데이터가 있을 때 empty_letter 초기화

    Promise.allSettled(baskets.map(basket => {
        return fetch(apiEndPoint + "/product/view/"+basket.productId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => {
                    throw new Error(error.message);
                });
            }
            return response.json().then(product => {
                return {basket, product};
            });
        })
        .catch(error => {
            alert(error.message);
        });
    }))
    .then(results => {
        let target = '';
        results.forEach(result => {
            if (result.status === "fulfilled") {
                const { basket, product } = result.value;
                target += createProductHTML(basket, product);
            } else {
                console.error('Product fetch failed:', result.reason);
            }
        });
        product_div.innerHTML = target;
    });
}

function createProductHTML(basket, product) {
    var memberId = "${member.memberId}";
    return "<fieldset>" +
        "<img src='" + product.mainPhotoUrl + "' alt='Product Image'>" +
        "<span>[제품명]</span>" +
        "<div>" + product.productName + "</div>" +
        "<span class='exp'>[가격]</span>" +
        "<div>" + product.productPrice + " 원</div>" +
        "<span class='exp'>[재고]</span>" +
        "<div>" + product.productQuantity + "</div>" +
        "<span class='exp'>[판매 상태]</span>" +
        "<div>" + product.productStatus + "</div>" +
        "<div class='buttons'>" +
            "<button><a class='detail' href='/product_detail/" + product.productId + "'>상세보기</a></button>" +
            "<button><a class='detail' target='_blank' href='https://smartstore.naver.com/glasstime/products/9846789288'>구매하기</a></button>" +
            "<button><a class='detail' href='#' onclick='deleteFromBasket(" + memberId + ", " + basket.basketId + ")'>장바구니 삭제</a></button>" +
        "</div>" +
        "</fieldset>";
}
function addToBasket(memberId, productId ){
    var apiEndPoint = "${apiEndPoint}";
    fetch(apiEndPoint+"/basket",{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            memberId: memberId,
            productId: productId
        })
    })
    .then(response => {
        if(!response.ok){
            return response.json().then(error => {
                throw new Error(error.message);
            })
        }
        return response.json();
    })
    .then(data => {
        console.log("서버 응답: ", data);
        alert('장바구니 추가 완료되었습니다.');
        window.location.href = "/productList";
    })
    .catch(error => {
        console.error('문제가 발생했습니다: ', error);
        alert(error.message);
    });
}
function deleteFromBasket(memberId, basketId){
    console.log('회원ID: '+memberId);
    console.log('장바구니ID: '+basketId);
    var apiEndPoint = "${apiEndPoint}";
    if(confirm('해당 제품을 장바구니에서 삭제하시겠습니까?')){
        fetch(apiEndPoint+"/basket/"+memberId+"/"+basketId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            return response.json().then(data => {
              if (!response.ok){
                  throw new Error(data.message);
              }
              return data;
            });
        })
        .then(data => {
            alert(data.message);
            window.location.href = '/myAllBasket';
        })
        .catch(error => {
            alert(error.message);
        });
    }
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

function search_keyword() {
    var apiEndPoint = "${apiEndPoint}";
    let keyword = document.getElementById('keyword').value;
    console.log("검색어 ", keyword);
    if(!keyword.trim()) {
        alert('검색어를 입력해 주세요');
        return;
    }
    const encodedKeyword = encodeURIComponent(keyword);
    fetch(apiEndPoint+"/product/search?keyword="+encodedKeyword)
    .then(response => {
        if (!response.ok) {
            throw new Error('검색 결과를 가져오는데 실패했습니다.');
        }
        return response.json();
    })
    .then(data => {
        console.log('data: ' + data);
        if(!data || data.length === 0) {
            alert(encodedKeyword + '로 검색된 결과가 없습니다.');
            return;
        }

        const products = data;
        const product_div = document.querySelector("#product_list");
        const empty_letter = document.querySelector("#empty_letter");

        if(products.length <= 0) {
            empty_letter.innerHTML = "<h1>죄송합니다<br>현재 판매 중인 제품이 없습니다.</h1>";
            return;
        } else {
            let target ='';
            products.forEach(product => {
                target +=
                    "<fieldset>"+
                    "<img src='" + product.mainPhotoUrl + "'>"+
                    "<p>제품명 : <span id='s_product_title'>" + product.productName + "</span></p>" +
                    "<p>가격 : <span id='s_product_price'>" + product.productPrice + " 원</span></p>" +
                    "<p>재고 : <span id='s_product_quantity'>" + product.productQuantity + "</span></p>" +
                    "<p>판매 상태 : <span id='s_product_status'>" + product.productStatus + "</span></p>" +
                    "<div class='buttons'>"+
                    "<button><a class='detail' href='/product_detail/" + product.productId + "'>상세보기</a></button>" +
                    "<button><a class='detail' target='_blank' href='https://smartstore.naver.com/glasstime/products/9846789288'>구매하기</a></button>" +
                    "<button><a class='detail' href='#' onclick='addToBasket("+product.productId+")'>장바구니 담기</a></button>" +
                    "</div>" +
                    "</fieldset>";
            });
            product_div.innerHTML = target;
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

</script>
</body>
</html>