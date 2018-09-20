/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators.frontend.jsp;

import nl.bzk.brp.bmr.generator.generators.frontend.FrontEndGeneratorUtil;
import nl.bzk.brp.bmr.generator.generators.frontend.JspGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.MessagePropertiesGenerator;
import nl.bzk.brp.bmr.generator.utils.ArtifactBuilder;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.Formulier;
import nl.bzk.brp.ecore.bmr.FrameVeld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Generator voor de tabel in de overzicht scherm.
 *
 */
@Component
public class JspTabelGenerator {

    private static final Logger LOGGER    = LoggerFactory.getLogger(JspGenerator.class);

    private static final String TABEL_JSP = "tabel";

    private ArtifactBuilder     r;

    /**
     * Genereert de tabel voor de overzicht pagina.
     *
     * @param file FileSystemAccess
     * @param formulier Formulier
     */
    public void genereerTabel(final FileSystemAccess file, final Formulier formulier) {
        String tabelFileNaam = getJspTabelFileNaam(formulier);
        LOGGER.debug("Genereren tabel '{}'.", tabelFileNaam);

        r = new ArtifactBuilder();

        r.regel("<%@ include file=\"/WEB-INF/jsp/common/taglibs.jsp\" %>");
        r.regel("");
        r.regel("<script type=\"text/javascript\">");
        r.incr();
        r.regel("$(document).ready(function() {");
        r.incr().regel("initKlikbaarTabelRegel();").decr();
        r.regel("});");
        r.decr();
        r.regel("</script>");
        r.regel("");
        r.regel("<div id=\"tabel\" class=\"grid_13 omega\">");
        r.incr().regel("<display:table name=\"${resultaat}\" requestURI=\"overzicht.html\"");
        r.incr().regel("id=\"resultaten\">").decr();

        r.incr();
        for (FrameVeld veld : formulier.getFrames().get(0).getVelden()) {
            if (FrontEndGeneratorUtil.isEnumVeld(veld)) {
                r.regel("<display:column headerClass=\"\" class=\"\"titleKey=\"",
                        MessagePropertiesGenerator.getMessagePropertieVeld(veld), "\">");
                r.incr()
                        .regel("<spring:message code=\"", MessagePropertiesGenerator.getEnumPrefix(veld),
                                "${resultaat.list[resultaten_rowNum-1].", FrontEndGeneratorUtil.getAttribuutNaam(veld),
                                "}\" text=\"\"/>").decr();
                r.regel("</display:column>");
            } else if (FrontEndGeneratorUtil.isDynamischVeld(veld)) {
                r.regel("<display:column headerClass=\"\" class=\"\" property=\"",
                        JspGenerator.getAttribuutPadVanObjectVeld(veld, null), "\" titleKey=\"",
                        MessagePropertiesGenerator.getMessagePropertieVeld(veld), "\"/>");
            } else {
                r.regel("<display:column headerClass=\"\" class=\"\" property=\"",
                        FrontEndGeneratorUtil.getAttribuutNaam(veld), "\" titleKey=\"",
                        MessagePropertiesGenerator.getMessagePropertieVeld(veld), "\"/>");
            }
        }

        r.regel("<display:column headerClass=\"subgrid_1 omega\" class=\"subgrid_1 omega edit\">");
        r.incr();
        r.regel("<c:url value=\"/beheren/partij/partij.html?id="
            + "${resultaat.list[resultaten_rowNum-1].ID}\" var=\"url\" />");
        r.regel("<a href=\"${url}\" class=\"iconEdit\"><span>&nbsp;</span></a>");
        r.decr();
        r.regel("</display:column>");
        r.decr();
        r.regel("</display:table>");
        r.decr();
        r.regel("</div>");

        file.generateFile(tabelFileNaam, r);
    }

    /**
     * Bouwt de jsp bestands naam van de JSP tabel.
     *
     * @param formulier Formulier
     * @return bestands naam van de jsp tabel
     */
    public static String getJspTabelFileNaam(final Formulier formulier) {
        return JspGenerator.JSP_LOCATION + FrontEndGeneratorUtil.getFormulierNaam(formulier).toLowerCase() + "/"
            + TABEL_JSP + JspGenerator.JSP_EXTENSIE;
    }
}
