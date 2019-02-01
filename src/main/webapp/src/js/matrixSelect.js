Vue.component('matrix-row', {
    props: ['feature-list'],
    template: '<div class="mt-2 row">\n' +
        '            <feature-box  v-on:toggled="toggled" class="col p-0 mr-2" v-for="feature in featureList" ' +
        '                           v-bind:key="feature.id"' +
        '                           v-bind:feature="feature"></feature-box>' +
        '        </div>',
    methods: {
        toggled: function (newVal, oldVal) {
            this.$emit("toggled", oldVal, newVal)
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
    template: '        <div >' +
        '<matrix-row  v-on:toggled="toggled" v-for="i in [...Array(options.row).keys()]"\n' +
        '                    v-bind:key="i"\n' +
        '                    v-bind:feature-list="featureList.slice(i * options.col, (i+1) * options.col)">\n' +
        '        </matrix-row></div>',
    methods: {
        toggled: function(newVal, oldVal) {
            if (newVal && !oldVal) this.count++;
            if (!newVal && oldVal) this.count--;
            if (this.count >= this.options.num) this.$emit("done", true)
            else this.$emit("done", false)
        }
    }
})
