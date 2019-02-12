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
        },
        streakText: function () {
            if (this.counter < 3 && this.counter >= 0) return this.localisation.streakTimesOne;
            else if (this.counter < 5) return this.localisation.streakTimesOnePointFive;
            else return this.localisation.streakTimesTwo;
        }
    },
    template:
        '<div class="card">' +
        '    <div class="card-body">' +
        '        <h5 class="card-title">{{ localisation.streak }}</h5>' +
        '        <div class="card-text">\n' +
        '            <div class="progress">\n' +
        '                <div class="progress-bar bg-primary" role="progressbar" v-bind:style="{width: progress1}" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>\n' +
        '                <div class="progress-bar bg-success" role="progressbar" v-bind:style="{width: progress2}" aria-valuenow="30" aria-valuemin="0" aria-valuemax="100"></div>\n' +
        '            </div>' +
        '            <div>{{this.streakText}}</div>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div>'
})
