/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.RelatieUtils;


/**
 * Geboortedatum ouders voor geboortedatum kind.
 *
 * @brp.bedrijfsregel BRBY0129
 */
@Named("BRBY0129")
public class BRBY0129 implements
    VoorActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0129;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView huidigeSituatie,
            final FamilierechtelijkeBetrekkingBericht nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        final PersoonBericht kindBericht = (PersoonBericht) RelatieUtils.haalKindUitRelatie(nieuweSituatie);
        if (kindBericht.getGeboorte() != null && kindBericht.getGeboorte().getDatumGeboorte() != null) {
            final DatumEvtDeelsOnbekendAttribuut kindGeboorteDatum = kindBericht.getGeboorte().getDatumGeboorte();
            for (final Persoon ouder : RelatieUtils.haalOudersUitRelatie(nieuweSituatie)) {
                final PersoonBericht ouderBericht = (PersoonBericht) ouder;
                final String identificerendeSleutel = ouderBericht.getIdentificerendeSleutel();
                final PersoonView ouderView = bestaandeBetrokkenen.get(identificerendeSleutel);
                if (ouderView != null && ouderView.getGeboorte() != null
                    && ouderView.getGeboorte().getDatumGeboorte() != null)
                {
                    final DatumEvtDeelsOnbekendAttribuut ouderGeboorteDatum =
                        ouderView.getGeboorte().getDatumGeboorte();
                    if (kindGeboorteDatum.voorOfOp(ouderGeboorteDatum)) {
                        objectenDieDeRegelOvertreden.add(kindBericht);
                        break;
                    }
                }
            }
        }
        return objectenDieDeRegelOvertreden;
    }

}
