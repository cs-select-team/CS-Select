Vue.component('termination-config', {
    props: ['termination-config-str'],
    data: function () {
        return {
            listOfTerminations: [{title: "Time termination", value: "time"},
                {title: "Rounds termination", value: "rounds"},
                {title: "Composite termination", value: "composite"}]
        }
    },
    template: "<div class=\"input-group mb-3\">" +
        "      <div class=\"input-group-prepend\">\n" +
        "    <span class=\"input-group-text\" >{{localisation.termination}}</span>\n" +
        "  </div>     " +
        "<select class='custom-select' v-model='currentTermination' >\n" +
        "    <option v-for='(term,index) in listOfTerminations' v-bind:key='index'" +
        "               :value='term.value'>{{term.title}}</option>\n" +
        "  </select>" +
        "   <component v-bind:is='componentName' v-bind:termination-config-str='terminationConfigStr'" +
        "               v-on:update-termination='updateTermination' v-bind:list-of-possible-terminations='listOfTerminations.slice(0,2)'></component>" +
        "</div>",
    methods: {
        updateTermination: function (newVal) {
            this.$emit('update-termination-str', newVal);
        }
    },
    watch: {
        terminationConfigStr: function(newVal) {
            this.$forceUpdate()
        }
    }
    ,

    computed: {
        componentName: function () {

            return "termination-config-" + this.currentTermination
        },
        currentTermination: {
            get: function() {
                if (this.terminationConfigStr.split(',').length < 2) {
                    return this.terminationConfigStr.split(':')[0];
                } else {
                    return 'composite'; // special case for composite termination
                }
            },
            set: function (newVal) {
                if (newVal == 'composite') this.$emit('update-termination-str', 'rounds:1,rounds:1');
                else this.$emit('update-termination-str', newVal + ':');
            }
        }
    }
});
Vue.component('termination-config-composite', {
    props: ['list-of-possible-terminations', 'termination-config-str'],
    data: function () {
        return {
            terminations: [],
            currentTermination: {},
            terminationStrings: []
        }
    },
    methods: {
        addTermination: function (term) {
            this.terminations.push(term);
            this.terminationStrings.push('')
        },
        removeTerminationByIndex: function (index) {
            this.terminations.splice(index, 1);
            this.terminationConfigString.splice(index, 1);
        },
        addTerminationStringByIndex: function (event, index) {
            this.terminationStrings[index] = event;
            this.$emit('update-termination', this.terminationStrings.join(','))
        }

    },
    mounted: function () {
        var self = this;
        Vue.nextTick(function() {
            self.terminationStrings = self.terminationConfigStr.split(',');
            self.terminationStrings.forEach(function (value, index) {
                var termination = {};
                self.listOfPossibleTerminations.forEach(function (term) {
                    if (term.value === value.split(':')[0]) termination = term;
                })
                self.terminations.push(termination)
            })
        })


    },
    watch: {
        terminationConfigStr: function(newVal) {
            var self = this
            this.terminations = [];
            self.terminationStrings = newVal.split(',');
            self.terminationStrings.forEach(function (value, index) {
                var termination = {};
                self.listOfPossibleTerminations.forEach(function (term) {
                    if (term.value === value.split(':')[0]) termination = term;
                })
                self.terminations.push(termination)
            })
        }
    },

    template: '<div>' +
        '     <div class="input-group mb-3">\n' +
        '  <select class="custom-select" v-model="currentTermination">\n' +
        '    <option v-for="(termination, index) in listOfPossibleTerminations" v-bind:key="index"' +
        '               :value="termination" >{{termination.title}}</option>' +
        '  </select>\n' +
        ' <div class="input-group-append">\n' +
        '    <button class="btn btn-outline-secondary" type="button" v-on:click="addTermination(currentTermination)">Add</button>\n' +
        '  </div>' +
        '</div>' +
        '<div v-for="(term, index) in terminations" class="row" >' +
        '<div class="col-10">' +
        '<component  v-bind:key="index" ' +
        '           v-bind:is="\'termination-config-\' + term.value" v-bind:termination-config-str="terminationStrings[index]"' +
        '           v-on:update-termination="addTerminationStringByIndex($event, index)"></component></div>' +
        '<div class="col-2"> ' +
        '<button type="button" class="close" v-on:click="removeTerminationByIndex(index)">\n' +
        '          <span aria-hidden="true">&times;</span>\n' +
        '        </button>\n' +
        '</div>' +
        '</div>' +
        '</div>'

})

Vue.component('termination-config-rounds', {
    props: ['termination-config-str'],
    watch: {
        terminationConfigStr: function (newVal) {
            this.$forceUpdate()
        }
    },
    methods: {
        isValidConf: function (string) {
            var args = string.split(':');
            if (args[0] !== 'rounds') return false;
            if (args.length !== 2) return false;
        }
    },
    computed: {
        number: {
            get: function () {
                if (this.terminationConfigStr == undefined || this.terminationConfigStr === '') this.$emit('update-termination', 'rounds:1');
                return parseInt(this.terminationConfigStr.split(':')[1])
            },
            set: function (newVal) {
                var args = this.terminationConfigStr.split(':');
                args[1] = newVal;

                this.$emit('update-termination', args.join(':'))
            }
        }
    },
    template: '<div class="input-group mb-3">\n' +
        '  <div class="input-group-prepend">\n' +
        '    <span class="input-group-text">{{localisation.rounds}}</span>\n' +
        '  </div>\n' +
        '  <input type="number" min="0" class="form-control" v-model="number">\n' +
        '</div>'

});

Vue.component('termination-config-time', {
    props: ['termination-config-str'],
    watch: {
        terminationConfigStr: function (newVal) {
            this.$forceUpdate();

        }
    },
    methods: {
        isValidConf: function (string) {
            var args = string.split(':');
            if (args[0] !== 'time') return false;
            if (args.length !== 2) return false;
        }
    },
    computed: {
        date: {
            get: function() {
                var args = this.terminationConfigStr.split(':');
                var date;
                if (args[1] === '') {
                    date = Date.now();
                } else {
                     date = new Date(Number(args[1]));
                }
                return date.toString();
            },
            set: function (newVal) {
                var args = this.terminationConfigStr.split(':');
                args[1] = new Date(newVal).getTime();
                this.$emit('update-termination', args.join(':'))
            }
        }
    },
    template: '<div class="input-group mb-3">\n' +
        '  <div class="input-group-prepend">\n' +
        '    <span class="input-group-text" >{{localisation.endDate}}</span>\n' +
        '  </div>\n' +
        '  <date-picker v-bind:config="{format: \'DD/MM/YYYY HH:mm\'}" v-model="date"></date-picker>' +
        '</div>'

});