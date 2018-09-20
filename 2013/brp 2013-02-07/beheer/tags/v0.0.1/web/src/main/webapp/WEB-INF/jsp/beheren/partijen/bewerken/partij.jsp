<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<formUtil:bind>
	<div id="invoer" class="grid_6">
		<div class="formSectie">
			<p><spring:message code="veld.partij.kop"/></p>
			<hr />
			<div class="rij">
				<span class="subgrid_2 alpha">
					<form:label cssClass="" for="partij.naam" path="partij.naam" cssErrorClass="error">
						<spring:message code="veld.partij.naam"/>
					</form:label>
					<form:errors path="partij.naam" />
				</span>
				<span class="subgrid_4 omega">
					<form:input path="partij.naam" type="text" class="input" disabled="${command.leesModus}"/>
				</span>
			</div>
			<div class="rij">
				<span class="subgrid_2 alpha">
					<form:label for="partij.soort" path="partij.soort" cssErrorClass="error">
						<spring:message code="veld.partij.soort"/>
					</form:label> 
					<form:errors path="partij.soort" /> 
				</span>
				<span class="subgrid_4 omega">
					<form:select cssClass="input" path="partij.soort" disabled="${command.leesModus}">
						<form:option value="">Selecteer Soort</form:option>
						<form:options items="${srtPartijen}" itemLabel="naam" itemValue="id" />
					</form:select>
				</span>
			</div>
			<div class="rij">
				<span class="subgrid_2 alpha">
					<form:label for="partij.sector" path="partij.sector" cssErrorClass="error">
						<spring:message code="veld.partij.sector"/>
					</form:label>
					<form:errors path="partij.sector" />
				 </span>
				 <span class="subgrid_4 omega">
					<form:select cssClass="input" path="partij.sector" disabled="${command.leesModus}">
						<form:option value="">Selecteer Sector</form:option>
						<form:options items="${partijSector}" itemLabel="naam" itemValue="id" />
					</form:select>
				</span>
			</div>
			<div class="rij">
				<span class="subgrid_2 alpha">
					<form:label for="partij.dataanv" path="partij.dataanv" cssErrorClass="error">
						<spring:message code="veld.partij.dataanv"/>
					</form:label>
					<form:errors path="partij.dataanv" />
				</span>
				<span class="subgrid_4 omega">
					<form:input cssClass="input" path="partij.dataanv" maxlength="8" disabled="${command.leesModus}"/>
				</span>
			</div>
			<div class="rij">
				<span class="subgrid_2 alpha">
					<form:label for="partij.dateinde" path="partij.dateinde" cssErrorClass="error">
						<spring:message code="veld.partij.dateinde"/>
					</form:label>
					<form:errors path="partij.dateinde" />
				</span>
				<span class="subgrid_4 omega">
					<form:input cssClass="input" path="partij.dateinde" maxlength="8" disabled="${command.leesModus}"/>
				</span>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			initInvoerveldHighlight();
		});
	</script>	
</formUtil:bind>