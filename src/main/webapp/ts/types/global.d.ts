// src/main/webapp/ts/types/global.d.ts
declare global {
    interface Window {
        confirmDelete: () => void;
        // 다른 전역 타입들도 여기에 추가
    }
}

export {};