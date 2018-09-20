/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import java.io.Serializable;
import java.util.Comparator;


/**
 * Comparator voor verantwoording, oftewel administratieve handelingen.
 */
public class VerantwoordingComparator implements Comparator<AdministratieveHandelingHisVolledig>, Serializable {

    @Override
    public int compare(final AdministratieveHandelingHisVolledig o1, final AdministratieveHandelingHisVolledig o2) {
        if (o1.getTijdstipRegistratie() != null) {
            return -1 * o1.getTijdstipRegistratie().compareTo(o2.getTijdstipRegistratie());
        }

        return -1;
    }
}
