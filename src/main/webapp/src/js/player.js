Vue.component('game-display', {
    props: ['game'],
    template:
        `<div class="card mt-1">
            <div class="card-body">
                <div class="row">
                    <div class="col">
                        <div :title="localisation.gameTitle" class="word-wrap">{{ game.title }}</div>
                        <div :title="localisation.gamemode">{{ game.type }}</div>
                        <div :title="localisation.roundsPlayedAll">{{ localisation.roundsPlayed + ': ' + game.roundsPlayed }}</div>
                        <div :title="localisation.gameDescription" class="word-wrap">{{game.desc}}</div>
                        <div :title="localisation.termination" >{{terminationNotice}}</div>
                    </div>
                    <div class="col"><input type="button" :title="localisation.playGameTooltip"
                                            class="btn btn-primary float-right" :value="localisation.play"
                                            v-on:click="startGame(game.id)"/></div>
                </div>
            </div>
        </div>`,
    methods: {
        startGame: function(gameId) {
            localStorage.setItem("gameId", gameId);
            window.location.href = "game.jsp"
        }
    },
    computed: {
        terminationType: function () {
            if (this.game.termination.split(',').length > 1) return 'composite';
            return this.game.termination.split(':')[0];
        },
        terminationNotice: function () {
            switch(this.terminationType) {
                case 'composite':
                    return this.localisation.compositeTermination;
                case 'organiser':
                    return this.localisation.organiserTermination;
                case 'time':
                    return this.localisation.timeTermination + ' ' + moment.unix(this.game.termination.split(':')[1] / 1000).format("DD/MM/YYYY");
                case 'rounds':
                    return this.localisation.roundsTermination + ' ' + this.game.termination.split(':')[1];
            }
        }
    }
});

Vue.component('daily-challenge', {
    props:['daily'],
    template:
        `<div class="card p-2" v-bind:class="{'disabled-div': daily.finished, 'bg-success': daily.finished}">
            <div class="card-body"><h5 class="card-title">{{daily.title}}</h5>
                <div class="card-text"> {{localisation.reward}}: {{daily.points}} {{localisation.points}}</div>
                <div class="card-text" v-if="daily.finished"> {{localisation.dailyFinished}}</div>
            </div>
        </div>`
});
Vue.component('leaderboard-element', {
    props: ['place', 'username', 'points'],
    template:
        `<tr>
            <th>{{ place }}</th>
            <th>{{ username }}</th>
            <th :title="localisation.lastWeekScoreTooltip">{{ points }}</th>
        </tr>`
});

Vue.component('stats-display', {
    props: ['points', 'username'],
    template:
        `<div>
            <b class="text">{{username}}</b>
            <p class="text" :title="localisation.totalScoreTooltip">{{points}}</p>
        </div>`
});
Vue.component('invite-element', {
    props: ['title', 'gameId'],
    template:
        `<div class="card mt-1">
            <div class="card-body">
                <div class="row">
                    <div class="col">
                        <div :title="localisation.gameTitle" class="float-left word-wrap">{{title}}</div>
                    </div>
                    <div class="col">
                        <div class="btn-group" role="group" aria-label="Annehmen/Ablehnen von Einladungen">
                            <button type="button" :title="localisation.acceptInvite" class="btn-primary btn float-right" v-on:click="accept(gameId)">
                                {{localisation.accept}}
                            </button>
                            <button type="button" :title="localisation.declineInvite" class="btn-secondary btn float-right" v-on:click="decline(gameId)">
                                {{localisation.decline}}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>`,
    methods: {
        accept: function (gameId) {
            axios({
                method: 'post',
                url: 'games/' + gameId + '/accept'
            });
            invites.listOfInvites.forEach(function (value, index) { // remove from the list without reloading page
                if (value.id === gameId){

                    games.listOfGames.push(invites.listOfInvites.splice(index, 1)[0]);
                }
            });
        },
        decline: function (gameId) {
            axios({
                method: 'post',
                url: 'games/' + gameId + '/decline'
            });
            invites.listOfInvites.forEach(function (value, index) {  // remove from the list without reloading page
                if (value.id === gameId) invites.listOfInvites.splice(index, 1);
            })
        }
    }
});

const invites = new Vue({
    el: '#invites',
    data: {
        listOfInvites: []
    },
    mounted: function () {
        axios({
            method: 'get',
            url: 'users/notifications'
        }).then(function (response) {
            invites.listOfInvites = response.data
        })

    }
});
const stats = new Vue({
    el: '#stats',
    data: {
        username: '',
        points: 0
    },
    mounted: function () {
        axios({
            method: 'get',
            url: 'users'
        }).then(function (response) {
            stats.points = response.data.points;
            stats.username = response.data.username;
        })

    }
});

const games = new Vue({
    el: '#games',
    data: {
        listOfGames: []
    },
    mounted: function () {
        axios({
            method: 'get',
            url: 'games'
        }).then(function (response) {
            games.listOfGames = response.data
        })
    }
});

const leaderboard = new Vue({
    el: '#leaderboard',
    data: {
        playerList: []
    },
    mounted: function () {
        axios({
            method: 'get',
            url: 'users/leaderboard'
        }).then(function (response) {
            leaderboard.playerList = response.data
        })
    }
});


const daily = new Vue({
    el: "#daily",
    data: {
        daily: {}
    },
    mounted: function () {
        axios({
            method: 'get',
            url: 'users/daily'
        }).then(function (response) {
            daily.daily = response.data
        })
    }
});

const playerAlerts = new Vue({
    el: '#playerAlerts',
    data: {
        gameTerminated: false
    },
    mounted: function () {
        if (localStorage.hasOwnProperty("gameTerminated")) {
            this.gameTerminated = localStorage.getItem("gameTerminated") === "true";
        }
        localStorage.setItem("gameTerminated", false);

    }
});
