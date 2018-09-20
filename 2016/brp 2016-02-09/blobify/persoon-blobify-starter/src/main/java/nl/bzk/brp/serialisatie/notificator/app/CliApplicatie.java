/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import joptsimple.OptionSet;
import nl.bzk.brp.serialisatie.notificator.exceptions.CommandException;
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
public final class CliApplicatie {

    private static final Logger LOGGER = LoggerFactory.getLogger(CliApplicatie.class);

    private static ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("datasource-beans.xml",
                    "persoon-serialisatie-notificator-context.xml");

    /**
     * Constructor.
     */
    private CliApplicatie() {
    }


    /**
     * Beginpunt van de applicatie.
     *
     * @param args argumenten gegeven aan de applicatie
     * @throws Exception bij een fout in de applicatie
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    // REDEN: Default signature van de main methode gooit een Exception, hier hebben we geen invloed op.
    public static void main(final String[] args) throws Exception {
        final CliParser parser = new CliParser();
        OptionSet options = null;


        final Catalog catalog = applicationContext.getBean("catalog", Catalog.class);

        final List<String> names = new ArrayList<String>();
        final Iterator knownNames = catalog.getNames();
        while (knownNames.hasNext()) {
            names.add((String) knownNames.next());
        }

        try {
            options = parser.parse(args);
        } catch (Exception e) {
            LOGGER.error("Fout bij verwerking argumenten {}", args, e);
        } finally {
            if (args.length == 0 || options == null || options.hasArgument("help")) {
                parser.printHelpOn(System.out);
            }
        }

        if (options != null && options.hasArgument(ContextParameterNames.SCENARIO.getName())
                && names.contains(options.valueOf(ContextParameterNames.SCENARIO.getName())))
        {
            final String scenario = (String) options.valueOf(ContextParameterNames.SCENARIO.getName());

            LOGGER.info("########## Uitvoer van scenario: {}", scenario);
            final CommandRunnerApp abstractApp = new CommandRunnerApp(catalog.getCommand(scenario));

            final Context context = new ContextBase();
            context.put(ContextParameterNames.PERSOON_ID_LIJST_STRINGS, options.valuesOf("persoonIdLijst"));
            context.put(ContextParameterNames.AANTAL_IDS_PER_BATCH, options.valueOf("aantalIdsPerBatch"));
            context.put(ContextParameterNames.TIJD_TUSSEN_BATCHES, options.valueOf("tijdTussenBatches"));
            context.put(ContextParameterNames.AANTAL_UREN_BIJGEHOUDEN_PERSONEN,
                    options.valueOf("aantalUrenBijgehoudenPersonen"));
            context.put(ContextParameterNames.VANAF_PERSOON_ID, options.valueOf("vanafPersoonId"));

            try {
                abstractApp.run(context);
            } catch (CommandException e) {
                LOGGER.error("Fout tijdens uitvoer: {}", e);
            }

            LOGGER.info("########## Einde");
            //        System.exit(0);
        } else {
            LOGGER.error("U dient een geldig scenario op te geven.");
            LOGGER.error("Mogelijke scenario's zijn: {}", names);
        }
    }
}
