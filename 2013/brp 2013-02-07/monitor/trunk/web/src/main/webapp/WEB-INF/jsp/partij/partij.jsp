<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="grid_16 alpha omega">
	<script type="text/javascript">
		$(document).ready(function() {
			initKlikbaarTabelRegel();
		});
	</script>

	<div id="controllePaneel">
		<div class="grid_5 alpha">
			<h2><a href="overzicht.html">Berichten per partij</a> > ${partij.naam}</h2>
		</div>

		<div class="grid_2 prefix_8"></div>
	</div>

	<div class="grid_16 alpha omega">
		<hr />
	</div>

	<div class="grid_8 alpha">
		<div class="formSectie">
			<display:table name="${berichten}" requestURI="partij.html"
				id="resultaten">
				<display:column property="ID" titleKey="partij.bericht.id"
					url="/monitor/partij/bericht.html" paramId="berichtId"
					paramProperty="ID" />
				<display:column property="datumTijdOntvangst.time"
					titleKey="partij.bericht.datumTijdOntvangst" />
				<display:column property="datumTijdVerzenden.time"
					titleKey="partij.bericht.datumTijdVerzenden" />
				<display:column property="antwoordOp.ID"
					titleKey="partij.bericht.antwoordOp" />
				<display:column property="richting"
					titleKey="partij.bericht.richting" />
			</display:table>
		</div>
	</div>
</div>