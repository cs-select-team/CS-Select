    <%@ include file="WEB-INF/jspf/header.jspf" %>
        <fmt:bundle basename="locale.Locale">
            <div class="container h-100">
            <%@ include file="WEB-INF/jspf/organiserNavbar.jspf" %>
            <div class="container">
                <div class="row">
                    <div class="col">
                        <div class="row">
                            <title></title>
                        </div>
                        <div class="row">
                            <invite></invite>
                        </div>
                    </div>
                    <div class="col">
                        <modi></modi>
                    </div>
                    <div class="col">
                        <layout></layout>
                    </div>
                    <div class="col">
                        <patterns></patterns>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <description></description>
                    </div>
                    <div class="col">
                        <termination></termination>
                    </div>
                    <div class="col">
                        <div class="row">
                            <features></features>
                        </div>
                        <div class="row">
                            <database></database>
                        </div>
                        <div class="row">
                            <control></control>
                        </div>
                    </div>
                </div>
            </div>
            </div>
            <script src="src/js/gamecreation.js"></script>
        </fmt:bundle>
        <%@ include file="WEB-INF/jspf/footer.jspf" %>