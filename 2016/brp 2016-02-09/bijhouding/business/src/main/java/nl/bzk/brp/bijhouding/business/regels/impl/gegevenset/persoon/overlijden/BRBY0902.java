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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;

/**
 * Datum overlijden op of na datum eerste inschrijving
 * <p/>
 * In de groep Overlijden moet datum overlijden liggen op of na datum inschrijving van de groep Inschrijving.
 *
 * @brp.bedrijfsregel BRBY0902
 */
@Named("BRBY0902")
public class BRBY0902 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0902;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
                                              final PersoonBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (huidigeSituatie.getInschrijving() != null && nieuweSituatie.getOverlijden() != null
                && huidigeSituatie.getInschrijving().getDatumInschrijving() != null
                && nieuweSituatie.getOverlijden().getDatumOverlijden() != null)
        {
            final DatumEvtDeelsOnbekendAttribuut
                    datumInschrijving = huidigeSituatie.getInschrijving().getDatumInschrijving();
            final DatumEvtDeelsOnbekendAttribuut datumOverlijden = nieuweSituatie.getOverlijden().getDatumOverlijden();

            if (datumOverlijden.voor(datumInschrijving) || DatumAttribuut.vandaag().voor(datumOverlijden)) {
                objectenDieDeRegelOvertreden.add(nieuweSituatie);
            }
        }

        return objectenDieDeRegelOvertreden;
    }
}
