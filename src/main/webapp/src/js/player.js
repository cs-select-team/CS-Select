Vue.component('game-display', {
    props: ['game', 'play'],
    template: '<div class="card-body">' +
        '                <div>{{ game.title }}</div>' +
        '                <div>{{ game.type }}</div>' +
        '                <div>{{ game.roundsPlayed  }}</div>' +
        '                <input type="button" class="btn btn-primary float-right" :value="play"/>' +
        '            </div>'
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