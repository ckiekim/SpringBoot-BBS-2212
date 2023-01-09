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
            	<h3><strong>도로명 주소 API 결과</strong></h3>
            	<hr>
				<table class="table">
					<tr><th>지명</th><th>도로명 주소</th></tr>
					<tr>
						<td>${keyword}</td>
						<td>${addr}</td>
					</tr>
				</table>
			</div>
            <!-- =================== main =================== -->
            
        </div>
    </div>
    
    <%@ include file="../common/bottom.jsp" %>
</body>
</html>