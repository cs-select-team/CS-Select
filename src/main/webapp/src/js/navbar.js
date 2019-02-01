var nav = new Vue({
    el: '#navbar',
    methods: {
        logout: function () {
            axios.get("login/logout");
            window.location.href = ".";
        }
    }
})