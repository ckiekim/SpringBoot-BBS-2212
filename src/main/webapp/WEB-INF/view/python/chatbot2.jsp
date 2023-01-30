<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <script>
    	function send() {
    		let userInput = $('#userInput').val();
    		$('#userInput').html('');
    		$('#container').attr({'class': 'container'})
    		$.ajax({
				type: 'POST',
				url: '/python/chatbot2',
				data: {userInput: userInput},
				/* success: function(result) {
					let obj = JSON.parse(result);
					$('#user').html(obj.user);
					$('#chatbot').html(obj.chatbot);
				} */
				success: function(e) { }
			});
    	}
    </script>
</head>
<body>
	<%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3><strong>심리상담 챗봇</strong></h3>
            	<hr>
            	<div class="d-flex justify-content-center">
            		<div class="input-group mb-3" style="width: 80%;">
						<span class="input-group-text">당신:</span>
						<input type="text" class="form-control" name="userInput" id="userInput"
							   onkeyup="if(window.event.keyCode==13) {send()}">
            			<button class="btn btn-primary" type="submit" onclick="send()">전송</button>
					</div>
				</div>
				<div class="d-flex justify-content-center">
               		<div class="container d-none" id="container" style="width: 80%; height: 600px;">
               		<c:forEach var="chat" items="${sessionChatBot}">
               			<div class="d-flex flex-row mt-1">
                            <div class="card bg-light text-dark w-75">
                                <div class="card-body">
									<img src="/img/ai.png" height="32">
									<span id="chatbot">${chat.chatbot}</span>
                                </div>
                            </div>
                        </div>
               			<div class="d-flex flex-row-reverse mt-1">
                            <div class="card w-75">
                                <div class="card-body text-end">
									<span id="user">${chat.user}</span>
									<img src="/img/person.png" height="32">
                                </div>
                            </div>
                        </div>
               		</c:forEach>
               		</div>
               	</div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>

</body>
</html>