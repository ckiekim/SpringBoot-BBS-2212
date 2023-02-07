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
                        <i class="fa-solid fa-less-than"></i>
                        <span class="badge bg-secondary mx-2">${year}.${month}</span>
                        <i class="fa-solid fa-greater-than"></i>
                    </div>
                    <div><i class="fa-solid fa-pen me-3"></i><i class="fa-solid fa-table-list"></i></div>
                </div>
                <table class="table table-bordered mt-2">
                    <tr>
                        <th class="text-danger">일</th>
                        <th>월</th><th>화</th><th>수</th><th>목</th><th>금</th>
                        <th class="text-primary">토</th>
                    </tr>
                <c:forEach var="week" items="${calendar}">
                    <tr>
                        <td style="height: 120px;">
                            <div class="d-flex justify-content-between">
                                <div class="text-danger">${week.get(0)}</div>
                                <div><i class="fa-solid fa-bars"></i></div>
                            </div>
                        <td>
                        	<div class="d-flex justify-content-between">
                                <div>${week.get(1)}</div>
                                <div><i class="fa-solid fa-bars"></i></div>
                            </div>
                        </td>
                        <td>
                        	<div class="d-flex justify-content-between">
                                <div>${week.get(2)}</div>
                                <div><i class="fa-solid fa-bars"></i></div>
                            </div>
                        </td>
                        <td>
                        	<div class="d-flex justify-content-between">
                                <div>${week.get(3)}</div>
                                <div><i class="fa-solid fa-bars"></i></div>
                            </div>
                        </td>
                        <td>
                        	<div class="d-flex justify-content-between">
                                <div>${week.get(4)}</div>
                                <div><i class="fa-solid fa-bars"></i></div>
                            </div>
                        </td>
                        <td>
                        	<div class="d-flex justify-content-between">
                                <div>${week.get(5)}</div>
                                <div><i class="fa-solid fa-bars"></i></div>
                            </div>
                        </td>
                        <td>
							<div class="d-flex justify-content-between">
                                <div class="text-primary">${week.get(6)}</div>
                                <div><i class="fa-solid fa-bars"></i></div>
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