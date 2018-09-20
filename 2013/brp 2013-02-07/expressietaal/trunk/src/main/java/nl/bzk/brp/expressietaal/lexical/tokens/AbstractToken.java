/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.lexical.tokens;

/**
 * Representeert tokens die een lexical analyzer uit een (bron)string afleidt en die door de parser worden ingelezen.
 * Implementatie van interface Token.
 */
public abstract class AbstractToken implements Token {

    private final TokenType type;
    private final TokenSubtype subtype;
    private final int position;

    /**
     * Constructor.
     *
     * @param type     Type van het token.
     * @param subtype  Subtype van het token.
     * @param position Startpositie van het token in de string.
     */
    AbstractToken(final TokenType type, final TokenSubtype subtype, final int position) {
        this.type = type;
        this.subtype = subtype;
        this.position = position;
    }

    @Override
    public final TokenType getTokenType() {
        return type;
    }

    @Override
    public final TokenSubtype getTokenSubtype() {
        return subtype;
    }

    @Override
    public final int getPosition() {
        return position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        return getTokenType().hashCode() + getValueAsString().hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object o) {
        if (o instanceof Token) {
            Token t = (Token) o;
            return getTokenType().equals(t.getTokenType()) && this.getValueAsString().equals(t.getValueAsString());
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        if (getTokenSubtype() == TokenSubtype.NONE) {
            return String.format("%s[%s]@%d", getTokenType(), getValueAsString(), getPosition());
        } else {
            return String.format("%s.%s@%d", getTokenType(), getTokenSubtype(), getPosition());
        }
    }
}
