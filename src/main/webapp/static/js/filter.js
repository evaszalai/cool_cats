const filter = {
    init: function () {
        this.addEventListeners();
    },
    addEventListeners: function () {
        document.querySelector("#categorySelector").addEventListener("change", filter.addActivityItem);
    },
    addActivityItem: function (e) {
        //option is selected
        fetch("/category?id="+e.target.value)
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                // main.reLoadNews(data);
            })
    }
}

filter.init();