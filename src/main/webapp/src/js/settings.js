

var app = new Vue({
    el: '#inputs',
    data: {
        email: '',
        password: '',
        repeatpassword: '',
        languageOptions: ['de', 'en'],
        language: '',
        alert: false
    },
    methods: {
        setEmail: function () {
            if (this.email != '')
            axios({
                method: 'post',
                url: 'users/setEmail',
                params: {
                    email: this.email
                }
            }).then(function () { app.alert = true })
        },
        setPassword: function () {
            if (this.password != '')
                axios({
                    method: 'post',
                    url: 'users/setPassword',
                    params: {
                        password: this.password
                    }
                }).then(function () { app.alert = true })
        },
        setLanguage: function () {
            if (this.language != '')
                axios({
                    method: 'post',
                    url: 'users/setLanguage',
                    params: {
                        lang: this.language
                    }
                }).then(function () { app.alert = true })
        }
    }
})