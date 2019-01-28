var app1 = new Vue({
    el: '#registerForm',
        data: {
            email: '',
            password: '',
            passwordRepeat: '',
            organiser: 'player', // so that the player is the default option for registering
            thirdParam: '',
            alert: false
        },
        methods: {
            submit: function(event) {
                event.preventDefault();
                axios({
                  method: 'post',
                  url: 'login/register',
                  params: {email: this.email,
                            password: this.password,
                            organiser: this.organiser == 'organiser',
                            thirdParam: this.thirdParam
                            }
                }).then(function (response) {
                    if (app1.organiser == "player") {
                        window.location.href = "player.jsp"
                    }
                    else if (app1.organiser == "organiser") {
                        window.location.href = "organiser.jsp"
                    }
                    // if (response.status == 202) window.open("login.jsp","_self")

                }).catch(function (error) {
                      app1.alert = true;
                    });
            }
        }
    })