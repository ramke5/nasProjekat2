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
                Izmijeni podatke o korisniku
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
                <li>Izmijeni korisnika</li>
            </ul>
        </div>
    </div>
    <div id="dashboard">
        <div class="row-fluid">
            <div class="span12">
                <form:form id="form" method="post" modelAttribute="userForm" class="form-horizontal">
                    <fieldset>
                        <legend>Podaci o korisniku: ${userForm.user.firstName} </legend>
                        <f:hidden path="user.id"/>
                        <f:hidden path="systemGeneratedPassword"/>

                        <div class="row-fluid">
                            <div class="span6">
                                <f:input path="user.firstName" label="Ime" required="true"/>
                                <f:input path="user.lastName" label="Prezime" required="true"/>
                                <f:input path="user.email" label="Email" required="true"/>
                            </div>

                            <div class="span6">
                                <f:input path="user.username" label="Korisničko ime" readonly="true"/>
                                <f:checkbox path="changePassword" label="Izmijeni šifru"/>
                                <div id='passwordHide'>
                                 <f:password path="user.password" label="Nova šifra" required="true"/>
                                <f:password path="confirmPassword" label="Potvrdi novu šifru" required="true"/>
                                </div>
                                <f:checkbox path="user.enabled" label="Aktivan korisnik"/>
                            </div>
                        </div>
                    </fieldset>
                    <div class="form-actions clearfix">
                    <button type="submit" class="btn btn-primary blue"><i class="icon-ok-sign"></i> Spasi izmjene</button>
                    <a href="/user/list" class="btn btn-primary grey"><i class="icon-remove-sign"></i> Poništi sve izmjene</a>
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
        User.init();
        $('#passwordHide').hide();
        $('#systemGeneratedPassword').val(false);
        $("input[name='changePassword']").on('change', function () {
            if ($(this).is(":checked")) {
                $('#passwordHide').show();
            }
            else
            {
                $('#passwordHide').hide();
            }

        });
        if($("input[name='changePassword']").is(":checked") )
        {
            $('#passwordHide').show(500);
        }
    });
</script>

<%@include file="/WEB-INF/includes/pageEnd.jsp" %>
