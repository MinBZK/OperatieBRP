/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.lexical.tokens;

import nl.bzk.brp.expressietaal.parser.ParserFoutCode;
import nl.bzk.brp.expressietaal.symbols.Keywords;


/**
 * Geordende collectie van tokens met een cursor, die het volgende te verwerken token aangeeft. TokenStack biedt
 * methoden om o.a. het bereiken van het einde van de collectie te testen, de cursor te verschuiven en controles uit
 * te voeren op het token dat wordt aangegeven door de cursor.
 */
public interface TokenStack {

    /**
     * Bepaalt of het einde van de token stack is bereikt.
     *
     * @return True als het einde van de stack is bereikt; anders false.
     */
    boolean finished();

    /**
     * Geeft aan of de lexicale analyse succesvol is verlopen.
     *
     * @return True als de lexicale analyse succesvol is verlopen.
     */
    boolean succes();

    /**
     * Geeft de gevonden fout terug.
     *
     * @return De gevonden fout.
     */
    ParserFoutCode getFout();

    /**
     * Geeft de positie van de gevonden fout terug.
     *
     * @return Positie van de gevonden fout.
     */
    int getFoutPositie();

    /**
     * Retourneert het totaal aantal tokens op de stack.
     *
     * @return Aantal tokens op de stack.
     */
    int size();

    /**
     * Geef het huidige token aangewezen door cursor.
     *
     * @return Huidige token.
     */
    Token currentToken();

    /**
     * Geef het huidige token aangewezen door cursor en schuif de cursor een token verder.
     *
     * @return Huidige token.
     */
    Token shift();

    /**
     * Controleer of het huidige token van het opgegeven type is.
     *
     * @param tokenType Te controleren tokentype.
     * @param shift     Als TRUE dan wordt de cursor een teken opgeschoven bij een positieve match (shift).
     * @return True als het huidige token van het opgegeven type is.
     */
    boolean matchNextToken(final TokenType tokenType, final boolean shift);

    /**
     * Controleer of het huidige token van het opgegeven type en subtype is.
     *
     * @param tokenType    Te controleren tokentype.
     * @param tokenSubtype Te controleren tokensubtype.
     * @param shift        Als TRUE dan wordt de cursor een teken opgeschoven bij een positieve match (shift).
     * @return True als het token van het opgegeven type en subtype is.
     */
    boolean matchNextToken(final TokenType tokenType, final TokenSubtype tokenSubtype, final boolean shift);

    /**
     * Controleer of het huidige token een gelijkheidsoperator (gelijk-aan, niet-gelijk-aan) is.
     *
     * @param shift Als TRUE dan wordt de cursor een teken opgeschoven bij een positieve match (shift).
     * @return True als huidige token een gelijkheidsoperator is.
     */
    boolean matchEqualityOperator(final boolean shift);

    /**
     * Controleer of het huidige token een optel- of aftrekoperator is.
     *
     * @param shift Als TRUE dan wordt de cursor een teken opgeschoven bij een positieve match (shift).
     * @return True als huidige token een optel- of aftrekoperator is.
     */
    boolean matchAdditiveOperator(final boolean shift);

    /**
     * Controleer of het huidige token een vermenigvuldig- of deeloperator is.
     *
     * @param shift Als TRUE dan wordt de cursor een teken opgeschoven bij een positieve match (shift).
     * @return True als huidige token een vermenigvuldig- of deeloperator is.
     */
    boolean matchMultiplicativeOperator(final boolean shift);

    /**
     * Controleer of het huidige token een vergelijkingsoperator is (kleiner, kleiner-of gelijk, groter,
     * groter-of-gelijk, element-van-lijst).
     *
     * @param shift Als TRUE dan wordt de cursor een teken opgeschoven bij een positieve match (shift).
     * @return True als huidige token een vergelijkingsoperator is.
     */
    boolean matchRelationalOperator(final boolean shift);

    /**
     * Controleer of het huidige token een bepaald keyword is.
     *
     * @param keyword Te controleren keyword.
     * @param shift   Als TRUE dan wordt de cursor een teken opgeschoven bij een positieve match (shift).
     * @return True als huidige token het opgegeven keyword is.
     */
    boolean matchKeyword(final Keywords keyword, final boolean shift);
}
