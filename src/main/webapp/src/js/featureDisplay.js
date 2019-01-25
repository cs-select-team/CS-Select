/**
 * include this file for a feature box, a component that displays a feature
 */
Vue.component('feature-box', {
    props: ['feature'],
    data: function() {
        return {
            toggled: false
        }
    },
    template: '                <div class="card" v-bind:class="{ \'bg-primary\': feature.toggled }" >' +
        '                        <h5 class="card-title">{{ feature.name }}</h5>' +
        '                        <div class="card-body">' +
        '                                {{ feature.desc }}' +
        '                        </div>' +
        '                        <div class="btn-group">' +
        '                        <button type="button" class="btn btn-secondary" data-toggle="modal" :data-target="\'#graphModal\' + feature.id">' +
        '                                Show graphs for this feature' +
        '                        </button>' +
        '                                <button type="button" class="btn btn-primary" v-on:click="feature.toggled = !feature.toggled">Select this feature</button>' +
        '                        </div>' +
        '                        <div class="modal fade" :id="\'graphModal\' + feature.id" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">' +
        '                                <div class="modal-dialog modal-lg" role="document">' +
        '                                        <div class="modal-content">' +
        '                                                <div class="modal-header">' +
        '                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
        '                                                                <span aria-hidden="true">&times;</span>' +
        '                                                        </button>' +
        '                                                </div>' +
        '                                                <div class="modal-body">' +
        '                                                        <div class="container">' +
        '                                                                <div class="row">' +
        '                                                                        <div class="col-sm">' +
        '                                                                                <img class="img-fluid" :src="feature.graph1"/>' +
        '                                                                        </div>' +
        '                                                                        <div class="col-sm">' +
        '                                                                                <img class="img-fluid" :src="feature.graph2"/>' +
        '                                                                        </div>' +
        '                                                                </div>' +
        '                                                        </div>' +
        '                                                </div>' +
        '                                        </div>' +
        '                                </div>' +
        '                        </div>' +
        '                </div>'
})
