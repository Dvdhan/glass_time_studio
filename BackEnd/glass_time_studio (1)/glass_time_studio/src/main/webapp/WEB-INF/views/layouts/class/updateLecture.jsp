<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>수업 정보 수정</title>

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
    </style>
</head>
<body>
    <%@ include file="../../navigation.jsp" %>
    <form id="lecture_form" name="form">
        <fieldset>
          <legend>클래스 추가</legend>
          <div class="class_attribute">
            <span class="class_head">클래스 이름</span>
            <input type="text" id="class_title_input" name="class_title_input" maxlength="30" value="${param.name}">
          </div>

          <div class="class_attribute">
            <span class="class_head">클래스 기간 (주 단위)</span>
            <input type="text" id="class_period_input" name="class_period_input" maxlength="30" value="${param.period}">
          </div>

          <div class="class_attribute">
            <span class="class_head">클래스 비용</span>
            <input type="text" id="class_cost_input" name="class_cost_input" maxlength="30" value="${param.cost}">
          </div>

          <div class="class_attribute">
            <span class="class_head">클래스 설명</span>
            <input type="text" id="class_description_input" name="class_description_input" maxlength="30" value="${param.description}">
          </div>

          <div class="class_attribute">
            <span class="class_head">클래스 상태</span>
            <select id="class_status_value">
              <option value="none" ${param.status == 'none' ? 'selected' : ''}>선택</option>
              <option value="Y" ${param.status == 'Y' ? 'selected' : ''}>Y</option>
              <option value="N" ${param.status == 'N' ? 'selected' : ''}>N</option>
            </select>
          </div>

          <div id="class_button">
          <button type="button" onclick="submitForm()">저장</button>
          </div>
        </fieldset>
        <input type="hidden" name="lecture_Id" id="lecture_Id" value="${param.id}">
    </form>

<script>
function submitForm() {
    var formData = {
        lecture_Id : document.getElementById('lecture_Id').value,
        lecture_Name : document.getElementById('class_title_input').value,
        lecture_Period : document.getElementById('class_period_input').value,
        lecture_Cost: document.getElementById('class_cost_input').value,
        lecture_Description: document.getElementById('class_description_input').value,
        lecture_Status: document.getElementById('class_status_value').value
    };
    console.log('formData: '+formData);

    fetch('/lecture/'+ formData.lecture_Id, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            lecture_Name: formData.lecture_Name,
            lecture_Period: formData.lecture_Period,
            lecture_Price: formData.lecture_Cost,
            lecture_Description: formData.lecture_Description,
            status: formData.lecture_Status
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
        window.location.href = '/viewLecture';
    })
    .catch(error => {
        console.error('Error: ', error);
        alert('수정 중 문제가 발생하였습니다. 콘솔로그 확인해주세요');
    });

}

</script>

</body>
</html>