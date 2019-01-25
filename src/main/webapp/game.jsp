<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename = "locale.Locale">
        <div class="container">
                <div id="binary">
                        <div class="row">
                                <div class="col-sm">
                                        <feature-box v-bind:feature="feature1"></feature-box>
                                </div>
                                <div class="col-sm">
                                        <feature-box v-bind:feature="feature2"></feature-box>
                                </div>
                        </div>
                </div>
        </div>
<script src="src/js/featureDisplay.js"></script>
        <script src="src/js/binarySelect.js"></script>
        </fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>