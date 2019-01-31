<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename = "locale.Locale">
    <div class="top-buffer"></div>
<div class="container">
<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <form id="registerForm">
                        <div class="alert alert-danger" v-bind:class="{ collapse: !alert }">
                            <fmt:message key="noRegister"/></div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" class="form-control" name="email" id="email" placeholder="Email-Adress" v-model="email">
                        </div>
                        <div class="form-group">
                            <label for="password"><fmt:message key="password"/></label>
                            <input type="password" class="form-control" name="password" id="password"
                                   placeholder="<fmt:message key="password"/>" v-model="password">
                        </div>
                        <div class="form-group">
                            <label for="passwordRepeat"><fmt:message key="passwordRepeat"/></label>
                            <input type="password" class="form-control" id="passwordRepeat"
                                   placeholder="<fmt:message key="passwordRepeat"/>" v-model="passwordRepeat">
                        </div>
                        <div class="alert alert-danger" v-bind:class="{collapse: passwordRepeat == password }">
                          <fmt:message key="passwordNoMatch"/>
                        </div>
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
                        <div class="form-group fixed-height" >
                            <div class="position-absolute" v-bind:class="{ invisible: organiser !='organiser' }">
                            <label for="thirdParam"><fmt:message key="masterPassword"/></label>
                            <input type="password" class="form-control" id="thirdParam"
                                   placeholder="<fmt:message key="masterPassword"/>" v-model="thirdParam">
                            </div>
                            <div class="position-absolute" v-bind:class="{ invisible: organiser =='organiser' }">
                            <label for="thirdParam"><fmt:message key="username"/></label>
                            <input type="text" class="form-control" id="thirdParam"
                                   placeholder="<fmt:message key="username"/>" v-model="thirdParam">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary" v-on:click="submit"
                        :disabled="passwordRepeat != password || email == '' || password == '' || thirdParam == ''">
                        <fmt:message key="register"/></button>

                    </form>
    </div>
</div>
<script src="src/js/register.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>