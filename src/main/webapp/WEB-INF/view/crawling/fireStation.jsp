<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
        th, td { text-align: center; }
    </style>
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.22/css/jquery.dataTables.min.css">
    <script src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#example').DataTable({
                info: false,
                searching: true,
                paging: true,
                ordering: false,
                //order: [[ 0, "asc" ]],
                columnDefs: [{
                    "targets": [ 0 ],
                    "visible": false,
                    "searchable": false
                }],
                language: {
                    thousands: ',',
                    search: '',
                    searchPlaceholder: "검색어",
                    paginate: {
                        first: '처음',
                        previous: '이전',
                        next: '다음',
                        last: '마지막',
                    }
                },
                oLanguage: {
                    sLengthMenu: "_MENU_",
                },
                "lengthMenu": [[10, -1], ["10개씩 보기", "모두 보기"]]
            });
        } );
    </script>
</head>
<body>
    <%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3><strong>서울 소재 소방서 현황</strong></h3>
            	<hr>
				<table id="example" class="display compact" style="width:100%;">
					<thead>
						<tr>
							<th>No.</th><th>이름</th><th>주소</th><th>전화번호</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="station" items="${stationList}" varStatus="loop">
						<tr>
							<td>${loop.count}</td>
							<td>${station.name}</td>
							<td>${station.addr}</td>
							<td>${station.tel}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>