<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename = "locale.Locale">
<div class="container">
    <div class="row">
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <form id="loginForm">
                        <div class="alert alert-danger" v-bind:class="{ invisible: alert }">
                            <fmt:message key="noLogin"/></div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" class="form-control" name="email" id="email" placeholder="Email-Adress" v-model="email">
                            <small id="registerNew" class="form-text text-muted"><a href="register.jsp"><fmt:message key="registerPrompt"/></a></small>
                        </div>
                        <div class="form-group">
                            <label for="password"><fmt:message key="password"/></label>
                            <input type="password" class="form-control" name="password" id="password"
                                   placeholder="Password" v-model="password">
                        </div>
                        <div class="custom-control custom-checkbox" id="organiserCheck">
                            <input type="checkbox" class="custom-control-input" name="organiser" id="organiser" v-model="organiser">
                            <label class="custom-control-label" for="organiser"> <fmt:message key="organiser"/></label>
                        </div>
                        <button type="submit" class="btn btn-primary" v-on:click="submit" :disabled="password == ''||email == ''"><fmt:message key="login"/></button>

                    </form>
    </div>
    </div>

</div>
<script src="src/js/login.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>