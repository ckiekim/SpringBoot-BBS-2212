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
				    	<table class="table table-borderless">
				    		<tr class="d-flex">
				    			<td class="col-2"><img src="/img/github.png" height="16"></td>
				    			<td class="col-10">${profile.github}</td>
				    		</tr>
				    		<tr class="d-flex">
				    			<td class="col-2"><img src="/img/insta.png" height="16"></td>
				    			<td class="col-10">${profile.instagram}</td>
				    		</tr>
				    		<tr class="d-flex">
				    			<td class="col-2"><img src="/img/avatar.jpg" height="16"></td>
				    			<td class="col-10">
				    				<img class="rounded-circle" width="100%" src="/bbs/file/download?file=profile/${fname}">
				    			</td>
				    		</tr>
				    		<tr>
				    			<td colspan="2"><button class="btn btn-primary me-2" onclick="location.href='/aside/profile'">재실행</button></td>
				    		</tr>
				    	</table>
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