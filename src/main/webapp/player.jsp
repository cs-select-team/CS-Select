<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename = "locale.Locale">
<div>
<%@ include file="WEB-INF/jspf/playerNavbar.jspf" %>
    <div class="top-buffer"></div>
    <div class="container">
<div class="row">
    <div class="col-md-3">
        <div id="daily">
            <daily-challenge v-bind:daily="daily"></daily-challenge>
        </div>
    </div>
    <div class="col-md-4" id="stats">
        <stats-display v-bind:username="username"
                      v-bind:points="points"></stats-display>
    </div>
    <div class="col-md-5 h-100 overflow-auto" id="leaderboard">
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
<div class="row">
    <div class="col-md-7 h-100 overflow-auto" id="games">
            <game-display
                    v-for="game in listOfGames"
                    v-bind:game="game"
                    v-bind:key="game.gameId">

            </game-display>
    </div>
    <div class="col-md-5 h-100" id="invites">
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
    </div>
<script src="src/js/player.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>