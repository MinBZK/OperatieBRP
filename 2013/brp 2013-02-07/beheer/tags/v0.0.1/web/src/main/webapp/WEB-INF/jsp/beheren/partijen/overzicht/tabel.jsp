<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<script type="text/javascript">
	$(document).ready(function() {
		$(".iconEdit").hide();
				
		//Zorgt ervoor dat er op de regel ge-clicked kan worden
		$("table tr").click(function() {
			var url = $(this).find("a").attr("href");
			
			if (url != null) {
				document.location = url;
			}
		})
	 });
</script>

<div id="tabel" class="grid_13 omega">
	<display:table name="${partijen}" requestURI="overzicht.html"
		id="resultaten">
		<display:column headerClass="subgrid_1 alpha" class="subgrid_1 alpha" property="id" title=""/>
		<display:column headerClass="subgrid_2" class="subgrid_2 bold" property="naam" titleKey="veld.naam"/>
		<display:column headerClass="subgrid_5" class="subgrid_5" titleKey="veld.beschrijving"/>
		<display:column headerClass="subgrid_2" class="subgrid_2" property="soort.naam" titleKey="veld.soort"/>
		<display:column headerClass="subgrid_2" class="subgrid_2" property="sector.naam" titleKey="veld.sector.naam"/>
		<display:column headerClass="subgrid_1 omega" class="subgrid_1 omega edit">
			<c:url
				value="/beheren/partijen/partij.html?id=${partijen.list[resultaten_rowNum-1].id}"
				var="url" />
			<a href="${url}" class="iconEdit"><span>&nbsp;</span></a>
		</display:column>
	</display:table>
</div>