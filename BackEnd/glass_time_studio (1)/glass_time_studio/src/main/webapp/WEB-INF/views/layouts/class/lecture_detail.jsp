<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>수업 정보</title>
    <style>
        fieldset{
            width: 25em;
            margin: auto;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .class_attribute{
            width: 16em;
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 0.5em;
        }
        .class_head {
            margin-right: auto;
        }
        input {
            margin-left: auto;
        }
        #class_button{
            margin-top: 1em;
            padding: 5px;
        }
        #class_button a {
            border: 1px solid white;
            padding: 5px;
        }
    </style>
</head>
<body>
    <%@ include file="../../navigation.jsp" %>
    <form id="lecture_form" name="form">
        <fieldset>
          <legend>클래스 추가</legend>
          <div class="class_attribute">
            <span class="class_head">클래스 이름</span>
            <span>${lecture.lecture_Name}</span>
          </div>

          <div class="class_attribute">
            <span class="class_head">클래스 기간 (주 단위)</span>
            <span>${lecture.lecture_Period}</span>
          </div>

          <div class="class_attribute">
            <span class="class_head">클래스 비용</span>
            <span>${lecture.lecture_Price}</span>
          </div>

          <div class="class_attribute">
            <span class="class_head">클래스 설명</span>
            <span>${lecture.lecture_Description}</span>
          </div>

          <div class="class_attribute">
            <span class="class_head">클래스 상태</span>
            <span>${lecture.status}</span>

          </div>

          <div id="class_button">
          <a id="update_btn" href="/updateLecture?id=${lecture.getLecture_Id()}&name=${lecture.getLecture_Name()}&period=${lecture.getLecture_Period()}&cost=${lecture.getLecture_Price()}&description=${lecture.getLecture_Description()}&status=${lecture.getStatus()}">수정하기</a>
          <a id="delete_btn" onclick="deleteLecture()">삭제하기</a>
          </div>
        </fieldset>
    </form>


<script>
    function deleteLecture(){
        var lecture_Id = "${lecture.lecture_Id}";
        if(confirm('정말로 삭제하시겠습니까 ?')){
            fetch('/lecture/' + lecture_Id, {
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
                window.location.href = '/viewLecture';
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