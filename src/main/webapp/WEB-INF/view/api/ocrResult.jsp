<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <script src="/js/ocr.js"></script>
    <script>
    	window.onload = function() {
    		ocrResult('${jsonResult}', '${fileName}');
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
			    <h3><strong>광학 문자 인식(OCR) 결과</strong></h3>
			    <hr>
			    <div class="row">
			    	<div class="col-1"></div>
			    	<div class="col-6">
			    		<div class="d-flex justify-content-center">
			    			<canvas id="tcanvas" width="100" height="100"></canvas>
			    		</div>
			    	</div>
			    	<div class="col-3">
			    		<h4>인식된 텍스트</h4>
			    		<p id="ocrText"></p>
			    	</div>
			    	<div class="col-1"></div>
			    </div>
			    <div class="d-flex justify-content-center mt-3">
			    	<button class="btn btn-primary" 
			    			onclick="location.href='/api/ocr'">재실행</button>
            	</div>
            	<br><br>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>