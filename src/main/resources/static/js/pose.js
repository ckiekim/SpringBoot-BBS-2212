/**
 *  Naver Pose Estimation API
 */
function estimatePose(jsonStr, fileName) {
	let partNames = ['코','목','오른쪽 어깨','오른쪽 팔굼치','오른쪽 손목','왼쪽 어깨',
                     '왼쪽 팔굼치','왼쪽 손목','오른쪽 엉덩이','오른쪽 무릎','오른쪽 발목','왼쪽 엉덩이',
                     '왼쪽 무릎','왼쪽 발목','오른쪽 눈','왼쪽 눈','오른쪽 귀','왼쪽 귀'];
    let obj = JSON.parse(jsonStr);
    let body = obj.predictions[0];

    const canvas = document.getElementById('tcanvas');
    let ctx = canvas.getContext("2d");
    let img = new Image();
    img.src = '/bbs/file/download?file=' + fileName;
    img.onload = function() {
        canvas.width = img.width;
        canvas.height = img.height;
        ctx.drawImage(img, 0, 0, img.width, img.height);

        ctx.strokeStyle = 'yellow';
        ctx.linewidth = 2;
        ctx.fillStyle = 'red';
        for (let i=0; i<18; i++) {
            let part = body[i];
            if (part == null)
                continue;
            let name = partNames[i];
            
            let x = part.x * img.width;
            let y = part.y * img.height;
            let label = name + ' (' + parseInt(part.score * 100) + '%)';
            let circle = new Path2D();
            circle.moveTo(x, y);
            circle.arc(x, y, 6, 0, 2 * Math.PI);
            ctx.fill(circle);
            ctx.stroke();
            if (i < 10)
                ctx.strokeText(i, x-3, y+3);
            else
            	ctx.strokeText(i, x-6, y+3);
        }
    }
}