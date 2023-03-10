<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
        th, td { text-align: center; }
    </style>
    <script src="https://cdn.ckeditor.com/4.18.0/standard/ckeditor.js"></script>
</head>

<body>
    <%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3>
            		<strong>게시글 수정</strong>
            		<span style="font-size: 0.6em;">
                        <a href="/bbs/board/list?p=${currentBoardPage}&f=&q=" class="ms-5"><i class="fa-solid fa-list"></i> 목록</a>
                    </span>
                    <span style="font-size: 0.6em;">
                        <a href="/bbs/board/detail?bid=${board.bid}&uid=${board.uid}" class="ms-3"><i class="fa-solid fa-magnifying-glass"></i> 조회</a>
                    </span>
            	</h3>
            	<hr>
	            <form action="/bbs/board/update" method="post" enctype="multipart/form-data">
	            	<input type="hidden" name="bid" value="${board.bid}">
	            	<input type="hidden" name="uid" value="${board.uid}">
	                <table class="table table-borderless">
	                    <tr class="d-flex">
	                        <td class="col-1"><label class="col-form-label">제목</label></td>
	                        <td class="col-11" colspan="2"><input class="form-control" type="text" name="title" value="${board.title}"></td>
	                    </tr>
	                    <tr class="d-flex">
	                        <td class="col-1"><label class="col-form-label">내용</label></td>
	                        <td class="col-11" colspan="2">
	                        	<textarea class="form-control" name="content" rows="10">${board.content}</textarea>
	                        </td>
	                    </tr>
	                    <tr class="d-flex">
	                        <td class="col-1">첨부파일</td>
	                        <td class="col-1">삭제</td>
	                        <td class="col-10" style="text-align: left">
	                        <c:forEach var="file" items="${fileList}">
	                        	<input class="ms-2" type="checkbox" name="delFile" value="${file}"> ${file}
	                        </c:forEach>
	                        </td>
	                    </tr>
	                    <tr class="d-flex">
	                    	<td class="col-1"></td>
	                        <td class="col-1"><label class="col-form-label">추가</label></td>
	                        <td class="col-10"><input class="form-control" type="file" name="files" multiple></td>
	                    </tr>
	                    <tr>
	                        <td colspan="2" style="text-align: center;">
	                            <input class="btn btn-primary" type="submit" value="수정">
	                            <input class="btn btn-secondary" type="reset" value="취소">
	                        </td>
	                    </tr>
	                </table>
	            </form>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
    <script>
        CKEDITOR.replace('content', {
            filebrowserImageUploadUrl: '/bbs/file/upload',
            filebrowserUploadMethod: 'form',
            height:400, width:800,
        });
    </script> 
</body>
</html>