Vue.component('matrix-row', {
    props: ['feature-list'],
    template:
        `<div class="mt-2 row">
            <feature-box v-on:toggled="toggled" v-on:useless-toggle="uselessToggle" class="col p-0 mr-2"
                v-for="feature in featureList" v-bind:key="feature.id" v-bind:feature="feature">
            </feature-box>
        </div>`,
    methods: {
        toggled(newVal, oldVal) {
            this.$emit('toggled', newVal, oldVal);
        },
        uselessToggle(newVal, toggled) {
            this.$emit('useless-toggle', newVal, toggled);
        }
    }
});
Vue.component('MatrixSelect', {
    props: ['feature-list', 'options'],
    data() {
        return {
            count: 0 // number of selected features
        };
    },
    mounted() { // giving the player information about this game
        this.$emit('add-alert', {message: this.localisation.matrixSelectHelp, type: 2});
        const messageString = this.localisation.matrixSelectMin + this.options.minSelect + this.localisation.matrixSelectMax + this.options.maxSelect + this.localisation.matrixSelectEnd;
        const alert = {message: messageString, type: 2};
        this.$emit('add-alert', alert);

    },
    computed: {
        col() {
            return Math.floor(document.getElementsByClassName('col-10')[0].offsetWidth / 350);
        },
        row() {
            return Math.ceil(this.options.numberOfFeatures / this.col);
        }
    },
    template:
        `<div ref="matrix" id="matrixSelect">
            <matrix-row v-on:toggled="toggled" v-on:useless-toggle="uselessToggle" v-for="i in [...Array(row).keys()]"
                        v-bind:key="i"
                        v-bind:feature-list="featureList.slice(i * col, (i+1) * col)">
            </matrix-row>
        </div>`,
    methods: {
        toggled(newVal, oldVal) {
            if (newVal && !oldVal) {
                this.count++;
            }
            if (!newVal && oldVal) {
                this.count--;
            }
            this.checkDone();
        },
        uselessToggle(newVal, toggled) {
            if (newVal && toggled) {
                this.count--;
            }
            this.checkDone();
        },
        checkDone() {
            if (this.count >= this.options.minSelect && this.count <= this.options.maxSelect) {
                this.$emit('done', true);
            }
            else {
                this.$emit('done', false);
            }
        }
    }
});
