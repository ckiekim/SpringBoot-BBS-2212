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
			    <h3><strong>음성 인식(녹음)</strong></h3>
			    <hr>
			    <div class="row">
			    	<div class="col-2"></div>
			        <div class="col-8">
				    	<form action="/api/speechRecog" method="post">
				        <table class="table table-borderless">
				            <tr class="d-flex">
				                <td class="col-2">언어 선택</td>
				                <td class="col-10">
				                    <input type="radio" name="language" value="Kor" checked> 한국어
				                    <input type="radio" name="language" value="Eng"> 영어
				                    <input type="radio" name="language" value="Jpn"> 일본어
				                    <input type="radio" name="language" value="Chn"> 중국어
				                </td>
				            </tr>
				            <tr><td> </td><td> </td></tr>
				            <tr>
				                <td colspan="2">
				                    <button id="record" class="btn btn-danger me-2">녹음</button>
				                    <span id="mic" class=""></span>
				                    <button id="stop" class="btn btn-dark disabled">정지</button>
				                </td>
				            </tr>
				            <tr>
				                <td colspan="2" id="td1"></td>      <!-- 오디오 컨트롤 -->
				            </tr>
				            <tr>
				                <td colspan="2" id="td2"></td>      <!-- 제출 버튼 -->
				            </tr>
				        </table>
				    	</form>
				    </div>
			        <div class="col-2"></div>
			    </div>
			</div>
            <!-- =================== main =================== -->
            
        </div>		<!-- row -->
    </div>			<!-- container -->
    
    <%@ include file="../common/bottom.jsp" %>
    <script src="/js/audio.js?q=1"></script>    
</body>
</html>