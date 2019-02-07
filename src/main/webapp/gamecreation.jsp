<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename="locale.Locale">
    <div>
         <%@ include file="WEB-INF/jspf/organiserNavbar.jspf" %>


        <div class="container" id="gamecreation">
            <div class="row mt-2">
                <div class="col-4">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" >Title</span>
                        </div>
                        <input type="text" class="form-control"v-model="title">
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" >Description</span>
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
                    <pattern-selection v-on:load-pattern="loadPattern"></pattern-selection>
                </div>
            </div>

        </div>
    </div>
    <script src="src/js/terminationCreation.js"></script>
    <script src="src/js/gamemodesCreation.js"></script>
    <script src="src/js/gamecreation.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>