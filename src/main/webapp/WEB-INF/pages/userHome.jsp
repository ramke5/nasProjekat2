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

                <small></small>
            </h3>
            <ul class="breadcrumb">
                <li>
                    <i class="icon-home"></i>
                    <a href="/">Home</a>
                </li>
            </ul>
        </div>
    </div>
    <div id="dashboard">
      <%--  <div class="row-fluid">
            <div class="span3">
                <a href="#" class="btn big red-stripe">Tenant</a>
            </div>
            <div class="span3">
                <a href="#" class="btn big purple-stripe">Prospect</a>
            </div>
            <div class="span3">
                <a href="#" class="btn big green-stripe">Portfolio</a>
            </div>
            <div class="span3">
                <a href="#" class="btn big yellow-stripe">Guarantor</a>
            </div>
        </div>--%>
    </div>


</div>
<!-- END CONTENT -->

<%@include file="/WEB-INF/includes/footer.jsp" %>
<%@include file="/WEB-INF/includes/commonJs.jsp" %>

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="/assets/scripts/app.js" type="text/javascript"></script>

<script>
    $(document).ready(function () {
        App.init(); // initlayout and core plugins
        App.setActiveNavigation("");
        App.activateSessionTimeoutTracking();
    });
</script>

<%@include file="/WEB-INF/includes/pageEnd.jsp" %>
