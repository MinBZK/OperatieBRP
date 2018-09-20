<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<div id="infoMessages">
	<ul class="messages">
		<c:forEach items="${infoMessages}" var="message">
			<c:if test="${message.severity == 'SUCCESS'}">
				<c:set var="icon" value="&radic;" />
			</c:if>
			<c:if test="${message.severity == 'INFO'}">
				<c:set var="icon" value="i" />
			</c:if>
			<c:if test="${message.severity == 'ERROR'}">
				<c:set var="icon" value="!" />
			</c:if>
			<li class="${message.severity}"><span>${icon}</span>${message.value}</li>
		</c:forEach>
		<spring:hasBindErrors name="command">
			<li class="ERROR"><span>!</span><spring:message code="error.algemeen"/></li>
		</spring:hasBindErrors>
	</ul>
</div>