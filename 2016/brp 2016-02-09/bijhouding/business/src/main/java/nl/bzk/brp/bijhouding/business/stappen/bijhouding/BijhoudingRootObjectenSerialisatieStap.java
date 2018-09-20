/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import org.springframework.stereotype.Component;

/**
 * In deze stap worden voor alle bijgehouden personen nieuwe BLOB's aangemaakt en opgeslagen in de database.
 */
@Component
public class BijhoudingRootObjectenSerialisatieStap {

    @Inject
    private BlobifierService blobifierService;

    /**
     * Voer stap uit.
     *  @param bericht the onderwerp
     * @param berichtContext the bericht context
     * @return Resultaat
     */
    public Resultaat voerStapUit(final BijhoudingsBericht bericht, final BijhoudingBerichtContext berichtContext) {
        // Sla de bijgehouden Persoon als BLOB op, in de database.
        // Bij prevalidatie slaan we deze stap over, aangezien de transactie teruggedraaid gaat worden in de BijhoudingTransactieStap.
        if (!BerichtUtils.isBerichtPrevalidatieAan(bericht) && !BerichtUtils.isHuwelijksberichtMetHandmatigFiatterendePartners(bericht,
            berichtContext))
        {
            for (final PersoonHisVolledigImpl persoon : berichtContext.getBijgehoudenPersonen(true)) {
                blobifierService.blobify(persoon, false);
            }
        }
        return Resultaat.LEEG;
    }
}
