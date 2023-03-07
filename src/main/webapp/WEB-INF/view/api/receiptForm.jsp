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
            	<h3><strong>영수증 인식(OCR)</strong></h3>
            	<hr>
                <div class="row">
                	<div class="col-2"></div>
                	<div class="col-8">
					    <form action="/api/receipt" method="post" enctype="multipart/form-data">
					    	<table class="table table-borderless">
					    		<tr>
					    			<td style="width:15%"><label class="col-form-label">인식할 파일</label></td>
					    			<td style="width:70%"><input type="file" name="upload" class="form-control"></td>
					    			<td style="width:15%; text-align:center">
					    				<button type="submit" class="btn btn-primary me-2">업로드</button>
					    			</td>
					    		</tr>
					    	</table>
						</form>
					</div>
					<div class="col-2"></div>
				</div>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>