Vue.component('points-display', {
    props: ['points'],
    template: '<div v-if="points !== undefined" class="card" style="width: 18rem;">\n' +
        '  <div class="card-body">\n' +
        '    <h5 class="card-title">{{localisation.points}}</h5>' +
        '       <div class="card-text">{{points}}</div>' +
        '  </div>\n' +
        '</div>'
})