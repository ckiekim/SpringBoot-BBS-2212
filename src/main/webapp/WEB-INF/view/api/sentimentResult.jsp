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
		                    <tr class="d-flex">
		                        <td class="col-1">콘텐트</td>
		                        <td class="col-11">${content}</td>
		                    </tr>
		                    <tr class="d-flex">
		                        <td class="col-1">감성</td>
		                        <td class="col-11" style="background-color: aliceblue;"><strong>${result}</strong></td>
		                    </tr>
		                    <tr>
		                        <td colspan="2" style="text-align: center;">
		                            <button class="btn btn-primary" 
                                			onclick="location.href='/api/sentiment'">재실행</button>
		                        </td>
		                    </tr>
		                </table>
			        </div>
			        <div class="col-1"></div>
				</div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>

</body>
</html>