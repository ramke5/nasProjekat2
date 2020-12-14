<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@include file="/WEB-INF/includes/pageBegin.jsp" %>
<%@include file="/WEB-INF/includes/commonCss.jsp" %>
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="/assets/css/pages/error.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL STYLES -->
</head>
<body class="page-403-full-page">
<div style="text-align:center;margin-top:50px">
    <img src="/assets/img/logo-big-full-black.png" alt="" />
</div>
<div class="row-fluid">
    <div class="span12 page-403">
        <div class="number">
        Pristup odbijen!
        </div>
        <div class="details">
            <h3></h3>
            <p>
                Niste autorizovani da pristupite ovoj stranici. Kontaktirajte svog administratora.<br/>
                <a href="/">Vratite se na početnu stranicu.</a>
            </p>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/includes/commonJs.jsp" %>
<%@include file="/WEB-INF/includes/pageEnd.jsp" %>
