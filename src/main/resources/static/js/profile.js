function redirectToUserProfile() {
    username = document.getElementById("username").textContent;
    window.location.href = "/profile/" + username;
}