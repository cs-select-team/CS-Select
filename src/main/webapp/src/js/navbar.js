const nav = new Vue({
    el: '#navbar',
    data: {
      resetHandler: null
    },
    methods: {
        logout() {
            axios.get('login/logout');
            window.location.href = '.';
        },
        resetTimeout() {
            window.clearTimeout(this.resetHandler);
            this.resetHandler = setTimeout(function () {
                localStorage.setItem('autoLogout', true);
                self.logout();
            }, 600000); // 600000 = 10 * 60 * 1000, so 10 minutes in ms
        }
    },
    mounted() {
        localStorage.setItem('autoLogout', false);
        const self = this;
        this.$nextTick(function () {
            this.resetHandler = setTimeout(function () {
                    localStorage.setItem('autoLogout', true);
                    self.logout();
                }, 600000); // 600000 = 10 * 60 * 1000, so 10 minutes in ms
        });
    }
});