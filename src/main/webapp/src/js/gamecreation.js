Vue.component('text-input',{
    props: ['label'],
    template: '<'
})

var creation = new Vue({
    el: '#gamecreation',
    data: {
        gamemodeOptions: {},

    },
    methods: {
        /** submits a new parameter to the api
         *
         * @param name name of the parameter
         * @param value value of that parameter
         */
        submitParameter: function(name, value) {
            axios({
                method: 'post',
                url: 'create/setParam',
                params: {
                    name: name,
                    value: value
                }
            })
        }
    }
});
