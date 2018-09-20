/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.groep;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;


/**
 * Als er een actueel voorkomen van de groep Bijhouding bij de Persoon bestaat met de NadereBijhoudingsaard "Overleden",
 * dan mogen de volgende gegevens niet meer bijgehouden worden:
 *  - elke Groep van de overleden Persoon zonder of alleen met FormeleHistorie (m.u.v. een correctie op Overlijden?)
 *  - elke Groep van de overleden Persoon met MateriëleHistorie waarbij DatumAanvangGeldigheid na de Datum van
 *  Overlijden ligt.
 * Opmerking:
 * 1. Gegevens over de Relaties van de Persoon mogen nog wel bijgehouden worden
 *    (ook FRB en ABS relaties na overlijdensdatum?)
 * 2. Betreft eigenlijk geen BRAL, maar een BRBY.
 *
 * @brp.bedrijfsregel BRAL9003
 */
@Named("BRAL9003")
public class BRAL9003 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRAL9003;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
                                              final PersoonBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (huidigeSituatie != null
            && huidigeSituatie.getBijhouding() != null
            && huidigeSituatie.getBijhouding().getNadereBijhoudingsaard() != null
            && huidigeSituatie.getBijhouding().getNadereBijhoudingsaard().getWaarde() == NadereBijhoudingsaard.OVERLEDEN)
        {
            final HisPersoonBijhoudingModel bijhouding = huidigeSituatie.getBijhouding();
            final DatumEvtDeelsOnbekendAttribuut datumNadereBijhoudingOverleden = bijhouding.getDatumAanvangGeldigheid();
            if (actie.getDatumAanvangGeldigheid() != null && !datumNadereBijhoudingOverleden.na(actie.getDatumAanvangGeldigheid()))
            {
                // BRAL9003 is een generieke bedrijfsregel, van toepassing op allerlei bijhoudingen.
                // de fout wordt dus gezet op de persoon.
                objectenDieDeRegelOvertreden.add(nieuweSituatie);
            }
        }
        return objectenDieDeRegelOvertreden;
    }

}
