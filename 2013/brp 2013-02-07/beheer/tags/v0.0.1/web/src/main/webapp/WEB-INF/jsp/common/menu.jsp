<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="menu" uri="http://web.beheer.brp.bzk.nl/tags/menu"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<menu:menu level="1" cssClass="nav main">
	<spring:message code="menu.home"/>=<c:url value="/home.html"/>
	<spring:message code="menu.beheer"/>=<c:url value="/beheren/partijen/overzicht.html"/>
</menu:menu>