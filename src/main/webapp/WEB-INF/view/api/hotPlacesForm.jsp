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
            	<h3><strong>Hot Places</strong> <small>(행안부 도로명주소, 네이버 Geocode/Static Map API)</small></h3>
            	<hr>
            	<div class="row">
				    <div class="col-1"></div>
			        <div class="col-10">
		            	<form action="/api/hotPlaces" method="post">
							<table class="table table-borderless">
								<tr>
									<td><label class="col-form-label" for="level">줌 레벨</label></td>
									<td>
										<select name="level" id="level" class="form-control">
										<c:forEach var="num" items="${levelList}" varStatus="loop">
											<option value="${num}" ${(num eq '12') ? "selected" : ''}>${num}</option>
										</c:forEach>
										</select>
									</td>
									<td colspan="2"></td>
								</tr>
								<tr>
									<td style="width:10%"><label class="col-form-label" for="place">지명</label></td>
									<td style="width:30%"><input type="text" name="places" id="place" class="form-control"></td>
									<td style="width:30%"><input type="text" name="places" class="form-control"></td>
									<td style="width:30%"><input type="text" name="places" class="form-control"></td>
								</tr>
								<tr>
									<td> </td>
									<td><input type="text" name="places" class="form-control"></td>
									<td><input type="text" name="places" class="form-control"></td>
									<td><input type="text" name="places" class="form-control"></td>
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
					<div class="col-1"></div>
			</div>
            <!-- =================== main =================== -->
            
        </div>
    </div>
    
    <%@ include file="../common/bottom.jsp" %>
</body>
</html>