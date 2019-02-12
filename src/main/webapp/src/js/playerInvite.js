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
        ' v-on:click="newEmail" id="inviteButton">{{ localisation.invite }}</button>\n' +
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
    props: ['invite-string'],
    data: function () {
        return {
            rawText: this.inviteString
            }
        },
    template: '<div class="input-group">\n' +
        '  <textarea class="form-control" aria-label="" :title="localisation.gamecreationMassInviteTooltip" v-model="rawText"></textarea>\n' +
        '</div>',
    methods: {
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
                this.$emit('update-invited', newArray.join(','));
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
Vue.component('player-invite-box', {
    props: ['invite-string'],
    data: function () {
        return {
            playerInputType: [],
            currentTab: 'single'
        }
    },
    mounted: function () {
        this.playerInputType = [{title: this.localisation.invitePlayerSingle, value: 'single'},
            {title: this.localisation.invitePlayerMass, value: 'textarea'}]
    },
    computed:{
        invitedPlayers: {
            get: function () {
                var array = this.inviteString.split(',');
                var newArray = [];
                array.forEach(function(value) { // removing empty strings
                   if (value !== '') {
                       newArray.push(value)
                   }
                });
                return newArray;
            }
        },
        currentTabComponent: function () {
            return 'player-invite-' + this.currentTab
        },
    }, methods: {
        addPlayer: function (email) {
            var players = this.invitedPlayers;
            players.push(email);
            this.$emit('update-invite-string', players.join(','))
        },
        updateInviteString: function (newVal) {
            this.$emit('update-invite-string', newVal)
        },
        updateTab: function(value) {
            this.currentTab = value;
        }
    },
    template: '<div>' +
        '<ul class="nav nav-tabs">\n' +
        '<player-invite-nav-tab v-for="(value, index) in playerInputType" v-bind:key="index"\n' +
        '                       v-bind:title="value.title" v-bind:value="value.value"\n' +
        '                       v-on:update-tab="updateTab">\n' +
        '</player-invite-nav-tab>\n' +
        '</ul>\n' +
        '<component v-bind:is="currentTabComponent" v-on:new-email="addPlayer" v-bind:invite-string="inviteString"\n' +
        '           v-on:update-invited="updateInviteString"></component>' +
        '</div>'
})