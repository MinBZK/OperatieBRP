/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators.frontend.jsp;

import nl.bzk.brp.bmr.generator.generators.frontend.FrontEndGeneratorUtil;
import nl.bzk.brp.bmr.generator.generators.frontend.JspGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.MessagePropertiesGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.controller.DetailControllerGenerator;
import nl.bzk.brp.bmr.generator.utils.ArtifactBuilder;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.Formulier;
import nl.bzk.brp.ecore.bmr.Frame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Generator voor tab menu jsp.
 *
 */
@Component
public class JspTabMenuGenerator {

    private static final Logger LOGGER         = LoggerFactory.getLogger(JspTabMenuGenerator.class);

    private static final String TAB_JSP_SUFFIX = "tabs";

    private ArtifactBuilder     r;

    /**
     * Genereer de tabbladen.
     *
     * @param file FileSystemAccess
     * @param formulier de formulier waarvoor het gegenereerd moet worden
     */
    public void genereerTabMenu(final FileSystemAccess file, final Formulier formulier) {
        String tabFileNaam = getJspTabFileNaam(formulier);
        LOGGER.debug("Genereren tabMenu '{}'.", tabFileNaam);

        r = new ArtifactBuilder();

        r.regel("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>");
        r.regel("<%@ taglib prefix=\"menu\" uri=\"http://web.beheer.brp.bzk.nl/tags/menu\"%>");
        r.regel("<%@ taglib prefix=\"spring\" uri=\"http://www.springframework.org/tags\"%>");

        r.regel("<div id=\"tabs\">");
        r.incr();
        r.regel("<menu:menu level=\"0\" cssClass=\"nav tabs\" liCssClass=\"subgrid_2\">");
        r.incr();
        for (Frame frame : formulier.getFrames()) {
            r.regel("<spring:message code=\"", MessagePropertiesGenerator.getMessagePropertieTab(frame),
                    "\"/>=<c:url value=\"", DetailControllerGenerator.getRequestUrlVoorController(frame), "\" />");
        }
        r.decr();
        r.regel("</menu:menu>");
        r.decr();
        r.regel("</div>");

        file.generateFile(tabFileNaam, r);
    }

    /**
     * Bouwt de jsp bestands naam van de JSP tab.
     *
     * @param formulier Formulier
     * @return bestands naam van de jsp tab
     */
    public static String getJspTabFileNaam(final Formulier formulier) {
        return JspGenerator.JSP_LOCATION + FrontEndGeneratorUtil.getFormulierNaam(formulier).toLowerCase() + "/"
            + FrontEndGeneratorUtil.getFormulierNaam(formulier).toLowerCase() + TAB_JSP_SUFFIX
            + JspGenerator.JSP_EXTENSIE;
    }
}
