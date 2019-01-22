<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename = "locale.Locale">
<div class="container h-100">
<%@ include file="WEB-INF/jspf/playerNavbar.jspf" %>
<div class="row h-50">
    <div class="col-sm-3">
        Übersicht
    </div>
    <div class="col-sm-4">
        Statistiken
    </div>
    <div class="col-sm-5">
        Leaderboard
    </div>
</div>
<div class="row h-50">
    <div class="col-sm-7" id="games">
        <div class="card">
            <game-display
                    v-for="game in listOfGames"
                    v-bind:game="game"
                    v-bind:key="game.key"
                     v-bind:play="'<fmt:message key="play"/>'">

            </game-display>
        </div>
    </div>
    <div class="col-sm-5">
        Einladungen
    </div>

</div>
</div>
<script src="src/js/player.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>