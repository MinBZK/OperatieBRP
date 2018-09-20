/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.bmr.generator.utils.ExtensionPointFileSystemAccess;
import nl.bzk.brp.ecore.bmr.BmrFactory;
import nl.bzk.brp.ecore.bmr.BmrPackage;
import nl.bzk.brp.ecore.bmr.MetaRegister;
import nl.bzk.brp.ecore.bmr.util.BmrResourceFactoryImpl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;


/**
 * Launcher voor code generatoren. Kan van een command-line worden gestart, of programmatisch. Dat laatste is nuttig
 * voor bijvoorbeel een maven-plugin.
 */
@Service
public class Main {

    private static final Logger LOGGER                          = LoggerFactory.getLogger(Main.class);

    private static final int    BMR_MODEL_FILE                  = 0;
    private static final int    DOMEIN_ARGUMENT_INDEX           = 1;
    private static final int    OUTPUT_DIRECTORY_ARGUMENT_INDEX = 2;
    private static final int    SOURCE_DIRECTORY_ARGUMENT_INDEX = 3;
    private static final int    GENERATOR_ARGUMENT_INDEX        = 4;

    /**
     * Entrypoint voor de applicatie voor invokatie vanaf een command-line.
     *
     * @param args Dit programma accepteert de volgende argumenten:
     *            <ul>
     *            <li>model: de naam van het XML document met het BMR model waaruit code wordt gegenereerd.
     *            <li>domein: de naam van het domein waarvoor code moet worden gegenereerd.
     *            <li>target: de pad naam van de directory waar de gegenereerde code wordt neergezet.
     *            <li>source: de pad naam van de directory waar gecheckt wordt of extension point al bestaan.
     *            <li>generator...: de generatoren die worden uitgevoerd.
     *            </ul>
     */
    public static void main(final String[] args) {
        try {
            checkArgs(args);
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:generator-beans.xml");
            Main main = context.getBean(Main.class);
            String uri = context.getResource(args[BMR_MODEL_FILE]).getURI().toString();
            main.genereer(uri, args[DOMEIN_ARGUMENT_INDEX], args[OUTPUT_DIRECTORY_ARGUMENT_INDEX],
                    args[SOURCE_DIRECTORY_ARGUMENT_INDEX],
                    Arrays.copyOfRange(args, GENERATOR_ARGUMENT_INDEX, args.length));
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    /**
     * Controleer argumenten.
     *
     * @param args de te controleren argumenten van de command line.
     */
    private static void checkArgs(final String[] args) {
        LOGGER.debug("Argumenten controleren: {}", args);
        if (args.length < GENERATOR_ARGUMENT_INDEX + 1) {
            LOGGER.debug("Actuele argumenten: {}", args);
            throw new IllegalArgumentException("Argumenten: model domein output source generator...");
        }
        String target = args[OUTPUT_DIRECTORY_ARGUMENT_INDEX];
        File pad = new File(target);
        if (!pad.exists()) {
            pad.mkdirs();
        }
    }

    @javax.annotation.Resource
    private Map<String, GeneratorSpecificatie> generatoren;

    /**
     * Genereer code voor het gegeven domein in de gegeven directory.
     *
     * @param de naam van de input file.
     * @param modelNaam de naam van het domein waarvoor code wordt gegenereerd.
     * @param target de pad naam van de directory waar de gegenereerde code wordt neergezet.
     * @param source de pad naam van de directory waar gecheckt wordt of extension point al bestaan.
     * @param generatorNames een lijst van namen van generatoren om uit te voeren.
     */
    public void genereer(final String uri, final String modelNaam, final String target, final String source,
        final String... generatorNames)
    {
        LOGGER.debug("Genereren {} met target {} en source {}", new String[] { modelNaam, target, source });
        ExtensionPointFileSystemAccess file = new ExtensionPointFileSystemAccess(target, source);

        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
                .put(Resource.Factory.Registry.DEFAULT_EXTENSION, new BmrResourceFactoryImpl());
        resourceSet.getPackageRegistry().put(BmrPackage.eNS_URI, BmrFactory.eINSTANCE);
        /*
         * Het volgende statement lijkt overbodig, maar heeft als noodzakelijk side-effect dat het BmrPackage bij EMF
         * geregistreerd wordt. Zie paragraaf 20.2.4 "Registering the Package" in "EMF: Eclipse Modeling Framework",
         * Dave Steinberg, Frank Budinsky, Marcelo Paternostro, Ed Merks, second edition.
         */
        @SuppressWarnings("unused")
        BmrPackage bmrPackage = BmrPackage.eINSTANCE;

        Resource resource = resourceSet.createResource(URI.createURI(uri));
        Map<String, String> options = new HashMap<String, String>();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        try {
            resource.load(options);
        } catch (IOException e) {
            LOGGER.error("ModelFile: {}", uri);
            throw new RuntimeException(e);
        }
        MetaRegister metaRegister = (MetaRegister) resource.getContents().get(0);
        for (String name : generatorNames) {
            LOGGER.debug("Generator {}...", name);
            GeneratorSpecificatie generatorSpecificatie = generatoren.get(name);
            if (generatorSpecificatie == null) {
                LOGGER.error("Generator '{}' onbekend.", name);
                continue;
            }
            Generator generator = generatorSpecificatie.getGenerator();
            if (generator == null) {
                LOGGER.warn("Generator '" + name + "' bestaat niet.");
                continue;
            }
            LOGGER.info(
                    "Generator '{}' voor {} '{}' naar directory '{}'.",
                    new String[] { name, generatorSpecificatie.getContainer(), modelNaam,
                        new File(target).getAbsolutePath() });
            generator.generate(metaRegister, modelNaam, file);
        }
        LOGGER.info("Klaar");
    }
}
