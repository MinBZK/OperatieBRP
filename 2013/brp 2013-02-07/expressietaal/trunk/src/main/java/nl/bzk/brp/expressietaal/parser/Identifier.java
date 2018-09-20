/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;

/**
 * Representeert een identifier in een expressie. Een identifier heeft een syntax (string) en een type.
 */
public class Identifier {

    private final String syntax;
    private final ExpressieType type;

    /**
     * Constructor.
     *
     * @param syntax Syntax van de identifier.
     * @param type   Type van de identifier.
     */
    public Identifier(final String syntax, final ExpressieType type) {
        this.syntax = syntax;
        this.type = type;
    }

    public String getSyntax() {
        return syntax;
    }

    public ExpressieType getType() {
        return type;
    }
}
