Vue.component('streak-display', {
    props: ['counter'],
    computed: {
      progress1: function () {
          if (this.counter >= 3) return 60 + '%'
          else return 20 * this.counter + '%'
      },
        progress2: function () {
            if (this.counter >= 5) return 40 + '%'
            else if (this.counter <= 3) return 0 + '%'
            else {
                return 20 * (this.counter - 3)+ '%'
            }
        }
    },
    template: ' <div class="card ">' +
        '    <h5 class="card-title">{{ localisation.streak }}</h5>' +


        '        <div class="card-body">\n' +
        '                <div class="progress">\n' +
        '                    <div class="progress-bar bg-primary" role="progressbar" v-bind:style="{width: progress1}" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>\n' +
        '                    <div class="progress-bar bg-success" role="progressbar" v-bind:style="{width: progress2}" aria-valuenow="30" aria-valuemin="0" aria-valuemax="100"></div>\n' +
        '                </div>\n' +
        '        </div>\n' +
        '    </div>'

})
