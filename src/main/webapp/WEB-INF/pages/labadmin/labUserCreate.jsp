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
                Dodaj novog korisnika laboratorije
                <small></small>
            </h3>

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
                <li>
                    <a href="/user/labUser/list/${labObj.id}">Korisnici laboratorije</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li>Dodaj novog korisnika laboratorije</li>
            </ul>
        </div>
    </div>
    <div id="dashboard">
        <div class="row-fluid">
            <div class="span12">
                <form:form id="form" method="post"  modelAttribute="userForm" class="form-horizontal" autocomplete="off" >
                    <fieldset>
                        <legend>Podaci o korisniku</legend>

                         <div>
                             <div class="span6">
                                 <f:hidden path="changePassword"/>
                                 <f:hidden path="labId"/>
                                 <f:input path="user.firstName" label="Ime" required="true"/>
                                 <f:input path="user.lastName" label="Prezime" required="true"/>
                                 <f:input path="user.phoneNumber" label="Broj telefona"/>
                                 <f:input path="user.mobileNumber" label="Broj mobitela"/>
                                 <f:input path="user.email" label="Email" required="true"/>

                                 <s:bind path="user.subLab">
                                     <div class="control-group ${status.error ? 'error' : '' }">
                                         <label class="control-label" for="user.subLab">
                                             Poslovna jedinica
                                         </label>
                                         <div class="controls">
                                             <form:select path="user.subLab"  cssClass="input-xlarge" required="true">
                                                 <form:option value="">Izaberi</form:option>
                                                 <form:option value="AGC">ALEA Genetički Centar</form:option>
                                                 <form:option value="PJA">Poslovna jedinica 2</form:option>
                                                 <form:option value="Ostalo">Ostalo</form:option>
                                             </form:select>
                                             <c:if test="${status.error}">
                                                 <span class="help-inline">${status.errorMessage}</span>
                                             </c:if>

                                         </div>
                                     </div>
                                 </s:bind>
                             </div>
                             <div class="span6">
                                 <f:input path="user.username" label="Korisničko ime" required="true"/>
                                 <f:checkbox path="systemGeneratedPassword" label="Automatsko generisanje šifre"/>
                                 <div id="passwordHide">
                                     <f:password path="user.password" label="Šifra" required="true"/>
                                     <f:password path="confirmPassword" label="Potvrdi šifru" required="true"/>
                                 </div>
                                 <f:checkbox path="welcomeEmail" label="Pošalji email"/>
                                 <f:checkbox path="user.enabled" label="Aktivan korisnik"/>
                             </div>
                         </div>
                    </fieldset>
                    <div class="form-actions clearfix">
                   <p>
                   <button id="compUserSave" type="submit" class="btn btn-primary blue"><i class="icon-ok-sign"></i> Spasi korisnika</button>
                   <a href="/user/labUser/list/${labObj.id}" class="btn btn-primary grey"><i class="icon-remove-sign"></i> Odustani</a></p>
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
        App.setActiveNavigation('/user/labUser/list/${labObj.id}');
        App.activateSessionTimeoutTracking();
        User.init();
        User.hidePasswordFields();
        $('#changePassword').val(true);
        User.setHelpText();
    });


</script>

<%@include file="/WEB-INF/includes/pageEnd.jsp" %>