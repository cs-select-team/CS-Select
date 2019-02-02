Vue.component('invite', {
    props: [''],
    template: '<div>' +
        '           <input type="text" id="emailToAdd" :placeholder="localisation.emailGC"/>' +
        '           <input type="button" class="btn btn-primary" ' +
        '               v-on:click="invite()" :value="localisation.invite"/>' +
        '      </div>',
    methods: {
        invite: function() {
            if(document.getElementById("emailToAdd").value.toString() != '') {
                creation.addPlayer(document.getElementById("emailToAdd").value.toString());
                document.getElementById("emailToAdd").value = '';
                alert("Successfully added Email")
            } else {
                alert("Please type in Email")
            }
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
    props: ['creationdisabled'],
    template: '<div class="container">' +
        '           <input type="button" class="btn btn-primary float-right btn-space" v-on:click="abort()"' +
        ':value="localisation.abort">' +
        '           <input type="button" class="btn btn-primary float-right btn-space" v-on:click="create()"' +
        ':value="localisation.create" :disabled="creationdisabled">' +
        '       </div>',
    methods: {
        abort : function() {
            creation.emptyStore();
        },
        create : function() {
            creation.fail = false;
            creation.creationenabled = true;
            axios.all([creation.submitTitle(), creation.submitDescription(), creation.submitDatabaseName(),
                creation.checkFeatureSet(), creation.invitePlayers(),
                creation.submitTermination(), creation.submitGamemode(), creation.submitFeatureSet()]).
                        then(function (response) {
                    if (!response[3].data) {
                        alert("Feature set does not exist");
                        return;
                    }
                    if (creation.savePattern) {
                        creation.saveP();
                    }
                    creation.createGame();
            });
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
        databaseName: '',
        savePattern: false,
        savedPatterns: ["pattern 1"],
        selectedPattern: null,
        callbackCounter: 1 ,// very primitive sephamore
        creationenabled: true,
        success: false // success message for the use
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
            this.databaseName = '';
            this.savePattern = false;
            this.selectedPattern = null;
        },
        submitTitle: function() {
            if (this.title == '') {
                alert("Please set title");
                return;
            }
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    option: "title",
                    data: this.title,
                }
            })
        },
        submitDescription: function() {
            if (this.description == '') {
                alert("Please set description");
                return;
            }
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    option: "description",
                    data: this.description,
                }
            })
        },
        submitDatabaseName: function() {
            if (this.databaseName == '') {
                alert("Please set database address");
                return;
            }
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    option: "addressOrganiserDatabase",
                    data: this.databaseName,
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
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    option: "addPlayers",
                    data: this.players,
                }
            })
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
            })
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
            })
        },
        checkFeatureSet: function() {
            var self = this;
            if (this.featureSet == '') {
                alert("Please set featureSet");
                return;
            } else {
                // checking if the feature set exists on the server
                return axios({
                    method: 'get',
                    url: 'create/exists',
                    params: {
                        name: this.featureSet
                    }
                })
            }
        },
        submitFeatureSet: function() {
                    axios({
                        method: 'post',
                        url: 'create/setParam',
                        params: {
                            option: "featureSet",
                            data: this.featureSet

                        }
                    })


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
            })
        },
        loadPattern: function() {
            this.title = this.selectedPattern.gameOptions.title;
            this.description = this.selectedPattern.gameOptions.desc;
            this.featureSet = this.selectedPattern.gameOptions.featureset;
            this.databaseName = this.selectedPattern.gameOptions.database;
            this.terminationtype = this.selectedPattern.gameOptions.termination.type;
            this.mode = this.selectedPattern.gameOptions.gamemode;
            this.terminationvalue = JSON.parse(this.selectedPattern.gameOptions.termination.value);
            this.invites = this.selectedPattern.gameOptions.invites;

        },
        createGame: function() {
            var self = this;
            self.creationenabled = false;
            if (this.fail) {
                self.creationenabled = true;
                return;
            }
            axios({
                method: 'post',
                url: 'create'
            }).then(function (response) {
                self.creationenabled = true;
                self.success = true;
            }).catch(function (error) {
                self.creationenabled = true;
                self.alert = true;
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
