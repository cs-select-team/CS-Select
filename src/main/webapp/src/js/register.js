var app1 = new Vue({
    el: '#registerForm',
        data: {
            email: '',
            organiser: 'player', // so that the player is the default option for registering
            thirdParam: '',
            emailInUse: false,
            missingConfig: false,
            wrongMasterPassword: false,
            missingDatabase: false
        },
    watch:{
        organiser: function () {
            this.thirdParam = '';
        }
    },
        methods: {
            submit: function(event) {
                event.preventDefault();
                axios({
                  method: 'post',
                  url: 'login/register',
                  params: {email: this.email,
                            organiser: this.organiser === 'organiser',
                            thirdParam: this.thirdParam
                            }
                }).then(function () {
                    window.open("index.jsp","_self")
                }).catch(function (error) {
                    if (error.response.status === 550) {
                        app1.missingConfig = true;
                    } else if(error.response.status === 552) {
                        app1.missingDatabase = true;
                    } else if (error.response.status === 409) {
                        app1.emailInUse = true;
                    } else if (error.response.status === 401) {
                        app1.wrongMasterPassword = true;
                    }
                })
            }
        }
    });