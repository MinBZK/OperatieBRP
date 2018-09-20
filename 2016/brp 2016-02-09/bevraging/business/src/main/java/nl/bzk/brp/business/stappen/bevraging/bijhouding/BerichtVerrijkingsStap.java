/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.bijhouding;

import javax.inject.Inject;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * De stap waarin extra gegevens opgehaald worden.
 */
public class BerichtVerrijkingsStap extends
        AbstractBerichtVerwerkingStap<BevragingsBericht, BevragingBerichtContextBasis, BevragingResultaat>
{

    private static final Logger      LOGGER = LoggerFactory.getLogger();

    @Inject
    private ReferentieDataRepository referentieRepository;

    /**
     * Voer stap uit.
     *
     * @brp.bedrijfsregel VR00050
     *
     * @param bericht the bericht
     * @param context the context
     * @param resultaat the resultaat
     * @return the boolean
     */
    @Regels(Regel.VR00050)
    @Override
    public final boolean voerStapUit(final BevragingsBericht bericht, final BevragingBerichtContextBasis context,
            final BevragingResultaat resultaat)
    {
        final Partij zendendePartij =
            referentieRepository.vindPartijOpCode(new PartijCodeAttribuut(Integer.parseInt(bericht.getStuurgegevens()
                    .getZendendePartijCode())));
        bericht.getStuurgegevens().setZendendePartij(new PartijAttribuut(zendendePartij));

        LOGGER.debug("BerichtverrijkingsStap.voerStapUit");

        return DOORGAAN;
    }

}
