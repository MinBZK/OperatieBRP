<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="side-bar">
    <a href="<c:url value="/"/>">Home</a>
    
    <p><fmt:message key="persoon.form.titel"/></p>
        <a href="<c:url value="/persoon/form.html"/>"><fmt:message key="button.create"/></a> 
        <a href="<c:url value="/persoon/zoek.html"/>"><fmt:message key="button.search"/></a>
</div>
