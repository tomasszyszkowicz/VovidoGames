let playerPosition = { x: 20, y: 180, velocityY: 0, gravity: 0.5, jumpPower: -10, onGround: true };
let obstaclePosition = { x: 380, y: 180 };
let speed = 2;

function setupGame() {
    let gameArea = document.getElementById("gameArea");
    gameArea.innerHTML = "<div class='player-cube' style='left: " + playerPosition.x + "px; top: " + playerPosition.y + "px;'></div>" +
                         "<div class='obstacle' style='left: " + obstaclePosition.x + "px; top: " + obstaclePosition.y + "px;'></div>";
}

function drawPlayer() {
    let player = document.querySelector('.player-cube');
    player.style.left = playerPosition.x + 'px';
    player.style.top = playerPosition.y + 'px';
}

function updateObstacle() {
    let obstacle = document.querySelector('.obstacle');
    if (obstaclePosition.x <= -20) {
        let random = Math.floor(Math.random() * 3) + 1;
        obstaclePosition.x = 380;
        if (random === 1) {
            obstaclePosition.y = 180;
        }
        if (random === 2) {
            obstaclePosition.y = 150; 
        }
        if (random === 3) {
            obstaclePosition.y = 130;
        }
    } else {
        obstaclePosition.x -= speed;
    }
    obstacle.style.left = obstaclePosition.x + 'px';
    obstacle.style.top = obstaclePosition.y + 'px';
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
    if (playerPosition.y > 180) {
        playerPosition.y = 180;
        playerPosition.velocityY = 0;
        playerPosition.onGround = true;
    }
}

function updateGame() {
    applyGravity();
    updateObstacle();
    drawPlayer();
}

window.addEventListener('keydown', function(e) {
    if (e.code === 'Space' && playerPosition.onGround) {
        handleJump();
    }
});

window.onload = function() {
    setupGame();
    setInterval(updateGame, 16);  // Update game approximately every 16 ms (~60 updates per second)
};