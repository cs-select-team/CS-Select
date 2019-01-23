Vue.component('active-games-display', {
    props: ['game', 'invite', 'terminate'],
    template: '<div class="container">' +
        '       <div class="container">' +
        '           <div class="row">' +
        '               <div class="col"><div>{{ game.title }}</div>' +
        '                   <div>{{ game.type }}</div>' +
        '                   <div>{{ game.termination  }}</div>' +
        '               </div>' +
        '               <div class="col">' +
        '                   <input type="button" class="btn btn-secondary float-right btn-space" :value="terminate"/>' +
        '                   <input type="button" class="btn btn-primary float-right btn-space" :value="invite"/>' +
        '               </div>' +
        '            </div>' +
        '       </div>' +
        '      </div>'
});

Vue.component('terminated-games-display', {
    props: [''],
    template: ''
});

Vue.component('control-element', {
    props: [''],
    template: ''
});

Vue.component('stats-display', {
    props: [''],
    template: ''
});

var activeGames = new Vue({
    el: "#active",
    data: {
        listOfGames: [{title:"myCoolGame", type:"Matrix", termination:0, gameId: 1},
            {title:"myRatherAmusingGame", type:"Binär", termination:0, gameId: 2},
            {title:"myUnderwhelmingGame", type:"Matrix", termination:0, gameId: 3},
            {title:"myDisappointingGame", type:"Matrix", termination:0, gameId: 4}]
    }
});

var terminatedGames = new Vue({
    el: "#terminated"
});

var control = new Vue({
    el: "#controls"
});

var stats = new Vue({
    el: "#stats"
});