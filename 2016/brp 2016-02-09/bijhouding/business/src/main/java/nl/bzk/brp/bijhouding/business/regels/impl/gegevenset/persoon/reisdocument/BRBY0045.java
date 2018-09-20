/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * BRBY0045: Aanduiding verplicht indien datum onttrekking gevuld.
 *
 * Als DatumOnttrekking in Reisdocument een waarde heeft dan moet ook AanduidingOnttrekking een waarde hebben, anders
 * mag AanduidingOnttrekking geen waarde hebben.
 *
 * @brp.bedrijfsregel BRBY0045
 */
@Named("BRBY0045")
public class BRBY0045 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie, final PersoonBericht nieuweSituatie,
            final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (nieuweSituatie.getReisdocumenten() != null) {
            for (PersoonReisdocumentBericht persoonReisdocumentBericht : nieuweSituatie.getReisdocumenten()) {
                if (persoonReisdocumentBericht.getStandaard() != null) {
                    final DatumEvtDeelsOnbekendAttribuut datumInhoudingVermissing =
                        persoonReisdocumentBericht.getStandaard().getDatumInhoudingVermissing();
                    final String redenVervallenCode = persoonReisdocumentBericht.getStandaard()
                                                                          .getAanduidingInhoudingVermissingCode();

                    // datum verplicht redenVervallenCode is ingevuld en vise versa
                    if ((datumInhoudingVermissing != null && redenVervallenCode == null)
                        || (datumInhoudingVermissing == null && redenVervallenCode != null))
                    {
                        objectenDieDeRegelOvertreden.add(persoonReisdocumentBericht);
                    }
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0045;
    }
}
