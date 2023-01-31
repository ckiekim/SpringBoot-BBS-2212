<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <script>
    	function send() {
    		let userInput = $('#userInput').val();
    		$('#userInput').html('');
    		let mark = 'mark' + Math.floor(Math.random() * 100000);
    		$.ajax({
				type: 'POST',
				url: '/python/chatbot2',
				data: {userInput: userInput},
				success: function(result) {
					let obj = JSON.parse(result);
					$('<div></div>')
							.attr({'class': 'd-flex flex-row mt-1', id: mark+'1'})
							.appendTo('#container');
						$('<div></div>')
							.attr({'class': 'card w-75', id: mark+'2'}).appendTo('#'+mark+'1');
						$('<div></div>')
							.attr({'class': 'card-body', id: mark+'3'}).appendTo('#'+mark+'2');
						$('<img></img>')
							.attr({src: '/img/person.png', height: '32'}).appendTo('#'+mark+'3');
						$('<span></span>')
							.text(obj.user).appendTo('#'+mark+'3');
					$('<div></div>')
							.attr({'class': 'd-flex flex-row-reverse mt-1', id: mark+'6'})
							.appendTo('#container');
						$('<div></div>')
							.attr({'class': 'card bg-light text-dark w-75', id: mark+'7'}).appendTo('#'+mark+'6');
						$('<div></div>')
							.attr({'class': 'card-body text-end', id: mark+'8'}).appendTo('#'+mark+'7');
						$('<span></span>')
							.text(obj.chatbot).appendTo('#'+mark+'8');
						$('<img></img>')
							.attr({src: '/img/ai.png', height: '32'}).appendTo('#'+mark+'8');
				}
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
               		<div class="container overflow-auto" id="container" style="width: 80%; height: 600px;">
               		</div>
               	</div>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>

</body>
</html>