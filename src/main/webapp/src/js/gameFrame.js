var gameFrame = new Vue({
    el: '#gameFrame',
    data: {
        featureList: [],
        options: {},
        gameType: '',
        forceUpdate: 1,
        counter: 0,
        buttonState: true,
        points: undefined,
        alerts: [] // alerts are cleared on nextRound
    },
    mounted: function () {
      this.getNextRound();
    },
    methods: {
        addAlert: function(alert) {
          this.alerts.push(alert);
        },
        clearAlerts: function() {
            this.alerts = []
        },
        unlockButton: function (done) {
            this.buttonState = !done
        },
        getSelectedFeaturesById: function() {
            idList = [];
            for (var i = 0; i < this.featureList.length; i++) {
                if (this.featureList[i].toggled) idList.push(this.featureList[i].id)
            }
            return idList;
        },
        getUselessFeaturesById: function() {
            idList = [];
            for (var i = 0; i < this.featureList.length; i++) {
                if (this.featureList[i].useless) idList.push(this.featureList[i].id)
            }
            return idList;
        },
        sendResults: function () {
            if (!this.buttonState) { // truly only send if the game is finished

                axios({
                    method: 'post',
                    url: 'games/' + localStorage.getItem("gameId") + "/play",
                    data: {
                        useless: JSON.stringify(this.getUselessFeaturesById()),
                        selected: JSON.stringify(this.getSelectedFeaturesById())
                    }
                }).then(function (response) {
                    gameFrame.points = response.data
                    gameFrame.getNextRound();
                })

            }
        },
        skip: function() {
            axios({
                method: 'post',
                url: 'games/' + localStorage.getItem("gameId") + "/skip",
                data: {
                    useless: JSON.stringify(this.getUselessFeaturesById())
                }
            }).then(function (value) {
                gameFrame.getNextRound();
            })

        },
        getNextRound: function() {
            this.clearAlerts();
            axios({
                method: 'post',
                url: 'games/' + localStorage.getItem("gameId") + "/start"
            }).then (function (response) {
                if (response.status == 204) {
                    gameFrame.quit()
                }
                gameFrame.featureList = response.data.listOfFeatures;
                gameFrame.featureList.forEach(function (feature) {
                    // add necessary properties
                    feature.toggled = false;
                    feature.useless = false;
                })
                gameFrame.options = response.data.options;
                gameFrame.gameType = response.data.gameType;
                gameFrame.forceUpdate++;
                gameFrame.buttonState = true;
                axios('users/streak').then(function (response) {
                    gameFrame.counter = response.data
                })
            })


        },
        quit: function() {
            localStorage.setItem("gameTerminated", true);
            window.location.href = 'player.jsp'
        },

    }


})