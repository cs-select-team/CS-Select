<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename="locale.Locale">
    <div class="container">
        <div class="row">
            <div class="col-md-3 col-lg-4">
            </div>
            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
                <form id="loginForm">
                    <div class="alert alert-danger mt-3" v-bind:class="{ collapse: !(autoLogout() == 'true') }">
                        <fmt:message key="autoLogout"/>
                    </div>
                    <div class="alert alert-info mt-3" v-bind:class="{ collapse: !(checkEmail() == 'true') }">
                        <fmt:message key="checkEmail"/>
                    </div>
                    <div class="alert alert-danger mt-3" v-bind:class="{ invisible: alert }">
                        <fmt:message key="noLogin"/>
                    </div>
                    <div class="alert alert-danger" v-bind:class="{ collapse: !missingConfig }">
                        <fmt:message key="noConfig"/>
                    </div>
                    <div class="alert alert-danger" v-bind:class="{ collapse: !missingDatabase }">
                        <fmt:message key="noDatabase"/>
                    </div>
                    <div class="login-form">
                        <img class="login-form-img" src="src/img/cs_select.svg" alt="CS:Select">
                        <div class="form-group login-form-div">
                            <label for="email"><fmt:message
                                    key="email"/></label>
                            <input type="email" class="form-control" name="email" id="email"
                                   placeholder="<fmt:message key="emailAddress"/>" v-model="email">
                            <small id="registerNew" class="form-text text-muted"><a href="register.jsp"><fmt:message
                                    key="registerPrompt"/></a></small>
                        </div>
                        <div class="form-group login-form-div">
                            <label for="password"><fmt:message key="password"/></label>
                            <input type="password" class="form-control" name="password" id="password"
                                   placeholder="<fmt:message key="password"/>" v-model="password">
                            <small id="passwordForgotten" class="form-text text-muted"><a href="resetPassword.jsp"><fmt:message
                                    key="passwordForgotten"/></a></small>
                        </div>
                        <div class="custom-control custom-checkbox login-form-div" id="organiserCheck">
                            <input type="checkbox" class="custom-control-input" name="organiser" id="organiser"
                                   v-model="organiser">
                            <label class="custom-control-label" for="organiser"> <fmt:message key="organiser"/></label>
                        </div>
                        <button type="submit" class="btn btn-primary login-form-button" v-on:click="submit"
                                :disabled="password == ''||email == ''"><fmt:message key="login"/></button>
                    </div>
                </form>
            </div>
            <div class="col-md-3 col-lg-4">
            </div>
        </div>

    </div>
    <script src="src/js/login.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>