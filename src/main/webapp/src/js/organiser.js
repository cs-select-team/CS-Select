Vue.component('active-games-display', {
    props: ['game', ],
    data: function () {
        return {
            email: '',
            inviteString: '',
        }
    }
    ,
    template:
        `<div class="container">
            <div class="container">
                <div class="row">
                    <div class="col-md-6 col-sm-12 col-xs-12">
                        <div class="word-wrap">
                            {{ game.title }}
                        </div>
                        <div>
                            {{ game.type }}
                        </div>
                        <div class="word-wrap">
                            {{game.desc}}
                        </div>
                        <div>
                            {{localisation.roundsPlayed + ": " + game.roundsPlayed}}
                        </div>
                    </div>
                    <div class="col">
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
        terminate: function(gameId) {
            axios({
                method: 'post',
                url: 'create/terminate',
                params: {
                    gameId: gameId
                }
            });
            this.$emit("terminate", gameId);
        },
        invitePlayers: function(gameId) {
            this.inviteString.split(',').forEach(function (value) {
                if (value != '') {
                    axios({
                        method: 'post',
                        url: 'create/invite',
                        params: {
                            gameId: gameId,
                            email: value
                        }
                    })
                }
            })
        },
        updateInviteString: function (newVal) {
            this.inviteString = newVal;
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
                        <div class="word-wrap">
                            {{ game.title }}
                        </div>
                        <div>
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
        remove: function (gameId) {
            axios({
                method: 'post',
                url: 'create/delete',
                params: {
                    gameId: gameId
                }
            });
            terminatedGames.listOfGames.forEach(function (value, index) { // remove from the list without reloading page
                if (value.id == gameId) terminatedGames.listOfGames.splice(index, 1);
            });
        }
    }
});

Vue.component('stats-display', {
    props: [''],
    template: ``
});

var activeGames = new Vue({
    el: "#active",
    data: {
        listOfGames: []
    },
    mounted: function () {
        axios({
            method: 'get',
            url: "create/active"
        }).then(function (response) {
            activeGames.listOfGames = response.data
        })
    },
    methods: {
        gameWasTerminated: function (gameId) {
            var self = this;
            this.listOfGames.forEach(function (value, index) {
                if (value.id == gameId) {
                    var game = self.listOfGames.splice(index, 1);
                    terminatedGames.listOfGames.push(game[0]);
                }
            })

        }
    }

});

var terminatedGames = new Vue({
    el: "#terminated",
    data: {
        listOfGames: []
    },
    mounted: function () {
        axios({
            method: 'get',
            url: "create/terminated"
        }).then(function (response) {
            terminatedGames.listOfGames = response.data
        })
    }
});

var stats = new Vue({
    el: "#stats"
});