/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators.frontend.jsp;

import nl.bzk.brp.bmr.generator.generators.frontend.JspGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.MessagePropertiesGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.MvcControllerGenerator;
import nl.bzk.brp.bmr.generator.utils.ArtifactBuilder;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.Applicatie;
import nl.bzk.brp.ecore.bmr.Formulier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Generator die de hoofd menu jsp genereert.
 *
 */
@Component
public class JspHoofdMenuGenerator {

    private static final Logger LOGGER     = LoggerFactory.getLogger(JspHoofdMenuGenerator.class);

    private static final String JSP_COMMON = "WEB-INF/jsp/common/";

    private static final String MENU_JSP   = "menu";

    private ArtifactBuilder     r;

    /**
     * Genereer de jsp voor de top menu.
     *
     * @param file FileSystemAccess
     * @param applicatie de applicatie waarvoor het gegenereerd moet worden
     */
    public void genereerHoofdMenu(final FileSystemAccess file, final Applicatie applicatie) {
        String hoofdMenuFileNaam = JSP_COMMON + MENU_JSP + JspGenerator.JSP_EXTENSIE;
        LOGGER.debug("Genereren tabInhoud '{}'.", hoofdMenuFileNaam);

        r = new ArtifactBuilder();

        r.regel("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>");
        r.regel("<%@ taglib prefix=\"menu\" uri=\"http://web.beheer.brp.bzk.nl/tags/menu\"%>");
        r.regel("<%@ taglib prefix=\"spring\" uri=\"http://www.springframework.org/tags\"%>");
        r.regel();
        r.regel("<menu:menu level=\"1\" cssClass=\"nav main\">");
        r.incr();
        r.regel("<spring:message code=\"menu.home\"/>=<c:url value=\"/home.html\"/>");
        for (Formulier formulier : applicatie.getFormulieren()) {
            r.regel("<spring:message code=\"", MessagePropertiesGenerator.getMessagePropertieMenu(formulier),
                    "\"/>=<c:url value=\"", MvcControllerGenerator.getControllerRequestMappingUrl(formulier),
                    "/overzicht.html\"/>");
        }
        r.decr();
        r.regel("</menu:menu>");

        file.generateFile(hoofdMenuFileNaam, r);
    }
}
