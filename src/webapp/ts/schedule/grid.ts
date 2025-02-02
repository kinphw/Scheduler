import { DAYS, DayType, TIME_SLOTS, DISPLAY_CONSTANTS, Schedule, SchedulePosition } from '../interfaces/Schedule';

export function calculatePosition(schedule: Schedule): SchedulePosition {
    const dayIndex = DAYS.indexOf(schedule.day as DayType);
    const startMinutes = timeToMinutes(schedule.startTime);
    const endMinutes = timeToMinutes(schedule.endTime);
    
    const top = minutesToPixels(startMinutes - TIME_SLOTS.START_HOUR * 60);
    const height = minutesToPixels(endMinutes - startMinutes);
    
    return { top, height, dayIndex };
}

export function minutesToPixels(minutes: number): number {
    return (minutes / TIME_SLOTS.INTERVAL_MINUTES) * DISPLAY_CONSTANTS.CELL_HEIGHT;
}

export function timeToMinutes(time: string): number {
    const [hours, minutes] = time.split(':').map(Number);
    return hours * 60 + minutes;
}

export function detectOverlappingSchedules(schedule: Schedule, allSchedules: Schedule[]): Schedule[] {
    return allSchedules.filter(s => 
        s.id !== schedule.id && 
        s.day === schedule.day &&
        isTimeOverlapping(schedule, s)
    );
}

export function isTimeOverlapping(schedule1: Schedule, schedule2: Schedule): boolean {
    const start1 = timeToMinutes(schedule1.startTime);
    const end1 = timeToMinutes(schedule1.endTime);
    const start2 = timeToMinutes(schedule2.startTime);
    const end2 = timeToMinutes(schedule2.endTime);
    
    return (start1 < end2 && end1 > start2);
}
