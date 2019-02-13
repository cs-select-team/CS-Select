<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename="locale.Locale">
    <div>
        <%@ include file="WEB-INF/jspf/organiserNavbar.jspf" %>
        <div class="container">
            <div class="row">
                <div class="col-md-8 col-sm-12 col-xs-12" id="games">
                    <div class="container h-10 title">
                        <fmt:message key="activeGames"/>
                    </div>
                    <div class="row" id="active">
                        <active-games-display
                                v-for="game in listOfGames"
                                v-bind:game="game"
                                v-bind:key="game.gameId" v-on:terminate="gameWasTerminated"></active-games-display>
                    </div>
                    <div class="container h-10 title">
                        <fmt:message key="terminatedGames"/>
                    </div>
                    <div class="row" id="terminated">
                        <terminated-games-display
                                v-for="game in listOfGames"
                                v-bind:game="game"
                                v-bind:game-id="game.gameId"
                                v-bind:key="game.gameId"></terminated-games-display>
                    </div>
                </div>
                <div class="col" id="stats">
                    <div class="container h-10 title">
                        <fmt:message key="help"/>
                    </div>
                    <fmt:message key="organiserHelp"/>
                </div>
            </div>
        </div>
    </div>
    <script src="src/js/playerInvite.js"></script>
    <script src="src/js/organiser.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>