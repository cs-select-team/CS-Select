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
        toggled: function (newVal, oldVal) {
            if(newVal)
                this.nextRound()
        }

    },
    mounted: function() {
        this.currentRound= -1;
        this.nextRound();
    },

    template: '<div class="row" v-bind:class="{\'disabled-div\': disabled}">\n' +
        '                                <div class="col-sm" >\n' +
        '                                        <feature-box v-bind:feature="feature1" v-on:toggled="toggled"></feature-box>\n' +
        '                                </div>\n' +
        '                                <div class="col-sm">\n' +
        '                                        <feature-box v-bind:feature="feature2" v-on:toggled="toggled"></feature-box>\n' +
        '                                </div>\n' +
        '                        </div>'
})
