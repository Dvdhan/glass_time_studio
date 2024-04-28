<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>이벤트 / 공지사항 작성하기</title>
    <script src="https://cdn.ckeditor.com/4.14.0/standard/ckeditor.js"></script>
</head>
<style>
    #body{
      width: 40em;
      margin: auto;
      text-align: center;
    }
    #body input{

      display: block;
      text-align: left;
    }
    #event_title, #event_content {
      margin: 10px auto; /* 상하 간격을 주고, 좌우로 자동 마진을 주어 중앙 정렬 */
      width: 30em; /* 너비를 일관되게 설정 */
    }
    #event_content textarea {
      width: 100%; /* 입력 필드와 텍스트 영역이 각 div의 전체 너비를 차지하도록 설정 */
    }
    #event_content{
      margin-top: 1em;
    }
    .an_head{
      display: block;
      text-align: left;
    }


</style>
<body>
  <%@ include file="../../navigation.jsp" %>

  <form name="form" onsubmit="saveit(event)">
      <div id="body">
        <fieldset>
          <legend>이벤트 게시글 작성</legend>

          <div id="event_title">
            <span class="an_head">이벤트/공지사항 제목</span>
            <input type="text" id="announcement_Title" name="announcement_Title" maxlength="30">
          </div>

          <div id="event_content">
            <span class="an_head">이벤트/공지사항 내용</span>
            <textarea id="announcement_Content" name="announcement_Content" cols="30" rows="5"></textarea>
          </div>

          <button type="submit">저장</button>
        </fieldset>
      </div>
  </form>

  <script>
  	CKEDITOR.replace('announcement_Content');

    function saveit(event) {
        event.preventDefault();

        let title = document.getElementById('announcement_Title').value;
        let content = CKEDITOR.instances.announcement_Content.getData();

        // AJAX 요청을 구성
        fetch('/Announcement', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                announcement_Title: title,
                announcement_Content: content
            })
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Network response was not ok.');
        })
        .then(data => {
            console.log("서버 응답:", data);
            alert('게시글이 작성이 완료되었습니다.');
            window.location.href = '/main';
        })
        .catch(error => {
            console.error('문제가 발생했습니다:', error);
        });
    }
  </script>
</body>
</html>