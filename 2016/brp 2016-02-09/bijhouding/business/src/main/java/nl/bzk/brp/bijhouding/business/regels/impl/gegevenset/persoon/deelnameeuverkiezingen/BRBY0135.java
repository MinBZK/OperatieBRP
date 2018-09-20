/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;

/**
 * Indien gevuld moet DatumEindeUitsluiting in EUverkiezingen, na DatumAanleidingAanpassing liggen.
 */
@Named("BRBY0135")
public class BRBY0135 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
                                              final PersoonBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        if (nieuweSituatie.getDeelnameEUVerkiezingen() != null
            && nieuweSituatie.getDeelnameEUVerkiezingen().getDatumVoorzienEindeUitsluitingEUVerkiezingen() != null
            //controleer datum einde in bericht tegen bericht situatie:
            && nieuweSituatie.getDeelnameEUVerkiezingen().getDatumAanleidingAanpassingDeelnameEUVerkiezingen() != null
            && nieuweSituatie.getDeelnameEUVerkiezingen().getDatumVoorzienEindeUitsluitingEUVerkiezingen()
            .voorOfOp(nieuweSituatie.getDeelnameEUVerkiezingen().getDatumAanleidingAanpassingDeelnameEUVerkiezingen()))
        {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }
        return objectenDieDeRegelOvertreden;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0135;
    }
}
