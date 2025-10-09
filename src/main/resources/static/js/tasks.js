// Функция переключения состояния задачи
function toggleTask(checkbox) {
    const taskCard = checkbox.closest('.task-card');
    const taskTitle = taskCard.querySelector('.task-title');

    if (checkbox.checked) {
        taskTitle.classList.add('completed');
        taskCard.style.opacity = '0.7';
    } else {
        taskTitle.classList.remove('completed');
        taskCard.style.opacity = '1';
    }

    // Здесь можно добавить AJAX запрос для сохранения состояния
}

// Открытие модального окна для добавления задачи
function openTaskModal() {
    // Реализация модального окна
    alert('Открытие формы создания задачи');
}

// Фильтрация задач
document.querySelectorAll('.filter-btn').forEach(btn => {
    btn.addEventListener('click', function() {
        // Убираем active у всех кнопок
        document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
        // Добавляем active к текущей
        this.classList.add('active');

        const filter = this.dataset.filter;
        filterTasks(filter);
    });
});

function filterTasks(filter) {
    const tasks = document.querySelectorAll('.task-card');

    tasks.forEach(task => {
        const checkbox = task.querySelector('input[type="checkbox"]');

        if (filter === 'all') {
            task.style.display = 'flex';
        } else if (filter === 'completed') {
            task.style.display = checkbox.checked ? 'flex' : 'none';
        } else if (filter === 'active') {
            task.style.display = !checkbox.checked ? 'flex' : 'none';
        }
    });
}

// Переключение папок
document.querySelectorAll('.folder-item').forEach(folder => {
    folder.addEventListener('click', function() {
        document.querySelectorAll('.folder-item').forEach(f => f.classList.remove('active'));
        this.classList.add('active');
    });
});