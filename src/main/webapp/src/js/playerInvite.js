Vue.component('player-invite-single', {
    data() {
        return {
            email: ''
        }
    },

    template:
        `<div class="input-group mb-3"><input type="email" class="form-control" placeholder="" aria-label="" v-model="email"
                                             aria-describedby="inviteButton">
            <div class="input-group-append">
                <button class="btn btn-outline-secondary" type="button" :disabled="!this.validateEmail" v-on:click="newEmail"
                        id="inviteButton">{{ localisation.invite }}
                </button>
            </div>
        </div>`, 
    methods: {
        newEmail() {
            this.$emit('new-email', this.email);
            this.email = "";
        }
    },
    computed: {
        validateEmail() {
            const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(String(this.email).toLowerCase());
        }
    }
});
Vue.component('player-invite-textarea', {
    props: ['invite-string'],
    data() {
        return {
            rawText: this.inviteString
            }
        },
    template:
        `<div class="input-group">
            <textarea class="form-control" aria-label=""
                                           :title="localisation.gamecreationMassInviteTooltip" v-model="rawText">
            </textarea>
        </div>`,
    methods: {
        validateEmail(email) {
            const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(String(email).toLowerCase());
        }
    },
    watch: {
        rawText(newVal) {
            const self = this;
            let wrongEmail = false;
            newVal = newVal.replace(/\s/g, '');
            const array = newVal.split(',');
            const newArray = [];
            array.forEach(function (value, index) {
                if (value === '') {

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
    template:
        `<li class="nav-item">
            <a class="nav-link" v-bind:class="{active: currentTab == value}" v-on:click="$emit('update-tab', value)" href="#" v-on>{{title}}</a>
        </li>`
});
Vue.component('player-invite-box', {
    props: ['invite-string'],
    data() {
        return {
            playerInputType: [],
            currentTab: 'single'
        }
    },
    mounted() {
        this.playerInputType = [{title: this.localisation.invitePlayerSingle, value: 'single'},
            {title: this.localisation.invitePlayerMass, value: 'textarea'}]
    },
    computed:{
        invitedPlayers: {
            get() {
                const array = this.inviteString.split(',');
                const newArray = [];
                array.forEach(function(value) { // removing empty strings
                   if (value !== '') {
                       newArray.push(value)
                   }
                });
                return newArray;
            }
        },
        currentTabComponent() {
            return 'player-invite-' + this.currentTab
        },
    }, methods: {
        addPlayer(email) {
            const players = this.invitedPlayers;
            players.push(email);
            this.$emit('update-invite-string', players.join(','))
        },
        updateInviteString(newVal) {
            this.$emit('update-invite-string', newVal)
        },
        updateTab(value) {
            this.currentTab = value;
        }
    },
    template:
        `<div>
            <ul class="nav nav-tabs">
                <player-invite-nav-tab v-for="(value, index) in playerInputType" v-bind:key="index" v-bind:title="value.title"
                                       v-bind:value="value.value" v-on:update-tab="updateTab"></player-invite-nav-tab>
            </ul>
            <component v-bind:is="currentTabComponent" v-on:new-email="addPlayer" v-bind:invite-string="inviteString"
                       v-on:update-invited="updateInviteString">
            </component>
        </div>`
});