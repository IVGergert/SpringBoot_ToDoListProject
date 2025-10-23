function openAddModal() {
    document.getElementById("addTaskModal").style.display = 'flex';
}

function closeAddModal() {
    document.getElementById("addTaskModal").style.display = "none";
}

function openEditModal() {
    document.getElementById("editTaskModal").style.display = 'flex';
}

function closeEditModal() {
    document.getElementById("editTaskModal").style.display = "none";
}

document.addEventListener("DOMContentLoaded", () => {
    const addModal = document.getElementById("addTaskModal");
    const editModal = document.getElementById("editTaskModal");

    document.addEventListener("keydown", (e) => {
        if (e.key === "Escape") {
            closeAddModal();
            closeEditModal();
        }
    });

    window.addEventListener("click", (event) => {
        if (event.target === addModal) {
            closeAddModal();
        }

        if (event.target === editModal) {
            closeEditModal();
        }
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const showAddModal = document.body.getAttribute('data-show-add-modal') === 'true';
    const showEditModal = document.body.getAttribute('data-show-edit-modal') === 'true';

    if (showAddModal) {
        openAddModal();
    } else if (showEditModal) {
        openEditModal();
    }
});
