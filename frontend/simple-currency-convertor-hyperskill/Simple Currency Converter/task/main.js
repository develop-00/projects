const input = require('sync-input');

let USDToCurrencyValues = {
    USD: 1,
    JPY: 113.5,
    EUR: 0.89,
    RUB: 74.36,
    GBP: .75
};
let currencies = Object.keys(USDToCurrencyValues);

console.log(`Welcome to Currency Converter!
1 USD equals 1 USD
1 USD equals 113.5 JPY
1 USD equals 0.89 EUR
1 USD equals 74.36 RUB
1 USD equals 0.75 GBP`);



let choice;
let exitProgram = false;
while (!exitProgram) {
    console.log(`What do you want to do?`);
    console.log(`1-Convert currencies 2-Exit program`);
    choice = Number(input());
    switch (choice) {
        case 1:
            currencyConvertor();
            break;
        case 2:
            console.log(`Have a nice day!`);
            exitProgram = true;
            break;
        default:
            console.log(`Unknown input`);
            break;
    }
}

function currencyConvertor() {
    console.log(`What do you want to convert?`);
    console.log(`From: `);
    let fromCurrency = input().toUpperCase();

    if (!currencies.includes(fromCurrency)) {
        console.log("Unknown currency");
    } else {
        console.log(`To: `);
        let toCurrency = input().toUpperCase();
        if (!currencies.includes(toCurrency)) {
            console.log("Unknown currency");
        } else {
            console.log(`Amount: `);
            let amountToConvert = Number(input());
            if (Number.isNaN(amountToConvert)) {
                console.log("The amount has to be a number");
            } else if (amountToConvert < 1) {
                console.log("The amount cannot be less than 1");
            } else {
                convertCurrency(fromCurrency, toCurrency, amountToConvert);
            }
        }
    }
}

function convertCurrency(fromCurrency, toCurrency, amountToConvert) {
    let USDAmount = amountToConvert;
    if (fromCurrency !== "USD") {
        USDAmount = convertToUSD(fromCurrency, amountToConvert);
    }

    let convertedAmount = (USDAmount * USDToCurrencyValues[toCurrency]).toFixed(4);
    console.log(`Result: ${amountToConvert} ${fromCurrency} equals ${convertedAmount} ${toCurrency}`);
}

function convertToUSD(fromCurrency, amountToConvert) {
    return amountToConvert / USDToCurrencyValues[fromCurrency];
}
