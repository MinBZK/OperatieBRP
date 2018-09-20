/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0003;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;

/**
 * De Persoon waarvoor EUverkiezingen wordt geregistreerd, moet op DatumAanleidingAanpassing meerderjarig zijn.
 */
@Named("BRBY0137")
public class BRBY0137 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Inject
    private BRBY0003 brby0003;

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
        final PersoonBericht nieuweSituatie,
        final Actie actie,
        final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        if (nieuweSituatie.getDeelnameEUVerkiezingen() != null
            && nieuweSituatie.getDeelnameEUVerkiezingen().getDatumAanleidingAanpassingDeelnameEUVerkiezingen() != null
            && !brby0003.isMeerderjarig(huidigeSituatie,
            new DatumAttribuut(nieuweSituatie.getDeelnameEUVerkiezingen().getDatumAanleidingAanpassingDeelnameEUVerkiezingen())))
        {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }
        return objectenDieDeRegelOvertreden;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0137;
    }
}
