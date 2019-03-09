Vue.component('active-games-display', {
    props: ['game'],
    data() {
        return {
            email: '',
            inviteString: '',
            showPatternModal: false,
            patternTitle: '',
        }
    }
    ,
    template:
        `<div class="container">
            <div class="container">
                <div class="row">
                    <div class="col-md-6 col-sm-12 col-xs-12">
                        <div :title="localisation.gameTitle" class="word-wrap">
                            {{ game.title }}
                        </div>
                        <div :title="localisation.gamemode">
                            {{ game.type }}
                        </div>
                        <div :title="localisation.gameDescription" class="word-wrap">
                            {{game.desc}}
                        </div>
                        <div :title="localisation.roundsPlayedAll">
                            {{localisation.roundsPlayed + ": " + game.roundsPlayed}}
                        </div>
                    </div>
                    <modal-template v-if="showPatternModal">
                        <h3 slot="header">{{localisation.patternFromGameModalTitle}}</h3>
                        <a slot="body">{{localisation.patternFromGameModalText}}</a>
                        <input type="text" slot="body" class="form-control" v-model="patternTitle" :placeholder="localisation.inputTitle">
                        <hr slot="body">
                        <button type="button"
                            slot="footer"
                            class="btn btn-primary"
                            v-on:click="doCreatePattern(game.id)">{{localisation.submit}}
                        </button>
                    </modal-template>
                    <div class="col">
                        <input type="button" class="btn btn-secondary float-right btn-space" :title="localisation.createPatternFromGameTooltip"
                         :value="localisation.createPatternFromGame" v-on:click="createPattern(game.id)">
                        <input type="button" :title="localisation.terminateGameHelp" class="btn btn-secondary float-right btn-space"
                            v-on:click="terminate(game.id)" :value="localisation.terminate">
                        <input type="button" class="btn btn-primary float-right btn-space" :value="localisation.invite"
                            data-toggle="modal" :title="localisation.invitePlayerHelp" :data-target="'#inviteModal' + game.id">
                    </div>
                </div>
            </div>
            <div class="modal fade" :id="'inviteModal' + game.id" tabindex="-1" role="dialog"
                 aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header"><h5 class="modal-title" id="modalLabel">{{ localisation.invite }}</h5>
                            <button :title="localisation.closeTooltip" type="button" class="close" data-dismiss="modal"
                                    aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <player-invite-box v-bind:invite-string="inviteString"
                                               v-on:update-invite-string="updateInviteString">
                            </player-invite-box>
                            <table class="table">
                                <thead>
                                    <th> {{localisation.playerEmails}} </th>
                                    <th>  {{localisation.remove }}</th>
                                </thead>
                                <tr v-if="inviteString != ''" v-for="(email,index) in inviteString.split(',')">
                                    <td>{{email}}</td>
                                    <td  ><a href="#" v-on:click="removePlayerByIndex(index)" >{{localisation.remove }}</a></td>
                                </tr>
                            </table>
                            <button class="btn btn-primary" v-on:click="invitePlayers(game.id)">
                                {{localisation.sendInvitations}}
                            </button>
                        </div>
                        <div class="modal-footer"></div>
                    </div>
                </div>
            </div>
            <hr class="separator">
        </div>`,
    methods: {
        terminate (gameId) {
            axios({
                method: 'post',
                url: 'create/terminate',
                params: {
                    gameId: gameId
                }
            });
            this.$emit('terminate', gameId);
        },
        invitePlayers (gameId) {
            this.inviteString.split(',').forEach(function (value) {
                if (value !== '') {
                    axios({
                        method: 'post',
                        url: 'create/invite',
                        params: {
                            gameId: gameId,
                            email: value
                        }
                    });
                }
            });
        },
        updateInviteString(newVal) {
            this.inviteString = newVal;
        },
        createPattern(gameId) {
            const self = this;
            self.showPatternModal = true;
        },
        doCreatePattern(gameId) {
            const self = this;
            self.showPatternModal = false;
            axios({
                method: 'post',
                url: 'create/patternFromGame',
                params: {
                    gameId,
                    title: self.patternTitle,
                }
            });
        },
        removePlayerByIndex(index) {
            const playerArray = this.inviteString.split(',');
            playerArray.splice(index, 1);
            this.inviteString = playerArray.join(',');
        }
    }
});

Vue.component('terminated-games-display', {
    props: ['game'],
    template:
        `<div class="container">
            <div class="container">
                <div class="row">
                    <div class="col-md-6 col-sm-12 col-xs-12">
                        <div :title="localisation.gameTitle" class="word-wrap">
                            {{ game.title }}
                        </div>
                        <div :title="localisation.gamemode">
                            {{ game.type }}
                        </div>
                    </div>
                    <div class="col">
                        <input type="button" :title="localisation.deleteGameHelp"
                                            class="btn btn-secondary float-right btn-space" v-on:click="remove(game.id)"
                                            :value="localisation.del">
                    </div>
                </div>
            </div>
        </div>`,
    methods: {
        remove(gameId) {
            axios({
                method: 'post',
                url: 'create/delete',
                params: {
                    gameId: gameId
                }
            });
            terminatedGames.listOfGames.forEach(function (value, index) { // remove from the list without reloading page
                if (value.id === gameId) terminatedGames.listOfGames.splice(index, 1);
            });
        }
    }
});

Vue.component('stats-display', {
    props: [''],
    template: ``
});

const activeGames = new Vue({
    el: '#active',
    data: {
        listOfGames: []
    },
    mounted() {
        axios({
            method: 'get',
            url: 'create/active'
        }).then(function (response) {
            activeGames.listOfGames = response.data
        });
    },
    methods: {
        gameWasTerminated(gameId) {
            const self = this;
            this.listOfGames.forEach(function (value, index) {
                if (value.id === gameId) {
                    const game = self.listOfGames.splice(index, 1);
                    terminatedGames.listOfGames.push(game[0]);
                }
            });
        }
    }
});

const terminatedGames = new Vue({
    el: '#terminated',
    data: {
        listOfGames: []
    },
    mounted() {
        axios({
            method: 'get',
            url: 'create/terminated'
        }).then(function (response) {
            terminatedGames.listOfGames = response.data;
        });
    }
});

const stats = new Vue({
    el: '#stats'
});