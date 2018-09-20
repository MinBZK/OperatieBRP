/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.symbols.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.symbols.Keywords;

/**
 * Representeert een boolean constante.
 */
public class BooleanLiteralExpressie extends AbstractLiteralExpressie {
    private final boolean value;

    /**
     * Constructor.
     *
     * @param value Waarde van het vaste-waardeobject.
     */
    public BooleanLiteralExpressie(final boolean value) {
        this.value = value;
    }

    public final boolean getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public final boolean isRootObject() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsLeesbareString() {
        if (value) {
            return DefaultKeywordMapping.getSyntax(Keywords.TRUE);
        } else {
            return DefaultKeywordMapping.getSyntax(Keywords.FALSE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsFormeleString() {
        return alsLeesbareString();
    }
}
