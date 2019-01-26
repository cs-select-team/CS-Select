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
        '           <button type="button" class="btn btn-primary float-right btn-space">Abort</button>' +
        '           <button type="button" class="btn btn-primary float-right btn-space">Okay</button>' +
        '       </div>'
});

var title = new Vue({
    el: "#title-area"
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