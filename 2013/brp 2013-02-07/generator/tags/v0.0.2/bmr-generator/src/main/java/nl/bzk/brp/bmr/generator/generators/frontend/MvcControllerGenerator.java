/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators.frontend;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.bmr.generator.AbstractGenerator;
import nl.bzk.brp.bmr.generator.Generator;
import nl.bzk.brp.bmr.generator.generators.frontend.controller.DetailControllerGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.controller.OverzichtControllerGenerator;
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
 * {@link Generator} implementatie voor een Controllers en FormBean.
 */
@Component
public class MvcControllerGenerator extends AbstractGenerator implements Generator {

    private static final Logger          LOGGER     = LoggerFactory.getLogger(MvcControllerGenerator.class);

    private static final String          BASIS_PAD  = "/beheren/";

    /** Basis package voor controllers. */
    public static final String           PACKAGE    = "nl.bzk.brp.beheer.web.controller";

    /** De url suffix. */
    public static final String           URL_SUFFIX = ".html";

    @Inject
    private OverzichtControllerGenerator overzichtControllerGenerator;

    @Inject
    private DetailControllerGenerator    detailControllerGenerator;

    private ArtifactBuilder              r;

    @Override
    public void generate(final ModelElement element, final FileSystemAccess file) {
        if (!(element instanceof Applicatie)) {
            throw new IllegalArgumentException("Kan alleen code genereren uit een Applicatie element.");
        }
        Applicatie applicatie = (Applicatie) element;
        LOGGER.info("MVC Controller genereren voor de '{}' applicatie", applicatie.getNaam());

        for (Formulier formulier : applicatie.getFormulieren()) {
            genereerPackageInfo(file, formulier);
            overzichtControllerGenerator.genereerClassOverzichtController(file, formulier);
            detailControllerGenerator.genereerClassController(file, formulier);
        }
    }

    /**
     * Genereert de package-info.java.
     *
     * @param file FileSystemAccess
     * @param formulier Formulier
     */
    private void genereerPackageInfo(final FileSystemAccess file, final Formulier formulier) {
        r = new ArtifactBuilder();

        r.regel("package ", getPackage(formulier), ";");

        file.generateFile(getPackageLokatie(formulier) + "/Package-info.java", r);
    }

    /**
     * Bouwt de request url van de controller.
     *
     * @param formulier Formulier
     * @return requestMapping url van de controller
     */
    public static String getControllerRequestMappingUrl(final Formulier formulier) {
        return BASIS_PAD + FrontEndGeneratorUtil.getFormulierNaam(formulier).toLowerCase();
    }

    /**
     * Geeft de package terug waarin de controller zich moet bevinden.
     *
     * @param formulier Formulier
     * @return string met de package naam
     */
    public static String getPackage(final Formulier formulier) {
        return PACKAGE + "." + FrontEndGeneratorUtil.getFormulierNaam(formulier).toLowerCase();
    }

    /**
     * Geeft de package directory terug waarin de controller zich moet bevinden.
     *
     * @param formulier Formulier
     * @return string met de package directory
     */
    public static String getPackageLokatie(final Formulier formulier) {
        return getPackage(formulier).replace(".", "/");
    }

    /**
     * Haalt de model imports op voor de frame.
     *
     * @param frame Frame
     * @return string met de alle imports
     */
    public static String getModelImports(final Frame frame) {
        List<String> imports = new ArrayList<String>();

        // Formulier bron import
        imports.add(FrontEndGeneratorUtil.getModelPadVoorFormulierBron(frame.getFormulier()));

        // Frame bron import
        if (!FrontEndGeneratorUtil.isHoofdFrame(frame)) {
            imports.add(FrontEndGeneratorUtil.getModelPadVoorFrameBron(frame));
        }

        return FrontEndGeneratorUtil.sorteerEnMaakImportStrings(imports);
    }
}
