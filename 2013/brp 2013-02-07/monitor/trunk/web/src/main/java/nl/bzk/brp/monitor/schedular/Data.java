/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.schedular;

/**
 * Data.
 *
 * @param <U> Type van de unit
 * @param <W> Type van de waarde
 */
public class Data<U, W> {

    private final U unit;
    private final W[] waarde;

    /**
     * Constructor.
     *
     * @param unit Eenheden
     * @param waarde waarden
     */
    public Data(final U unit, final W... waarde) {
        this.unit = unit;
        this.waarde = waarde;
    }

    public U getUnit() {
        return unit;
    }

    public W[] getWaarde() {
        return waarde;
    }
}
