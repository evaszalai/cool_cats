import {dataHandler} from "./data_handler.js";

let itemCounter;

export let dom = {
    const: itemCounter = 0,
    init: function () {
        this.addEventListeners();
        this.refreshCartCounter();
    },
    addEventListeners: function () {
        for (let category of document.querySelectorAll(".categorySelector")) {
            category.addEventListener("click", dom.getProductByCategory);
        }
        for (let category of document.querySelectorAll(".supplierSelector")) {
            category.addEventListener("click", dom.getProductBySupplier);
        }
        dom.addEventListenerToAddToCartButtons();
        document.querySelector("#shoppingCart").addEventListener("click", dom.shoppingCartInit)
    },
    addEventListenerToAddToCartButtons: function () {
        for (let addToCartButton of document.querySelectorAll("[data-productid]")) {
            addToCartButton.addEventListener("click", dom.addToCart);
        }
    },
    addToCart: function (e) {
        let data = {};
        data.id = e.target.dataset.productid;
        data.action = "up";
        dataHandler._api_post("/cart", data, function (response) {
            itemCounter += 1;
            dom.refreshCartCounter();
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
        dom.addEventListenerToAddToCartButtons();
    },
    makeACard: function (product) {
        let price = Number(product.defaultPrice).toLocaleString('en');
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
                                <p class="lead enMoney">${price}</p>
                            </div>
                            <div class="card-text">
                                <a class="btn btn-success shoppingCart" href="#" data-productid="${product.id}">Add to cart</a>
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
        dataHandler._api_get("/cart", function (productsInCart) {
            dom.fillCartWithProducts(productsInCart);
            dom.checkIfCartEmpty();
        });

    },
    fillCartWithProducts(productsInCart) {
        let cartTableBody = document.querySelector(".shopping-cart-table");
        cartTableBody.innerHTML = "";
        for (let product of productsInCart) {
            cartTableBody.insertAdjacentHTML("beforeend", dom.makeCartRow(product));
        }
        for (let button of document.querySelectorAll(".qty-up")) {
            button.addEventListener("click", dom.productQuantityUp);
        }
        for (let button of document.querySelectorAll(".qty-down")) {
            button.addEventListener("click", dom.productQuantityDown);
        }
        dom.shoppingCartPriceInit();
    },
    productQuantityUp(e) {
        let data = {};
        data.id = e.target.dataset.productid;
        data.action = "up";
        dataHandler._api_post("/cart", data, function (response) {
            e.target.nextSibling.nextElementSibling.innerHTML = parseInt(e.target.nextSibling.nextElementSibling.innerHTML) + 1;
            dom.refreshQuantity();
            itemCounter += 1;
            dom.refreshCartCounter();
        })
    },
    productQuantityDown(e) {
        let data = {};
        data.id = e.target.dataset.productid;
        data.action = "down";
        dataHandler._api_post("/cart", data, function (response) {
            e.target.previousSibling.previousElementSibling.innerHTML = parseInt(e.target.previousSibling.previousElementSibling.innerHTML) - 1;
            dom.refreshQuantity();
            itemCounter -= 1;
            dom.refreshCartCounter();
        })
    },
    makeCartRow: function (product) {
        let unitPrice = product.defaultPrice.toLocaleString('en');

        return `<tr>
            <td class="w-25">
                <img class="cart-img" src="/static/img/${product.name}.jpg" class="img-fluid img-thumbnail" alt="" />
            </td>
            <td>${product.name}</td>
            <td data-unitprice class="enMoney">${unitPrice}</td>
            <td class="qty">
            <button class="btn fas fa-plus qty-up" data-productid="${product.id}"></button>
            <span class="quantity">${product.quantity}</span>
            <button class="btn fas fa-minus qty-down" data-productid="${product.id}"></button>
            </td>
            <td data-subprice></td>
        </tr>`
    },
    shoppingCartPriceInit: function () {
        dom.changeQuantityFieldValue();
        dom.displaySubPrice().then(() => dom.displayTotalPrice());
    },
    displayTotalPrice: function () {
        let totalPriceToDisplay = 0;
        let subPrices = document.querySelectorAll('[data-subprice]');
        let totalPrice = document.querySelector('.price')

        for (let subPrice of subPrices) {
            let intSubPrice = Number(subPrice.innerHTML.replace(/[^0-9.-]+/g,""));
            totalPriceToDisplay += parseInt(intSubPrice);
        }

        totalPrice.classList.add('enMoney');
        totalPrice.innerHTML = Number(totalPriceToDisplay).toLocaleString('en');
    },
    displaySubPrice: async function () {
        let shoppingCartTable = document.querySelector('.shopping-cart-table');

        for (let row of shoppingCartTable.rows) {
            let unitPrice = parseInt(row.querySelector('[data-unitprice]').innerHTML.replace(/[^0-9.-]+/g,""));
            let quantity = parseInt(row.querySelector('.quantity').innerHTML);
            let subPrice = row.querySelector('[data-subprice]');

            if (quantity <= 0) {
                row.remove();
            }

            subPrice.classList.add('enMoney');
            subPrice.innerHTML = Number(unitPrice * quantity).toLocaleString('en');
        }
    },
    changeQuantityFieldValue: function (action) {
        let fields = document.querySelectorAll('.quantity');

        for (let field of fields) {
            field.type = 'number';
            field.step = '1';
            field.addEventListener('change', dom.refreshQuantity);
        }
    },
    refreshQuantity: function () {
        dom.shoppingCartPriceInit();
        dom.checkIfCartEmpty()
    },
    checkIfCartEmpty: function () {
        let shoppingCartTable = document.querySelector('.shopping-cart-table');
        let checkoutButton = document.querySelector('#checkout');

        if (shoppingCartTable.rows.length === 0) {
            let message = '<tr> <td colspan="6" style="text-align:center;" > Your cart is empty! </td> </tr>';

            shoppingCartTable.insertAdjacentHTML('beforeend', message)
            checkoutButton.disabled = true;
        } else {
            checkoutButton.disabled = false;
        }
    },

    refreshCartCounter: function () {
        let cart = document.querySelector('#shoppingCart');

        cart.innerHTML = itemCounter > 0 ? `View cart (${itemCounter})` : 'View cart';
    }
}