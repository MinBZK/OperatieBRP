/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * De gemeente overlijden moet verwijzen naar een geldig voorkomen in stamgegeven "Gemeente" op peildatum datum
 * overlijden.
 *
 * @brp.bedrijfsregel BRBY0903
 */
@Named("BRBY0903")
public class BRBY0903 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0903;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie, final PersoonBericht nieuweSituatie,
            final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (nieuweSituatie.getOverlijden() != null) {
            final GemeenteAttribuut gemeente = nieuweSituatie.getOverlijden().getGemeenteOverlijden();
            final DatumEvtDeelsOnbekendAttribuut datumOverlijden = nieuweSituatie.getOverlijden().getDatumOverlijden();
            if (gemeente != null && gemeente.getWaarde() != null
                && !datumOverlijden.isGeldigTussen(gemeente.getWaarde().getDatumAanvangGeldigheid(), gemeente.getWaarde().getDatumEindeGeldigheid()))
            {
                objectenDieDeRegelOvertreden.add(nieuweSituatie);
            }
        }

        return objectenDieDeRegelOvertreden;
    }

}
