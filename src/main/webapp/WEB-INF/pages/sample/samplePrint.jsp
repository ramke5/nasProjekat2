<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="f" tagdir="/WEB-INF/tags/bootstrap"%>
<%@include file="/WEB-INF/includes/pageBegin.jsp"%>
<%@include file="/WEB-INF/includes/commonCss.jsp"%>
<!-- BEGIN PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
</head>
<body>
	<%@include file="/WEB-INF/includes/header.jsp"%>

	<!-- BEGIN CONTENT -->
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<h3 class="page-title">
					Printanje nalaza <small></small>
				</h3>
				<ul class="breadcrumb">
					<li><i class="icon-home"></i> <a href="/">Početna stranica</a>
						<i class="icon-angle-right"></i></li>
					<li><a href="/sample/list/${labObj.id}">Evidencija uzoraka</a>
						<i class="icon-angle-right"></i></li>
					<li>Printanje nalaza</li>
				</ul>
			</div>
		</div>
		<div>
			<form:form id="form" method="post" modelAttribute="sample"
				class="form-horizontal">

				<fieldset>
					<legend>Podaci o pacijentu: ${sample.patientName}
						${sample.patientSurname}</legend>
					<form:hidden path="id" />
					<div class="row-fluid">
						<div class="span6">
							<f:input path="patientName" label="Ime" readonly="true" />
						</div>
						<div class="span6">
							<f:input path="patientSurname" label="Prezime" readonly="true" />
						</div>
					</div>
				</fieldset>
				<div class="form-actions clearfix">
					<p align="center">
						<a href="/sample/downloadresult/${sample.id}" target="_blank"
							class="btn btn-primary blue"><i class="icon-ok-sign"></i>
							Preuzmi nalaz</a> <a href="/sample/list/${labObj.id}"
							class="btn btn-primary grey"><i class="icon-remove-sign"></i>
							Idi na listu uzoraka</a>
					</p>
				</div>
			</form:form>
		</div>

	</div>
	<!-- END CONTENT -->

	<%@include file="/WEB-INF/includes/footer.jsp"%>
	<%@include file="/WEB-INF/includes/commonJs.jsp"%>

	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script type="text/javascript" src="/assets/scripts/app.js"></script>
	<script type="text/javascript" src="/js/ba/nalaz/sample.js"></script>
	<script type="text/javascript"
		src="/assets/plugins/append-grid/js/jquery-appendGrid-1.1.2.js"></script>
	<!-- END PAGE LEVEL SCRIPTS -->

	<script>
		$(document).ready(
				function() {
					App.init(); // initlayout and core plugins
					App.setActiveNavigation('/sample/list');
					App.activateSessionTimeoutTracking();
					Sample.setSampleValues('${sample.sampleType}',
							'${sample.analysisType}', '${sample.gender}',
							'${sample.method}', '${sample.eGenResult}',
							'${sample.nGenResult}', '${sample.overallResult}');
					Sample.setHelpText();
				});
	</script>

	<%@include file="/WEB-INF/includes/pageEnd.jsp"%>