<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="David.glass_time_studio.domain.member.entity.Member" %>
<%@ page import="David.glass_time_studio.domain.review.entity.Review" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>예약 수정</title>
    <style>
        fieldset{
            width: 30em;
            margin: auto;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .class_attribute{
            width: 14em;
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 0.5em;
        }
        #review_btn{
            margin-top: 1em;
        }
        #content{
            width: 14em;
            display: block;
            text-align: left;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <%@ include file="../../navigation.jsp" %>
    <form>
        <fieldset>
            <legend>수강생 후기 게시글</legend>
            <div class="class_attribute">
            <span>[수업 이름]</span>
            <span>[${review.lecture_Name}]</span>
            </div>

            <div class="class_attribute">
            <span>[게시글 제목]</span>
            <span>[${review.title}]</span>
            </div>

            <div class="class_attribute">
            <span>[게시글 내용]</span>
            </div>
            <div id="content">
            <span>${review.content}</span>
            </div>


            <div id="review_btn">
            <%
                Review review = (Review) request.getAttribute("review");
                Member member = (Member) request.getAttribute("member");
                Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");
                boolean isAuthor = review.getMemberId().equals(member.getMemberId());

                if(isAuthor) {
            %>
                <button type="button" onclick="updatePage()">게시글 수정</button>
            <%
                }
                if (isAuthor || (isAdmin != null && isAdmin)) {
            %>
                <button type="button" onclick="deleteReview()">게시글 삭제</button>
            <%
                }
            %>
            </div>
        </fieldset>
    </form>


<script>
function updatePage() {
    var reviewId = "${review.reviewId}";
    if(confirm('정말로 게시글을 수정하시겠습니까 ?')){
        window.location.href = '/review/update/'+reviewId;
    }
}

function deleteReview() {
    var memberId = "${review.memberId}";
    var reviewId = "${review.reviewId}";
    var apiEndPoint = "${apiEndPoint}";

    if(confirm('정말로 게시글을 삭제하시겠습니까 ?')){
        fetch(apiEndPoint+'/review/delete/'+reviewId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if(!response.ok){
                return response.json().then(error => {
                    throw new Error(error.message);
                })
            }
            return response.json();
        })
        .then(data => {
            alert(data.message);
            window.location.href = '/review';
        })
        .catch(error => {
            console.error('Error: ', error);
            alert(error.message);
        });
    }
}
</script>
</body>
</html>