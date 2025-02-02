let schedules = [];
let currentPerson = '';

// 스케줄 데이터 초기화
function initializeSchedules(schedulesData, person) {
    schedules = schedulesData;
    currentPerson = person;
}

// 페이지 로드 처리
function handlePageLoad(result, message) {
    console.log('Window loaded, rendering schedules...');
    renderSchedules(schedules, currentPerson);

    // result와 message가 'null' 또는 undefined가 아닐 때만 alert 표시
    if (result && result !== 'null' && message && message !== 'null') {
        showResultMessage(result, message);
    }
}

// 결과 메시지 표시
function showResultMessage(result, message) {
    alert(message + ' :' + (result === 'success' ? '성공' : '실패'));
}

// 스케줄 렌더링
function renderSchedules() {
    if (!schedules || schedules.length === 0) {
        console.log('No schedules to render');
        initializeEmptyCells(currentPerson);
        return;
    }

    schedules.forEach(schedule => {
        const cells = document.querySelectorAll(`.schedule-cell[data-day="${schedule.day}"]`);
        cells.forEach(cell => {
            const cellTime = cell.getAttribute('data-time');
            if (isTimeInRange(cellTime, schedule.startTime, schedule.endTime)) {
                renderCell(cell, schedule);
            }
        });
    });
    initializeEmptyCells(currentPerson);
}

// 셀 렌더링
function renderCell(cell, schedule) {
    if (cell.innerHTML) {
        cell.innerHTML += '<br>' + schedule.content;  // 기존 내용이 있으면 줄바꿈 후 추가
    } else {
        cell.innerHTML = schedule.content;  // 첫 내용이면 그대로 표시
    }
    cell.classList.add('occupied');
    cell.onclick = () => handleCellClick(cell, schedule);
}

// 셀 클릭 처리
function handleCellClick(cell, schedule) {
    location.href = `editSchedule.jsp?person=${currentPerson}&day=${schedule.day}&time=${cell.getAttribute('data-time')}&mode=edit&id=${schedule.id}`;
}