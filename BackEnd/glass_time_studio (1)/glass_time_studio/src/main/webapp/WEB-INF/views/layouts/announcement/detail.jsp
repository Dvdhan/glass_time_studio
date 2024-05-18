<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="David.glass_time_studio.domain.announcement.dto.AnnouncementDto.Response" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>이벤트/공지사항</title>
    <link rel="stylesheet" href="../../css/background_black.css">

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
      padding: 0.5em;
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

<%
    Response announcement = (Response) request.getAttribute("announcement");
%>

<div id="head">
    <fieldset>
        <legend><h1>이벤트/공지사항 상세보기</h1></legend>
        <div id="body_ct">
            <p>
                게시글 번호: ${announcement.announcement_Id}
            </p>
            <p>
                게시글 제목: ${announcement.announcement_Title}
            </p>
            <p>
                게시글 내용: ${announcement.announcement_Content}
            </p>
            <p>
                작성일: <span id="created_at" style="margin-left: 5px;"></span>
            </p>
            <p>
                수정일: <span id="modified_at" style="margin-left: 5px;"></span>
            </p>
        </div>

        <%
            if(request.getAttribute("member") != null && (Boolean) request.getAttribute("isAdmin")) {
                String encodedTitle = URLEncoder.encode(announcement.getAnnouncement_Title(), "UTF-8");
                String encodedContent = URLEncoder.encode(announcement.getAnnouncement_Content(), "UTF-8");
        %>
            <div id="btns">
                <a id="update" href="/updateAnnouncement?id=${announcement.getAnnouncement_Id()}&title=<%=encodedTitle%>&body=<%=encodedContent%>">수정하기</a>
                <a id="delete" onclick="deleteAnnouncement()">삭제하기</a>
            </div>
        <% } %>

    </fieldset>
</div>

<script>

var created_at_str = "${announcement.created_at}";
var modified_at_str = "${announcement.modified_at}";

var createdAt = new Date(created_at_str).toLocaleString();
var modifiedAt = new Date(modified_at_str).toLocaleString();

document.getElementById('created_at').innerText = '[' + createdAt + ']';
document.getElementById('modified_at').innerText = '[' + modifiedAt + ']';

function deleteAnnouncement(){
    var announcement_Id = "${announcement.announcement_Id}";
    if(confirm('정말로 삭제하시겠습니까 ?')){
        fetch('/Announcement/' + announcement_Id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) throw new Error ('삭제 실패');
            return response.json();
        })
        .then(data => {
            alert(data.message);
            window.location.href = '/announcement';
        })
        .catch(error => {
            console.error('Error: ', error);
            alert('삭제 중 문제가 발생했습니다.');
        });
    }
}



</script>
</body>
</html>