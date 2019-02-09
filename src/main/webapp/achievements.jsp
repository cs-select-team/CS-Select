<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename="locale.Locale">
    <%@include file="WEB-INF/jspf/playerNavbar.jspf" %>
    <div class="container">
        <div id="achievements">
            <div class="row">
                <div class="col">
                    <div class="card-columns">
                        <achievement v-for="achievement in achievementList"
                                     v-bind:key="achievement.id"
                                     v-bind:achievement="achievement"></achievement>
                    </div>
                </div>
            </div>


        </div>
    </div>
    <script src="src/js/achievements.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>