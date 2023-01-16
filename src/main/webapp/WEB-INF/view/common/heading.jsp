<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

    <title>게시판 샘플 프로젝트</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <script src="/js/bootstrap.bundle.min.js"></script>
    <script src="/js/jquery-3.6.3.min.js"></script>
    <script src="https://kit.fontawesome.com/77ad8525ff.js" crossorigin="anonymous"></script>
    <style>
        * { font-family: 'Noto Sans KR', sans-serif; }
        a { text-decoration: none; }
    </style>
    <script>
    	$(document).ready(function() {
    		$('#stateMsgBtn').click(function(e) {
    			$('#stateMsgInput').attr({'class': 'mt-2'});
    			$('#stateInput').attr({value: $('#stateMsg').text()});
    		});
    		$('#stateMsgSubmit').click(function(e) {
    			$('#stateMsgInput').attr({'class': 'mt-2 d-none'});
    			let stateInputVal = $('#stateInput').val();
    			$.ajax({
    				type: 'GET',
    				url: '/aside/stateMsg',
    				data: {stateMsg: stateInputVal},
    				success: function(e) {
    					console.log('state message:', stateInputVal);
    					$('#stateMsg').html(stateInputVal);
    				}
    			});
    		});
    		$('#weather').click(getWeatherInfo);
    	});
    	function getWeatherInfo() {
    		$.ajax({
    			type: "GET",
                url: "/aside/weather",
                data: {'addr': $('#addr').text()},
                success: function(result) {
                    console.log("success");
                    $('#weatherInfo').html(result);
                },
    		});
    	}
    </script>