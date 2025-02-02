declare global {
    interface Window {
        schedules: Schedule[];
    }
}

import { Schedule, DISPLAY_CONSTANTS } from '../interfaces/Schedule';
import { calculatePosition, detectOverlappingSchedules } from './grid';

export function renderSchedule(schedule: Schedule, container: HTMLElement): void {
    console.debug('3. Rendering individual schedule:', {
        schedule,
        containerExists: !!container,
        containerChildren: container.children.length
    }); // 디버그 포인트 3
    
    const position = calculatePosition(schedule);
    console.debug('4. Calculated position:', position); // 디버그 포인트 4
    
    const overlapping = detectOverlappingSchedules(schedule, window.schedules || []);
    
    const scheduleElement = document.createElement('div');
    scheduleElement.className = 'schedule-item';
    scheduleElement.dataset.id = schedule.id.toString();
    
    Object.assign(scheduleElement.style, {
        position: 'absolute',
        top: `${position.top}px`,
        height: `${position.height}px`,
        left: `${position.dayIndex * 20}%`,
        width: DISPLAY_CONSTANTS.DAY_COLUMN_WIDTH
    });

    if (overlapping.length > 0) {
        scheduleElement.classList.add('overlapping');
    }

    scheduleElement.innerHTML = `
        <div class="schedule-content">
            <span class="time">${schedule.startTime}-${schedule.endTime}</span>
            <span class="title">${schedule.content}</span>
        </div>
    `;

    container.appendChild(scheduleElement);
}

export function renderAllSchedules(schedules: Schedule[]): void {
    const container = document.getElementById('schedule-container');
    console.debug('5. renderAllSchedules called:', {
        schedulesCount: schedules.length,
        containerExists: !!container
    }); // 디버그 포인트 5
    
    if (!container) return;
    
    container.innerHTML = '';
    schedules.forEach(schedule => renderSchedule(schedule, container));
}
