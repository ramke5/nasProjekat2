<%@include file="/WEB-INF/includes/pageBegin.jsp" %>
<%@include file="/WEB-INF/includes/commonCss.jsp" %>
<link href="/assets/css/pages/login.css" rel="stylesheet" type="text/css"/>
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
                    <%--<li><a href="#"><i class="icon-rss"></i></a></li>--%>
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
                Activate Account
                <small>

                </small>
            </h3>
            <ul class="breadcrumb">
                    <a href="/">Home</a>
                </li>
                <li>
                    / Activate Account
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="deco-banner"></div>

<div class="container min-hight">
	<div class="content">
	    <%@include file="/WEB-INF/includes/messageDisplay.jsp" %>
	    <form class="form-vertical" method="post" action="/signUp/activateAccount">
	        <h3 class="form-title">Activate Account</h3>
	        <p>Enter your e-mail address below to get your activation link.</p>
	        <div class="control-group">
	            <div class="controls">
	                <div class="input-icon left">
	                    <i class="icon-envelope"></i>
	                    <input class="m-wrap placeholder-no-fix" type="text" placeholder="Email" name="email" />
	                </div>
	            </div>
	        </div>
	        <div class="form-actions">
	            <a href="/login" class="btn black"><i class="m-icon-swapleft  m-icon-white"></i> Back</a>
	            <button type="submit" class="btn blue pull-right">Submit <i class="m-icon-swapright m-icon-white"></i></button>
	        </div>
	    </form>
	</div>
</div>

<%@include file="/WEB-INF/includes/commonJs.jsp" %>
<script src="/assets/scripts/app.js" type="text/javascript"></script>
<!-- <script src="/assets/scripts/login.js" type="text/javascript"></script> -->
<%@include file="/WEB-INF/includes/footer.jsp" %>
<%@include file="/WEB-INF/includes/pageEnd.jsp" %>