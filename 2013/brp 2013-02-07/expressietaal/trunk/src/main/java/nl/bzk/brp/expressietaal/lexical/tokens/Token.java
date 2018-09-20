/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.lexical.tokens;

import nl.bzk.brp.expressietaal.symbols.Keywords;


/**
 * Representeert tokens die een lexical analyzer uit een string afleidt en die door de parser worden ingelezen.
 */
public interface Token {

    /**
     * Geeft het type van het token.
     *
     * @return Type van het token.
     */
    TokenType getTokenType();

    /**
     * Geeft het subtype van het token, indien relevant; anders TokenSubtype.NONE.
     *
     * @return Subtype van het token.
     */
    TokenSubtype getTokenSubtype();

    /**
     * Geeft de positie waarop het token in de brontekst begint.
     *
     * @return Tekstpositie.
     */
    int getPosition();

    /**
     * Geeft de waarde van het token als string.
     *
     * @return Stringwaarde van het token.
     */
    String getValueAsString();

    /**
     * Geeft de waarde van het token als integer.
     *
     * @return Integerwaarde van het token.
     */
    Integer getValueAsInteger();

    /**
     * Geeft de waarde van het token als keyword.
     *
     * @return Keywordwaarde van het token.
     */
    Keywords getValueAsKeyword();
}
