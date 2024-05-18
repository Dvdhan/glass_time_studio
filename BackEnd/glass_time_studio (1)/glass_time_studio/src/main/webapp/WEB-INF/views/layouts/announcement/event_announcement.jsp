<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Event/Announcement</title>
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
    #announcement_btns{
      text-align: center;
    }
    #cont{
      margin: auto;
      width: 30em;

    }
    #cont_table{
      margin: auto;
      text-align: center;
      width: 30em;
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
    #announcement_btn_reverse{
      width: 33em;
      margin: auto;
      margin-top: 1em;
      text-align: right;
    }
    #aesc_order_btn, #desc_order_btn{
      background-color: black;
      border: 1px solid white;
      color: white;
    }
  </style>
</head>
<body>
  <%@ include file="../../navigation.jsp" %>
  <div id="post-list">
    <fieldset>
      <legend>
        <h2>&nbsp;&nbsp;이벤트/공지사항 목록&nbsp;&nbsp;</h2>
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

    <% if(request.getAttribute("member") != null && (Boolean) request.getAttribute("isAdmin")) { %>
      <div id="announcement_btns">
          <a href="/announcement/write" id="add">공지사항 작성하기</a>
      </div>
    <% } %>

<script>
// 페이지 로드 시 첫 번째 페이지 로드
document.addEventListener('DOMContentLoaded', function() {
    loadPage(1);
});

let currentPage = 1; // 현재 페이지 상태를 전역으로 관리

// 페이지와 정렬을 동시에 처리하는 함수
function loadPage(pageNumber, sortOrder = 'asc', sortBy = 'id') {
    currentPage = pageNumber;  // 현재 페이지 업데이트
    console.log('Loading page number: ' + pageNumber + ' with sort order: ' + sortOrder);
    fetch('http://localhost:8080/Announcement/all?page='+pageNumber+'&size=10')
    .then(response => response.json())
    .then(data => {
        console.log('Received data:', data);
        let announcements = data.data;
        // 정렬 조건 확인 및 적용
        switch (sortBy) {
            case 'id':
                announcements.sort((a, b) => sortOrder === 'desc' ? b.announcement_Id - a.announcement_Id : a.announcement_Id - b.announcement_Id);
                break;
            case 'created_at':
                announcements.sort((a, b) => sortOrder === 'desc' ? new Date(b.created_at) - new Date(a.created_at) : new Date(a.created_at) - new Date(b.created_at));
                break;
            case 'modified_at':
                announcements.sort((a, b) => sortOrder === 'desc' ? new Date(b.modified_at) - new Date(a.modified_at) : new Date(a.modified_at) - new Date(b.modified_at));
                break;
        }
        updateTable(announcements);  // 실제 공지사항 데이터 업데이트
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
    created: 'asc',
    modified: 'asc'
};

function toggleSortOrder(currentOrder) {
    return currentOrder === 'asc' ? 'desc' : 'asc';
}

function updateTable(announcements) {
    const table = document.querySelector("#cont_table");
    if (announcements.length === 0) {
        table.innerHTML = "<tr><td colspan='4'>작성된 이벤트/공지사항이 없습니다.</td></tr>";
        return;
    }
      let rows = `<tr>
                  <td id="sortById">[공지번호]</td>
                  <td>[이벤트/공지제목]</td>
                  <td id="sortByCreated">[작성일]</td>
                  <td id="sortByModified">[수정일]</td>
                  </tr>`;
    announcements.forEach(announcement => {
        const createdAt = new Date(announcement.created_at).toLocaleString();
        const modifiedAt = new Date(announcement.modified_at).toLocaleString();
        rows +=
          "<tr>"+
          "<td><a href='/Announcement/"+announcement.announcement_Id+"'>" + announcement.announcement_Id + "</a></td>" +
          "<td><a href='/Announcement/"+announcement.announcement_Id+"'>" + announcement.announcement_Title + "</a></td>" +
          "<td><a href='/Announcement/"+announcement.announcement_Id+"'>" + createdAt + "</a></td>"+
          "<td><a href='/Announcement/"+announcement.announcement_Id+"'>" + modifiedAt + "</a></td>"+
          "</tr>";
    });
    table.innerHTML = rows;
    bindTableHeaderClicks();
}
function bindTableHeaderClicks() {
    document.getElementById('sortById').addEventListener('click', () => loadPage(currentPage, sortStates['id'] = toggleSortOrder(sortStates['id']), 'id'));
    document.getElementById('sortByCreated').addEventListener('click', () => loadPage(currentPage, sortStates['created_at'] = toggleSortOrder(sortStates['created_at']), 'created_at'));
    document.getElementById('sortByModified').addEventListener('click', () => loadPage(currentPage, sortStates['modified_at'] = toggleSortOrder(sortStates['modified_at']), 'modified_at'));
}

function search_keyword(){
    let keyword = document.getElementById('keyword').value;
    console.log("검색어 ", keyword);
    if(!keyword.trim()) {
        alert('검색어를 입력해 주세요');
        return;
    }

    const encodedKeyword = encodeURIComponent(keyword);
    fetch("http://localhost:8080/Announcement/search?keyword="+encodedKeyword)
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

        const announcements = data;
        const table = document.querySelector("#cont_table");
        let rows = "<tr><td>[공지번호]</td><td>[이벤트/공지제목]</td><td>[작성일]</td><td>[수정일]</td></tr>";

        if(announcements.length <= 0) {
            table.innerHTML = "<tr><td colspan='4'> 검색된 결과가 없습니다.</td></tr>";
        } else {
            announcements.forEach(announcement => {
                const createdAt = new Date(announcement.created_at).toLocaleString();
                const modifiedAt = new Date(announcement.modified_at).toLocaleString();
                rows +=
                    "<tr>" +
                    "<td><a href='/Announcement/" + announcement.announcement_Id + "'>" + announcement.announcement_Id + "</a></td>" +
                    "<td><a href='/Announcement/" + announcement.announcement_Id + "'>" + announcement.announcement_Title + "</td>" +
                    "<td><a href='/Announcement/" + announcement.announcement_Id + "'>" + createdAt + "</td>" +
                    "<td><a href='/Announcement/" + announcement.announcement_Id + "'>" + modifiedAt + "</td>" +
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

function aesc_effect_btn_on() {
    let target = document.getElementById("aesc_order_btn");
    target.style.color="yellow";
    target.style.textDecoration="underline";
    target.style.cursor="pointer";
}
function aesc_effect_btn_off() {
    let target = document.getElementById("aesc_order_btn");
    target.style.color="white";
    target.style.textDecoration="none";
}
function desc_effect_btn_on() {
    let target = document.getElementById("desc_order_btn");
    target.style.color="yellow";
    target.style.textDecoration="underline";
    target.style.cursor="pointer";
}
function desc_effect_btn_off() {
    let target = document.getElementById("desc_order_btn");
    target.style.color="white";
    target.style.textDecoration="none";
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