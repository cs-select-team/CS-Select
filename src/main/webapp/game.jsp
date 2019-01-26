<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename = "locale.Locale">
        <div class="container">
                <div id="gameFrame">
                        <component v-bind:is="gameType"
                                   v-bind:featurelist="featureList" v-bind:options="options" v-on:done="unlockButton" v-bind:key="forceUpdate"></component>
                        <input type="button" class="btn btn-primary" v-on:click="forceUpdate++" v-bind:class="{ disabled: this.buttonState }" value="Next"/>
                </div>
        </div>

        <script src="src/js/featureDisplay.js"></script>
        <script src="src/js/binarySelect.js"></script>
        <script src="src/js/gameFrame.js"></script>
        </fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>