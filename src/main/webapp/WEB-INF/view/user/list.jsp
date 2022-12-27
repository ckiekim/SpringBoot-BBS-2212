<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
        th, td { text-align: center; }
        .disabled-link { pointer-events: none; }
    </style>
</head>

<body style="height: 2000px">
    <%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3><strong>사용자 목록</strong></h3>
            	<hr>
                <div class="row">
		            <table class="table table-condensed table-hover" style="margin-bottom: 80px;">
		                <tr class="table-secondary d-flex">
		                    <th class="col-1">번호</th>
		                    <th class="col-2">uid</th>
		                    <th class="col-2">이름</th>
		                    <th class="col-4">이메일</th>
		                    <th class="col-2">가입일</th>
		                    <th class="col-1">액션</th>
		                </tr>   
		                <!-- 반복되는 부분 -->
		            <c:forEach var="user" items="${userList}" varStatus="loop">
		                <tr class="d-flex">
	                        <td class="col-1">${loop.count}</td>
	                        <td class="col-2">${user.uid}</td>
	                        <td class="col-2">${user.uname}</td>
	                        <td class="col-4">${user.email}</td>
	                        <td class="col-2">${user.regDate}</td>
	                        <td class="col-1">
	                        <%-- 본인만이 수정 권한이 있음 --%>
				            <c:if test="${uid eq user.uid}">
				            	<a href="/bbs/user/update?uid=${user.uid}"><i class="fas fa-user-edit"></i></a>
				            </c:if>
				            <c:if test="${uid ne user.uid}">
				            	<a href="#" class="disabled-link"><i class="fas fa-user-edit"></i></a>
				            </c:if>
				            
				            <%-- 관리자(admin) 만이 삭제 권한이 있음 --%>
				            <c:if test="${uid eq 'admin'}">
	                            <a href="/bbs/user/delete?uid=${user.uid}"><i class="fas fa-user-minus"></i></a>
				            </c:if>
				            <c:if test="${uid ne 'admin'}">
				            	<a href="#" class="disabled-link"><i class="fas fa-user-minus"></i></a>
				            </c:if>
	                        </td>
                 		</tr> 
                 	</c:forEach>
                 	</table>    
                 	<ul class="pagination justify-content-center">
	                    <li class="page-item"><a class="page-link" href="#">&laquo;</a></li>
	                <c:forEach var="page" items="${pageList}" varStatus="loop">    
	                    <li class="page-item ${(currentUserPage eq page) ? 'active' : ''}">
	                    	<a class="page-link" href="/bbs/user/list?page=${page}">${page}</a>
	                    </li>
	                </c:forEach>   
	                    <li class="page-item"><a class="page-link" href="#">&raquo;</a></li>
	                </ul>        
                </div>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>