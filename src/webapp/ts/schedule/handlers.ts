import { Schedule } from '../interfaces/Schedule';

export function handleScheduleClick(
    event: MouseEvent, 
    schedule: Schedule, 
    overlapping: Schedule[]
): void {
    event.stopPropagation();
    
    if (overlapping.length > 0) {
        showOverlappingModal(schedule, overlapping);
    } else {
        navigateToEdit(schedule);
    }
}

function showOverlappingModal(schedule: Schedule, overlapping: Schedule[]): void {
    const modal = document.createElement('div');
    modal.className = 'schedule-modal';
    modal.innerHTML = `
        <div class="modal-content">
            <h3>중복된 일정</h3>
            <ul>
                <li data-id="${schedule.id}">${schedule.content} (${schedule.startTime}-${schedule.endTime})</li>
                ${overlapping.map(s => 
                    `<li data-id="${s.id}">${s.content} (${s.startTime}-${s.endTime})</li>`
                ).join('')}
            </ul>
        </div>
    `;

    modal.addEventListener('click', (e) => {
        const target = e.target as HTMLElement;
        const li = target.closest('li');
        if (li) {
            const id = li.dataset.id;
            const selectedSchedule = [...overlapping, schedule].find(s => s.id.toString() === id);
            if (selectedSchedule) {
                navigateToEdit(selectedSchedule);
            }
        }
    });

    document.body.appendChild(modal);
}

function navigateToEdit(schedule: Schedule): void {
    location.href = `editSchedule.jsp?person=${schedule.person}&day=${schedule.day}&time=${schedule.startTime}&mode=edit&id=${schedule.id}`;
}

export function handleEmptyCellClick(day: string, time: string, person: string): void {
    location.href = `editSchedule.jsp?person=${person}&day=${day}&time=${time}&mode=add`;
}
