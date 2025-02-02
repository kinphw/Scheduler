export interface Schedule {
    id: number;
    person: string;
    day: string;
    startTime: string;
    endTime: string;
    content: string;
}

export interface SchedulePosition {
    top: number;
    height: number;
    dayIndex: number;
}

export type DayType = '월' | '화' | '수' | '목' | '금';

export const DAYS: DayType[] = ['월', '화', '수', '목', '금'];

export const TIME_SLOTS = {
    START_HOUR: 9,
    END_HOUR: 21,
    INTERVAL_MINUTES: 10
};

export const DISPLAY_CONSTANTS = {
    CELL_HEIGHT: 10,  // px per 10 minutes
    TIME_COLUMN_WIDTH: 80,  // px
    DAY_COLUMN_WIDTH: '20%'
};
