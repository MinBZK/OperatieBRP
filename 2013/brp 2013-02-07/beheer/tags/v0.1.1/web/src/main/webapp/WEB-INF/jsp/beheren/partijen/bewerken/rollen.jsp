<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<formUtil:bind>
	<div id="toegevoegd" class="grid_6">
		<div class="formSectie">
			<p class="tabelKop"><spring:message code="veld.partij.partijrols"/></p>
			<hr class="tabelHr"/>
			<table class="selecteerbaar">
				<c:forEach items="${command.partij.partijrols}" var="partijrol">
					<tr>
						<td class=""><form:checkbox path="teVerwijderenRollen" value="${partijrol.rol.id}" disabled="${command.leesModus}"/> ${partijrol.rol.naam}</td>
					</tr>
				</c:forEach>
			</table>
			<c:if test="${!command.leesModus}">
				<div class="subprefix_4 subgrid_1"><input type="submit" id="knopRolToevoegen" value="+" name="_rolToevoegen" class="button-01 klein"/></div>
				<div class="subgrid_1 omega"><input type="submit" id="knopRolVerwijderen" value="-" name="_rolVerwijderen" class="button-01 klein"/></div>
				<script type="text/javascript">
					Spring.addDecoration(new Spring.AjaxEventDecoration(
						{
							elementId : "knopRolToevoegen",
							formId : "formbody",
							event : "onclick",
							params : {
								fragments : "tabInhoud,berichten"
							}
						}));
					
					Spring.addDecoration(new Spring.AjaxEventDecoration(
						{
							elementId : "knopRolVerwijderen",
							formId : "formbody",
							event : "onclick",
							params : {
								fragments : "tabInhoud,berichten"
							}
						}));				
				</script>
			</c:if>
		</div>
	</div>
	<div id="invoer" class="grid_6">
		<div class="formSectie">
			<p><spring:message code="veld.rollen.kop"/></p>
			<hr />
			<div class="rij">
				<span class="subgrid_2 alpha">
					<form:label for="toeTeVoegenRollen" path="toeTeVoegenRollen" cssErrorClass="error"><spring:message code="veld.rollen.toeTeVoegenRollen"/> </form:label>
					<form:errors path="toeTeVoegenRollen" />
				</span>
				<span class="subgrid_4 omega">
					<form:select cssClass="input multi" path="toeTeVoegenRollen" multiple="true" disabled="${command.leesModus}">
						<form:options items="${rollen}" itemLabel="naam" itemValue="id" />
					</form:select>
				</span>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			initSelecteerbaarTabel();
			initInvoerveldHighlight();
	 	});
	</script>
</formUtil:bind>