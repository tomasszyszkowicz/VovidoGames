const gameArea = document.getElementById('gameArea');
let snake = [{ x: 20, y: 20 }]; // Initial position of the snake
let dx = 20; // Horizontal movement speed
let dy = 0; // Vertical movement speed
const gameWidth = 400; // Width of the game area
const gameHeight = 400; // Height of the game area
let food = { x: 60, y: 20 }; // Initial food position

function drawSnake() {
    // Clear previous snake parts
    document.querySelectorAll('.snake').forEach(el => el.remove());
    // Draw new snake parts
    snake.forEach(segment => {
        const snakeElement = document.createElement('div');
        snakeElement.style.left = `${segment.x}px`;
        snakeElement.style.top = `${segment.y}px`;
        snakeElement.classList.add('snake');
        gameArea.appendChild(snakeElement);
    });
}

function moveSnake() {
    // Calculate next head position
    const nextHead = { x: snake[0].x + dx, y: snake[0].y + dy };
    // Check for game over condition before the snake moves
    if (isGameOver(nextHead)) {
        alert('Game Over!');
        clearInterval(gameLoopInterval); // Stop the game loop
        return; // Prevent further execution
    }
    // Move the snake by adding the new head and removing the last segment
    snake.unshift(nextHead);
    snake.pop();
}

function changeDirection(event) {
    const keyPressed = event.keyCode;
    const goingUp = dy === -20;
    const goingDown = dy === 20;
    const goingRight = dx === 20;
    const goingLeft = dx === -20;

    if (keyPressed === 37 && !goingRight) {
        dx = -20;
        dy = 0;
    }
    if (keyPressed === 39 && !goingLeft) {
        dx = 20;
        dy = 0;
    }
    if (keyPressed === 38 && !goingDown) {
        dx = 0;
        dy = -20;
    }
    if (keyPressed === 40 && !goingUp) {
        dx = 0;
        dy = 20;
    }
}

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

function drawFood() {
    const foodElement = document.createElement('div');
    foodElement.style.left = `${food.x}px`;
    foodElement.style.top = `${food.y}px`;
    foodElement.classList.add('food');
    gameArea.appendChild(foodElement);
}

function isGameOver(nextHead) {
    // Check if next head position collides with game boundaries
    const hitLeftWall = nextHead.x < 0;
    const hitRightWall = nextHead.x >= gameWidth;
    const hitTopWall = nextHead.y < 0;
    const hitBottomWall = nextHead.y >= gameHeight;
    return hitLeftWall || hitRightWall || hitTopWall || hitBottomWall;
}

function gameLoop() {
    moveSnake();
    drawSnake();
}

document.addEventListener('keydown', changeDirection);

// Start the game loop
const gameLoopInterval = setInterval(gameLoop, 170);
