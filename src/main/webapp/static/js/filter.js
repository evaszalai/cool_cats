const filter = {
    init: function () {
        this.addEventListeners();
    },
    addEventListeners: function () {
        document.querySelector("#categorySelector").addEventListener("change", filter.addActivityItem);
    },
    addActivityItem: function (e) {
        //option is selected
        fetch("/category?id=" + e.target.value)
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                filter.reLoadProducts(data)
            })
    },
    reLoadProducts: function (products) {
        let productsContainer = document.querySelector("#products");
        productsContainer.innerHTML = "";
        for (let product of products) {
            productsContainer.appendChild(filter.makeACard(product));
        }
    },
    makeACard: function (product) {
        const container = document.createElement('div');
        container.classList.add("col");
        container.classList.add("col-sm-12");
        container.classList.add("col-md-6");
        container.classList.add("col-lg-4");
        const card = document.createElement('div');
        card.classList.add('card');
        container.appendChild(card);
        let image = document.createElement('img');
        image.setAttribute("src", `/static/img/product_${product.id}.jpg`);
        card.appendChild(image);
        let head = document.createElement('div');
        head.classList.add("card-header");
        let productName = document.createElement('h4');
        productName.classList.add("card-title");
        productName.innerHTML = product.name;
        let productDescription = document.createElement('p');
        productDescription.classList.add("card-text");
        productDescription.innerHTML = product.description;
        head.appendChild(productName);
        head.appendChild(productDescription);
        let body = document.createElement('div');
        body.classList.add("card-body");
        let row = document.createElement('div');
        row.classList.add("row");
        let col1 = document.createElement('div');
        col1.classList.add("col-sm")
        let text1 = document.createElement('div');
        text1.classList.add("card-text");
        let productPrice = document.createElement('p');
        productPrice.classList.add("lead");
        productPrice.innerHTML = product.defaultPrice;
        text1.appendChild(productPrice);
        let text2 = document.createElement('div');
        text2.classList.add("card-text");
        let addToCartButton = document.createElement('a');
        addToCartButton.classList.add("btn");
        addToCartButton.classList.add("btn-success")
        addToCartButton.innerHTML = "Add to cart";
        text2.appendChild(addToCartButton);
        col1.appendChild(text1);
        col1.appendChild(text2);
        let col2 = document.createElement('div');
        col2.classList.add("col-sm")
        let text3 = document.createElement('div');
        text3.classList.add("card-text");
        let productCategory = document.createElement('p');
        productCategory.classList.add("lead");
        productCategory.innerHTML = product.productCategory.name;
        text3.appendChild(productCategory);
        let text4 = document.createElement('div');
        text4.classList.add("card-text");
        let productSupplier = document.createElement('a');
        productSupplier.classList.add("lead");
        productSupplier.innerHTML = product.supplier.name;
        text4.appendChild(productSupplier);
        col2.appendChild(text3);
        col2.appendChild(text4);
        row.appendChild(col1);
        row.appendChild(col2);
        body.appendChild(row);
        card.appendChild(head);
        card.appendChild(body);
        container.appendChild(card);
        return container;
    }
}

filter.init();