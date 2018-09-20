/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import nl.bzk.brp.expressietaal.lexical.LexicalAnalyzer;
import nl.bzk.brp.expressietaal.lexical.tokens.TokenStack;


/**
 * Vertaalt een lijst tokens (TokenStack) naar een Expressie, een abstracte syntax tree van de tokens.
 */
public interface Parser {

    /**
     * Probeert de tekstuele expressie te vertalen naar een Expressie. Bij succes is de overeenkomstige Expressie
     * te vinden in het ParserResultaat; anders zijn daar foutmeldingen te vinden en is Expressie null.
     *
     * @param expressie       De te vertalen expressie.
     * @param lexicalAnalyzer De lexical analyzer die gebruikt moet worden om de string in tokens om te zetten.
     * @return Het resultaat van het parsen: ofwel een vertaalde Expressie, ofwel een lijst met foutmeldingen.
     */
    ParserResultaat parse(String expressie, LexicalAnalyzer lexicalAnalyzer);

    /**
     * Probeert de lijst met tokens te vertalen naar een Expressie. Bij succes is de overeenkomstige Expressie
     * te vinden in het ParserResultaat; anders zijn daar foutmeldingen te vinden en is Expressie null.
     *
     * @param tokenStack De stack met te parsen tokens.
     * @return Het resultaat van het parsen: ofwel een vertaalde Expressie, ofwel een lijst met foutmeldingen.
     */
    ParserResultaat parse(TokenStack tokenStack);
}
