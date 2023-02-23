<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
            	<h3><strong>문장 생성</strong> <small>(카카오 KoGPT API)</small></h3>
            	<hr>
                <div class="row">
				    <div class="col-1"></div>
			        <div class="col-10">
			            <form action="/python/genText" method="post">
			                <table class="table table-borderless">
			                    <tr>
			                        <td colspan="2">
			                        	<label for="prompt">시작하는 말(Prompt)</label>
			                        	<input class="form-control" type="text" name="prompt" id="prompt">
			                        </td>
			                    </tr>
			                    <tr class="d-flex">
			                        <td class="col-6">
			                        	<label for="maxTokens">최대 단어수</label>
			                        	<input class="form-control" type="number" name="maxTokens" id="maxTokens" value="120">
			                        </td>
			                        <td class="col-6">
			                        	<label for="numResults">결과 개수</label>
			                        	<input class="form-control" type="number" name="numResults" id="numResults" value="2">
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
			            <c:forEach var="text" items="${textList}">
						<div class="card mt-3">
							<div class="card-body">${text}</div>
						</div>			        
			        </c:forEach> 
			        </div>
			        <div class="col-1"></div>
				</div>
            <!-- =================== main =================== -->
            <br><br><br> 
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>

</body>
</html>