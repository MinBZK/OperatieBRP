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
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.RelatieUtils;


/**
 * Implementatie van bedrijfsregel BRBY0169 Moeder van kind moet ingezetene zijn.
 * <p/>
 * De Persoon wiens Ouderschap met de indicatie OuderUitWieHetKindIsVoortgekomen door de Actie RegistratieGeboorte wordt
 * geregistreerd, moet op DatumGeboorte van Kind een Ingezetene zijn.
 *
 * @brp.bedrijfsregel BRBY0169
 */
@Named("BRBY0169")
public class BRBY0169 implements
        VoorActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0169;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView huidigeSituatie,
            final FamilierechtelijkeBetrekkingBericht nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final List<Persoon> moeders = RelatieUtils.haalAlleMoedersUitRelatie(nieuweSituatie);
        for (final Persoon moeder : moeders) {
            final PersoonView persoon = bestaandeBetrokkenen.get(((PersoonBericht) moeder).getIdentificerendeSleutel());

            // Fout als persoon null is (niet ingeschrevene) of niet ingezeten is.
            if (persoon == null || !persoon.isIngezetene()) {
                objectenDieDeRegelOvertreden.add((BerichtEntiteit) moeder);
            }
        }

        return objectenDieDeRegelOvertreden;
    }
}
