let playerPosition = {
	x: 50,
	y: 169,
	velocityY: 0,
	gravity: 0.5,
	jumpPower: -10,
	onGround: true,
};
let obstaclePosition = { x: 380, y: 180 };
let speed = 2.5;
let speedIncreaseInterval = 10000; // Increase speed every 10 seconds
let lastIncreaseTime = Date.now(); // Initialize the last increase time
let gameInterval;
let score = 0;
let pfp;

function setupGame() {
	let gameArea = document.getElementById("gameArea");
	gameArea.innerHTML =
		"<img src='" + pfp +
         "' class='player-cube' style='left: " +
		playerPosition.x +
		"px; top: " +
		playerPosition.y +
		"px;'>" +
		"<div class='obstacle' style='left: " +
		obstaclePosition.x +
		"px; top: " +
		obstaclePosition.y +
		"px;'></div>";
}

function drawPlayer() {
	let player = document.querySelector(".player-cube");
	player.style.left = playerPosition.x + "px";
	player.style.top = playerPosition.y + "px";
}

function updateObstacle() {
	let obstacle = document.querySelector(".obstacle");
	if (obstaclePosition.x <= 5) {
		let random = Math.floor(Math.random() * 4) + 1;
		updateScore();
		obstaclePosition.x = 380;
		if (random === 1) {
			obstaclePosition.y = 180;
		} else if (random === 2) {
			obstaclePosition.y = 160;
		} else if (random === 3) {
            obstaclePosition.y = 140;
        }
        else {
			obstaclePosition.y = 110;
		}
	} else {
		obstaclePosition.x -= speed;
	}
	obstacle.style.left = obstaclePosition.x + "px";
	obstacle.style.top = obstaclePosition.y + "px";
}

function handleJump() {
	if (playerPosition.onGround) {
		playerPosition.velocityY = playerPosition.jumpPower;
		playerPosition.onGround = false;
	}
}

function applyGravity() {
	playerPosition.velocityY += playerPosition.gravity;
	playerPosition.y += playerPosition.velocityY;
	if (playerPosition.y > 169) {
		playerPosition.y = 169;
		playerPosition.velocityY = 0;
		playerPosition.onGround = true;
	}
}

function checkCollision() {
	// Reduce the effective hitbox size by adding a margin to the position checks
	let playerHitboxMargin = 1; // Reduce player hitbox by 5 pixels on all sides
	let obstacleHitboxMargin = 1; // Reduce obstacle hitbox by 5 pixels on all sides

	if (
		playerPosition.x + playerHitboxMargin < obstaclePosition.x + 20 - obstacleHitboxMargin &&
        playerPosition.x + 30 - playerHitboxMargin > obstaclePosition.x + obstacleHitboxMargin &&
        playerPosition.y + playerHitboxMargin < obstaclePosition.y + 20 - obstacleHitboxMargin &&
        playerPosition.y + 30 - playerHitboxMargin > obstaclePosition.y + obstacleHitboxMargin
	) {
		clearInterval(gameInterval);
		resetGame();
	}
}

function updateScore() {
	score++;
	const scoreElement = document.getElementById("score");
	scoreElement.innerHTML = `Score: <b>${score}</b>`;
}

function resetGame() {
	playerPosition.x = 20;
	playerPosition.y = 180;
	playerPosition.velocityY = 0;
	obstaclePosition.x = 380;
	obstaclePosition.y = 169;
	playerPosition.onGround = true;
	speed = 2; // Reset speed to initial value
	showEndModal();
}

function increaseSpeed() {
	let currentTime = Date.now();
	if (currentTime - lastIncreaseTime > speedIncreaseInterval) {
		speed += 0.5;
		lastIncreaseTime = currentTime;
		if (speed > 9) {
			speedIncreaseInterval = 30000;
		}
	}
}

function updateGame() {
	applyGravity();
	updateObstacle();
	drawPlayer();
	checkCollision();
	increaseSpeed();
}

window.addEventListener("keydown", function (e) {
	if (e.code === "Space" && playerPosition.onGround) {
		handleJump();
	}
});

function fetchUser() {
	const username = document.getElementById("username").innerText;
	const url = "/users/" + username;

	fetch(url)
		.then((response) => response.json())
		.then((data) => {
            pfp = data.profilePictureURL;   
		});
}

/**
 * Shows the end modal with the final score and options to play again or return to the main menu.
 */
function showEndModal() {
    createJumpResult();
	const modal = document.getElementById("modal");
	const gameDetails = document.getElementById("game-details");
	gameDetails.innerHTML = `
    <h1 style="margin-top: 40px;">Game Over!</h1>
    <p>Your score: ${score}</p>
    <a href="/home">Back to main menu</a>
    <a style="margin-bottom: 40px" onclick="closeModalAndRefresh()">Play again</a>
    `;
	modal.style.display = "block";
	requestAnimationFrame(() => {
		requestAnimationFrame(() => {
			modal.style.top = "0"; // Start the animation
		});
	});
}

/**
 * Initializes the game by adding event listeners and starting the game loop.
 */
function showStartModal() {
	const modal = document.getElementById("modal");
	const gameDetails = document.getElementById("game-details");
	gameDetails.innerHTML = `
    <h1 style="margin-top: 40px;">Welcome to Jump Jump!</h1>
    <p>Jump with spacebar to avoid the obstacles!</p>
    <a style="margin-bottom: 40px;" onclick="closeModal()">Start</a>
    `;

	modal.style.display = "block";
	setTimeout(() => {
		modal.style.top = "0";
	}, 10);
}

/**
 * Closes the modal and starts the game.
 */
function closeModal() {
	const modal = document.getElementById("modal");
	modal.style.top = "-100%";
	setTimeout(() => {
		modal.style.display = "none";
		setupGame();
		gameInterval = setInterval(updateGame, 16);
	}, 500);
}

/**
 * Closes the modal and refreshes the page to restart the game.
 */
function closeModalAndRefresh() {
	const modal = document.getElementById("modal");
	modal.style.top = "-100%";
	setTimeout(() => {
		modal.style.display = "none";
		window.location.reload();
	}, 500);
}

/**
 * Creates a new jump jump result by sending a POST request to the server.
 */
function createJumpResult() {

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    username = document.getElementById("username").textContent;

    const url = '/jump';
    const result = {
        score: score,
        username: username
    };
    console.log(result);
    
    const requestOptions = {
        method: 'POST',
        headers: { 
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(result)
    };

    fetch(url, requestOptions)
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Network response was not ok.');
        })
        .then(data => {
            console.log('Result created:', data);
        })
        .catch(error => {
            console.error('There was a problem with your fetch operation:', error);
        });
}
