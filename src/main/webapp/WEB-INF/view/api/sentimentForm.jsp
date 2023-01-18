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
            	<h3><strong>감성 분석</strong> <small>(네이버 감성분석 API)</small></h3>
            	<hr>
                <div class="row">
				    <div class="col-1"></div>
			        <div class="col-10">
			            <form action="/api/sentiment" method="post">
			                <table class="table table-borderless">
			                    <tr>
			                        <td>콘텐트</td>
			                        <td><textarea name="content" cols="80" rows="5"></textarea></td>
			                    </tr>
			                        </td>
			                    </tr>
			                    <tr>
			                        <td colspan="2" style="text-align: center;">
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