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
 * Comparator om persoon nationaliteiten te sorteren, wordt gebruikt in bevraging om de nationaliteiten van een persoon in een vaste volgorde in het
 * response te tonen. De sortering gebeurt nu op basis van de nationaliteit code.
 */
@Regels(Regel.VR00092)
public class PersoonNationaliteitComparator implements Comparator<PersoonNationaliteitHisVolledig>, Serializable {

    @Override
    public int compare(final PersoonNationaliteitHisVolledig persNation1,
        final PersoonNationaliteitHisVolledig persNation2)
    {
        return persNation1.getNationaliteit().getWaarde().getCode().getWaarde()
            .compareTo(persNation2.getNationaliteit().getWaarde().getCode().getWaarde());
    }
}
