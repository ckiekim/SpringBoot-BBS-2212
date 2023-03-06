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
            	<h3><strong>번역 결과 (${lang})</strong> <small>(네이버 Pagago API)</small></h3>
            	<hr>
                <div class="row">
				    <div class="col-1"></div>
			        <div class="col-10">
		                <table class="table">
		                    <tr>
		                        <td style="width:10%">원문</td>
		                        <td style="width:90%">${srcText}</td>
		                    </tr>
		                    <tr>
		                        <td>번역문</td>
		                        <td style="background-color: aliceblue;">${dstText}</td>
		                    </tr>
		                    <tr>
		                        <td colspan="2" style="text-align: center;">
		                            <button class="btn btn-primary" 
                                			onclick="location.href='/api/translate'">재실행</button>
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