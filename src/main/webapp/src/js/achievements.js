Vue.component('achievement',{
    props: ['achievement'],

    template: '    <div class="card" v-if="achievement.state > 0">\n' +
        '        <h5 class="card-title"  v-if="achievement.state > 1">{{ achievement.title }}</h5>\n' +
        '        <div  v-if="achievement.state > 2"class="card-body">\n' +
        '            {{ achievement.desc }}\n' +
        '        </div>\n' +
        '    </div>'

})

/*  state   description
    0       invisiable
    1       hidden( the box is there, but nothing else)
    2       shown
    3       completed
 */
var achievements = new Vue({
    el: '#achievements',
    data: {
        achievementList: [
            {state: 0, desc:'click the like button 500 times', title: 'moron', id: 0},
            {state: 1, desc:'click the like button 500 times', title: 'moron', id: 1},
            {state: 2, desc:'click the like button 500 times', title: 'moron', id: 2},
            {state: 2, desc:'click the like button 500 times', title: 'moron', id: 2},
            {state: 2, desc:'click the like button 500 times', title: 'moron', id: 2},
            {state: 2, desc:'click the like button 500 times', title: 'moron', id: 2},
            {state: 2, desc:'click the like button 500 times', title: 'moron', id: 2},
            {state: 2, desc:'click the like button 500 times', title: 'moron', id: 2},
            {state: 2, desc:'click the like button 500 times', title: 'moron', id: 2},
            {state: 3, desc:'click the like button 500 times', title: 'moron', id: 3},
            {state: 3, desc:'click the like button 500 times', title: 'moron', id: 3},
            {state: 3, desc:'click the like button 500 times', title: 'moron', id: 3},
            {state: 3, desc:'click the like button 500 times', title: 'moron', id: 3},
            {state: 3, desc:'click the like button 500 times', title: 'moron', id: 3},
            {state: 3, desc:'click the like button 500 times', title: 'moron', id: 3},
            {state: 3, desc:'click the like button 500 times', title: 'moron', id: 3},
            {state: 3, desc:'click the like button 500 times', title: 'moron', id: 3},
            {state: 3, desc:'click the like button 500 times', title: 'moron', id: 3},
            {state: 3, desc:'click the like button 500 times', title: 'moron', id: 3}]

    }
})