<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="controllePaneel">
	<div class="grid_5 alpha">
		<spring:message code="entity.${param['entity']}" var="entity"/>
		<h2><spring:message code="kop.actie.overzicht" arguments="${entity}"/></h2>
	</div>
	<div class="grid_3">
		<input type="text" name="zoekterm" />
		<input type="hidden" name="pagina" value="${resultaat.pageNumber}" />
	</div>
	<div class="grid_1">
		<input type="submit" id="knopZoek" class="button-01 default" value="Zoek"
			name="_zoek" />
	</div>
	<div class="grid_2">
		<spring:message code="overzicht.paginering" arguments="${resultaat.currentPageFirstItem}, ${resultaat.currentPageLastItem}, ${resultaat.fullListSize}"/>
	</div>
	<div class="grid_1">
		<c:if test="${resultaat.firstPage}">
			<c:set var="eerste" value="disabled=\"disabled\"" />
		</c:if>
		<input type="submit" id="knopVorige" class="button-01" value="&lt;&lt;"
			name="_vorige" ${eerste}/>
	</div>
	<div class="grid_1">
		<c:if test="${resultaat.lastPage}">
			<c:set var="laatste" value="disabled=\"disabled\"" />
		</c:if>
		<input type="submit" id="knopVolgende" class="button-01" value="&gt;&gt;"
			name="_volgende" ${laatste}/>
	</div>
	<div class="grid_2 omega">
		<a href="<c:url value="${param['detailUrl']}?_nieuw"/>">
			<span class="button-01"><spring:message code="knop.toevoegen"/></span>
		</a>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			initVoorkomenEnterToets();
	 	});
	</script>
</div>