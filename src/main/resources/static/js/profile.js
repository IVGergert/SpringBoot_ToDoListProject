let editMode = false;
let passwordVisible = false;

function toggleEdit() {
    const passwordDisplay = document.getElementById("password-display");
    const newPasswordBlock = document.getElementById("new-password-block");
    const newPasswordField = document.getElementById("new-password");

    if (!editMode) {
        passwordDisplay.style.display = "none";
        newPasswordBlock.style.display = "flex";
        editMode = true;

    } else {
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