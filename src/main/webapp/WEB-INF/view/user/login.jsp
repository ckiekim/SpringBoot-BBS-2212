<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="../common/heading.jsp" %>
</head>

<body>
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
        <div class="container-fluid">
            <img src="/img/ckworld-logo.png" alt="Logo" style="height:60px;" class="rounded-3 mx-2">
            <div class="p-2 bg-dark justify-content-center rounded">
                <img src="https://picsum.photos/1500/200" width="100%">
            </div>
        </div>
    </nav>
    <div class="container" style="margin-top: 300px;">
        <div class="row">
            <div class="col-4"></div>
            <div class="col-4">
                <h3>
                    <strong>로그인</strong>
                    <span style="font-size: 0.6em;">
                        <a href="/bbs/user/register" class="ms-5"><i class="fas fa-user-plus"></i> 회원가입</a>
                    </span>
                </h3>
                <hr>
                <form action="/bbs/user/login" method="post">
                    <table class="table table-borderless">
                        <tr>
                            <td><label for="uid" class="col-form-label">아이디</label></td>
                            <td><input type="text" name="uid" id="uid" class="form-control" placeholder="아이디"></td>
                        </tr>
                        <tr>
                            <td><label for="pwd" class="col-form-label">패스워드</label></td>
                            <td><input type="password" name="pwd" id="pwd" class="form-control" placeholder="패스워드"></td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: center;">
                                <input class="btn btn-primary" type="submit" value="로그인">
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