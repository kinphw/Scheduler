export function initializeHelp(): void {
    const helpButton = document.getElementById('helpButton');
    if (helpButton) {
        helpButton.addEventListener('click', showHelp);
    }
}

function showHelp(): void {
    alert(
        "건우건영특공대 시간표관리 v0.1.0 DD 250308" +
        "\n" +
        "by APPA" );
}