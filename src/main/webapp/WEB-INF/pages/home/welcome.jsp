<%@include file="/WEB-INF/includes/pageBegin.jsp" %>
<%@include file="/WEB-INF/includes/commonCss.jsp" %>
<link href="/assets/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="/assets/plugins/bootstrap/css/bootstrap-responsive.css" rel="stylesheet" type="text/css"/>
<link href="/assets/css/reset.css" rel="stylesheet" type="text/css"/>
<link href="/assets/css/style-metro.css" rel="stylesheet" type="text/css"/>
<link href="/assets/css/style-back.css" rel="stylesheet" type="text/css"/>
<link href="/assets/css/style.css" rel="stylesheet" type="text/css"/>
<link href="/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
<link href="/assets/plugins/select2/css/select2_modified.css" rel="stylesheet" type="text/css"/>
<link href="/assets/plugins/dynatree/css/dynatree.css" rel="stylesheet" type="text/css"/>
<link href="/assets/css/themes/blue.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="/assets/css/themes/blue-frontend.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="/assets/css/extra.css" rel="stylesheet" media="screen">
<link rel="shortcut icon" href="/favicon.ico"/>
<link href="/assets/css/pages/welcome.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%@include file="/WEB-INF/includes/header.jsp" %>
<div class="jumbotron jumbotron-lg">
    <div class="container welcome">
        <h1>WELCOME! <span class="blue-text">${userObj.fullName}</span></h1>
        <div class="row-fluid">
        	<div class="span9">	            
	            <h2 style="margin-bottom: 10px;">Nalaz.ba</h2>	            
            </div>
	    </div>
	</div>
</div>
<%@include file="/WEB-INF/includes/footer.jsp" %>
<%@include file="/WEB-INF/includes/commonJs.jsp" %>

<script type="text/javascript" src="/assets/scripts/app.js"></script>
<script>
    $(document).ready(function () {
        App.init();
        App.setActiveNavigation('/welcome');
        App.activateSessionTimeoutTracking();
        App.activateSearchReset();
        App.refreshPage();
    });
</script>
<%@include file="/WEB-INF/includes/pageEnd.jsp" %>