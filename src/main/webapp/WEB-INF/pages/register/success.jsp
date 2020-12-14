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
	        <h3 class="page-title">Register</h3>
	
	        <ul class="breadcrumb">
	            <li>
	                <i class="icon-home"></i>
	                <a href="/">Home</a>
	                <i class="icon-angle-right"></i>
	            </li>
	            <li>
	            	Register
	            </li>
	        </ul>
	    </div>
	</div>

	<%@include file="/WEB-INF/includes/messageDisplay.jsp" %>

	<div id="dashboard">
        <div class="row-fluid">
            <div class="span12">
           		<p>Click on the activation link in the Email to activate your account</p>
            </div>
        </div>
    </div>

	<%@include file="/WEB-INF/includes/footer.jsp" %>
    <%@include file="/WEB-INF/includes/commonJs.jsp" %>

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script type="text/javascript" src="/assets/scripts/app.js"></script>
<%-- <script type="text/javascript" src="/js/ba/nalaz/user.js"></script>	--%>
<!-- END PAGE LEVEL SCRIPTS -->
<%@include file="/WEB-INF/includes/pageEnd.jsp" %>