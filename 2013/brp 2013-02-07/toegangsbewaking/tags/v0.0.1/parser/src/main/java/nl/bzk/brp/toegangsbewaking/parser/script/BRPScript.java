/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.script;

import java.util.List;

import nl.bzk.brp.toegangsbewaking.parser.Parser;
import nl.bzk.brp.toegangsbewaking.parser.grammar.DefaultGrammar;
import nl.bzk.brp.toegangsbewaking.parser.grammar.Grammar;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.AbstractTokenizer;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.DefaultTokenizer;


/**
 * Specifiek script voor BRP Expressies. Encapsulatie van tokenizer, parse tree, parser
 * en de tekstuele vorm van een expressie.
 *
 *
 * Voorbeeld expressie:
 * (Leeftijd >= 18) en ( (Provincie(Woonplaats) = 'Noord Holland') or (Woonplaats = 'Utrecht') )
 *
 * Logische unaire operator:
 * niet !
 *
 * Logische binaire operatoren:
 * en &
 * of |
 *
 * Binaire operatoren
 * gelijk =
 * gelijk_cs == Case sensitive
 * kleiner <
 * groter >
 * kleiner_gelijk <=
 * groter_gelijk >=
 * bevat %
 * start %-
 * einde -%
 * leeg #
 *
 * Overige operatoren
 * Custom functies.
 * Zie voorlopig even de BRPParser en daarbinnen de init functie!
 *
 * Identifiers:
 * Moeten beginnen met een letter. Karakters: a..z, A..Z, 0..9 en de punt en de underscore.
 *
 * Constanten:
 * Tekst: Omgeven door enkele quotes. Quote in de waarde dient herhaald te worden (character stuffing).
 *
 * Bijzondere karakters
 * Alleen #9 (tab), #10, #13 (carriage return en enter) en #32 (spatie) zijn geldig.
 *
 * Vragen:
 * ? Moet de SoundEx ook ?
 */
public class BRPScript extends AbstractScript {

    public BRPScript(final List<String> lines) {
        super(lines, new DefaultGrammar());
    }

    @Override
    protected Parser createParser(final Grammar grammar) {
        return new Parser(grammar);
    }

    @Override
    protected AbstractTokenizer createTokenizer(final List<String> lineList) {
        return new DefaultTokenizer(lineList);
    }

}
