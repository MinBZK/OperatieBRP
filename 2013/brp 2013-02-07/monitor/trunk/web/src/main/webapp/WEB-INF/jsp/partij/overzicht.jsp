<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="grid_16 alpha omega">
	<script type="text/javascript">
		$(document).ready(function() {
			initKlikbaarTabelRegel();
		});
	</script>

	<div id="controllePaneel">
		<div class="grid_5 alpha">
			<h2>Berichten per partij</h2>
		</div>

		<div class="grid_2 prefix_8"></div>
	</div>

	<div class="grid_16 alpha omega">
		<hr />
	</div>

	<div class="grid_8 alpha">
		<div class="formSectie">
			<display:table name="${berichtenPerPartij}" id="resultaten">
				<display:column property="partijId" titleKey="overzicht.partij.id"
					url="/monitor/partij/partij.html" paramId="partijId"
					paramProperty="partijId" />
				<display:column property="partijNaam"
					titleKey="overzicht.partij.naam" />
				<display:column property="aantal" titleKey="overzicht.partij.aantal" />
				<display:column property="tijdLaatsteBericht.time"
					titleKey="overzicht.partij.laatste" />
			</display:table>
		</div>
	</div>
	<div class="grid_8 omega">
		<div class="formSectie">
			<script type="text/javascript">
				google.setOnLoadCallback(drawChart);
				function drawChart() {
					var data = new google.visualization.DataTable(${jsonData})

					var chart = new google.visualization.ColumnChart(document
							.getElementById('chart_div'));
					chart.draw(data);
				}
			</script>

			<div id="chart_div" style="height: 400px;"></div>
		</div>
	</div>
</div>