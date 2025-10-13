function openAddModal() {
    document.getElementById('taskModal').style.display = 'flex';
}

function openEditModal() {
    document.getElementById('editModal').style.display = 'flex';
}

function closeAddModal() {
    document.getElementById('taskModal').style.display = 'none';
}

function closeEditModal() {
    document.getElementById('editModal').style.display = 'none';

}

window.onclick = function(event) {
    const modal = document.getElementById('taskModal');
    const editModal = document.getElementById('editModal');
    if (event.target === modal) {
        closeAddModal();
    }

    if (event.target === editModal) {
        closeEditModal();
    }
}

document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape') {
        closeAddModal();
        closeEditModal();
    }
});

document.addEventListener('DOMContentLoaded', () => {
    const showModal = document.body.getAttribute('data-show-add-modal') === 'true';
    const showEditModal = document.body.getAttribute('data-show-edit-modal') === 'true';

    if (showModal) {
        openAddModal();
    } else if (showEditModal) {
        openEditModal();
    }
});
