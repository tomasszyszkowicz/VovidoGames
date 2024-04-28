var numberOfClicks = 0;
var numberOfMatches = 0;

var values = [];

var firstFlippedCard = null;

function encodeValue(value) {
    return btoa(value); // Base64 encode
}

function decodeValue(encodedValue) {
    return atob(encodedValue); // Base64 decode
}

/**
* Assigns shuffled values to cards on the board.
*/
function assignCardValues() {
    var cards = document.querySelectorAll('.card');
    var randomIndexes = generateRandomIndexes(cards.length);

    for (var i = 0; i < cards.length; i++) {
        // Encode each value before setting it
        cards[i].setAttribute('data-value', encodeValue(values[randomIndexes[i]]));
        cards[i].textContent = ""; // Optionally, remove text content if any
    }
}


/**
* Generates a shuffled array of indexes to randomly assign card values.
* @param {number} length - The length of the values array.
* @returns {Array} An array of shuffled indexes.
*/
function generateRandomIndexes(length) {
    var indexes = [];

    for (var i = 0; i < length; i++) {
        indexes.push(i);
    }

    for (var i = indexes.length - 1; i > 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        [indexes[i], indexes[j]] = [indexes[j], indexes[i]];
    }

    return indexes;
}

/**
* Flips the card and checks for a match.
*/
function flipCard() {
    var clickedCard = this;
    numberOfClicks++;
    document.getElementById("clicks").textContent = numberOfClicks;

    if (clickedCard.classList.contains('flipped')) {
        // Hide the value when flipping back
        clickedCard.textContent = '';
        clickedCard.classList.remove('flipped');
    } else {
        // Decode and show the value when flipped
        clickedCard.textContent = decodeValue(clickedCard.getAttribute('data-value'));
        clickedCard.classList.add('flipped');

        if (firstFlippedCard === null) {
            firstFlippedCard = clickedCard;
            firstFlippedCard.removeEventListener('click', flipCard);
        } else {
            if (decodeValue(firstFlippedCard.getAttribute('data-value')) === decodeValue(clickedCard.getAttribute('data-value'))) {
                console.log("Match found!");
                numberOfMatches++;
                firstFlippedCard.classList.add('matched');
                clickedCard.classList.add('matched');
                firstFlippedCard.removeEventListener('click', flipCard);
                clickedCard.removeEventListener('click', flipCard);
                setTimeout(checkEndGame, 200);
            } else {
                var cards = document.querySelectorAll('.card');
                cards.forEach(card => {
                    card.removeEventListener('click', flipCard);
                });

                setTimeout(function(card1, card2) {
                    // Hide values again when flipping back
                    card1.textContent = '';
                    card2.textContent = '';
                    card1.classList.remove('flipped');
                    card2.classList.remove('flipped');
                    cards.forEach(card => {
                        if (!card.classList.contains('matched')) {
                            card.addEventListener('click', flipCard);
                        }
                    });
                }, 1000, firstFlippedCard, clickedCard);
            }
            firstFlippedCard = null;
        }
    }
}



/**
* Checks if the game has ended.
*/
function checkEndGame() {
    if (numberOfMatches === values.length / 2) {
        const modal = document.getElementById('modal');
        const gameDetails = document.getElementById('game-details');
        const difficulty = getQueryParamater("difficulty");
        createResult();
        gameDetails.innerHTML = `
        <h1 style="margin-top: 40px;">You have solved the pexeso!</h1>
        <p>Clicks: ${numberOfClicks}</p>
        <a onclick="closeModalandRestart()">Play again</a>
        <a href="/pexeso-menu">Back to pexeso menu</a>
        <a style="margin-bottom: 40px;" href="/home">Back to main menu</a>
        `;
        modal.style.display = 'block';
        requestAnimationFrame(() => {
            requestAnimationFrame(() => {
                modal.style.top = '0'; // Start the animation
            });
        });
    }
}

/**
 * Shows start modal.
 */
function showStartModal() {
    
    const modal = document.getElementById('modal');
    const gameDetails = document.getElementById('game-details');
    gameDetails.innerHTML = `
    <h1 style="margin-top: 40px";>Welcome to pexeso!</h1>
    <p>Click on a card to flip it. Find all matching pairs to win!</p>
    <a style="margin-bottom: 40px;" onclick="closeModal()">Start</a>
    `;

    modal.style.display = 'block';
    setTimeout(() => {
        modal.style.top = '0'; // This will trigger the transition
    }, 10); // A slight delay to ensure 'display: block' is applied first

}

/**
 * Closes the modal.
 */
function closeModal() {
    startPexeso();
    const modal = document.getElementById('modal');
    modal.style.top = '-100%'; // Move the modal back off-screen
    setTimeout(() => modal.style.display = 'none', 500); // Hide after transition
}


/**
* Sets up values based on the selected difficulty.
*/
function setupValues(){
    var easyValues = ["A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F", "G", "G", "H", "H"];
    var mediumValues = ["A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F", "G", "G", "H", "H", "I", "I", "J", "J", "K", "K", "L", "L", "M", "M", "N", "N", "O", "O", "P", "P", "Q", "Q", "R", "R"];
    var hardValues = ["A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F", "G", "G", "H", "H", "I", "I", "J", "J", "K", "K", "L", "L", "M", "M", "N", "N", "O", "O", "P", "P", "Q", "Q", "R", "R", "S", "S", "T", "T", "U", "U", "V", "V", "W", "W", "X", "X", "Y", "Y", "Z", "Z", "1", "1", "2", "2", "3", "3", "4", "4", "5", "5", "6", "6"];

    const urlParams = new URLSearchParams(window.location.search);
    const difficulty = urlParams.get('difficulty');
    switch (difficulty) {
        case 'easy':
            values = easyValues;
            break;
        case 'medium':
            values = mediumValues;
            break;
        case 'hard':
            values = hardValues;
            break;
    }
}

/**
 * Starts the pexeso game.
 */
function startPexeso() {
    setupValues();
    assignCardValues();

    var cards = document.querySelectorAll('.card');
    cards.forEach(card => card.addEventListener('click', flipCard));
}

/**
 * Restarts the game.
 */
function closeModalandRestart() {
    closeModal();
    setTimeout(() => {
        location.reload();
    }, 500);
    
}