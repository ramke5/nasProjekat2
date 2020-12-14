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
                Sign Up For nalaz <img style="width: 80px;" alt="nalaz" src="/assets/img/industry-score.png">
            </h3>
            <ul class="breadcrumb">
                    <a href="/">Home</a>
                </li>
                <li>
                    / Sign Up
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="deco-banner"></div>

<div class="container">
	<div class="row-fluid">
		<div class="span12">
			<h4 style="margin-top: -45px; font-size: 20px;">Fill out the information below to create a user account and register with Paypal. Once complete, you will have access to on demand reports for any industry in any US market. Contact us with questions or to obtain bulk discounts by clicking <a href="">here</a>.</h4>
		</div>
	</div>
</div>
<div class="container min-hight">
	<div class="content signupform">
		<%@include file="/WEB-INF/includes/messageDisplay.jsp" %>
		<form:form id="form" method="post"  modelAttribute="userForm" class="form-horizontal" autocomplete="off">
			<div class="row-fluid signup-form">
			    <div class="span6">
			        <f:hidden path="systemGeneratedPassword"/>
					<f:hidden path="changePassword"/>
					<f:input path="user.firstName" label="First Name" required="true"/>
					<f:input path="user.lastName" label="Last Name" required="true"/>
			        <div class="control-group">
						<label class="control-label" for="org">Company</label>
						<div class="controls">
	                  		<input type="text" name="org"/>
						</div>
					</div>
			        <div class="control-group">
						<label class="control-label" for="phone">Phone Number</label>
						<div class="controls">
	                  		<input type="text" name="phone"/>
						</div>
					</div>
	            </div>
				<div class="span6">
					<f:input path="user.email" label="Email" required="true"/>
					<f:input path="user.username" label="Username" required="true"/>
					<f:password path="user.password" label="Password" required="true"/>
					<f:password path="confirmPassword" label="Confirm Password" required="true"/>
					<%-- <f:checkbox path="user.enabled" label="Enabled"/> --%>
				</div>
			</div>                        
	        <div class="form-actions">
	            <a href="/login" class="btn black"><i class="m-icon-swapleft  m-icon-white"></i> Back</a>
	            <button type="submit" class="btn blue pull-right">Submit <i class="m-icon-swapright m-icon-white"></i></button>
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
