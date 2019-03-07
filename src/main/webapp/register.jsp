<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename="locale.Locale">
    <div class="top-buffer"></div>
    <div class="container">
    <div class="row">
        <div class="col-md-3 col-lg-4">
        </div>
        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
            <form id="registerForm">
                <div class="alert alert-danger" v-bind:class="{ collapse: !emailInUse }">
                    <fmt:message key="emailInUse"/></div>
                <div class="alert alert-danger" v-bind:class="{ collapse: !wrongMasterPassword }">
                    <fmt:message key="wrongMasterPassword"/></div>
                <div class="alert alert-danger" v-bind:class="{ collapse: !missingConfig }">
                    <fmt:message key="noConfig"/></div>
                <div class="alert alert-danger" v-bind:class="{ collapse: !missingDatabase }">
                    <fmt:message key="noDatabase"/></div>
                <div class="login-form">
                    <img class="login-form-img" src="src/img/cs_select.svg" alt="CS:Select">
                    <div class="form-group login-form-div">
                        <label for="email"><fmt:message
                                key="email"/></label>
                        <input type="email" class="form-control" name="email" id="email"
                               placeholder="<fmt:message key="emailAddress"/>" v-model="email">
                        <small id="login" class="form-text text-muted"><a href="index.jsp"><fmt:message
                                key="loginPrompt"/></a></small>
                    </div>
                    <div class="login-form-div">
                        <div class="form-check" id="organiserCheck">
                            <input type="radio" class="form-check-input" name="organiser" id="player"
                                   value="player" v-model="organiser">
                            <label class="form-check-label" for="player"> <fmt:message key="player"/></label>
                        </div>
                        <div class="form-check" id="organiserCheck">
                            <input type="radio" class="form-check-input" name="organiser" id="organiser"
                                   value="organiser" v-model="organiser">
                            <label class="form-check-label" for="organiser"> <fmt:message key="organiser"/></label>
                        </div>
                    </div>
                    <div class="form-group fixed-height login-form-div">
                        <div class="position-absolute login-form-absolute" v-bind:class="{ invisible: organiser !='organiser' }">
                            <label for="secondParam"><fmt:message key="masterPassword"/></label>
                            <input type="password" class="form-control" id="secondParam"
                                   placeholder="<fmt:message key="masterPassword"/>" v-model="secondParam">
                        </div>
                        <div class="position-absolute login-form-absolute" v-bind:class="{ invisible: organiser =='organiser' }">
                            <label for="secondParam"><fmt:message key="username"/></label>
                            <input type="text" class="form-control" id="secondParam"
                                   placeholder="<fmt:message key="username"/>" v-model="secondParam">
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary login-form-button" v-on:click="submit"
                            :disabled="passwordRepeat != password || email == '' || password == '' || secondParam == ''">
                        <fmt:message key="register"/></button>
                </div>
            </form>
        </div>
        <div class="col-md-3 col-lg-4">
        </div>
    </div>
    <script src="src/js/register.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>