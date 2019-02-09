<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename="locale.Locale">
    <%@include file="WEB-INF/jspf/playerNavbar.jspf" %>
    <div class="container-flex ">
        <div id="gameFrame">
            <div class="row mt-5">
                <div class="col-10">
                    <component v-bind:is="gameType"
                               v-bind:feature-list="featureList" v-bind:options="options" v-on:done="unlockButton"
                               v-bind:key="forceUpdate"></component>
                </div>
                <div class="col-2">
                    <input type="button" class="btn btn-primary btn-block" v-on:click="sendResults"
                           v-bind:class="{ disabled: this.buttonState }" value="<fmt:message key="next"/>"/>
                    <input type="button" class="btn btn-outline-primary btn-block" v-on:click="skip"
                           value="<fmt:message key="skip"/>"/>
                    <input type="button" class="btn btn-outline-primary btn-block" v-on:click="quit"
                           value="<fmt:message key="quit"/>"/>

                    <streak-display v-bind:counter="counter"></streak-display>
                    <points-display v-bind:points="points"></points-display>
                </div>
            </div>
        </div>
    </div>

    <script src="src/js/featureDisplay.js"></script>
    <script src="src/js/streak.js"></script>
    <script src="src/js/pointsDisplay.js"></script>


    <script src="src/js/binarySelect.js"></script>
    <script src="src/js/matrixSelect.js"></script>
    <!-- add new gamemodes here -->

    <script src="src/js/gameFrame.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>