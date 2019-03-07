// type alert-*
// 0    danger
// 1    success
// 2    info
Vue.component('alert-box', {
    props: ['alert-message', 'type'],
    data() {
        return {
            alertClasses: [
                "alert-danger",
                "alert-success",
                "alert-info"
            ]
        }
    },
    template:
        `<div class="alert" v-bind:class="alertClasses[type]" role="alert">
            {{alertMessage}}\n
        </div>`
});