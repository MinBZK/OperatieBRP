<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="controllePaneel">
	<div class="grid_5 alpha">
		<h2>
			<spring:message code="entity.${param['entity']}" var="entity"/>
			<c:choose>
				<c:when test="${!leesModus}">
					<spring:message code="kop.actie.toevoegen" arguments="${entity}"/>
				</c:when>
				<c:otherwise>
					<c:if test="${!command.leesModus}">
						<spring:message code="kop.actie.wijzigen" arguments="${entity}"/>
					</c:if>
					<c:if test="${command.leesModus}">
						<spring:message code="kop.actie.raadplegen" arguments="${entity}"/>
					</c:if>					
				</c:otherwise>
			</c:choose>
		</h2>
	</div>
	
	<div class="grid_2 prefix_8">
		<c:if test="${!command.leesModus}">
			<spring:message code="knop.opslaan" var="label"/>
			<input type="submit" id="knopActie" value="${label}" class="button-01" name="_opslaan" />
		</c:if>
		<c:if test="${command.leesModus}">
			<spring:message code="knop.wijzigen" var="label"/>
			<input type="submit" id="knopActie" value="${label}" class="button-01" name="_wijzigen" />
		</c:if>		
	</div>
	
	<script type="text/javascript">
		Spring.addDecoration(new Spring.AjaxEventDecoration({
			elementId : "knopActie",
			formId : "formbody",
			event : "onclick",
			params : {
				fragments : "controllePaneel,tabInhoud,berichten"
			}
		}));
	</script>
</div>
