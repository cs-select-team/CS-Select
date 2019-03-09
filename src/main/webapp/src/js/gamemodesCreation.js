/**
 * to implement a new gamemode in the creation you need to create a new component that takes a configString prop
 * and that raises the update-config-str event if the config string changes
 */
Vue.component('gamemode-config-binarySelect', {
    props: ['configString'],
    template: `<div></div>`,

    mounted(newVal) {
        this.$emit('update-config-str', "binarySelect")
    }
});
Vue.component('gamemode-config-matrixSelect', {
    props: ['config-string'],
    data() {
        return {
            conf: this.isValidConfig(this.configString)?this.configString:'matrixSelect,0,0,0'
        }
    },
    template:
        `<div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">{{localisation.numberOfFeatures}}</span>
                </div>
                <input type="number" min="0" class="form-control" v-model="numberOfFeatures">
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">{{localisation.minNumberOfFeatures}}</span>
                </div>
                <input type="number" min="0" :max="max" class="form-control" v-model="min"></div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">{{localisation.maxNumberOfFeatures}}</span>
                </div>
                <input type="number" :min="min" :max="numberOfFeatures" class="form-control" v-model="max">
            </div>
        </div>`,
    methods: {
        getArgByIndex(index) {
            const args = this.conf.split(',');

            return args[index + 1];
        },
        setArgByIndex(index, newVal) {
            const arguments = this.conf.split(',');
            arguments[index + 1] = newVal;
            this.conf = arguments.join(',');

        },
        isValidConfig(string) {
            const args = string.split(',');
            if (args[0] !== 'matrixSelect') return false;
            return args.length === 4;

        }
    },
    computed: {
        numberOfFeatures: {
            get() {
                return parseInt(this.getArgByIndex(0), 10)
            },
            set(newVal) {
                this.setArgByIndex(0, newVal)
            }
        },
        min: {
            get() {
                return parseInt(this.getArgByIndex(1), 10)
            },
            set(newVal) {
                this.setArgByIndex(1, newVal)
            }
        },
        max: {
            get() {
                return parseInt(this.getArgByIndex(2), 10)
            },
            set(newVal) {
                this.setArgByIndex(2, newVal)
            }
        }
    },
    watch: {
        conf(newVal) {
            this.$emit('update-config-str', this.conf)
        }
    }
});
Vue.component('gamemode-config', {
    props: ['gamemode-config-str'],
    data() {
        return {
            listOfGamemodes: [{title: "Matrix Select", value: 'matrixSelect'}, {title: "Binary Select", value: 'binarySelect'}]
        }
    },
    methods: {
        updateConfString(newVal) {
            this.$emit('update-config-str', newVal);
        }
    },
    // language=HTML
    template:
        `<div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">{{localisation.gamemode}}</span>
            </div>
            <select class="custom-select" v-model="currentGm">
                <option v-for="(gm, index) in listOfGamemodes" v-bind:key="index" v-bind:config-string="gamemodeConfigStr"
                        :value="gm.value">{{gm.title}}
                </option>
            </select>
            <component v-bind:is="componentName" v-bind:config-string="gamemodeConfigStr"
                       v-on:update-config-str="updateConfString">
            </component>
        </div>`,
    computed: {
        componentName() {
            return 'gamemode-config-' + this.currentGm;
        },
        currentGm: {
            get() {
                if (this.gamemodeConfigStr === '') return 'binarySelect';
                return this.gamemodeConfigStr.split(',')[0];
            },
            set(newVal) {
                this.updateConfString(newVal + ',')
            }
        }
    }
});