// src/main/resources/static/js/login.js

document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const usernameInput = document.querySelector('input[name="username"]');
    const passwordInput = document.querySelector('input[name="password"]');
    const submitButton = document.querySelector('button[type="submit"]');

    // Form validation
    form.addEventListener('submit', function(e) {
        const username = usernameInput.value.trim();
        const password = passwordInput.value.trim();

        if (username === '' || password === '') {
            e.preventDefault();
            alert('Please fill in all fields');
            return false;
        }

        // Add loading state to button
        submitButton.textContent = 'Signing In...';
        submitButton.disabled = true;
    });

    // Add input animation effects
    const inputs = document.querySelectorAll('input');
    inputs.forEach(input => {
        input.addEventListener('focus', function() {
            this.parentElement.classList.add('focused');
        });

        input.addEventListener('blur', function() {
            this.parentElement.classList.remove('focused');
        });
    });

    // Check for error message in URL (optional)
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('error')) {
        showError('Invalid username or password');
    }
    if (urlParams.get('logout')) {
        showSuccess('You have been logged out successfully');
    }
});

function showError(message) {
    const container = document.querySelector('.login-container');
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = message;
    errorDiv.style.cssText = 'color: #d32f2f; margin-top: 1rem; padding: 0.5rem; background: #ffebee; border-radius: 4px;';
    container.appendChild(errorDiv);

    setTimeout(() => errorDiv.remove(), 3000);
}

function showSuccess(message) {
    const container = document.querySelector('.login-container');
    const successDiv = document.createElement('div');
    successDiv.className = 'success-message';
    successDiv.textContent = message;
    successDiv.style.cssText = 'color: #388e3c; margin-top: 1rem; padding: 0.5rem; background: #e8f5e9; border-radius: 4px;';
    container.appendChild(successDiv);

    setTimeout(() => successDiv.remove(), 3000);
}