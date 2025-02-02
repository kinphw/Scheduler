function initializeEmptyCells(person) {
    const cells = document.querySelectorAll('.schedule-cell:not(.occupied)');
    cells.forEach(cell => {
        cell.onclick = () => location.href =
            `editSchedule.jsp?person=${person}&day=${cell.getAttribute('data-day')}&time=${cell.getAttribute('data-time')}&mode=add`;
    });
}

function isTimeInRange(cellTime, startTime, endTime) {
    const cell = new Date(`2024-01-01 ${cellTime}:00`);
    const start = new Date(`2024-01-01 ${startTime}`);
    const end = new Date(`2024-01-01 ${endTime}`);
    return cell >= start && cell < end;
}