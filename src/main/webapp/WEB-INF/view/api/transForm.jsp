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
            	<h3><strong>번역</strong> <small>(네이버 Pagago API)</small></h3>
            	<hr>
                <div class="row">
				    <div class="col-1"></div>
			        <div class="col-10">
			            <form action="/api/translate" method="post">
			                <table class="table table-borderless">
			                    <tr>
			                        <td style="width:10%">텍스트</td>
			                        <td style="width:90%"><textarea name="text" class="form-control" rows="5"></textarea></td>
			                    </tr>
			                    <tr>
			                        <td>언어 선택</td>
			                        <td style="text-align: center;">
			                        <c:forEach var="option" items="${options}">
			                            <div class="form-check-inline">
			                                <label class="form-check-label">
			                                    <input type="radio" class="form-check-input" name="lang" value="${option.get(0)}" ${option.get(0) eq 'ko' ? "checked" : ''}> ${option.get(1)}
			                                </label>
			                            </div>
			                        </c:forEach>
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