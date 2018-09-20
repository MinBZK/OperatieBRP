/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators.frontend;

import nl.bzk.brp.bmr.generator.AbstractGenerator;
import nl.bzk.brp.bmr.generator.Generator;
import nl.bzk.brp.bmr.generator.generators.frontend.controller.DetailControllerGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.jsp.JspTabInhoudGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.jsp.JspTabMenuGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.jsp.JspTabelGenerator;
import nl.bzk.brp.bmr.generator.utils.ArtifactBuilder;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.Applicatie;
import nl.bzk.brp.ecore.bmr.Formulier;
import nl.bzk.brp.ecore.bmr.Frame;
import nl.bzk.brp.ecore.bmr.ModelElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * {@link Generator} implementatie voor de tiles-definitie generator.
 */
@Component
public class TilesDefinitieGenerator extends AbstractGenerator implements Generator {

    private static final Logger LOGGER         = LoggerFactory.getLogger(TilesDefinitieGenerator.class);

    private static final String TILES_DEF_FILE = "WEB-INF/tiles-defs/generated-def.xml";

    private static final String BASIS_PAD      = "beheren/";

    private ArtifactBuilder     r;

    @Override
    public void generate(final ModelElement element, final FileSystemAccess file) {
        if (!(element instanceof Applicatie)) {
            throw new IllegalArgumentException("Kan alleen code genereren uit een Domein Applicatie.");
        }
        Applicatie applicatie = (Applicatie) element;
        LOGGER.info("MVC Tiles definitie genereren voor de '{}' applicatie", applicatie.getNaam());

        genereerTilesDefinition(file, applicatie);
    }

    /**
     * Genereer mvc tiles definities.
     *
     * @param file file FileSystemAccess
     * @param applicatie de Applicatie waarvoor de definitie gegenereerd moet worden
     */
    private void genereerTilesDefinition(final FileSystemAccess file, final Applicatie applicatie) {
        LOGGER.debug("Genereren tiles-definition '{}'.", TILES_DEF_FILE);

        r = new ArtifactBuilder();
        r.regel("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");
        r.regel("<!DOCTYPE tiles-definitions PUBLIC \"-//Apache Software Foundation//DTD Tiles Configuration",
                " 2.0//EN\" \"http://tiles.apache.org/dtds/tiles-config_2_0.dtd\">");
        r.regel("<tiles-definitions>");
        r.incr();
        for (Formulier formulier : applicatie.getFormulieren()) {
            String formulierNaam = FrontEndGeneratorUtil.getFormulierNaam(formulier);

            r.regel("<!-- Definities voor ", formulierNaam, " -->");
            r.regel("<definition name=\"", getViewNaam(formulier), "\" extends=\".mainTemplate\">");
            r.incr().regel("<put-attribute name=\"body\" value=\"body", formulierNaam, "Overzicht\" />").decr();
            r.regel("</definition>");
            r.regel();
            r.regel("<definition name=\"body", formulierNaam, "Overzicht\" extends=\".bodyTemplate\">");
            r.incr();
            r.regel("<put-attribute name=\"controllePaneel\" "
                + "value=\"/WEB-INF/jsp/common/controllePaneelOverzicht.jsp?entity=",
                    FrontEndGeneratorUtil.getFormulierBron(formulier), "&amp;detailUrl=",
                    DetailControllerGenerator.getRequestUrlVoorController(formulier.getFrames().get(0)), "\"/>");
            r.regel("<put-attribute name=\"zijPaneel\" value=\"blank\"/>");
            r.regel("<put-attribute name=\"tabMenu\" value=\"blank\"/>");
            r.regel("<put-attribute name=\"tabInhoud\" value=\"/", JspTabelGenerator.getJspTabelFileNaam(formulier),
                    "\"/>");
            r.decr();
            r.regel("</definition>");
            r.regel();

            for (Frame frame : formulier.getFrames()) {
                LOGGER.info("Tiles genereren voor frame '{}'.", frame.getNaam());

                String paginaNaam = FrontEndGeneratorUtil.getFrameNaam(frame);

                r.regel("<definition name=\"", getViewNaam(frame), "\" extends=\".mainTemplate\">");
                r.incr().regel("<put-attribute name=\"body\" value=\"body", paginaNaam, "\" />").decr();
                r.regel("</definition>");
                r.regel();
                r.regel("<definition name=\"body", paginaNaam, "\" extends=\".bodyTemplate\">");
                r.incr();
                r.regel("<put-attribute name=\"controllePaneel\" ",
                        "value=\"/WEB-INF/jsp/common/controllePaneelBewerk.jsp?entity=",
                        FrontEndGeneratorUtil.getFormulierBron(formulier), "\"/>");
                r.regel("<put-attribute name=\"zijPaneel\" value=\"blank\"/>");
                r.regel("<put-attribute name=\"tabMenu\" value=\"block", paginaNaam, "Tab\"/>");
                r.regel("<put-attribute name=\"tabInhoud\" value=\"block", paginaNaam, "\"/>");
                r.decr();
                r.regel("</definition>");
                r.regel();
                r.regel("<definition name=\"block", paginaNaam, "Tab\" template=\"/",
                        JspTabMenuGenerator.getJspTabFileNaam(formulier), "\"/>");
                r.regel("<definition name=\"block", paginaNaam, "\" template=\"/",
                        JspTabInhoudGenerator.getJspTabInhoudFileNaam(frame), "\"/>");
                r.regel();
            }

        }
        r.decr();
        r.regel("</tiles-definitions>");

        file.generateFile(TILES_DEF_FILE, r);
    }

    /**
     * Bouwt de MVC view naam op voor frame.
     *
     * @param frame Frame
     * @return MVC view naam
     */
    public static String getViewNaam(final Frame frame) {
        return BASIS_PAD + FrontEndGeneratorUtil.getFormulierNaam(frame.getFormulier()).toLowerCase() + "/"
            + FrontEndGeneratorUtil.getFrameNaam(frame).toLowerCase();
    }

    /**
     * Bouwt de MVC view naam op voor formulier.
     *
     * @param formulier Formulier
     * @return MVC view naam
     */
    public static String getViewNaam(final Formulier formulier) {
        return BASIS_PAD + FrontEndGeneratorUtil.getFormulierNaam(formulier).toLowerCase() + "/overzicht";
    }

}
