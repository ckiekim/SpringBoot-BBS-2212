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
            	<h3><strong>심리상담 챗봇</strong></h3>
            	<hr>
            	<div class="d-flex justify-content-center">
                	<iframe src="http://localhost:8501/" border="1" style="width: 80%; height: 600px;"></iframe>
				</div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>

</body>
</html>