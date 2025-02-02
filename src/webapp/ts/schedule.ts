export interface Schedule {
    id: number;
    person: string;
    day: string;
    startTime: string;
    endTime: string;
    content: string;
}

export type DayType = '월' | '화' | '수' | '목' | '금';
export const DAYS: DayType[] = ['월', '화', '수', '목', '금'];

const TIME_SLOTS = {
    START_HOUR: 9,
    END_HOUR: 21,
    INTERVAL_MINUTES: 10
};

let schedules: Schedule[] = [];
let currentPerson: string = '';

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
    location.href = `editSchedule.jsp?person=${currentPerson}&day=${schedule.day}&time=${cell.getAttribute('data-time')}&mode=edit&id=${schedule.id}`;
}

function isTimeInRange(cellTime: string, startTime: string, endTime: string): boolean {
    return cellTime >= startTime && cellTime <= endTime;
}