<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="controllePaneel">
	<div class="grid_5 alpha">
		<spring:message code="entity.Partij" var="entity"/>
		<h2><spring:message code="kop.actie.overzicht" arguments="${entity}"/></h2>
	</div>
	<div class="grid_3">
		<form:input path="naam" />
		<input type="hidden" name="pagina" value="${partijen.pageNumber}" />
	</div>
	<div class="grid_1">
		<input type="submit" id="knopZoek" class="button-01 default" value="Zoek"
			name="_zoek" />
	</div>
	<div class="grid_2">
		<spring:message code="overzicht.paginering" arguments="${partijen.currentPageFirstItem}, ${partijen.currentPageLastItem}, ${partijen.fullListSize}"/>
	</div>
	<div class="grid_1">
		<c:if test="${partijen.firstPage}">
			<c:set var="eerste" value="disabled=\"disabled\"" />
		</c:if>
		<input type="submit" id="knopVorige" class="button-01" value="&lt;&lt;"
			name="_vorige" ${eerste}/>
	</div>
	<div class="grid_1">
		<c:if test="${partijen.lastPage}">
			<c:set var="laatste" value="disabled=\"disabled\"" />
		</c:if>
		<input type="submit" id="knopVolgende" class="button-01" value="&gt;&gt;"
			name="_volgende" ${laatste}/>
	</div>
	<div class="grid_2 omega">
		<a href="<c:url value="/beheren/partijen/partij.html?_nieuw"/>">
			<span class="button-01"><spring:message code="knop.toevoegen"/></span>
		</a>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			initVoorkomenEnterToets();
	 	});
	</script>
</div>