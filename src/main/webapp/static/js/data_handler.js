export let dataHandler = {
    _data: {},
    init: function () {},
    _api_get: function (url, callback) {
        fetch(url, {
            method: 'GET',
            credentials: 'same-origin'
        })
            .then(response => response.json())  // parse the response as JSON
            .then(json_response => callback(json_response));  // Call the `callback` with the returned object
    },
    _api_post: function (url, data, callback) {
        fetch(url, {
            method: 'POST',
            credentials: 'include',
            body: JSON.stringify(data),
            headers: new Headers({
                'Content-type': 'application/json; charset=UTF-8'
            })
        })
            .then(response => response.json())
            .then(json_response => callback(json_response))
            .catch((error) => {
                console.log("Fetch error: " + error);
            });
    },
    _api_delete: function (url, callback) {
        fetch(url, {
            method: 'DELETE'
        })
            .then(response => callback(response))
    }
}