/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieTaalConstanten;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.FoutExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.parser.antlr.BRPExpressietaalLexer;
import nl.bzk.brp.expressietaal.parser.antlr.BRPExpressietaalParser;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Persoon;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.atn.PredictionMode;

/**
 * Utility class voor het parsen en evalueren van BRP-expressies.
 */
public final class BRPExpressies {

    /**
     * Private constructor voor utility class.
     */
    private BRPExpressies() {
    }

    /**
     * Probeert de tekstuele expressie te vertalen naar een expressie, gegeven een aantal gedefinieerde identifiers. Bij succes is de overeenkomstige
     * expressie te vinden in het ParserResultaat; anders zijn daar foutmeldingen te vinden en is expressie null.
     *
     * @param expressieString De te vertalen expressie.
     * @param context         Gedefinieerde identifiers.
     * @return ParserResultaat met de expressie of, indien die NULL is, de gevonden fouten.
     */
    public static ParserResultaat parse(final String expressieString, final Context context) {
        final ParserResultaat result;
        // TEAMBRP-2535 de expressie syntax kan niet goed omgaan met een string waarachter ongedefinieerde velden staan.
        // Door haakjes toe te voegen zal dan een fout gesignaleerd worden, aangezien de content dan niet meer precies
        // gematched kan worden.
        final String expressieStringMetHaakjes = String.format("(%s)", expressieString);

        // Parsing gebeurt met een door ANTLR gegenereerde visitor. Om die te kunnen gebruiken, moet een treintje
        // opgetuigd worden (String->CharStream->Lexer->TokenStream).
        final CharStream cs = new ANTLRInputStream(expressieStringMetHaakjes);
        final BRPExpressietaalLexer lexer = new BRPExpressietaalLexer(cs);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);

        final BRPExpressietaalParser parser = new BRPExpressietaalParser(tokens);
        parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);
        Context initialContext;
        if (context != null) {
            initialContext = new Context(context);
        } else {
            initialContext = new Context();
        }
        // Declareer het standaard object 'persoon'.
        initialContext.declareer(ExpressieTaalConstanten.DEFAULT_OBJECT, ExpressieType.PERSOON);
        // Verwijder bestaande (default) error listeners en voeg de eigen error listener toe.
        parser.removeErrorListeners();
        final BRPExpressietaalErrorListener errorListener = new BRPExpressietaalErrorListener();
        parser.addErrorListener(errorListener);

        // Maak de parse tree. Hier gebeurt het feitelijke parsing.
        final BRPExpressietaalParser.Brp_expressieContext tree = parser.brp_expressie();

        List<ParserFout> fouten = errorListener.getFouten();
        if (!fouten.isEmpty()) {
            result = new ParserResultaat(fouten.get(0));
        } else {
            // Maak een visitor voor parsing.
            final BRPExpressieVisitor visitor = new BRPExpressieVisitor(initialContext, errorListener);
            // De visitor zet een parse tree om in een Expressie. Tenzij er een fout optreedt.
            final Expressie expressie = visitor.visit(tree);
            fouten = errorListener.getFouten();
            if (!fouten.isEmpty()) {
                result = new ParserResultaat(fouten.get(0));
            } else {
                result = new ParserResultaat(expressie);
            }
        }
        return result;
    }

    /**
     * Probeert de tekstuele expressie te vertalen naar een expressie. Bij succes is de overeenkomstige expressie te vinden in het ParserResultaat; anders
     * zijn daar foutmeldingen te vinden en is expressie null.
     *
     * @param expressieString De te vertalen expressie.
     * @return ParserResultaat met de expressie of, indien die NULL is, de gevonden fouten.
     */
    public static ParserResultaat parse(final String expressieString) {
        return parse(expressieString, new Context());
    }

    /**
     * Evalueert de expressie voor de gegeven persoon.
     *
     * @param expressie De te evalueren expressie.
     * @param context   Gedefinieerde symbolische namen met hun waarden.
     * @return Resultaat van de evaluatie.
     */
    public static Expressie evalueer(final Expressie expressie, final Context context) {
        Expressie result = expressie.evalueer(context);
        if (result == null) {
            result = NullValue.getInstance();
        }
        return result;
    }

    /**
     * Evalueert de expressie voor de gegeven persoon.
     *
     * @param expressie De te evalueren expressie.
     * @param persoon   De persoon (zonder historie).
     * @return Resultaat van de evaluatie.
     */
    public static Expressie evalueer(final Expressie expressie, final Persoon persoon) {
        final Context context = new Context();
        context.definieer(ExpressieTaalConstanten.DEFAULT_OBJECT, new BrpObjectExpressie(persoon, ExpressieType.PERSOON));
        return evalueer(expressie, context);
    }

    /**
     * Evalueert de expressie voor de gegeven persoon.
     *
     * @param expressie De te evalueren expressie.
     * @param persoon   De persoon (met historie).
     * @return Resultaat van de evaluatie.
     */
    public static Expressie evalueer(final Expressie expressie, final PersoonHisVolledig persoon) {
        final Context context = new Context();
        context.definieer(ExpressieTaalConstanten.DEFAULT_OBJECT, new BrpObjectExpressie(persoon, ExpressieType.PERSOON));
        return evalueer(expressie, context);
    }

    /**
     * Vertaalt en evalueert de expressie voor de gegeven persoon.
     *
     * @param expressieString De te evalueren expressie.
     * @param persoon         De persoon (zonder historie).
     * @return Resultaat van de evaluatie.
     */
    public static Expressie evalueer(final String expressieString, final Persoon persoon) {
        final ParserResultaat resultaat = parse(expressieString);
        final Expressie expressie;
        if (resultaat.getExpressie() != null) {
            expressie = evalueer(resultaat.getExpressie(), persoon);
        } else {
            expressie = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE, expressieString);
        }
        return expressie;
    }


    /**
     * Vertaalt en evalueert de expressie voor de gegeven persoon.
     *
     * @param expressieString De te evalueren expressie.
     * @param persoon         De persoon (met historie).
     * @return Resultaat van de evaluatie.
     */
    public static Expressie evalueer(final String expressieString, final PersoonHisVolledig persoon) {
        final ParserResultaat resultaat = parse(expressieString);
        final Expressie expressie;
        if (resultaat.getExpressie() != null) {
            expressie = evalueer(resultaat.getExpressie(), persoon);
        } else {
            expressie = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE, expressieString);
        }
        return expressie;
    }
}
