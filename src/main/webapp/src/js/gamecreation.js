Vue.component('pattern-modal', {
    template: '#modal-template'
});
Vue.component('title-modal', {
    template: '#modal-template'
});
Vue.component('pattern-selection', {
    data: function() {
        return {
            listOfPatterns: [],
            selectedPattern: {}
        }
    },
    template: '<div class="input-group mb-3">\n' +
        '  <div class="input-group-prepend">\n' +
        '    <label class="input-group-text" >{{localisation.pattern}}</label>\n' +
        '  </div>\n' +
        '  <select class="custom-select" v-model="selectedPattern">\n' +
        '    <option v-for="(p, index) in listOfPatterns" v-bind:key="index" :value="p">{{p.title}}</option>' +
        '  </select>\n' +
        '</div>',
    mounted: function() {
        var self = this;
        axios({
            method: 'get',
            url: 'create/patterns'
        }).then(function (response) {
            self.listOfPatterns = response.data;
            self.$emit('set-patterns' ,response.data);
        });
    },
    watch: {
        selectedPattern: function(newVal) {
            this.$emit('load-pattern', newVal)
        }
    }
});
var creation = new Vue({
    el: '#gamecreation',
    data: {

        title: '',
        desc: '',
        inviteString: '',
        gameModeConfigString: '',
        terminationConfigString: '',
        databaseName: '',
        featureSet: '',
        saveAsPattern: false,
        patternName: '',
        listOfPatterns: [],
        showPatternModal: false,
        showTitleModal: false,
        fromCreation: false,
        createButtonEnabled: true,
        acceptTitle: true,
        alerts: []
    },
    methods: {
        /** submits a new parameter to the api
         *
         * @param name name of the parameter
         * @param value value of that parameter
         */
        submitParameter: function (name, value) {
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    option: name,
                    data: value
                }
            })
        },
        updateInviteString: function (newVal) {
            this.inviteString = newVal;
        },
        updateConfString: function (newVal) {
            this.gameModeConfigString = newVal;
        },
        updateTerminationString: function (newVal) {
            this.terminationConfigString = newVal;
        },
        loadPattern: function (newVal) {
            var gameOptions = newVal.gameOptions;
            this.featureSet = gameOptions.featureset;
            this.desc = gameOptions.desc;
            this.title = gameOptions.title;
            this.databaseName = gameOptions.database;
            this.gameModeConfigString = gameOptions.gamemodeConf;
            this.terminationConfigString = gameOptions.termination;
            this.invitedPlayers = gameOptions.invites.join(',');
        },
        checkFeatureSet: function () {
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
        startCreation: function () {
            var self = this;
            self.createButtonEnabled = false;
            if (!this.checkParameters()) {
                self.createButtonEnabled = true;
                return;
            }
            if (self.checkTitleExists()) {
                self.fromCreation = true;
                self.showTitleModal = true;
            } else {
                self.submitGame()
            }
        },
        submitGame: function () {
            var self = this;
            axios.all(
                [this.checkFeatureSet(), this.submitParameter('title', this.title),
                this.submitParameter('description', this.desc), this.submitParameter('addressOrganiserDatabase', this.databaseName),
                this.submitParameter('termination', this.terminationConfigString), this.submitParameter('gamemode', this.gameModeConfigString),
                this.submitParameter('featureSet', this.featureSet), this.submitParameter('addPlayers', this.inviteString)]).then(function (response) {
                if (!response[0].data) {
                    self.alerts.push({message: self.localisation.featureSetMissingMessage, type: 0});
                    self.createButtonEnabled = true;
                    return;
                }
                if (self.saveAsPattern) {
                    var isOverwriting = false;
                    self.listOfPatterns.forEach(function (pattern) {
                        if (pattern.title === self.patternName) {
                            isOverwriting = true
                        }
                    });
                    if (isOverwriting) {
                        self.showPatternModal = true
                    } else {
                        self.submitOverwritePattern()
                    }
                }
                self.createGame();
            }).catch(function (reason) {
                if (504 === reason.response.status) { // if gateway unavailable(i.e. ML-Server does not respond
                    self.createButtonEnabled = true;
                    self.alerts.push({message: self.localisation.MLServerDown, type: 0})
                }
            })
        },
        checkParameters: function () {
            this.alerts = [];
            // language=RegExp
            if (this.title.match(/^\s*$/)) this.alerts.push({message: this.localisation.enterTitle, type: 0});
            // language=RegExp
            if (this.desc.match(/^\s*$/)) this.alerts.push({message: this.localisation.enterDescription, type: 0});
            // language=RegExp
            if (this.databaseName.match(/^\s*$/)) this.alerts.push({
                message: this.localisation.enterDatabaseName,
                type: 0
            });
            if (this.featureSet.match(/^\s*$/)) this.alerts.push({message: this.localisation.enterFeatureset, type: 0});
            if (this.terminationConfigString.split(':').length < 2 &&
                this.terminationConfigString.split(':')[0].toString() != 'organiser')
                this.alerts.push({message: this.localisation.enterTermination, type: 0});
            return this.alerts.length === 0;
        },
        submitTitle: function () {
            var self = this;
            self.showTitleModal = false;
            if (self.fromCreation === true) self.submitGame()
        },
        createGame: function () {
            var self = this;
            axios({
                method: 'post',
                url: 'create'
            }).then(function (response) {
                self.createButtonEnabled = true;
                self.alerts.push({message: self.localisation.creationSuccess, type: 1})
            }).catch(function (error) {
                if (error.status == 551) { // game has not been created
                    self.createButtonEnabled;
                    self.alerts.push({message: self.localisation.creationFail, type: 0})
                }
            });
        },
        setPatterns: function (list) {
            this.listOfPatterns = list;
        },
        submitOverwritePattern: function () {
            var self = this;
            axios({
                method: 'post',
                url: 'create/savePattern',
                params: {
                    title: self.patternName,
                }
            }).then(function () {
                self.showPatternModal = false;
            })
        },
        declineOverwritePattern: function () {
            var self = this;
            self.showPatternModal = false
        },
        checkTitleExists: function() {
            return axios({
                method: 'get',
                url: 'create/titleexists',
                params: {
                    name: self.title
                }
            })
        },
    },
});
