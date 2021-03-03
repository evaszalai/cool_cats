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
    },
    getProductByCategory: function (e) {
        dataHandler._api_get("/category?id=" + e.target.dataset.id, function (products) {
            dom.reLoadProducts(products);
        })
    },
    getProductBySupplier(e) {
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
                <img class="" src="/static/img/product_${product.id}.jpg" alt="" />
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
    }
}