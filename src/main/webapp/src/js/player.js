Vue.component('game-display', {
    props: ['game', 'play'],
    template: '<div class="card-body">' +
        '                <div>{{ game.title }}</div>' +
        '                <div>{{ game.type }}</div>' +
        '                <div>{{ game.roundsPlayed  }}</div>' +
        '                <input type="button" class="btn btn-primary float-right" :value="play"/>' +
        '            </div>'
});

Vue.component('leaderboard-element', {
    props: ['place', 'username', 'points'],
    template: '<tr>\n' +
        '                    <th>{{ place }}</th>\n' +
        '                    <th>{{ username }}</th>\n' +
        '                    <th>{{ points }}</th>\n' +
        '                </tr>'
})

var games = new Vue({
    el: '#games',
    data: {
        listOfGames: []
    },
    mounted: function () {
        axios({
            method: 'get',
            url: 'games'
        }).then(function (response) {
            this.listOfGames = JSON.parse(response.data)
        })
    }
})

var leaderboard = new Vue({
    el: '#leaderboard',
    data: {
        playerList: [{username: "bendix", points:1000, place:1},
            {username: "fred", points:500, place:2},
            {username: "olaf", points:250, place:3},
            {username: "dieter", points:0, place:4}
            ]
    },
    mounted: function () {
        //axios({
        //    method: 'get',
        //    url: 'users/leaderboard'
        //}).then(function (response) {
        //    this.playerList = JSON.parse(response.data)
        //})
    }
})