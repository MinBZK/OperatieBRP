/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators.frontend.controller;

import java.util.List;

import nl.bzk.brp.bmr.generator.generators.frontend.FrontEndGeneratorUtil;
import nl.bzk.brp.bmr.generator.generators.frontend.MvcControllerGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.MvcFormBeanGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.MvcServiceGenerator;
import nl.bzk.brp.bmr.generator.generators.frontend.TilesDefinitieGenerator;
import nl.bzk.brp.bmr.generator.utils.ArtifactBuilder;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.Formulier;
import nl.bzk.brp.ecore.bmr.Frame;
import nl.bzk.brp.ecore.bmr.FrameVeld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Generator die een mvc controlleer genereert voor de detail scherm.
 * Deze controller beheert de functionaliteit van de detail pagina zoals het inzien
 * en bewerken van gegevens.
 *
 */
@Component
public class DetailControllerGenerator {

    private static final Logger LOGGER           = LoggerFactory.getLogger(DetailControllerGenerator.class);

    private static final String KNOP_TOEVOEGEN   = "_toevoegen";
    private static final String KNOP_VERWIJDEREN = "_verwijderen";

    private ArtifactBuilder     r;

    private List<FrameVeld>     stamgegevensVelden;

    /**
     * Genereer de Controller class.
     *
     * @param file file FileSystemAccess
     * @param formulier formulier waarvoor de formBean gegenereerd moet worden
     */
    public void genereerClassController(final FileSystemAccess file, final Formulier formulier) {
        String formulierBron = FrontEndGeneratorUtil.getFormulierBron(formulier);

        for (Frame frame : formulier.getFrames()) {
            stamgegevensVelden = FrontEndGeneratorUtil.getStamgegevensVeldenVoorFrame(frame);

            String frameNaam = FrontEndGeneratorUtil.getFrameNaam(frame);
            String javaClass = frameNaam + "Controller";
            String javaClassLokatie = MvcControllerGenerator.getPackageLokatie(formulier) + "/" + javaClass + ".java";

            LOGGER.debug("Genereren Controller '{}'.", javaClassLokatie);

            r = new ArtifactBuilder();

            r.regel("package ", MvcControllerGenerator.getPackage(formulier), ";");
            r.regel();
            genereerControllerImports(formulier, frame);
            r.regel();
            r.regel();
            r.regel("/**");
            r.regel(" * Controller voor de ", frameNaam, " scherm.");
            r.regel(" *");
            r.regel(" */");
            r.regel("@Controller");
            r.regel("@RequestMapping(\"", MvcControllerGenerator.getControllerRequestMappingUrl(formulier), "/\")");
            r.regel("@SessionAttributes({ AbstractController.SESSION_ATTRIBUTE_ID, ",
                    "AbstractDetailController.SESSION_ATTRIBUTE_LEESMODUS,");
            r.incr().append("    AbstractController.SESSION_ATTRIBUTE_COMMAND ");
            // Stamgegevens
            for (FrameVeld veld : stamgegevensVelden) {
                r.append(", \"");
                r.append(getStamGegevensVariabelNaam(veld));
                r.append("\"");
            }
            r.decr();
            r.regel(" })");
            r.regel("public class ", javaClass, " extends AbstractDetailController {");
            r.regel();
            r.incr();
            r.regel("private static final String BEWERK_PAGINA = \"",
                    TilesDefinitieGenerator.getViewNaam(frame), "\";");
            r.regel();
            r.regel("@Inject");
            r.regel("@Named(\"", MvcServiceGenerator.getDomeinServiceNaam(formulier), "\")");
            r.regel("private GenericDomeinService<", formulierBron, ">       domeinService;");
            r.regel();
            r.regel("@Inject");
            r.regel("private DomeinObjectFactory domeinObjectFactory;");
            r.regel();

            if (stamgegevensVelden.size() > 0) {
                r.regel("@Inject");
                r.regel("private ", MvcServiceGenerator.STAMGEGEVENS_SERVICE_CLASS, " stamgegevensService;");
            }
            r.regel();
            if (FrontEndGeneratorUtil.isHoofdFrame(frame)) {
                genereerControllerMethodeOpenNieuwPagina(frame);
                r.regel();
                genereerdControllerMethodeOpenBewerkPagina(frame);
                r.regel();
                genereerControllerMethodeOpenPagina(frame);
            } else {
                genereerSecondairTabControllerMethodeOpenPagina(frame);
            }
            r.regel();
            genereerControllerMethodeWijzigen(frame);
            r.regel();
            genereerControllerMethodeOpslaan(frame);
            r.regel();
            if (FrontEndGeneratorUtil.isHoofdFrame(frame)) {
                genereerControllerMethodeInitialiseer();
            }
            if (!FrontEndGeneratorUtil.isHoofdFrame(frame)) {
                genereerControllerMethodeToevoegen(frame);
                genereerControllerMethodeVerwijderen(frame);
            }

            r.regel();
            r.regel("public void setDomeinService(final GenericDomeinService<", formulierBron, "> domeinService) {");
            r.incr().regel("this.domeinService = domeinService;").decr();
            r.regel("}");
            r.regel();
            r.regel("public void setDomeinObjectFactory(final DomeinObjectFactory domeinObjectFactory) {");
            r.incr().regel("this.domeinObjectFactory = domeinObjectFactory;").decr();
            r.regel("}");
            r.regel();
            if (stamgegevensVelden.size() > 0) {
                r.regel("public void setStamgegevensService(final ", MvcServiceGenerator.STAMGEGEVENS_SERVICE_CLASS,
                        " stamgegevensService) {");
                r.incr().regel("this.stamgegevensService = stamgegevensService;").decr();
                r.regel("}");
            }
            r.decr();
            r.regel("}");
            r.regel();

            file.generateFile(javaClassLokatie, r);
        }
    }

    /**
     * Genereer benodigde imports voor de controller.
     *
     * @param formulier Formulier
     * @param frame Frame
     */
    private void genereerControllerImports(final Formulier formulier, final Frame frame) {
        r.regel("import javax.inject.Inject;");
        r.regel("import javax.inject.Named;");
        r.regel();
        r.regel("import nl.bzk.brp.beheer.web.controller.AbstractController;");
        r.regel("import nl.bzk.brp.beheer.web.controller.AbstractDetailController;");
        r.regel("import nl.bzk.brp.beheer.web.messages.MessageSeverity;");
        r.regel("import nl.bzk.brp.beheer.web.service.GenericDomeinService;");
        if (stamgegevensVelden.size() > 0) {
            r.regel("import ", MvcServiceGenerator.STAMGEGEVENS_SERVICE_PACKAGE, ".",
                    MvcServiceGenerator.STAMGEGEVENS_SERVICE_CLASS, ";");
        }
        r.regel("import nl.bzk.brp.beheer.web.validator.ValidatorUtil;");
        r.regel("import nl.bzk.brp.domein.DomeinObjectFactory;");
        r.regel(MvcControllerGenerator.getModelImports(frame));
        r.regel("import org.apache.commons.lang.StringUtils;");
        r.regel("import org.springframework.stereotype.Controller;");
        r.regel("import org.springframework.ui.ModelMap;");
        r.regel("import org.springframework.validation.BindingResult;");
        r.regel("import org.springframework.web.bind.annotation.ModelAttribute;");
        r.regel("import org.springframework.web.bind.annotation.RequestMapping;");
        r.regel("import org.springframework.web.bind.annotation.RequestMethod;");
        if (FrontEndGeneratorUtil.isHoofdFrame(frame)) {
            r.regel("import org.springframework.web.bind.annotation.RequestParam;");
        }
        r.regel("import org.springframework.web.bind.annotation.SessionAttributes;");
        if (!FrontEndGeneratorUtil.isHoofdFrame(frame)) {
            r.regel("import org.springframework.web.servlet.ModelAndView;");
        }
    }

    /**
     * Genereer de controller methode OpenNieuwPagina.
     *
     * @param frame de frame
     */
    private void genereerControllerMethodeOpenNieuwPagina(final Frame frame) {
        r.regel("/**");
        r.regel(" * Deze method reset de id en zorgt ervoor dat de gebruiker in de toevoegen");
        r.regel(" * flow terecht komt.");
        r.regel(" *");
        r.regel(" * @param modelMap ModelMap");
        r.regel(" * @return de view");
        r.regel(" */");
        r.regel("@RequestMapping(value = \"", getControllerMethodRequestMappingUrl(frame),
                "\", method = RequestMethod.GET, params = \"_nieuw\")");
        r.regel("public String openNieuwPagina(final ModelMap modelMap) {");
        r.incr();
        r.regel("initialiseer(modelMap);");
        r.regel();
        r.regel("return openPagina(modelMap);");
        r.decr();
        r.regel("}");
    }

    /**
     * Genereer de controller methode OpenBewerkPagina.
     *
     * @param frame Frame
     */
    private void genereerdControllerMethodeOpenBewerkPagina(final Frame frame) {
        r.regel("/**");
        r.regel(" * Deze methode zorgt ervoor dat de gebruiker in de raadpleeg en bewerk flow terecht komt.");
        r.regel(" *");
        r.regel(" * @param modelMap ModelMap");
        r.regel(" * @param id Id van de ", FrontEndGeneratorUtil.getFrameBron(frame), ".");
        r.regel(" * @return view");
        r.regel(" */");
        r.regel("@RequestMapping(value = \"", getControllerMethodRequestMappingUrl(frame),
                "\", method = RequestMethod.GET, params = SESSION_ATTRIBUTE_ID)");
        r.regel("public String openBewerkPagina(final ModelMap modelMap, @RequestParam(value = SESSION_ATTRIBUTE_ID,");
        r.incr().regel("required = false) final String id)").decr();
        r.regel("{");
        r.incr();
        r.regel("modelMap.addAttribute(SESSION_ATTRIBUTE_ID, id);");
        r.regel("modelMap.addAttribute(SESSION_ATTRIBUTE_LEESMODUS, true);");
        r.regel();
        r.regel("return openPagina(modelMap);");
        r.decr();
        r.regel("}");
    }

    /**
     * Genereer de controller methode OpenPagina.
     *
     * @param frame Frame
     */
    private void genereerControllerMethodeOpenPagina(final Frame frame) {
        String frameBron = FrontEndGeneratorUtil.getFrameBron(frame);
        r.regel("/**");
        r.regel(" * Deze methode wordt aangeroepen wanneer de gebruiker op de toevoegen knop");
        r.regel(" * drukt of wanneer de gebruiker via de tabs navigeert.");
        r.regel(" *");
        r.regel(" * @param modelMap ModelMap");
        r.regel(" * @return view");
        r.regel("*/");
        r.regel("@RequestMapping(value = \"", getControllerMethodRequestMappingUrl(frame),
                "\", method = RequestMethod.GET)");
        r.regel("public String openPagina(final ModelMap modelMap) {");
        r.incr();
        r.regel(MvcFormBeanGenerator.getFormBeanNaam(frame), " formBean = new ",
                MvcFormBeanGenerator.getFormBeanNaam(frame), "(domeinObjectFactory.create",
                FrontEndGeneratorUtil.getFormulierBron(frame.getFormulier()), "());");
        r.regel();
        r.regel("// Haal de ", frameBron, " op");
        r.regel("String id = (String) modelMap.get(SESSION_ATTRIBUTE_ID);");
        r.regel("if (StringUtils.isNotBlank(id)) {");
        r.incr().regel("formBean.set", frameBron, "(domeinService.ophalenObject", "(Integer.parseInt(id)));").decr();
        r.regel("} else if (id == null) {");
        r.incr().regel("initialiseer(modelMap);").decr();
        r.regel("}");
        r.regel();
        r.regel("formBean.setLeesModus((Boolean) modelMap.get(SESSION_ATTRIBUTE_LEESMODUS));");
        r.regel();
        r.regel("modelMap.addAttribute(SESSION_ATTRIBUTE_COMMAND, formBean);");
        r.regel();
        genereerModelMapStamgegevens();
        r.regel();
        r.regel("return BEWERK_PAGINA;");
        r.decr();
        r.regel("}");
    }

    /**
     * Genereer de openPagina methode voor secundaire tabs.
     *
     * @param frame Frame
     */
    private void genereerSecondairTabControllerMethodeOpenPagina(final Frame frame) {
        String formulierBron = FrontEndGeneratorUtil.getFormulierBron(frame.getFormulier());
        r.regel("/**");
        r.regel(" * Openen van de rol pagina.");
        r.regel(" *");
        r.regel(" * @param id id van de parent entity");
        r.regel(" * @param leesModus de lees modus");
        r.regel(" * @param modelMap ModelMap");
        r.regel(" * @return view");
        r.regel(" */");
        r.regel("@RequestMapping(value = \"", getControllerMethodRequestMappingUrl(frame),
                "\", method = RequestMethod.GET)");
        r.regel("public ModelAndView openToevoegenPagina(@ModelAttribute(SESSION_ATTRIBUTE_ID) final String id,");
        r.incr()
                .incr()
                .regel("@ModelAttribute(SESSION_ATTRIBUTE_LEESMODUS) final boolean leesModus, ",
                        "final ModelMap modelMap)").decr().decr();
        r.regel("{");
        r.incr();
        r.regel("if (StringUtils.isBlank(id)) {");
        r.incr();
        r.regel("getMessageUtil().addMessage(MessageSeverity.INFO, \"bericht.parentnietopgeslagen\",");
        r.incr();
        r.incr();
        r.regel("new String[] { \"entity.", FrontEndGeneratorUtil.getFrameBron(frame), "\", \"entity.", formulierBron,
                "\" }, true);");
        r.regel();
        r.decr();
        r.decr();
        r.regel("return redirect(\"", getControllerMethodRequestMappingUrl(frame.getFormulier().getFrames().get(0))
                .substring(1), "\");");
        r.decr();
        r.regel("}");
        r.regel();
        r.regel(MvcFormBeanGenerator.getFormBeanNaam(frame), " formBean =");
        r.incr();
        r.regel("new ", MvcFormBeanGenerator.getFormBeanNaam(frame), "(domeinObjectFactory.create", formulierBron,
                "(),");
        r.incr().regel("domeinObjectFactory.create", FrontEndGeneratorUtil.getFrameBron(frame), "());").decr();
        r.decr();
        r.regel("formBean.setLeesModus(leesModus);");
        r.regel("formBean.set", formulierBron, "(domeinService.ophalenObject", "(Integer.parseInt(id)));");
        r.regel();
        r.regel("modelMap.addAttribute(SESSION_ATTRIBUTE_COMMAND, formBean);");
        r.regel();
        genereerModelMapStamgegevens();
        r.regel();
        r.regel("return new ModelAndView(BEWERK_PAGINA);");
        r.decr();
        r.regel("}");
        r.regel();
    }

    /**
     * Genereert de methode toevoegen.
     *
     * @param frame Frame
     */
    private void genereerControllerMethodeToevoegen(final Frame frame) {
        String frameBron = FrontEndGeneratorUtil.getFrameBron(frame);

        r.regel("/**");
        r.regel(" * ", frameBron, " toevoegen.");
        r.regel(" *");
        r.regel(" * @param formBean FormBean");
        r.regel(" * @param result BindingResult");
        r.regel(" * @param modelMap ModelMap");
        r.regel(" * @return view");
        r.regel(" */");
        r.regel("@RequestMapping(value = \"", getControllerMethodRequestMappingUrl(frame),
                "\", method = RequestMethod.POST,");
        r.regel("                params = \"", getControllerMethodRequestParamToevoegen(frame), "\")");
        r.regel("public String voeg", frameBron, "Toe(");
        r.incr().incr();
        r.regel("@ModelAttribute(SESSION_ATTRIBUTE_COMMAND) final ", MvcFormBeanGenerator.getFormBeanNaam(frame),
                " formBean,");
        r.regel("final BindingResult result, final ModelMap modelMap)");
        r.decr().decr();
        r.regel("{");
        r.incr();
        r.regel("// Velden die op de scherm zitten");
        // Genereerd zichtbare velden voor validatie
        r.regel("String[] velden = {");
        r.incr();
        for (FrameVeld veld : frame.getVelden()) {
            String veldPad = MvcFormBeanGenerator.getPadNaarToeTeVoegenObjectAttribuut(veld);
            if (frame.getVelden().get(frame.getVelden().size() - 1).getId() != veld.getId()) {
                r.regel("\"", veldPad, "\",");
            } else {
                r.regel("\"", veldPad, "\"");
            }
        }
        r.decr();
        r.regel("};");
        r.regel();
        r.regel("if (ValidatorUtil.isValid(result, formBean, velden)) {");
        r.incr();
        String attribuutToeTeVoegen = MvcFormBeanGenerator.getAttribuutToeTeVoegen(frame, false);
        String formulierBron = FrontEndGeneratorUtil.getFormulierBron(frame.getFormulier());
        r.regel("formBean.get", formulierBron, "().add", frameBron, "(formBean.get", attribuutToeTeVoegen, "());");
        r.regel("formBean.set", attribuutToeTeVoegen, "(domeinObjectFactory.create", frameBron, "());");
        r.decr();
        r.regel("}");
        r.regel("return null;");
        r.decr();
        r.regel("}");
    }

    /**
     * Genereer de methode verwijderen.
     *
     * @param frame Frame
     */
    private void genereerControllerMethodeVerwijderen(final Frame frame) {
        String formulierBron = FrontEndGeneratorUtil.getFormulierBron(frame.getFormulier());
        String frameBron = FrontEndGeneratorUtil.getFrameBron(frame);

        r.regel("/**");
        r.regel(" * Verwijder rollen.");
        r.regel(" *");
        r.regel(" * @param formBean de formBean");
        r.regel(" * @param result BindingResult");
        r.regel(" * @param modelMap ModelMap0");
        r.regel(" * @return view");
        r.regel(" */");
        r.regel("@RequestMapping(value = \"", getControllerMethodRequestMappingUrl(frame),
                "\", method = RequestMethod.POST,");
        r.regel("                params = \"", getControllerMethodRequestParamVerwijderen(frame), "\")");
        r.regel("public String verwijderen", frameBron, "(");
        r.incr();
        r.regel("@ModelAttribute(SESSION_ATTRIBUTE_COMMAND) final ", MvcFormBeanGenerator.getFormBeanNaam(frame),
                " formBean,");
        r.regel("final BindingResult result, final ModelMap modelMap)");
        r.decr();
        r.regel("{");
        r.regel();
        r.incr();
        r.regel("if (formBean.getTeVerwijderen", frameBron, "() == null) {");
        r.incr();
        r.regel("getMessageUtil().addMessage(MessageSeverity.INFO, \"bericht.nietgeselecteerd\",");
        r.incr().regel("new String[] { \"entity.", frameBron, "\" }, false);").decr();
        r.regel("return null;");
        r.decr();
        r.regel("}");
        r.regel();
        r.regel("for (String id : formBean.", MvcFormBeanGenerator.getGetterTeVerwijderenAttribuut(frame), ") {");
        r.incr();
        r.regel(frameBron, " teVerwijderen = null;");
        r.regel("for (", frameBron, " object : formBean.get", formulierBron, "().get", frameBron, "en()) {");
        r.incr();
        r.regel("if (object.hashCode() == Integer.parseInt(id)) {");
        r.incr();
        r.regel("teVerwijderen = object;");
        r.regel("break;");
        r.decr();
        r.regel("}");
        r.decr();
        r.regel("}");
        r.regel("formBean.get", formulierBron, "().remove", frameBron, "(teVerwijderen);");
        r.decr();
        r.regel("}");
        r.regel();
        r.regel();
        r.decr();
        r.regel("    return null;");
        r.regel("}");
    }

    /**
     * Genereer code om stamgegevens in de modelmap te stoppen.
     *
     */
    private void genereerModelMapStamgegevens() {
        for (FrameVeld veld : stamgegevensVelden) {
            r.regel("modelMap.addAttribute(\"", getStamGegevensVariabelNaam(veld), "\", stamgegevensService.",
                    MvcServiceGenerator.getGetterVoorStamgegevens(veld), ");");
        }
    }

    /**
     * Genereer controller methode wijzigen.
     *
     * @param frame Frame
     */
    private void genereerControllerMethodeWijzigen(final Frame frame) {
        r.regel("/**");
        r.regel(" * Zet de raadpleeg modus in bewerk modus.");
        r.regel(" *");
        r.regel(" * @param formBean FormBean");
        r.regel(" * @return view");
        r.regel("*/");
        r.regel("@RequestMapping(value = \"", getControllerMethodRequestMappingUrl(frame),
                "\", params = \"_wijzigen\")");
        r.regel("public String wijzigen(@ModelAttribute(SESSION_ATTRIBUTE_COMMAND) final ",
                MvcFormBeanGenerator.getFormBeanNaam(frame), " formBean) {");
        r.incr();
        r.regel("formBean.setLeesModus(false);");
        r.regel();
        r.regel("return null;");
        r.decr();
        r.regel("}");
    }

    /**
     * Genereer de controller methode opslaan.
     *
     * @param frame Frame
     */
    private void genereerControllerMethodeOpslaan(final Frame frame) {
        String formulierBron = FrontEndGeneratorUtil.getFormulierBron(frame.getFormulier());
        String frameBron = FrontEndGeneratorUtil.getFrameBron(frame);
        r.regel("/**");
        r.regel(" * Opslaan van de ", frameBron, ".");
        r.regel(" *");
        r.regel(" * @param formBean FormBean");
        r.regel(" * @param result BindingResult");
        r.regel(" * @param modelMap ModelMap");
        r.regel(" * @return view");
        r.regel(" */");
        r.regel("@RequestMapping(value = \"", getControllerMethodRequestMappingUrl(frame),
                "\", method = RequestMethod.POST, params = \"_opslaan\")");
        r.regel("public String opslaan(@ModelAttribute(SESSION_ATTRIBUTE_COMMAND) final ",
                MvcFormBeanGenerator.getFormBeanNaam(frame), " formBean,");
        r.incr().incr().regel("final BindingResult result, final ModelMap modelMap)").decr().decr();
        r.regel("{");
        r.incr();
        if (FrontEndGeneratorUtil.isHoofdFrame(frame)) {
            r.regel("// Velden die op de scherm zitten");
            // Genereerd zichtbare velden voor validatie
            r.append("        String[] velden = { ");
            for (FrameVeld veld : frame.getVelden()) {
                String veldPad = MvcFormBeanGenerator.getAttribuutPad(veld);
                if (frame.getVelden().get(0).getId() != veld.getId()) {
                    r.append(", ");
                }
                r.append("\"");
                r.append(veldPad);
                r.append("\"");
            }
            r.decr();
            r.decr();
            r.regel("};");
            r.regel();
            r.incr();
            r.incr();
            r.regel("if (!ValidatorUtil.isValid(result, formBean, velden)) {");
            r.incr();
            r.regel("// Terug naar de pagina van submit");
            r.regel("return null;");
            r.decr();
            r.regel("}");
            r.regel();
        }
        r.regel("// Sla op in database");
        r.regel("formBean.set", formulierBron, "(domeinService.opslaan(formBean.get", formulierBron, "()));");
        r.regel();
        if (FrontEndGeneratorUtil.isHoofdFrame(frame)) {
            r.regel("// Sla op in sessie");
            r.regel("modelMap.addAttribute(SESSION_ATTRIBUTE_ID, formBean.get", formulierBron,
                    "().getID().toString());");
            r.regel();
        }
        r.regel("getMessageUtil().addMessage(MessageSeverity.SUCCESS, \"bericht.isopgeslagen\",");
        r.incr().incr().regel("new String[] { \"entity.", frameBron, "\" }, false);").decr().decr();
        r.regel();
        r.regel("formBean.setLeesModus((Boolean) modelMap.get(SESSION_ATTRIBUTE_LEESMODUS));");
        r.regel();
        r.regel("return null;");
        r.decr();
        r.regel("}");
    }

    /**
     * Genereer controlle methode initialiseer.
     */
    private void genereerControllerMethodeInitialiseer() {
        r.regel("/**");
        r.regel(" * Initialiseerd the flow.");
        r.regel(" *");
        r.regel(" * @param modelMap ModelMap");
        r.regel(" */");
        r.regel("private void initialiseer(final ModelMap modelMap) {");
        r.incr();
        r.regel("modelMap.addAttribute(SESSION_ATTRIBUTE_ID, \"\");");
        r.regel("modelMap.addAttribute(SESSION_ATTRIBUTE_LEESMODUS, false);");
        r.decr();
        r.regel("}");
    }

    /**
     * Veld naam voor de stamgegevens.
     *
     * @param veld FrameVeld
     * @return de naam van de stamgegevens veld
     */
    public static String getStamGegevensVariabelNaam(final FrameVeld veld) {
        return FrontEndGeneratorUtil.getAttribuutType(veld);
    }

    /**
     * Bouwt de request url van de controller method.
     *
     * @param frame Frame
     * @return requestMapping url van de controller method
     */
    public static String getControllerMethodRequestMappingUrl(final Frame frame) {
        return "/" + FrontEndGeneratorUtil.getFrameNaam(frame).toLowerCase() + MvcControllerGenerator.URL_SUFFIX;
    }

    /**
     * Bouwt de request url om een controller method aan te roepen.
     *
     * @param frame Frame
     * @return web request url naar de methode
     */
    public static String getRequestUrlVoorController(final Frame frame) {
        return MvcControllerGenerator.getControllerRequestMappingUrl(frame.getFormulier())
            + getControllerMethodRequestMappingUrl(frame);
    }

    /**
     * Geeft de methode naam terug voor toevoegen functionaliteit.
     *
     * @param frame Frame
     * @return de naam van de methode toevoegen.
     */
    public static String getControllerMethodRequestParamToevoegen(final Frame frame) {
        return KNOP_TOEVOEGEN + FrontEndGeneratorUtil.getFrameBron(frame);
    }

    /**
     * Geeft de methode naam terug voor verwijderen functionaliteit.
     *
     * @param frame Frame
     * @return de naam van de methode verwijderen.
     */
    public static String getControllerMethodRequestParamVerwijderen(final Frame frame) {
        return KNOP_VERWIJDEREN + FrontEndGeneratorUtil.getFrameBron(frame);
    }
}
