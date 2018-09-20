/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators.frontend;

import static java.beans.Introspector.decapitalize;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.bmr.generator.AbstractGenerator;
import nl.bzk.brp.bmr.generator.Generator;
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
 * {@link Generator} implementatie voor services voor de controller.
 */
@Component
public class MvcServiceGenerator extends AbstractGenerator implements Generator {

    private static final Logger LOGGER                       = LoggerFactory.getLogger(MvcControllerGenerator.class);

    private static final String PACKAGE                      = "nl.bzk.brp.beheer.web.service";

    private static final String BEAN_DEFINITIES              = "spring/generated-beans.xml";

    /** package naar de stamgegevensservice. **/
    public static final String  STAMGEGEVENS_SERVICE_PACKAGE = PACKAGE + ".stamgegevens";
    /** de class naam voor de stamgegevens service. */
    public static final String  STAMGEGEVENS_SERVICE_CLASS   = "StamgegevensService";

    private ArtifactBuilder     r;

    private List<FrameVeld>     stamgegevensVelden;

    @Override
    public void generate(final MetaRegister register, final String naam, final FileSystemAccess file) {
        Applicatie applicatie = register.getApplicatie(naam);
        LOGGER.info("Webinterface genereren voor de '{}' applicatie", applicatie.getNaam());

        stamgegevensVelden = FrontEndGeneratorUtil.getStamgegevensVeldenVoorApplicatie(applicatie);

        genereerPackageInfo(file);
        genereerStamGegevensServiceInterface(file, applicatie);
        genereerStamGegevensServiceImpl(file, applicatie);
        genereerDomeinServiceBeanDefinities(file, applicatie);
    }

    /**
     * Genereert de Package-info.java.
     *
     * @param file FileSystemAccess
     */
    private void genereerPackageInfo(final FileSystemAccess file) {
        r = new ArtifactBuilder();

        r.regel("package ", STAMGEGEVENS_SERVICE_PACKAGE, ";");

        file.generateFile(STAMGEGEVENS_SERVICE_PACKAGE.replace(".", "/") + "/Package-info.java", r);
    }

    /**
     * Genereer stamgegevensservice class implementatie.
     *
     * @param file FileSystemAccess
     * @param applicatie Applicatie
     */
    private void genereerStamGegevensServiceImpl(final FileSystemAccess file, final Applicatie applicatie) {
        String packageLokatie = STAMGEGEVENS_SERVICE_PACKAGE.replace(".", "/");
        String javaClassLokatie = packageLokatie + "/" + STAMGEGEVENS_SERVICE_CLASS + "Impl.java";

        LOGGER.debug("Genereren Service '{}'.", javaClassLokatie);

        r = new ArtifactBuilder();

        r.regel("package ", PACKAGE, ".stamgegevens;");
        r.regel();
        r.regel("import java.util.LinkedHashMap;");
        r.regel("import java.util.List;");
        r.regel("import java.util.Locale;");
        r.regel("import java.util.Map;");
        r.regel();
        r.regel("import javax.inject.Inject;");
        r.regel();
        r.regel("import nl.bzk.brp.beheer.web.dao.GenericDao;");
        r.regel("import nl.bzk.brp.domein.DomeinObjectFactory;");
        r.regel(getModelImports());
        r.regel("import org.springframework.context.MessageSource;");
        r.regel("import org.springframework.stereotype.Service;");
        r.regel();
        r.regel();
        r.regel("/**");
        r.regel(" * Service om stamgegevens op te halen.");
        r.regel(" *");
        r.regel(" */");
        r.regel("@Service");
        r.regel("public class StamgegevensServiceImpl implements StamgegevensService {");
        r.regel();
        r.incr();
        r.regel("@Inject");
        r.regel("private DomeinObjectFactory domeinObjectFactory;");
        r.regel();
        r.regel("@Inject");
        r.regel("private GenericDao    genericDao;");
        r.regel();
        r.regel("@Inject");
        r.regel("private MessageSource messageSource;");
        r.regel();

        for (FrameVeld veld : stamgegevensVelden) {
            String attribuutType = FrontEndGeneratorUtil.getAttribuutType(veld);
            if (FrontEndGeneratorUtil.isEnumVeld(veld)) {
                r.regel("@Override");
                r.regel("public Map<", attribuutType, ", String> ", getGetterVoorStamgegevens(veld), " {");
                r.incr();
                r.regel("Map<", attribuutType, ", String> vertaaldeEnumWaarde = ", "new LinkedHashMap<", attribuutType,
                        ", String>();");
                r.regel();
                r.regel("for (", attribuutType, " enumWaarde : ", attribuutType, ".values()) {");
                r.incr();
                r.regel("if (enumWaarde != ", attribuutType, ".DUMMY) {");
                r.incr();
                r.regel("vertaaldeEnumWaarde.put(enumWaarde,");
                r.incr()
                        .incr()
                        .regel("messageSource.getMessage(\"", MessagePropertiesGenerator.getEnumPrefix(veld),
                                "\" + enumWaarde, null, Locale.getDefault()));").decr().decr();
                r.decr();
                r.regel("}");
                r.decr();
                r.regel("}");
                r.regel();
                r.regel("return vertaaldeEnumWaarde;");
                r.decr();
                r.regel("}");
                r.regel();
            } else if (FrontEndGeneratorUtil.isDynamischVeld(veld)) {
                r.regel("@SuppressWarnings(\"unchecked\")");
                r.regel("@Override");
                r.regel("public List<", attribuutType, "> ", getGetterVoorStamgegevens(veld), " {");
                r.incr()
                        .regel("return (", "List<", attribuutType, ">",
                                ") genericDao.findAll(domeinObjectFactory.getImplementatie(", attribuutType,
                                ".class));").decr();
                r.regel("}");
                r.regel();
            }
        }

        r.regel("@Override");
        r.regel("public <T> Object find(final Class<T> entityClass, final Integer id) {");
        r.incr().regel("return genericDao.getById(domeinObjectFactory.getImplementatie(entityClass), id);").decr();
        r.regel("}");
        r.regel();
        r.regel("public void setGenericDao(final GenericDao genericDao) {");
        r.incr().regel("this.genericDao = genericDao;").decr();
        r.regel("}");
        r.regel();
        r.regel("public void setDomeinObjectFactory(final DomeinObjectFactory domeinObjectFactory) {");
        r.incr().regel("this.domeinObjectFactory = domeinObjectFactory;").decr();
        r.regel("}");
        r.decr();
        r.regel("}");
        r.regel();

        file.generateFile(javaClassLokatie, r);
    }

    /**
     * Genereer stamgegevensservice class interface.
     *
     * @param file FileSystemAccess
     * @param applicatie Applicatie
     */
    private void genereerStamGegevensServiceInterface(final FileSystemAccess file, final Applicatie applicatie) {
        String packageLokatie = STAMGEGEVENS_SERVICE_PACKAGE.replace(".", "/");
        String javaClassLokatie = packageLokatie + "/" + STAMGEGEVENS_SERVICE_CLASS + ".java";

        LOGGER.debug("Genereren Service '{}'.", javaClassLokatie);

        r = new ArtifactBuilder();

        r.regel("package ", PACKAGE, ".stamgegevens;");
        r.regel();
        r.regel("import java.util.List;");
        r.regel("import java.util.Map;");
        r.regel();
        r.regel(getModelImports());
        r.regel();
        r.regel();
        r.regel("/**");
        r.regel(" * Service om de stamgegevens op te halen.");
        r.regel(" *");
        r.regel(" */");
        r.regel("public interface StamgegevensService {");
        r.regel();
        r.incr();
        for (FrameVeld veld : stamgegevensVelden) {
            String attribuutType = FrontEndGeneratorUtil.getAttribuutType(veld);
            if (FrontEndGeneratorUtil.isEnumVeld(veld)) {
                r.regel("/**");
                r.regel(" * Haal alle ", attribuutType, " op.");
                r.regel(" *");
                r.regel(" * @return lijst van ", attribuutType);
                r.regel(" */");
                r.regel("Map<", attribuutType, ", String> ", getGetterVoorStamgegevens(veld), ";");
                r.regel();
            } else if (FrontEndGeneratorUtil.isDynamischVeld(veld)) {
                r.regel("/**");
                r.regel(" * Haal alle ", attribuutType, " op.");
                r.regel(" *");
                r.regel(" * @return lijst van ", attribuutType);
                r.regel(" */");
                r.regel("List<", attribuutType, "> ", getGetterVoorStamgegevens(veld), ";");
                r.regel();
            }
        }
        r.regel("/**");
        r.regel(" * Algemene ophaal met id methode.");
        r.regel(" *");
        r.regel(" * @param <T> type van de op te halen object");
        r.regel(" * @param entityClass");
        r.regel(" *            type entity dat gezocht moet worden");
        r.regel(" * @param id");
        r.regel(" *            id van de entity");
        r.regel(" * @return object van de opgegeven type");
        r.regel(" */");
        r.regel("<T> Object find(final Class<T> entityClass, final Integer id);");
        r.decr();
        r.regel("}");
        r.regel();

        file.generateFile(javaClassLokatie, r);
    }

    /**
     * Genereer service beans definities.
     *
     * @param file FileSystemAccess
     * @param applicatie de applicatie waarvoor de definities gegenereerd moet worden
     */
    private void genereerDomeinServiceBeanDefinities(final FileSystemAccess file, final Applicatie applicatie) {
        LOGGER.debug("Genereren Service bean Definities '{}'.", BEAN_DEFINITIES);

        r = new ArtifactBuilder();

        r.regel("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        r.regel("<beans xmlns=\"http://www.springframework.org/schema/beans\"");
        r.incr();
        r.regel("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xmlns:context=\"http://www.springframework.org/schema/context\"");
        r.regel("xsi:schemaLocation=\"http://www.springframework.org/schema/beans "
            + "http://www.springframework.org/schema/beans/spring-beans.xsd");
        r.incr()
                .incr()
                .regel("http://www.springframework.org/schema/context "
                    + "http://www.springframework.org/schema/context/spring-context-3.0.xsd\">").decr().decr();
        r.regel();

        geneerConverteerBaarBeans();

        r.regel();

        for (Formulier formulier : applicatie.getFormulieren()) {
            r.regel("<bean id=\"", getDomeinServiceNaam(formulier),
                    "\" class=\"nl.bzk.brp.beheer.web.service.GenericDomeinServiceImpl\">");
            r.incr()
                    .regel("<property name=\"dao\" ref=\"",
                            decapitalize(FrontEndGeneratorUtil.getFormulierBron(formulier)), "Dao\"/>").decr();
            r.regel("</bean>");
            r.regel("");
        }

        r.decr();
        r.regel("</beans>");

        file.generateFile(BEAN_DEFINITIES, r);
    }

    /**
     * Genereer definities voor de ConversionService om aan te geven welke objecten van string naar entity geconverteerd
     * kunnen worden en andersom.
     */
    private void geneerConverteerBaarBeans() {
        r.regel("<bean id=\"conversionService\"");
        r.incr();
        r.regel("class=\"org.springframework.context.support.ConversionServiceFactoryBean\">");
        r.regel("<property name=\"converters\">");
        r.incr();
        r.regel("<list>");
        r.incr();
        r.regel("<bean class=\"nl.bzk.brp.beheer.web.converter.StringToEntityConverter\">");
        r.incr();
        r.regel("<property name=\"entitiesToConvert\">");
        r.incr();
        r.regel("<list>");
        r.incr();
        for (FrameVeld veld : stamgegevensVelden) {
            if (FrontEndGeneratorUtil.isDynamischVeld(veld)) {
                r.regel("<value>", FrontEndGeneratorUtil.getModelPad(veld), "</value> ");
            }
        }
        r.decr();
        r.regel("</list>");
        r.decr();
        r.regel("</property>");
        r.decr();
        r.regel("</bean>");
        r.decr();
        r.regel("</list>");
        r.decr();
        r.regel("</property>");
        r.decr();
        r.regel("</bean>");
    }

    /**
     * Haalte de model imports op voor alle stamgegevens die gebruikt worden binnen de applicatie.
     *
     * @return string met alle imports.
     */
    private String getModelImports() {
        List<String> imports = new ArrayList<String>();
        for (FrameVeld veld : stamgegevensVelden) {
            if (FrontEndGeneratorUtil.isEnumVeld(veld) || FrontEndGeneratorUtil.isDynamischVeld(veld)) {
                imports.add(FrontEndGeneratorUtil.getModelPad(veld));
            }
        }

        return FrontEndGeneratorUtil.sorteerEnMaakImportStrings(imports);
    }

    /**
     * Geeft de getter methode terug voor een stamgegeven.
     *
     * @param veld FrameVeld
     * @return de getter voor betreffende stamgegeven
     */
    public static String getGetterVoorStamgegevens(final FrameVeld veld) {
        return "get" + FrontEndGeneratorUtil.getAttribuutType(veld) + "()";
    }

    /**
     * Geeft de domein service naam voor de formulier.
     *
     * @param formulier Formulier
     * @return de domein service naam
     */
    public static String getDomeinServiceNaam(final Formulier formulier) {
        return decapitalize(FrontEndGeneratorUtil.getFormulierBron(formulier)) + "Service";
    }
}
