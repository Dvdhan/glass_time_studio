<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>이벤트/공지사항</title>
    <link rel="stylesheet" href="../../css/background_black.css">
    <script src="https://cdn.ckeditor.com/4.14.0/standard/ckeditor.js"></script>

    <style>
    #head{
     margin: auto;
     text-align: center;
     width: 35em;
    }
    #tb_head{
      margin: auto;
      text-align: center;
    }
    #body_ct{
      width: 20em;
      text-align: left;
      border: 1px solid white;
      margin: auto;
    }
    #update, #delete{
      border: 1px solid white;
      padding: 7px;
    }
    #btns{
        margin-top: 1em;
    }
    </style>
</head>
<body>
  <%@ include file="../../navigation.jsp" %>

<div id="head">
<form id="updateForm">
    <fieldset>
        <legend><h1>이벤트/공지사항 수정하기</h1></legend>
        <div id="body_ct">
            <p>
                게시글 번호: ${param.id}
            </p>
            <p>
                게시글 제목: <input type="text" name ="announcement_Title" id="announcement_Title" value="${param.title}">
            </p>
            <p>
                게시글 내용: <input type="text" name ="announcement_Content" id="announcement_Content" value="${param.body}">
            </p>
            <input type="hidden" name="announcementId" id="announcementId" value="${param.id}">
            <input type="hidden" name="modified_at" id="modified_at">

        </div>

        <div id="btns">
            <a id="update" onclick="updateAnnouncement()">저장하기</a>
        </div>

    </fieldset>
</form>
</div>

<script>
var modified_at_str = "";
var modifiedAt = new Date(modified_at_str).toLocaleString();

CKEDITOR.replace('announcement_Content', {
    on: {
        instanceReady: function(evt) {
            this.setData(document.getElementById('announcement_Content').value);
        }
    }
});

function updateAnnouncement() {

    let content = CKEDITOR.instances.announcement_Content.getData();

    var formData = {
        announcementId: document.getElementById('announcementId').value,
        announcement_Title: document.getElementById('announcement_Title').value,
        announcement_Content: content
    };

    fetch('/Announcement/'+ formData.announcementId, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            announcement_Title: formData.announcement_Title,
            announcement_Content: formData.announcement_Content
        })
    })
    .then(response => {
        if(!response.ok){
            throw new Error('수정 실패');
        }
        return response.json();
    })
    .then(data => {
        alert('수정되었습니다.');
        window.location.href = '/announcement';
    })
    .catch(error => {
        console.error('Error: ', error);
        alert('수정 중 문제가 발생하였습니다. 콘솔로그 확인해주세요');
    });
}

</script>
</body>
</html>