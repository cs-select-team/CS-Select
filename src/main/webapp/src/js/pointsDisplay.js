Vue.component('points-display', {
    props: ['points'],
    template:
        `<div v-if="points !== undefined" class="card" style="width: 18rem;">
            <div class="card-body"><h5 class="card-title">{{localisation.points}}</h5>
                <div class="card-text">
                    {{points}}
                </div>
            </div>
        </div>`
});