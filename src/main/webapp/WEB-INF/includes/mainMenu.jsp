<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<div class="nav-collapse collapse">
	<!-- BEGIN SIDEBAR MENU -->
	<ul class="nav">
		<li>
			<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
			<div class="sidebar-toggler hidden-phone"></div>
			<p></p> <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
		</li>
		<li>
			<!-- BEGIN RESPONSIVE QUICK SEARCH FORM --> <!-- END RESPONSIVE QUICK SEARCH FORM -->
		</li>
		<security:authorize ifAnyGranted="ROLE_SUPER_ADMIN">
			<li class="start" class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" data-hover="dropdown" data-delay="0"
				data-close-others="false" href="javascript:;"> <i
					class="icon-pencil"></i> <span class="title">Administracija</span>
					<span class="arrow "></span> <i class="icon-angle-down"></i>
			</a>
				<ul class="dropdown-menu">
					<li><a href="/lab/list"> Laboratorije</a></li>
					<li><a href="/user/list"> Korisnici</a></li>
				</ul></li>
		</security:authorize>
		<security:authorize
			ifAnyGranted="ROLE_ADMIN_KOMPANIJE, ROLE_KORISNIK_KOMPANIJE, ROLE_KORISNIK">
<%-- 			<li class=""><a href="/sample/list/${labObj.id}"> <i --%>
<!-- 					class="icon-building"></i> <span class="title">Evidencija -->
<!-- 						uzoraka</span> <span class="selected"></span> -->
<!-- 			</a> -->
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" data-hover="dropdown" data-delay="0"
				data-close-others="false" href="javascript:;"> <i
					class="icon-building"></i> <span class="title">Evidencija
						uzoraka</span> <span class="selected"></span>
			</a>
				<ul class="dropdown-menu">
					<security:authorize
						ifAnyGranted="ROLE_ADMIN_KOMPANIJE, ROLE_KORISNIK_KOMPANIJE, ROLE_KORISNIK">
						<li><a href="/sample/list/${labObj.id}"> <span
								class="title">Neobrađeni uzorci</span>
						</a></li>
						<li><a href="/sample/processedList/${labObj.id}"> <span
								class="title">Obrađeni uzorci</span>
						</a></li>
					</security:authorize>
				</ul></li>
		</security:authorize>
		<security:authorize ifAnyGranted="ROLE_ADMIN_KOMPANIJE">
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" data-hover="dropdown" data-delay="0"
				data-close-others="false" href="javascript:;"> <i
					class="icon-pencil"></i> <span class="title">Administracija</span>
					<span class="arrow "></span> <i class="icon-angle-down"></i>
			</a>
				<ul class="dropdown-menu">
					<security:authorize ifAnyGranted="ROLE_ADMIN_KOMPANIJE">
						<li><a href="/user/labUser/list/${labObj.id}"> <span
								class="title">Korisnici laboratorije</span>
						</a></li>
						<li><a href="/lab/labProfile/${labObj.id}"> <span
								class="title">Profil laboratorije</span>
						</a></li>
					</security:authorize>
				</ul></li>
		</security:authorize>
</div>