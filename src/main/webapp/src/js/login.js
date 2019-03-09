var app1 = new Vue({
    el: '#loginForm',
    data: {
        email: '',
        password: '',
        organiser: false,
        alert: true,
        missingConfig: false,
        missingDatabase: false
    },
    computed: {

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
                if (response.status == 202) {
                    if (app1.organiser) window.open("organiser.jsp","_self");
                    else window.open("player.jsp", "_self")

                }

            }).catch(function (error) {
                if (error.response.status == 550) {
                    app1.missingConfig = true;
                } else if (error.response.status == 552) {
                    app1.missingDatabase = true;
                }
                 else if (error.response) {
                    app1.alert = false;
                  }
                });
        },
        autoLogout: function() {
            var val = localStorage.getItem('autoLogout');
            localStorage.setItem("autoLogout", false);
            return val;
        },
        checkEmail: function() {
            var val = localStorage.getItem('checkEmail');
            localStorage.setItem('checkEmail', false);
            return val;
        }
    }
    });