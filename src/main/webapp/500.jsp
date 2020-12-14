<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page language="java" isErrorPage="true" %>
<%@include file="/WEB-INF/includes/pageBegin.jsp" %>
<%@include file="/WEB-INF/includes/commonCss.jsp" %>
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="/assets/css/pages/error.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL STYLES -->
</head>
<body class="page-500-full-page">
<div style="text-align:center;margin-top:50px">
    <img src="/assets/img/logo-big-full-black.png" alt="" />
</div>
<div class="row-fluid">
    <div class="span12 page-500">
        <div class=" number">
            Sistemska greška!
        </div>
        <div class=" details">
            <h3></h3>
            <p>Molimo pokušajte kasnije. Ukoliko se problem ponovi, kontaktirajte tehničku podršku.<br/>
            <a href="/">Vratite se na početnu stranicu.</a>
            </p>
        </div>
        <div class="page-500">
            <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
            <code>
                ${exception.message}
            </code>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/includes/commonJs.jsp" %>
<%@include file="/WEB-INF/includes/pageEnd.jsp" %>