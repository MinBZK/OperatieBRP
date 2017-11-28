/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.ExpressieLogger;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.ExpressieTaalConstanten;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalLexer;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.atn.PredictionMode;

/**
 * Facace voor het parsen van de BRP expressie.
 */
public final class ExpressieParser {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private ExpressieParser() {
    }

    /**
     * Probeert de tekstuele expressie te vertalen naar een expressie. Bij succes is de overeenkomstige expressie te vinden in het ParserResultaat; anders zijn
     * daar foutmeldingen te vinden en is expressie null.
     * @param expressieString De te vertalen expressie.
     * @return ParserResultaat met de expressie of, indien die NULL is, de gevonden fouten.
     * @throws ExpressieException als de expressie niet geparsed kan worden
     */
    public static Expressie parse(final String expressieString) throws ExpressieException {
        final Context context = new Context();
        // Declareer het standaard object 'persoon'.
        context.declareer(ExpressieTaalConstanten.CONTEXT_PERSOON, ExpressieType.BRP_METAOBJECT);
        context.declareer(ExpressieTaalConstanten.CONTEXT_PERSOON_OUD, ExpressieType.BRP_METAOBJECT);
        context.declareer(ExpressieTaalConstanten.CONTEXT_PERSOON_NIEUW, ExpressieType.BRP_METAOBJECT);
        return safeParse(expressieString, context);
    }

    /**
     * Probeert de tekstuele expressie te vertalen naar een expressie, gegeven een aantal gedefinieerde identifiers. Bij succes is de overeenkomstige expressie
     * te vinden in het ParserResultaat; anders zijn daar foutmeldingen te vinden en is expressie null.
     * @param expressieString De te vertalen expressie.
     * @param context Gedefinieerde identifiers.
     * @return ParserResultaat met de expressie of, indien die NULL is, de gevonden fouten.
     * @throws ExpressieException als de expressie niet geparsed kan worden
     */
    private static Expressie safeParse(final String expressieString, final Context context) throws ExpressieException {
        if (ExpressieLogger.IS_DEBUG_ENABLED) {
            LOGGER.debug("Parse expressie: '{}'", expressieString);
        }
        try {
            return doParse(expressieString, context);
        } catch (final ExpressieRuntimeException | StringIndexOutOfBoundsException e) {
            LOGGER.info("Expressie foutmelding = {}", e.getMessage());
            throw new ExpressieException("Parsefout: " + e.getMessage(), e);
        }
    }

    private static Expressie doParse(final String expressieString, final Context context) {
        // TEAMBRP-2535 de expressie syntax kan niet goed omgaan met een string waarachter ongedefinieerde velden staan.
        // Door haakjes toe te voegen zal dan een fout gesignaleerd worden, aangezien de content dan niet meer precies
        // gematched kan worden.
        final String expressieStringMetHaakjes = String.format("(%s)", expressieString);

        // Parsing gebeurt met een door ANTLR gegenereerde visitor. Om die te kunnen gebruiken, moet een treintje
        // opgetuigd worden (String->CharStream->Lexer->TokenStream).
        final CharStream cs = CharStreams.fromString(expressieStringMetHaakjes);
        final ParserErrorListener parserErrorListener = new ParserErrorListener();
        final BRPExpressietaalLexer lexer = new BRPExpressietaalLexer(cs);
        // verwijdert de interne listener van de lexer die naar system/out/err wegschrijft
        // expressies als ***bla*** logt bijvoorbeeld unrecognized token errors naar system/out
        // in plaats hiervan neem een eigen error listener op.
        lexer.removeErrorListeners();
        lexer.addErrorListener(parserErrorListener);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);

        final BRPExpressietaalParser parser = new BRPExpressietaalParser(tokens);
        parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);

        // Verwijder bestaande (default) error listeners en voeg de eigen error listener toe.
        parser.removeErrorListeners();
        parser.addErrorListener(parserErrorListener);

        // Maak de parse tree. Hier gebeurt het feitelijke parsing.
        final BRPExpressietaalParser.Brp_expressieContext tree = parser.brp_expressie();

        // Maak een visitor voor parsing.
        final ExpressieVisitor visitor = new ExpressieVisitor(context);
        // De visitor zet een parse tree om in een Expressie. Tenzij er een fout optreedt.
        return visitor.visit(tree);
    }

}
