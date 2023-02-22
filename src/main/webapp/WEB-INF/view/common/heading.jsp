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
                data: {'addr': $('#address').text()},
                success: function(result) {
                    // console.log("success");
                    $('#weatherInfo').html(result);
                },
    		});
    	}
    	function editProfile(uid) {
    		$.ajax({
    			type: "GET",
                url: "/aside/profile/" + uid,
                success: function(result) {
                    const profile = JSON.parse(result);
                    $('#github').val(profile.github);
                    $('#instagram').val(profile.instagram);
                    $('#facebook').val(profile.facebook);
                    $('#twitter').val(profile.twitter);
                    $('#homepage').val(profile.homepage);
                    $('#blog').val(profile.blog);
                    $('#addr').val(profile.addr);
                    $('#filename').val(profile.filename);
                }
    		});
    		$('#profile').hide();
    		$('#hiddenProfile').show();
    	}
    	function editExecute(uid) {
    		const formData = new FormData();
    		formData.append('uid', uid);
    		formData.append('github', $('#github').val());
    		formData.append('instagram', $('#instagram').val());
    		formData.append('facebook', $('#facebook').val());
    		formData.append('twitter', $('#twitter').val());
    		formData.append('homepage', $('#homepage').val());
    		formData.append('blog', $('#blog').val());
    		formData.append('addr', $('#addr').val());
    		formData.append('filename', $('#filename').val());
    		let imageInputVal = $('#image')[0];
			formData.append('image', imageInputVal.files[0]);
			$.ajax({
    			type: "POST",
                url: "/aside/profile",
                data: formData,
				processData: false,
				contentType: false,
                success: function(result) {
                    /* const profile = JSON.parse(result);
                    console.log(profile); */
		    		$('#profile').show();
		    		$('#hiddenProfile').hide();
                    location.reload();
                },
    		});
    	}
    	function editCancel() {
    		$('#profile').show();
    		$('#hiddenProfile').hide();
    	}
    </script>