// src/main/resources/static/js/login.js

document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const usernameInput = document.querySelector('input[name="username"]');
    const passwordInput = document.querySelector('input[name="password"]');
    const submitButton = document.querySelector('.primary-btn');

    // Form validation
    if (form) {
        form.addEventListener('submit', function(e) {
            const username = usernameInput.value.trim();
            const password = passwordInput.value.trim();

            if (username === '' || password === '') {
                e.preventDefault();
                showError('Please fill in all fields');
                return false;
            }

            if (username.length < 3) {
                e.preventDefault();
                showError('Username must be at least 3 characters');
                return false;
            }

            if (password.length < 6) {
                e.preventDefault();
                showError('Password must be at least 6 characters');
                return false;
            }

            // Add loading state to button
            submitButton.innerHTML = '<span>Signing In...</span>';
            submitButton.disabled = true;
            submitButton.style.opacity = '0.7';
            submitButton.style.cursor = 'not-allowed';
        });
    }

    // Add input focus effects
    const inputs = document.querySelectorAll('.input');
    inputs.forEach(input => {
        input.addEventListener('focus', function() {
            this.style.transform = 'scale(1.01)';
        });

        input.addEventListener('blur', function() {
            this.style.transform = 'scale(1)';
        });

        // Clear error message on input
        input.addEventListener('input', function() {
            const errorMessage = document.querySelector('.error-message');
            if (errorMessage) {
                errorMessage.remove();
            }
        });
    });

    // Check for error or success message in URL
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('error')) {
        showError('Invalid username or password');
    }
    if (urlParams.get('logout')) {
        showSuccess('You have been logged out successfully');
    }

    // Social login handlers
    const socialButtons = document.querySelectorAll('.social-btn');
    socialButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const provider = this.querySelector('span:last-child').textContent;
            showInfo(`${provider} login is not yet implemented`);
        });
    });

    // Add hover effect to primary button
    if (submitButton) {
        submitButton.addEventListener('mouseenter', function() {
            this.style.boxShadow = '0 16px 32px rgba(37,99,235,0.5)';
        });

        submitButton.addEventListener('mouseleave', function() {
            this.style.boxShadow = '0 14px 26px rgba(37,99,235,0.45)';
        });
    }
});

function showError(message) {
    removeMessages();
    const form = document.querySelector('form');
    if (!form) return;

    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = message;

    form.insertBefore(errorDiv, form.firstChild);

    setTimeout(() => errorDiv.remove(), 5000);
}

function showSuccess(message) {
    removeMessages();
    const form = document.querySelector('form');
    if (!form) return;

    const successDiv = document.createElement('div');
    successDiv.className = 'success-message';
    successDiv.textContent = message;

    form.insertBefore(successDiv, form.firstChild);

    setTimeout(() => successDiv.remove(), 5000);
}

function showInfo(message) {
    removeMessages();
    const form = document.querySelector('form');
    if (!form) return;

    const infoDiv = document.createElement('div');
    infoDiv.className = 'success-message';
    infoDiv.textContent = message;

    form.insertBefore(infoDiv, form.firstChild);

    setTimeout(() => infoDiv.remove(), 3000);
}

function removeMessages() {
    const messages = document.querySelectorAll('.error-message, .success-message');
    messages.forEach(msg => msg.remove());
}

// Add smooth animations on page load
window.addEventListener('load', function() {
    const formSide = document.querySelector('.form-side');
    const heroSide = document.querySelector('.hero-side');

    if (formSide) {
        formSide.style.opacity = '0';
        formSide.style.transform = 'translateX(20px)';

        setTimeout(() => {
            formSide.style.transition = 'all 0.6s ease-out';
            formSide.style.opacity = '1';
            formSide.style.transform = 'translateX(0)';
        }, 100);
    }

    if (heroSide) {
        heroSide.style.opacity = '0';

        setTimeout(() => {
            heroSide.style.transition = 'opacity 0.8s ease-out';
            heroSide.style.opacity = '1';
        }, 50);
    }
});