function initializeTimeValidation() {
    const startTimeInput = document.getElementById('startTime') as HTMLInputElement | null;
    if (startTimeInput) {
        startTimeInput.addEventListener('change', function() {
            const startTime = this.value;
            const endTimeSelect = document.getElementById('endTime') as HTMLSelectElement | null;

            if (endTimeSelect) {
                Array.from(endTimeSelect.options).forEach(option => {
                    const optionElement = option as HTMLOptionElement;
                    optionElement.disabled = optionElement.value <= startTime;
                });

                if (endTimeSelect.value <= startTime) {
                    for (let i = 0; i < endTimeSelect.options.length; i++) {
                        const optionElement = endTimeSelect.options[i] as HTMLOptionElement;
                        if (!optionElement.disabled) {
                            endTimeSelect.selectedIndex = i;
                            break;
                        }
                    }
                }
            }
        });
    }
}

function initializeDaySelection() {
    const urlParams = new URLSearchParams(window.location.search);
    const day = urlParams.get('day');
    if (day) {
        const dayInput = document.getElementById('day') as HTMLInputElement | null;
        if (dayInput) {
            dayInput.value = day;
        }
    }
}

// Initialize all form validations
window.onload = function() {
    initializeTimeValidation();
    initializeDaySelection();
}