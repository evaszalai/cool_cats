import {dataHandler} from "./data_handler.js";

export let dom = {
    init: function () {
        this.addEventListeners();
    },
    addEventListeners: function () {
        for (let category of document.querySelectorAll(".categorySelector")) {
            category.addEventListener("click", dom.getProductByCategory);
        }
        for (let category of document.querySelectorAll(".supplierSelector")) {
            category.addEventListener("click", dom.getProductBySupplier);
        }
        for (let addToCartButton of document.querySelectorAll("[data-productid]")) {
            addToCartButton.addEventListener("click", dom.addToCart);
        }
        document.querySelector("#shoppingCart").addEventListener("click", dom.shoppingCartInit)
    },
    addToCart: function (e) {
        let data = {};
        data.id = e.target.dataset.productid;
        dataHandler._api_post("/cart", data, function (response) {
            alert(response);
        })
    },
    getProductByCategory: function (e) {
        dataHandler._api_get("/category?id=" + e.target.dataset.id, function (products) {
            dom.reLoadProducts(products);
        })
    },
    getProductBySupplier: function (e) {
        dataHandler._api_get("/supplier?id=" + e.target.dataset.id, function (products) {
            dom.reLoadProducts(products);
        })
    },
    reLoadProducts: function (products) {
        let productsContainer = document.querySelector("#products");
        productsContainer.innerHTML = "";
        for (let product of products) {
            productsContainer.insertAdjacentHTML("beforeend", dom.makeACard(product));
        }
    },
    makeACard: function (product) {
        return `
            <div class="col col-sm-12 col-md-6 col-lg-4">
            <div class="card">
                <img class="" src="/static/img/${product.name}.jpg" alt="" />
                <div class="card-header">
                    <h4 class="card-title">${product.name}</h4>
                    <p class="card-text">${product.description}</p>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-sm">
                            <div class="card-text">
                                <p class="lead">${product.defaultPrice} ${product.defaultCurrency}</p>
                            </div>
                            <div class="card-text">
                                <a class="btn btn-success" href="#">Add to cart</a>
                            </div>
                        </div>
                        <div class="col-sm">
                            <div class="card-text">
                                <p class="lead">${product.productCategory.name}</p>
                            </div>
                            <div class="card-text">
                                <p class="lead">${product.supplier.name}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>`
    },
    shoppingCartInit: function () {
        dom.displaySubPrice().then(() => dom.displayTotalPrice());
        dom.addEventListenerToQuantityField();
        dataHandler._api_get("/cart", function (productsInCart) {
            console.log(productsInCart);
        })
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
            field.addEventListener('change', dom.refreshQuantity);
        }
    },
    refreshQuantity: function () {
        dom.shoppingCartInit();
        dom.checkIfCartEmpty()
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