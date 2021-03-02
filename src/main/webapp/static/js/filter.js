const filter = {
    init: function () {
        this.addEventListeners();
    },
    addEventListeners: function () {
        // document.querySelector("#categorySelector").addEventListener("change", filter.addActivityItem);
        for (let category of document.querySelectorAll(".categorySelector")) {
            category.addEventListener("click", filter.getProductByCategory);
        }
        for (let category of document.querySelectorAll(".supplierSelector")) {
            category.addEventListener("click", filter.getProductBySupplier);
        }
    },
    getProductByCategory: function (e) {
        fetch("/category?id=" + e.target.dataset.id)
            .then((response) => response.json())
            .then((data) => {
                filter.reLoadProducts(data)
            })
    },
    getProductBySupplier(e) {
        fetch("/supplier?id=" + e.target.dataset.id)
            .then((response) => response.json())
            .then((data) => {
                filter.reLoadProducts(data)
            })
    },
    reLoadProducts: function (products) {
        let productsContainer = document.querySelector("#products");
        productsContainer.innerHTML = "";
        for (let product of products) {
            productsContainer.insertAdjacentHTML("beforeend", filter.makeACard(product));
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
            </div>
        `
    }
}

filter.init();