/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators.frontend;

import static java.beans.Introspector.decapitalize;
import nl.bzk.brp.bmr.generator.Generator;
import nl.bzk.brp.bmr.generator.utils.ArtifactBuilder;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.Applicatie;
import nl.bzk.brp.ecore.bmr.Formulier;
import nl.bzk.brp.ecore.bmr.Frame;
import nl.bzk.brp.ecore.bmr.FrameVeld;
import nl.bzk.brp.ecore.bmr.MetaRegister;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * {@link Generator} implementatie voor een Controllers en FormBean.
 */
@Component
public class MvcFormBeanGenerator implements Generator {

    private static final Logger LOGGER              = LoggerFactory.getLogger(MvcFormBeanGenerator.class);

    private static final String PACKAGE             = "nl.bzk.brp.beheer.web.controller";

    private static final String TE_VERWIJDEREN_ATTR = "TeVerwijderen";

    private static final String TOE_TE_VOEGEN_ATTR  = "ToeTeVoegen";

    private ArtifactBuilder     r;

    @Override
    public void generate(final MetaRegister register, final String naam, final FileSystemAccess file) {
        Applicatie applicatie = register.getApplicatie(naam);
        LOGGER.info("Webinterface genereren voor de '{}' applicatie", applicatie.getNaam());

        for (Formulier formulier : applicatie.getFormulieren()) {
            genereerClassFormBean(file, formulier);
        }
    }

    /**
     * Genereer de FormBean voor de controller.
     *
     * @param file file FileSystemAccess
     * @param formulier formulier waarvoor de formBean gegenereerd moet worden
     */
    private void genereerClassFormBean(final FileSystemAccess file, final Formulier formulier) {
        String formulierNaam = FrontEndGeneratorUtil.getFormulierNaam(formulier);
        String formulierBron = FrontEndGeneratorUtil.getFormulierBron(formulier);
        String packageLokatie = PACKAGE.replace(".", "/") + "/" + formulierNaam.toLowerCase();

        // Maak form bean voor elke frame
        for (Frame frame : formulier.getFrames()) {
            String frameBron = FrontEndGeneratorUtil.getFrameBron(frame);

            String javaClass = getFormBeanNaam(frame);
            String javaClassLokatie = packageLokatie + "/" + javaClass + ".java";

            // Attributen van de formBean
            String attribuutToeTeVoegen = getAttribuutToeTeVoegen(frame, true);
            String attribuutTeVerwijderen = getAttribuutTeVerwijderen(frame, true);

            LOGGER.debug("Genereren formBean '{}'.", javaClassLokatie);

            r = new ArtifactBuilder();

            r.regel("package ", PACKAGE, ".", formulierNaam.toLowerCase(), ";");

            if (!FrontEndGeneratorUtil.isHoofdFrame(frame)) {
                r.regel();
                r.regel("import java.util.List;");
            }
            r.regel();
            r.regel("import javax.validation.Valid;");
            r.regel();
            r.regel("import nl.bzk.brp.beheer.web.controller.AbstractFormBean;");
            r.regel(MvcControllerGenerator.getModelImports(frame));
            r.regel();
            r.regel("/**");
            r.regel(" * Form bean voor ", formulierBron, ".");
            r.regel(" *");
            r.regel(" */");
            r.regel("public class ", javaClass, " extends AbstractFormBean {");
            r.regel();
            r.incr();
            r.regel("@Valid");
            r.regel("private ", formulierBron, " ", decapitalize(formulierBron), ";");
            r.regel();

            if (!FrontEndGeneratorUtil.isHoofdFrame(frame)) {
                r.regel("@Valid");
                r.regel("private ", frameBron, " ", attribuutToeTeVoegen, ";");
                r.regel();
                r.regel("private List<String>", " ", attribuutTeVerwijderen, ";");
            }

            r.regel();
            r.regel("/**");
            r.regel(" * Constructor.");
            r.regel(" * @param ", decapitalize(formulierBron), " ", formulierBron);
            if (!FrontEndGeneratorUtil.isHoofdFrame(frame)) {
                r.regel(" * @param ", decapitalize(frameBron), " ", frameBron);
            }
            r.regel(" */");
            String parameters = "final " + formulierBron + " " + decapitalize(formulierBron);
            if (!FrontEndGeneratorUtil.isHoofdFrame(frame)) {
                parameters += ", final " + frameBron + " " + decapitalize(frameBron);
            }
            r.regel("public ", javaClass, "(", parameters, ") {");
            r.incr();
            r.regel("this.", decapitalize(formulierBron), " = ", decapitalize(formulierBron), ";");
            if (!FrontEndGeneratorUtil.isHoofdFrame(frame)) {
                r.regel("this.", attribuutToeTeVoegen, " = ", decapitalize(frameBron), ";");
            }
            r.regel("setLeesModus(false);");
            r.decr();
            r.regel("}");
            r.regel();

            // genereer methoden
            genereerMethoden(frame, formulierBron, frameBron, attribuutToeTeVoegen, attribuutTeVerwijderen);

            r.decr();
            r.regel("}");

            file.generateFile(javaClassLokatie, r);
        }
    }

    /**
     * Deze methode genereerd methodes van de formBean.
     *
     * @param frame Frame
     * @param formulierBron bron van de formulier
     * @param frameBron bron van de frame
     * @param attribuutToeTeVoegen de attribuut naam van de toe te voegen object
     * @param attribuutTeVerwijderen de attribuut naam van de te verwijderen object
     */
    private void genereerMethoden(final Frame frame, final String formulierBron, final String frameBron,
        final String attribuutToeTeVoegen, final String attribuutTeVerwijderen)
    {
        r.regel("public ", formulierBron, " get", formulierBron, "() {");
        r.incr().regel("return ", decapitalize(formulierBron), ";").decr();
        r.regel("}");
        r.regel();
        r.regel("/**");
        r.regel(" * Zet ", formulierBron, " in de formBean.");
        r.regel(" *");
        r.regel(" * @param ", decapitalize(formulierBron), " ", formulierBron);
        r.regel(" */");
        r.regel("public void set", formulierBron, "(final ", formulierBron, " ", decapitalize(formulierBron), ") {");
        r.incr();
        r.regel("this.", decapitalize(formulierBron), " = ", decapitalize(formulierBron), ";");
        if (!FrontEndGeneratorUtil.isHoofdFrame(frame)) {
            r.regel(attribuutToeTeVoegen, ".set", formulierBron, "(", decapitalize(formulierBron), ");");
        }
        r.decr();
        r.regel("}");

        if (!FrontEndGeneratorUtil.isHoofdFrame(frame)) {
            // getter
            r.regel("public ", frameBron, " ", getGetterToeTeVoegenAttribuut(frame), " {");
            r.incr().regel("return ", attribuutToeTeVoegen, ";").decr();
            r.regel("}");
            r.regel();
            // setter
            r.regel("public void ", getSetterToeTeVoegenAttribuut(frame), "(final ", frameBron, " ",
                    attribuutToeTeVoegen, ") {");
            r.incr().regel("this.", attribuutToeTeVoegen, " = ", attribuutToeTeVoegen, ";").decr();
            r.regel("}");

            // getter
            r.regel("public List<String> ", getGetterTeVerwijderenAttribuut(frame), " {");
            r.incr().regel("return ", attribuutTeVerwijderen, ";").decr();
            r.regel("}");
            r.regel();
            // setter
            r.regel("public void ", getSetterTeVerwijderenAttribuut(frame), "(final List<String> ",
                    attribuutTeVerwijderen, ") {");
            r.incr().regel("this.", attribuutTeVerwijderen, " = ", attribuutTeVerwijderen, ";").decr();
            r.regel("}");
        }
    }

    /**
     * Bouwt formBean pad naar de veld.
     *
     * @param veld FrameVeld
     * @return formBean pad naar de veld
     */
    public static String getAttribuutPad(final FrameVeld veld) {
        String veldResultaat = "";

        if (!FrontEndGeneratorUtil.isHoofdFrame(veld.getFrame())) {
            veldResultaat = decapitalize(FrontEndGeneratorUtil.getFormulierBron(veld.getFrame().getFormulier())) + ".";
        }

        veldResultaat = veldResultaat + decapitalize(veld.getAttribuut().getObjectType().getIdentifierCode());

        if (!FrontEndGeneratorUtil.isHoofdFrame(veld.getFrame())) {
            veldResultaat = veldResultaat + "en";
        }

        if (FrontEndGeneratorUtil.isHoofdFrame(veld.getFrame())) {
            veldResultaat = veldResultaat + "." + decapitalize(veld.getAttribuut().getIdentifierCode());
        }

        return veldResultaat;
    }

    /**
     * Haalt de pad op van de attribuut van het toe te voegen object.
     *
     * @param veld FrameVeld
     * @return een string met de pad naar het toe te voegen object
     */
    public static String getPadNaarToeTeVoegenObjectAttribuut(final FrameVeld veld) {
        return getAttribuutToeTeVoegen(veld.getFrame(), true) + "." + FrontEndGeneratorUtil.getAttribuutNaam(veld);
    }

    /**
     * Haalt de pad op van de attribuut van het te verwijderen object.
     *
     * @param frame Frame
     * @return een string met de pad naar het te verwijderen object
     */
    public static String getPadNaarTeVerwijderenObjectAttribuut(final Frame frame) {
        return getAttribuutTeVerwijderen(frame, true);
    }

    /**
     * Haalt de getter op voor de attribuut te verwijderen object.
     *
     * @param frame Frame
     * @return de getter van het te verwijderen object.
     */
    public static String getGetterTeVerwijderenAttribuut(final Frame frame) {
        return "get" + getAttribuutTeVerwijderen(frame, false) + "()";
    }

    /**
     * Haalt de naam van de Form Bean op voor de frame.
     *
     * @param frame Frame
     * @return de formBean
     */
    public static String getFormBeanNaam(final Frame frame) {
        return FrontEndGeneratorUtil.getFrameNaam(frame) + "FormBean";
    }

    /**
     * Haalt de getter op voor de attribuut toe te voegen object.
     *
     * @param frame Frame
     * @return de getter van het toe te voegen object
     */
    private static String getGetterToeTeVoegenAttribuut(final Frame frame) {
        return "get" + getAttribuutToeTeVoegen(frame, false) + "()";
    }

    /**
     * Haalt de setter op voor de attribuut te verwijderen object.
     *
     * @param frame Frame
     * @return de setter van het te verwijderen object
     */
    private static String getSetterTeVerwijderenAttribuut(final Frame frame) {
        return "set" + getAttribuutTeVerwijderen(frame, false);
    }

    /**
     * Haalt de setter op voor de attribuut toe te voegen object.
     *
     * @param frame Frame
     * @return de setter van het toe te voegen object
     */
    private static String getSetterToeTeVoegenAttribuut(final Frame frame) {
        return "set" + getAttribuutToeTeVoegen(frame, false);
    }

    /**
     * Haal de naam op voor de attribuut van de toe te voegen object.
     *
     * @param frame Frame
     * @param decapitalize boolean true als het de begin letter in lowercase moet
     * @return naam attribuut toe te voegen object
     */
    public static String getAttribuutToeTeVoegen(final Frame frame, final boolean decapitalize) {
        String veld = TOE_TE_VOEGEN_ATTR;

        if (decapitalize) {
            veld = decapitalize(veld);
        }

        return veld + FrontEndGeneratorUtil.getFrameBron(frame);
    }

    /**
     * Haal de naam op voor de attribuut van de toe te voegen object.
     *
     * @param frame Frame
     * @param decapitalize boolean true als de begin letter in lowercase moet
     * @return naam attribuut te verwijderen object
     */
    private static String getAttribuutTeVerwijderen(final Frame frame, final boolean decapitalize) {
        String veld = TE_VERWIJDEREN_ATTR;

        if (decapitalize) {
            veld = decapitalize(veld);
        }

        return veld + FrontEndGeneratorUtil.getFrameBron(frame);
    }
}
