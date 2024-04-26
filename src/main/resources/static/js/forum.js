var globalBottom;
var globalTop;
var noMorePosts = false;

/**
 * Shows the post creation modal.
 * @returns {void}
 */
function showPostModal() {
	var modal = document.getElementById("modal");

	const postCreationDetails = document.getElementById("postCreationDetails");
	postCreationDetails.innerHTML = `
				<div class="close" onclick="closeModal()">✖</div>
				<div style="margin-top: 40px;">
					<form id="postCreationForm">
						<p>Title:</p>
						<input type="text" id="title" name="title" required><br>
						<p>Content:</p>
						<textarea id="content" name="content" required></textarea><br>
						<a style="margin-bottom: 40px;" onclick="submitPost()">Create Post</a>
					</form>
				</div>
            `;

	modal.style.display = "block";
	setTimeout(() => {
		modal.style.top = "0"; // This will trigger the transition
	}, 10);
}

/**
 * Closes the modal.
 * @returns {void}
 */
function closeModal() {
	var modal = document.getElementById("modal");
	modal.style.top = "-100%"; // This will trigger the transition
	setTimeout(() => {
		modal.style.display = "none";
	}, 500);
}

/**
 * Submits a post to the server.
 * @returns {void}
 * @throws {Error} If the network response was not ok.
 * @throws {Error} If there was a problem with the fetch operation.
 * @throws {void} If the post is successfully created.
 */
function submitPost() {
	const csrfToken = document
		.querySelector('meta[name="_csrf"]')
		.getAttribute("content");
	const csrfHeader = document
		.querySelector('meta[name="_csrf_header"]')
		.getAttribute("content");

	const title = document.getElementById("title").value;
	const content = document.getElementById("content").value;

	const username = document.getElementById("username").innerText;

	if (!title || !content) {
		alert("Please fill in all fields");
		return;
	}

	const post = {
		username: username,
		title: title,
		content: content,
	};

	console.log(post);

	const requestOptions = {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
			[csrfHeader]: csrfToken,
		},
		body: JSON.stringify(post),
	};

	fetch("/posts", requestOptions)
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
			throw new Error("Network response was not ok.");
		})
		.then((data) => {
			console.log("Post created:", data);
		})
		.catch((error) => {
			console.error(
				"There was a problem with your fetch operation:",
				error
			);
		});

	closePostModal();
}

/**
 * Closes the post creation modal.
 * @returns {void}
 */
function closePostModal() {
	var modal = document.getElementById("modal");
	modal.style.top = "-100%"; // This will trigger the transition
	setTimeout(() => {
		modal.style.display = "none";
		window.location.reload();
	}, 500);
}

/**
 * Initializes the forum page.
 * @returns {void}
 * @throws {Error} If there was a problem with the fetch operation.
 * @throws {void} If the posts are successfully fetched.
 */
function fetchPosts() {
	console.log("Fetching posts...");
	const url = "/posts?top=" + globalTop + "&bottom=" + globalBottom;
	fetch(url)
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
			throw new Error("Network response was not ok.");
		})
		.then((data) => {
			if (data.length === 0) {
				document.getElementById("postsHeader").textContent =
					"No more posts to show.";
				noMorePosts = true;
				updateNavigationBar();
				return;
			}
			data.forEach((post) => {
				createPostContainer(post);
			});
		})
		.catch((error) => {
			console.error(
				"There was a problem with your fetch operation:",
				error
			);
		});
}

/**
 * Creates a post container.
 * @param {Object} post - The post object.
 * @returns {void}
 */
function createPostContainer(post) {
	const subContainer = document.getElementById("postContainer");
	const postContainer = document.createElement("div");
	postContainer.classList.add("post-container");

	const postHeader = document.createElement("div");
	postHeader.classList.add("post-header");
	postHeader.innerHTML = `
						<span class="post-title">${post.title}</span>
						<div class="post-user-info">
							<span onclick="redirectToProfile('${post.user.username}')" class="post-user">${post.user.username}</span>
							<img class="post-pfp" src="${post.user.profilePictureURL}" onclick="redirectToProfile('${post.user.username}')">
						</div>
                `;

	const postContent = document.createElement("div");
	postContent.classList.add("post-content");
	postContent.innerHTML = `<p>${post.content}</p>`;

	const additionalHeader = document.createElement("div");
	additionalHeader.classList.add("additional-header");
	let formattedDate = post.dateCreated.substring(0, 16).replace("T", ", ");
	additionalHeader.innerHTML = `
                    <a class="view-post" onclick="redirectToPost(${post.id})">View Post</a>
                    <span class="date-created">${formattedDate}</span>
                `;

	postContainer.appendChild(postHeader);
	postContainer.appendChild(postContent);
	postContainer.appendChild(additionalHeader);

	const navButton = document.getElementById("turnPageDownButton");
	subContainer.insertBefore(postContainer, navButton);
}

/**
 * Sets the top and bottom global variables.
 * which are used to fetch posts specific number
 * of posts.
 * @returns {void}
 */
function setTopAndBottom() {
	var bottom = parseInt(getQueryParamater("bottom"));
	var top = parseInt(getQueryParamater("top"));

	if (bottom) {
		globalBottom = bottom;
	} else {
		globalBottom = 0;
	}

	if (top) {
		globalTop = top;
	} else {
		globalTop = 9;
	}

	console.log("Bottom:", globalBottom);
	console.log("Top:", globalTop);

	updateNavigationBar();
}

/**
 * Updates the navigation bar.
 * @returns {void}
 */
function updateNavigationBar() {
	const turnPageDownButton = document.getElementById("turnPageDownButton");
	if (globalBottom === 0) {
		turnPageDownButton.style.visibility = "hidden";
	} else {
		turnPageDownButton.style.visibility = "visible";
	}

	const turnPageUpButton = document.getElementById("turnPageUpButton");
	if (noMorePosts) {
		turnPageUpButton.style.visibility = "hidden";
	} else {
		turnPageUpButton.style.visibility = "visible";
	}

	document.getElementById("globalBottom").textContent = globalBottom + 1;
	document.getElementById("globalTop").textContent = globalTop + 1;
}

/**
 * Gives +10 to the globalTop and globalBottom to
 * fetch the next 10 posts accordingly.
 * @returns {void}
 */
function turnPageUp() {
	globalTop += 10;
	globalBottom += 10;
	window.location.href =
		"/forum?top=" + globalTop + "&bottom=" + globalBottom;
}

/**
 * Gives -10 to the globalTop and globalBottom to
 * fetch the previous 10 posts accordingly.
 * @returns {void}
 */
function turnPageDown() {
	globalTop -= 10;
	globalBottom -= 10;
	window.location.href =
		"/forum?top=" + globalTop + "&bottom=" + globalBottom;
}

/**
 * Gets the query parameter from the URL.
 * @param {string} paramater - The query parameter.
 * @returns {string} The query parameter value.
 */
function getQueryParamater(paramater) {
	const urlParams = new URLSearchParams(window.location.search);
	return urlParams.get(paramater);
}

/**
 * Redirects to the post page.
 * @param {number} postId - The post id.
 * @returns {void}
 */
function redirectToPost(postId) {
	window.location.href = "/post/" + postId;
}

/**
 * Redirects to the profile page.
 * @param {string} username - The username to redirect to.
 * @returns {void}
 */
function redirectToProfile(username) {
	window.location.href = "/profile/" + username;
}
