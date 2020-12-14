<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
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
                Evidencija neobrađenih uzoraka
                <small>

                </small>
            </h3>
            <%@include file="/WEB-INF/includes/messageDisplay.jsp" %>
            <ul class="breadcrumb">
                <li>
                    <i class="icon-home"></i>
                    <a href="/">Početna stranica</a>
                    <i class="icon-angle-right"></i>
                </li>
                <li>Evidencija neobrađenih uzoraka</li>
            </ul>
        </div>
    </div>
    <div>
        <div class="row-fluid">
            <div class="span6">
                <a class="btn blue" href="/sample/create"><i class="icon-plus-sign"></i> Dodaj novi uzorak</a>
            </div>
            <div class="span6">
                <form name="search">
                    <div class="input-append pull-right">
                        <input name="q" class="m-wrap" size="10" type="text" value="${search}" autofocus="autofocus" ><button class="btn"><i class="icon-search"></i></button>
                        <c:if test="${not empty search}"><button id="searchReset" class="btn"><i class="icon-remove"></i></button></c:if>
                    </div>
                </form>
            </div>
            <div class="row-fluid">
                <div class="span12">
                    <div id="sampleList">
                    <display:table list="${data}" uid="row" class="table" sort="external" requestURI="" partialList="true" size="${total}" pagesize="10">
                        <display:setProperty name="paging.banner.item_name" value="Sample"/>
                        <display:setProperty name="paging.banner.items_name" value="Uzorci"/>
                        <display:column title="ID" property="id" sortable="true" sortName="id"/>
                        <display:column title="Ime" property="patientName" sortable="true" sortName="patientName"/>
                        <display:column title="Prezime" property="patientSurname" sortable="true" sortName="patientSurame"/>                       
                        <display:column title="Grad" property="city" sortable="true" sortName="city"/>
                        <display:column title="Datum unosa" sortable="true" sortName="createdDate"> <fmt:formatDate type="date" value="${row.createdDate}" /> </display:column>
                        <display:column title="Uzorak unio" property="createdUser.username" sortable="true" sortName="createdUser.username"/>
                        <display:column title="Barcode" property="sampleCode" sortable="true" sortName="createdUser.username"/>
                        <display:column >  <i class="icon-edit icon-grey"></i>
                            <a href="/sample/edit/${row.id}"> Napravi izmjenu</a> |
                            <a href="/sample/barcode/view/${row.id}"> Pogledaj barcode</a> 
                            
                            <security:authorize ifAnyGranted="ROLE_SUPER_ADMIN">
                             |
                            <i class="icon-remove icon-grey"></i><a href="javascript:deleteItem(${row.id})"> Obriši sample</a>
                        	</security:authorize>
                        </display:column>
                    </display:table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <form name="delForm" action="/sample/delete/" method="post">
    </form>
    <!-- END CONTENT -->
</div>

    <%@include file="/WEB-INF/includes/footer.jsp" %>
    <%@include file="/WEB-INF/includes/commonJs.jsp" %>

    <!-- BEGIN PAGE LEVEL SCRIPTS -->
    <script src="/assets/scripts/app.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL SCRIPTS -->

    <script>
        $(document).ready(function () {
            App.init(); // initlayout and core plugins
            App.setActiveNavigation('/sample/list');
            App.activateSessionTimeoutTracking();
            App.activateSearchReset();
        });


    </script>
    <script type="text/javascript">
        function deleteItem(id) {
            document.forms['delForm'].selectedId.value = id;
            if (confirm('Are you sure you want to delete?')) {
                document.forms['delForm'].submit();
            }
        }
    </script>

    <%@include file="/WEB-INF/includes/pageEnd.jsp" %>
