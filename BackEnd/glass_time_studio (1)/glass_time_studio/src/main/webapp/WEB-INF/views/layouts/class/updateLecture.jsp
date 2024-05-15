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
    </form>

<script>
function submitForm() {
    alert('저장됬겠니 ?');
}

</script>

</body>
</html>