/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.schedular.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Rij van tabel.
 *
 */
public class Row {

    private List<Value> c = new ArrayList<Value>();

    /**
     * Voeg rij toe.
     *
     * @param waarden een lijst van cell waarden
     */
    public void addWaarden(final Object... waarden) {
        for (Object waarde : waarden) {
            c.add(new Value(waarde));
        }
    }

    /**
     * Voeg rij toe met een array van Value objecten.
     *
     * @param waarden Value array
     */
    public void addWaarden(final Value... waarden) {
        for (Value waarde : waarden) {
            c.add(waarde);
        }
    }

    public List<Value> getC() {
        return c;
    }

    public void setC(final List<Value> c) {
        this.c = c;
    }
}
