<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>수업 리스트</title>
    <style>
        fieldset{
            width: 30em;
            margin: auto;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        #lecture_table{
          margin: auto;
          text-align: center;
          width: 30em;
          border: 1px solid white;
          border-collapse: collapse;
        }
        #lecture_table td{
          text-align: center;
          border: 1px solid white;
        }
        #lecture_table td:hover {
            cursor: pointer;
            color: yellow;
        }
    </style>
</head>
<body>
    <%@ include file="../../navigation.jsp" %>
    <form id="lecture_form" name="form">
        <fieldset>
          <legend>수업 리스트</legend>

          <table id="lecture_table"></table>

          <div id="search_area" style="margin-top: 1em;">
          <input type="text" id="keyword" name="keyword" onkeypress="handleKeyPress(event)">
          <button type="button" onClick="search_keyword()">검색하기</button>
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
    fetch('http://localhost:8080/lecture/all?page='+pageNumber+'&size=10')
    .then(response => response.json())
    .then(data => {
        console.log('Received data: '+data);
        let lectures = data.data;
        // 정렬 조건
        switch (sortBy) {
            case 'id':
                lectures.sort((a, b) => sortOrder === 'desc' ? b.lecture_Id - a.lecture_Id : a.lecture_Id - b.lecture_Id);
                break;
            case 'status':
                lectures.sort((a, b) => {
                    if (sortOrder === 'asc') {
                        return a.status.localeCompare(b.status);
                    } else {
                        return b.status.localeCompare(a.status);
                    }
                });
                break;
        }
        updateTable(lectures);
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
function updateTable(lectures) {
    const table = document.querySelector("#lecture_table");
    if (lectures.length === 0) {
        table.innerHTML = "<tr><td colspan='5'>수정할 수업이 없습니다.</td></tr>";
        return;
    }
      let rows = `<tr>
                  <td id="sortById">[수업 번호]</td>
                  <td>[수업 이름]</td>
                  <td>[수업 기간] (주 단위)</td>
                  <td>[수업 비용]</td>
                  <td id="sortByStatus">[수업 상태]</td>
                  </tr>`;
    lectures.forEach(lecture => {
        rows +=
          "<tr>"+
          "<td><a href='/lecture/"+lecture.lecture_Id+"'>" + lecture.lecture_Id + "</a></td>" +
          "<td><a href='/lecture/"+lecture.lecture_Id+"'>" + lecture.lecture_Name + "</a></td>" +
          "<td><a href='/lecture/"+lecture.lecture_Id+"'>" + lecture.lecture_Period + "</a></td>" +
          "<td><a href='/lecture/"+lecture.lecture_Id+"'>" + lecture.lecture_Price + "</a></td>" +
          "<td><a href='/lecture/"+lecture.lecture_Id+"'>" + lecture.status + "</a></td>" +
          "</tr>";
    });
    table.innerHTML = rows;
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
    fetch("http://localhost:8080/lecture/search?keyword="+encodedKeyword)
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

        const lectures = data;
        const table = document.querySelector("#lecture_table");
        let rows = "<tr><td>[수업번호]</td><td>[수업이름]</td><td>[수업기간] (주 단위)</td><td>[수업비용]</td><td>[수업상태]</td></tr>";

        if(lectures.length <= 0) {
            table.innerHTML = "<tr><td colspan='5'> 검색된 결과가 없습니다.</td></tr>";
        } else {
            lectures.forEach(lecture => {
                rows +=
                      "<tr>"+
                      "<td><a href='/lecture/"+lecture.lecture_Id+"'>" + lecture.lecture_Id + "</a></td>" +
                      "<td><a href='/lecture/"+lecture.lecture_Id+"'>" + lecture.lecture_Name + "</a></td>" +
                      "<td><a href='/lecture/"+lecture.lecture_Id+"'>" + lecture.lecture_Period + "</a></td>" +
                      "<td><a href='/lecture/"+lecture.lecture_Id+"'>" + lecture.lecture_Price + "</a></td>" +
                      "<td><a href='/lecture/"+lecture.lecture_Id+"'>" + lecture.status + "</a></td>" +
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