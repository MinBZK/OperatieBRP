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
import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledig;

/**
 * Comparator voor acties.
 */
@Regels(Regel.VR00090)
public final class ActieComparator extends AbstractIdComparator implements Comparator<ActieHisVolledig>, Serializable {

    @Override
    public int compare(final ActieHisVolledig a1, final ActieHisVolledig a2) {
        if (a1 == null || a2 == null) {
            throw new IllegalArgumentException("Missend object waardoor deze niet te vergelijken is.");
        }

        int resultaat;
        if (a1.getTijdstipRegistratie() == a2.getTijdstipRegistratie()) {
            resultaat = 0;
        } else if (a1.getTijdstipRegistratie() == null) {
            resultaat = 1;
        } else if (a2.getTijdstipRegistratie() == null) {
            resultaat = -1;
        } else {
            resultaat = a1.getTijdstipRegistratie().compareTo(a2.getTijdstipRegistratie()) * -1;
        }
        if (resultaat == 0) {
            resultaat = vergelijkModelIdentificeerbaar(a1, a2);
        }
        return resultaat;
    }

}
