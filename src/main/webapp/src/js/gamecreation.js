Vue.component('invite', {
    props: [''],
    template: '<div>Invite People</div>'
});

Vue.component('mode', {
    props: [''],
    template: '<div class="container">' +
        '           <div class="col">Checklist</div>' +
        '           <div class="col">Layout</div>' +
        '      </div>'
});

Vue.component('layout', {
    props: [''],
    template: '<div class="container">' +
        '       <div class="col">' +
        '        Checklist' +
        '       </div>' +
        '           <div class="col">' +
        '           <form>' +
        '               <input type="text" name="height">' +
        '               <input type="text" name="width">' +
        '           </form>' +
        '       </div>' +
        '      </div>'
});

Vue.component('pat', {
    props: [''],
    template: '<div class="container">' +
        '       <div class="row">' +
        '           Dropdown' +
        '       </div>' +
        '       <div class="row">' +
        '           Save pattern?' +
        '       </div>' +
        '       <div class="row">' +
        '           Input name' +
        '       </div>' +
        '      </div>'
});

Vue.component('termination', {
    props: [''],
    template: '<div class="container">Termination</div>'
});

Vue.component('control', {
    props: [''],
    template: '<div class="container">' +
        '           <input type="button" class="btn btn-primary float-right btn-space" v-on:click="abort()" :value="localisation.abort">' +
        '           <input type="button" class="btn btn-primary float-right btn-space" :value="localisation.create">' +
        '       </div>',
    methods: {
        abort : function() {
            creation.emptyStore();
        }
    }
});

var creation = new Vue({
    el: '#gamecreation',
    data: {
        title: '',
        invites: [],
        mode: '',
        width: '',
        height: '',
        pattern: '',
        description: '',
        terminationtype: '',
        terminationvalue: '',
        featureSet: '',
        databaseAddress: '',
        savePattern: false,
    },
    methods: {
        emptyStore() {
            this.title = '';
            this.invites = [];
            this.mode = '';
            this.width = '';
            this.height= '';
            this.pattern = '';
            this.description = '';
            this.terminationtype = '';
            this.terminationvalue = '';
            this.featureSet = '';
            this.databaseAddress = '';
            this.savePattern = false;
        }
    }
});