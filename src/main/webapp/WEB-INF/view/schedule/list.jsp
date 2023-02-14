<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
        th, td { text-align: center;}
    </style>
    <script>
    	function addAnniversary() {
    		$('#addAnnivModal').modal('show');
    	}
    	function getDate(sdate, index) {
    		sdate = sdate + '';
    		let day = new Date(sdate.substring(0,4)+'-'+sdate.substring(4,6)+'-'+sdate.substring(6,8));
    		let date = '일 월 화 수 목 금 토'.split(' ')[day.getDay()];
    		$('#d'+index).text('(' + date + ')');
    	}
    </script>
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
                        <span class="badge bg-primary mx-2">${year}.${month}</span>
                    </div>
                    <div>
                    	<a href="#" onclick="addAnniversary()"><i class="fa-solid fa-pen me-3"></i></a>
                    	<a href="/schedule/calendar"><i class="fa-solid fa-calendar-days"></i></a>
                    </div>
                </div>
                <table class="table table-sm mt-2">
                    <tr class="table-active d-flex">
                        <th class="col-1">중요도</th>
                        <th class="col-2">날짜</th>
                        <th class="col-1">시작시각</th>
                        <th class="col-1">종료시각</th>
                        <th class="col-2">제목</th>
                        <th class="col-2">장소</th>
                        <th class="col-3">메모</th>
                    </tr>
                <c:forEach var="sched" items="${schedList}" varStatus="loop">
                    <tr class="d-flex">
                        <td class="col-1" style="text-align: center;">
                        	<input type="checkbox" ${sched.isImportant eq 1 ? 'checked' : ''}>
                        </td>
                        <td class="col-2">
                        	${fn:substring(sched.startTime, 0, 10)}<span id="d${loop.index}"></span>
                        	<script>getDate(${sched.sdate}, ${loop.index})</script>
                        </td>
                        <td class="col-1">${fn:substring(sched.startTime, 11, 16)}</td>
                        <td class="col-1">${fn:substring(sched.endTime, 11, 16)}</td>
                        <td class="col-2">${sched.title}</td>
                        <td class="col-2">${sched.place}</td>
                        <td class="col-3">${sched.memo}</td>
                    </tr>
                </c:forEach>
                </table>
                <ul class="pagination justify-content-center">
                    <li class="page-item"><a class="page-link" href="#">&laquo;</a></li>
                <c:forEach var="page" items="${pageList}" varStatus="loop">    
                    <li class="page-item ${(currentSchedulePage eq page) ? 'active' : ''}">
                    	<a class="page-link" href="/schedule/list/${page}">${page}</a>
                    </li>
                </c:forEach>   
                    <li class="page-item"><a class="page-link" href="#">&raquo;</a></li>
                </ul> 
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
    
	
	<div class="modal" id="addAnnivModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">기념일 추가</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
			
				<!-- Modal body -->
				<div class="modal-body">
					<form action="/schedule/insertAnniv" method="post">
						<table class="table table-borderless">
	                        <tr>
	                            <td style="text-align: left;">
	                                <label for="title">제목</label>
	                                <input class="ms-5 me-2" type="checkbox" name="holiday">공휴일
	                                <input class="form-control" type="text" id="title" name="title">
	                            </td>
	                        </tr>
	                        <tr>
	                            <td style="text-align: left;">
	                                <label for="annivDate">날짜</label>
	                                <input class="form-control" type="date" id="annivDate" name="annivDate">
	                            </td>
	                        </tr>
	                        <tr>
	                            <td style="text-align: right;">
	                                <button class="btn btn-primary me-2" type="submit">제출</button>
	                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">종료</button>
	                            </td>
	                        </tr>
	                    </table>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>