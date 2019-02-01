Vue.component('matrix-row', {
    props: ['feature-list'],
    template: '<div class="mt-2 row">\n' +
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
            return Math.sqrt(this.options.numberOfFeatures);
        },
        row: function () {
            return Math.sqrt(this.options.numberOfFeatures);
        }
    },
    template: '        <div >' +
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
