/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.app;

import java.util.List;

import joptsimple.OptionSet;
import nl.bzk.brp.bevraging.exceptions.CommandException;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Applicatie die op basis van de argumenten een "programma" start.
 */
public class CliApplicatie {
    private static final Logger LOGGER = LoggerFactory.getLogger(CliApplicatie.class);

    /**
     * Beginpunt van de applicatie.
     *
     * @param args Argumenten gegeven aan de applicatie
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        CliParser parser = new CliParser();

        OptionSet options = null;
        Context context = new ContextBase();

        try {
            options = parser.parse(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // als de commandline niet correct is, of help optie bevat, print help EXIT
            if (args.length == 0 || options == null || options.has("help")) {
                parser.printHelpOn(System.out);
                System.exit(0);
            }
        }

        String scenario = (String) options.valueOf("scenario");

        LOGGER.info("########## Uitvoer van scenario: {}", scenario);
        App app = new CommandRunnerApp(scenario);

        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, options.valueOf("bsn"));
        context.put(ContextParameterNames.AANTAL_THREADS, options.valueOf("threads"));
        context.put(ContextParameterNames.SCENARIO, options.valueOf("scenario"));
        context.put(ContextParameterNames.DUBBELE_PER, options.valueOf("dubbele"));
        context.put(ContextParameterNames.COMMENT, options.valueOf("message"));
        context.put(ContextParameterNames.FILENAME, options.valueOf("file"));

        try {
            app.run(context);
        } catch (CommandException e) {
            LOGGER.error("Fout tijdens uitvoer: {}", e.getMessage(), e);
        }

        System.exit(0);
    }
}
