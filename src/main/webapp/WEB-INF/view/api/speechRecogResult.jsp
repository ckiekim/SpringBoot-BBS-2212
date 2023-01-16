<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
        td  { text-align: center; }
    </style>
</head>
<body>
	<%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
			    <h3><strong>음성 인식 결과</strong></h3>
			    <hr>
				<div class="row">
			        <div class="col-1"></div>
			        <div class="col-10">
			            <table class="table">
			                <tr class="d-flex">
			                    <td class="col-2">텍스트</td>
			                    <td style="text-align: left;">${text}</td>
			                </tr>
			                <tr class="d-flex" style="background-color: aliceblue;">
			                    <td class="align-middle col-2">오디오</td>
			                    <td class="col-10">
			                    	<audio controls	src="/bbs/file/download?file=rawAudio.wav" />
			                    </td>
			                </tr>
			                <tr>
			                    <td colspan="2" style="text-align: center;">
			                        <button class="btn btn-primary" 
			                                onclick="location.href='/api/speechRecog'">재실행</button>
			                    </td>
			                </tr>
			            </table>
			        </div>
			        <div class="col-1"></div>
			    </div>			    
    		</div>
            <!-- =================== main =================== -->
            
        </div>		<!-- row -->
    </div>			<!-- container -->
    
    <%@ include file="../common/bottom.jsp" %>
    
</body>
</html>