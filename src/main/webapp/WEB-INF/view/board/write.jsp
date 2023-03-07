<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
            	<h3><strong>게시글 쓰기</strong></h3>
            	<hr>
	            <form action="/bbs/board/write" method="post" enctype="multipart/form-data">
	            	<input type="hidden" name="uid" value="${uid}">
	                <table class="table table-borderless">
	                    <tr>
	                        <td style="width:10%"><label class="col-form-label">제목</label></td>
	                        <td style="width:90%"><input class="form-control" type="text" name="title"></td>
	                    </tr>
	                    <tr>
	                        <td><label class="col-form-label">내용</label></td>
	                        <td>
	                        	<textarea class="form-control" name="content" rows="10"></textarea>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td><label class="col-form-label">첨부파일</label></td>
	                        <td><input class="form-control" type="file" name="files" multiple></td>
	                    </tr>
	                    <tr>
	                        <td colspan="2" style="text-align: center;">
	                            <input class="btn btn-primary" type="submit" value="제출">
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