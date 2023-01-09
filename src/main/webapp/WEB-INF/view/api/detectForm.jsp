<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
</head>
<body>
	<%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3><strong>네이버 인공지능 API 객체 탐지</strong></h3>
            	<hr>
                <div class="row">
				    <form action="/api/detect" method="post" enctype="multipart/form-data">
				    	탐지할 파일: <input type="file" name="upload">
				    	<button type="submit" class="btn btn-primary me-2">업로드</button>
					</form>
				</div>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>