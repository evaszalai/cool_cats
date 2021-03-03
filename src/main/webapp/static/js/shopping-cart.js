const shoppingCart = {

    init: function () {
        shoppingCart.displaySubPrice().then(() => shoppingCart.displayTotalPrice());
        shoppingCart.addEventListenerToQuantityField();
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

            if (quantity === '0') {
                row.remove();
            }

            subPrice.innerHTML = (unitPrice * quantity).toString() + '$';
        }
    },

    addEventListenerToQuantityField: function () {
        let fields = document.querySelectorAll('.quantity');

        for (let field of fields) {
            field.type = 'number';
            field.step = '1';
            field.addEventListener('change', shoppingCart.refreshQuantity);
        }
    },

    refreshQuantity: function () {
        shoppingCart.init();
        shoppingCart.checkIfCartEmpty()
    },

    checkIfCartEmpty: function () {
        let shoppingCartTable = document.querySelector('.shopping-cart-table');
        let checkoutButton = document.querySelector('#checkout');

        if (shoppingCartTable.rows.length === 0) {
            let message = '<tr> <td colspan="6" style="text-align:center;" > Your cart is empty! </td> </tr>';

            shoppingCartTable.insertAdjacentHTML('beforeend', message)
            checkoutButton.disabled = true;
        }
    }
}