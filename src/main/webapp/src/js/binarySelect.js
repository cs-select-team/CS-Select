Vue.component('BinarySelect', {
    props: ['feature-list', 'options'],
    data: function() {
        return {
            feature1: {},
            feature2: {},
            currentRound: -1,
            maxRound: 5,
            disabled: false
        }
    },
    watch: {
    },

    methods: {
        nextRound: function () {
            this.currentRound++;
            if (this.currentRound < this.maxRound) {
                this.feature1 = this.featureList[2 * this.currentRound];
                this.feature2 = this.featureList[2 * this.currentRound + 1];
            } else {
                this.$emit('done', true);
                this.disabled = true
            }
        },
        uselessToggle: function(newVal) {
            if (this.feature1.useless && this.feature2.useless) { // in case both features are selected as useless
                this.nextRound();
                var alert = {message: this.localisation.binaryRoundSkipped, type: 1}
                this.$emit("clear-alerts")
                this.$emit("add-alert", alert)
            }
        },
        toggled: function (newVal, oldVal) {
            if(newVal){
                this.nextRound()
                this.$emit("clear-alerts")
            }

        }

    },
    mounted: function() {
        this.$emit('add-alert', {message: this.localisation.binarySelectHelp, type: 2})
        this.currentRound= -1;
        this.nextRound();
    },

    template: '<div class="row" :title="binarySelectHelp" v-bind:class="{\'disabled-div\': disabled}">\n' +
        '                                <div class="col-sm" >\n' +
        '                                        <feature-box v-bind:feature="feature1" v-on:toggled="toggled" v-on:useless-toggle="uselessToggle"></feature-box>\n' +
        '                                </div>\n' +
        '                                <div class="col-sm">\n' +
        '                                        <feature-box v-bind:feature="feature2" v-on:toggled="toggled" v-on:useless-toggle="uselessToggle"></feature-box>\n' +
        '                                </div>\n' +
        '                        </div>'
})
