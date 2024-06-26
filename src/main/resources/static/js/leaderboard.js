var global_bottom = 0;
var global_top = 19;

var global_difficulty = 1;

var globalBaseUrl;

function getResultsToLeaderboard(baseUrl, top, bottom, difficulty) {
	var url = baseUrl;
	if (top) {
		url += "?top=" + top;
	}
	if (bottom) {
		url += "&bottom=" + bottom;
	}
	if (difficulty < 4) {
		url += "&difficulty=" + difficulty;
	}

	fetch(url)
		.then((response) => response.json())
		.then((data) => {
			updateNavigationButtonVisibility();
			document.getElementById("globalBottom").textContent =
				global_bottom + 1;
			document.getElementById("globalTop").textContent = global_top + 1;
			const leaderboardBody = document.getElementById("leaderboardBody");
			leaderboardBody.innerHTML = ""; // Clear existing rows
			data.forEach((result, index) => {
				const row = document.createElement("tr");
				const rank = global_bottom + index + 1;
				if (rank === 1) {
					row.classList.add("gold");
				} else if (rank === 2) {
					row.classList.add("silver-color");
				} else if (rank === 3) {
					row.classList.add("bronze");
				}
				const rankCell = document.createElement("td");
				// Calculate rank based on global_bottom and index
				rankCell.textContent = global_bottom + index + 1;
				const playerCell = document.createElement("td");
				playerCell.textContent = result.user.username;
				const scoreCell = document.createElement("td");
				scoreCell.textContent = result.score;
				row.appendChild(rankCell);
				row.appendChild(playerCell);
				row.appendChild(scoreCell);
				leaderboardBody.appendChild(row);
			});
		});
}

function turnPageUp() {
	global_bottom += 20;
	global_top += 20;
	getResultsToLeaderboard(
		globalBaseUrl,
		global_top,
		global_bottom,
		global_difficulty
	);
}

function turnPageDown() {
	global_bottom -= 20;
	global_top -= 20;
	getResultsToLeaderboard(
		globalBaseUrl,
		global_top,
		global_bottom,
		global_difficulty
	);
}

function updateNavigationButtonVisibility() {
	const turnPageDownButton = document.getElementById("turnPageDownButton");
	if (global_bottom === 0) {
		turnPageDownButton.style.visibility = "hidden";
	} else {
		turnPageDownButton.style.visibility = "visible";
	}
}

function sumbitForm() {
	global_bottom = 0;
	global_top = 19;
	const difficulty = document.getElementById("difficulty").value;

	// Retrieve the checked radio button in the group 'recordResults'
	const selectedOption = document.querySelector(
		'input[name="recordResults"]:checked'
	).value;

	var endpoint;

	if (selectedOption === "records") {
		endpoint = "records";
	} else if (selectedOption === "results") {
		endpoint = "results";
	}

	if (difficulty === "1") {
		document.getElementById("difficultyText").textContent =
			"Easy pexeso " + endpoint;
		globalBaseUrl = "/pexeso/" + endpoint;
	} else if (difficulty === "2") {
		document.getElementById("difficultyText").textContent =
			"Medium pexeso " + endpoint;
		globalBaseUrl = "/pexeso/" + endpoint;
	} else if (difficulty === "3") {
		document.getElementById("difficultyText").textContent =
			"Hard pexeso " + endpoint;
		globalBaseUrl = "/pexeso/" + endpoint;
	} else if (difficulty === "4") {
		document.getElementById("difficultyText").textContent =
			"Snake " + endpoint;
		globalBaseUrl = "/snake/" + endpoint;
	} else if (difficulty === "5") {
		document.getElementById("difficultyText").textContent =
			"Jump jump " + endpoint;
		globalBaseUrl = "/jump/" + endpoint;
	}
	global_difficulty = difficulty;
	getResultsToLeaderboard(globalBaseUrl, 19, 0, global_difficulty);
}
