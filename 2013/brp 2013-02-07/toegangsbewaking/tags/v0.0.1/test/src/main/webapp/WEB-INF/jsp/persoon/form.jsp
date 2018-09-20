<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1><fmt:message key="persoon.form.titel"/></h1>

<c:url var="url" value="/persoon/form.html" /> 
<form:form action="${url}" commandName="persoon" modelAttribute="persoon">
<form:errors path="*" cssClass="errorBox" element="div"/>
    <form:hidden path="id" />

    <fieldset>
        <div class="form-row">
            <label for="bsn"><fmt:message key="persoon.form.bsn"/>:</label>
            <span class="input"><form:input path="bsn" /></span>
        </div>
        <div class="form-row">
            <label for="anr"><fmt:message key="persoon.form.anr"/>:</label>
            <span class="input"><form:input path="anr" /></span>
        </div>
        <div class="form-row">
            <label for="geslachtsaand"><fmt:message key="persoon.form.geslachtsaand"/>:</label>
            <span class="input">
            	<form:select path="geslachtsaand">
            	    <form:options items="${geslachtsAanduidingen}" itemLabel="naam" itemValue="code"/>
            	</form:select>
            </span>
        </div>
        <div class="form-row">
            <label for="geslnaam"><fmt:message key="persoon.form.geslnaam"/>:</label>
            <span class="input"><form:input path="geslnaam" /></span>
        </div>
        <div class="form-row">
            <label for="indafgeleid"><fmt:message key="persoon.form.indafgeleid"/>:</label>
            <span class="input">
            	<form:select path="indafgeleid">
            		<form:option value="J"><fmt:message key="form.indicatie.J" /></form:option>
            		<form:option value="N"><fmt:message key="form.indicatie.N" /></form:option>
            	</form:select>
            </span>
        </div>
        <div class="form-row">
            <label for="geslnaamaanschr"><fmt:message key="persoon.form.geslnaamaanschr"/>:</label>
            <span class="input"><form:input path="geslnaamaanschr" /></span>
        </div>
        <div class="form-row">
            <label for="indaanschrafgeleid"><fmt:message key="persoon.form.indaanschrafgeleid"/>:</label>
            <span class="input">
            	<form:select path="indaanschrafgeleid">
            		<form:option value="J"><fmt:message key="form.indicatie.J" /></form:option>
            		<form:option value="N"><fmt:message key="form.indicatie.N" /></form:option>
            	</form:select>
            </span>
        </div>
        <div class="form-row">
            <label for="gemByBijhgem"><fmt:message key="persoon.form.gemByBijhgem"/>:</label>
            <span class="input">
            	<form:select path="gemByBijhgem">
            	    <form:options items="${gemeentes}" itemLabel="naam" itemValue="id"/>
            	</form:select>
            </span>
        </div>
        <div class="form-row">
            <label for="verantwoordelijke"><fmt:message key="persoon.form.verantwoordelijke"/>:</label>
            <span class="input">
            	<form:select path="verantwoordelijke">
            	    <form:options items="${verantwoordelijken}" itemLabel="naam" itemValue="id"/>
            	</form:select>
            </span>
        </div>
        
        <div class="form-row">
            <label for="datgeboorte"><fmt:message key="persoon.form.datgeboorte"/>:</label>
            <span class="input"><form:input path="datgeboorte" /></span>
        </div>
        <div class="form-row">
            <label for="landByLandgeboorte"><fmt:message key="persoon.form.landgeboorte"/>:</label>
            <span class="input">
            	<form:select path="landByLandgeboorte">
            	    <form:options items="${landen}" itemLabel="naam" itemValue="id"/>
            	</form:select>
            </span>
        </div>
        <div class="form-row">
            <label for="indgegevensinonderzoek"><fmt:message key="persoon.form.indgegevensinonderzoek"/>:</label>
            <span class="input">
            	<form:select path="indgegevensinonderzoek">
            		<form:option value="J"><fmt:message key="form.indicatie.J" /></form:option>
            		<form:option value="N"><fmt:message key="form.indicatie.N" /></form:option>
            	</form:select>
            </span>
        </div>
        <div class="form-row">
            <label for="datinschr"><fmt:message key="persoon.form.datinschr"/>:</label>
            <span class="input"><form:input path="datinschr" /></span>
        </div>
        <div class="form-row">
            <label for="datvestiginginnederland"><fmt:message key="persoon.form.datvestiginginnederland"/>:</label>
            <span class="input"><form:input path="datvestiginginnederland" /></span>
        </div>
        <div class="form-row">
            <label for="datinschringem"><fmt:message key="persoon.form.datinschringem"/>:</label>
            <span class="input"><form:input path="datinschringem" /></span>
        </div>
        <br/>
        <hr/>
        <div class="form-row">
            <label for="persadreses[0].functieadres"><fmt:message key="persoon.form.adres.functie"/>:</label>
            <span class="input">
                <form:select path="persadreses[0].functieadres">
            	    <form:options items="${adresFuncties}" itemLabel="naam" itemValue="id"/>
            	</form:select>
            </span>
        </div>
        <div class="form-row">
            <label for="persadreses[0].postcode"><fmt:message key="persoon.form.adres.postcode"/>:</label>
            <span class="input"><form:input path="persadreses[0].postcode" /></span>
        </div>
        <div class="form-row">
            <label for="persadreses[0].huisnr"><fmt:message key="persoon.form.adres.huisnr"/>:</label>
            <span class="input"><form:input path="persadreses[0].huisnr" /></span>
        </div>
        <div class="form-row">
            <label for="persadreses[0].plaats"><fmt:message key="persoon.form.adres.plaats"/>:</label>
            <span class="input">
                <form:select path="persadreses[0].plaats">
            	    <form:options items="${plaatsen}" itemLabel="naam" itemValue="id"/>
            	</form:select>
            </span>
        </div>
        <div class="form-row">
            <label for="persadreses[0].gem"><fmt:message key="persoon.form.adres.gem"/>:</label>
            <span class="input">
                <form:select path="persadreses[0].gem">
            	    <form:options items="${gemeentes}" itemLabel="naam" itemValue="id"/>
            	</form:select>
            </span>
        </div>
        <div class="form-row">
            <label for="persadreses[0].land"><fmt:message key="persoon.form.adres.land"/>:</label>
            <span class="input">
                <form:select path="persadreses[0].land">
            	    <form:options items="${landen}" itemLabel="naam" itemValue="id"/>
            	</form:select>
            </span>
        </div>
        
        <div class="form-buttons">
            <div class="button"><input name="submit" type="submit" value="<fmt:message key="button.save"/>" /></div>
        </div>
    </fieldset>
</form:form>