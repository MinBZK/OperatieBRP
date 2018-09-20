/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.lexical.tokens;

import nl.bzk.brp.expressietaal.symbols.Keywords;

/**
 * Tokens die overeenstemmen met een attribuut uit het gegevensmodel.
 */
public class AttributeToken extends AbstractToken {

    private final String value;
    //private final Attributes value;

    /**
     * Constructor.
     *
     * @param position  Startpositie van het token in de string.
     * @param attribute Attribute dat correspondeert met het token.
     */
    public AttributeToken(final int position, final String attribute) {
        super(TokenType.ATTRIBUTE, TokenSubtype.NONE, position);
        this.value = attribute;
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
