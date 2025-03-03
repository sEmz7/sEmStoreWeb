function toggleMenu() {
    const menu = document.getElementById("support-menu");
    
    if (menu.style.display === "none" || menu.style.display === "") {
        menu.style.display = "block";
    } else {
        menu.style.display = "none";
    }
}

function validateForm() {
    let isValid = true;

    let firstName = document.getElementById("first-name");
    let firstNameError = document.getElementById("first-name-error");
    if (!/^[A-ZА-Я][a-zа-я]*$/.test(firstName.value)) {
        firstNameError.textContent = "Имя должно начинаться с заглавной буквы и содержать только буквы.";
        firstNameError.style.visibility = "visible";
        isValid = false;
    } else {
        firstNameError.style.visibility = "hidden";
    }

    let lastName = document.getElementById("last-name");
    let lastNameError = document.getElementById("last-name-error");
    if (!/^[A-ZА-Я][a-zа-я]*$/.test(lastName.value)) {
        lastNameError.textContent = "Фамилия должна начинаться с заглавной буквы и содержать только буквы.";
        lastNameError.style.visibility = "visible";
        isValid = false;
    } else {
        lastNameError.style.visibility = "hidden";
    }

    let email = document.getElementById("email");
    let emailError = document.getElementById("email-error");
    if (!/\S+@\S+\.\S+/.test(email.value)) {
        emailError.textContent = "Введите корректный email.";
        emailError.style.visibility = "visible";
        isValid = false;
    } else {
        emailError.style.visibility = "hidden";
    }

    let password = document.getElementById("password");
    let passwordError = document.getElementById("password-error");
    if (password.value.length < 8) {
        passwordError.textContent = "Пароль должен быть не менее 8 символов.";
        passwordError.style.visibility = "visible";
        isValid = false;
    } else {
        passwordError.style.visibility = "hidden";
    }

    let confirmPassword = document.getElementById("confirm-password");
    let confirmPasswordError = document.getElementById("confirm-password-error");
    if (confirmPassword.value !== password.value) {
        confirmPasswordError.textContent = "Пароли не совпадают.";
        confirmPasswordError.style.visibility = "visible";
        isValid = false;
    } else {
        confirmPasswordError.style.visibility = "hidden";
    }

    return isValid;
}
