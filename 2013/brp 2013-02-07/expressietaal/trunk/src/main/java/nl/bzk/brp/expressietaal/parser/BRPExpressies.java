/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import nl.bzk.brp.expressietaal.lexical.LexicalAnalyzer;
import nl.bzk.brp.expressietaal.lexical.LexicalAnalyzerImpl;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Context;
import nl.bzk.brp.expressietaal.parser.syntaxtree.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.parser.syntaxtree.EvaluatieResultaat;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.symbols.AttributeSolver;
import nl.bzk.brp.expressietaal.symbols.DefaultSolver;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * Utility class voor het parsen van expressies. In deze class wordt de keuze gemaakt voor de concrete implementatie
 * van lexical analyzer en parser.
 */
public final class BRPExpressies {

    private static final LexicalAnalyzer LEXICAL_ANALYZER;
    private static final Parser PARSER;

    static {
        LEXICAL_ANALYZER = new LexicalAnalyzerImpl();
        PARSER = new ParserImpl();
    }

    /**
     * Private constructor voor utility class.
     */
    private BRPExpressies() {
    }

    /**
     * Probeert de tekstuele expressie te vertalen naar een expressie. Bij succes is de overeenkomstige expressie
     * te vinden in het ParserResultaat; anders zijn daar foutmeldingen te vinden en is expressie null.
     *
     * @param expressie De te vertalen expressie.
     * @return ParserResultaat met de expressie of, indien die NULL is, de gevonden fouten.
     */
    public static ParserResultaat parse(final String expressie) {
        return PARSER.parse(expressie, LEXICAL_ANALYZER);
    }

    /**
     * Evalueert de boolean expressie voor de gegeven persoon.
     *
     * @param expressie De te evalueren expressie.
     * @param persoon   De persoon.
     * @return Resultaat van de evaluatie.
     */
    public static EvaluatieResultaat evalueer(final Expressie expressie, final Persoon persoon) {
        AttributeSolver solver = new DefaultSolver();
        Context context = new Context(solver);
        context.put("persoon", persoon);
        return expressie.evalueer(context);
    }

    /**
     * Vertaalt en evalueert de (boolean) expressie voor de gegeven persoon.
     *
     * @param expressieString De te evalueren expressie.
     * @param persoon         De persoon.
     * @return Resultaat van de evaluatie.
     */
    public static EvaluatieResultaat evalueer(final String expressieString, final Persoon persoon) {
        ParserResultaat resultaat = parse(expressieString);
        Expressie expressie = resultaat.getExpressie();
        if (expressie != null) {
            return evalueer(expressie, persoon);
        } else {
            return new EvaluatieResultaat(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        }
    }
}
