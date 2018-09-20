/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.lexical.tokens;

/**
 * Opsomming van alle mogelijke tokentypes die de lexical analyzer herkent.
 */
public enum TokenType {
    /**
     * Tokentype identifier.
     */
    IDENTIFIER("identifier"),
    /**
     * Tokentype keyword.
     */
    KEYWORD("keyword"),
    /**
     * Tokentype attribute.
     */
    ATTRIBUTE("attribute"),
    /**
     * Tokentype string literal.
     */
    STRING_LITERAL("string_literal"),
    /**
     * Tokentype number literal.
     */
    NUMBER_LITERAL("number_literal"),
    /**
     * Tokentype operator.
     */
    OPERATOR("operator"),
    /**
     * Tokentype bracket.
     */
    BRACKET("bracket"),
    /**
     * Tokentype separator.
     */
    SEPARATOR("separator"),
    /**
     * Tokentype qualifier.
     */
    QUALIFIER("qualifier"),
    /**
     * Tokentype end of line.
     */
    END_OF_LINE("end_of_line");

    private final String tokenTypeNaam;

    /**
     * Constructor.
     *
     * @param tokenTypeNaam Naam van het tokentype.
     */
    private TokenType(final String tokenTypeNaam) {
        this.tokenTypeNaam = tokenTypeNaam;
    }

    String getNaam() {
        return tokenTypeNaam;
    }

    @Override
    public String toString() {
        return getNaam();
    }
}
