var app1 = new Vue({
    el: '#registerForm',
        data: {
            email: '',
            password: '',
            passwordRepeat: '',
            organiser: 'player', // so that the player is the default option for registering
            thirdParam: '',
            emailInUse: false,
            missingConfig: false,
            wrongMasterPassword: false,
            missingDatabase: false,
            usernameInUse: false,
            emailInvalid: false
        },
    watch:{
        organiser: function () {
            this.thirdParam = '';
        }
    },
        methods: {
            submit: function(event) {
                event.preventDefault();
                const re = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
                if (!re.test(this.email)) {
                    app1.emailInvalid = true;
                    return;
                }
                axios({
                  method: 'post',
                  url: 'login/register',
                  params: {email: this.email,
                            password: this.password,
                            organiser: this.organiser === 'organiser',
                            thirdParam: this.thirdParam
                            }
                }).then(function (response) {
                    if (app1.organiser === "player") {
                        window.location.href = "player.jsp"
                    }
                    else if (app1.organiser === "organiser") {
                        window.location.href = "organiser.jsp"
                    }
                    // if (response.status == 202) window.open("login.jsp","_self")

                }).catch(function (error) {
                    if (error.response.status === 550) {
                        app1.missingConfig = true;
                    } else if(error.response.status === 552) {
                        app1.missingDatabase = true;
                    } else if (error.response.status === 409) {
                        app1.emailInUse = true;
                    } else if (error.response.status === 401) {
                        app1.wrongMasterPassword = true;
                    } else if (error.response.status === 450) {
                        app1.usernameInUse = true;
                    }
                })
            }
        }
    });