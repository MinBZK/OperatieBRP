<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<formUtil:bind>
	<div id="invoer" class="grid_6">
		<div class="formSectie">
			<p><spring:message code="veld.authenticatiemiddel.kop"/></p>
			<hr />
			<div class="rij">
				<span class="subgrid_2 alpha">
					<form:label for="authenticatiemiddel.certificaatTbvSsl.subject" path="authenticatiemiddel.certificaatTbvSsl.subject" cssErrorClass="error">
						<spring:message code="veld.authenticatiemiddel.certificaatTbvSsl.subject"/>
					</form:label>
					<form:errors path="authenticatiemiddel.certificaatTbvSsl.subject" />
				</span>
				<span class="subgrid_4 omega">
					<form:input path="authenticatiemiddel.certificaatTbvSsl.subject" class="input" disabled="${command.leesModus}"/>
				</span>
			</div>
			<div class="rij">
				<span class="subgrid_2 alpha">
					<form:label for="authenticatiemiddel.certificaatTbvOndertekening.subject" path="authenticatiemiddel.certificaatTbvOndertekening.subject" cssErrorClass="error">
						<spring:message code="veld.authenticatiemiddel.certificaatTbvOndertekening.subject"/>
					</form:label>
					<form:errors path="authenticatiemiddel.certificaatTbvOndertekening.subject" />				
				</span>
				<span class="subgrid_4 omega">
					<form:input	path="authenticatiemiddel.certificaatTbvOndertekening.subject" class="input"  disabled="${command.leesModus}"/>
				</span>
			</div>
			<div class="rij">
				<span class="subgrid_2 alpha">
					<label><spring:message code="veld.authenticatiemiddel.rol"/></label>
				</span>
				<span class="subgrid_4 omega">
					<form:select cssClass="input" path="authenticatiemiddel.rol" disabled="${command.leesModus}">
						<form:option value="" disabled="${!command.rolGeenToegestaan}"><spring:message code="veld.waarde.geen" /></form:option>
						<form:options items="${command.partij.partijrols}"
							itemLabel="rol.naam" itemValue="rol.id" disabled="${!command.rolToegestaan}"/>
					</form:select>	
				</span>			
			</div>						
		</div>
	</div>
	<div id="toegevoegd" class="grid_6">
		<div class="formSectie">
			<p class="tabelKop"><spring:message code="veld.partij.authenticatiemiddels"/></p>
			<hr class="tabelHr"/>
			<table class="selecteerbaar">
				<c:forEach items="${command.partij.authenticatieMiddels}" var="partijrol" varStatus="status">
					<tr>
						<td class="subgrid_2 alpha"><form:checkbox path="teVerwijderenAuthmiddelen" value="${status.index}" /> 
							<c:if test="${partijrol.rol.naam == null}">
								<spring:message code="veld.waarde.geen" />
							</c:if>
							<c:if test="${partijrol.rol.naam != null}">
								${partijrol.rol.naam}
							</c:if>
						</td>
						<td class="subgrid_2 alpha">${partijrol.certificaatTbvSsl.subject}</td>
						<td class="subgrid_2 omega">${partijrol.certificaatTbvOndertekening.subject}</td>
					</tr>
				</c:forEach>
			</table>
			<c:if test="${!command.leesModus}">
				<div class="subprefix_4 subgrid_1"><input type="submit" id="knopAutToevoegen" value="+" name="_addAuth" class="button-01 klein"/></div>
				<div class="subgrid_1 omega"><input type="submit" id="knopAutVerwijderen" value="-" name="_deleteAuth" class="button-01 klein"/></div>
				<script type="text/javascript">
					Spring.addDecoration(new Spring.AjaxEventDecoration(
						{
							elementId : "knopAutToevoegen",
							formId : "formbody",
							event : "onclick",
							params : {
								fragments : "tabInhoud,berichten"
							}
						}));
					
					Spring.addDecoration(new Spring.AjaxEventDecoration(
						{
							elementId : "knopAutVerwijderen",
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
	<script type="text/javascript">
		$(document).ready(function() {
			initSelecteerbaarTabel();
			initInvoerveldHighlight();
	 	});
	</script>
</formUtil:bind>
