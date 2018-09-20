/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorExceptie;
import nl.bzk.brp.generatoren.java.model.AbstractJavaImplementatieType;
import nl.bzk.brp.generatoren.java.model.AbstractJavaType;
import nl.bzk.brp.generatoren.java.model.JavaEnumeratie;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaInterface;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaSymbolEnum;
import nl.bzk.brp.generatoren.java.writer.formatter.BronCodeFormatter;
import nl.bzk.brp.generatoren.java.writer.formatter.BronCodeFormatterImpl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.misc.STMessage;

/**
 * Class voor het genereren en wegschrijven van Java source code bestanden (voor klasses, interfaces en/of enums) op
 * basis van een 'Platform Specifiek Model' (een Java model in dit geval). Deze class genereert de Javacode op basis
 * van een template (afhankelijk of het een Java klasse, interface of enum is) en schrijft daarna het bestand weg
 * conform de package van de Java klasse/interface/enum, diens naam en natuurlijk het basis generatie pad.
 *
 * @param <T> Java Type dat geschreven/gegenereerd dient te worden.
 */
public class GeneriekeEnkelBestandJavaWriter<T extends AbstractJavaType> implements JavaWriter<T> {

    private static Logger log = LoggerFactory.getLogger(GeneriekeEnkelBestandJavaWriter.class);

    private static final Map<Class<?>, String> TEMPLATES;

    /** Het maximum aantal parameters dat checkstyle toestaat. */
    private static final int MAX_CHECKSTYLE_PARAMETERS = 7;
    private static final String CHECKSTYLE_MAX_PARAMS_OFF = "CHECKSTYLE-BRP:OFF - MAX PARAMS";
    private static final String CHECKSTYLE_MAX_PARAMS_ON = "CHECKSTYLE-BRP:ON - MAX PARAMS";

    private BronCodeFormatter bronCodeFormatter;

    private static final String  PAD_SEPARATOR           = System.getProperty("file.separator");
    private static final int     MAX_LIJN_LENGTE         = 120;
    private static final String  ENCODING                = "UTF-8";
    private static final STGroup TEMPLATE_GROEP          = new STGroupFile("javabestand.stg");
    private static final String  TEMPLATE_OBJECT_ATTR    = "object";
    private static final String  JAVA_EXTENSIE           = ".java";

    static {
        Map<Class<?>, String> templateMap = new HashMap<Class<?>, String>();
        templateMap.put(JavaKlasse.class, "objecttype");
        templateMap.put(JavaInterface.class, "interface");
        templateMap.put(JavaEnumeratie.class, "enumeratie");
        templateMap.put(JavaSymbolEnum.class, "symboltable");
        TEMPLATES = Collections.unmodifiableMap(templateMap);

        // Als er binnen String Template handling iets fout gaat, print dat dan en
        // knal uit de generatie. Dit ivm het anders onopgemerkt blijven van issues
        // rondom het verwerken van de template.
        TEMPLATE_GROEP.setListener(new STErrorListener() {
            @Override
            public void runTimeError(final STMessage msg) {
                System.out.println("ST RUNTIME ERROR: " + msg);
                System.exit(-1);
            }
            @Override
            public void internalError(final STMessage msg) {
                System.out.println("ST INTERNAL ERROR: " + msg);
                System.exit(-1);
            }
            @Override
            public void compileTimeError(final STMessage msg) {
                System.out.println("ST COMPILE ERROR: " + msg);
                System.exit(-1);
            }
            @Override
            public void IOError(final STMessage msg) {
                System.out.println("ST IOERROR: " + msg);
                System.exit(-1);
            }
        });
    }

    private final String generatieBasisFolder;
    private final boolean overschrijf;

    /**
     * Constructor die de basis map zet waarin de sources gegenereerd dienen te worden.
     *
     * @param generatieBasisFolder de basis folder waarin de sources gegenereerd worden.
     * @param overschrijf boolean die aangeeft of een weg te schrijven bestand eventueel overschreven moet worden
     * indien het al bestaat of niet.
     */
    public GeneriekeEnkelBestandJavaWriter(final String generatieBasisFolder, final boolean overschrijf) {
        this.generatieBasisFolder = generatieBasisFolder;
        this.overschrijf = overschrijf;

        this.bronCodeFormatter = new BronCodeFormatterImpl();
    }

    /**
     * Genereert voor de opgegeven Java broncode objecten de code en schrijft deze ook weg naar de bijbehorende
     * bestanden.
     *
     * @param javaBroncodeObjecten de Java broncode-objecten waarvoor de code gegenereerd en weggeschreven dienen te
     * worden.
     * @param generator de generator die de Java broncode-objecten heeft gecreëerd.
     * @return Lijst van weggeschreven java typen.
     */
    @Override
    public List<T> genereerEnSchrijfWeg(final List<T> javaBroncodeObjecten, final AbstractGenerator generator) {
        final List<T> geschrevenBroncodeBestanden = new ArrayList<T>();
        for (T javaBroncodeObject : javaBroncodeObjecten) {
            this.voerCheckstyleNabewerkingUit(javaBroncodeObject);
            final String code = genereerCode(javaBroncodeObject, generator);
            final String geformatteerdeCode = bronCodeFormatter.formatteerCode(code);
            final File bestand = bouwBronBestand(javaBroncodeObject);

            if (!bestand.exists() || overschrijf) {
                try {
                    FileUtils.deleteQuietly(bestand);
                    FileUtils.writeStringToFile(bestand, geformatteerdeCode, ENCODING);
                    geschrevenBroncodeBestanden.add(javaBroncodeObject);
                } catch (IOException e) {
                    String bericht = "IOException opgetreden tijdens het wegschrijven van een java bestand.";
                    log.error(bericht, e);
                    throw new GeneratorExceptie(bericht, e);
                }
            }
        }
        return geschrevenBroncodeBestanden;
    }

    /**
     * In deze methode worden nog enkele specfieke checkstyle bewerkingen gedaan.
     * Hiermee worden checkstyle errors voorkomen op plekken waar we die bewust 'schenden'.
     *
     * @param broncodeObject het broncode object
     */
    private void voerCheckstyleNabewerkingUit(final T broncodeObject) {
        // Voor alle constructoren, functies, getters en setters die gegenereerd worden, geldt:
        // meer dan 7 parameters is niet te voorkomen en accepteren we dus ook.
        List<JavaFunctie> javaFuncties = new ArrayList<JavaFunctie>();
        javaFuncties.addAll(broncodeObject.getFuncties());
        if (broncodeObject instanceof AbstractJavaImplementatieType) {
            AbstractJavaImplementatieType implementatieType = (AbstractJavaImplementatieType) broncodeObject;
            javaFuncties.addAll(implementatieType.getConstructoren());
            // Ok, getters en setters zullen deze regel nooit overtreden, maar ze gaan erbij voor de vorm. :)
            javaFuncties.addAll(implementatieType.getGetters());
            javaFuncties.addAll(implementatieType.getSetters());
        }

        for (JavaFunctie javaFunctie : javaFuncties) {
            if (javaFunctie.getParameters().size() > MAX_CHECKSTYLE_PARAMETERS) {
                // Plak het checkstyle off commentaar achter de huidige javadoc.
                String huidigeJavadoc = StringUtils.join(javaFunctie.getJavaDoc(), " ");
                javaFunctie.setJavaDoc(huidigeJavadoc + String.format("%n%s", CHECKSTYLE_MAX_PARAMS_OFF));
                // Plak het checkstyle on commentaar voor de huidige implementatie van de body.
                String huidigeBody = javaFunctie.getBody();
                javaFunctie.setBody(String.format("// %s%n", CHECKSTYLE_MAX_PARAMS_ON) + huidigeBody);
            }
        }
    }

    /**
     * Bouwt een nieuw bestand voor het opgegeven Java broncode object, waarbij het gehele pad wordt opgegeven. De op
     * de writer geconfigureerde basis map voor generatie zal hier als basis worden gebruikt, waarna verder het package
     * en de naam van het bestand van invloed zijn op de bestandsnaam.
     *
     * @param javaBroncodeObject het Java broncode object waarvoor een bestand gemaakt dient te worden.
     * @return een bestand met een voor het Java broncode object specifieke bestandslocatie.
     */
    private File bouwBronBestand(final T javaBroncodeObject) {
        StringBuilder bestandNaam = new StringBuilder();
        bestandNaam.append(generatieBasisFolder);
        bestandNaam.append(PAD_SEPARATOR);
        if (javaBroncodeObject.getPackagePad() != null) {
            bestandNaam.append(zetPackageOmNaarPad(javaBroncodeObject.getPackagePad()));
            bestandNaam.append(PAD_SEPARATOR);
        }
        bestandNaam.append(javaBroncodeObject.getNaam());
        bestandNaam.append(JAVA_EXTENSIE);
        return new File(bestandNaam.toString());
    }

    /**
     * Zet de opgegeven package naam om naar een platform specifiek pad. Hierbij wordt elk subpackage een aparte map.
     *
     * @param packageNaam de package naam die omgezet dient te worden.
     * @return het platform specifeke pad voor de opgegeven package naam.
     */
    private String zetPackageOmNaarPad(final String packageNaam) {
        return packageNaam.replace(".", PAD_SEPARATOR);
    }

    /**
     * Genereert de Java code voor de opgegeven {@link nl.bzk.brp.generatoren.java.model.AbstractJavaType}.
     * <p>\
     * Hiervoor wordt gebruik gemaakt van een Template, welke afhankelijk is van het type van het Java broncode object,
     * waarbij de code tevens wordt gewrapped.
     * </p>
     *
     * @param javaBroncodeObject de {@link nl.bzk.brp.generatoren.java.model.AbstractJavaType}
     * waarvoor de code moet worden gegenereerd.
     * @param generator de generator die het betreffende Java broncode-object heeft gecreëerd.
     * @return de gegenereerde code (gewrapped en al).
     */
    private String genereerCode(final T javaBroncodeObject, final AbstractGenerator generator) {
        final ST template = getTemplateInstantie(javaBroncodeObject);

        template.add(TEMPLATE_OBJECT_ATTR, javaBroncodeObject);
        return template.render(MAX_LIJN_LENGTE);
    }

    /**
     * Retourneert de correcte Template instatie welke gebruikt dient te worden voor Code generatie.
     *
     * @param clazz De klasse waarvoor een template moet worden opgehaald.
     * @return de String Template die gebruikt dient te worden.
     */
    private ST getTemplateInstantie(final T clazz) {
        String templateName = TEMPLATES.get(clazz.getClass());
        return TEMPLATE_GROEP.getInstanceOf(templateName);
    }

}
