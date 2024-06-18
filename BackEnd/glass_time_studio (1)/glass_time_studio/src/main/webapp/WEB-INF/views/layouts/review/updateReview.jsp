<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="David.glass_time_studio.domain.member.entity.Member" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>클래스 후기 작성하기</title>
    <script src="https://cdn.ckeditor.com/4.14.0/standard/ckeditor.js"></script>
</head>
<style>
    #body{
      width: 35em;
      margin: auto;
      text-align: center;
    }
    #body input{
      display: block;
      text-align: left;
    }
    #class_info, #event_title, #event_content {
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
    #class_info{
        display: flex;
        text-align: left;
    }
</style>
<body>
  <%@ include file="../../navigation.jsp" %>

  <% Member member = (Member) request.getAttribute("member"); %>
  <form name="form" onsubmit="saveit(event)">
      <div id="body">
        <fieldset>
          <legend>클래스 후기 게시글 작성</legend>

          <div id="class_info">
            <span class="an_head">참여했던 수업</span>
            <span class="an_head">&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;[${review.lecture_Name}]</span>
          </div>

          <div id="event_title">
            <span class="an_head">후기 게시글 제목</span>
            <input type="text" id="review_Title" name="review_Title" maxlength="30" value="${review.title}">
          </div>

          <div id="event_content">
            <span class="an_head">후기 게시글 내용</span>
            <textarea id="review_Content" name="review_Content" cols="30" rows="5">${review.content}</textarea>
          </div>

          <button type="submit">저장</button>
        </fieldset>
      </div>
  </form>

  <script>
  var memberId = "${member.memberId}";
  	CKEDITOR.replace('review_Content');

    function saveit(event) {
        event.preventDefault();
        var memberId = "${review.memberId}";
        var reviewId = "${review.reviewId}";
        var apiEndPoint = "${apiEndPoint}";
        var title = document.getElementById('review_Title').value;
        var content = CKEDITOR.instances.review_Content.getData();

        if (!title) {
            alert('후기 게시글 제목을 입력해주세요');
            return;
        }else if (!content){
            alert('후기 게시글 내용을 입력해주세요');
            return;
        }
        if(confirm('정말로 게시글을 수정하시겠습니까 ?')){
            fetch(apiEndPoint+'/review/update/'+memberId+'/'+reviewId, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    title: title,
                    content: content
                })
            })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(error => {
                        throw new Error(error.message);
                    })
                }
                return response.json();
            })
            .then(data => {
                console.log("서버 응답:", data);
                alert('게시글이 수정되었습니다.');
                window.location.href = '/review';
            })
            .catch(error => {
                console.error('문제가 발생했습니다:', error);
                alert(error.message);
            });
        }
    }
  </script>
</body>
</html>