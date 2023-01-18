<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <style>
        td  { text-align: center; }
    </style>
</head>
<body>
	<%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
			    <h3><strong>음성 인식(녹음)</strong></h3>
			    <hr>
			    <div class="row">
			    	<div class="col-2"></div>
			        <div class="col-8">
				    	<form action="/api/speechRecog" method="post">
				        <table class="table table-borderless">
				            <tr class="d-flex">
				                <td class="col-2">언어 선택</td>
				                <td class="col-10">
				                    <input type="radio" name="language" value="Kor" checked> 한국어
				                    <input type="radio" name="language" value="Eng"> 영어
				                    <input type="radio" name="language" value="Jpn"> 일본어
				                    <input type="radio" name="language" value="Chn"> 중국어
				                </td>
				            </tr>
				            <tr><td> </td><td> </td></tr>
				            <tr>
				                <td colspan="2">
				                    <button id="record" class="btn btn-danger me-2">녹음</button>
				                    <span id="mic" class=""></span>
				                    <button id="stop" class="btn btn-dark disabled">정지</button>
				                </td>
				            </tr>
				            <tr>
				                <td colspan="2" id="td1"></td>      <!-- 오디오 컨트롤 -->
				            </tr>
				            <tr>
				                <td colspan="2" id="td2"></td>      <!-- 제출 버튼 -->
				            </tr>
				        </table>
				    	</form>
				    </div>
			        <div class="col-2"></div>
			    </div>
			</div>
            <!-- =================== main =================== -->
            
        </div>		<!-- row -->
    </div>			<!-- container -->
    
    <%@ include file="../common/bottom.jsp" %>
    <script>
	    const record = document.getElementById("record");
	    const stop = document.getElementById("stop");
	    const mic = document.getElementById("mic");
	    const td1 = document.getElementById("td1");
	    const td2 = document.getElementById("td2");
	    const audio = document.createElement('audio');
	
	    function tableData() {
	        audio.setAttribute('controls', '');
	        td1.append(audio);
	
	        let input = document.createElement("input");
	        input.classList.add("btn", "btn-primary", "me-2");
	        input.setAttribute('type', 'submit');
	        input.setAttribute('value', '제출');
	        td2.append(input);
	        input = document.createElement("input");
	        input.classList.add("btn", "btn-secondary");
	        input.setAttribute('type', 'reset');
	        input.setAttribute('value', '취소');
	        td2.append(input);
	    }
	
	    console.log(navigator.mediaDevices);
	    if (navigator.mediaDevices) {
	        console.log('getUserMedia supported.');
	
	        const constraints = { audio: true };
	        let chunks = [];
	
	        navigator.mediaDevices.getUserMedia(constraints).then(stream => {
	            const mediaRecorder = new MediaRecorder(stream);
	
	            record.onclick = e => {
	                e.preventDefault();
	                mediaRecorder.start();
	                console.log("recorder started", mediaRecorder.state);
	                record.classList.replace('btn-danger', 'btn-secondary')
	                mic.classList.add('me-2')
	                mic.innerHTML = '<i class="fas fa-microphone"></i>'
	                stop.classList.replace('btn-dark', 'btn-danger')
	                stop.classList.remove('disabled');
	            }
	
	            stop.onclick = e => {
	                e.preventDefault();
	                tableData();
	                mediaRecorder.stop();
	                console.log("recorder stopped", mediaRecorder.state);
	            }
	            
	            mediaRecorder.onstop = e => {
	                console.log("data available after MediaRecorder.stop() called.");
	                const blob = new Blob(chunks, {
	                    type: 'audio/wav codecs=opus'       // 44100 Hz 녹음이 됨
	                });
	
	                // 오디오 데이터 ajax
	                const formData = new FormData();
	                formData.append("audioBlob", blob);
	
	                $.ajax({
	                    type: "POST",
	                    url: "/api/audioUpload",
	                    data: formData,
	                    contentType: false,
	                    processData: false,
	                    success: function(result) {
	                        console.log("success");
	                    },
	                    error: function(result) {
	                        alert("failed");
	                    }
	                })
	                const audioURL = URL.createObjectURL(blob);
	                audio.src = audioURL
	                console.log("recorder stopped");
	            }
	            mediaRecorder.ondataavailable = e => {
	                chunks.push(e.data);
	            }
	        })
	        .catch(err => {
	            console.log('The following error occurred: ' + err);
	        })
	    }    
    </script>    
</body>
</html>