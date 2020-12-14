<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@include file="/WEB-INF/includes/pageBegin.jsp" %>
<%@include file="/WEB-INF/includes/commonCss.jsp" %>
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="/assets/css/pages/login.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL STYLES -->
</head>
<body class="login">

<!-- BEGIN HEADER -->
<style type="text/css">
    #idletimeout { background: #ff6970; border:3px solid #ff0f03; color:#fff; font-family:arial, sans-serif; text-align:center; font-size:12px; padding:10px; position:relative; top:0px; left:0; right:0; z-index:100000; display:none; }
    #idletimeout a { color:#fff; font-weight:bold }
    #idletimeout span { font-weight:bold }
</style>
<div id="idletimeout">
    Bićete odjavljeni za <span><!-- countdown place holder --></span>&nbsp;sekundi zbog neaktivnosti.
    <a id="idletimeout-resume" href="#">Kliknite ovdje kako biste nastavili koristiti nalaz.ba</a>.
</div>

<!-- BEGIN TOP NAVIGATION BAR -->
<div class="front-header">
    <div class="container">
        <div class="navbar">
            <div class="navbar-inner">
                <!-- BEGIN LOGO -->
                <a class="brand logo-v1" href="/">
                    <img src="/assets/img/logo-big-full-black.png" alt="logoimg"/>
                </a>
                
                <a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">
                    <img src="/assets/img/menu-toggler.png" alt=""/>
                </a>
            </div>
            <div style="margin-top: 50px 0">
        </div>
    </div>
</div>

<!-- END HEADER -->

<!-- Deco Banner -->
<div class="deco-banner">

</div>
<!-- END Deco Banner -->

<div class="container min-hight">
<!-- BEGIN LOGIN -->
<div class="content">
    <!-- BEGIN LOGIN FORM -->
    <form class="form-vertical login-form" method="post" action="/j_spring_security_check">
        <h3 class="form-title">Prijavite se na <b>nalaz.ba</b></h3>
        <%@include file="/WEB-INF/includes/messageDisplay.jsp" %>
        <c:if test="${param.error == 'authFailure'}">
            <c:choose>
                <c:when test="${fn:contains(SPRING_SECURITY_LAST_EXCEPTION, 'SessionAuthenticationException')}">
                    <div class="alert alert-error">
                        <button class="close" data-dismiss="alert"></button>
                        <fmt:message key="errors.login.multiSession"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-error">
                        <button class="close" data-dismiss="alert"></button>
                        <fmt:message key="errors.login.authFailure"/>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:if>        
		<c:if test="${param.error == 'disabled'}">
			<div class="alert alert-error">
                <button class="close" data-dismiss="alert"></button>
                <fmt:message key="errors.login.disabled"/>
            </div>
		</c:if>
        <div class="control-group username">
            <label class="control-label visible-ie8 visible-ie9">Korisničko ime</label>
            <div class="controls">
                <div class="input-icon left">
                    <i class="icon-user"></i>
                    <input class="m-wrap placeholder-no-fix" type="text" placeholder="Korisnicko ime" name="j_username"/>
                </div>
            </div>
        </div>
        <div class="control-group password">
            <label class="control-label visible-ie8 visible-ie9">Šifra</label>
            <div class="controls">
                <div class="input-icon left">
                    <i class="icon-lock"></i>
                    <input class="m-wrap placeholder-no-fix" type="password" placeholder="Sifra" name="j_password"/>
                </div>
            </div>
        </div>
        <c:if test="${fn:contains(SPRING_SECURITY_LAST_EXCEPTION, 'SessionAuthenticationException')}">
            <label class="checkbox">
                <input type="checkbox" name="kickout" value="true"/> Odjavite prijavljenog korisnika
            </label>
        </c:if>
        <div class="form-actions">
            <button type="submit" class="btn blue pull-right">
                Prijavi se <i class="m-icon-swapright m-icon-white"></i>
            </button>
        </div>
<!--         <div class="forget-password"> -->
<!--             <p>Zaboravili ste šifru? <a href="/forgotPassword" class="" id="forget-password">Kliknite ovdje</a> -->
<!--                 kako biste resetovali šifru. -->
<!--             </p> -->
<!--              <h3> -->
<!--             	<a href="/register/" class="" id="signUp">Kliknite ovdje</a> za registraciju. -->
<!--             </h3> -->
<!--         </div> -->
    </form>
    <!-- END LOGIN FORM -->
</div>
<!-- END LOGIN -->
<!-- BEGIN COPYRIGHT -->

<%@include file="/WEB-INF/includes/commonJs.jsp" %>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="/assets/scripts/app.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<%@include file="/WEB-INF/includes/footer.jsp" %>
<%@include file="/WEB-INF/includes/pageEnd.jsp" %>