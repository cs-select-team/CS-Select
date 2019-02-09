Vue.component('alert-box', {
    props: ['alert-message', 'type'],
    template: '<div class="alert" v-bind:class="{\'alert-danger\': type==0, \'alert-success\': type==1}" role="alert">\n' +
        '  {{alertMessage}}\n' +
        '</div>'
});