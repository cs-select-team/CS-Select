<%@ include file="WEB-INF/jspf/header.jspf" %>
    <fmt:bundle basename="locale.Locale">
        <div>
        <%@ include file="WEB-INF/jspf/organiserNavbar.jspf" %>
        <div class="container">
            <div class="row">
                <div class="col-6" id="games">
                    <div class="container h-10 title">
                        <fmt:message key="activeGames"/>
                    </div>
                    <div class="row" id="active">
                        <active-games-display
                            v-for="game in listOfGames"
                            v-bind:game="game"
                            v-bind:key="game.gameId"
                            v-bind:invite="'<fmt:message key="invite"/>'"
                            v-bind:terminate="'<fmt:message key="terminate"/>'"></active-games-display>
                    </div>
                    <div class="container h-10 title">
                        <fmt:message key="terminatedGames"/>
                    </div>
                    <div class="row" id="terminated">
                        <terminated-games-display
                            v-for="game in listOfGames"
                            v-bind:game="game"
                            v-bind:game-id="game.gameId"
                            v-bind:key="game.gameId"
                            v-bind:del="'<fmt:message key="delete"/>'"></terminated-games-display>
                    </div>
                </div>
                <div class="col" id="stats">
                    <div class="container h-10 title">
                        <fmt:message key="stats"/>
                    </div>
                    <stats-display></stats-display>
                </div>
            </div>
        </div>
        </div>
        <script src="src/js/organiser.js"></script>
    </fmt:bundle>
    <%@ include file="WEB-INF/jspf/footer.jspf" %>