/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Ouder relatie sorteerder op basis van 8510.
 **/
public final class Comparator8510OuderRelaties implements Comparator<OuderRelatie>, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(final OuderRelatie o1, final OuderRelatie o2) {
        int waarde = 0;
        if (o1.bevatActueel()) {
            waarde = 1;
        }
        if (o2.bevatActueel()) {
            waarde = -1;
        }
        if (waarde != 0) {
            return waarde;
        }
        Integer datum1 = o1.getRecords().get(0).getHistorie().getIngangsdatumGeldigheid().getIntegerWaarde();
        Integer datum2 = o2.getRecords().get(0).getHistorie().getIngangsdatumGeldigheid().getIntegerWaarde();
        if (datum1 == null) {
            datum1 = 0;
        }
        if (datum2 == null) {
            datum2 = 0;
        }
        return datum1 - datum2;
    }
}
