/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVerstrekkingsbeperkingBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * Verstekkingsbeperking moet mogelijk zijn voor specieke partij.
 *
 * @brp.bedrijfsregel BRBY0192
 */
@Named("BRBY0192")
public class BRBY0192 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0192;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
            final PersoonBericht nieuweSituatie, final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        // Null check verplicht, want actie kan ook alleen volledige verstrekkingsbeperking bevatten.
        if (nieuweSituatie.getVerstrekkingsbeperkingen() != null) {
            for (PersoonVerstrekkingsbeperkingBericht verstrekkingsbeperking : nieuweSituatie.getVerstrekkingsbeperkingen())
            {
                if (verstrekkingsbeperking.getPartij() != null
                    // Als op de partij geconfigureerd is dat er geen verstrekkingsbeperking mogelijk is, dan gaat de regel af.
                    && !verstrekkingsbeperking.getPartij().getWaarde().getIndicatieVerstrekkingsbeperkingMogelijk().getWaarde())
                {
                    objectenDieDeRegelOvertreden.add(verstrekkingsbeperking);
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }

}
