<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
    	tr {text-align: center;}
    </style>
</head>
<body>
	<%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3><strong>감성분석 결과</strong> <small>(네이버 감성분석 API)</small></h3>
            	<hr>
                <div class="row">
				    <div class="col-1"></div>
			        <div class="col-10">
		                <table class="table">
		                    <tr>
		                        <td style="width:10%">콘텐트</td>
		                        <td style="width:90%">${content}</td>
		                    </tr>
		                    <tr>
		                        <td>감성</td>
		                        <td style="background-color: aliceblue;"><strong>${result}</strong></td>
		                    </tr>
		                    <tr>
		                        <td colspan="2" style="text-align: center;">
		                            <button class="btn btn-primary" 
                                			onclick="location.href='/api/sentiment'">재실행</button>
		                        </td>
		                    </tr>
		                </table>
		                <br><br><br>
			        </div>
			        <div class="col-1"></div>
				</div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>

</body>
</html>