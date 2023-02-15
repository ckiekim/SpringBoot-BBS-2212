<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isErrorPage="true"%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="../common/heading.jsp" %>
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
                <h3>
                    <strong>에러 페이지</strong>
                </h3>
                <hr>
                <h1>요청한 페이지는 존재하지 않습니다.</h1>
            </div>
            <div class="col-4"></div>
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>