<%@ taglib prefix="f" tagdir="/WEB-INF/tags/bootstrap" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%@include file="/WEB-INF/includes/pageBegin.jsp" %>
<%@include file="/WEB-INF/includes/commonCss.jsp" %>
<link href="/assets/css/pages/login.css" rel="stylesheet" type="text/css"/>

<!-- BEGIN PAGE LEVEL STYLES -->
<link href="/assets/plugins/jquery-ui/css/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css"/>

</head>
<body class="login">
<!-- TOP BAR -->
<div class="front-topbar">
    <div class="container">
        <div class="row-fluid">
            <div class="span6">
            </div>
            <div class="span6 topbar-social">
                <ul class="unstyled inline">
                    <li><a href="http://www.facebook.com" target="_blank"><i class="icon-facebook"></i></a></li>
                    <li><a href="http://www.linkedin.com" target="_blank"><i class="icon-linkedin"></i></a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<!-- END TOP BAR -->
<!-- PRE HEADER -->
<div id="pre-header">
    <div class="container text-right">
        <ul class="unstyled inline links">
            <%--<li><a href="#">Create An Account</a></li>--%>
            <%--<li><a href="#" class="active">Client Log In</a></li>--%>
            <li><a href="">Contact Us</a></li>
            <li><a href="">nalaz</a></li>
        </ul>
    </div>
</div>
<!-- END PRE HEADER -->
<!-- TOP NAVIGATION BAR -->
<div class="front-header">
    <div class="container">
        <div class="navbar">
            <div class="navbar-inner">
                <a class="brand logo-v1" href="/"><img src="/assets/img/logo-big-full-black.png" alt="logoimg"/></a>
                <a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">
                    <img src="/assets/img/menu-toggler.png" alt=""/>
                </a>
            </div>
        </div>
    </div>
</div>
<!-- END NAVIGATION BAR -->

<div class="container">
    <div class="row-fluid">
        <div class="span12">
            <h3 class="page-title">
                Register
            </h3>
            <ul class="breadcrumb">
                    <a href="/">Home</a>
                </li>
                <li>
                    / Register
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="deco-banner"></div>
<div class="container min-hight">
	<div class="content signupform">
	    <%@include file="/WEB-INF/includes/messageDisplay.jsp" %>
		<br/>
		<form:form id="form" method="post"  modelAttribute="userForm" class="form-horizontal" autocomplete="on">
			<div class="row-fluid signup-form">
				<div class="span6">
					<f:hidden path="user.username"/>
					<f:input path="user.email"  label="Email" required="true" readonly="true"/>
					<f:input path="user.firstName" label="First name" required="true"/>
					<f:input path="user.lastName" label="Last name" required="true"/>
					<f:input path="user.phoneNumber" label="Phone number"/>
					<f:input path="user.company" label="Company" required="false"/>
				</div>
				<div class="span6">
					<f:password path="user.password" label="Password" required="true"/>
					<f:password path="confirmPassword" label="Confirm Password" required="true"/>
				</div>
			</div>
			<div class="form-actions">
				<div class="span3">
				</div>
				<div class="span6">
	            	<button type="submit" style="margin-left: 50px;" class="btn blue">Submit <i class="m-icon-swapright m-icon-white"></i></button>
				</div>
	        </div>
		</form:form>
	</div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>
<%@include file="/WEB-INF/includes/commonJs.jsp" %>

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script type="text/javascript" src="/assets/scripts/app.js"></script>
<%-- <script type="text/javascript" src="/js/ba/nalaz/user.js"></script> --%>
<!-- END PAGE LEVEL SCRIPTS -->
<%@include file="/WEB-INF/includes/pageEnd.jsp" %>
