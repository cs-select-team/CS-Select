var nav = new Vue({
    el: '#navbar',
    methods: {
        logout: function () {
            axios.get("login/logout");
            window.location.href = ".";
        }
    },
    mounted: function () {
        localStorage.setItem("autoLogout", false);
        var self = this;
        setTimeout(function() {
            localStorage.setItem("autoLogout", true);
            self.logout();
        }, 600000);// 600000 = 10 * 60 * 1000, so 10 minutes in ms
    }
})