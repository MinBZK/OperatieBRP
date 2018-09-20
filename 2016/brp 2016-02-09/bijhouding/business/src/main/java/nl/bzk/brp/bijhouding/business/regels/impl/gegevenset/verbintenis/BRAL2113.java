/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;

/**
 * Indien gevuld moet DatumEinde in de groep StandaardHuwelijkPartnerschap op of na DatumAanvang liggen.
 *
 * @brp.bedrijfsregel BRAL2113
 */
@Named("BRAL2113")
public class BRAL2113 implements VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView,
        HuwelijkGeregistreerdPartnerschapBericht>
{
    @Override
    public final Regel getRegel() {
        return Regel.BRAL2113;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
                                              final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (bevatBeideDatumWaardes(huidigeSituatie, nieuweSituatie)
            && nieuweSituatie.getStandaard().getDatumEinde().voor(huidigeSituatie.getStandaard().getDatumAanvang()))
        {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of er voor beide situaties wel de benodigde datum waardes aanwezig zijn.
     *
     * @param huidigeSituatie de huidige situatie rond het huwelijk.
     * @param nieuweSituatie de nieuwe (beeindigde) situatie rond het huwelijk.
     * @return boolean die aangeeft of beide datums er zijn of niet.
     */
    private boolean bevatBeideDatumWaardes(final HuwelijkGeregistreerdPartnerschap huidigeSituatie,
        final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie)
    {
        return nieuweSituatie.getStandaard().getDatumEinde() != null
            && nieuweSituatie.getStandaard().getDatumEinde().getWaarde() != null
            && huidigeSituatie.getStandaard().getDatumAanvang() != null
            && huidigeSituatie.getStandaard().getDatumAanvang().getWaarde() != null;
    }

}
