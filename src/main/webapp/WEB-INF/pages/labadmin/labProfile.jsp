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
                Profil laboratorije
                <small></small>
            </h3>
            <%@include file="/WEB-INF/includes/messageDisplay.jsp" %>
            <ul class="breadcrumb">
                <li>
                    <i class="icon-home"></i>
                    <a href="/">Početna stranica</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li>
                   Administracija
                    <i class="icon-angle-right"></i>
                </li>
                <li>Profil laboratorije</li>
            </ul>
        </div>
    </div>
    <div id="dashboard">
        <div class="row-fluid">
            <div class="span12">
                <form:form id="form" method="post" modelAttribute="lab" class="form-horizontal">
                    <fieldset>
                        <legend id="lab_details">Podaci o laboratoriji</legend>
                        <div id="lab-contact">
                            <div class="span6">
                                <f:hidden path="id" />
                                <f:input path="name" label="Ime laboratorije" required="true"/>
                                <f:input path="contact.firstName" label="Ime kontakta" required="true"/>
                                <f:input path="contact.lastName" label="Prezime kontakta" required="true"/>
                                <f:input path="contact.officePhone" label="Broj telefona"/>
                                <f:input path="contact.mobileNumber" label="Broj mobitela"/>
                                <f:input path="contact.email" label="Email" required="true"/>
                            </div>
                            <div class="span6">
                                <f:input path="contact.address1" label="Adresa 1" required="true"/>
                                <f:input path="contact.address2" label="Adresa 2"/>
                                <f:input path="contact.city" label="Grad" required="true"/>
                            </div>
                        </div>
                    </fieldset>
                    <div class="form-actions clearfix">
                    <p>
                        <button type="submit" class="btn btn-primary blue"><i class="icon-ok-sign"></i> Spasi izmjene</button>
                        <a href="/"
                           class="btn btn-primary grey"><i class="icon-remove-sign"></i> Poništi sve izmjene</a></p>
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
<script type="text/javascript" src="/js/ba/nalaz/lab.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->

<script>
    $(document).ready(function () {
        App.init(); // initlayout and core plugins
        App.setActiveNavigation('/lab/labProfile/');
        App.activateSessionTimeoutTracking();
        Lab.init();
        Lab.setStateMainContact();
        Lab.setStateBillingContact();
        Lab.setInputMask();
    });
</script>

<%@include file="/WEB-INF/includes/pageEnd.jsp" %>
