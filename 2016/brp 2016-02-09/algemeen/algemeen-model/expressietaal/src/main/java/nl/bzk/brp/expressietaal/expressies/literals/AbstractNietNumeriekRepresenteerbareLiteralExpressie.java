/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.literals;

/**
 * Representeert literal (constante) expressies die niet numeriek representeerbaar zijn. Deze hebben dan ook een
 * standaard implementatie voor de {@link #alsInteger()} en {@link #alsLong()} methodes.
 */
abstract class AbstractNietNumeriekRepresenteerbareLiteralExpressie extends AbstractLiteralExpressie {

    @Override
    public final int alsInteger() {
        // Default integer-waarde voor een expressie is 0.
        return 0;
    }

    @Override
    public final long alsLong() {
        // Default long-waarde voor een expressie is 0.
        return 0L;
    }
}
