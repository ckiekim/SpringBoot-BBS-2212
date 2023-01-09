<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file="../common/heading.jsp" %>
    <style>
        th, td { text-align: center; }
    </style>
</head>
<body style="height: 2000px">
	<%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3><strong>인터파크 도서 베스트 셀러</strong></h3>
            	<hr>
                <div class="row">
					<table class="table">
						<tr class="d-flex">
							<th class="col-1">순위</th>
							<th class="col-1">이미지</th>
							<th class="col-4">제목</th>
							<th class="col-3">저자</th>
							<th class="col-2">출판사</th>
							<th class="col-1">가격</th>
						</tr>
					<c:forEach var="book" items="${bookList}">
						<tr class="d-flex">
							<td class="col-1">${book.rank}</td>
							<td class="col-1"><img src="${book.imgSrc}" height="36"></td>
							<td class="col-4">${book.title}</td>
							<td class="col-3">${book.author}</td>
							<td class="col-2">${book.company}</td>
							<td class="col-1"><fmt:formatNumber type="number" value="${book.price}" /></td>
						</tr>
					</c:forEach>
					</table>
				</div>
			</div>
			<!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>