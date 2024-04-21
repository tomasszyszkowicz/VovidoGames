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
        <div class="close">✖</div><br>
        <div class="password-container">
            <p>Old password:</p>
            <input type="password" id="oldPassword">
            <p>New password:</p>
            <input type="password" id="newPassword">
            <p>Confirm new password:</p>
            <input type="password" id="confirmPassword"></br>
            <a onclick="updatePassword()" style="margin-top: 10px">Save</a>
            <div class="modal-error-message" id="errorMessage"></div>
        </div>
    `;
    modal.style.display = "block";
    setTimeout(() => {
        modal.style.top = "0"; // This will trigger the transition
    }, 10);

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
        <div class="close">✖</div>
        <div class="password-container">
            <p>New email:</p>
            <input type="email" id="newEmail"></br>
            <p>Password:</p>
            <input type="password" id="emailPassword"></br>
            <a onclick="updateEmail()" style="margin-top: 10px">Save</a>
            <div class="modal-error-message" id="errorMessage"></div>
        </div>
    `;
    modal.style.display = "block";
    setTimeout(() => {
        modal.style.top = "0"; // This will trigger the transition
    }, 10);

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
    // Setup the request options
    const requestOptions = {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({
            email: newEmail
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
        <div class="close">✖</div>
        <div class="password-container">
            <p>New username:</p>
            <input type="text" id="newUsername"></br>
            <p>Password:</p>
            <input type="password" id="usernamePassword"></br>
            <a onclick="updateUsername()" style="margin-top: 10px">Save</a>
            <div class="error-message" id="errorMessage" style="color: red; margin: 10px;"></div>
        </div>
    `;
    modal.style.display = "block";
    setTimeout(() => {
        modal.style.top = "0"; // This will trigger the transition
    }, 10);
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
    // Setup the request options
    const requestOptions = {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({
            username: newUsername
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
            console.log('Username successfully updated:', data);
            alert('Username successfully updated!');
        })
        .catch(error => {
            document.getElementById('errorMessage').innerText = error.message;
        });
}

function showChangeAvatarModal() {
    const modal = document.getElementById('modal');
    const modalContent = document.getElementById('profileEditDetails');
    modalContent.innerHTML = `
        <div class="close">✖</div>
        <div class="password-container">
            <p>Avatar URL:</p>
            <input type="text" id="avatarURL" placeholder="Image URL"></br>
            <p>Password:</p>
            <input type="password" id="AvatarPassword"></br>
            <a onclick="updateAvatar()" style="margin-top: 10px">Save</a>
            <div class="error-message" id="errorMessage" style="color: red; margin: 10px;"></div>
        </div>
    `;
    modal.style.display = "block";
    setTimeout(() => {
        modal.style.top = "0"; // This will trigger the transition
    }, 10);
}

function updateAvatar() {
    const username = document.getElementById('username').innerText;
    const url = `/users/${username}/avatar`;

    const csrfToken = document
        .querySelector('meta[name="_csrf"]')
        .getAttribute("content");
    const csrfHeader = document
        .querySelector('meta[name="_csrf_header"]')
        .getAttribute("content");

    const avatar = document.getElementById('avatar').files[0];
    // Setup the request options
    const requestOptions = {
        method: 'PATCH',
        headers: {
            [csrfHeader]: csrfToken
        },
        body: avatar
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