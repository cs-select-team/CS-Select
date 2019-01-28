/**
 * include this file for a feature box, a component that displays a feature
 * has an event called 'toggled' which will fire if the feature is selected
 */
Vue.component('feature-box', {
    props: ['feature'],
    watch: {
      'feature.toggled': function (oldVal, newVal) {
          this.$emit("toggled", newVal, oldVal)
      }
    },
    template: '                <div class="card" v-bind:class="{ \'bg-primary\': feature.toggled, \'gray-out\': feature.useless}" >' +
        '                        <h5 class="card-title">{{ feature.name }}</h5>' +
        '                        <div class="card-body">' +
        '                                {{ feature.desc }}' +
        '                        </div>' +
        '                        <div class="btn-group">' +
        '                        <button type="button" class="btn btn-secondary" data-toggle="modal" :data-target="\'#graphModal\' + feature.id">' +
        '                                {{ localisation.showGraphs }}' +
        '                        </button>' +
        '                        <button type="button" class="btn btn-primary" v-on:click="feature.toggled = !feature.toggled">{{ localisation.selectFeature}}</button>' +
        '                        <button type="button" class="btn btn-secondary" v-on:click="feature.useless = !feature.useless">{{ localisation.toggleUseless}}</button>' +
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
