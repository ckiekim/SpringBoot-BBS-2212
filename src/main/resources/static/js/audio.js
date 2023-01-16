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