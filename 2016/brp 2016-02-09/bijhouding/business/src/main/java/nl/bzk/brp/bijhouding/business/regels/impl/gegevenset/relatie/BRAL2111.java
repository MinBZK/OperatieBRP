/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.relatie;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;

/**
 * In een H/P-relatie zijn twee partners betrokken.
 *
 * @brp.bedrijfsregel BRAL2111
 */
@Named("BRAL2111")
public class BRAL2111 implements VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView,
        HuwelijkGeregistreerdPartnerschapBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRAL2111;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
                                              final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (isRegelOvertreden(nieuweSituatie)) {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of de regel overtreden is.
     *
     * @param hgp Huwelijk Geregistreerd Partnerschap Bericht
     * @return true als regel overtreden is
     */
    private boolean isRegelOvertreden(final HuwelijkGeregistreerdPartnerschapBericht hgp) {
        return isCorrecteSoortRelatie(hgp.getSoort().getWaarde())
                && !heeftRelatieTweePartnerBetrokkenheden(hgp.getBetrokkenheden());
    }

    /**
     * Controleert of de relatie van het juiste soort is.
     *
     * @param soortRelatie soort relatie
     * @return true als relatie van soort huwelijk of geregistreerd partnerschap is
     */
    private boolean isCorrecteSoortRelatie(final SoortRelatie soortRelatie) {
        return soortRelatie == SoortRelatie.HUWELIJK || soortRelatie == SoortRelatie.GEREGISTREERD_PARTNERSCHAP;
    }

    /**
     * Controleert of de relatie twee partner betrokkenheden bevat.
     *
     * @param betrokkenheden lijst met betrokkenheden
     * @return true als relatie twee partner betrokkenheden heeft
     */
    private boolean heeftRelatieTweePartnerBetrokkenheden(final List<BetrokkenheidBericht> betrokkenheden) {
        final List<BetrokkenheidBericht> partnerBetrokkenheden = new ArrayList<>();

        for (BetrokkenheidBericht betrokkenheid : betrokkenheden) {
            if (betrokkenheid.getRol().getWaarde() == SoortBetrokkenheid.PARTNER) {
                partnerBetrokkenheden.add(betrokkenheid);
            }
        }

        return partnerBetrokkenheden.size() == 2;
    }
}
