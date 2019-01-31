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
            creation.submitTitle();
            creation.submitDescription();
            creation.submitDatabaseAddress();
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
        pattern: '',
        description: '',
        terminationtype: '',
        terminationvalue: '',
        featureSet: '',
        databaseAddress: '',
        savePattern: false,
        savedPatterns: ["pattern 1"],
        selected: 'null',
        callbackCounter: 0 // very primitive sephamore
    },
    methods: {
        emptyStore: function() {
            this.title = '';
            this.invites = [];
            this.mode = '';
            this.numberFeatures = '';
            this.minSelect = '';
            this.maxSelect = '';
            this.pattern = null;
            this.description = '';
            this.terminationtype = '';
            this.terminationvalue = '';
            this.features = '';
            this.databaseAddress = '';
            this.savePattern = false;
            this.selected = 'null';
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
            this.invites.forEach(function(email, index) {
                if (index == 0) {
                    this.players = email;
                } else {
                    this.players += "_" + email;
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
                    data: this.terminationtype + "_" + this.terminationvalue,
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
                    data: this.mode + "_" +
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
        saveP: function() {
           if (this.pattern == '') {
               alert("Please name your pattern");
               return;
           }
           this.callbackCounter++;
            axios({
                method: 'post',
                url: 'create/savePattern',
                params: {
                    title: this.pattern,
                }
            }).then(function(response) {
                creation.callbackCounter--;
                if (creation.callbackCounter == 0) {
                    creation.createGame();
                }
            });
        },
        loadPattern: function() {
            this.title = this.pattern.gameOptions.title;
            this.description = this.pattern.gameOption.desc;
            // TODO add the rest
            this.terminationtype = this.pattern.termination.type;
        },
        createGame: function () {
            this.callbackCounter++;
            axios({
                method: 'post',
                url: 'create'
            })
        }
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