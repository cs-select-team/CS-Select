/**
 * to implement a new gamemode in the creation you need to create a new component that takes a configString prop
 * and that raises the update-config-str event if the config string changes
 */
Vue.component('gamemode-config-BinarySelect', {
    props: ['configString'],
    template: '<div></div>',

    mounted: function (newVal) {
        this.$emit('update-config-str', "BinarySelect:")
    }
});
Vue.component('gamemode-config-MatrixSelect', {
    props: ['config-string'],
    data: function () {
        return {
            conf: this.configString.split(':')[0] != "MatrixSelect" ? "MatrixSelect:num~0-min~0-max~0" : this.configString
        }
    },
    template: '<div>\n' +
        '  <div class="input-group mb-3">\n' +
        '  <div class="input-group-prepend">\n' +
        '    <span class="input-group-text" >Number of Features</span>\n' +
        '  </div>\n' +
        '  <input type="number" min="0" class="form-control" placeholder="Number of Features" v-model="numberOfFeatures">' +
        '</div>' +
        '<div class="input-group mb-3">\n' +
        '  <div class="input-group-prepend">\n' +
        '    <span class="input-group-text">Max</span>\n' +
        '  </div>\n' +
        '  <input type="number" :min="min" :max="numberOfFeatures" class="form-control" placeholder="Maximum selected features" v-model="max">\n' +
        '</div>\n' +
        '<div class="input-group mb-3">\n' +
        '  <div class="input-group-prepend">\n' +
        '    <span class="input-group-text">Min</span>\n' +
        '  </div>\n' +
        '  <input type="number" min="0" :max="max" class="form-control" placeholder="Minimum selected features" v-model="min">\n' +
        '</div>\n' +
        '</div>',

    methods: {
        getArgByName: function (name) {
            var arguments = this.conf.split(':')[1];
            var args = arguments.split('-');
            var returnVal = "";
            args.forEach(function (value) {
                var type = value.split('~')[0];
                if (type == name) {
                    returnVal = value.split('~')[1];
                }
            });
            return returnVal;
        },
        setArgByName: function (name, newVal) {
            var newConf = '';
            var arguments = this.conf.split(':');
            newConf += arguments[0] + ':';
            var args = arguments[1].split('-');
            args.forEach(function (value, index) {
                var type = value.split('~')[0];
                if (type == name) {
                    newConf += value.split('~')[0] + '~';
                    newConf += newVal;
                } else newConf += value;
                if (index < args.length - 1) newConf += "-"
            });
            this.conf = newConf
        }
    },
    computed: {
        numberOfFeatures: {
            get: function () {
                return parseInt(this.getArgByName("num"))
            },
            set: function (newVal) {
                this.setArgByName("num", newVal)
            }
        },
        min: {
            get: function () {
                return parseInt(this.getArgByName("min"))
            },
            set: function (newVal) {
                this.setArgByName("min", newVal)
            }
        },
        max: {
            get: function () {
                return parseInt(this.getArgByName("max"))
            },
            set: function (newVal) {
                this.setArgByName("max", newVal)
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
            listOfGamemodes: [{title: "Matrix Select", value: 'MatrixSelect'}, {title: "Binary Select", value: 'BinarySelect'}],
            currentGm: 'BinarySelect'
        }
    },
    methods: {
        updateConfString: function (newVal) {
            this.$emit('update-config-str', newVal);
        }
    },
    // language=HTML
    template: '<div><select class="custom-select" v-model="currentGm">' +
        '<option v-for="(gm, index) in listOfGamemodes" v-bind:key="index"' +
        '   v-bind:config-string="gamemodeConfigStr" :value="gm.value">{{gm.title}}</option>' +
        '</select> ' +
        '<component v-bind:is="componentName" v-bind:config-string="gamemodeConfigStr" v-on:update-config-str="updateConfString">' +
        '</component>' +
        '</div>',
    computed: {
        componentName: function () {
            return 'gamemode-config-' + this.currentGm;
        }
    }
})