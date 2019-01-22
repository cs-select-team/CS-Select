<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename = "locale.Locale">
<div class="container h-100">
<%@ include file="WEB-INF/jspf/playerNavbar.jspf" %>
<div class="row h-50">
    <div class="col-sm-3">
        Übersicht
    </div>
    <div class="col-sm-4" id="stats">
        <stats-display v-bind:username="username"
                      v-bind:points="points"></stats-display>
    </div>
    <div class="col-sm-5 h-100 overflow-auto" id="leaderboard">
        <table class="table">
            <thead>
                <tr>
                    <th>#</th>
                    <th><fmt:message key="username"/></th>
                    <th><fmt:message key="points"/></th>
                </tr>
            </thead>
            <tbody>
                <tr is="leaderboard-element" v-for="player in playerList"
                             v-bind:key="player.place"
                             v-bind:place="player.place"
                             v-bind:username="player.username"
                             v-bind:points="player.points">
                </tr>

            </tbody>
        </table>
    </div>
</div>
    <div class="top-buffer"></div>
<div class="row h-50">
    <div class="col-sm-7 h-100 overflow-auto" id="games">
            <game-display
                    v-for="game in listOfGames"
                    v-bind:game="game"
                    v-bind:key="game.gameId"
                     v-bind:play="'<fmt:message key="play"/>'">

            </game-display>
    </div>
    <div class="col-sm-5 h-100" id="invites">
        <div class="overflow-auto h-100">
            <invite-element v-for="invite in listOfInvites"
                            v-bind:key="invite.gameId"
                            v-bind:game-id="invite.gameId"
                            v-bind:title="invite.title">

            </invite-element>
        </div>
    </div>

</div>
</div>
<script src="src/js/player.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>