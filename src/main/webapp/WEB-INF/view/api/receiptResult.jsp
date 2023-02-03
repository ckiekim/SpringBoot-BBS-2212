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
			    <h3><strong>영수증 인식(OCR) 결과</strong></h3>
			    <hr>
			    <div class="row">
			    	<div class="col-1"></div>
			    	<div class="col-6">
			    		<div class="d-flex justify-content-center">
			    			<canvas id="tcanvas" width="100" height="100"></canvas>
			    		</div>
			    	</div>
			    	<div class="col-4">
			    		<div class="mb-3">
							<label for="shop" class="form-label">매장명:</label>
							<input type="text" class="form-control" id="shop" name="shop" value="${rec.shop}">
						</div>
						<div class="mb-3">
							<label for="buyDate" class="form-label">구매일자:</label>
							<input type="date" class="form-control" id="buyDate" name="buyDate" value="${rec.buyDate}">
						</div>
						<div class="mb-5">
							<label for="price" class="form-label">금액:</label>
							<input type="text" class="form-control" id="price" name="price" value="${rec.price}">
						</div>
			    		<h4>인식된 텍스트</h4>
			    		<p id="ocrText" class="small"></p>
			    	</div>
			    	<div class="col-1"></div>
			    </div>
			    <div class="d-flex justify-content-center mt-3">
			    	<button class="btn btn-primary" 
			    			onclick="location.href='/api/receipt'">재실행</button>
            	</div>
            	<br><br>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>