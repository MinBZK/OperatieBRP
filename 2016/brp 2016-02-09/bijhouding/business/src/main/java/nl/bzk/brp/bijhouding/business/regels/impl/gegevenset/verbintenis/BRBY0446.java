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
 * Land einde mag niet NL zijn bij ontbinding H/P in buitenland.
 *
 * @brp.bedrijfsregel BRBY0446
 */
@Named("BRBY0446")
public class BRBY0446 implements VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView,
        HuwelijkGeregistreerdPartnerschapBericht>
{
    @Override
    public final Regel getRegel() {
        return Regel.BRBY0446;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
                                              final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (isEindeBuitenland(actie.getAdministratieveHandeling().getSoort().getWaarde())
                && nieuweSituatie.getStandaard() != null
                && nieuweSituatie.getStandaard().getLandGebiedEinde() != null
                && nieuweSituatie.getStandaard().getLandGebiedEinde().getWaarde().getCode().getWaarde().shortValue()
                        == LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT.shortValue())
        {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Kijkt of het een adm.hand. betreft van een HGP einde in het buitenland.
     *
     * @param soortAdministratieveHandeling soort adm.hand.
     * @return einde buitenland (true) of niet (false)
     */
    private boolean isEindeBuitenland(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        return soortAdministratieveHandeling == SoortAdministratieveHandeling.ONTBINDING_HUWELIJK_IN_BUITENLAND
            || soortAdministratieveHandeling == SoortAdministratieveHandeling.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND;
    }

}
