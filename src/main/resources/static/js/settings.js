function fetchUser() {
    const username = document.getElementById('username').innerText;
    const url = '/users/' + username;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            document.getElementById('detailsUsername').innerText = data.username;
            document.getElementById('detailsEmail').innerText = data.email;
            document.getElementById('pfp').src = data.profilePictureURL;
        });
}

function showChangePasswordModal() {
    const modal = document.getElementById('modal');
    const modalContent = document.getElementById('profileEditDetails');
    modalContent.innerHTML = `
        <div class="close" onclick="closeModal()">✖</div>
        <div class="password-container">
        <form id="passwordForm">
            <p>Old password:</p>
            <input type="password" id="oldPassword" required>
            <p>New password:</p>
            <input type="password" id="newPassword" required>
            <p>Confirm new password:</p>
            <input type="password" id="confirmPassword" required></br>
            <button type="submit" style="margin-top: 10px;">Change Password</button>
            <div class="modal-error-message" id="errorMessage"></div>
        </form>
        </div>
    `;
    modal.style.display = "block";
    setTimeout(() => {
        modal.style.top = "0"; // This will trigger the transition
    }, 10);

    document.getElementById('passwordForm').onsubmit = function(event) {
        event.preventDefault(); // Prevent the form from submitting traditionally
        updatePassword(); // Call your function
        return false; // Prevent the default form submission
    }

}

function updatePassword() {
    const username = document.getElementById('username').innerText;
    const url = `/users/${username}/password`;

    const csrfToken = document
        .querySelector('meta[name="_csrf"]')
        .getAttribute("content");
    const csrfHeader = document
        .querySelector('meta[name="_csrf_header"]')
        .getAttribute("content");

    const oldPassword = document.getElementById('oldPassword').value;
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    // Setup the request options
    const requestOptions = {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({
            oldPassword: oldPassword,
            newPassword: newPassword,
            confirmPassword: confirmPassword
        })
    };

    // Make the fetch request
    fetch(url, requestOptions)
        .then(response => {
            if (!response.ok) {
                // Convert non-2xx HTTP responses into errors
                return response.text().then(text => { throw new Error(text || 'Server error'); });
            }
            return response.text();
        })
        .then(data => {
            console.log('Password successfully updated:', data);
            alert('Password successfully updated!');
        })
        .catch(error => {
            document.getElementById('errorMessage').innerText = error.message;
        });
}

function showChangeEmailModal() {
    const modal = document.getElementById('modal');
    const modalContent = document.getElementById('profileEditDetails');
    modalContent.innerHTML = `
        <div class="close" onclick="closeModal()">✖</div>
        <div class="password-container">
        <form id="emailForm">
            <p>New email:</p>
            <input type="email" id="newEmail" required></br>
            <p>Password:</p>
            <input type="password" id="emailPassword" required></br>
            <div class="form-group">
                <button type="submit" style="margin-top: 10px;">Change Email</button>
            </div>
            <div class="modal-error-message" id="errorMessage"></div>
        </form>
        </div>
    `;
    modal.style.display = "block";
    setTimeout(() => {
        modal.style.top = "0"; // This will trigger the transition
    }, 10);

    document.getElementById('emailForm').onsubmit = function(event) {
        event.preventDefault(); // Prevent the form from submitting traditionally
        updateEmail(); // Call your function
        return false; // Prevent the default form submission
    };

}

function updateEmail() {
    const username = document.getElementById('username').innerText;
    const url = `/users/${username}/email`;

    const csrfToken = document
        .querySelector('meta[name="_csrf"]')
        .getAttribute("content");
    const csrfHeader = document
        .querySelector('meta[name="_csrf_header"]')
        .getAttribute("content");

    const newEmail = document.getElementById('newEmail').value;
    const password = document.getElementById('emailPassword').value;

    const requestOptions = {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({
            email: newEmail,
            password: password
        })
    };

    // Make the fetch request
    fetch(url, requestOptions)
        .then(response => {
            if (!response.ok) {
                // Convert non-2xx HTTP responses into errors
                return response.text().then(text => { throw new Error(text || 'Server error'); });
            }
            return response.text();
        })
        .then(data => {
            console.log('Email successfully updated:', data);
            alert('Email successfully updated!');
        })
        .catch(error => {
            document.getElementById('errorMessage').innerText = error.message;
        });
}

function showChangeUsernameModal() {
    const modal = document.getElementById('modal');
    const modalContent = document.getElementById('profileEditDetails');
    modalContent.innerHTML = `
        <div class="close" onclick="closeModal()">✖</div>
        <div class="password-container">
        <form id="usernameForm">
            <p style="color: red;">WARNING:</p>
            <p style="color: red;">You will have to login with your new username.</p>
            <p>New username:</p>
            <input type="text" id="newUsername" required></br>
            <p>Password:</p>
            <input type="password" id="usernamePassword" required></br>
            <button type="submit" style="margin-top: 10px;">Change Username</button>
            <div class="modal-error-message" id="errorMessage" style="color: red; margin: 10px;"></div>
        </form>
        </div>
    `;
    modal.style.display = "block";
    setTimeout(() => {
        modal.style.top = "0"; // This will trigger the transition
    }, 10);

    document.getElementById('usernameForm').onsubmit = function(event) {
        event.preventDefault(); // Prevent the form from submitting traditionally
        updateUsername(); // Call your function
        return false; // Prevent the default form submission
    }
}

function updateUsername() {
    const username = document.getElementById('username').innerText;
    const url = `/users/${username}/username`;

    const csrfToken = document
        .querySelector('meta[name="_csrf"]')
        .getAttribute("content");
    const csrfHeader = document
        .querySelector('meta[name="_csrf_header"]')
        .getAttribute("content");

    const newUsername = document.getElementById('newUsername').value;
    const password = document.getElementById('usernamePassword').value;

    const requestOptions = {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({
            username: newUsername,
            password: password
        })
    };

    // Make the fetch request
    fetch(url, requestOptions)
        .then(response => {
            if (!response.ok) {
                // Convert non-2xx HTTP responses into errors
                return response.text().then(text => { throw new Error(text || 'Server error'); });
            }
            return response.text();
        })
        .then(data => {
            window.location.href = `/login`;
        })
        .catch(error => {
            document.getElementById('errorMessage').innerText = error.message;
        });
}

function showChangeAvatarModal() {
    const modal = document.getElementById('modal');
    const modalContent = document.getElementById('profileEditDetails');
    modalContent.innerHTML = `
        <div class="close" onclick="closeModal()">✖</div>
        <div class="password-container">
        <form id="avatarForm">
            <p>Avatar URL:</p>
            <input type="text" id="avatarURL" placeholder="Image URL" required></br>
            <p>Password:</p>
            <input type="password" id="AvatarPassword" required></br>
            <button type="submit" style="margin-top: 10px;">Change Avatar</button>
            <div class="modal-error-message" id="errorMessage" style="color: red; margin: 10px;"></div>
        </form>
        </div>
    `;
    modal.style.display = "block";
    setTimeout(() => {
        modal.style.top = "0"; // This will trigger the transition
    }, 10);

    document.getElementById('avatarForm').onsubmit = function(event) {
        event.preventDefault(); // Prevent the form from submitting traditionally
        updateAvatar(); // Call your function
        return false; // Prevent the default form submission
    }
}

function updateAvatar() {
    const username = document.getElementById('username').innerText;
    const url = `/users/${username}/pfp`;

    const csrfToken = document
        .querySelector('meta[name="_csrf"]')
        .getAttribute("content");
    const csrfHeader = document
        .querySelector('meta[name="_csrf_header"]')
        .getAttribute("content");

    const avatarURL = document.getElementById('avatarURL').value;
    console.log(avatarURL)
    const password = document.getElementById('AvatarPassword').value;

    const requestOptions = {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({
            profilePictureURL: avatarURL,
            password: password
        })
    };

    // Make the fetch request
    fetch(url, requestOptions)
        .then(response => {
            if (!response.ok) {
                // Convert non-2xx HTTP responses into errors
                return response.text().then(text => { throw new Error(text || 'Server error'); });
            }
            return response.text();
        })
        .then(data => {
            console.log('Avatar successfully updated:', data);
            alert('Avatar successfully updated!');
        })
        .catch(error => {
            document.getElementById('errorMessage').innerText = error.message;
        });
}

function closeModal() {
    const modal = document.getElementById('modal');
    modal.style.top = "-100%";
    setTimeout(() => {
        modal.style.display = "none";
    }, 500);
}