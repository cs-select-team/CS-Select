Vue.component('player-invite-single', {
    data: function () {
        return {
            email: ''
        }
    },

    template: '<div class="input-group mb-3">\n' +
        '  <input type="email" class="form-control" placeholder="" aria-label="" ' +
        '       v-model="email" aria-describedby="inviteButton">\n' +
        '  <div class="input-group-append">\n' +
        '    <button class="btn btn-outline-secondary" type="button" :disabled="!this.validateEmail"' +
        ' v-on:click="newEmail" id="inviteButton">Button</button>\n' +
        '  </div>\n' +
        '</div>',

    methods: {
        newEmail: function () {
            this.$emit('new-email', this.email)
            this.email = "";
        }
    },
    computed: {
        validateEmail: function () {
            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(String(this.email).toLowerCase());
        }
    }
});
Vue.component('player-invite-textarea', {
    props: ['invited-players'],
    data: function () {
        return {
            rawText: this.joinedWith(this.invitedPlayers, ',')
        }
    },
    template: '<div class="input-group">\n' +
        '  <textarea class="form-control" aria-label="" v-model="rawText"></textarea>\n' +
        '</div>',
    methods: {
        joinedWith: function (array, char) {
            var string = '';
            array.forEach(function (value, index) {
                string += value;
                if (index < array.length - 1) {
                    string += char
                }
            })
            return string;
        },
        validateEmail: function (email) {
            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(String(email).toLowerCase());
        }
    },
    watch: {
        rawText: function (newVal) {
            var self = this;
            var wrongEmail = false;
            newVal = newVal.replace(/\s/g, '');
            var array = newVal.split(',');
            var newArray = [];
            array.forEach(function (value, index) {
                if (value == '') {

                    return
                } // trailing comma will cause one empty string
                else {
                    newArray.push(value)
                }
                if (!self.validateEmail(value)) {
                    wrongEmail = true;
                }
            });
            if (wrongEmail) {
                // TODO inform user
            } else {
                this.$emit('update-invited', newArray);
            }
        }
    }
});
Vue.component('player-invite-nav-tab', {
    props: ['title', 'value', 'current-tab'],
    template: '<li class="nav-item">\n' +
        '                            <a class="nav-link" v-bind:class="{active: currentTab == value}"\n' +
        '                                v-on:click="$emit(\'update-tab\', value)" href="#" v-on>{{title}}</a>\n' +
        '                        </li>'
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
        '    <label class="input-group-text" >Patterns</label>\n' +
        '  </div>\n' +
        '  <select class="custom-select" v-model="selectedPattern">\n' +
        '    <option v-for="(p, index) in listOfPatterns" v-bind:key="index" :value="p">{{p.title}}</option>' +
        '  </select>\n' +
        '</div>',
    mounted: function() {
        axios({
            method: 'get',
            url: 'create/patterns'
        }).then(function (response) {
            this.listOfPatterns = response.data
        })
    },
    watch: {
        selectedPattern: function(newVal) {
            this.$emit('load-pattern', newVal)
        }
    }
})
var creation = new Vue({
    el: '#gamecreation',
    data: {
        invitedPlayers: [],
        playerInputType: [{title: 'single', value: 'single'}, {title: 'textarea', value: 'textarea'}],
        title: '',
        desc: '',
        currentTab: 'single',
        gameModeConfigString: '',
        terminationConfigString: '',
        databaseName: '',
        featureSet: ''

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
                    name: name,
                    value: value
                }
            })
        },
        addInvitedPlayer: function (email) {
            this.invitedPlayers.push(email);
        },
        updateInvited: function (array) {

            this.invitedPlayers = array;
        },
        updateTab: function (newVal) {
            this.currentTab = newVal;
        },
        updateConfString: function (newVal) {
            this.gameModeConfigString = newVal;
        },
        updateTerminationString: function (newVal) {
            this.terminationConfigString = newVal;
        },
        loadPattern: function(newVal) {
            // TODO load pattern in newVal
        }

    },
    computed: {
        currentTabComponent: function () {
            return 'player-invite-' + this.currentTab
        }
    }
});
