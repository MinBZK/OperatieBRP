<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="menu" uri="http://web.monitor.brp.bzk.nl/tags/menu"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<menu:menu level="1" cssClass="nav main">
    <spring:message code="menu.home"/>=<c:url value="/monitor/systeem.html"/>
    <spring:message code="menu.partijberichten"/>=<c:url value="/monitor/partij/overzicht.html"/>
</menu:menu>
