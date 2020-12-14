<%@ taglib prefix="f" tagdir="/WEB-INF/tags/bootstrap" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>


<%@include file="/WEB-INF/includes/pageBegin.jsp" %>
<%@include file="/WEB-INF/includes/commonCss.jsp" %>

<!-- BEGIN PAGE LEVEL STYLES -->
<link href="/assets/plugins/jquery-ui/css/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css"/>
<link href="/assets/css/pages/lrTenantAssessment.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%@include file="/WEB-INF/includes/header.jsp" %>

<!-- BEGIN CONTENT -->
<div class="container-fluid">
	<div class="row-fluid">		
	    <div>
	        <h3 class="page-title">Sign up</h3>
	
	        <ul class="breadcrumb">
	            <li>
	                <i class="icon-home"></i>
	                <a href="/">Home</a>
	                <i class="icon-angle-right"></i>
	            </li>
	            <li>
	            	Sign Up
	            </li>
	        </ul>
	    </div>
	</div>

	<%@include file="/WEB-INF/includes/messageDisplay.jsp" %>

	<div id="dashboard">
        <div class="row-fluid">
            <div class="span12">
            	<c:if test="${showEmailLink}">
            		<p>Click on the activation link in the Email to activate your account</p>
            		<%-- <form name="activateManually" action="/signUp/activateManually" method="post" class="form-horizontal" autocomplete="off">
                        <div class="control-group">
							<label class="control-label" for="username">Username</label>
							<div class="controls">
								<input type="text" name="username">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="activationKey">Activation Key</label>
							<div class="controls">
								<input type="text" name="activationKey">
							</div>
						</div>
	                    <div class="form-actions clearfix">
		                    <p>
	                        	<button type="submit" class="btn btn-primary blue"><i class="icon-ok-sign"></i> Continue</button>
							</p>
						</div>
            		</form> --%>
            	</c:if>
            	<c:if test="${showLoginLink}">
            		<p>Click <a href="/login"><b>here</b></a> to login.</p>
            	</c:if>
            </div>
        </div>
    </div>

	<%@include file="/WEB-INF/includes/footer.jsp" %>
    <%@include file="/WEB-INF/includes/commonJs.jsp" %>

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script type="text/javascript" src="/assets/scripts/app.js"></script>
<%-- <script type="text/javascript" src="/js/ba/nalaz/user.js"></script>	--%>
<!-- END PAGE LEVEL SCRIPTS -->

<script>
    $(document).ready(function () {
        App.init(); // initlayout and core plugins
        App.setActiveNavigation('/user/list');
        App.activateSessionTimeoutTracking();
        //User.disableFields();
        //User.init();
        $('#changePassword').val(true);
        $('#systemGeneratedPassword').val(false);
    });
</script>

<%@include file="/WEB-INF/includes/pageEnd.jsp" %>