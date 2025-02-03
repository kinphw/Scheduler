export interface Schedule {
    id: number;
    person: string;
    day: string;
    startTime: string;
    endTime: string;
    content: string;
}

let activeSchedules: Schedule[] = [];

export function renderCell(cell: HTMLElement, schedule: Schedule): void {
    if (cell.innerHTML) {
        cell.innerHTML += '<br>' + schedule.content;
    } else {
        cell.innerHTML = schedule.content;
    }
    cell.classList.add('occupied');
    cell.onclick = () => handleCellClick(cell, schedule);
}

export function handleCellClick(cell: HTMLElement, schedule: Schedule): void {
    location.href = `editSchedule.jsp?person=${schedule.person}&day=${schedule.day}&time=${cell.getAttribute('data-time')}&mode=edit&id=${schedule.id}`;
}

function isTimeInRange(cellTime: string, startTime: string, endTime: string): boolean {
    return cellTime >= startTime && cellTime <= endTime;
}

export function renderSchedule(schedule: Schedule): void {
    activeSchedules.push(schedule);
    renderActiveSchedules();
}

function renderActiveSchedules(): void {
    const overlay = document.getElementById('schedule-overlay');
    if (!overlay) return;
    overlay.innerHTML = '';

    activeSchedules.forEach(schedule => {
        renderSingleSchedule(schedule);
    });
}

function renderSingleSchedule(schedule: Schedule): void {
    const startTime = schedule.startTime.split(':').slice(0, 2).join('');
    const endTime = schedule.endTime.split(':').slice(0, 2).join('');
    
    const startCellId = `cell-${schedule.day}-${startTime}`;
    const endCellId = `cell-${schedule.day}-${endTime}`;
    
    const startCell = document.getElementById(startCellId);
    const endCell = document.getElementById(endCellId);
    
    if (!startCell || !endCell) {
        console.error('Cell not found:', { startCellId, endCellId });
        return;
    }

    const gridElement = document.querySelector('.schedule-grid');
    if (!gridElement) {
        console.error('Schedule grid not found');
        return;
    }

    const startRect = startCell.getBoundingClientRect();
    const endRect = endCell.getBoundingClientRect();
    const tableRect = gridElement.getBoundingClientRect();
    
    const scheduleDiv = document.createElement('div');
    scheduleDiv.className = 'schedule-item';
    
    scheduleDiv.dataset.scheduleId = schedule.id.toString();
    scheduleDiv.dataset.day = schedule.day;
    scheduleDiv.dataset.startTime = schedule.startTime;
    scheduleDiv.dataset.endTime = schedule.endTime;
    
    const width = startRect.width;
    // const height = endRect.bottom - startRect.top;
    const height = endRect.top - startRect.top;
    const left = startRect.left - tableRect.left;
    const top = startRect.top - tableRect.top;

    scheduleDiv.style.cssText = `
        position: absolute;
        top: ${top}px;
        left: ${left}px;
        width: ${width}px;
        height: ${height}px;
        background-color: rgba(0, 123, 255, 0.2);
        border: 1px solid rgba(0, 123, 255, 0.5);
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        z-index: 100;
    `;

    // scheduleDiv.innerHTML = `<span class="schedule-content">${schedule.content}</span>`;
    scheduleDiv.innerHTML = `<span class="schedule-content">${schedule.content}<br>${schedule.startTime.slice(0, 5)}<br>${schedule.endTime.slice(0, 5)}</span>`;

    scheduleDiv.addEventListener('click', (e) => {
        e.stopPropagation();
        handleCellClick(startCell, schedule);
    });

    const overlay = document.getElementById('schedule-overlay');
    if (overlay) {
        overlay.appendChild(scheduleDiv);
    }
}

// let resizeTimeout: number;
// window.addEventListener('resize', () => {
//     clearTimeout(resizeTimeout);
//     resizeTimeout = setTimeout(() => {
//         renderActiveSchedules();
//     }, 250);
// });

let isResizing = false;

window.addEventListener('resize', () => {
    if (!isResizing) {
        isResizing = true;
        requestAnimationFrame(() => {
            renderActiveSchedules();
            isResizing = false;
        });
    }
});