/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;


/**
 * Comparator die indicaties sorteert. Volgorde in de xsd is volgorde in BMR is ordinal in enum.
 */
@Regels(Regel.VR00092)
public class PersoonIndicatieComparator implements Comparator<PersoonIndicatieHisVolledig>, Serializable {

    @Override
    public int compare(final PersoonIndicatieHisVolledig o1, final PersoonIndicatieHisVolledig o2) {
        return o1.getSoort().getWaarde().ordinal() - o2.getSoort().getWaarde().ordinal();
    }
}
