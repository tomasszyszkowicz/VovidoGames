var global_entity = 1;

function submitForm() {
	global_entity = document.getElementById("entity").value;

	let url;

	switch (global_entity) {
		case "1":
			url = "/users";
			break;
		case "2":
			url = "/posts";
			break;
		case "3":
			url = "/comments";
			break;
		default:
			console.error("Invalid entity selection");
			return;
	}

	fetch(url)
		.then((response) => {
			if (!response.ok) {
				throw new Error("Network response was not ok");
			}
			return response.json();
		})
		.then((data) => {
			const resultsTable = document.getElementById("resultsTable");
			resultsTable.innerHTML = ""; // Clear existing table data

			// Create header row
			let headerRow = resultsTable.insertRow();
			let headers = [];
			let fields = [];

			if (global_entity === "1") {
				headers = ["ID", "Username", "Email", "Avatar", "Actions"];
				fields = [
					"id",
					"username",
					"email",
					"profilePictureURL",
					"actions",
				];
			} else if (global_entity === "2") {
				headers = ["Post ID", "Author", "Title", "Actions"];
				fields = ["id", "user.username", "title", "actions"];
			} else if (global_entity === "3") {
				headers = ["Comment ID", "Post ID", "Author", "Actions"];
				fields = ["id", "post.id", "user.username", "actions"];
			}

			headers.forEach((text) => {
				let headerCell = headerRow.insertCell();
				headerCell.textContent = text;
				headerCell.classList.add("table-header");
			});

			data.forEach((element) => {
				let row = resultsTable.insertRow();
				fields.forEach((field, index) => {
					let cell = row.insertCell();
					if (
						field === "profilePictureURL" &&
						global_entity === "1"
					) {
						let img = document.createElement("img");
						img.src = element[field];
						img.alt = "Profile Picture";
						img.style.width = "50px";
						img.style.height = "50px";
						cell.appendChild(img);
					} else if (field === "actions" && global_entity === "1") {
						let deleteButton = document.createElement("button");
						deleteButton.textContent = "Delete";
						deleteButton.onclick = function () {
							deleteEntity(element.username);
						};
						cell.appendChild(deleteButton);
					} else if (field === "actions" && global_entity === "2") {
						let deleteButton = document.createElement("button");
						deleteButton.textContent = "Delete";
						deleteButton.onclick = function () {
							deleteEntity(element.id);
						};
						cell.appendChild(deleteButton);
					} else if (field === "actions" && global_entity === "3") {
						let deleteButton = document.createElement("button");
						deleteButton.textContent = "Delete";
						deleteButton.onclick = function () {
							deleteEntity(element.id);
						};
						cell.appendChild(deleteButton);
					} else if (field === "user.username") {
						cell.textContent = element.user.username;
						cell.onclick = function () {
							viewProfile(element.user.username);
						};
						cell.classList.add("clickable");
					} else if (field === "post.id") {
						cell.textContent = element.post.id;
						cell.onclick = function () {
							viewPost(element.post.id);
						};
						cell.classList.add("clickable");
					} else if (field === "id" && global_entity === "2") {
						cell.textContent = element.id;
						cell.onclick = function () {
							viewPost(element.id);
						};
						cell.classList.add("clickable");
					} else {
						cell.textContent = element[field];
						if (field === "username") {
							cell.onclick = function () {
								viewProfile(element.username);
							};
							cell.classList.add("clickable");
						}
					}
				});
			});
		})
		.catch((error) => {
			console.error("Failed to fetch data:", error);
		});
}

function deleteEntity(identifier) {
	if (global_entity === "1") {
		deleteUser(identifier);
	} else if (global_entity === "2") {
		deletePost(identifier);
	} else if (global_entity === "3") {
		deleteComment(identifier);
	}
}

function deleteUser(username) {
	const csrfToken = document
		.querySelector('meta[name="_csrf"]')
		.getAttribute("content");
	const csrfHeader = document
		.querySelector('meta[name="_csrf_header"]')
		.getAttribute("content");
	const url = "/users/" + username;

	const requestOptions = {
		method: "DELETE",
		headers: {
			"Content-Type": "application/json",
			[csrfHeader]: csrfToken,
		},
	};

	fetch(url, requestOptions)
		.then((response) => {
			if (!response.ok) {
				return response.text().then((text) => {
					throw new Error(text || "Server error");
				});
			}
			return response.text();
		})
		.then((data) => {
			console.log("User deleted:", data);
			submitForm();
		})
		.catch((error) => {
			console.error("Failed to delete user:", error);
		});
}

function deleteComment(identifier) {
	const csrfToken = document
		.querySelector('meta[name="_csrf"]')
		.getAttribute("content");
	const csrfHeader = document
		.querySelector('meta[name="_csrf_header"]')
		.getAttribute("content");
	const url = "/comments/" + identifier;

	const requestOptions = {
		method: "DELETE",
		headers: {
			"Content-Type": "application/json",
			[csrfHeader]: csrfToken,
		},
	};

	fetch(url, requestOptions)
		.then((response) => {
			if (!response.ok) {
				return response.text().then((text) => {
					throw new Error(text || "Server error");
				});
			}
			return response.text();
		})
		.then((data) => {
			console.log("Comment deleted:", data);
			submitForm();
		})
		.catch((error) => {
			console.error("Failed to delete comment:", error);
		});
}

function deletePost(identifier) {
	const csrfToken = document
		.querySelector('meta[name="_csrf"]')
		.getAttribute("content");
	const csrfHeader = document
		.querySelector('meta[name="_csrf_header"]')
		.getAttribute("content");
	const url = "/posts/" + identifier;

	const requestOptions = {
		method: "DELETE",
		headers: {
			"Content-Type": "application/json",
			[csrfHeader]: csrfToken,
		},
	};

	fetch(url, requestOptions)
		.then((response) => {
			if (!response.ok) {
				return response.text().then((text) => {
					throw new Error(text || "Server error");
				});
			}
			return response.text();
		})
		.then((data) => {
			console.log("Post deleted:", data);
			submitForm();
		})
		.catch((error) => {
			console.error("Failed to delete post:", error);
		});
}

function viewProfile(username) {
	window.location.href = "/profile/" + username;
}

function viewPost(postId) {
	window.location.href = "/post/" + postId;
}
