<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="../common/heading.jsp" %>
    <script src="/js/pose.js"></script>
    <script>
    	window.onload = function() {
    		estimatePose('${jsonResult}', '${fileName}');
    	}
    </script>
</head>
<body>
	<%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            
            <!-- =================== main =================== -->
            <div class="col-sm-9">
            	<h3><strong>Pose Estimation 결과</strong></h3>
            	<hr>
            	<div class="d-flex justify-content-center">
                	<canvas id="tcanvas" width="100" height="100" />
                </div>
			    <br>
			    <div class="row">
			    	<div class="col-2"></div>
			    	<div class="col-8">
					    <table class="table table-sm">
					        <tr><td>0</td><td>코</td><td>1</td><td>목</td><td>2</td><td>오른쪽 어깨</td></tr>
					        <tr><td>3</td><td>오른쪽 팔굼치</td><td>4</td><td>오른쪽 손목</td><td>5</td><td>왼쪽 어깨</td></tr>
					        <tr><td>6</td><td>왼쪽 팔굼치</td><td>7</td><td>왼쪽 손목</td><td>8</td><td>오른쪽 엉덩이</td></tr>
					        <tr><td>9</td><td>오른쪽 무릎</td><td>10</td><td>오른쪽 발목</td><td>11</td><td>왼쪽 엉덩이</td></tr>
					        <tr><td>12</td><td>왼쪽 무릎</td><td>13</td><td>왼쪽 발목</td><td>14</td><td>오른쪽 눈</td></tr>
					        <tr><td>15</td><td>왼쪽 눈</td><td>16</td><td>오른쪽 귀</td><td>17</td><td>왼쪽 귀</td></tr>
					    	<tr>
					    		<td colspan="6" style="text-align: center">
								    <button class="btn btn-primary" onclick="location.href='/api/pose'">재실행</button>
					    		</td>
					    	</tr>
					    </table>
					    <br><br><br>
	            	</div>
	            	<div class="col-2"></div>
            	</div>
            </div>
            <!-- =================== main =================== -->
            
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>