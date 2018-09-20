/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.hisvolledig.util.PersoonUtil;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;


/**
 * Verkrijger Nederlandschap mag hoogstens één H/P relatie hebben.
 *
 * @brp.bedrijfsregel BRBY0157
 */
@Named("BRBY0157")
public class BRBY0157 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0157;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
            final PersoonBericht nieuweSituatie, final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (NationaliteitRegelUtil.verkrijgtNederlandseNationaliteit(nieuweSituatie) && huidigeSituatie != null) {
            int aantalActueleHgps = 0;
            for (HuwelijkGeregistreerdPartnerschap hgp : PersoonUtil.getHGPs(huidigeSituatie)) {
                // Een hgp is actueel als er geen datum einde voor bestaat.
                if (hgp.getStandaard().getDatumEinde() == null) {
                    aantalActueleHgps++;
                }
            }
            // Als er meer dan 1 actueel hgp gevonden is, dan is de persoon in overtreding.
            if (aantalActueleHgps > 1) {
                objectenDieDeRegelOvertreden.add(nieuweSituatie);
            }
        }

        return objectenDieDeRegelOvertreden;
    }

}
