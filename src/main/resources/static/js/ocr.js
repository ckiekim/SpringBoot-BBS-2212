/**
 * Naver OCR(Optical Character Reader) API
 */
function ocrResult(jsonStr, fileName) {
    let obj = JSON.parse(jsonStr);
    let images = obj.images[0];
    let fields = images.fields;
    const num = fields.length;
    
    const canvas = document.getElementById('tcanvas');
    const ocrText = document.getElementById('ocrText');
    let ctx = canvas.getContext("2d");
    let img = new Image();
    
    img.src = '/bbs/file/download?file=' + fileName;
    img.onload = function() {
		let height = 600;
		let ratio = height / img.height;
		let width = Math.floor(img.width * ratio);
        /*canvas.width = img.width;
        canvas.height = img.height;
        ctx.drawImage(img, 0, 0, img.width, img.height);*/
        canvas.width = width;
        canvas.height = height;
        ctx.drawImage(img, 0, 0, width, height);

        ctx.strokeStyle = 'red';
        ctx.linewidth = 2;
        let results = '';
        for (let i=0; i<num; i++) {
			let vertices = fields[i].boundingPoly.vertices;
            let x = vertices[0].x * ratio;
            let y = vertices[0].y * ratio;
            let w = (vertices[2].x - vertices[0].x) * ratio;
            let h = (vertices[2].y - vertices[0].y) * ratio;
            ctx.strokeRect(x, y, w, h);
            let text = fields[i].inferText;
            results += text + ', ';
        }
        ocrText.innerHTML = results;
    }
}