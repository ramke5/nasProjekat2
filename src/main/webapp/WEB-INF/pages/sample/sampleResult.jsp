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
					Napravi izmjenu na postojećem uzorku <small></small>
				</h3>
				<ul class="breadcrumb">
					<li><i class="icon-home"></i> <a href="/">Početna stranica</a>
						<i class="icon-angle-right"></i></li>
					<li><a href="/sample/list/${labObj.id}">Evidencija uzoraka</a>
						<i class="icon-angle-right"></i></li>
					<li>Napravi izmjenu</li>
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
					<%--<form:hidden path="county" />--%>
					<div class="row-fluid">
						<div class="span6">
							<f:input path="patientName" label="Ime" required="true" />
						</div>
						<div class="span6">
							<f:input path="patientSurname" label="Prezime" required="true" />
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<f:input path="birthDate" label="Datum rođenja" required="true" />
						</div>
						<div class="span6">
							<f:select path="gender" label="Spol" required="true">
								<option value="">Izaberi</option>
								<option value="Ženski">Ženski</option>
								<option value="Muški">Muški</option>
							</f:select>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<f:input path="address" label="Adresa" required="true" />
						</div>
						<div class="span6">
							<f:input path="city" label="Grad" required="true" />
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<f:input path="phoneNumber" label="Broj telefona" required="true" />
						</div>
						<div class="span6">
							<f:input path="email" label="Email" />
						</div>
					</div>
					<legend>Podaci o uzorku: ${sample.sampleCode}</legend>
					<div class="row-fluid">
						<div class="span6">
							<f:select path="sampleType" label="Tip uzorka" required="true">
								<option value="">Izaberi</option>
								<option value="Bris nazofarinksa i orofarinksa">Bris
									nazofarinksa i orofarinksa</option>
								<option value="Tip 2">Tip 2</option>
								<option value="Tip 3">Tip 3</option>
							</f:select>
						</div>
						<div class="span6">
							<f:select path="analysisType" label="Vrsta analize"
								required="true">
								<option value="">Izaberi</option>
								<option value="SARS-CoV-2">SARS-CoV-2</option>
								<option value="Analiza 2">Analiza 2</option>
								<option value="Analiza 3">Analiza 3</option>
							</f:select>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<f:select path="method" label="Metod rada" required="true">
								<option value="">Izaberi</option>
								<option value="RT-PCR">RT-PCR</option>
								<option value="Metod 2">Metod 2</option>
								<option value="Metod 3">Metod 3</option>
							</f:select>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<f:textarea path="analysisReason" label="Razlog testiranja"
								required="true">
							</f:textarea>
						</div>
						<div class="span6">
							<f:textarea path="note" label="Napomena">
							</f:textarea>
						</div>
					</div>
					<legend>Analiza uzorka</legend>
					<div class="row-fluid">
						<div class="span6">
							<f:select path="overallResult" label="Rezultat">
								<option class="font-weight-bold" value="">Izaberi</option>
								<option value="Negativan">Negativan</option>
								<option value="Pozitivan">Pozitivan</option>
							</f:select>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<f:select path="eGenResult" label="E GEN">
								<option value="">Izaberi</option>
								<option value="Negativan">Negativan</option>
								<option value="Pozitivan">Pozitivan</option>
							</f:select>
						</div>
						<div class="span6">
							<f:select path="nGenResult" label="N GEN">
								<option value="">Izaberi</option>
								<option value="Negativan">Negativan</option>
								<option value="Pozitivan">Pozitivan</option>
							</f:select>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
							<f:textarea path="analysisPurpose" label="Cilj analize">
							</f:textarea>
						</div>
					</div>

				</fieldset>
				<div class="form-actions clearfix">
					<p>
						<button type="submit" class="btn btn-primary blue">
							<i class="icon-ok-sign"></i> Spasi izmjene
						</button>
						<a href="/sample/list/${labObj.id}" class="btn btn-primary grey"><i
							class="icon-remove-sign"></i> Poništi sve izmjene</a>
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