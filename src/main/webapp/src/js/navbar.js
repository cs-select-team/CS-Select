var nav = new Vue({
    el: '#navbar',
    methods: {
        logout: function () {
            axios.post("login/logout");
            window.location.href = ".";
        }
    }
})