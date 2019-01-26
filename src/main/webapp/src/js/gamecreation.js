Vue.component('gametitle', {
    props: [''],
    template: '<form>' +
        '       <input type="text" name="title">' +
        '      </form>'
});

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

Vue.component('description', {
    props: [''],
    template: '<form>' +
        '           <input type="text" name="description">' +
        '      </form>'
});

Vue.component('termination', {
    props: [''],
    template: '<div class="container">Termination</div>'
});

Vue.component('features', {
    props: [''],
    template: '<div class="container">Features</div>'
});

Vue.component('database', {
    props: [''],
    template: '<div class="container">Database</div>'
});

Vue.component('control', {
    props: [''],
    template: '<div class="container">' +
        '           <button type="button" class="btn btn-primary float-right btn-space" v-on:click="abort()">Abort</button>' +
        '           <button type="button" class="btn btn-primary float-right btn-space">Okay</button>' +
        '       </div>',
    abort() {
        store.state.forEach(function(item) {
            item = '';
        });
    }
});

var title = new Vue({
    el: "#title-area",
    data: {
        sharedState: store.state.title
    }
});

var invite = new Vue({
    el: "#invite-area"
});

var mode = new Vue({
    el: "#mode-area"
});

var pattern = new Vue({
    el: "#pattern-area"
});

var description = new Vue({
    el: "#description-area"
});

var termination = new Vue({
    el: "#termination-area"
});

var features = new Vue({
    el: "#feature-area"
});

var database = new Vue({
    el: "#database-area"
});

var control = new Vue({
    el: "#control-area"
});

var store = {
    state: {
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
    setOption(option, value) {
        switch(option) {
            case "title":
                this.state.title = value;
                break;
            case "addInvite":
                this.state.invites.push(value);
                break;
            case "removeInvite":
                this.state.invites.forEach(function (item, index,) {
                    if(value == item.toString()) this.state.invites.splice(index, 1);
                });
                break;
            case "mode":
                this.state.mode = value;
                break;
            case "width":
                if (isNaN(value) || value < 1) {
                    alert("Input not valid");
                    return;
                }
                this.state.width = value;
                break;
            case "height":
                if (isNaN(value) || value < 1) {
                    alert("Input not valid");
                    return;
                }
                this.state.height = value;
                break;
            case "pattern":
                this.store.state.pattern = value;
                break;
            case "description":
                this.store.state.description = value;
                break;
            case "terminationtype":
                this.store.state.terminationtype = value;
                break;
            case "terminationvalue":
                this.store.state.terminationvalue = value;
                break;
            case "featureSet":
                this.store.state.featureSet = value;
                break;
            case "database":
                this.store.state.databaseAddress = value;
                break;
            case "savePattern":
                this.store.state.savePattern = value;
                break;
        }
    },
};