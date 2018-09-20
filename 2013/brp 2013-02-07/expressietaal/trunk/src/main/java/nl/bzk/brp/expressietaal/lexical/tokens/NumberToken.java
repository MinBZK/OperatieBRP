/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.lexical.tokens;

import nl.bzk.brp.expressietaal.symbols.Keywords;


/**
 * Tokens die overeenstemmen met een numerieke waarde (integer).
 */
public class NumberToken extends AbstractToken {

    private final int value;

    /**
     * Constructor.
     *
     * @param position Startpositie van het token in de string.
     * @param value    Numerieke waarde van het token.
     */
    public NumberToken(final int position, final int value) {
        super(TokenType.NUMBER_LITERAL, TokenSubtype.NONE, position);
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        return String.valueOf(value);
    }

    @Override
    public Integer getValueAsInteger() {
        return value;
    }

    @Override
    public Keywords getValueAsKeyword() {
        return null;
    }
}
