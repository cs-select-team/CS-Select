<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename="locale.Locale">
    <div>
    <%@ include file="WEB-INF/jspf/organiserNavbar.jspf" %>
    <div class="top-buffer"></div>
    <div class="container" id="inputs">
        <div class="alert alert-success" v-bind:class="{collapse: !alert}" role="alert">
            <fmt:message key="settingsUpdated"></fmt:message>
        </div>
        <div class="alert alert-danger" v-bind:class="{collapse: !emailFalse}" role="alert">
            <fmt:message key="emailTaken"></fmt:message>
        </div>
        <div class="input-group mb-3">
            <input type="email" v-model="email" class="form-control"
                   placeholder="<fmt:message key="newEmail"></fmt:message>">
            <div class="input-group-append">
                <button class="btn btn-outline-secondary" v-bind:class="{ disabled: email==''}" type="button"
                        v-on:click="setEmail"><fmt:message key="update"></fmt:message></button>
            </div>
        </div>
        <div class="input-group mb-3">
            <input type="password" v-model="password" class="form-control" placeholder="<fmt:message key="newPassword"/>">
            <div class="input-group-append">
                <button class="btn btn-outline-secondary" v-bind:class="{ disabled: password==''}" type="button"
                        v-on:click="setPassword"><fmt:message key="update"></fmt:message></button>
            </div>
        </div>
        <div class="input-group mb-3">
            <select class="custom-select" id="Lang" v-model="language">
                <option value="" disabled selected><fmt:message key="language"></fmt:message></option>
                <option v-for="lang in languageOptions"
                        v-bind:value="lang"> {{ lang }}
                </option>
            </select>
            <div class="input-group-append">
                <button class="btn btn-outline-secondary" type="button" v-bind:class="{ disabled: language==''}"
                        v-on:click="setLanguage"><fmt:message key="update"></fmt:message></button>
            </div>
        </div>
    </div>

    <script src="src/js/settings.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>
