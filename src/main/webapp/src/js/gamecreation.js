
Vue.component('pattern-selection', {
    data() {
        return {
            listOfPatterns: [],
            selectedPattern: {}
        };
    },
    template:
        `<div class="input-group mb-3">
            <div class="input-group-prepend">
                <label class="input-group-text">{{localisation.pattern}}</label>
            </div>
            <select class="custom-select" v-model="selectedPattern">
                <option v-for="(p, index) in listOfPatterns" v-bind:key="index" :value="p">{{p.title}}</option>
            </select>
        </div>`,
    mounted() {
        const self = this;
        axios({
            method: 'get',
            url: 'create/patterns'
        }).then(function (response) {
            self.listOfPatterns = response.data;
            self.$emit('set-patterns' ,response.data);
        });
    },
    watch: {
        selectedPattern(newVal) {
            this.$emit('load-pattern', newVal);
        }
    }
});
const creation = new Vue({
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
        showDatabaseModal: false,
        submittedTitle: true,
        titleDuplicate: false,
        submittedDatabaseName: true,
        createButtonEnabled: true,
        alerts: []
    },
    methods: {
        /** submits a new parameter to the api
         *
         * @param name name of the parameter
         * @param value value of that parameter
         */
        submitParameter(name, value) {
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    option: name,
                    data: value
                }
            });
        },
        updateInviteString(newVal) {
            const clearedString = newVal.split(',').filter(function (item, index, allItems) {
                return index === allItems.indexOf(item);
            }).join(',');
            $('#output').append(clearedString);
            this.inviteString = clearedString;
        },
        removePlayerByIndex(index) {
            const playerArray = this.inviteString.split(',');
            playerArray.splice(index, 1);
            this.inviteString = playerArray.join(',');
        },
        updateConfString(newVal) {
            this.gameModeConfigString = newVal;
        },
        updateTerminationString(newVal) {
            this.terminationConfigString = newVal;
        },
        loadPattern(newVal) {
            const gameOptions = newVal.gameOptions;
            this.featureSet = gameOptions.featureset;
            this.desc = gameOptions.desc;
            this.title = gameOptions.title;
            this.databaseName = gameOptions.database;
            this.gameModeConfigString = gameOptions.gamemodeConf;
            this.terminationConfigString = gameOptions.termination;
            this.inviteString = gameOptions.invites.join(',');
        },
        checkFeatureSet() {
            const self = this;
            if (this.featureSet === '') {
                self.alerts.push({message: self.localisation.featureSetNotSet, type: 0});
                return false;
            } else {
                // checking if the feature set exists on the server
                return axios({
                    method: 'get',
                    url: 'create/exists',
                    params: {
                        name: this.featureSet
                    }
                });
            }
        },
        startCreation() {
            const self = this;
            self.createButtonEnabled = false;
            if (!this.checkParameters()) {
                self.createButtonEnabled = true;
                return;
            }
            self.checkConflicts();
        },
        checkConflicts() {
            const self = this;
            axios.all([self.checkDatabaseExists(), self.checkTitleExists()]).then(function (response) {
                if (response[0].data || response[1].data) {
                    if (response[0].data) {
                        self.submittedDatabaseName = false;
                        self.showDatabaseModal = true;
                    }
                    if (response[1].data) {
                        self.submittedTitle = false;
                        self.showTitleModal = true;
                    }
                } else {
                    self.submitGame();
                }
            });
        },
        checkResolved() {
            if (this.submittedTitle && this.submittedDatabaseName) {
                this.submitGame();
            }
        },
        submitGame() {
            const self = this;
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
                    let isOverwriting = false;
                    self.listOfPatterns.forEach(function (pattern) {
                        if (pattern.title === self.patternName) {
                            isOverwriting = true;
                        }
                    });
                    if (isOverwriting) {
                        self.showPatternModal = true;
                    } else {
                        self.submitOverwritePattern();
                    }
                }
                self.createGame();
            }).catch(function (reason) {
                if (reason.response !== undefined) {
                    if (504 === reason.response.status) { // if gateway unavailable(i.e. ML-Server does not respond
                        self.alerts.push({message: self.localisation.MLServerDown, type: 0});
                        self.createButtonEnabled = true;
                    }
                } else {
                    self.alerts.push({message: self.localisation.creationFail, type: 0});
                    self.createButtonEnabled = true;
                }
            });
        },
        checkParameters() {
            this.alerts = [];
            // language=RegExp
            if (this.title.match(/^\s*$/)) {
                this.alerts.push({message: this.localisation.enterTitle, type: 0});
            }
            // language=RegExp
            if (this.desc.match(/^\s*$/)) {
                this.alerts.push({message: this.localisation.enterDescription, type: 0});
            }
            // language=RegExp
            if (this.databaseName.match(/^\s*$/)) {
                this.alerts.push({
                    message: this.localisation.enterDatabaseName,
                    type: 0
                });
            }
            if (this.featureSet.match(/^\s*$/)) {
                this.alerts.push({message: this.localisation.enterFeatureset, type: 0});
            }
            if ((this.terminationConfigString.split(':').length < 2 || this.terminationConfigString.split(':')[1] === '') &&
                this.terminationConfigString.split(':')[0].toString() !== 'organiser') {
                this.alerts.push({message: this.localisation.enterTermination, type: 0});
            }
            if (this.terminationConfigString.split(',').length > 1) {
                // composite termination
                for (let i = 0; i < this.terminationConfigString.split(',').length; i++) {
                    if (this.terminationConfigString.split(',')[i].split(':')[1] === '') {
                        this.alerts.push({message: this.localisation.enterTermination, type: 0});
                        break;
                    }
                }
            }
            return this.alerts.length === 0;
        },
        createGame() {
            const self = this;
            axios({
                method: 'post',
                url: 'create'
            }).then(function (response) {
                self.createButtonEnabled = true;
                self.alerts.push({message: self.localisation.creationSuccess, type: 1});
            }).catch(function (error) {
                if (error.status === 551) { // game has not been created
                    self.createButtonEnabled = true;
                    self.alerts.push({message: self.localisation.creationFail, type: 0});
                }
            });
        },
        setPatterns(list) {
            this.listOfPatterns = list;
        },
        submitOverwritePattern() {
            const self = this;
            axios({
                method: 'post',
                url: 'create/savePattern',
                params: {
                    title: self.patternName,
                }
            }).then(function () {
                self.showPatternModal = false;
            });
        },
        declineOverwritePattern() {
            const self = this;
            self.showPatternModal = false;
        },
        checkDatabaseExists() {
            const self = this;
            return axios({
                method: 'get',
                url: 'create/dbexists',
                params: {
                    name: self.databaseName
                }
            });
        },
        checkTitleExists() {
            const self = this;
            return axios({
                method: 'get',
                url: 'create/titleexists',
                params: {
                    name: self.title
                }
            });
        },
        submitDatabaseName() {
            const self = this;
            self.submittedDatabaseName = true;
            self.showDatabaseModal = false;
            self.checkResolved();
        },
        submitTitle() {
            const self = this;
            self.submittedTitle = true;
            self.showTitleModal = false;
            self.checkResolved();
        },
        abortCreation() {
            const self = this;
            self.showTitleModal = false;
            self.showDatabaseModal = false;
            self.submittedTitle = false;
            self.submittedDatabaseName = false;
            self.createButtonEnabled = true;
            self.alerts.push({message: self.localisation.creationAbort, type: 0});
        }
    },
});
