Vue.component('invite', {
    props: [''],
    template: '<div>' +
        '           <input type="text" id="emailToAdd" :placeholder="localisation.emailGC"/>' +
        '           <input type="button" class="btn btn-primary" ' +
        '               v-on:click="invite()" :value="localisation.invite"/>' +
        '      </div>',
    methods: {
        invite() {
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
        loadPattern() {
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
            setTimeout(creation.submitTitle, 1000);
            setTimeout(creation.submitDescription, 1000);
            setTimeout(creation.submitDatabaseAddress, 1000);
            setTimeout(creation.invitePlayers, 1000);
            setTimeout(creation.submitTermination, 1000);
            setTimeout(creation.submitGamemode, 1000);
            if (creation.savePattern) {
                setTimeout(creation.saveP());
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
    },
    methods: {
        emptyStore() {
            this.title = '';
            this.invites = [];
            this.mode = '';
            this.numberFeatures = '';
            this.minSelect = '';
            this.maxSelect = '';
            this.pattern = '';
            this.description = '';
            this.terminationtype = '';
            this.terminationvalue = '';
            this.features = '';
            this.databaseAddress = '';
            this.savePattern = false;
            this.selected = 'null';
        },
        submitTitle() {
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
            });
        },
        submitDescription() {
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
            });
        },
        submitDatabaseAddress() {
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
            });
        },
        addPlayer(email) {
            this.invites.push(email.toString());
        },
        invitePlayers() {
            if (this.invites.length == 0) {
                alert("You did not invite any players. Invite them later to your game")
            }
            let players = '';
            this.invites.forEach(function(email, index) {
                if (index == 0) {
                    players += email.toString();
                } else {
                    players += ":" + email.toString();
                }
            });
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    option: "addPlayers",
                    data: players,
                }
            });
        },
        submitTermination() {
            if (this.terminationtype == '') {
                alert("Please set termination type");
                return;
            }
            if (this.terminationvalue == '') {
                alert("Please set a value for your termination");
                return;
            }
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    option: "termination",
                    data: this.terminationtype + ":" + this.terminationvalue,
                }
            });
        },
        submitGamemode() {
            if (this.mode == '') {
                alert("Please set gamemmode");
                return;
            }
            if (this.mode == "matrix" && (this.numberFeatures == '' || this.maxSelect == '' || this.minSelect == '')) {
                alert("Please configure matrix select mode");
                return;
            }
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
            });
        },
        saveP() {
           if (this.pattern == '') {
               alert("Please name your pattern");
               return;
           }
            axios({
                method: 'post',
                url: 'create/savePattern',
                params: {
                    title: this.pattern,
                }
            });
        },
        loadPattern() {
            this.savedPatterns.forEach(function(value) {
               if (value.toString() == creation.selected.toString()) {
                   axios({
                       method: 'post',
                       url: 'create/loadPattern',
                       params: {
                           pattern: value,
                       }
                   })
               }
            });
            //TODO: Extract somehow the parameters for all fields
        }
    },
    mounted: function() {
        axios({
            method: 'get',
            url: 'create/patterns',
        }).then(function (response) {
            this.patterns= response.data;
        });
    }
});