/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.erkenning;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.hisvolledig.util.PersoonUtil;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.RelatieUtils;


/**
 * Erkenner moet minstens 16 jaar zijn.
 *
 * @brp.bedrijfsregel BRBY0143
 */
@Named("BRBY0143")
public class BRBY0143 implements
        VoorActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0143;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView huidigeSituatie,
            final FamilierechtelijkeBetrekkingBericht nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        // Er is bij een erkenning altijd maar 1 ouder in het bericht.
        final PersoonBericht ouderPersoonBericht =
                (PersoonBericht) RelatieUtils.haalOudersUitRelatie(nieuweSituatie).get(0);
        Persoon teCheckenPersoon = bestaandeBetrokkenen.get(ouderPersoonBericht.getIdentificerendeSleutel());
        if (teCheckenPersoon == null) {
            // persoon komt niet voor in de db, ouder is een niet ingeschrevene. Ouder uit het bericht controleren
            teCheckenPersoon = ouderPersoonBericht;
        }
        if (!PersoonUtil.isLeeftijdOfOuderOpDatum(teCheckenPersoon, DatumEvtDeelsOnbekendAttribuut.ZESTIEN_JAAR,
                new DatumAttribuut(actie.getDatumAanvangGeldigheid())))
        {
            objectenDieDeRegelOvertreden.add(ouderPersoonBericht);
        }

        return objectenDieDeRegelOvertreden;
    }

}
