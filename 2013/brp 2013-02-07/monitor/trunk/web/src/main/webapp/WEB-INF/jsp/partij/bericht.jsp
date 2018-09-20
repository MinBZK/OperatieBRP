<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="grid_16 alpha omega">

	<div id="controllePaneel">
		<div class="grid_5 alpha">
			<h2><a href="overzicht.html">Berichten per partij</a> > <a href="partij.html?partijId=${partij.ID}">${partij.naam}</a> > ${param.berichtId}</h2>
		</div>

		<div class="grid_2 prefix_8"></div>
	</div>

	<div class="grid_16 alpha omega">
		<hr />
	</div>

	<div class="grid_8 alpha">
		<div class="formSectie">
			<p>Inkomende bericht</p>
			<hr />

			<textarea class="subgrid_8 alpha omega big">
			${fn:escapeXml(inkomendeBericht.data)}
			</textarea>
		</div>
	</div>

	<div class="grid_8 alpha">
		<div class="formSectie">
			<p>Uitgaande bericht</p>
			<hr>
			
			<textarea class="subgrid_8 alpha omega big">
			${fn:escapeXml(uitgaandeBericht.data)}
			</textarea>
		</div>
	</div>