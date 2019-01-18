var app1 = new Vue({
    el: '#registerForm',
        data: {
            email: '',
            password: '',
            passwordRepeat: '',
            organiser: 'player', // so that the player is the default option for registering
            thirdParam: ''
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
                    window.open("","_self")
                    // if (response.status == 202) window.open("login.jsp","_self")

                }).catch(function (error) {
                      if (error.response) {
                      }
                    });
            }
        }
    })