/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import joptsimple.OptionSet;
import nl.bzk.brp.bevraging.exceptions.CommandException;
import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Applicatie die op basis van de argumenten een "programma" start.
 */
public class CliApplicatie {
    private static final Logger LOGGER = LoggerFactory.getLogger(CliApplicatie.class);

    private static ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("datasource-context.xml", "bevraging-beans.xml");
    /**
     * Beginpunt van de applicatie.
     *
     * @param args argumenten gegeven aan de applicatie
     * @throws Exception bij een fout in de applicatie
     */
    public static void main(final String[] args) throws Exception {
        CliParser parser = new CliParser();
        OptionSet options = null;

        Catalog catalog = applicationContext.getBean("catalog", Catalog.class);

        List<String> names = new ArrayList<String>();
        Iterator knownNames = catalog.getNames();
        while (knownNames.hasNext()) {
            names.add((String) knownNames.next());
        }

        try {
            options = parser.parse(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // als de commandline niet correct is, of help optie bevat, print help EXIT
            if (args.length == 0 || options == null || options.has("help")) {
                parser.printHelpOn(System.out);
                System.exit(1);
            }
        }

        String scenario = (String) options.valueOf("scenario");

        if (!names.contains(scenario)) {
            System.out.printf("Opgegeven scenario '%s' kan niet worden uitgevoerd\n", scenario);
            System.out.printf("Mogelijke scenario's zijn: %s\n", names);

            System.exit(1);
        }

        LOGGER.info("########## Uitvoer van scenario: {}", scenario);
        App app = new CommandRunnerApp(catalog.getCommand(scenario));

        Context context = new ContextBase();
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, options.valueOf("bsn"));
        context.put(ContextParameterNames.AANTAL_THREADS, options.valueOf("threads"));
        context.put(ContextParameterNames.SCENARIO, options.valueOf("scenario"));
        context.put(ContextParameterNames.DUBBELE_PER, options.valueOf("dubbele"));
        context.put(ContextParameterNames.COMMENT, options.valueOf("message"));
        context.put(ContextParameterNames.FILENAME, options.valueOf("file"));
        context.put(ContextParameterNames.AANTAL_AFNEMERS, options.valueOf("afnemers"));
        context.put(ContextParameterNames.PERSOON_ID, options.valueOf("id"));

        try {
            app.run(context);
        } catch (CommandException e) {
            LOGGER.error("Fout tijdens uitvoer: {}", e.getMessage(), e);
        }

        System.exit(0);
    }
}
