<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
	<script>
		window.onload = function() {
			const form = document.getElementById('form');
			form.submit();
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
            	<h3><strong>서울 소재 소방서 현황</strong></h3>
            	<hr>
				<img src="/img/please-wait.gif" class="ms-5">
				<form style="display: none;" action="/crawling/fireStation" method="post" id="form">
					<input type="hidden" name="dummy" value="0">
				</form>
			</div>
            <!-- =================== main =================== -->
            
        </div>
    </div>
    
    <%@ include file="../common/bottom.jsp" %>
</body>
</html>