/**
 * Naver Object Detect API
 */
function detectObject(jsonStr, fileName) {
    let obj = JSON.parse(jsonStr);
    let prediction = obj.predictions[0];
    let num = parseInt(prediction.num_detections);
    let names = prediction.detection_names;
    let scores = prediction.detection_scores;
    let boxes = prediction.detection_boxes;

    const canvas = document.getElementById('tcanvas');
    let ctx = canvas.getContext("2d");
    let img = new Image();
    
    img.src = '/bbs/file/download?file=' + fileName;
    img.onload = function() {
        canvas.width = img.width;
        canvas.height = img.height;
        ctx.drawImage(img, 0, 0, img.width, img.height);

        ctx.strokeStyle = 'red';
        ctx.linewidth = 2;
        for (let i=0; i<num; i++) {
            let x = boxes[i][1] * img.width;
            let y = boxes[i][0] * img.height;
            let w = (boxes[i][3] - boxes[i][1]) * img.width;
            let h = (boxes[i][2] - boxes[i][0]) * img.height;
            let label = names[i] + ' (' + parseInt(scores[i] * 100) + '%)';
            ctx.strokeRect(x, y, w, h);
            ctx.strokeText(label, x+5, y-5);
        }
    }
}