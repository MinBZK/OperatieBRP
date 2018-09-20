/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

public class BronnenRepoTable {
    public Class<?> clazz;
    public long max;

    public BronnenRepoTable(final Class<?> clazz) {
        this.clazz = clazz;
    }

    public String toString() {
        return clazz.getName() + ", max=" + max;
    }

    public String getMaxQuery() {
        return "FROM " + clazz.getSimpleName() + " WHERE van <= :val AND tot_en_met >= :val";
    }

}
