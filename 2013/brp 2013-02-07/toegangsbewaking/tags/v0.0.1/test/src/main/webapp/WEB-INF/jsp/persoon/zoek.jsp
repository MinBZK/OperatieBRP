                
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1><fmt:message key="persoon.zoek.titel"/></h1>

<c:url var="zoekUrl" value="/persoon/zoek.html" /> 
<form:form method="post" modelAttribute="zoekForm" action="${zoekUrl}">
    <form:errors path="*" cssClass="errorBox" element="div"/>
    <fieldset>
        <div class="form-row">
            <label for="constraint"><fmt:message key="zoek.form.constraint"/>:</label>
            <span class="input full-form-input"><form:textarea cssClass="full-form-input" path="constraint" rows="3"/></span>
        </div>
        <div class="form-buttons">
            <div class="button"><input name="submit" type="submit" value="<fmt:message key="button.search"/>" /></div>
        </div>
    </fieldset>
</form:form>

<div style="float: left">
<c:if test="${not empty timing}">
	<h3>Timing</h3>
	Tokenizing: ${timing.tokenizing} ms<br/> 
	Parsing: ${timing.parsing} ms<br/> 

	<b>Total: ${timing.total} ms</b><br/> 	
</c:if>
</div>

<div style="clear: both">
<table class="zoek">
    <tr>
        <th><fmt:message key="persoon.form.bsn"/></th>
        <th><fmt:message key="persoon.form.geslachtsaand"/></th>
        <th><fmt:message key="persoon.form.geslnaam"/></th>
        <th><fmt:message key="persoon.form.gemByBijhgem"/></th>
        <th><fmt:message key="persoon.form.datgeboorte"/></th>
        <th><fmt:message key="persoon.form.adres.plaats"/></th>
        <th><img src="<c:url value="/images/edit.png"/>"/></th>
        <th><img src="<c:url value="/images/delete.png"/>"/></th>
    </tr>
<c:forEach var="persoon" items="${personen}" varStatus="status">
    <tr>
        <c:set var="personFormId" value="persoon${status.index}"/>

        <c:url var="editUrl" value="/persoon/form.html">
            <c:param name="id" value="${persoon.id}" />
        </c:url>
        <c:url var="deleteUrl" value="/persoon/delete.html"/>
        <form:form id="${personFormId}" action="${deleteUrl}" method="POST">
            <input id="id" name="id" type="hidden" value="${persoon.id}"/>
        </form:form>

    	<td>${persoon.bsn}</td>
    	<td>${persoon.geslachtsaand.naam}</td>
        <td>${persoon.geslnaam}</td> 
        <td>${persoon.gemByBijhgem.naam}</td>
        <td>${persoon.datgeboorte}</td>
        <td>
            <c:forEach var="adres" items="${persoon.persadreses}" varStatus="adresStatus">
                ${adres.plaats.naam}
            </c:forEach>
        </td>
    	<td><a href='<c:out value="${editUrl}"/>'><img src="<c:url value="/images/edit.png"/>"/></a></td>
        <td>
            <a href="javascript:document.forms['${personFormId}'].submit();"><img src="<c:url value="/images/delete.png"/>"/></a> 
        </td>
    </tr>
</c:forEach>
</table>

<c:if test="${not empty parsetree}">
	<h3>Parse Tree</h3>
	<textarea contenteditable="false" rows="30" cols="130">${parsetree}</textarea>
</c:if>
</div>
