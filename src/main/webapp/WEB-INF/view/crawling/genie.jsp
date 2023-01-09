<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
        th, td { text-align: center; }
    </style>
</head>
<body>
    <%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3><strong>Genie Chart</strong></h3>
            	<hr>
                <div class="row">
					<table class="table mb-5">
						<tr class="d-flex">
							<th class="col-1">순위</th>
							<th class="col-1">이미지</th>
							<th class="col-4">제목</th>
							<th class="col-3">아티스트</th>
							<th class="col-2">앨범</th>
						</tr>
					<c:forEach var="song" items="${songList}">
						<tr class="d-flex">
							<td class="col-1">${song.rank}</td>
							<td class="col-1"><img src="${song.imgSrc}" height="36"></td>
							<td class="col-4">${song.title}</td>
							<td class="col-3">${song.artist}</td>
							<td class="col-2">${song.album}</td>
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