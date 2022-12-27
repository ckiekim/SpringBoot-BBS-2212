<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
        th, td { text-align: center; }
    </style>
    <script>
    	var index = 1;
    	$(document).ready(function() {
    		$('#btn').on('click', function(e) {
    			$('<input></input>')
    				.attr({type: 'file'; name: 'file'+index++})
    				.appendTo('#additionalFile');
    		});
    	});
    </script>
</head>

<body>
    <%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3><strong>파일 다운로드</strong></h3>
            	<hr>
                <div class="row">
			        <div class="col-1"></div>
			        <div class="col-10">
					    <a href="c:/Temp/upload/1.svg" download>1.svg</a><br>
					    <a href="/bbs/board/download?file=2.svg">2.svg</a>
			        </div>
			        <div class="col-1"></div>
			    </div>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>