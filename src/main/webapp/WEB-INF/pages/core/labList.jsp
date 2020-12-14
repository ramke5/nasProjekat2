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
                Laboratorije
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
                <li>Laboratorije</li>
            </ul>
        </div>
    </div>
    <div>
        <div class="row-fluid">
            <div class="span6">
                <a class="btn blue" href="/lab/create"><i class="icon-plus-sign"></i> Dodaj novu laboratoriju</a>
            </div>
            <div class="span6">
                <form name="search">
                    <div class="input-append pull-right">
                        <input name="q" class="m-wrap" size="10" type="text" value="${search}" ><button class="btn"><i class="icon-search"></i></button>
                        <c:if test="${not empty search}"><button id="searchReset" class="btn"><i class="icon-remove"></i></button></c:if>
                    </div>
                </form>
            </div>
        <div class="row-fluid">
            <div class="span12">
                <display:table list="${data}" uid="row" class="table" htmlId="labList" sort="external" requestURI="" partialList="true" size="${total}" pagesize="10">
                    <display:setProperty name="paging.banner.item_name" value="Company"/>
                    <display:setProperty name="paging.banner.items_name" value="Companies"/>
                    <display:column title="Ime laboratorije" property="name" sortable="true" sortName="name" sortProperty="name"/>
                    <display:column title="Adresa" property="contact.address1" sortable="true" sortName="contact.address1"/>
                    <display:column title="Grad" property="contact.city" sortable="true" sortName="contact.city"/>
                    <display:column title="Datum kreiranja" sortable="true" sortName="createdDate"> <fmt:formatDate type="date" value="${row.createdDate}"/> </display:column>
                    <display:column title="Laboratoriju kreirao" property="createdUser.username" sortable="false"></display:column>
                    <display:column >  <i class="icon-edit icon-grey"></i>
                        <a href="/lab/edit/${row.id}">Izmijeni laboratoriju</a> | <i class="icon-remove icon-grey"></i><a href="javascript:deleteItem((${row.id}))"> Obriši laboratoriju</a>
                    </display:column>
                    <display:column >  <i class="icon-user icon-grey"></i>
                        <a href="/lab/labAdministrator/list/${row.id}"> Administrator</a>
                    </display:column>
                </display:table>
            </div>
        </div>
    </div>
</div>
    <form name="delForm" action="/lab/delete" method="post">

        <input type="hidden" name="selectedId" value="">
    </form>
<!-- END CONTENT -->

<%@include file="/WEB-INF/includes/footer.jsp" %>
<%@include file="/WEB-INF/includes/commonJs.jsp" %>

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="/assets/scripts/app.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->

<script>
    $(document).ready(function () {
        App.init(); // initlayout and core plugins
        App.setActiveNavigation('/lab/list');
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
