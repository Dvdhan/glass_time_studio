<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Event/Announcement</title>
  <link rel="stylesheet" href="../../css/background_black.css">
  <style>
    #content {
      /* border: 1px solid white; */
      width: 80%;
      text-align: center;
      margin: auto;
    }
    #post-list, #post-list li{
      list-style: none;
      text-align: center;
      margin: auto;
    }
  </style>
</head>
<body>
  <%@ include file="../../navigation.jsp" %>
  <div style="text-align: center;">
    <h1>이벤트/공지사항</h1>
  </div>

  <div id="content">
    <fieldset id="main">

      <div id="createdAt">
      <p style="text-align: right; margin-right: 5%;">포스팅 날짜: 2024.02.08</p>
      </div>

      <div id="title">
        <b>['유리하는 시간'공방 운영방침]</b><br><br><br><br>
      </div>

      <div id="body">
      <b>'유리하는 시간' 공방은 예약제로 운영됩니다.</b><br>
      <br><br>
      <b>[클래스]</b> 하루에 한 타임이 진행됩니다.<br><br>
      - 첫시작 11:00 ~ (3시간30분)<br>
      - 마지막 15:00 ~ (3시간30분)<br>
        평균 최소 3시간이 소요되며, 개인의 작업 속도에 따라 달라질 수 있습니다.<br><br>
        <b>(1) 원데이 클래스</b><br>
        - 샘플 작품 중 원하는 작품을 선택하여 도안대로 만들어 보는 시간입니다.<br>
        - 샘플 도안에서 '일부' 모양이나 조각 수는 변경하셔도 됩니다.<br>
        <br>
        <b>(2) 취미반 클래스</b><br>
        - 수강생님이 원하는 도안으로 나만의 작품을 만들어 보는 시간입니다.<br>
        - 취미반 커리큘럼에 맞춰진 주차별 프로그램 기준으로 진행됩니다.<br>
        - 부자재 사용이 자유롭습니다.<br>
        <br>
        <b><i><s>(3) 정규반 클래스</b><br>
        공방 창업이 목적인 분들께 적합한 클래스입니다.<br>
        커리큘럼대로 작품 제작은 물론<br>
        도구, 재료, 부자재, 도안작업, 거래처 등<br>
        창업에 꼭 필요한 정보를 제공합니다.</s></i><br>
        <br><b>(정규반은 당분간 진행하지 않습니다.)</b><br><br>
        <br>

        <br><div class="bar"></div><br><br><br>
        
        <b>[주문제작]</b> 최대 2주가 소요됩니다.<br><br>
        <b>(1) 액자</b><br>
        우정/커플사진, 웨딩/가족사진,<br>
        아이/부모님사진, 좋아하는 애니메이션 등<br>
        오래도록 간직하고 싶은 장면을<br>
        유리로 재현하여 액자에 담아 드립니다.<br><br>
        <b>(2) 미니 간판</b><br>
        로고나 CI를 유리로 재현하여<br>
        행잉간판 또는 미니간판으로 제작해 드립니다.<br><br>
        대략적인 도안 작업 후, 견적을 전달드립니다.<br>
        <b>결제 후, 작업이 진행됩니다.</b><br><br><br>

        <br><div class="bar"></div><br><br><br>

        <b>[출강]</b> 최대 5인 동시수강 가능합니다.<br><br>
        <b>(1) 제안서 전달</b><br>
        출강으로 수업 가능한 내용으로 준비한<br>
        출강 제안서 파일을 전달드립니다.<br>
        클래스 취지, 성별, 연령, 금액에 맞는<br>
        클래스를 추천드리고<br>
        새로운 프로그램 구성도 가능합니다.<br><br>
        <b>(2) 클래스 픽스</b><br>
        계약서 작성 또는 선결제로<br>
        클래스를 픽스한 후,<br>
        수업 진행 방법에 대해 설명드립니다.<br><br>
        <b>(3) 출강비용 조율</b><br>
        체험대상, 이동거리, 회차에 따른<br>
        출강비용을 책정해서 전달드립니다.<br><br><br>

        <br><div class="bar"></div><br><br><br>

        <b>[연령제한]</b><br><br>
        유리, 유리칼, 그라인더, 납, 인두기, 각 종 화학제품 등<br>
        취급에 주의가 필요한 재료와 도구들이 많습니다.<br>
        안전을 위해 연령 별로 체험의 제한이 있습니다.<br>
        <br>
        <b>초등 5학년 미만</b><br>
        보호자 동반에 한해 일부 체험 가능<br>
        <br>
        <b>초등 5학년 이상</b><br>
        보호자 없이 전체 체험 가능<br><br><br>
      </div>
    </fieldset>
  </div>


  <div id="post-list">
    <h2>이벤트/공지사항 목록</h2>
    <ul>
      <li><a href="#">공지사항 1</a></li>
      <li><a href="#">공지사항 2</a></li>
      <li><a href="#">공지사항 3</a></li>
    </ul>
  </div>
</body>
</html>