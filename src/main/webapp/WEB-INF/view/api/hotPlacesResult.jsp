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
            	<h3><strong>Hot Places 결과</strong> <small>(행안부 도로명주소, 네이버 Geocode/Static Map API)</small></h3>
            	<hr>
                <div class="row">
				    <img src="${url}" width="75%">
    				<br><br>
    				<button class="btn btn-primary"
                            onclick="location.href='/api/hotPlaces'">재실행</button>
				</div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>

</body>
</html>