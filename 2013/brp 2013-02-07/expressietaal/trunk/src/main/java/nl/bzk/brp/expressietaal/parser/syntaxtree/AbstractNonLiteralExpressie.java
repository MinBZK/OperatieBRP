/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

/**
 * Representeert niet-literal (niet-constante) expressies.
 */
public abstract class AbstractNonLiteralExpressie extends AbstractExpressie {
    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isConstanteWaarde() {
        return false;
    }

    @Override
    public final boolean isRootObject() {
        return false;
    }
}
