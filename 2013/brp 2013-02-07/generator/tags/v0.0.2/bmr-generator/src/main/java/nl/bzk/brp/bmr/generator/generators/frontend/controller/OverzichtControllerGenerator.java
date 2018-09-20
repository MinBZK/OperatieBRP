/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.bmr.generator.generators.frontend.FrontEndGeneratorUtil;
import nl.bzk.brp.bmr.generator.generators.frontend.MvcControllerGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.MvcServiceGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.TilesDefinitieGenerator;
import nl.bzk.brp.bmr.generator.utils.ArtifactBuilder;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.Formulier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Generator die een mvc controller genereert voor de overzicht scherm.
 *
 */
@Component
public class OverzichtControllerGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(OverzichtControllerGenerator.class);

    private ArtifactBuilder     r;

    /**
     * Genereer de Controller class.
     *
     * @param file file FileSystemAccess
     * @param formulier formulier waarvoor de formBean gegenereerd moet worden
     */
    public void genereerClassOverzichtController(final FileSystemAccess file, final Formulier formulier) {
        r = new ArtifactBuilder();
        String formulierNaam = FrontEndGeneratorUtil.getFormulierNaam(formulier);

        String javaClass = formulierNaam + "OverzichtController";
        String javaClassLokatie = MvcControllerGenerator.getPackageLokatie(formulier) + "/" + javaClass + ".java";

        String formulierBron = FrontEndGeneratorUtil.getFormulierBron(formulier);

        LOGGER.debug("Genereren Controller '{}'.", javaClassLokatie);

        r.regel("package ", MvcControllerGenerator.getPackage(formulier), ";");
        r.regel("");
        r.regel("import javax.inject.Inject;");
        r.regel("import javax.inject.Named;");
        r.regel();
        r.regel("import nl.bzk.brp.beheer.web.controller.AbstractOverzichtController;");
        r.regel("import nl.bzk.brp.beheer.web.service.GenericDomeinService;");
        // Dit is import naar de parent object
        r.regel(getModelImports(formulier));
        r.regel("import org.springframework.stereotype.Controller;");
        r.regel("import org.springframework.web.bind.annotation.RequestMapping;");
        r.regel("");
        r.regel("");
        r.regel("/**");
        r.regel(" * Deze class verzorgt het weergeven van de overzicht scherm.");
        r.regel(" *");
        r.regel(" */");
        r.regel("@Controller");
        r.regel("@RequestMapping(\"", MvcControllerGenerator.getControllerRequestMappingUrl(formulier), "\")");
        r.regel("public class ", formulierNaam, "OverzichtController extends AbstractOverzichtController<",
                formulierBron, "> {");
        r.regel("");
        r.incr();
        r.regel("private static final String OVERZICHT_PAGINA = \"", TilesDefinitieGenerator.getViewNaam(formulier),
                "\";");
        r.regel();
        r.regel("/**");
        r.regel(" * Constructor om de abstract class correct the instantieeren.");
        r.regel(" */");
        r.regel(formulierNaam, "OverzichtController() {");
        r.incr().regel("super(OVERZICHT_PAGINA);").decr();
        r.regel("}");
        r.regel();
        r.regel("@Override");
        r.regel("@Inject");
        r.regel("@Named(\"", MvcServiceGenerator.getDomeinServiceNaam(formulier), "\")");
        r.regel("public void setDomeinService(final GenericDomeinService<", formulierBron, "> service) {");
        r.incr().regel("super.setDomeinService(service);").decr();
        r.regel("}");
        r.regel();
        r.decr().regel("}");
        r.regel();

        file.generateFile(javaClassLokatie, r);
    }

    /**
     * Maak en sorteer imports.
     *
     * @param formulier Formulier
     * @return string met de imports op de juiste volgorde
     */
    private String getModelImports(final Formulier formulier) {
        List<String> imports = new ArrayList<String>();

        // Formulier bron import
        imports.add(FrontEndGeneratorUtil.getModelPadVoorFormulierBron(formulier));

        return FrontEndGeneratorUtil.sorteerEnMaakImportStrings(imports);
    }
}
