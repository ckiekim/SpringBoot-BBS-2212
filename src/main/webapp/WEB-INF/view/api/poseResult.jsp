<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
</head>
<body>
	<%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3><strong>Pose Estimation</strong></h3>
            	<hr>
                <canvas id="tcanvas" width="100" height="100"></canvas>
			    <br><br>
			    <table class="table table-sm">
			        <tr><td>0</td><td>코</td><td>1</td><td>목</td><td>2</td><td>오른쪽 어깨</td></tr>
			        <tr><td>3</td><td>오른쪽 팔굼치</td><td>4</td><td>오른쪽 손목</td><td>5</td><td>왼쪽 어깨</td></tr>
			        <tr><td>6</td><td>왼쪽 팔굼치</td><td>7</td><td>왼쪽 손목</td><td>8</td><td>오른쪽 엉덩이</td></tr>
			        <tr><td>9</td><td>오른쪽 무릎</td><td>10</td><td>오른쪽 발목</td><td>11</td><td>왼쪽 엉덩이</td></tr>
			        <tr><td>12</td><td>왼쪽 무릎</td><td>13</td><td>왼쪽 발목</td><td>14</td><td>오른쪽 눈</td></tr>
			        <tr><td>15</td><td>왼쪽 눈</td><td>16</td><td>오른쪽 귀</td><td>17</td><td>왼쪽 귀</td></tr>
			    </table>
			    <br>
			    <button class="btn btn-primary" onclick="location.href='/api/pose'">재실행</button>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
    <script>
        let jsonStr = '${jsonResult}';
        let partNames = ['코','목','오른쪽 어깨','오른쪽 팔굼치','오른쪽 손목','왼쪽 어깨',
                         '왼쪽 팔굼치','왼쪽 손목','오른쪽 엉덩이','오른쪽 무릎','오른쪽 발목','왼쪽 엉덩이',
                         '왼쪽 무릎','왼쪽 발목','오른쪽 눈','왼쪽 눈','오른쪽 귀','왼쪽 귀'];
        let obj = JSON.parse(jsonStr);
        let body = obj.predictions[0];

        const canvas = document.getElementById('tcanvas');
        let ctx = canvas.getContext("2d");
        let img = new Image();
        img.src = '/bbs/file/download?fileName=${fileName}';
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
    </script>
</body>
</html>