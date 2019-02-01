Vue.component('invite', {
    props: [''],
    template: '<div>' +
        '           <input type="text" id="emailToAdd" :placeholder="localisation.emailGC"/>' +
        '           <input type="button" class="btn btn-primary" ' +
        '               v-on:click="invite()" :value="localisation.invite"/>' +
        '      </div>',
    methods: {
        invite: function() {
            creation.addPlayer(document.getElementById("emailToAdd").value.toString());
            document.getElementById("emailToAdd").value = '';
            alert("Successfully added Email")
        }
    }
});

Vue.component('pat', {
    props: [''],
    template: '<input type="button" class="btn btn-primary" :value="localisation.loadPattern" ' +
        'v-on:click="loadPattern()">',
    methods: {
        loadPattern: function() {
            creation.loadPattern();
        }
    }
});

Vue.component('control', {
    props: [''],
    template: '<div class="container">' +
        '           <input type="button" class="btn btn-primary float-right btn-space" v-on:click="abort()"' +
        ':value="localisation.abort">' +
        '           <input type="button" class="btn btn-primary float-right btn-space" v-on:click="create()"' +
        ':value="localisation.create">' +
        '       </div>',
    methods: {
        abort : function() {
            creation.emptyStore();
        },
        create : function() {
            creation.fail = false;
            creation.submitTitle();
            creation.submitDescription();
            creation.submitDatabaseAddress();
            creation.submitFeatureSet();
            creation.invitePlayers();
            creation.submitTermination();
            creation.submitGamemode();
            if (creation.savePattern) {
                 creation.saveP();
            }
        }
    }
});

var creation = new Vue({
    el: '#gamecreation',
    data: {
        title: '',
        invites: [],
        mode: '',
        numberFeatures: '',
        minSelect: '',
        maxSelect: '',
        patternName: '',
        description: '',
        terminationtype: '',
        terminationvalue: '',
        featureSet: '',
        databaseAddress: '',
        savePattern: false,
        savedPatterns: ["pattern 1"],
        selectedPattern: null,
        callbackCounter: 1 ,// very primitive sephamore
        alert: false,
        success: false, // success message for the use
        fail: false
    },
    methods: {
        emptyStore: function() {
            this.title = '';
            this.invites = [];
            this.mode = '';
            this.numberFeatures = '';
            this.minSelect = '';
            this.maxSelect = '';
            this.patternName = '';
            this.description = '';
            this.terminationtype = '';
            this.terminationvalue = '';
            this.featureSet = '';
            this.databaseAddress = '';
            this.savePattern = false;
            this.selectedPattern = null;
        },
        submitTitle: function() {
            if (this.title == '') {
                alert("Please set title");
                return;
            }
            this.callbackCounter++;
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    option: "title",
                    data: this.title,
                }
            }).then(function(response) {
                creation.callbackCounter--;
                if (creation.callbackCounter == 0) {
                    creation.createGame();
                }
            });
        },
        submitDescription: function() {
            if (this.description == '') {
                alert("Please set description");
                return;
            }
            this.callbackCounter++;
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    option: "description",
                    data: this.description,
                }
            }).then(function(response) {
                creation.callbackCounter--;
                if (creation.callbackCounter == 0) {
                    creation.createGame();
                }
            });
        },
        submitDatabaseAddress: function() {
            if (this.databaseAddress == '') {
                alert("Please set database address");
                return;
            }
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    option: "addressOrganiserDatabase",
                    data: this.databaseAddress,
                }
            })
        },
        addPlayer: function(email) {
            this.invites.push(email.toString());
        },
        invitePlayers: function() {
            if (this.invites.length == 0) {
                alert("You did not invite any players. Invite them later to your game")
            }
            this.players = '';
            var self = this;
            this.invites.forEach(function(email, index) {
                if (index == 0) {
                    self.players = email;
                } else {
                    self.players += "_" + email;
                }
            });
            this.callbackCounter++;
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    option: "addPlayers",
                    data: this.players,
                }
            }).then(function(response) {
                creation.callbackCounter--;
                if (creation.callbackCounter == 0) {
                    creation.createGame();
                }
            });
        },
        submitTermination: function() {
            if (this.terminationtype == '') {
                alert("Please set termination type");
                return;
            }
            if (this.terminationvalue == '') {
                alert("Please set a value for your termination");
                return;
            }
            this.callbackCounter++;
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    option: "termination",
                    data: this.terminationtype + ":" + this.terminationvalue,
                }
            }).then(function(response) {
                creation.callbackCounter--;
                if (creation.callbackCounter == 0) {
                    creation.createGame();
                }
            });
        },
        submitGamemode: function() {
            if (this.mode == '') {
                alert("Please set gamemmode");
                return;
            }
            if (this.mode == "matrix" && (this.numberFeatures == '' || this.maxSelect == '' || this.minSelect == '')) {
                alert("Please configure matrix select mode");
                return;
            }
            this.callbackCounter++;
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    option: "gamemode",
                    data: this.mode + ":" +
                        "num~" + this.numberFeatures + "-" +
                        "min~" + this.minSelect + "-" +
                        "max~" + this.maxSelect,
                }
            }).then(function(response) {
                creation.callbackCounter--;
                if (creation.callbackCounter == 0) {
                    creation.createGame();
                }
            });
        },
        submitFeatureSet: function() {

            var self = this;
            if (this.featureSet == '') {
                alert("Please set featureSet");
                return;
            } else {
                // checking if the feature set exists on the server
                axios({
                    method:'get',
                    url:'create/exists',
                    params: {
                        name: this.featureSet
                    }
                }).then(function(response) {
                    if (!response.data) {
                        alert("Feature set does not exist");
                        self.fail = true;
                        return;
                    }
                    axios({
                        method: 'post',
                        url: 'create/setParam',
                        params: {
                            option: "featureSet",
                            data: self.featureSet

                        }
                    }).then(function(response) {
                        creation.callbackCounter--;
                        if (creation.callbackCounter == 0) {
                            creation.createGame();
                        }
                    });
                })
            }


        },
        saveP: function() {
           if (this.patternName == '') {
               alert("Please name your pattern");
               return;
           }
           this.callbackCounter++;
            axios({
                method: 'post',
                url: 'create/savePattern',
                params: {
                    title: this.patternName,
                }
            }).then(function(response) {
                creation.callbackCounter--;
                if (creation.callbackCounter == 0) {
                    creation.createGame();
                }
            });
        },
        loadPattern: function() {
            this.title = this.selectedPattern.gameOptions.title;
            this.description = this.selectedPattern.gameOption.desc;
            // TODO add the rest
            this.terminationtype = this.selectedPattern.termination.type;
        },
        createGame: function() {
            var self = this;
            if (this.fail) return;
            axios({
                method: 'post',
                url: 'create'
            }).then(function (response) {
                self.success = true;
                window.location.href = "organiser.jsp"
            }).catch(function (error) {
                creation.alert = true;
            });
        },
    },
    mounted: function() {
        var self = this;
        axios({
            method: 'get',
            url: 'create/patterns',
        }).then(function (response) {
            self.savedPatterns = response.data;
        });
    }
});