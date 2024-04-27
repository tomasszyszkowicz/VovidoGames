const gameArea = document.getElementById('gameArea');
let snake = [{ x: 20, y: 20 }]; // Initial position of the snake
let dx = 20; // Horizontal movement speed
let dy = 0; // Vertical movement speed
const gameWidth = 400; // Width of the game area
const gameHeight = 400; // Height of the game area
let food = randomFoodPosition(); // Initial food position
let score = 0; // Initial score
let gameLoopInterval;

/**
 * Draws the snake by creating and positioning segments in the game area.
 * Clears old segments first to prevent duplication. Each segment's position
 * is determined by the snake array's x and y values.
 */
function drawSnake() {
    document.querySelectorAll('.snake').forEach(el => el.remove());
    snake.forEach(segment => {
        const snakeElement = document.createElement('div');
        snakeElement.style.left = `${segment.x}px`;
        snakeElement.style.top = `${segment.y}px`;
        snakeElement.classList.add('snake');
        gameArea.appendChild(snakeElement);
    });
}

/**
 * Moves the snake by adding a new head segment in the direction of movement
 * and removing the tail segment. If the snake eats the food, the tail is not
 * removed and the food position is updated.
 */
function moveSnake() {
    const nextHead = { x: snake[0].x + dx, y: snake[0].y + dy };
    console.log(`Next head position: x=${nextHead.x}, y=${nextHead.y}`);
    console.log(`Food position: x=${food.x}, y=${food.y}`);

    if (isGameOver(nextHead)) {
        showEndModal();
        clearInterval(gameLoopInterval);
        return;
    }

    if (nextHead.x === food.x && nextHead.y === food.y) {
        console.log('Eating food');
        score += 1;
        updateScore();
        food = randomFoodPosition();
    } else {
        snake.pop(); // Remove the tail segment unless the food is eaten
    }

    snake.unshift(nextHead); // Add the new head to the front of the snake array
}

/**
 * Changes the direction of the snake based on the key pressed. The snake cannot
 * reverse its direction to prevent moving into itself. The direction is changed
 * by modifying the dx and dy values which are used to calculate the next head position.
 * @param {KeyboardEvent} event - The keydown event object.
 * @returns {void}
 */
function changeDirection(event) {
    const keyPressed = event.keyCode;
    const goingUp = dy === -20;
    const goingDown = dy === 20;
    const goingRight = dx === 20;
    const goingLeft = dx === -20;

    if ((keyPressed === 37 || keyPressed === 65) && !goingRight) { // Left arrow or 'A'
        dx = -20;
        dy = 0;
    }
    if ((keyPressed === 39 || keyPressed === 68) && !goingLeft) { // Right arrow or 'D'
        dx = 20;
        dy = 0;
    }
    if ((keyPressed === 38 || keyPressed === 87) && !goingDown) { // Up arrow or 'W'
        dx = 0;
        dy = -20;
    }
    if ((keyPressed === 40 || keyPressed === 83) && !goingUp) { // Down arrow or 'S'
        dx = 0;
        dy = 20;
    }
}

/**
 * Checks if the game is over by determining if the next head position is outside
 * the game area or colliding with the snake itself.
 * @param {Object} nextHead - The next head position of the snake.
 * @returns {boolean} True if the game is over, false otherwise.
 */
function isGameOver(nextHead) {
    const hitWall = nextHead.x < 0 || nextHead.x >= gameWidth || nextHead.y < 0 || nextHead.y >= gameHeight;
    const hitSelf = snake.some(segment => segment.x === nextHead.x && segment.y === nextHead.y);
    return hitWall || hitSelf;
}

/**
 * Generates a random position for the food that does not overlap with the snake.
 * @returns {Object} A random position object with x and y values.
 */
function randomFoodPosition() {
    let position;
    do {
        position = {
            x: Math.floor(Math.random() * (gameWidth / 20)) * 20,
            y: Math.floor(Math.random() * (gameHeight / 20)) * 20,
        };
    } while (snake.some(segment => segment.x === position.x && segment.y === position.y));
    return position;
}

/**
 * Draws the food by creating and positioning a food element in the game area.
 * Clears old food elements first to prevent duplication.
 */
function drawFood() {
    document.querySelectorAll('.food').forEach(el => el.remove());
    const foodElement = document.createElement('div');
    foodElement.style.left = `${food.x}px`;
    foodElement.style.top = `${food.y}px`;
    foodElement.classList.add('food');
    gameArea.appendChild(foodElement);
}

/**
 * Updates the score element with the current score.
 */
function updateScore() {
    const scoreElement = document.getElementById('score');
    scoreElement.innerHTML = `Score: <b>${score}</b>`;
}

/**
 * The main game loop that moves the snake and draws the game elements.
 */
function gameLoop() {
    moveSnake();
    drawSnake();
    drawFood();
}

/**
 * Initializes the game by adding event listeners and starting the game loop.
 */
function showStartModal() {
    
    const modal = document.getElementById('modal');
    const gameDetails = document.getElementById('game-details');
    gameDetails.innerHTML = `
    <h1 style="margin-top: 40px;">Welcome to Snake!</h1>
    <p>Eat food to get bigger and try to survive as long as possible!</p>
    <a style="margin-bottom: 40px;" onclick="closeModal()">Start</a>
    `;

    modal.style.display = 'block';
    setTimeout(() => {
        modal.style.top = '0';
    }, 10);

}

/**
 * Closes the modal and starts the game.
 */
function closeModal() {
    const modal = document.getElementById('modal');
    modal.style.top = '-100%';
    setTimeout(() => {
        modal.style.display = 'none';
    }, 500);

    document.addEventListener('keydown', changeDirection);
    gameLoopInterval = setInterval(gameLoop, 170);
}

/**
 * Shows the end modal with the final score and options to play again or return to the main menu.
 */
function showEndModal() {
    createSnakeResult();
    const modal = document.getElementById('modal');
    const gameDetails = document.getElementById('game-details');
    gameDetails.innerHTML = `
    <h1 style="margin-top: 40px;">Game Over!</h1>
    <p>Your score: ${score}</p>
    <a onclick="closeModalAndRefresh()">Play again</a>
    <a style="margin-bottom: 40px" href="/home">Back to main menu</a>
    `;
    modal.style.display = 'block';
    setTimeout(() => {
        modal.style.top = '0';
    }, 10);
}

/**
 * Closes the modal and refreshes the page to start a new game.
 */
function closeModalAndRefresh() {
    const modal = document.getElementById('modal');
    modal.style.top = '-100%';
    setTimeout(() => {
        modal.style.display = 'none';
        location.reload();
    }, 500);
}

/**
 * Creates a new snake result by sending a POST request to the server.
 */
function createSnakeResult() {

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    username = document.getElementById("username").textContent;

    const url = '/snake';
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