const app = new Vue({
    el: '#inputs',
    data: {
        email: '',
        password: '',
        repeatpassword: '',
        languageOptions: ['de', 'en'],
        language: '',
        alert: false,
        emailFalse: false
    },
    methods: {
        setEmail() {
            this.emailFalse = false;
            if (this.email !== '')
                axios({
                    method: 'post',
                    url: 'users/setEmail',
                    params: {
                        email: this.email
                    }
                }).then(function () {
                    app.alert = true;
                }).catch(function (reason) {
                    if (reason.response.status === 409) { // Conflict has happend
                        app.emailFalse = true;
                    }
                })
        },
        setPassword() {
            if (this.password !== '' && this.password === this.repeatpassword)
                axios({
                    method: 'post',
                    url: 'users/setPassword',
                    params: {
                        password: this.password
                    }
                }).then(function () {
                    app.alert = true;
                })
        },
        setLanguage() {
            if (this.language !== '')
                axios({
                    method: 'post',
                    url: 'users/setLanguage',
                    params: {
                        lang: this.language
                    }
                }).then(function () {
                    app.alert = true;
                })
        }
    }
});