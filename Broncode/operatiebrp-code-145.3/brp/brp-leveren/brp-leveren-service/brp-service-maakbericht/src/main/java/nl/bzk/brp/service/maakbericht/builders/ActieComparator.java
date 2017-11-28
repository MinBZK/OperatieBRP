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
import nl.bzk.brp.domain.leveringmodel.Actie;

/**
 * Comparator voor acties.
 */
@Bedrijfsregel(Regel.R1804)
final class ActieComparator implements Comparator<Actie>, Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Een herbruikbare / threadsafe instantie van de ActieComparator.
     */
    public static final ActieComparator INSTANCE = new ActieComparator();

    private ActieComparator() {

    }

    @Override
    public int compare(final Actie a1, final Actie a2) {
        if (a1 == null || a2 == null) {
            throw new IllegalArgumentException("Missend object waardoor deze niet te vergelijken is.");
        }

        int resultaat;
        if (a1.getTijdstipRegistratie() == null) {
            resultaat = 1;
        } else if (a2.getTijdstipRegistratie() == null) {
            resultaat = -1;
        } else if (a1.getTijdstipRegistratie().equals(a2.getTijdstipRegistratie())) {
            resultaat = 0;
        } else {
            resultaat = a1.getTijdstipRegistratie().isBefore(a2.getTijdstipRegistratie()) ? 1 : -1;
        }
        if (resultaat == 0) {
            resultaat = Long.compare(a1.getId(), a2.getId());
        }
        return resultaat;
    }

}
