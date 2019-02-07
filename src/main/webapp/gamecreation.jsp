<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename="locale.Locale">
    <div>
        <%@ include file="WEB-INF/jspf/organiserNavbar.jspf" %>
        <div class="container" id="gamecreation">
            <div class="row mt-2">
                <div class="col-4">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="Name" aria-label="Name" v-model="name">
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
            </div>

        </div>
    </div>
    <script src="src/js/gamecreation.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>