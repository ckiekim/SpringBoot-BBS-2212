<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <script src="/js/detect.js"></script>
    <script>
    	window.onload = function() {
    		detectObject('${jsonResult}', '${fileName}');
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
			    <h3><strong>객체 탐지 결과</strong> <small>(네이버 Object Detection API)</small></h3>
			    <hr>
			    <div class="d-flex justify-content-center">
			    	<canvas id="tcanvas" width="100" height="100" />
			    </div>
			    <div class="d-flex justify-content-center mt-3">
			    	<button class="btn btn-primary" 
			    			onclick="location.href='/api/detect'">재실행</button>
            	</div>
            	<br><br>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>