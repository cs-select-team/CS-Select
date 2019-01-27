Vue.component('binarySelect', {
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
        'feature1.toggled': function (val, oldVal) {
            if(val)
            this.nextRound()
        },
        'feature2.toggled': function (val, oldVal) {
            if(val)
            this.nextRound()
        }
    },

    methods: {
        nextRound: function () {
            this.currentRound++;
            if (this.currentRound < this.maxRound) {
                this.feature1 = this.featurelist[2 * this.currentRound];
                this.feature2 = this.featurelist[2 * this.currentRound + 1];
            } else {
                this.$emit('done', true);
                this.disabled = true
            }
        }
    },
    mounted: function() {
        this.currentRound= -1;
        this.nextRound();
        this.maxRound = this.options.maxRound;
    },

    template: '<div class="row" v-bind:class="{\'disabled-div\': disabled}">\n' +
        '                                <div class="col-sm" >\n' +
        '                                        <feature-box v-bind:feature="feature1"></feature-box>\n' +
        '                                </div>\n' +
        '                                <div class="col-sm">\n' +
        '                                        <feature-box v-bind:feature="feature2"></feature-box>\n' +
        '                                </div>\n' +
        '                        </div>'
})
