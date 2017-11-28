/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;

/**
 * Comparator voor administratieve handelingen.
 */
@Bedrijfsregel(Regel.R1804)
final class AdministratievehandelingComparator implements Comparator<AdministratieveHandeling>, Serializable {

    /**
     * Een herbruikbare / threadsafe instantie van deze comparator.
     */
    public static final AdministratievehandelingComparator INSTANCE = new AdministratievehandelingComparator();

    private static final long serialVersionUID = 1L;

    private AdministratievehandelingComparator() {
    }

    @Override
    public int compare(final AdministratieveHandeling ah1, final AdministratieveHandeling ah2) {
        if (ah1 == null || ah2 == null) {
            throw new IllegalArgumentException("Missend object waardoor deze niet te vergelijken is.");
        }

        int resultaat;
        if (ah1.getTijdstipRegistratie() == null) {
            resultaat = 1;
        } else if (ah2.getTijdstipRegistratie() == null) {
            resultaat = -1;
        } else if (ah1.getTijdstipRegistratie().equals(ah2.getTijdstipRegistratie())) {
            resultaat = 0;
        } else {
            resultaat = ah1.getTijdstipRegistratie().isBefore(ah2.getTijdstipRegistratie()) ? 1 : -1;
        }
        if (resultaat == 0) {
            resultaat = Long.compare(ah1.getId(), ah2.getId());
        }
        return resultaat;
    }
}
