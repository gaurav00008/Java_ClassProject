// =============================================
// currency.js — now talks to our Java backend!
// The UI is 100% the same as the original project.
// Only the API call has changed: instead of calling
// exchangerate-api.com directly, we call our own
// Java Spring Boot server at /api/convert
// =============================================

const dropdowns = document.querySelectorAll(".dropdown select");
const btn       = document.querySelector("button");
const fromCurr  = document.querySelector(".from select");
const toCurr    = document.querySelector(".to select");
const msg       = document.querySelector(".msg");

// Populate both dropdowns with currency options
for (let select of dropdowns) {
    for (let currCode in countryList) {
        let newOption = document.createElement("option");
        newOption.innerText = currCode;
        newOption.value     = currCode;

        if (select.name === "from" && currCode === "USD") {
            newOption.selected = true;
        } else if (select.name === "to" && currCode === "INR") {
            newOption.selected = true;
        }
        select.append(newOption);
    }

    // Update flag when user changes dropdown
    select.addEventListener("change", (evt) => {
        updateFlag(evt.target);
    });
}

// -----------------------------------------------
// KEY CHANGE: we now call our Java Spring Boot API
// instead of exchangerate-api.com directly
// -----------------------------------------------
const updateExchangeRate = async () => {
    let amountInput = document.querySelector(".amount input");
    let amtVal = parseFloat(amountInput.value);

    if (isNaN(amtVal) || amtVal <= 0) {
        amtVal = 1;
        amountInput.value = "1";
    }

    // Call our Java backend (runs on localhost:8080)
    const URL = `/api/convert?from=${fromCurr.value}&to=${toCurr.value}&amount=${amtVal}`;

    try {
        const response = await fetch(URL);

        if (!response.ok) {
            throw new Error("Could not get exchange rate from server.");
        }

        // Java sends back a JSON object like:
        // { fromCurrency, toCurrency, originalAmount, exchangeRate, convertedAmount }
        const data = await response.json();

        const formatter = new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: toCurr.value.toUpperCase(),
        });

        msg.innerText = `${amtVal} ${fromCurr.value} = ${formatter.format(data.convertedAmount)}`;

    } catch (error) {
        console.error("Error fetching rate:", error);
        msg.innerText = "Error getting exchange rate. Is the Java server running?";
    }
};

// Update the flag image when a currency is selected
const updateFlag = (element) => {
    let currCode    = element.value;
    let countryCode = countryList[currCode];
    let newSrc      = `https://flagsapi.com/${countryCode}/flat/64.png`;
    let img         = element.parentElement.querySelector("img");
    img.src         = newSrc;
};

// Button click triggers conversion
btn.addEventListener("click", (evt) => {
    evt.preventDefault();
    updateExchangeRate();
});

// Auto-convert on page load
window.addEventListener("load", () => {
    updateExchangeRate();
});
