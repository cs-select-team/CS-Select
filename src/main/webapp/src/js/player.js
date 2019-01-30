Vue.component('game-display', {
    props: ['game'],
    template: '<div class="card mt-1"><div class="card-body"><div class="row">' +
        '                <div class="col"><div>{{ game.title }}</div>' +
        '                <div>{{ game.type }}</div>' +
        '                <div>{{ game.roundsPlayed  }}</div></div>' +
        '                <div class="col"><input type="button" class="btn btn-primary float-right" :value="localisation.play" v-on:click="startGame(game.gameId)"/></div>' +
        '            </div></div></div>',
    methods: {
        startGame: function(gameId) {
            localStorage.setItem("gameId", gameId);
            window.location.href = "game.jsp"
        }
    }
});

Vue.component('daily-challenge', {
    props:['daily'],
    template: '<div class="card" v-bind:class="{\'disabled-div\': daily.finished}">' +
        '<h5 class="card-title">{{daily.title}}</h5>' +
        '<div class="card-body">' +
        '   {{localisation.points}}: {{daily.points}}' +
        '</div>' +
        '</div>'
})

Vue.component('leaderboard-element', {
    props: ['place', 'username', 'points'],
    template: '<tr>\n' +
        '                    <th>{{ place }}</th>\n' +
        '                    <th>{{ username }}</th>\n' +
        '                    <th>{{ points }}</th>\n' +
        '                </tr>'
})

Vue.component('stats-display', {
    props: ['points', 'username'],
    template: '        <div>\n' +
        '            <b class="text">{{username}}</b>\n' +
        '            <p class="text">{{points}}</p>\n' +
        '        </div>'
})
Vue.component('invite-element', {
    props: ['title', 'gameId'],
    template: '        <div class="card mt-1">\n' +
        '            <div class="card-body">\n' +
        '            <div class="row">\n' +
        '                <div class="col">\n' +
        '                <div class="float-left">{{title}} {{gameId}}</div>\n' +
        '                </div>\n' +
        '                <div class="col">\n' +
        '                <div class="btn-group" role="group" aria-label="Annehmen/Ablehnen von Einladungen">\n' +
        '                    <button type="button" class="btn-primary btn float-right" v-on:click="accept(gameId)">{{localisation.accept}}</button>\n' +
        '                    <button type="button" class="btn-secondary btn float-right" v-on:click="decline(gameId)">{{localisation.decline}}</button>\n' +
        '                </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            </div>\n' +
        '        </div>',
    methods: {
        accept: function (gameId) {
            axios({
                method: 'post',
                url: 'games/' + gameId + '/accept'
            })
            invites.listOfInvites.forEach(function (value, index) { // remove from the list without reloading page
                if (value.gameId == gameId) invites.listOfInvites.splice(index, 1);
            });
        },
        decline: function (gameId) {
            axios({
                method: 'post',
                url: 'games/' + gameId + '/decline'
            })
            invites.listOfInvites.forEach(function (value, index) {  // remove from the list without reloading page
                if (value.gameId == gameId) invites.listOfInvites.splice(index, 1);
            })
        }
    }
});

var invites = new Vue({
    el: '#invites',
    data: {
        listOfInvites: [{title: "Titel", gameId: 1},
            {title: "Titel2", gameId: 2},
            {title: "Titel3", gameId: 3},
            {title: "Titel4", gameId: 4}]
    },
    mounted: function () {
        axios({
            method: 'get',
            url: 'users/notifications'
        }).then(function (response) {
            invites.listOfInvites = response.data
        })

    }
})
var stats = new Vue({
    el: '#stats',
    data: {
        username: 'Bendix',
        points: 1234
    }, //TODO remove comment if API does actually return something
    mounted: function () {
        axios({
            method: 'get',
            url: 'users'
        }).then(function (response) {
            stats.points = response.data.points;
            stats.username = response.data.username;
        })

    }
})

var games = new Vue({
    el: '#games',
    data: {
        listOfGames: [{title:"myCoolGame", type:"Matrix", roundsPlayed:12, gameId: 1},
            {title:"myRatherAmusingGame", type:"Binary", roundsPlayed:42, gameId: 2},
            {title:"myUnderwhelmingGame", type:"Matrix", roundsPlayed:53, gameId: 3},
            {title:"myDisappointingGame", type:"Matrix", roundsPlayed:256, gameId: 4}]
    },
    mounted: function () {
        axios({
            method: 'get',
            url: 'games'
        }).then(function (response) {
            games.listOfGames = response.data
        })
    }
})

var leaderboard = new Vue({
    el: '#leaderboard',
    data: {
        playerList: [{username: "bendix", points: 1000, place: 1},
            {username: "fred", points: 500, place: 2},
            {username: "olaf", points: 250, place: 3},
            {username: "dieter", points: 0, place: 4}
        ]
    },
    mounted: function () {
        axios({
            method: 'get',
            url: 'users/leaderboard'
        }).then(function (response) {
            leaderboard.playerList = response.data
        })
    }
})


var daily = new Vue({
    el: "#daily",
    data: {
        daily: {
            title: 'Töte drei piraten an diesem Tag(Ich hab gerade eine Runde Blackwake gespielt)',
            points: 500,
            finished: true
        }
    },
    mounted: function () {
        axios({
            method: 'get',
            url: 'users/daily'
        }).then(function (response) {
            daily.daily = response.data
        })
    }
})
