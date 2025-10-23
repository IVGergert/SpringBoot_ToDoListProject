let editMode = false;
let passwordVisible = false;

// Сброс состояния при загрузке/возврате через кнопку "Назад"
window.addEventListener('pageshow', function(event) {
    resetPasswordState();
});

function resetPasswordState() {
    const passwordDisplay = document.getElementById("password-display");
    const newPasswordBlock = document.getElementById("new-password-block");
    const newPasswordField = document.getElementById("new-password");

    if (passwordDisplay) passwordDisplay.style.display = "flex";
    if (newPasswordBlock) newPasswordBlock.style.display = "none";

    if (newPasswordField) {
        newPasswordField.value = "";
        newPasswordField.type = "password";
    }

    const eyeBtn = document.querySelector(".eye-btn");
    if (eyeBtn) eyeBtn.textContent = "👁️";

    editMode = false;
    passwordVisible = false;
}

function toggleEdit() {
    const passwordDisplay = document.getElementById("password-display");
    const newPasswordBlock = document.getElementById("new-password-block");
    const newPasswordField = document.getElementById("new-password");

    if (!editMode) {
        // Режим редактирования: показать поле нового пароля
        passwordDisplay.style.display = "none";
        newPasswordBlock.style.display = "flex";
        editMode = true;

    } else {
        // Режим просмотра: вернуться к точкам
        passwordDisplay.style.display = "flex";
        newPasswordBlock.style.display = "none";

        newPasswordField.value = "";
        newPasswordField.type = "password";
        passwordVisible = false;

        const eyeBtn = document.querySelector(".eye-btn");
        if (eyeBtn) eyeBtn.textContent = "👁️";

        editMode = false;
    }
}

function togglePasswordVisibility() {
    const field = document.getElementById("new-password");
    const eyeBtn = document.querySelector(".eye-btn");

    if (!passwordVisible) {
        field.type = "text";
        eyeBtn.textContent = "🙈";
        passwordVisible = true;
    } else {
        field.type = "password";
        eyeBtn.textContent = "👁️";
        passwordVisible = false;
    }
}