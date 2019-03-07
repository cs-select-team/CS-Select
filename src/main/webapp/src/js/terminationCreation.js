Vue.component('termination-config', {
    props: ['termination-config-str'],
    data() {
        return {
            listOfTerminations: []
        }
    },
    template:
        `<div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">{{localisation.termination}}</span>
            </div>
            <select class='custom-select' v-model='currentTermination'>
                <option v-for='(term,index) in listOfTerminations' v-bind:key='index' :value='term.value'>{{term.title}}
                </option>
            </select>
            <component v-bind:is='componentName' v-bind:termination-config-str='terminationConfigStr'
                       v-on:update-termination='updateTermination'
                       v-bind:list-of-possible-terminations='listOfTerminations.slice(2,4)'>
            </component>
        </div>`,
    methods: {
        updateTermination(newVal) {
            this.$emit('update-termination-str', newVal);
        }
    },
    watch: {
        terminationConfigStr(newVal) {
            this.$forceUpdate()
        }
    }
    ,
    mounted() {
        this.listOfTerminations = [{title: this.localisation.compositeTermination, value: "composite"},
            {title: this.localisation.organiserTermination, value:'organiser'},
            {title: this.localisation.timeTermination, value: "time"},
            {title: this.localisation.roundsTermination, value: "rounds"}
            ]
    },

    computed: {
        componentName() {

            return "termination-config-" + this.currentTermination
        },
        currentTermination: {
            get() {
                if (this.terminationConfigStr.split(',').length < 2) {
                    return this.terminationConfigStr.split(':')[0];
                } else {
                    return 'composite'; // special case for composite termination
                }
            },
            set(newVal) {
                if (newVal === 'composite') this.$emit('update-termination-str', 'rounds:1,time:');
                else this.$emit('update-termination-str', newVal + ':');
            }
        }
    }
});
Vue.component('termination-config-organiser', {
    template:
        `<div>{{localisation.organiserTerminationInfo}}</div>`
});
Vue.component('termination-config-composite', {
    props: ['list-of-possible-terminations', 'termination-config-str'],
    data() {
        return {
            terminations: [],
            currentTermination: {},
            terminationStrings: []
        }
    },
    methods: {
        addTermination(term) {
            if (!this.terminations.includes(term)) {
                this.terminationStrings.push(term.value + ':');
                this.terminations.push(term);
            }
            this.$emit('update-termination', this.terminationStrings.join(','))
        },
        removeTerminationByIndex(index) {
            this.terminations.splice(index, 1);
            this.terminationStrings.splice(index, 1);
            this.$emit('update-termination', this.terminationStrings.join(','))
        },
        addTerminationStringByIndex(event, index) {
            this.terminationStrings[index] = event;
            this.$emit('update-termination', this.terminationStrings.join(','))
        }

    },
    mounted() {
        const self = this;
        Vue.nextTick(function() {
            self.terminationStrings = self.terminationConfigStr.split(',');
            self.terminationStrings.forEach(function (value, index) {
                let termination = {};
                self.listOfPossibleTerminations.forEach(function (term) {
                    if (term.value === value.split(':')[0]) termination = term;
                });
                self.terminations.push(termination)
            })
        })


    },
    watch: {
        terminationConfigStr(newVal) {
            const self = this;
            this.terminations = [];
            self.terminationStrings = newVal.split(',');
            self.terminationStrings.forEach(function (value, index) {
                let termination = {};
                self.listOfPossibleTerminations.forEach(function (term) {
                    if (term.value === value.split(':')[0]) termination = term;
                });
                self.terminations.push(termination)
            })
        }
    },

    template: // if there are more than 2 termination types, add the add button back in by putting this back into the code
        `<div>
            <!-- <div class="input-group mb-3" :title="localisation.selectTerminationTooltip"><select class="custom-select" 
                                                                                                 v-model="currentTermination">
                <option v-for="(termination, index) in listOfPossibleTerminations" v-bind:key="index" :value="termination">
                    {{termination.title}}
                </option>
            </select>
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="button" v-on:click="addTermination(currentTermination)">
                        {{localisation.add}}
                    </button>
                </div>
            </div> -->
            <div v-for="(term, index) in terminations" class="row">
                <div class="col-10">
                    <component v-bind:key="index" v-bind:is="'termination-config-' + term.value"
                               v-bind:termination-config-str="terminationStrings[index]"
                               v-on:update-termination="addTerminationStringByIndex($event, index)"></component>
                </div>
                <div class="col-2">
                    <button type="button" class="close" v-on:click="removeTerminationByIndex(index)"><span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
        </div>` 
});

Vue.component('termination-config-rounds', {
    props: ['termination-config-str'],
    watch: {
        terminationConfigStr(newVal) {
            this.$forceUpdate()
        }
    },
    methods: {
        isValidConf(string) {
            const args = string.split(':');
            if (args[0] !== 'rounds') return false;
            if (args.length !== 2) return false;
        }
    },
    computed: {
        number: {
            get() {
                if (this.terminationConfigStr === undefined || this.terminationConfigStr === '') this.$emit('update-termination', 'rounds:1');
                return parseInt(this.terminationConfigStr.split(':')[1])
            },
            set(newVal) {
                const args = this.terminationConfigStr.split(':');
                args[1] = newVal;

                this.$emit('update-termination', args.join(':'))
            }
        }
    },
    template:
        `<div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">{{localisation.rounds}}</span>
            </div>
            <input type="number" min="0" class="form-control" v-model="number">
        </div>`
});

Vue.component('termination-config-time', {
    props: ['termination-config-str'],
    watch: {
        terminationConfigStr(newVal) {
            this.$forceUpdate();

        }
    },
    methods: {
        isValidConf(string) {
            const args = string.split(':');
            if (args[0] !== 'time') return false;
            if (args.length !== 2) return false;
        }
    },
    computed: {
        date: {
            get() {
                const args = this.terminationConfigStr.split(':');
                let date;
                if (args[1] === '') {
                    date = Date.now();
                } else {
                     date = new Date(Number(args[1]));
                }
                return date.toString();
            },
            set(newVal) {
                const args = this.terminationConfigStr.split(':');
                args[1] = moment(newVal, 'DD/MM/YYYY HH:mm')._d.getTime();
                this.$emit('update-termination', args.join(':'))
            }
        }
    },
    template:
        `<div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">{{localisation.endDate}}</span>
            </div>
            <date-picker v-bind:config="{format: 'DD/MM/YYYY HH:mm'}" v-model="date"></date-picker>
        </div>`
});