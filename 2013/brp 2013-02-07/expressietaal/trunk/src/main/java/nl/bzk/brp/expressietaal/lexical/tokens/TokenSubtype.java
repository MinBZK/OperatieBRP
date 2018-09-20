/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.lexical.tokens;

/**
 * Opsomming van subtypes van tokens, in aanvulling op TokenType.
 */
public enum TokenSubtype {
    /**
     * Subtype NONE.
     */
    NONE("none"),
    /**
     * Subtype operator <.
     */
    LESS("<"),
    /**
     * Subtype operator >.
     */
    GREATER(">"),
    /**
     * Subtype operator <=.
     */
    LESS_OR_EQUAL("<="),
    /**
     * Subtype operator >=.
     */
    GREATER_OR_EQUAL(">="),
    /**
     * Subtype operator =.
     */
    EQUAL("="),
    /**
     * Subtype operator <>.
     */
    NOT_EQUAL("<>"),
    /**
     * Subtype haakje (.
     */
    LEFT_BRACKET("("),
    /**
     * Subtype haakje ).
     */
    RIGHT_BRACKET(")"),
    /**
     * Subtype haakje [.
     */
    LIST_START("["),
    /**
     * Subtype haakje ].
     */
    LIST_END("]"),
    /**
     * Subtype operator +.
     */
    PLUS("+"),
    /**
     * Subtype operator -.
     */
    MINUS("-"),
    /**
     * Subtype operator *.
     */
    MULTIPLY("*"),
    /**
     * Subtype operator /.
     */
    DIVIDE("/"),
    /**
     * Subtype markering datum literal.
     */
    DATE_MARKER("#"),
    /**
     * Begin index.
     */
    INDEX_START("["),
    /**
     * Eind index.
     */
    INDEX_EIND("]");

    private final String tokenSubtypeNaam;

    /**
     * Constructor.
     *
     * @param tokenSubtypeNaam Naam van het token-subtype.
     */
    private TokenSubtype(final String tokenSubtypeNaam) {
        this.tokenSubtypeNaam = tokenSubtypeNaam;
    }

    String getNaam() {
        return tokenSubtypeNaam;
    }

    @Override
    public String toString() {
        return getNaam();
    }
}
