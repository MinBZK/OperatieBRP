/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.util.RelatieUtils;


/**
 * BRBY0177: Datum aanvang geldigheid actie gelijk aan datum geboorte
 * <p/>
 * In het Bericht moet DatumAanvangGeldigheid van de Actie RegistratieGeboorte dezelfde waarde hebben als DatumGeboorte
 * van het Kind.
 * <p/>
 *
 * @brp.bedrijfsregel BRBY0177
 */
@Named("BRBY0177")
public class BRBY0177 implements
        VoorActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0177;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView huidigeSituatie,
            final FamilierechtelijkeBetrekkingBericht nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final PersoonBericht kindBericht = (PersoonBericht) RelatieUtils.haalKindUitRelatie(nieuweSituatie);
        if (kindBericht != null
            && kindBericht.getGeboorte() != null
            && kindBericht.getGeboorte().getDatumGeboorte() != null
            && !kindBericht.getGeboorte().getDatumGeboorte().getWaarde()
                .equals(actie.getDatumAanvangGeldigheid().getWaarde()))
        {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }

        return objectenDieDeRegelOvertreden;
    }
}
