<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Class</title>

  
  <style>
    #introduce{
      display: grid;
      grid-template-columns: 15em 15em;
      grid-template-areas: 
      "one_day hobby";
      margin: auto;
      column-gap: 1em;
      margin-top: 2em;
      justify-content: center;

    }
    #one_day{
      grid-area: one_day;
      border: 1px solid white;
    }
    #hobby{
      grid-area: hobby;
      border: 1px solid white;
    }
    #btns{
      display: grid;
      grid-template-columns: 13em 13em;
      grid-template-areas: 
      "make_rsvn asking";
      justify-content: center;
      margin: 2em 0;
    }
    #make_rsvn{
      grid-area: make_rsvn;
    }
    #make_rsvn a, #asking a{
      font-size: 1.7em;
      padding: 5px;
    }
    #asking{
      grid-area: asking
    }
    .rsvn a{
      font-size: 4em;
      padding: 10px;
    }
    button{
      background-color: black;
      border: 1px solid white;
    }
    p{
      text-align: left;
      padding-left: 1em;
    }
    table{
      margin: auto;
      border-collapse: collapse;
      width: 30em;
      text-align: center;
    }
    th, td{
      border: 1px solid white;
    }
    #diff td{
      text-align: left;
      padding-left: 1em;
      border: none;
      border-right: 1px solid white;
      border-left : 1px solid white;
    }
    #manager{
        margin-bottom: 2em;
    }
    #manager button{
        color: white;
    }

  </style>
</head>
<body>
  <%@ include file="../../navigation.jsp" %>

  <div style="text-align: center;">
    <div id="notice">
      <h1>클래스 안내</h1>
      [클래스] 하루에 한 타임이 진행됩니다.<br><br>
        - 첫시작 11:00 ~ (3시간30분)<br>
        - 마지막 15:00 ~ (3시간30분)<br>
        평균 최소 3시간이 소요되며, 개인의 작업 속도에 따라 달라질 수 있습니다.<br><br>
        제한없이 보다 자유롭게 꾸준한 취미로 즐기실 목적이시라면<br>
        "<u>하비 클래스(hobby class)</u>" 를 추천드립니다.<br>
        <br>
        유리하는 시간의 "hobby class"는<br>
        <span style="color:skyblue;">시간과 재료 사용이 여유롭고<br>
        합리적인 가격대</span>가 최대 장점입니다.<br>
        <br>
        초보자분께도 어렵지않게 알려드립니다.<br>
        유리를 즐길 수 있도록 도와드리겠습니다.<br><br>
        아래에서 "원데이 클래스"와 "하비 클래스"의<br>
        차이점을 참고해주세요. :)<br><br><br>
      <table>
        <caption style="padding: 10px; font-size: 1.5em;">[하비클래스 커리큘럼 및 비용 안내]</caption>
        <thead>
          <tr>
            <td rowspan="2">구분</td>
            <td rowspan="2">주차</td>
            <td rowspan="2">커리큘럼<br>(작품구성)</td>
            <td rowspan="2" colspan="2">원데이클래스 및<br>스마트스토어 구매 비용</td>
            <td rowspan="2">*하비클래스<br>4주/8주 비용</td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td rowspan="4">하비A</td>
            <td>1</td>
            <td>썬캐쳐 or 도어벨</td>
            <td>60,000 원</td>
            <td rowspan="4">264,000 원</td>
            <td rowspan="4">300,000 원</td>
          </tr>
          <tr>
            <td>2</td>
            <td>캔들홀더 or 입체트레이</td>
            <td>50,000 원</td>
          </tr>
          <tr>
            <td>3</td>
            <td>벽거울</td>
            <td>84,000 원</td>
          </tr>
          <tr>
            <td>4</td>
            <td>칵테일 조명</td>
            <td>70,000 원</td>
          </tr>
          <tr>
            <td rowspan="4">하비B</td>
            <td>5</td>
            <td rowspan="2">25cm x 25cm 사진액자</td>
            <td rowspan="2">290,000 원</td>
            <td rowspan="4">560,000 원</td>
            <td rowspan="4">450,000 원</td>
          </tr>
          <tr>
            <td>6</td>
          </tr>
          <tr>
            <td>7</td>
            <td rowspan="2">유리 해바라기 화병 (67조각)</td>
            <td rowspan="2">270,000 원</td>
          </tr>
          <tr>
            <td>8</td>
          </tr>
        </tbody>
        <tr></tr>
      </table>
      <br><br>

      <table id="diff">
        <caption style="margin-bottom: 10px;">
          <span style="font-size: 1.5em;">[클래스 별 차이점]</span>
        </caption>
        <tr>
          <th>* 하비클래스</th>
          <th>원데이클래스</th>
        </tr>
        <tbody>
          <tr>
            <td style="padding-top: 10px;">&bull; 도안 변경 가능</td>
            <td style="padding-top: 10px;">&bull; 정해진 도안으로만 제작</td>
          </tr>
          <tr>
            <td>&bull; 자유 도안 가능 (도안 작업 비용 없음)</td>
            <td>&bull; 난이도 낮음</td>
          </tr>
          <tr>
            <td>&bull; 기타 부자재 변경 가능</td>
            <td>&bull; 기타 부자재 변경 불가</td>
          </tr>
          <tr>
            <td>&bull; 최대 6시간 수업</td>
            <td>&bull; 최대 3시간 30분 수업</td>
          </tr>
          <tr>
            <td>&bull; 1층 카페 음료 무료 제공</td>
            <td>&bull; 1층 카페 음료 반값 혜택</td>
          </tr>
          <tr>
            <td>&bull; 클래스 전 과제 있음 (도안구상, 스케치)</td>
            <td rowspan="3" style="border-bottom: 1px solid white;">&nbsp;&nbsp;&nbsp;</td>
          </tr>
          <tr>
            <td>&bull; 클래스 후 과제 있음 (포일링)</td>
          </tr>
          <tr>
            <td style="border-bottom: 1px solid white; padding-bottom: 10px;">&bull; 지인 원데이클래스 12% 할인쿠폰 2장</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div id="introduce">
      <div id="one_day">
        <h2>1. 원데이 클래스(Oneday class)</h2>
        <p>- 샘플 작품 중 원하는 작품을 선택하여<br>
          <span style="padding-left: 0.8em;">도안대로 만들어 보는 시간입니다.</span>
        </p>
        <p>- 샘플 도안에서 '일부' 모양이나 조각 수는<br>
          <span style="padding-left: 0.8em;">변경하셔도 됩니다.</span>
        </p>
      </div>

      <div id="hobby">
        <h2>2. 하비 클래스(hobby class)</h2>
        <p>- 수강생님이 원하는 도안으로<br>
          <span style="padding-left: 0.8em;">나만의 작품을 만들어 보는 시간입니다.</span>
        </p>
        <p>- 취미반 커리큘럼에 맞춰진 <br>
          <span style="padding-left: 0.8em;">주차별 프로그램 기준으로 진행됩니다.</span>
        </p>
      </div>
    </div>

    <div id="btns">
      <div id="make_rsvn">
        <button><a href="/reservation">클래스 예약하기</a></button>
      </div>

      <div id="asking">
        <button><a target="_blank" href="https://talk.naver.com/ct/w4fpms?frm=pblog#nafullscreen">네이버 톡톡 문의하기</a></button>
      </div>
    </div>

    <% if(request.getAttribute("member") != null && (Boolean) request.getAttribute("isAdmin")) { %>
      <div id="manager">
          <button><a href="/createLecture">클래스 추가하기</a></button>
          <button><a href="/viewLecture">클래스 수정하기</a></button>
          <button><a href="">클래스 삭제하기</a></button>
      </div>
    <% } %>
    
  </div>

</body>
</html>