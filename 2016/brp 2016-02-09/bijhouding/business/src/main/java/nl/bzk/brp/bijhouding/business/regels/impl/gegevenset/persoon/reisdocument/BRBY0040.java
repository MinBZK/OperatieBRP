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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;

/**
 * BRBY0040: Reisdocumenthouder mag geen belemmering verstrekking hebben.
 *
 * @brp.bedrijfsregel BRBY0040
 */
@Named("BRBY0040")
public class BRBY0040 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
                                              final PersoonBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        for (PersoonIndicatie persoonIndicatie : huidigeSituatie.getIndicaties()) {
            if (SoortIndicatie.INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT
                    == persoonIndicatie.getSoort().getWaarde()
                    && persoonIndicatie.getStandaard().getWaarde().getWaarde() == Ja.J)
            {
                objectenDieDeRegelOvertreden.add(nieuweSituatie);
            }
        }
        return objectenDieDeRegelOvertreden;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0040;
    }
}
