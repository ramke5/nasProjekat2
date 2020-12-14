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
                Korisnici
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
                <li>Korisnici</li>
            </ul>
        </div>
    </div>
    <div>
        <div class="row-fluid">
            <div class="span6">
                <a class="btn blue" href="/user/create"><i class="icon-plus-sign"></i> Dodaj novog korisnika</a>
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
                <display:table list="${data}" uid="row" class="table" sort="external" requestURI="" partialList="true" size="${total}" pagesize="10">
                    <display:setProperty name="paging.banner.item_name" value="User"/>
                    <display:setProperty name="paging.banner.items_name" value="Users"/>
                    <display:column title="Korisničko ime" property="username" sortable="true" sortName="username"/>
                    <display:column title="Ime" property="firstName" sortable="true" sortName="firstName"/>
                    <display:column title="Prezime" property="lastName" sortable="true" sortName="lastName"/>
                     <display:column title="Aktivan korisnik" sortable="true" sortName="enabled">
                         <c:if
                                 test="${row.enabled == true}"><c:out value="Da"/></c:if>
                         <c:if
                                 test="${row.enabled == false}"><c:out value="Ne"/></c:if>
                     </display:column>
                    <display:column >  <i class="icon-edit icon-grey"></i>
                        <a href="/user/edit/${row.id}"> Izmijeni korisnika</a> | <i class="icon-remove icon-grey"></i><a href="javascript:deleteItem((${row.id}))"> Obriši korisnika</a>
                    </display:column>
                </display:table>
            </div>
        </div>
    </div>
</div>
    <form name="delForm" action="/user/delete" method="post">

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
        App.setActiveNavigation('/user/list');
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
