<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>수업 생성</title>
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
            <input type="text" id="class_title_input" name="class_title_input" maxlength="30">
          </div>

          <div class="class_attribute">
            <span class="class_head">클래스 기간 (주 단위)</span>
            <input type="text" id="class_period_input" name="class_period_input" maxlength="30">
          </div>

          <div class="class_attribute">
            <span class="class_head">클래스 비용</span>
            <input type="text" id="class_cost_input" name="class_cost_input" maxlength="30">
          </div>

          <div class="class_attribute">
            <span class="class_head">클래스 설명</span>
            <input type="text" id="class_description_input" name="class_description_input" maxlength="30">
          </div>

          <div class="class_attribute">
            <span class="class_head">클래스 상태</span>
            <select id="class_status_value">
              <option value="none">선택</option>
              <option value="Y">Y</option>
              <option value="N">N</option>
            </select>
          </div>

          <div id="class_button">
          <button type="button" onclick="submitForm()">저장</button>
          </div>
        </fieldset>
    </form>

    <script>
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