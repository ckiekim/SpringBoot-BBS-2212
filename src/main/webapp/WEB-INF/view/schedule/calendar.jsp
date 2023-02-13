<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="height" value="${600 / numberOfWeeks}"></c:set>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
        th { text-align: center; width: 14.28%;}
    </style>
    <script>
    	var schedClicked = false;
    	function cellClick(date) {
    		if (schedClicked)
    			schedClicked = false;
    		else {
    			date = date + '';		// number type을 문자열로 변환
    			const dateForm = date.substring(0,4)+'-'+date.substring(4,6)+'-'+date.substring(6,8);
    			let t = new Date();
    			let hour = t.getHours();
    			let minute = t.getMinutes();
    			if (minute < 30)
    				minute = 30;
    			else {
    				minute = 0; hour = (hour + 1) % 24;
    			}
    			const startStr = ((hour >= 10) ? ''+hour : '0'+hour) + ':' + ((minute == 0) ? '00' : '30');
    			const endStr = ((hour >= 9) ? ''+(hour+1) : '0'+(hour+1)) + ':' + ((minute == 0) ? '00' : '30');
    			$('#startDate').val(dateForm);
    			$('#startTime').val(startStr);
    			$('#endDate').val(dateForm);
    			$('#endTime').val(endStr);
    			$('#addModal').modal('show');
    		}
    	}
    	function schedClick(sid) {
    		schedClicked = true;
    		console.log(sid);
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
                        <a href="/schedule/calendar/left2"><i class="fa-solid fa-angles-left"></i></a>
                        <a href="/schedule/calendar/left"><i class="fa-solid fa-angle-left ms-2"></i></a>
                        <span class="badge bg-primary mx-2">${year}.${month}</span>
                        <a href="/schedule/calendar/right"><i class="fa-solid fa-angle-right me-2"></i></a>
                        <a href="/schedule/calendar/right2"><i class="fa-solid fa-angles-right"></i></a>
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
                    <c:forEach var="day" items="${week}">
                        <td style="height: ${height}px;" onclick="cellClick(${day.sdate})">
                            <div class="d-flex justify-content-between">
                           	<c:if test="${day.isOtherMonth eq 1}">
                               	<div class="${(day.date eq 0 or day.isHoliday eq 1) ? 'text-danger' : day.date eq 6 ? 'text-primary' : ''}" style="opacity: 0.5;"><strong>${day.day}</strong></div>
	                        	<div style="opacity: 0.5;">
		                        <c:forEach var="name" items="${day.annivList}" varStatus="loop">
		                        	${loop.first ? '' : '&middot;'} ${name}
	                        	</c:forEach>
	                        	</div>
                           	</c:if>
                           	<c:if test="${day.isOtherMonth eq 0}">
                               	<div class="${(day.date eq 0 or day.isHoliday eq 1) ? 'text-danger' : day.date eq 6 ? 'text-primary' : ''}"><strong>${day.day}</strong></div>
                               	<div>
		                        <c:forEach var="name" items="${day.annivList}" varStatus="loop">
		                        	${loop.first ? '' : '&middot;'} ${name}
	                        	</c:forEach>
	                        	</div>
                           	</c:if>
                            </div>
                        <c:forEach var="sched" items="${day.schedList}" varStatus="loop">
                        	<div class="${loop.first ? 'mt-1' : ''}" style="font-size: 12px;" onclick="schedClick(${sched.sid})">
	                        	${fn:substring(sched.startTime, 11, 16)}
	                        	${sched.title}
                        	</div>
                        </c:forEach>
                        </td>
                    </c:forEach>
                    </tr>
                </c:forEach>
                </table>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
    
	<div class="modal" id="addModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">일정 추가</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
			
				<!-- Modal body -->
				<div class="modal-body">
					<form action="/schedule/insert" method="post">
						<table class="table table-borderless">
	                        <tr>
	                            <td colspan="2">
	                                <label for="title">제목</label>
	                                <input class="ms-5 me-2" type="checkbox" name="importance">중요
	                                <input class="form-control" type="text" id="title" name="title">
	                            </td>
	                        </tr>
	                        <tr>
	                            <td>
	                                <label for="startDate">시작일</label>
	                                <input class="form-control" type="date" id="startDate" name="startDate">
	                            </td>
	                            <td>
	                                <label for="startTime">시작시간</label>
	                                <select class="form-control" name="startTime" id="startTime">
	                                <c:forEach var="tl" items="${timeList}">
	                                    <option value="${tl}" >${tl}</option>
	                                </c:forEach>
	                                </select>
	                            </td>
	                        </tr>
	                        <tr>
	                            <td>
	                                <label for="endDate">종료일</label>
	                                <input class="form-control" type="date" id="endDate" name="endDate">
	                            </td>
	                            <td>
	                                <label for="endTime">종료시간</label>
	                                <select class="form-control" name="endTime" id="endTime">
	                                <c:forEach var="tl" items="${timeList}">
	                                    <option value="${tl}" >${tl}</option>
	                                </c:forEach>
	                                </select>
	                            </td>
	                        </tr>
	                        <tr>
	                            <td colspan="2">
	                                <label for="place">장소</label>
	                                <input class="form-control" type="text" id="place" name="place">
	                            </td>
	                        </tr>
	                        <tr>
	                            <td colspan="2">
	                                <label for="memo">메모</label>
	                                <input class="form-control" type="text" id="memo" name="memo">
	                            </td>
	                        </tr>
	                        <tr>
	                            <td colspan="2" style="text-align: right;">
	                                <button class="btn btn-primary me-2" type="submit">제출</button>
	                                <button class="btn btn-secondary" type="reset">취소</button>
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