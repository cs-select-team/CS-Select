/**
 * to implement a new gamemode in the creation you need to create a new component that takes a configString prop
 * and that raises the update-config-str event if the config string changes
 */
Vue.component('gamemode-config-binarySelect', {
    props: ['configString'],
    template: '<div></div>',

    mounted: function (newVal) {
        this.$emit('update-config-str', "binarySelect")
    }
});
Vue.component('gamemode-config-matrixSelect', {
    props: ['config-string'],
    data: function () {
        return {
            conf: this.isValidConfig(this.configString)?this.configString:'matrixSelect,0,0,0'
        }
    },
    template: '<div>\n' +
        '  <div class="input-group mb-3">\n' +
        '  <div class="input-group-prepend">\n' +
        '    <span class="input-group-text" >{{localisation.numberOfFeatures}}</span>\n' +
        '  </div>\n' +
        '  <input type="number" min="0" class="form-control"  v-model="numberOfFeatures">' +
        '</div>' +
        '<div class="input-group mb-3">\n' +
        '  <div class="input-group-prepend">\n' +
        '    <span class="input-group-text">{{localisation.maxNumberOfFeatures}}</span>\n' +
        '  </div>\n' +
        '  <input type="number" :min="min" :max="numberOfFeatures" class="form-control"  v-model="max">\n' +
        '</div>\n' +
        '<div class="input-group mb-3">\n' +
        '  <div class="input-group-prepend">\n' +
        '    <span class="input-group-text">{{localisation.minNumberOfFeatures}}</span>\n' +
        '  </div>\n' +
        '  <input type="number" min="0" :max="max" class="form-control"  v-model="min">\n' +
        '</div>\n' +
        '</div>',

    methods: {
        getArgByIndex: function (index) {
            var args = this.conf.split(',');

            return args[index + 1];
        },
        setArgByIndex: function (index, newVal) {
            var arguments = this.conf.split(',');
            arguments[index + 1] = newVal;
            this.conf = arguments.join(',');

        },
        isValidConfig: function (string) {
            var args = string.split(',');
            if (args[0] !== 'matrixSelect') return false;
            return args.length === 4;

        }
    },
    computed: {
        numberOfFeatures: {
            get: function () {
                return parseInt(this.getArgByIndex(0))
            },
            set: function (newVal) {
                this.setArgByIndex(0, newVal)
            }
        },
        min: {
            get: function () {
                return parseInt(this.getArgByIndex(1))
            },
            set: function (newVal) {
                this.setArgByIndex(1, newVal)
            }
        },
        max: {
            get: function () {
                return parseInt(this.getArgByIndex(2))
            },
            set: function (newVal) {
                this.setArgByIndex(2, newVal)
            }
        }
    },
    watch: {
        conf: function (newVal) {
            this.$emit('update-config-str', this.conf)
        }
    }
});
Vue.component('gamemode-config', {
    props: ['gamemode-config-str'],
    data: function () {
        return {
            listOfGamemodes: [{title: "Matrix Select", value: 'matrixSelect'}, {title: "Binary Select", value: 'binarySelect'}]
        }
    },
    methods: {
        updateConfString: function (newVal) {
            this.$emit('update-config-str', newVal);
        }
    },
    // language=HTML
    template: '<div  class="input-group mb-3">' +
        '<div class="input-group-prepend">\n' +
        '    <span class="input-group-text">{{localisation.gamemode}}</span>\n' +
        '  </div>' +
        '<select class="custom-select" v-model="currentGm">' +
        '<option v-for="(gm, index) in listOfGamemodes" v-bind:key="index"' +
        '   v-bind:config-string="gamemodeConfigStr" :value="gm.value">{{gm.title}}</option>' +
        '</select> ' +
        '<component v-bind:is="componentName" v-bind:config-string="gamemodeConfigStr" v-on:update-config-str="updateConfString">' +
        '</component>' +
        '</div>',
    computed: {
        componentName: function () {
            return 'gamemode-config-' + this.currentGm;
        },
        currentGm: {
            get: function() {
                if (this.gamemodeConfigStr == '') return 'binarySelect'
                return this.gamemodeConfigStr.split(',')[0];
            },
            set: function (newVal) {
                this.updateConfString(newVal + ',')
            }
        }
    }
})