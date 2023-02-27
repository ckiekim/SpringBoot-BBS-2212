/**
 * calendar.js
 * 		calendar.jsp 에서 사용하였던 자바스크립트 코드
 */

 var schedClicked = false;
 
function cellClick(date) {
	if (schedClicked)
		schedClicked = false;
	else {
		date = date + '';		// number type을 문자열로 변환
		const dateForm = date.substring(0,4)+'-'+date.substring(4,6)+'-'+date.substring(6,8);
		let t = new Date();
		let hour = t.getHours();
		let minute = t.getMinutes();
		if (minute < 30)
			minute = 30;
		else {
			minute = 0; hour = (hour + 1) % 24;
		}
		const startStr = ((hour >= 10) ? ''+hour : '0'+hour) + ':' + ((minute == 0) ? '00' : '30');
		const endStr = ((hour >= 9) ? ''+(hour+1) : '0'+(hour+1)) + ':' + ((minute == 0) ? '00' : '30');
		$('#startDate').val(dateForm);
		$('#startTime').val(startStr);
		$('#endDate').val(dateForm);
		$('#endTime').val(endStr);
		$('#addModal').modal('show');
	}
}

function schedClick(sid) {
	schedClicked = true;
	$.ajax({
		type: 'GET',
		url: '/schedule/detail/' + sid,
		success: function(jsonSched) {
			let sched = JSON.parse(jsonSched);
			$('#sid2').val(sched.sid);
			$('#title2').val(sched.title);
			if (sched.isImportant == 1)
				$('#importance2').prop('checked', true);
			$('#startDate2').val(sched.startTime.substring(0,10));
			$('#startTime2').val(sched.startTime.substring(11,16));
			$('#endDate2').val(sched.endTime.substring(0,10));
			$('#endTime2').val(sched.endTime.substring(11,16));
			$('#place2').val(sched.place);
			$('#memo2').val(sched.memo);
			$('#updateModal').modal('show');
		}
	});
}

function deleteSchedule() {
	let sid = $('#sid2').val();
	const answer = confirm('정말로 삭제하시겠습니까?');
	if (answer) {
		location.href = '/schedule/delete/' + sid;
	}
}

function addAnniversary() {
	$('#addAnnivModal').modal('show');
}