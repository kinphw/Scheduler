export function initializeEditForm(): void {
    // 시간 입력 제한 설정
    const timeInputs = document.querySelectorAll('input[type="time"]');
    timeInputs.forEach(input => {
        input.addEventListener('change', function(this: HTMLInputElement) {
            const time = this.value;
            const minutes = parseInt(time.split(':')[1]);
            if (minutes % 10 !== 0) {
                const roundedMinutes = Math.round(minutes / 10) * 10;
                this.value = `${time.split(':')[0]}:${roundedMinutes < 10 ? '0' : ''}${roundedMinutes}`;
            }
        });
    });

    // 삭제 버튼 이벤트 리스너
    const deleteButton = document.querySelector('.btn-danger');
    deleteButton?.addEventListener('click', function() {
        // console.log("DEBUG");
        if (confirm('정말로 삭제하시겠습니까?')) {
            (document.getElementById('deleteForm') as HTMLFormElement)?.submit();
        }
    });

    // // 삭제 확인
    // window.confirmDelete = function(): void {
    //     if (confirm('정말로 삭제하시겠습니까?')) {
    //         (document.getElementById('deleteForm') as HTMLFormElement)?.submit();
    //     }
    // };

    // 폼 제출 시 시간 검증
    const form = document.querySelector('.edit-form') as HTMLFormElement;
    form?.addEventListener('submit', function(e: Event) {
        const startHour = (document.getElementById('startHour') as HTMLSelectElement).value;
        const startMinute = (document.getElementById('startMinute') as HTMLSelectElement).value;
        const endHour = (document.getElementById('endHour') as HTMLSelectElement).value;
        const endMinute = (document.getElementById('endMinute') as HTMLSelectElement).value;

        const startTime = parseInt(startHour + startMinute);
        const endTime = parseInt(endHour + endMinute);

        if (startTime >= endTime) {
            e.preventDefault();
            alert('종료 시간은 시작 시간보다 늦어야 합니다.');
        }
    });
}

// 페이지 로드 시 자동 초기화
document.addEventListener('DOMContentLoaded', function() {
    initializeEditForm();
});