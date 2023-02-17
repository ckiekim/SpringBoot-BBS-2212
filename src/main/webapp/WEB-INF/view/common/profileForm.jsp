<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
    	td { text-align: center; }
    </style>
</head>
<body>
	<%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3><strong>프로파일 설정</strong></h3>
            	<hr>
                <div class="row">
                	<div class="col-4">
				    <form action="/aside/profile" method="post" enctype="multipart/form-data">
				    	<table class="table table-borderless">
				    		<tr class="d-flex">
				    			<td class="col-2"><img src="/img/github.png" height="16"></td>
				    			<td class="col-10"><input class="form-control" type="text" name="github" id="github"></td>
				    		</tr>
				    		<tr class="d-flex">
				    			<td class="col-2"><img src="/img/insta.png" height="16"></td>
				    			<td class="col-10"><input class="form-control" type="text" name="instagram" id="instagram"></td>
				    		</tr>
				    		<tr class="d-flex">
				    			<td class="col-2"><img src="/img/avatar.png" height="16"></td>
				    			<td class="col-10"><input class="form-control" type="file" name="image" id="image"></td>
				    		</tr>
				    		<tr>
				    			<td colspan="2"><button type="submit" class="btn btn-primary me-2">제출</button></td>
				    		</tr>
				    	</table>
					</form>
					</div>
					<div class="col-8"></div>
				</div>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>