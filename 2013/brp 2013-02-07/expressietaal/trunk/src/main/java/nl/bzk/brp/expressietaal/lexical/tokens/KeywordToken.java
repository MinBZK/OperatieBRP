/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.lexical.tokens;

import nl.bzk.brp.expressietaal.symbols.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.symbols.Keywords;

/**
 * Tokens die overeenstemmen met een keyword.
 */
public class KeywordToken extends AbstractToken {

    private final Keywords keyword;

    /**
     * Constructor.
     *
     * @param position Startpositie van het token in de string.
     * @param keyword  Keyword dat correspondeert met het token.
     */
    public KeywordToken(final int position, final Keywords keyword) {
        super(TokenType.KEYWORD, TokenSubtype.NONE, position);
        this.keyword = keyword;
    }

    @Override
    public String getValueAsString() {
        return DefaultKeywordMapping.getSyntax(keyword);
    }

    @Override
    public Integer getValueAsInteger() {
        return 0;
    }

    @Override
    public Keywords getValueAsKeyword() {
        return keyword;
    }
}
