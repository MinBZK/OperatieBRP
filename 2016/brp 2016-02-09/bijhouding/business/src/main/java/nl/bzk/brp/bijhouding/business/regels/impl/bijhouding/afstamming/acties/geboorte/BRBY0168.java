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
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.RelatieUtils;


/**
 * Implementatie van bedrijfsregel BRBY0168. Er is precies 1 persoon aanwezig in het bericht die in de rol van ouder
 * betrokken is bij de relatie van de
 * soort familierechtelijke betrekking en de indicatie 'ouder uit wie het kind geboren is' heeft in het bericht.
 *
 * @brp.bedrijfsregel BRBY0168
 */
@Named("BRBY0168")
public class BRBY0168 implements
        VoorActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0168;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView huidigeSituatie,
            final FamilierechtelijkeBetrekkingBericht nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final List<Persoon> moeders = RelatieUtils.haalAlleMoedersUitRelatie(nieuweSituatie);
        if (moeders.size() != 1) {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }
        return objectenDieDeRegelOvertreden;
    }

}
