<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

    <nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
        <div class="container-fluid">
            <ul class="navbar-nav">
                <a class="navbar-brand ms-5 me-5" href="#">
                    <img src="/img/ckworld-logo.png" alt="Logo" style="height:36px;" class="rounded-3">
                </a>
                <li class="nav-item ms-3">
                    <a class="nav-link ${menu eq 'board' ? "active" : ''}" href="/bbs/board/list?p=1&f=&q="><i class="far fa-list-alt"></i> 게시판</a>
                </li>
                <li class="nav-item ms-3">
                    <a class="nav-link ${menu eq 'user' ? "active" : ''}" href="/bbs/user/list/1"><i class="fas fa-user-friends"></i> 사용자</a>
                </li>
                <li class="nav-item dropdown ms-3">
	                <a class="nav-link dropdown-toggle ${(menu eq 'api') ? "active" : ''}" href="#" data-bs-toggle="dropdown">
	                    <i class="fa-solid fa-cloud-arrow-down"></i> API's
	                </a>
	                <ul class="dropdown-menu">
	                    <li><a class="dropdown-item" href="/api/hotPlaces">Hot Places</a></li>
	                    <li><a class="dropdown-item" href="/api/translate">번역</a></li>
	                    <li><a class="dropdown-item" href="/api/sentiment">감성 분석</a></li>
	                    <li><hr class="dropdown-divider"></hr></li>
	                    <li><a class="dropdown-item" href="/api/ocr">광학문자(OCR) 인식</a></li>
	                    <li><a class="dropdown-item" href="/api/detect">객체 검출</a></li>
	                    <li><a class="dropdown-item" href="/api/pose">Pose Estimation</a></li>
	                    <li><a class="dropdown-item" href="/api/speechRecog">음성 인식</a></li>
	                </ul>
	            </li>
	            <li class="nav-item dropdown ms-3">
	                <a class="nav-link dropdown-toggle ${menu eq 'crawling' ? "active" : ''}" href="#" data-bs-toggle="dropdown">
	                    <i class="fa-solid fa-spider"></i> 크롤링
	                </a>
	                <ul class="dropdown-menu">
	                    <li><a class="dropdown-item" href="/crawling/interpark">인터파크</a></li>
	                    <li><a class="dropdown-item" href="/crawling/genie2">지니차트</a></li>
	                    <li><a class="dropdown-item" href="/crawling/fireStation">소방서</a></li>
	                </ul>
	            </li>
	            <li class="nav-item dropdown ms-3">
	                <a class="nav-link dropdown-toggle ${menu eq 'python' ? "active" : ''}" href="#" data-bs-toggle="dropdown">
	                    <i class="fa-solid fa-brain"></i> 인공지능
	                </a>
	                <ul class="dropdown-menu">
	                    <li><a class="dropdown-item" href="/python/chatbot">Streamlit</a></li>
	                    <li><a class="dropdown-item" href="/python/chatbot2">챗봇</a></li>
	                </ul>
	            </li>
                <li class="nav-item ms-3">
                    <a class="nav-link" href="/bbs/user/logout"><i class="fas fa-sign-out-alt"></i> 로그아웃</a>
                </li>
            </ul>
            <span class="navbar-text me-3">${uname}님 환영합니다.</span>
        </div>
    </nav>