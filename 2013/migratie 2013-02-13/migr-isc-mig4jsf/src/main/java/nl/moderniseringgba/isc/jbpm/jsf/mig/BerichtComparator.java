/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.jsf.mig;

import java.util.Comparator;

public class BerichtComparator implements Comparator<Bericht> {

    private final int ascending;

    public BerichtComparator() {
        this(true);
    }

    public BerichtComparator(final boolean ascending) {
        if (ascending) {
            this.ascending = -1;
        } else {
            this.ascending = 1;
        }
    }

    @Override
    public int compare(final Bericht o1, final Bericht o2) {

        final int result = ascending * o1.getTijdstip().compareTo(o2.getTijdstip());

        if (result == 0) {
            o1.getId().compareTo(o2.getId());
        }

        return result;
    }

}
