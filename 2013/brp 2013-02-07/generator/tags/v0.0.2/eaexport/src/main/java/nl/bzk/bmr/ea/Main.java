/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.bmr.ea;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.Arrays;

import com.google.common.base.Strings;

import nl.bzk.brp.bmr.metamodel.SoortExport;
import nl.bzk.brp.bmr.metamodel.VersieTag;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessResourceFailureException;


public class Main {

    private static final String COMMAND_LINE_PREFIX = "eaexport domein";
    private static final Logger LOGGER              = LoggerFactory.getLogger(Main.class);

    @SuppressWarnings("static-access")
    public static void main(final String... args) throws Exception {

        /*
         * Definieer command-line opties:
         */
        Options options = new Options();
        options.addOption(OptionBuilder.withLongOpt("help").withType(boolean.class)
                .withDescription("Toont deze help informatie").create("h"));
        options.addOption(OptionBuilder.withLongOpt("debug").withType(boolean.class)
                .withDescription("Geef meer debugging informatie").create("d"));

        OptionGroup group = new OptionGroup();
        group.setRequired(true);
        Option exportOption =
            OptionBuilder.withLongOpt("export").withDescription("exporteer de BMR als Enterprise Architect export")
                    .create("e");
        group.addOption(exportOption);
        Option importOption =
            OptionBuilder.withLongOpt("import")
                    .withDescription("importeer een bestand met Enterprise Architect GUID's").create("i");
        group.addOption(importOption);
        options.addOptionGroup(group);

        options.addOption(OptionBuilder.withLongOpt("soort").isRequired().withArgName("soort").hasArg()
                .withDescription("het soort export: " + Arrays.asList(SoortExport.values())).create("s"));
        options.addOption(OptionBuilder.withLongOpt("versietag").withArgName("tag").hasArg()
                .withDescription("de versie tag. Default is 'W'").create("t"));
        options.addOption(OptionBuilder.withLongOpt("file").withArgName("file").hasArg().withType(File.class)
                .isRequired().withDescription("de input of output file.").create("f"));
        options.addOption(OptionBuilder.withLongOpt("url").withArgName("url").hasArg()
                .withDescription("de JDBC url van de database met het BRP metaregister").create("u"));
        options.addOption(OptionBuilder.withLongOpt("username").withArgName("username").hasArg()
                .withDescription("de JDBC username voor de database met het BRP metaregister").create("n"));
        options.addOption(OptionBuilder.withLongOpt("password").withArgName("password").hasArg()
                .withDescription("het JDBC password voor de database met het BRP metaregister").create("p"));
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(120);

        /*
         * Parse command-line argumenten en opties:
         */
        ImportExport importExport = ImportExport.EXPORT;
        SoortExport soort = null;
        String versieTag = VersieTag.W.name();
        File file = null;
        CommandLineParser parser = new GnuParser();
        CommandLine cl = null;
        try {
            cl = parser.parse(options, args);
        } catch (MissingOptionException e) {
            LOGGER.error("Er ontbreken één of meer verplichte opties: '{}'", e.getMissingOptions());
            formatter.printHelp(COMMAND_LINE_PREFIX, options, true);
            return;
        } catch (ParseException e) {
            LOGGER.error("De commandline is niet goed. Oorzaak: '{}'", e.getMessage());
            formatter.printHelp(COMMAND_LINE_PREFIX, options, true);
            return;
        }
        /*
         * 'help' optie.
         */
        if (cl.hasOption("h")) {
            formatter.printHelp(COMMAND_LINE_PREFIX, options, true);
            return;
        }
        /*
         * 'debug' optie.
         */
        if (cl.hasOption("d")) {
            org.apache.log4j.Logger root = org.apache.log4j.Logger.getLogger(LOGGER.getName());
            root.setLevel(Level.DEBUG);
        }
        /*
         * 'domein' argument.
         */
        if (cl.getArgs().length != 1) {
            LOGGER.error("Verplicht argument 'domein' ontbreekt.");
            formatter.printHelp(COMMAND_LINE_PREFIX, options, true);
            return;
        }
        String domein = cl.getArgs()[0];
        /*
         * 'import' of 'export' optie.
         */
        if (cl.hasOption("e")) {
            importExport = ImportExport.EXPORT;
        } else if (cl.hasOption("i")) {
            importExport = ImportExport.IMPORT;
        }
        /*
         * 'soort' optie.
         */
        try {
            soort = SoortExport.valueOf(cl.getOptionValue("s"));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Soort '{}' is geen geldige soort. Geldig zijn: {}", cl.getOptionValue("s"),
                    SoortExport.values());
            formatter.printHelp(COMMAND_LINE_PREFIX, options, true);
            return;
        }
        /*
         * 'versietag' optie.
         */
        if (cl.hasOption("t")) {
            versieTag = cl.getOptionValue("t");
        }
        /*
         * 'output' optie.
         */
        if (cl.hasOption("f")) {
            file = (File) cl.getParsedOptionValue("f");
        }
        /*
         * 'url' optie.
         */
        if (cl.hasOption("u")) {
            URI uri = new URI(cl.getOptionValue("u"));
            System.setProperty("jdbc.url", uri.toASCIIString());
        }
        /*
         * 'username' optie.
         */
        if (cl.hasOption("n")) {
            System.setProperty("jdbc.username", cl.getOptionValue("n"));
        }
        /*
         * 'password' optie.
         */
        if (cl.hasOption("p")) {
            System.setProperty("jdbc.password", cl.getOptionValue("p"));
        }
        /*
         * Gevonden argumenten en opties loggen.
         */
        LOGGER.debug("Domein: '{}'", domein);
        LOGGER.debug("Import/export: '{}'", importExport);
        LOGGER.debug("Soort export: '{}'.", soort);
        LOGGER.debug("Versie tag: '{}'.", versieTag);
        LOGGER.debug("File: '{}'", file == null ? "-" : file);
        if (System.getProperty("jdbc.url") != null) {
            LOGGER.debug("JDBC URL: '{}'.", System.getProperty("jdbc.url"));
        }
        if (System.getProperty("jdbc.username") != null) {
            LOGGER.debug("JDBC username: '{}'.", System.getProperty("jdbc.username"));
        }
        if (System.getProperty("jdbc.password") != null) {
            LOGGER.debug("JDBC password: '{}'.", Strings.padEnd("*", System.getProperty("jdbc.password").length(), '*'));
        }

        /*
         * Spring applicatiecontext maken.
         */
        ApplicationContext context = null;
        try {
            context = new ClassPathXmlApplicationContext("classpath:generator-beans.xml");
        } catch (BeanCreationException e) {
            if (e.getCause() instanceof DataAccessResourceFailureException) {
                LOGGER.error("Kon de database niet openen: {}", e.getCause().getCause().getMessage());
                return;
            }
            throw e;
        }
        /*
         * Go!
         */
        SoortExport.setHuidigeSoort(soort);
        switch (importExport) {
            case EXPORT:
                EaExport exporter = context.getBean(EaExport.class);
                exporter.exportXMI(domein, soort, versieTag, file == null ? System.out : new FileOutputStream(file));
                break;
            case IMPORT:
                EaImport importer = context.getBean(EaImport.class);
                importer.importGuids(file);
                break;
            default:
                throw new IllegalArgumentException("Eén van import of export moet opgegeven worden.");
        }
    }
}
