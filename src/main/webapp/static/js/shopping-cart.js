function displayTotalPrice() {
    let totalPrice = 0;
    let subPrices = document.querySelectorAll('[data-subprice]');

    for (let subPrice of subPrices) {
        totalPrice += parseInt(subPrice.innerHTML);
    }

    document.querySelector('.price').innerHTML = totalPrice.toString() + '$';
}