<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<form:form id="formbody">
	<div class="grid_16 alpha omega">
		<tiles:insertAttribute name="controllePaneel"/>
	</div>

	<div class="grid_16 alpha omega">
		<hr />
	</div>

	<div class="grid_3 alpha " id="zijPaneel">
		<tiles:insertAttribute name="zijPaneel"/>
	</div>

	<div class="grid_13 omega" id="tabMenu">
		<tiles:insertAttribute name="tabMenu"/>
	</div>
	
	<tiles:insertAttribute name="tabInhoud" />
</form:form>