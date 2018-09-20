/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service.impl;

import java.io.Serializable;
import java.util.Comparator;

import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;

/**
 * De comparator voor HisPersoonAfgeleidAdministratiefModel op basis van de tijdstip laatste wijziging.
 */
public class HisPersoonAfgeleidAdministratiefModelComparator implements Comparator<HisPersoonAfgeleidAdministratiefModel>, Serializable {

    @Override
    public final int compare(final HisPersoonAfgeleidAdministratiefModel hisAfgAdm1, final HisPersoonAfgeleidAdministratiefModel hisAfgAdm2) {
        final int resultaat;
        if (hisAfgAdm1.getID().equals(hisAfgAdm2.getID())) {
            resultaat = 0;
        } else if (hisAfgAdm1.getTijdstipLaatsteWijziging().voor(hisAfgAdm2.getTijdstipLaatsteWijziging())) {
            resultaat = -1;
        } else if (hisAfgAdm1.getTijdstipLaatsteWijziging().na(hisAfgAdm2.getTijdstipLaatsteWijziging())) {
            resultaat = 1;
        } else {
            // TijdstipLaatsteWijziging is gelijk
            resultaat = hisAfgAdm1.getID().compareTo(hisAfgAdm2.getID());
        }
        return resultaat;
    }
}
