/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.app;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * Wrapper om een instantie van {@link OptionParser} die de commandline opties kent.
 */
public class CliParser {

    /**
     * ARG klassificatie.
     */
    public static final String ARG = "ARG";

    private OptionParser parser;


    /**
     * Default constructor.
     */
    public CliParser() {
        parser = this.createParser();
    }

    /**
     * Copied from {@link OptionParser#parse(String...)}
     * Parses the given command line arguments according to the option specifications given to the parser.
     *
     * @param args arguments to parse
     * @return an {@link OptionSet} describing the parsed options, their arguments, and any non-option arguments found
     * @throws NullPointerException if the argument list is {@code null}
     */
    public final OptionSet parse(final String... args) {
        return parser.parse(args);
    }

    /**
     * Copied from {@link OptionParser#printHelpOn(java.io.OutputStream)}
     * Writes information about the options this parser recognizes to the given output sink.
     * <p/>
     * The output sink is flushed, but not closed.
     *
     * @param sink the sink to write information to
     * @throws java.io.IOException  if there is a problem writing to the sink
     * @throws NullPointerException if {@code sink} is {@code null}
     * @see OptionParser#printHelpOn(java.io.Writer)
     */
    public final void printHelpOn(final OutputStream sink) throws IOException {
        parser.printHelpOn(sink);
    }

    /**
     * Creëer de parser.
     *
     * @return de gecreëerde parse
     */
    private OptionParser createParser() {
        final OptionParser optionParser = new OptionParser();

        optionParser.acceptsAll(Arrays.asList(ContextParameterNames.SCENARIO.getName(), "s"), "Het scenario dat wordt uitgevoerd").withRequiredArg()
                .ofType(String.class).describedAs("name").required();

        optionParser.acceptsAll(Arrays.asList(ContextParameterNames.PERSOON_ID_LIJST.getName(), "p"),
                "Identifiers van specifieke personen").withRequiredArg().ofType(String.class)
                .describedAs(ARG);

        optionParser.acceptsAll(Arrays.asList(ContextParameterNames.AANTAL_IDS_PER_BATCH.getName(), "a"),
                "Aantal ID's per batch (bij alle personen)").withRequiredArg().ofType(String.class)
                .describedAs(ARG);

        optionParser.acceptsAll(Arrays.asList(ContextParameterNames.TIJD_TUSSEN_BATCHES.getName(), "t"),
                "Wachttijd in secondes tussen de batches (bij alle personen)").withRequiredArg().ofType(String.class)
                .describedAs(ARG);

        optionParser.acceptsAll(Arrays.asList(ContextParameterNames.AANTAL_UREN_BIJGEHOUDEN_PERSONEN.getName(), "u"),
                "Het aantal uren in het verleden waarvoor bijgehouden personen dienen te worden geserialiseerd. (bij "
                        + "bijgehouden personen)")
                .withRequiredArg().ofType(String.class);

        optionParser.acceptsAll(Arrays.asList(ContextParameterNames.VANAF_PERSOON_ID.getName(), "v"),
                "Het persoon id vanaf waar we beginnen (bij alle personen)").withRequiredArg().ofType(String.class)
                .describedAs(ARG);

        optionParser.acceptsAll(Arrays.asList("help", "?"), "Toon help informatie");

        return optionParser;
    }
}
