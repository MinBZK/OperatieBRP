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
 * De Persoon waarvoor de groep EUverkiezingen wordt geregistreerd, moet op DatumAanleidingAanpassing daarvan de
 * Nationaliteit van een EU-lidstaat bezitten anders dan Nederlandse.
 */
@Named("BRBY0132")
public class BRBY0132 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
                                              final PersoonBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        // We checken (in overleg) niet op specifieke EU nationaliteiten, want die zijn niet bekend in de DB.
        // Als alternatief valideren we dus alleen dat de nationaliteit niet NL is.
        if (huidigeSituatie.heeftNederlandseNationaliteit()) {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }
        return objectenDieDeRegelOvertreden;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0132;
    }
}
