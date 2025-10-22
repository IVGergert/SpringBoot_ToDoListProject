
function openModal(){
    const modal = document.getElementById("taskModal");

    if (modal) {
        modal.style.display = "flex";
    }
}

function closeModal(){
    const modal = document.getElementById("taskModal");

    if (modal) {
        modal.style.display = "none";
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("taskModal");
    const modalContent = document.getElementById("taskModalContent");

    // Esc
    document.addEventListener("keydown", (e) => {
        if (e.key === "Escape") closeTaskModal();
    });

    // Клик вне окна
    modal.addEventListener("click", (e) => {
        if (!modalContent.contains(e.target)) closeTaskModal();
    });
});