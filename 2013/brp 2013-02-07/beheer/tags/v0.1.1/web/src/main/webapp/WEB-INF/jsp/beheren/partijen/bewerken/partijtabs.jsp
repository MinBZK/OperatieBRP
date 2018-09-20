<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="menu" uri="http://web.beheer.brp.bzk.nl/tags/menu"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="tabs">
	<menu:menu level="0" cssClass="nav tabs" liCssClass="subgrid_2">
	<spring:message code="tabs.partij.algemeen"/>=<c:url value="/beheren/partijen/partij.html" />
	<spring:message code="tabs.partij.rollen"/>=<c:url value="/beheren/partijen/rol.html" />
	<spring:message code="tabs.partij.authenticatie"/>=<c:url value="/beheren/partijen/authenticatiemiddel.html" />
	</menu:menu>
</div>