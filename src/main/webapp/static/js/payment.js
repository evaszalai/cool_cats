const payment = {

    init: function () {
        this.addEventListener();
    },

    addEventListener: function () {
        let selectButton = document.querySelector('#CreditCardType');

        selectButton.addEventListener('change', payment.changePaymentCredentials);
    },

    changePaymentCredentials: function () {
        let paymentMethod = document.querySelector('#CreditCardType').value;
        let paymentCredentials = document.querySelector('#payment-credentials');

        while (paymentCredentials.firstChild) {
            paymentCredentials.removeChild(paymentCredentials.firstChild);
        }

        if (paymentMethod === 'credit-card') {
            payment.addCreditCardCredentials(paymentCredentials);
        } else {
            payment.addPayPalCredentials(paymentCredentials);
        }
    },

    addCreditCardCredentials: function (paymentCredentials) {
        let creditCardCredentials =
            '<div class="form-group"">\n' +
            '                <div class="col-md-12"><strong>Credit Card Number:</strong></div>\n' +
            '                <div class="col-md-12"><input type="text" style="width: 50%" class="form-control" name="credit_card_number" value=""/></div>\n' +
            '            </div>\n' +
            '            <div class="form-group">\n' +
            '                <div class="col-md-12"><strong>Card CVV:</strong></div>\n' +
            '                <div class="col-md-12"><input type="text" class="form-control" name="credit_card_code" value=""/></div>\n' +
            '            </div>\n' +
            '            <div class="form-group">\n' +
            '                <div class="col-md-12">\n' +
            '                    <strong>Expiration Date</strong>\n' +
            '                </div>\n' +
            '                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">\n' +
            '                    <select class="form-control" name="">\n' +
            '                        <option value="">Month</option>\n' +
            '                        <option value="01">01</option>\n' +
            '                        <option value="02">02</option>\n' +
            '                        <option value="03">03</option>\n' +
            '                        <option value="04">04</option>\n' +
            '                        <option value="05">05</option>\n' +
            '                        <option value="06">06</option>\n' +
            '                        <option value="07">07</option>\n' +
            '                        <option value="08">08</option>\n' +
            '                        <option value="09">09</option>\n' +
            '                        <option value="10">10</option>\n' +
            '                        <option value="11">11</option>\n' +
            '                        <option value="12">12</option>\n' +
            '                    </select>\n' +
            '                </div>\n' +
            '                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">\n' +
            '                    <select class="form-control" name="">\n' +
            '                        <option value="">Year</option>\n' +
            '                        <option value="2021">2021</option>\n' +
            '                        <option value="2022">2022</option>\n' +
            '                        <option value="2023">2023</option>\n' +
            '                        <option value="2024">2024</option>\n' +
            '                        <option value="2025">2025</option>\n' +
            '                        <option value="2026">2026</option>\n' +
            '                        <option value="2027">2027</option>\n' +
            '                        <option value="2028">2028</option>\n' +
            '                        <option value="2029">2029</option>\n' +
            '                        <option value="2030">2030</option>\n' +
            '                    </select>\n' +
            '                </div>\n' +
            '            </div>'

        paymentCredentials.insertAdjacentHTML('beforeend', creditCardCredentials);
    },

    addPayPalCredentials: function (paymentCredentials) {
        let payPalCredentials =
            '<div class="form-group">\n' +
            '                <div class="col-md-12"><strong>Username:</strong></div>\n' +
            '                <div class="col-md-12"><input type="text" class="form-control" name="username" value=""/></div>\n' +
            '            </div>\n' +
            '            <div class="form-group">\n' +
            '                <div class="col-md-12"><strong>Password:</strong></div>\n' +
            '                <div class="col-md-12"><input type="password" class="form-control" name="password" value=""/></div>\n' +
            '</div>'

        paymentCredentials.insertAdjacentHTML('beforeend', payPalCredentials);
    }
}

payment.init();