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
 * Deze klasse wordt gebruikt om de betrokkenheden te sorteren zoals ze in de xsd <sequence> staan.
 */
@Regels(Regel.VR00092)
public final class BetrokkenheidComparator implements Comparator<BetrokkenheidHisVolledig>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Sorteert de betrokkenheden op alfabetische volgorde en daarbinnen op (database) ID van de persoon, daarna op databas ID van de betrokkenheid,
     * conform xsd.
     *
     * @param o1 De eerste BetrokkenheidHisVolledig.
     * @param o2 De tweede BetrokkenheidHisVolledig.
     * @return De string compare van de rol code als int.
     */
    @Override
    public int compare(final BetrokkenheidHisVolledig o1, final BetrokkenheidHisVolledig o2) {
        int vergelijking = o1.getRol().getWaarde().getCode().compareTo(o2.getRol().getWaarde().getCode());
        final boolean beidePersonenZijnNietNull = o1.getPersoon() != null && o2.getPersoon() != null;
        final boolean beidePersoonIdsZijnNietNull = beidePersonenZijnNietNull && o1.getPersoon().getID() != null && o2.getPersoon().getID() != null;
        if (vergelijking == 0 && beidePersoonIdsZijnNietNull) {
            vergelijking = o1.getPersoon().getID().compareTo(o2.getPersoon().getID());
        }
        if (vergelijking == 0) {
            vergelijking = o1.getID().compareTo(o2.getID());
        }
        return vergelijking;
    }
}
