<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="brp" uri="http://nl.bzk.brp.preview/TagFuncties"%>
<%@ attribute name="betrokkene" required="true"
	type="nl.bzk.brp.model.data.kern.Betr"%>

<div>
	<table>
		<tr>
			<td class="label">Datum aanvang</td>
			<td class="data"><c:out value="${brp:datum(betrokkene.relatie.dataanv)}" /></td>
			<td class="label">Naam</td>
			<td class="data"><c:out value="${betrokkene.betrokkene.voornamen} ${betrokkene.betrokkene.voorvoegsel} ${betrokkene.betrokkene.geslnaam}" />
			</td>
		</tr>
		<tr>
			<td class="label">BSN</td>
			<td class="data">
				<a href="<c:url value='/dashboard/bevraging?bsn=${betrokkene.betrokkene.bsn}' />">
					<c:out value="${betrokkene.betrokkene.bsn}" />
				</a>
			</td>
			<td class="label">Datum geboorte</td>
			<td class="data"><c:out value="${brp:datum(betrokkene.betrokkene.datgeboorte)}" /></td>
		</tr>
		<tr>
			<td class="label">Geslacht</td>
			<td class="data"><c:out value="${betrokkene.betrokkene.geslachtsaand.naam}" /></td>
			<td class="label">Plaats geboorte</td>
			<td class="data"><c:out value="${betrokkene.betrokkene.wplgeboorte.naam}" /></td>
		</tr>
		<tr>
			<td class="label">&nbsp;</td>
			<td class="data">&nbsp;</td>
			<td class="label">Land geboorte</td>
			<td class="data"><c:out value="${betrokkene.betrokkene.landgeboorte.naam}" /></td>
		</tr>
	</table>
</div>
