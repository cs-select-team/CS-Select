var reset = new Vue({
    el: '#resetForm',
    data: {
        alert: false,
        missingConfig: false,
        emailSent: false,
        email: '',
        organiser: false
    },
    methods: {
        submit: function (event) {
            event.preventDefault()
            var self = this;
            self.emailSent = false;
            self.alert = false;
            axios({
                method: 'post',
                url: 'login/reset',
                params: {
                    email: this.email,
                    organiser: this.organiser
                }
            }).then(function (response) {
                self.emailSent = true;
            }).catch(function (error) {
                if (error.response.status == 550) {
                    self.missingConfig = true;
                } else {
                    self.alert = true;
                }
            })
        }
    }
})