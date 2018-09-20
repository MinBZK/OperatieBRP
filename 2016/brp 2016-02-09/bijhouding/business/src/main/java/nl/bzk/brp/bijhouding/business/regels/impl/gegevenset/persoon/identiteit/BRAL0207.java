/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identiteit;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;

/**
 * Check dat na een bijhouding alle niet ingeschrevenen maar maximaal 1 betrokkenheid hebben.
 *
 * @brp.bedrijfsregel BRAL0207
 */
@Named("BRAL0207")
public class BRAL0207 implements RegelInterface {

    @Override
    public final Regel getRegel() {
        return Regel.BRAL0207;
    }

    /**
     * Voer de regel uit.
     * @param bericht bericht
     * @param berichtNietIngeschrevenenMetMeerdereBetrokkenheden berichtNietIngeschrevenenMetMeerdereBetrokkenheden
     * @return de lijst met overtreders
     */
    public List<BerichtIdentificeerbaar> voerRegelUit(final BijhoudingsBericht bericht,
        final List<BerichtEntiteit> berichtNietIngeschrevenenMetMeerdereBetrokkenheden)
    {
        final List<BerichtIdentificeerbaar> overtredendeObjecten = new ArrayList<>();


        for (BerichtEntiteit persoonBericht : berichtNietIngeschrevenenMetMeerdereBetrokkenheden) {
            if (persoonBericht != null) {
                overtredendeObjecten.add(persoonBericht);
            } else {
                // Het zou kunnen dat de persoon niet gevonden wordt in het bericht,
                // dan gebruiken we de adm.hand. als backup bericht entiteit.
                overtredendeObjecten.add(bericht.getAdministratieveHandeling());
            }
        }

        return overtredendeObjecten;
    }


}
