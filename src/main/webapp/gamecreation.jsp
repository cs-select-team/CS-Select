<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename="locale.Locale">
    <div>
         <%@ include file="WEB-INF/jspf/organiserNavbar.jspf" %>

        <script src="https://cdn.jsdelivr.net/npm/moment@2.22"></script>

        <script src="https://cdn.jsdelivr.net/npm/pc-bootstrap4-datetimepicker@4.17/build/js/bootstrap-datetimepicker.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/pc-bootstrap4-datetimepicker@4.17/build/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

        <script src="https://cdn.jsdelivr.net/npm/vue-bootstrap-datetimepicker@5"></script>


        <script src="https://use.fontawesome.com/ea33c7e9d8.js"></script>

        <script>
            // Initialize as global component
            Vue.component('date-picker', VueBootstrapDatetimePicker);
        </script>

        <div class="container" id="gamecreation">
            <alert-box v-for="(alert, index) in alerts" v-bind:key="index" v-bind:alert-message="alert.message"
                        v-bind:type="alert.type"></alert-box>
            <div class="row mt-2">

                <div class="col-4">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" ><fmt:message key="title"/></span>
                        </div>
                        <input type="text" class="form-control"v-model="title">
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" ><fmt:message key="description"/></span>
                        </div>
                        <textarea class="form-control"  v-model="desc"></textarea>
                    </div>
                    <ul class="nav nav-tabs">
                        <player-invite-nav-tab v-for="(value, index) in playerInputType" v-bind:key="index"
                                               v-bind:title="value.title" v-bind:value="value.value"
                                                v-on:update-tab="updateTab">

                        </player-invite-nav-tab>
                    </ul>
                    <component v-bind:is="currentTabComponent" v-on:new-email="addInvitedPlayer" v-bind:invited-players="invitedPlayers"
                                          v-on:update-invited="updateInvited"></component>
                </div>
                <div class="col-4">
                    <gamemode-config v-bind:gamemode-config-str="gameModeConfigString"
                                        v-on:update-config-str="updateConfString">

                    </gamemode-config>
                    <termination-config v-bind:termination-config-str="terminationConfigString"
                                        v-on:update-termination-str="updateTerminationString">

                    </termination-config>
                </div>
                <div class="col-4">
                    <pattern-selection :title="localisation.selectPatternTooltip" v-on:load-pattern="loadPattern"></pattern-selection>


                    <div class="input-group mb-3" :title="localisation.saveAsPatternTooltip">
                        <div class="input-group-prepend">
                            <div class="input-group-text">
                                <input type="checkbox" v-model="saveAsPattern" >
                            </div>

                        </div>
                        <input type="text" class="form-control" :disabled="!saveAsPattern" v-model="patternName" :placeholder="localisation.patternTitle">
                    </div>
                    <div class="input-group mb-3" :title="localisation.featureSetTooltip">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="">{{localisation.inputFeatures}}</span>
                        </div>
                        <input type="text" class="form-control" v-model="featureSet">
                    </div>

                    <div class="input-group mb-3" :title="localisation.databaseNameTooltip">
                        <div class="input-group-prepend">
                            <span class="input-group-text">{{localisation.inputDatabase}}</span>

                        </div>
                        <input type="text" class="form-control" v-model="databaseName">
                    </div>
                    <button type="button" class="btn btn-primary btn-lg" v-on:click="submitGame" :disabled="!createButtonEnabled">{{localisation.create}}</button>

                </div>
            </div>

        </div>
    </div>
    <script src="src/js/alert.js"></script>
    <script src="src/js/terminationCreation.js"></script>
    <script src="src/js/gamemodesCreation.js"></script>
    <script src="src/js/playerInvite.js"></script>

    <script src="src/js/gamecreation.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>