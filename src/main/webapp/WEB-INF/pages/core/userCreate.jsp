<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags/bootstrap" %>
<%@include file="/WEB-INF/includes/pageBegin.jsp" %>
<%@include file="/WEB-INF/includes/commonCss.jsp" %>
<!-- BEGIN PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
</head>
<body>
<%@include file="/WEB-INF/includes/header.jsp" %>

<!-- BEGIN CONTENT -->
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <h3 class="page-title">
                Dodaj novog korisnika
                <small></small>
            </h3>

            <ul class="breadcrumb">
                <li>
                    <i class="icon-home"></i>
                    <a href="/">Početna stranica</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li>
                    <a href="/user/list">Korisnici</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li>Dodaj novog korisnika</li>
            </ul>
        </div>
    </div>
    <div id="dashboard">
        <div class="row-fluid">
            <div class="span12">
                <form:form id="form" method="post"  modelAttribute="userForm" class="form-horizontal" autocomplete="off">
                    <fieldset>
                        <legend>Podaci o korisniku</legend>
                        <div class="row-fluid">
                            <div class="span6">
                                <f:hidden path="systemGeneratedPassword"/>
                                <f:hidden path="changePassword"/>
		                        <f:input path="user.firstName" label="Ime" required="true"/>
		                        <f:input path="user.lastName" label="Prezime" required="true"/>
		                        <f:input path="user.email" label="Email" required="true"/>
                            </div>
                            <div class="span6">
		                        <f:input path="user.username" label="Korisničko ime" required="true"/>
		                        <f:password path="user.password" label="Šifra" required="true"/>
		                        <f:password path="confirmPassword" label="Potvrdi šifru" required="true"/>
		                        <f:checkbox path="user.enabled" label="Aktivan korisnik"/>
                        	</div>
                        </div>
                    </fieldset>
                    <div class="form-actions clearfix">
	                    <p>
	                        <button type="submit" class="btn btn-primary blue"><i class="icon-ok-sign"></i> Spasi korisnika</button>
	                        <a href="/user/list" class="btn btn-primary grey"><i class="icon-remove-sign"></i> Odustani</a>
	                    </p>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<!-- END CONTENT -->

<%@include file="/WEB-INF/includes/footer.jsp" %>
<%@include file="/WEB-INF/includes/commonJs.jsp" %>

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script type="text/javascript" src="/assets/scripts/app.js"></script>
<script type="text/javascript" src="/js/ba/nalaz/user.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->

<script>
    $(document).ready(function () {
        App.init(); // initlayout and core plugins
        App.setActiveNavigation('/user/list');
        App.activateSessionTimeoutTracking();
        User.disableFields();
        User.init();
        $('#changePassword').val(true);
        $('#systemGeneratedPassword').val(false);
    });
</script>

<%@include file="/WEB-INF/includes/pageEnd.jsp" %>
