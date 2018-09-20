/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.lexical.tokens;

import nl.bzk.brp.expressietaal.symbols.Keywords;


/**
 * Representatie van het einde van de (bron)regel.
 */
public class EndOfLineToken extends AbstractToken {

    /**
     * Constructor.
     *
     * @param position Startpositie van het token in de string.
     */
    public EndOfLineToken(final int position) {
        super(TokenType.END_OF_LINE, TokenSubtype.NONE, position);
    }

    @Override
    public String getValueAsString() {
        return "";
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
