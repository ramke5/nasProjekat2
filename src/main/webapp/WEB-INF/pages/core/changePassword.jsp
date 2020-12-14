<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags/bootstrap" %>
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
                Promijeni šifru
                <small></small>
            </h3>
            <%@include file="/WEB-INF/includes/messageDisplay.jsp" %>
            <ul class="breadcrumb">
                <li>
                    <i class="icon-home"></i>
                    <a href="/">Početna stranica</a>
                    <i class="icon-angle-right"></i>
                </li>

                <li>Promijeni šifru</li>
            </ul>
        </div>
    </div>
    <div id="dashboard">
        <div class="row-fluid">
            <div class="span12">
                <form:form id="form" method="post"  modelAttribute="userForm" class="form-horizontal" >

                    <fieldset>
                        <legend>Promijeni šifru</legend>
                        <div class="row-fluid">

                            <div class="span6">

                                <f:password path="user.password" label="Nova šifra" required="true"/>
                                <f:password path="confirmPassword" label="Potvrdi novu šifru" required="true"/>

                            </div>
                            </div>

                    </fieldset>
                    <div class="form-actions clearfix">
                        <p>
                            <button type="submit" class="btn btn-primary blue"><i class="icon-ok-sign"></i> Spasi novu šifru</button>
                            <a href="/userHome" class="btn btn-primary grey"><i class="icon-remove-sign"></i> Odustani</a>
                        </p>
                    </div>

                </form:form>

            </div>
        </div>
    </div>

</div>
<!-- END CONTENT -->

<%@include file="/WEB-INF/includes/footer.jsp" %>
<%@include file="/WEB-INF/includes/commonJs.jsp" %>

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script type="text/javascript" src="/assets/scripts/app.js"></script>
<script type="text/javascript" src="/js/ba/nalaz/user.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->

<script>
    $(document).ready(function () {
        App.init(); // initlayout and core plugins
        App.setActiveNavigation('/user/changePassword/create');
        App.activateSessionTimeoutTracking();

    });
</script>

<%@include file="/WEB-INF/includes/pageEnd.jsp" %>
