/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators.frontend.jsp;

import static java.beans.Introspector.decapitalize;
import nl.bzk.brp.bmr.generator.generators.frontend.FrontEndGeneratorUtil;
import nl.bzk.brp.bmr.generator.generators.frontend.JspGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.MessagePropertiesGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.MvcFormBeanGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.controller.DetailControllerGenerator;
import nl.bzk.brp.bmr.generator.utils.ArtifactBuilder;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.Formulier;
import nl.bzk.brp.ecore.bmr.Frame;
import nl.bzk.brp.ecore.bmr.FrameVeld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Generator voor de inhoud van een tab blad.
 *
 */
@Component
public class JspTabInhoudGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(JspTabInhoudGenerator.class);

    private ArtifactBuilder     r;

    /**
     * Genereer tab inhoud, dit is de invoerscherm.
     *
     * @param file FileSystemAccess
     * @param formulier de formulier waarvoor het gegenereerd moet worden
     */
    public void genereerTabInhoud(final FileSystemAccess file, final Formulier formulier) {
        for (Frame frame : formulier.getFrames()) {
            String frameBron = FrontEndGeneratorUtil.getFrameBron(frame);

            String tabInhoudFileNaam = getJspTabInhoudFileNaam(frame);
            LOGGER.debug("Genereren tabInhoud '{}'.", tabInhoudFileNaam);
            r = new ArtifactBuilder();

            r.regel("<%@ include file=\"/WEB-INF/jsp/common/taglibs.jsp\" %>");

            r.regel("<formUtil:bind>");
            r.incr();
            r.regel("<div id=\"links\" class=\"grid_6\">");
            r.incr();
            r.regel("<div class=\"formSectie\">");
            r.incr();
            r.regel("<spring:message code=\"entity.", frameBron, "\" var=\"argument\"/>");
            r.regel("<p><spring:message code=\"veld.kop\" arguments=\"${argument}\"/></p>");
            r.regel("<hr />");

            // Genereer de velden
            genereerTabInhoudVeldenKolomLinks(frame);

            r.decr();
            r.regel("</div>");
            r.decr();
            r.regel("</div>");

            if (!FrontEndGeneratorUtil.isHoofdFrame(frame)) {
                r.incr();
                r.regel("<div id=\"rechts\" class=\"grid_6\">");
                r.incr();
                r.regel("<div class=\"formSectie\">");
                r.incr();
                r.regel("<spring:message code=\"entity.", frameBron, "\" var=\"argument\"/>");
                r.regel("<p><spring:message code=\"veld.kop\" arguments=\"${argument}\"/></p>");
                r.regel("<hr />");

                genereerTabInhoudVeldenKolomRechts(frame);

                r.decr();
                r.regel("</div>");
                r.decr();
                r.regel("</div>");
                r.regel();
            }

            r.regel();
            r.regel("<script type=\"text/javascript\">");
            r.incr();
            r.regel("$(document).ready(function() {");
            r.incr();
            r.regel("initInvoerveldHighlight();");
            r.regel("initSelecteerbaarTabel();");
            r.decr();
            r.regel("});");
            r.decr();
            r.regel("</script>");
            r.decr();
            r.regel("</formUtil:bind>");

            file.generateFile(tabInhoudFileNaam, r);
        }
    }

    /**
     * Genereer de invoer velden.
     *
     * @param frame de Frame waarvoor de velden gegenereerd moet worden
     */
    private void genereerTabInhoudVeldenKolomLinks(final Frame frame) {
        if (FrontEndGeneratorUtil.isHoofdFrame(frame)) {
            genereerTabInhoudVeldenKolomLinksHoofdFrame(frame);
        } else {
            genereerTabInhoudVeldenKolomLinksOverigeFrame(frame);
        }
    }

    /**
     * Genereert de inhoud van linkerkant van de tab inhoud voor de eerste tabblad.
     *
     * @param frame Frame
     */
    private void genereerTabInhoudVeldenKolomLinksHoofdFrame(final Frame frame) {
        for (FrameVeld veld : frame.getVelden()) {
            String veldPath = MvcFormBeanGenerator.getAttribuutPad(veld);

            if (!veldPath.endsWith("ID")) {
                r.regel("<div class=\"rij\">");
                r.incr();
                r.regel("<span class=\"subgrid_2 alpha\">");
                r.incr();
                r.regel("<form:label cssClass=\"\" for=\"", veldPath, "\" path=\"", veldPath,
                        "\" cssErrorClass=\"error\">");
                r.incr();
                r.regel("<spring:message code=\"", MessagePropertiesGenerator.getMessagePropertieVeld(veld), "\"/>");
                r.decr();
                r.regel("</form:label>");
                r.regel("<form:errors path=\"", veldPath, "\" />");
                r.decr();
                r.regel("</span>");
                r.regel("<span class=\"subgrid_4 omega\">");

                if (FrontEndGeneratorUtil.isEnumVeld(veld)) {
                    r.incr();
                    r.regel("<form:select cssClass=\"input\" path=\"", veldPath,
                            "\" disabled=\"${command.leesModus}\">");
                    r.incr();
                    r.regel("<form:option value=\"\"><spring:message code=\"veld.dropdown.geen\"/></form:option>");
                    r.regel("<form:options items=\"${", DetailControllerGenerator.getStamGegevensVariabelNaam(veld),
                            "}\" />");
                    r.decr();
                    r.regel("</form:select>");
                } else if (FrontEndGeneratorUtil.isDynamischVeld(veld)) {
                    r.incr();
                    r.regel("<form:select cssClass=\"input\" path=\"", veldPath,
                            "\" disabled=\"${command.leesModus}\">");
                    r.incr();
                    r.regel("<form:option value=\"\"><spring:message code=\"veld.dropdown.geen\"/></form:option>");
                    r.regel("<form:options items=\"${", DetailControllerGenerator.getStamGegevensVariabelNaam(veld),
                            "}\" itemValue=\"", FrontEndGeneratorUtil.getAttribuutNaamVanObjectVeld(veld, 0),
                            "\" itemLabel=\"", FrontEndGeneratorUtil.getAttribuutNaamVanObjectVeld(veld, 1), "\"/>");
                    r.decr();
                    r.regel("</form:select>");
                } else {
                    r.incr();
                    r.regel("<form:input path=\"", veldPath,
                            "\" type=\"text\" class=\"input\" disabled=\"${command.leesModus}\"/>");
                }

                r.decr();
                r.regel("</span>");
                r.decr();
                r.regel("</div>");
            }
        }
    }

    /**
     * Genereert de inhoud van de tab inhoud van de overige tab bladen.
     *
     * @param frame Frame
     */
    private void genereerTabInhoudVeldenKolomLinksOverigeFrame(final Frame frame) {
        String formulierBron = FrontEndGeneratorUtil.getFormulierBron(frame.getFormulier());
        String frameBron = FrontEndGeneratorUtil.getFrameBron(frame);

        r.regel("<table class=\"selecteerbaar\">");
        r.incr();
        r.regel("<c:forEach items=\"${command.", decapitalize(formulierBron), ".", decapitalize(frameBron),
                "en}\" var=\"item\">");
        r.incr();
        r.regel("<tr>");

        r.incr();
        for (FrameVeld veld : frame.getVelden()) {
            r.regel("<td class=\"\">");
            r.incr();

            // Alleen een check in de <td>
            if (frame.getVelden().indexOf(veld) == 0) {
                r.regel("<form:checkbox path=\"", MvcFormBeanGenerator.getPadNaarTeVerwijderenObjectAttribuut(frame),
                        "\" value=\"${ofn:hashCode(item)}\" disabled=\"${command.leesModus}\"/>");
            }

            if (FrontEndGeneratorUtil.isEnumVeld(veld)) {
                r.regel("<spring:message code=\"", MessagePropertiesGenerator.getEnumPrefix(veld), "${item.",
                        FrontEndGeneratorUtil.getAttribuutNaam(veld), "}\" text=\"\"/>");
            } else if (FrontEndGeneratorUtil.isDynamischVeld(veld)) {
                r.regel("${item.", JspGenerator.getAttribuutPadVanObjectVeld(veld, null), "}");
            } else {
                r.regel("${item.", FrontEndGeneratorUtil.getAttribuutNaam(veld), "}");
            }
            r.decr();
            r.regel("</td>");
        }
        r.decr();
        r.regel("</tr>");
        r.decr();
        r.regel("</c:forEach>");
        r.decr();
        r.regel("</table>");
        r.regel("<c:if test=\"${!command.leesModus}\">");

        r.incr();
        r.regel("<div class=\"subprefix_4 subgrid_1\"><input type=\"submit\" id=\"knop", frameBron,
                "Toevoegen\" value=\"+\" name=\"",
                DetailControllerGenerator.getControllerMethodRequestParamToevoegen(frame),
                "\" class=\"button-01 klein\"/></div>");
        r.regel("<div class=\"subgrid_1 omega\"><input type=\"submit\" id=\"knop", frameBron,
                "Verwijderen\" value=\"-\" name=\"",
                DetailControllerGenerator.getControllerMethodRequestParamVerwijderen(frame),
                "\" class=\"button-01 klein\"/></div>");
        r.regel("<script type=\"text/javascript\">");
        r.incr();
        r.regel("Spring.addDecoration(new Spring.AjaxEventDecoration(");
        r.incr();
        r.regel("{");
        r.incr();
        r.regel("elementId : \"knop", frameBron, "Toevoegen\",");
        r.regel("formId : \"formbody\",");
        r.regel("event : \"onclick\",");
        r.regel("params : {");
        r.incr();
        r.regel("fragments : \"tabInhoud,berichten\"");
        r.decr();
        r.regel("}");
        r.decr();
        r.regel("}));");
        r.regel();
        r.decr();
        r.regel("Spring.addDecoration(new Spring.AjaxEventDecoration(");
        r.incr();
        r.regel("{");
        r.incr();
        r.regel("elementId : \"knop", frameBron, "Verwijderen\",");
        r.regel("formId : \"formbody\",");
        r.regel("event : \"onclick\",");
        r.regel("params : {");
        r.incr();
        r.regel("fragments : \"tabInhoud,berichten\"");
        r.decr();
        r.regel("}");
        r.decr();
        r.regel("}));");
        r.decr().decr();
        r.regel("</script>");
        r.decr();
        r.regel("</c:if>");
    }

    /**
     * Genereer de inhoud van rechter kolom.
     *
     * @param frame Frame
     */
    private void genereerTabInhoudVeldenKolomRechts(final Frame frame) {
        for (FrameVeld veld : frame.getVelden()) {
            r.regel("<div class=\"rij\">");
            r.regel("   <span class=\"subgrid_2 alpha\">");
            r.regel("       <form:label for=\"", MvcFormBeanGenerator.getPadNaarToeTeVoegenObjectAttribuut(veld),
                    "\" path=\"", MvcFormBeanGenerator.getPadNaarToeTeVoegenObjectAttribuut(veld),
                    "\" cssErrorClass=\"error\"><spring:message code=\"",
                    MessagePropertiesGenerator.getMessagePropertieVeld(veld), "\"/></form:label>");
            r.regel("       <form:errors path=\"", MvcFormBeanGenerator.getPadNaarToeTeVoegenObjectAttribuut(veld),
                    "\" />");
            r.regel("   </span>");
            r.regel("   <span class=\"subgrid_4 omega\">");
            if (FrontEndGeneratorUtil.isEnumVeld(veld)) {
                r.regel("       <form:select cssClass=\"input\" path=\"",
                        MvcFormBeanGenerator.getPadNaarToeTeVoegenObjectAttribuut(veld),
                        "\" disabled=\"${command.leesModus}\">");
                r.regel("           <form:options items=\"${",
                        DetailControllerGenerator.getStamGegevensVariabelNaam(veld), "}\" />");
                r.regel("       </form:select>");
            } else {
                r.regel("<form:input path=\"", MvcFormBeanGenerator.getPadNaarToeTeVoegenObjectAttribuut(veld),
                        "\" type=\"text\" class=\"input\" disabled=\"${command.leesModus}\"/>");
            }

            r.regel("   </span>");
            r.regel("</div>");
        }
    }

    /**
     * Bouwt de jsp bestands naam van een JSP tab inhoud.
     *
     * @param frame Frame
     * @return bestands naam van de tab inhoud jsp
     */
    public static String getJspTabInhoudFileNaam(final Frame frame) {
        return JspGenerator.JSP_LOCATION + FrontEndGeneratorUtil.getFormulierNaam(frame.getFormulier()).toLowerCase()
            + "/" + FrontEndGeneratorUtil.getFrameNaam(frame).toLowerCase() + JspGenerator.JSP_EXTENSIE;
    }
}
