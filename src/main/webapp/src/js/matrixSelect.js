Vue.component('matrix-row', {
    props: ['feature-list'],
    template: '<div class="mt-2 row" :title="localisation.matrixSelectHelp">\n' +
        '            <feature-box  v-on:toggled="toggled" v-on:useless-toggle="uselessToggle" class="col p-0 mr-2" v-for="feature in featureList" ' +
        '                           v-bind:key="feature.id"' +
        '                           v-bind:feature="feature"></feature-box>' +
        '        </div>',
    methods: {
        toggled: function (newVal, oldVal) {
            this.$emit("toggled", newVal, oldVal)
        },
        uselessToggle: function (newVal, toggled) {
            this.$emit('useless-toggle', newVal, toggled)
        }
    }
})
Vue.component('MatrixSelect', {
    props: ['feature-list', 'options'],
    data: function() {
        return {
            count: 0 // number of selected features
        }
    },
    mounted: function() { // giving the player information about this game
        var messageString = this.localisation.matrixSelectMin + this.options.minSelect + this.localisation.matrixSelectMax + this.options.maxSelect + this.localisation.matrixSelectEnd
        var alert = {message: messageString, type: 2}
        this.$emit("add-alert", alert)

    },
    computed: {
        col: function () {
            return Math.floor(document.getElementsByClassName("col-10")[0].offsetWidth / 350);
        },
        row: function () {
            return Math.ceil(this.options.numberOfFeatures / this.col);
        }
    },
    template: '        <div ref="matrix">' +
        '<matrix-row  v-on:toggled="toggled" v-on:useless-toggle="uselessToggle" v-for="i in [...Array(row).keys()]"\n' +
        '                    v-bind:key="i"\n' +
        '                    v-bind:feature-list="featureList.slice(i * col, (i+1) * col)">\n' +
        '        </matrix-row></div>',
    methods: {
        toggled: function(newVal, oldVal) {
            if (newVal && !oldVal) this.count++;
            if (!newVal && oldVal) this.count--;
            this.checkDone();
        },
        uselessToggle: function (newVal, toggled) {
            if (newVal && toggled) this.count--;
            this.checkDone();
        },
        checkDone: function () {
            if (this.count >= this.options.minSelect && this.count <= this.options.maxSelect) {
                this.$emit("done", true)
            }
            else this.$emit("done", false)
        }
    }
})
