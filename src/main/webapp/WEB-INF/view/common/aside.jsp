<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

			<div class="col-sm-3">
				<div class="d-flex flex-row">
					<div>
		                <img id="profileImg" class="rounded-circle" width="100%"
		                	 src="/bbs/file/download?file=${empty sessionProfile ? 'avatar_man.png' : sessionProfile}">
	                </div>
	                <div class="align-self-end me-4">
	                	<a href="#" id="profileBtn" >
	                		<span class="badge bg-light text-secondary">이미지 변경</span>
                		</a>
	                </div>
                </div>
                <div id="profileInputDisp" class="mt-2 d-none">
                	<form id="profileForm" enctype="multipart/form-data">
                		<input class="form-control form-control-sm" type="file" id="profileInput">
                	</form>
                	<a href="#" id="profileSubmit"><span class="badge bg-primary">확인</span></a>
                </div>
                
                <span class="mt-3" id="stateMsg">
                	${empty sessionStateMsg ? '피할 수 없으면 즐겨라!!!' : sessionStateMsg}
                </span>
               	<a href="#" id="stateMsgBtn"><span class="badge bg-secondary">수정</span></a>
                <div id="stateMsgInput" class="mt-2 d-none">
                	<input class="form-control form-control-sm" type="text" id="stateInput">
                	<a href="#" id="stateMsgSubmit"><span class="badge bg-primary">확인</span></a>
                </div>
                
                <div class="mt-5">
                    <i class="fa fa-envelope me-2"></i><a href="#">email-id@mulcam.com</a><br>
                    <img src="/img/github.png" height="16" class="me-2"><a href="#">github-id</a><br>
                    <img src="/img/insta.png" height="16" class="me-2"><a href="#">instagram-id</a><br>
                    <img src="/img/facebook.png" height="16" class="me-2"><a href="#">facebook-id</a><br>
                    <img src="/img/twitter.png" height="16" class="me-2"><a href="#">twitter-id</a><br>
                    <img src="/img/homepage.png" height="16" class="me-2"><a href="#">www.homepage.co.kr</a><br>
                    <img src="/img/blog.png" height="16" class="me-2"><a href="#">blog.naver.com/blog-id</a><br>
                    
                    <a href="#" id="addrChange"><img src="/img/addr.png" height="16" class="me-1"></a>
                   	<span id="addr">
                   		${empty sessionAddress ? '서울시 광진구' : sessionAddress}
                   	</span>
                   	<div id="addrInputDisp" class="mt-2 d-none">
	                	<input class="form-control form-control-sm" type="text" id="addrInput">
	                	<a href="#" id="addrSubmit"><span class="badge bg-primary">확인</span></a>
	                </div>
                   	<a href="#" id="weather"><span class="badge bg-secondary">날씨</span></a><br>
                    <div id="weatherInfo">${sessionWeather}</div>
                </div>
                <button class="btn btn-sm btn-outline-primary mt-3">Edit Profile</button>
            </div>