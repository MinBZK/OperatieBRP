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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * Datum aanvang H/P mag niet in de toekomst liggen.
 *
 * @brp.bedrijfsregel BRBY0438
 */
@Named("BRBY0438")
public class BRBY0438 implements VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView,
        HuwelijkGeregistreerdPartnerschapBericht>
{
    @Override
    public final Regel getRegel() {
        return Regel.BRBY0438;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
                                              final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (nieuweSituatie.getStandaard() != null
                && nieuweSituatie.getStandaard().getDatumAanvang() != null
                && nieuweSituatie.getStandaard().getDatumAanvang().na(DatumAttribuut.vandaag()))
        {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }

        return objectenDieDeRegelOvertreden;
    }

}
