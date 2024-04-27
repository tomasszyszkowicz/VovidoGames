/**
* Sends a POST request to create a new PexesoResult.
* 
* @param {Object} result - The result object to be sent to the server.
*/
function createResult() {

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    username = document.getElementById("username").textContent;
    var difficulty = getQueryParamater("difficulty");

    if (difficulty === "easy") {
        difficulty = 1;
    } 
    else if (difficulty === "medium") {
        difficulty = 2;
    }
    else if (difficulty === "hard"){
        difficulty = 3;
    }
    else {
        difficulty = 1;
    }

    // Replace the URL with the actual endpoint
    const url = '/pexeso';
    const result = {
        score: numberOfClicks,
        username: username,
        difficulty: difficulty,
    };

    console.log(result);

    // Configure the request options
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(result),
    };

    // Use the Fetch API to send the POST request
    fetch(url, requestOptions)
        .then(response => {
            if (response.ok) {
                return response.json(); // Parse the JSON response body
            }
            throw new Error('Network response was not ok.');
        })
        .then(data => {
            console.log('Result created:', data); // Handle the response data
        })
        .catch(error => {
            console.error('There was a problem with your fetch operation:', error);
        });
}

function getResultsToLeaderboard(top) {

    var difficulty = getQueryParamater("difficulty");

    if (difficulty === "easy") {
        difficulty = 1;
    }
    else if (difficulty === "medium") {
        difficulty = 2;
    }
    else if (difficulty === "hard") {
        difficulty = 3;
    }

    var url = '/pexeso/records';
    if (top) {
        url += `?top=${top}`;
    }
    if (difficulty) {
        url += `&difficulty=${difficulty}`;
    }
    fetch(url)
        .then(response => response.json())
        .then(data => {
            const leaderboardBody = document.getElementById("leaderboardBody");
            leaderboardBody.innerHTML = ""; // Clear existing rows
            data.forEach((result, index) => {
                const row = document.createElement("tr");
                const rank = index + 1;
                if (rank === 1) {
                    row.classList.add('gold');
                } else if (rank === 2) {
                    row.classList.add('silver-color');
                } else if (rank === 3) {
                    row.classList.add('bronze');
                }
                const rankCell = document.createElement("td");
                rankCell.textContent = (index + 1);
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

function getSnakeResultsToLeaderboard(top, bottom) {
    var url = '/snake/records';
    if (top) {
        url += `?top=${top}`;
    }
    if (bottom) {
        url += `&bottom=${bottom}`;
    }
    fetch(url)
        .then(response => response.json())
        .then(data => {
            const leaderboardBody = document.getElementById("leaderboardBody");
            leaderboardBody.innerHTML = ""; // Clear existing rows
            data.forEach((result, index) => {
                const row = document.createElement("tr");
                const rank = index + 1;
                if (rank === 1) {
                    row.classList.add('gold');
                } else if (rank === 2) {
                    row.classList.add('silver-color');
                } else if (rank === 3) {
                    row.classList.add('bronze');
                }
                const rankCell = document.createElement("td");
                rankCell.textContent = (index + 1);
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

function getQueryParamater(paramater){
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(paramater);
}