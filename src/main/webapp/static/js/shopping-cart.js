const shoppingCart = {

    init: function () {
        this.displaySubPrice().then(() => this.displayTotalPrice());
        this.addEventListenerToQuantityField();
    },

    displayTotalPrice: function () {
        let totalPrice = 0;
        let subPrices = document.querySelectorAll('[data-subprice]');

        for (let subPrice of subPrices) {
            totalPrice += parseInt(subPrice.innerHTML);
        }

        document.querySelector('.price').innerHTML = totalPrice.toString() + '$';
    },

    displaySubPrice: async function () {
        let shoppingCartTable = document.querySelector('.shopping-cart-table');

        for (let row of shoppingCartTable.rows) {
            let unitPrice = parseInt(row.querySelector('[data-unitprice]').innerHTML);
            let quantity = row.querySelector('.quantity').value;
            let subPrice = row.querySelector('[data-subprice]');

            subPrice.innerHTML = (unitPrice * quantity).toString() + '$';
        }
    },

    addEventListenerToQuantityField: function () {
        let fields = document.querySelectorAll('.quantity');

        for (let field of fields) {
            field.addEventListener('focusout', this.refreshQuantity);
        }
    },

    refreshQuantity: function () {
        shoppingCart.init();
    }
}