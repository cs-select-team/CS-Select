
Vue.component('termination-config', {
    props: ['termination-config-str'],
    data: function(){
        return {
           listOfTerminations: [{title: "Time termination", value:"time"},
                                {title: "Rounds termination", value:"rounds"}],
            currentTermination: 'rounds'
        }
    },
    template: "<div>" +
        "<select class='custom-select' v-model='currentTermination' >\n" +
        "    <option v-for='(term,index) in listOfTerminations' v-bind:key='index'" +
        "               :value='term.value'>{{term.title}}</option>\n" +
        "  </select>" +
        "   <component v-bind:is='componentName' v-bind:termination-config-str='terminationConfigStr'" +
        "               v-on:update-termination='updateTermination'></component>" +
        "</div>",
    methods: {
        updateTermination: function(newVal) {
            this.$emit('update-termination-str', newVal);
        }
    },
    computed: {
        componentName: function() {

            return "termination-config-" + this.currentTermination
        }
    }
})

Vue.component('termination-config-rounds', {
    props: ['termination-config-str'],
    data: function() {
        return {
            conf: this.isValidConf(this.terminationConfigStr)?this.terminationConfigStr:"rounds:1"
        }
    },
    mounted: function () {
        this.$emit('update-termination', this.conf)
    },

    watch: {
      conf: function(newVal) {
          this.$emit('update-termination', newVal)
      }
    },
    methods: {
        isValidConf: function(string) {
            var args = string.split(':');
            if (args[0] !== 'rounds') return false;
            if (args.length !== 2) return false;
        }
    },
    computed: {
        number: {
            get: function() {
                return parseInt(this.conf.split(':')[1])
            },
            set: function(newVal) {
                var args = this.conf.split(':');
                args[1] = newVal;
                this.conf = args.join(':')
            }
        }
    },
    template: '<div class="input-group mb-3">\n' +
        '  <div class="input-group-prepend">\n' +
        '    <span class="input-group-text">Rounds</span>\n' +
        '  </div>\n' +
        '  <input type="number" min="0" class="form-control" v-model="number">\n' +
        '</div>'

});

Vue.component('termination-config-time', {
    props: ['termination-config-str'],
    data: function() {
        return {
            conf: this.isValidConf(this.terminationConfigStr)?this.terminationConfigStr:"time:2019-02-08T12:27:18.232Z"
        }
    },
    watch: {
        date: function(newVal) {
            this.$emit('update-termination', newVal)
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
                return new Date(this.conf.split(':')[1])
            },
            set: function(newVal) {
                var newConf = '';
                newConf += this.conf.split(':')[0] + ':';
                newConf += new Date(newVal).toISOString();
                this.conf = newConf;
            }
        }
    },
    template: '<input type="text" v-model="date"></input>'

});