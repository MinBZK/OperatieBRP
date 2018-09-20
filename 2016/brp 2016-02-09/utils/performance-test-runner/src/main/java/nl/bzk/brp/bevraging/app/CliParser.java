/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.app;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.io.OutputStream;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * Wrapper om een instantie van {@link OptionParser} die de commandline opties kent.
 */
public class CliParser {
    private static final int DEFAULT_THREADS = 5;
    private static final int DEFAULT_BSNS = 1000;

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
     * @throws joptsimple.OptionException if problems are detected while parsing
     * @throws NullPointerException       if the argument list is {@code null}
     */
    public OptionSet parse(final String... args) {
        return parser.parse(args);
    }

    /**
     * Copied from {@link OptionParser#printHelpOn(java.io.OutputStream)}
     * Writes information about the options this parser recognizes to the given output sink.
     * <p/>
     * The output sink is flushed, but not closed.
     *
     * @param sink the sink to write information to
     * @throws IOException          if there is a problem writing to the sink
     * @throws NullPointerException if {@code sink} is {@code null}
     * @see OptionParser#printHelpOn(java.io.Writer)
     */
    public void printHelpOn(final OutputStream sink) throws IOException {
        parser.printHelpOn(sink);
    }

    /**
     * Creëer de parser.
     *
     * @return de gecreëerde parse
     */
    private OptionParser createParser() {
        OptionParser optionParser = new OptionParser();
        //optionParser.recognizeAlternativeLongOptions( true );

        optionParser.acceptsAll(asList("threads", "T"), "Aantal Thread").withRequiredArg().ofType(
                Integer.class).defaultsTo(DEFAULT_THREADS).describedAs("count");

        optionParser.acceptsAll(asList("bsn", "B"), "Aantal BSNs dat wordt opgehaald").withRequiredArg().ofType(
                Integer.class).defaultsTo(DEFAULT_BSNS).describedAs("amount");

        optionParser.acceptsAll(asList("id", "I"), "Persoon ID").withRequiredArg().ofType(Integer.class).describedAs("persoon id");

        optionParser.acceptsAll(asList("scenario", "S"), "Het scenario dat wordt uitgevoerd").withRequiredArg()
                .ofType(String.class)
                .describedAs("name").required();

        optionParser.accepts("dubbele", "Voeg een dubbele BSN in per elke x (groter dan 2)").withRequiredArg()
                .ofType(Integer.class).describedAs("count").defaultsTo(0);

        optionParser.acceptsAll(asList("message", "m"), "Commentaar in het rapport").withRequiredArg()
                .ofType(String.class).describedAs("ARG");
        optionParser.acceptsAll(asList("file", "F"), "Naam voor het rapport bestand").withRequiredArg()
                .ofType(String.class).describedAs("ARG");

        optionParser.acceptsAll(asList("help", "?"), "Display help information");

        optionParser.acceptsAll(asList("afnemers", "A"), "Aantal te gebruiken afnemers").withRequiredArg().
                ofType(Integer.class).describedAs("aantalAfnemers");

        return optionParser;
    }
}
