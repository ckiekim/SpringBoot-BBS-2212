<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
        th, td { text-align: center; }
    </style>
</head>
<body>
	<%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3><strong>Hot Places</strong>(행안부 도로명주소, 네이버 Geocode/Static Map API)</h3>
            	<hr>
            	<form action="/api/hotPlaces" method="post">
					<table class="table table-borderless">
						<tr>
							<td>줌 레벨</td>
							<td colspan="3" style="text-align: left;">
								<select name="level">
								<c:forEach var="num" items="${levelList}" varStatus="loop">
									<option value="${num}" ${(num eq '12') ? "selected" : ''}>${num}</option>
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>지명</td>
							<td><input type="text" name="places"></td>
							<td><input type="text" name="places"></td>
							<td><input type="text" name="places"></td>
						</tr>
						<tr>
							<td> </td>
							<td><input type="text" name="places"></td>
							<td><input type="text" name="places"></td>
							<td><input type="text" name="places"></td>
						</tr>
						<tr>
							<td colspan="4">
								<button type="submit" class="btn btn-primary me-2">실행</button>
                            	<button type="reset" class="btn btn-secondary">취소</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
            <!-- =================== main =================== -->
            
        </div>
    </div>
    
    <%@ include file="../common/bottom.jsp" %>
</body>
</html>