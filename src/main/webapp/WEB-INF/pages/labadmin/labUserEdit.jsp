<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags/bootstrap"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@include file="/WEB-INF/includes/pageBegin.jsp"%>
<%@include file="/WEB-INF/includes/commonCss.jsp"%>
<!-- BEGIN PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
</head>
<body>
<%@include file="/WEB-INF/includes/header.jsp"%>

<!-- BEGIN CONTENT -->
<div class="container-fluid">
<div class="row-fluid">
<div class="span12">
<h3 class="page-title">
Izmijeni korisnika laboratorije <small></small>
</h3>
<ul class="breadcrumb">
<li><i class="icon-home"></i> <a href="/">Početna stranica</a>
<i class="icon-angle-right"></i></li>
<li>Administracija <i class="icon-angle-right"></i>
</li>
<li><a href="/user/labUser/list/${labObj.id}">Korisnici
laboratorije</a> <i class="icon-angle-right"></i></li>

<li>Izmijeni korisnika laboratorije</li>
</ul>
</div>
</div>
<div id="dashboard">
<div class="row-fluid">
<div class="span12">
<form:form id="form" method="post" modelAttribute="userForm"
class="form-horizontal">
<fieldset>
<legend>Podaci o korisniku</legend>
<div>
<div class="span6">
<f:hidden path="user.id" />
<f:input path="user.firstName" label="Ime" />
<f:input path="user.lastName" label="Prezime" />
<f:input path="user.phoneNumber" label="Broj telefona" />
<f:input path="user.mobileNumber" label="Broj mobitela" />
<f:input path="user.email" label="Email" />

<s:bind path="user.subLab">
<div class="control-group ${status.error ? 'error' : '' }">
<label class="control-label" for="user.subLab">
Poslovna jedinica </label>
<div class="controls">
<form:select path="user.subLab" cssClass="input-xlarge" required="true">
<form:option value="">Izaberi</form:option>
<form:option value="AGC">ALEA Genetički Centar</form:option>
<form:option value="PDJ">Poslovna jedinica 2</form:option>
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
<f:input path="user.username" label="Korisničko ime" readonly="true" />
<f:checkbox path="systemGeneratedPassword"
label="Automatsko generisanje šifre" />
<f:checkbox path="changePassword" label="Promijeni šifru" />
<div id="passwordHideEdit">
<f:password path="user.password" label="Nova šifra" />
<f:password path="confirmPassword" label="Potvrdi novu šifru" />
</div>
<f:checkbox path="welcomeEmail" label="Pošalji email" />
<f:checkbox path="user.enabled" label="Aktivan korisnik" />
</div>
</div>
</fieldset>
<div class="form-actions clearfix">
<p>
<button id="btnSubmit" type="submit"
class="btn btn-primary blue">
<i class="icon-ok-sign"></i> Izmijeni korisnika
</button>
<a href="/user/labUser/list/${labObj.id}"
class="btn btn-primary grey"><i class="icon-remove-sign"></i>
Poništi sve izmjene</a>
</p>
</div>
</form:form>
</div>
</div>
</div>
</div>
<!-- END CONTENT -->

<%@include file="/WEB-INF/includes/footer.jsp"%>
<%@include file="/WEB-INF/includes/commonJs.jsp"%>

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script type="text/javascript" src="/assets/scripts/app.js"></script>
<script type="text/javascript" src="/js/ba/nalaz/user.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->

<script>
    $(document).ready(function () {
        App.init(); // initlayout and core plugins
        App.setActiveNavigation('/user/labUser/list/${labObj.id}');
        App.activateSessionTimeoutTracking();
        User.hidePasswordFields();
        $('#passwordHideEdit').hide();
        $("input[name='changePassword']").on('change', function () {
            if ($(this).is(":checked")) {
                $("#systemGeneratedPassword1").prop('checked', false);
                $("#welcomeEmail1").prop('checked', false);
                $('#passwordHideEdit').show();
            }
            else
            {
                $('#passwordHideEdit').hide();
            }

        });
        if($("input[name='changePassword']").is(":checked") )
        {
            $('#passwordHideEdit').show(500);
        }
        User.setUserValues('${user.subLab}');
        User.setHelpText();
    });
</script>

<%@include file="/WEB-INF/includes/pageEnd.jsp"%>