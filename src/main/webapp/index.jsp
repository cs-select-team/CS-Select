<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<%@ page isELIgnored="false"%>
<html xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-bind="http://www.w3.org/1999/xhtml">
<head>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
          integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="src/css/main.css" >
</head>
<body>
<fmt:setLocale value = "${sessionScope.lang}"/>
<fmt:bundle basename = "locale.Locale">
<div class="container">
    <div class="row">
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <form id="loginForm">
                        <div class="alert alert-danger" v-bind:class="{ invisible: alert }">
                            Wrong password or username. Or are you sure that you are an organiser?</div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" class="form-control" name="email" id="email" placeholder="Email-Adress" v-model="email">
                            <small id="registerNew" class="form-text text-muted"><fmt:message key="registerPrompt"/></small>
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
                        <button type="submit" class="btn btn-primary" v-on:click="submit"><fmt:message key="login"/></button>

                    </form>
    </div>
    </div>

</div>
<script src="src/js/login.js"></script>
</fmt:bundle>
</body>
</html>