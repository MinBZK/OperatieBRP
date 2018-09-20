/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieeindehuwelijkpartnerschap;

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


/**
 * BRBY0443: Te beëindigen relatie mag niet al zijn beëindigd.
 *
 * De DatumEinde in de groep StandaardHuwelijkPartnerschap die door RegistratieEindeHuwelijkPartnerschap wordt gevuld,
 * mag (vóór verwerking nog) geen waarde hebben.
 */
@Named("BRBY0443")
public class BRBY0443 implements
        VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView, HuwelijkGeregistreerdPartnerschapBericht>
{

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
            final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        if (huidigeSituatie.getStandaard() != null && huidigeSituatie.getStandaard().getDatumEinde() != null
            && huidigeSituatie.getStandaard().getDatumEinde().heeftWaarde())
        {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }
        return objectenDieDeRegelOvertreden;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0443;
    }
}
