var app1 = new Vue({
    el: '#loginForm',
    data: {
        email: '',
        password: '',
        organiser: false,
        alert: true
    },
    methods: {
        submit: function(event) {
            event.preventDefault();
            axios({
              method: 'post',
              url: 'login',
              params: {email: this.email,
                        password: this.password,
                        organiser: this.organiser}
            }).then(function (response) {
                if (response.status == 202) window.open("landing","_self")

            }).catch(function (error) {
                  if (error.response) {
                    app1.alert = false;
                  }
                });
        }
    }})