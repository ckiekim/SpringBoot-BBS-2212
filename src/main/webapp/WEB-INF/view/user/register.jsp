<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
        th, td { text-align: center; }
    </style>
</head>

<body>
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
        <div class="container-fluid">
            <img src="/img/ckworld_logo.png" alt="Logo" style="height:60px;" class="rounded-3 mx-2">
            <div class="p-2 bg-dark justify-content-center rounded">
                <img src="https://picsum.photos/1500/200" width="100%">
            </div>
        </div>
    </nav>

    <div class="container" style="margin-top: 300px;">
        <div class="row">
        	<div class="col-4"></div>
            <div class="col-4">
            	<h3><strong>회원 가입</strong></h3>
            	<hr>
	            <form action="/bbs/user/register" method="post">
	                <table class="table table-borderless">
	                    <tr>
	                        <td><label for="uid">사용자 ID</label></td>
	                        <td><input type="text" name="uid" id="uid"></td>
	                    </tr>
	                    <tr>
	                        <td><label for="pwd">패스워드</label></td>
	                        <td><input type="password" name="pwd" id="pwd"></td>
	                    </tr>
	                    <tr>
	                        <td><label for="pwd2">패스워드 확인</label></td>
	                        <td><input type="password" name="pwd2" id="pwd2"></td>
	                    </tr>
	                    <tr>
	                        <td><label for="uname">이름</label></td>
	                        <td><input type="text" name="uname" id="uname"></td>
	                    </tr>
	                    <tr>
	                        <td><label for="email">이메일</label></td>
	                        <td><input type="text" name="email" id="email"></td>
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
            <div class="col-4"></div>
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>