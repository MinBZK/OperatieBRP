/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.tokenizer;

/**
 * Type van een Token.
 */
public enum TokenType {

    /** Identifiers. */
    IDENTIFIER("Identifier"),

    /** Speciale karakters en symbolen (: = := etc). */
    SYMBOL("Symbol"),

    /** Commentaar. */
    COMMENT("Comment"),

    /** Text tussen enkele of dubbele quotes. */
    STRING("String"),

    /** Integer getallen. */
    INTEGER("Integer"),

    /** Floating point getallen. */
    FLOAT("Float"),

    /** Groeperings synbolen. */
    GROEPERING("Groepering");

    private final String name;

    private TokenType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
