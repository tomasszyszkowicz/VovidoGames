var globalBottom;
var globalTop;
var noMoreComments = false;
const postId = document.getElementById('data').getAttribute('data-attribute');

/**
 * Fetches the post data from the server.
 * @returns {void}
 */
function fetchPost() {
	const postId = document
		.getElementById("data")
		.getAttribute("data-attribute");
	fetch("/posts/" + postId)
		.then((response) => response.json())
		.then((data) => {
			document.getElementById("titleHeader").innerText = data.title;
			document.title = "Vovido Games | " + data.title;
			document.getElementById("postTitle").innerText = data.title;
			document.getElementById("postAuthor").innerText = data.user.username;
			document.getElementById("postAuthor").onclick = function () {
				redirectToProfile(data.user.username);
			}
			document.getElementById("pfp").src = data.user.profilePictureURL;
			document.getElementById("pfp").onclick = function () {
				redirectToProfile(data.user.username);
			}
			document.getElementById("postContent").innerText = data.content;
			let formattedDate = data.dateCreated
				.substring(0, 16)
				.replace("T", ", ");
			document.getElementById("dateCreated").innerText = formattedDate;
		});
}

/**
 * Fetches the comments from the server.
 * @returns {void}
 */
function fetchComments() {
	const postId = document
		.getElementById("data")
		.getAttribute("data-attribute");
	const url =
		"/comments?postId=" +
		postId +
		"&top=" +
		globalTop +
		"&bottom=" +
		globalBottom;
	fetch(url)
		.then((response) => response.json())
		.then((data) => {
			if (data.length === 0) {
				if (globalBottom === 0) {
					document.getElementById("commentsHeader").innerText =
						"No comments";
					hideNavigation();
				} else {
					document.getElementById("commentsHeader").innerText =
						"No more comments";
					noMoreComments = true;
					updateNavigationBar();
				}
			}
			const commentContainer =
				document.getElementById("commentContainer");
			data.forEach((comment) => {
				let formattedDate = comment.dateCreated
					.substring(0, 16)
					.replace("T", ", ");
				const commentDiv = document.createElement("div");
				commentDiv.className = "comment-container";
				commentDiv.innerHTML = `
                    <div class="comment-header">
                        <span class="comment-date">${formattedDate}</span>
						<div class="post-user-info">
							<span onclick="redirectToProfile('${comment.user.username}')" class="comment-user">${comment.user.username}</span>
							<img class="comment-pfp" src="${comment.user.profilePictureURL}" onclick="redirectToProfile('${comment.user.username}')">
						</div>
                    </div>
                    <div class="comment-content">
                        <p>${comment.content}</p>
                    </div>`;
				const navigationButton =
					document.getElementById("turnPageDownButton");
				commentContainer.insertBefore(commentDiv, navigationButton);
			});
		});
}

/**
 * Shows the comment creation modal.
 * @returns {void}
 */
function showCommentModal() {
	var modal = document.getElementById("modal");

	const commentCreationDetails = document.getElementById(
		"commentCreationDetails"
	);
	commentCreationDetails.innerHTML = `
		<div class="close" onclick="closeModal()">✖</div>
		<div style="margin-top: 40px;">
			<form id="postCreationForm">
				<p>Comment:</p>
				<textarea id="content" name="content" required></textarea><br>
				<a style="margin-bottom: 40px;" onclick="submitComment()">Create Comment</a>
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
 */
function closeModal() {
	var modal = document.getElementById("modal");
	modal.style.top = "-100%"; // This will trigger the transition
	setTimeout(() => {
		modal.style.display = "none";
	}, 500);
}

/**
 * Submits a comment to the server.
 * @returns {void}
 * @throws {Error} If the network response was not ok.
 * @throws {Error} If there was a problem with the fetch operation.
 * @throws {void} If the comment is successfully created.
 */
function submitComment() {
	const csrfToken = document
		.querySelector('meta[name="_csrf"]')
		.getAttribute("content");
	const csrfHeader = document
		.querySelector('meta[name="_csrf_header"]')
		.getAttribute("content");

	const content = document.getElementById("content").value;

	const username = document.getElementById("username").innerText;

	const postId = document
		.getElementById("data")
		.getAttribute("data-attribute");

	if (!content) {
		alert("Please fill in all fields");
		return;
	}

	const comment = {
		username: username,
		postId: postId,
		content: content,
	};

	fetch("/comments", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
			[csrfHeader]: csrfToken,
		},
		body: JSON.stringify(comment),
	}).then((response) => {
		if (response.ok) {
			closeModalAndReload();
		} else {
			alert("Error creating comment");
		}
	});
}

/**
 * Closes the modal and reloads the page.
 * @returns {void}
 */
function closeModalAndReload() {
	var modal = document.getElementById("modal");
	modal.style.top = "-100%"; // This will trigger the transition
	setTimeout(() => {
		modal.style.display = "none";
		window.location.reload();
	}, 500);
}

/**
 * Sets the global top and bottom variables.
 * which are used for pagination.
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
	if (noMoreComments) {
		turnPageUpButton.style.visibility = "hidden";
	} else {
		turnPageUpButton.style.visibility = "visible";
	}

	document.getElementById("globalBottom").textContent = globalBottom + 1;
	document.getElementById("globalTop").textContent = globalTop + 1;
}

/**
 * Hides the navigation bar.
 * @returns {void}
 */
function hideNavigation() {
	const turnPageDownButton = document.getElementById("turnPageDownButton");
	turnPageDownButton.style.display = "none";
	const turnPageUpButton = document.getElementById("turnPageUpButton");
	turnPageUpButton.style.display = "none";
	document.getElementById("pages").style.display = "none";
}

/**
 * Gives +10 to the globalTop and globalBottom to
 * fetch the next 10 comments accordingly.
 * @returns {void}
 */
function turnPageUp() {
	globalTop += 10;
	globalBottom += 10;
	window.location.href =
		"/post/" + postId + "?top=" + globalTop + "&bottom=" + globalBottom;
}

/**
 * Gives -10 to the globalTop and globalBottom to
 * fetch the previous 10 comments accordingly.
 * @returns {void}
 */
function turnPageDown() {
	globalTop -= 10;
	globalBottom -= 10;
	window.location.href =
		"/post/" + postId + "?top=" + globalTop + "&bottom=" + globalBottom;
}

/**
 * Gets a query parameter from the URL.
 * @param {string} paramater - The query parameter to get.
 * @returns {string} The query parameter value.
 */
function getQueryParamater(paramater) {
	const urlParams = new URLSearchParams(window.location.search);
	return urlParams.get(paramater);
}

/**
 * Redirects to the profile page.
 * @param {string} username - The username to redirect to.
 * @returns {void}
 */
function redirectToProfile(username) {
	window.location.href = "/profile/" + username;
}