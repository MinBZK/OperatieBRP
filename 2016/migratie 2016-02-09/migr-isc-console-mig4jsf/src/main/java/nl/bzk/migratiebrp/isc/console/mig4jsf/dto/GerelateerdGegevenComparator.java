/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.dto;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Berichten vergelijker voor volgorde in UI.
 */
public final class GerelateerdGegevenComparator implements Comparator<GerelateerdGegeven>, Serializable {

    private static final long serialVersionUID = 1L;

    private final int ascending;

    /**
     * Default contructor (aflopend).
     */
    public GerelateerdGegevenComparator() {
        this(true);
    }

    /**
     * Constructor.
     *
     * @param ascending
     *            indicatie sortering aflopend
     */
    public GerelateerdGegevenComparator(final boolean ascending) {
        if (ascending) {
            this.ascending = -1;
        } else {
            this.ascending = 1;
        }
    }

    @Override
    public int compare(final GerelateerdGegeven o1, final GerelateerdGegeven o2) {

        int result = ascending * o1.getSoort().compareTo(o2.getSoort());

        if (result == 0) {
            result = o1.getGegeven().compareTo(o2.getGegeven());
        }

        return result;
    }

}
