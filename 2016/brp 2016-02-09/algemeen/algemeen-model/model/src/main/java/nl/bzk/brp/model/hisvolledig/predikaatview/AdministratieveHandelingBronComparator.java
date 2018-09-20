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
import nl.bzk.brp.model.hisvolledig.kern.ActieBronHisVolledig;

/**
 * Comparator voor bronnen binnen een administratieve handeling.
 */
@Regels(Regel.VR00090)
public final class AdministratieveHandelingBronComparator extends AbstractIdComparator implements Comparator<ActieBronHisVolledig>, Serializable {

    @Override
    public int compare(final ActieBronHisVolledig bron1, final ActieBronHisVolledig bron2) {
        if (bron1 == null || bron2 == null) {
            throw new IllegalArgumentException("Missend object waardoor deze niet te vergelijken is.");
        }

        final int resultaat;

        if (bron1.getDocument() != null && bron2.getDocument() != null) {
            resultaat = vergelijkModelIdentificeerbaar(bron1.getDocument(), bron2.getDocument());
        } else if (bron1.getDocument() != null) {
            resultaat = -1;
        } else if (bron2.getDocument() != null) {
            resultaat = 1;
        } else if (bron1.getRechtsgrond() != null && bron2.getRechtsgrond() != null) {
            resultaat = vergelijkIds(bron1.getRechtsgrond().getWaarde().getID(), bron2.getRechtsgrond().getWaarde().getID());
        } else if (bron1.getRechtsgrond() != null) {
            resultaat = -1;
        } else if (bron2.getRechtsgrond() != null) {
            resultaat = 1;
        } else if (bron1.getRechtsgrondomschrijving() != null && bron2.getRechtsgrondomschrijving() != null) {
            resultaat = bron1.getRechtsgrondomschrijving().getWaarde().compareTo(bron2.getRechtsgrondomschrijving().getWaarde());
        } else {
            resultaat = 0;
        }

        return resultaat;
    }

}
