/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators.frontend;

import javax.inject.Inject;

import nl.bzk.brp.bmr.generator.Generator;
import nl.bzk.brp.bmr.generator.generators.frontend.jsp.JspHoofdMenuGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.jsp.JspTabInhoudGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.jsp.JspTabMenuGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.jsp.JspTabelGenerator;
import nl.bzk.brp.bmr.generator.utils.ArtifactBuilder;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.Applicatie;
import nl.bzk.brp.ecore.bmr.Formulier;
import nl.bzk.brp.ecore.bmr.FrameVeld;
import nl.bzk.brp.ecore.bmr.MetaRegister;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * {@link Generator} implementatie voor jsp.
 */
@Component
public class JspGenerator implements Generator {

    private static final Logger   LOGGER       = LoggerFactory.getLogger(JspGenerator.class);

    /** lokatie van beheer jsp's. **/
    public static final String    JSP_LOCATION = "WEB-INF/jsp/beheren/";

    /** de extensie van de jsp file. **/
    public static final String    JSP_EXTENSIE = ".jsp";

    private ArtifactBuilder       r;

    @Inject
    private JspTabMenuGenerator   jspTabMenuGenerator;

    @Inject
    private JspTabInhoudGenerator jspTabInhoudGenerator;

    @Inject
    private JspTabelGenerator     jspTabelGenerator;

    @Inject
    private JspHoofdMenuGenerator jspHoofdMenuGenerator;

    @Override
    public void generate(final MetaRegister register, final String naam, final FileSystemAccess file) {
        Applicatie applicatie = register.getApplicatie(naam);
        LOGGER.info("JSP genereren voor de '{}' applicatie", applicatie.getNaam());

        for (Formulier formulier : applicatie.getFormulieren()) {
            jspTabMenuGenerator.genereerTabMenu(file, formulier);
            jspTabInhoudGenerator.genereerTabInhoud(file, formulier);
            jspTabelGenerator.genereerTabel(file, formulier);
        }

        jspHoofdMenuGenerator.genereerHoofdMenu(file, applicatie);
    }

    /**
     * Geeft de pad van een object naar een attribuut, bijvoorbeeld sector.naam.
     *
     * @param veld FrameVeld
     * @param veldNummer het attribuut binnen de veld
     * @return string met de pad naar de attribuut
     */
    public static String getAttribuutPadVanObjectVeld(final FrameVeld veld, final Integer veldNummer) {
        return FrontEndGeneratorUtil.getAttribuutNaam(veld) + "."
            + FrontEndGeneratorUtil.getAttribuutNaamVanObjectVeld(veld, veldNummer);
    }
}
