<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!-- BEGIN TOP BAR -->
<div class="front-topbar">
    <div class="container">
    </div>
</div>
<!-- END TOP BAR -->
<!-- PRE HEADER -->
<div id="pre-header">
    <div class="container text-right">
        <ul class="unstyled inline links">
            <%--<li><a href="#">Create An Account</a></li>--%>
            <security:authorize ifNotGranted="ROLE_SUPER_ADMIN,ROLE_ADMIN_KOMPANIJE,ROLE_KORISNIK_KOMPANIJE,ROLE_KORISNIK">
                <li><a href="/login">Client Log In</a></li>
            </security:authorize>
            <security:authorize ifAnyGranted="ROLE_SUPER_ADMIN,ROLE_ADMIN_KOMPANIJE,ROLE_KORISNIK_KOMPANIJE,ROLE_KORISNIK">
                <li class="dropdown user">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="icon-user"></i>
                        <span class="username">${userObj.fullName}</span>
                        <i class="icon-angle-down"></i>
                    </a>
                    <ul class="dropdown-menu text-left">
                        <li><a href="/user/changePassword/change"><i class="icon-lock"></i> Promijeni šifru</a></li>
                        <li><a href="/logout"><i class="icon-key"></i> Odjavi se</a></li>
                    </ul>
                </li>
            </security:authorize>
        </ul>
    </div>
</div><!--/pre-header-->

<!-- BEGIN HEADER -->
<style type="text/css">
    #idletimeout { background: #ff6970; border:3px solid #ff0f03; color:#fff; font-family:arial, sans-serif; text-align:center; font-size:12px; padding:10px; position:relative; top:0px; left:0; right:0; z-index:100000; display:none; }
    #idletimeout a { color:#fff; font-weight:bold }
    #idletimeout span { font-weight:bold }
</style>
<div id="idletimeout">
    Bićete odjavljeni za <span><!-- countdown place holder --></span>&nbsp;sekundi zbog neakitvnosti.
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
                <!-- <div class="brandText">
                    ${labObj.name}
                </div>-->
                <!-- END LOGO -->
                <!-- BEGIN RESPONSIVE MENU TOGGLER -->
                <a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">

                    <img src="/assets/img/menu-toggler.png" alt=""/>
                </a>
                <!-- END RESPONSIVE MENU TOGGLER -->
                <!-- BEGIN TOP NAVIGATION MENU -->
                <%@include file="mainMenu.jsp"%>
                <!-- END TOP NAVIGATION MENU -->

                <!-- END TOP NAVIGATION BAR -->
            </div>
        </div>
    </div>
</div>
<!-- END HEADER -->

<!-- BEGIN CONTAINER -->
<div class="container min-hight">
    <!-- BEGIN SIDEBAR -->
    <!--%@include file="mainMenu.jsp"%-->
    <!-- END SIDEBAR -->
