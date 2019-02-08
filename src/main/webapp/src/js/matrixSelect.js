Vue.component('matrix-row', {
    props: ['feature-list'],
    template: '<div class="mt-2 row" :title="localisation.matrixSelectHelp">\n' +
        '            <feature-box  v-on:toggled="toggled" class="col p-0 mr-2" v-for="feature in featureList" ' +
        '                           v-bind:key="feature.id"' +
        '                           v-bind:feature="feature"></feature-box>' +
        '        </div>',
    methods: {
        toggled: function (newVal, oldVal) {
            this.$emit("toggled", newVal, oldVal)
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
    computed: {
        col: function () {
            return Math.floor(document.getElementsByClassName("col-10")[0].offsetWidth / 350);
        },
        row: function () {
            return Math.ceil(this.options.numberOfFeatures / this.col);
        }
    },
    template: '        <div ref="matrix">' +
        '<matrix-row  v-on:toggled="toggled" v-for="i in [...Array(row).keys()]"\n' +
        '                    v-bind:key="i"\n' +
        '                    v-bind:feature-list="featureList.slice(i * col, (i+1) * col)">\n' +
        '        </matrix-row></div>',
    methods: {
        toggled: function(newVal, oldVal) {
            if (newVal && !oldVal) this.count++;
            if (!newVal && oldVal) this.count--;
            if (this.count >= this.options.minSelect && this.count <= this.options.maxSelect) {
                this.$emit("done", true)
            }
            else this.$emit("done", false)
        }
    }
})
