// Use "input()" to input a line from the user
// Use "input(str)" to print some text before requesting input
// You will need this in the following stages
const input = require('sync-input')
let availableWater = 400;
let availableMilk = 540;
let availableBeans = 120;
let availableCups = 9;
let balance = 550;
coffeeMachine();

function coffeeMachine() {
    let response = -1;
    do {
        response = askForAction();
    } while (response !== "exit");
}

function askForAction() {
    let request = input(`Write action (buy, fill, take, remaining, exit): `).toLowerCase();
    switch (request) {
        case "buy":
            let coffeeType = askForCoffee();
            if (coffeeType === "main menu") {
                return;
            }
            break;
        case "fill":
            refillMachine();
            break;
        case "take":
            withdrawMoney();
            break;
        case "remaining":
            showMachineStatus();
            break;
        case "exit":
            return "exit";
        default:
            break;
    }
}


function showMachineStatus() {
    console.log(`The coffee machine has:
${availableWater} ml of water
${availableMilk} ml of milk
${availableBeans} g of coffee beans
${availableCups} disposable cups
$${balance} of money`);
}

function refillMachine() {
    availableWater += Number(input(`Write how many ml of water you want to add:`));
    availableMilk += Number(input(`Write how many ml of milk you want to add:`));
    availableBeans += Number(input(`Write how many grams of coffee beans you want to add:`));
    availableCups += Number(input(`Write how many disposable coffee cups you want to add:`));
}

function withdrawMoney() {
    console.log(`I gave you $${balance}`);
    balance = 0;
}

function askForCoffee() {
    let coffeeChoice = input(`What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, 4 - mocha, back - to main menu: `);
    if (coffeeChoice === "back") {
        return "main menu";
    }
    coffeeChoice = Number(coffeeChoice);
    switch (coffeeChoice) {
        case 1:
            buyEspresso();
            break;
        case 2:
            buyLatte();
            break;
        case 3:
            buyCappuccino();
            break;
        case 4:
            buyMocha();
            break;
        default:
            break;
    }
}

function buyEspresso() {
    if (availableWater < 250) {
        console.log(`Sorry, not enough water!`);
        return;
    } else {
        availableWater -= 250;
    }
    if (availableBeans < 16) {
        console.log(`Sorry, not enough beans!`);
        return;
    } else {
        availableBeans -= 16;
    }
    if (availableCups < 1) {
        console.log(`Sorry, not enough cups!`);
        return;
    } else {
        availableCups -= 1;
    }
    console.log(`I have enough resources, making you a coffee!`);
    balance += 4;
}

function buyLatte() {
    if (availableWater < 350) {
        console.log(`Sorry, not enough water!`);
        return;
    } else {
        availableWater -= 350;
    }
    if (availableMilk < 75) {
        console.log("Sorry, not enough milk!");
        return;
    } else {
        availableMilk -= 75;
    }
    if (availableBeans < 20) {
        console.log(`Sorry, not enough beans!`);
        return;
    } else {
        availableBeans -= 20;
    }
    if (availableCups < 1) {
        console.log(`Sorry, not enough cups!`);
        return;
    } else {
        availableCups -= 1;
    }
    console.log(`I have enough resources, making you a coffee!`);
    balance += 7;
}

function buyCappuccino() {
    if (availableWater < 200) {
        console.log(`Sorry, not enough water!`);
        return;
    } else {
        availableWater -= 200;
    }
    if (availableMilk < 100) {
        console.log("Sorry, not enough milk!");
        return;
    } else {
        availableMilk -= 100;
    }
    if (availableBeans < 12) {
        console.log(`Sorry, not enough beans!`);
        return;
    } else {
        availableBeans -= 12;
    }
    if (availableCups < 1) {
        console.log(`Sorry, not enough cups!`);
        return;
    } else {
        availableCups -= 1;
    }
    console.log(`I have enough resources, making you a coffee!`);
    balance += 6;
}

function buyMocha() {
    if (availableWater < 150) {
        console.log(`Sorry, not enough water!`);
        return;
    } else {
        availableWater -= 150;
    }
    if (availableMilk < 350) {
        console.log("Sorry, not enough milk!");
        return;
    } else {
        availableMilk -= 350;
    }
    if (availableBeans < 15) {
        console.log(`Sorry, not enough beans!`);
        return;
    } else {
        availableBeans -= 15;
    }
    if (availableCups < 1) {
        console.log(`Sorry, not enough cups!`);
        return;
    } else {
        availableCups -= 1;
    }
    console.log(`I have enough resources, making you a coffee!`);
    balance += 9;
}
