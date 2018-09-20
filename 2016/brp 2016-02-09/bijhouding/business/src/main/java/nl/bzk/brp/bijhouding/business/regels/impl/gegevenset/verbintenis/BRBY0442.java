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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * Land aanvang mag niet NL zijn bij voltrekking H/P in buitenland.
 *
 * @brp.bedrijfsregel BRBY0442
 */
@Named("BRBY0442")
public class BRBY0442 implements VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView,
        HuwelijkGeregistreerdPartnerschapBericht>
{
    @Override
    public final Regel getRegel() {
        return Regel.BRBY0442;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
                                              final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (isAanvangBuitenland(actie.getAdministratieveHandeling().getSoort().getWaarde())
                && nieuweSituatie.getStandaard() != null
                && nieuweSituatie.getStandaard().getLandGebiedAanvang() != null
                && nieuweSituatie.getStandaard().getLandGebiedAanvang().getWaarde().getCode().getWaarde().shortValue()
                        == LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT.shortValue())
        {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Kijkt of het een adm.hand. betreft van een HGP aanvang in het buitenland.
     *
     * @param soortAdministratieveHandeling soort adm.hand.
     * @return aanvang buitenland (true) of niet (false)
     */
    private boolean isAanvangBuitenland(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        return soortAdministratieveHandeling == SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_BUITENLAND
            || soortAdministratieveHandeling == SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND;
    }

}
