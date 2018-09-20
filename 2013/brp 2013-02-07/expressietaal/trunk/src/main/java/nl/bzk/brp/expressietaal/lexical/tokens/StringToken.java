/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.lexical.tokens;

import nl.bzk.brp.expressietaal.symbols.Keywords;

/**
 * Tokens die gerepresenteerd worden door een string.
 */
public class StringToken extends AbstractToken {

    private final String value;

    /**
     * Constructor.
     *
     * @param type     Type van het token.
     * @param subtype  Subtype van het token.
     * @param position Startpositie van het token in de string.
     * @param value    Stringrepresentatie van het token.
     */
    public StringToken(final TokenType type, final TokenSubtype subtype, final int position, final String value) {
        super(type, subtype, position);
        this.value = value;
    }

    /**
     * Constructor.
     *
     * @param type     Type van het token.
     * @param position Startpositie van het token in de string.
     * @param value    Stringrepresentatie van het token.
     */
    public StringToken(final TokenType type, final int position, final String value) {
        this(type, TokenSubtype.NONE, position, value);
    }

    /**
     * Constructor.
     *
     * @param type     Type van het token.
     * @param subtype  Subtype van het token.
     * @param position Startpositie van het token in de string.
     * @param value    Character-representatie van het token.
     */
    public StringToken(final TokenType type, final TokenSubtype subtype, final int position, final char value) {
        this(type, subtype, position, String.valueOf(value));
    }

    /**
     * Constructor.
     *
     * @param type     Type van het token.
     * @param position Startpositie van het token in de string.
     * @param value    Character-representatie van het token.
     */
    public StringToken(final TokenType type, final int position, final char value) {
        this(type, TokenSubtype.NONE, position, String.valueOf(value));
    }

    @Override
    public String getValueAsString() {
        return value;
    }

    @Override
    public Integer getValueAsInteger() {
        return 0;
    }

    @Override
    public Keywords getValueAsKeyword() {
        return null;
    }
}
