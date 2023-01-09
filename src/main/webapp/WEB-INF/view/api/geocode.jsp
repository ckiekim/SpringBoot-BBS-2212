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
            	<h3><strong>좌표 구하기</strong></h3>
            	<hr>
				<table class="table">
					<tr><th>도로명 주소</th><th>경도</th><th>위도</th></tr>
					<tr>
						<td>${keyword}</td>
						<td>${coord.get(0)}</td>
						<td>${coord.get(1)}</td>
					</tr>
				</table>
			</div>
            <!-- =================== main =================== -->
            
        </div>
    </div>
    
    <%@ include file="../common/bottom.jsp" %>
</body>
</html>