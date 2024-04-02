var numberOfClicks = 0;
var numberOfMatches = 0;

var values = [];

var firstFlippedCard = null;

/**
* Assigns shuffled values to cards on the board.
*/
function assignCardValues() {
    var cards = document.querySelectorAll('.card');
    var randomIndexes = generateRandomIndexes(cards.length);

    for (var i = 0; i < cards.length; i++) {
        cards[i].textContent = values[randomIndexes[i]];
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
        clickedCard.classList.remove('flipped');
    } else {
        clickedCard.classList.add('flipped');

        if (firstFlippedCard === null) {
            firstFlippedCard = clickedCard;
            firstFlippedCard.removeEventListener('click', flipCard);
        } else {
            if (firstFlippedCard.textContent === clickedCard.textContent) {
                console.log("Match found!");
                numberOfMatches++;
                document.getElementById("matches").textContent = numberOfMatches;
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
                    card1.classList.remove('flipped');
                    card2.classList.remove('flipped');
                    cards.forEach(card => {
                        if (!card.classList.contains('flipped')) {
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
        alert("Congratulations! You have completed the game with " + numberOfClicks + " clicks.");
        const modal = document.getElementById('modal');
        const gameDetails = document.getElementById('game-details');
        gameDetails.innerHTML = `
            <h1>You have solved the pexeso!</h1>
            <p>Clicks: </p>
        `;
        modal.style.display = 'block';
        createResult();
    }
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