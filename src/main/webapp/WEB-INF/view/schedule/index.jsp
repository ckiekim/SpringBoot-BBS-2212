<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
        th { text-align: center; width: 14.28%;}
    </style>
</head>
<body>
	<%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3><strong>일정표</strong></h3>
            	<hr>
                <div class="d-flex justify-content-between">
                    <div>${today}</div>
                    <div>
                        <a href="/schedule/index/left2"><i class="fa-solid fa-angles-left"></i></a>
                        <a href="/schedule/index/left"><i class="fa-solid fa-angle-left ms-2"></i></a>
                        <span class="badge bg-secondary mx-2">${year}.${month}</span>
                        <a href="/schedule/index/right"><i class="fa-solid fa-angle-right me-2"></i></a>
                        <a href="/schedule/index/right2"><i class="fa-solid fa-angles-right"></i></a>
                    </div>
                    <div><i class="fa-solid fa-pen me-3"></i><i class="fa-solid fa-table-list"></i></div>
                </div>
                <table class="table table-bordered mt-2">
                    <tr>
                        <th class="text-danger">일</th>
                        <th>월</th><th>화</th><th>수</th><th>목</th><th>금</th>
                        <th class="text-primary">토</th>
                    </tr>
                <c:forEach var="week" items="${calendar}" varStatus="loop">
                    <tr>
                        <td style="height: ${numberOfWeeks eq 5 ? '120px' : '100px'};">
                            <div class="d-flex justify-content-between">
                            	<c:if test="${loop.first and week.get(0) gt 20}">
                                	<div class="text-danger" style="opacity: 0.5;">${week.get(0)}</div>
                            		<div style="opacity: 0.5;"><i class="fa-solid fa-bars"></i></div>
                            	</c:if>
                            	<c:if test="${not (loop.first and week.get(0) gt 20)}">
                                	<div class="text-danger">${week.get(0)}</div>
                                	<div><i class="fa-solid fa-bars"></i></div>
                            	</c:if>
                            </div>
                        </td>
                    <c:forTokens var="i" items="1,2,3,4,5" delims=",">
                        <td>
                        	<div class="d-flex justify-content-between">
                        		<c:if test="${(loop.first and week.get(i) gt 20) or (loop.last and week.get(i) lt 7)}">
                                	<div style="opacity: 0.5;">${week.get(i)}</div>
                                	<div style="opacity: 0.5;"><i class="fa-solid fa-bars"></i></div>
                                </c:if>
                                <c:if test="${not ((loop.first and week.get(i) gt 20) or (loop.last and week.get(i) lt 7))}">
                                	<div>${week.get(i)}</div>
                                	<div><i class="fa-solid fa-bars"></i></div>
                                </c:if>
                            </div>
                        </td>
                    </c:forTokens>
                        <td>
							<div class="d-flex justify-content-between">
								<c:if test="${loop.last and week.get(6) lt 7}">
                                	<div class="text-primary" style="opacity: 0.5;">${week.get(6)}</div>
                                	<div style="opacity: 0.5;"><i class="fa-solid fa-bars"></i></div>
                                </c:if>
                                <c:if test="${not (loop.last and week.get(6) lt 7)}">
                                	<div class="text-primary">${week.get(6)}</div>
                                	<div><i class="fa-solid fa-bars"></i></div>
                                </c:if>
                            </div>
						</td>
                    </tr>
                </c:forEach>
                </table>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>