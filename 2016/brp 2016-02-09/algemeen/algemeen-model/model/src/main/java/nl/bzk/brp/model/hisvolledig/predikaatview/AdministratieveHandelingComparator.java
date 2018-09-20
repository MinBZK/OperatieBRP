/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.AbstractIdComparator;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;

/**
 * Comparator voor administratieve handelingen.
 */
@Regels(Regel.VR00090)
public final class AdministratieveHandelingComparator extends AbstractIdComparator implements Comparator<AdministratieveHandelingHisVolledig>, Serializable {

    @Override
    public int compare(final AdministratieveHandelingHisVolledig ah1, final AdministratieveHandelingHisVolledig ah2) {
        if (ah1 == null || ah2 == null) {
            throw new IllegalArgumentException("Missend object waardoor deze niet te vergelijken is.");
        }

        int resultaat;
        if (ah1.getTijdstipRegistratie() == ah2.getTijdstipRegistratie()) {
            resultaat = 0;
        } else if (ah1.getTijdstipRegistratie() == null) {
            resultaat = 1;
        } else if (ah2.getTijdstipRegistratie() == null) {
            resultaat = -1;
        } else {
            resultaat = ah1.getTijdstipRegistratie().compareTo(ah2.getTijdstipRegistratie()) * -1;
        }
        if (resultaat == 0) {
            resultaat = vergelijkModelIdentificeerbaar(ah1, ah2);
        }
        return resultaat;
    }
}
