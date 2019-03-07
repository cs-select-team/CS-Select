/**
 * include this file for a feature box, a component that displays a feature
 * has an event called 'toggled' which will fire if the feature is selected
 */
Vue.component('feature-box', {
    props: ['feature'],
    watch: {
        'feature.toggled'(newVal, oldVal) {

        }
    },
    computed: {
        desc() {
            return this.feature.desc.replace(/(\\n)+/g, '<br>');
        }
    },
    template:
        `<div class="card feature-display" v-bind:class="{ 'bg-primary': feature.toggled, 'gray-out': feature.useless}">
            <div class="card-body"><h5 class="card-title">{{ feature.name }}</h5>
                <p class="card-text" v-html="desc"></p></div>
            <div class="btn-group">
                <button type="button" class="btn btn-secondary" data-toggle="modal" :data-target="'#graphModal' + feature.id"
                        :disabled="feature.useless"> {{ localisation.showGraphs }}
                </button>
                <button type="button" class="btn btn-primary" v-on:click="toggleMarked" :disabled="feature.useless">{{
                    localisation.selectFeature}}
                </button>
                <button type="button" class="btn btn-secondary" v-on:click="toggleUseless">{{ localisation.toggleUseless}}
                </button>
            </div>
            <div class="modal fade" :id="'graphModal' + feature.id" tabindex="-1" role="dialog"
                 aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" :title="localisation.close" class="close" data-dismiss="modal"
                                    aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <div class="container">
                                <div class="row">
                                    <div class="col-sm"><img class="img-fluid" :src="feature.graph1"/></div>
                                    <div class="col-sm"><img class="img-fluid" :src="feature.graph2"/></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>`,
    methods: {
        toggleMarked() {
            const oldVal = this.feature.toggled;
            if (!this.feature.useless) this.feature.toggled = !this.feature.toggled;
            this.$forceUpdate();
            this.$emit("toggled", !oldVal, oldVal)
        },
        toggleUseless() {
            this.feature.useless = !this.feature.useless;
            this.$emit("useless-toggle", this.feature.useless, this.feature.toggled);
            this.feature.toggled = false;
            this.$forceUpdate()

        }
    }
});
