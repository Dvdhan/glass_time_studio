<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Review</title>
  <link rel="stylesheet" href="../../css/background_black.css">
  <style>
    #post-list, #post-list li{
      list-style: none;
      text-align: center;
      margin: auto;
      width: 35em;
    }
    #add{
      display: inline-block;
      border: 1px solid white;
      margin-top: 1em;
      padding : 10px;
      margin-left: 0.8em;
    }
    #review_btns{
      text-align: center;
    }
    #cont{
      margin: auto;
      width: 32em;
    }
    #cont_table{
      margin: auto;
      text-align: center;
      width: 32em;
      border: 1px solid white;
      border-collapse: collapse;
    }
    #cont_table td{
      text-align: center;
      border: 1px solid white;
    }
    #cont_table td:hover {
        cursor: pointer;
        color: yellow;
    }
  </style>
</head>
<body>
  <%@ include file="../../navigation.jsp" %>
  <div id="post-list">
    <fieldset>
      <legend>
        <h2>&nbsp;&nbsp;클래스 수강생 후기 게시글&nbsp;&nbsp;</h2>
      </legend>
      <div id="cont">
        <table id="cont_table">
        </table>
      </div>
      <div id="search_area" style="margin-top: 1em;">
      <input type="text" id="keyword" name="keyword" onkeypress="handleKeyPress(event)">
      <button onClick="search_keyword();">검색하기</button>
      </div>
      <div id="pagination" style="margin-top: 1em;"></div>
    </fieldset>
  </div>

  <div id="review_btns">
      <a href="/review/write?memberId=${member.memberId}" id="add">후기 게시글 작성하기</a>
  </div>

<script>
// 페이지 로드 시 첫 번째 페이지 로드
document.addEventListener('DOMContentLoaded', function() {
    loadPage(1);
});

let currentPage = 1; // 현재 페이지 상태를 전역으로 관리

// 페이지와 정렬을 동시에 처리하는 함수
function loadPage(pageNumber, sortOrder = 'asc', sortBy = 'id') {
    let apiEndPoint = "${apiEndPoint}";
    currentPage = pageNumber;  // 현재 페이지 업데이트
    console.log('Loading page number: ' + pageNumber + ' with sort order: ' + sortOrder);
    fetch(apiEndPoint+'/review/all?page='+pageNumber+'&size=10')
    .then(response => response.json())
    .then(data => {
        console.log('Received data:', data);
        let reviews = data.data;
        // 정렬 조건 확인 및 적용
        switch (sortBy) {
            case 'id':
                reviews.sort((a, b) => sortOrder === 'desc' ? b.reviewId - a.reviewId : a.reviewId - b.reviewId);
                break;
            case 'lecture_Name':
                reviews.sort((a, b) => sortOrder === 'desc' ? b.lecture_Name.localeCompare(a.lecture_Name) : a.lecture_Name.localeCompare(b.lecture_Name));
                break;
            case 'created_at':
                reviews.sort((a, b) => sortOrder === 'desc' ? new Date(b.created_at) - new Date(a.created_at) : new Date(a.created_at) - new Date(b.created_at));
                break;
            case 'modified_at':
                reviews.sort((a, b) => sortOrder === 'desc' ? new Date(b.modified_at) - new Date(a.modified_at) : new Date(a.modified_at) - new Date(b.modified_at));
                break;
        }
        updateTable(reviews);  // 실제 공지사항 데이터 업데이트
        createPagination(data.pageinfo.totalPages, pageNumber);  // 페이지 버튼 재생성
    })
    .catch(error => console.error('Failed to load page data:', error));
}


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
    lecture_Name: 'asc',
    created: 'asc',
    modified: 'asc'
};

function toggleSortOrder(currentOrder) {
    return currentOrder === 'asc' ? 'desc' : 'asc';
}

function updateTable(reviews) {
    const table = document.querySelector("#cont_table");
    if (reviews.length === 0 || !reviews) {
        table.innerHTML = "<tr><td colspan='5'>작성된 클래스 수강생 후기 게시글이 없습니다.</td></tr>";
        return;
    }
      let rows = `<tr>
                  <td id="sortById">[게시글 번호]</td>
                  <td id="sortByLectureName">[수업 이름]</td>
                  <td>[후기 제목]</td>
                  <td id="sortByCreated">[작성일]</td>
                  <td id="sortByModified">[수정일]</td>
                  </tr>`;
    reviews.forEach(review => {
        const createdAt = new Date(review.created_at).toLocaleString();
        const modifiedAt = new Date(review.modified_at).toLocaleString();
        rows +=
          "<tr>"+
          "<td><a href='/review/"+review.reviewId+"'>" + review.reviewId + "</a></td>" +
          "<td><a href='/review/"+review.reviewId+"'>" + review.lecture_Name + "</a></td>" +
          "<td><a href='/review/"+review.reviewId+"'>" + review.title + "</a></td>" +
          "<td><a href='/review/"+review.reviewId+"'>" + createdAt + "</a></td>"+
          "<td><a href='/review/"+review.reviewId+"'>" + modifiedAt + "</a></td>"+
          "</tr>";
    });
    table.innerHTML = rows;
    bindTableHeaderClicks();
}
function bindTableHeaderClicks() {
    document.getElementById('sortById').addEventListener('click', () => loadPage(currentPage, sortStates['id'] = toggleSortOrder(sortStates['id']), 'id'));
    document.getElementById('sortByLectureName').addEventListener('click', () => loadPage(currentPage, sortStates['lecture_Name'] = toggleSortOrder(sortStates['lecture_Name']), 'lecture_Name'));
    document.getElementById('sortByCreated').addEventListener('click', () => loadPage(currentPage, sortStates['created_at'] = toggleSortOrder(sortStates['created_at']), 'created_at'));
    document.getElementById('sortByModified').addEventListener('click', () => loadPage(currentPage, sortStates['modified_at'] = toggleSortOrder(sortStates['modified_at']), 'modified_at'));
}

function search_keyword(){
    var apiEndPoint = "${apiEndPoint}";
    let keyword = document.getElementById('keyword').value;
    console.log("검색어 ", keyword);
    if(!keyword.trim()) {
        alert('검색어를 입력해 주세요');
        return;
    }

    const encodedKeyword = encodeURIComponent(keyword);
    fetch(apiEndPoint+"/review/searchLectureName?keyword="+encodedKeyword)
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

        const reviews = data;
        const table = document.querySelector("#cont_table");
        let rows = `<tr>
                    <td id="sortById">[게시글 번호]</td>
                    <td id="sortByLectureName">[수업 이름]</td>
                    <td>[후기 제목]</td>
                    <td id="sortByCreated">[작성일]</td>
                    <td id="sortByModified">[수정일]</td>
                    </tr>`;

        if(reviews.length === 0 || !reviews) {
            table.innerHTML = "<tr><td colspan='5'>작성된 클래스 수강생 후기 게시글이 없습니다.</td></tr>";
        } else {
            reviews.forEach(review => {
                const createdAt = new Date(review.created_at).toLocaleString();
                const modifiedAt = new Date(review.modified_at).toLocaleString();
                rows +=
                  "<tr>"+
                  "<td><a href='/review/"+review.reviewId+"'>" + review.reviewId + "</a></td>" +
                  "<td><a href='/review/"+review.reviewId+"'>" + review.lecture_Name + "</a></td>" +
                  "<td><a href='/review/"+review.reviewId+"'>" + review.title + "</a></td>" +
                  "<td><a href='/review/"+review.reviewId+"'>" + createdAt + "</a></td>"+
                  "<td><a href='/review/"+review.reviewId+"'>" + modifiedAt + "</a></td>"+
                  "</tr>";
            });
            table.innerHTML = rows;
        }
    })
    .catch(error => {
        console.error('Error during fetch operation: ', error);
        alert(error.message);
    });
}

function handleKeyPress(event) {
    if (event.key === 'Enter'){
        event.preventDefault(); // 기본 제출 동작을 방지
        search_keyword();
    }
}
</script>
</body>
</html>