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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import nl.bzk.brp.util.RelatieUtils;


/**
 * BRBY0103: Adresgevende ouder mag geen briefadres hebben.
 * <p/>
 * De Ingezetene wiens Ouderschap met de indicatie AdresgevendeOuder wordt geregistreerd door de Actie
 * RegistratieGeboorte, mag op DatumGeboorte van het Kind geen Adres van de Soort "Briefadres" hebben.
 *
 * @brp.bedrijfsregel BRBY0103
 */
@Named("BRBY0103")
public class BRBY0103 implements
        VoorActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0103;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView huidigeSituatie,
            final FamilierechtelijkeBetrekkingBericht nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        // adresgevens ouders zijn de ouwkivs
        final List<Persoon> moeders = RelatieUtils.haalAlleMoedersUitRelatie(nieuweSituatie);
        for (final Persoon moeder : moeders) {

            // zoek de huidige gegevens van de moeder op
            final Persoon moederDb = bestaandeBetrokkenen.get(((PersoonBericht) moeder).getIdentificerendeSleutel());
            if (moederDb != null && moederDb.isIngezetene()) {
                // controleer of binnen de huidige gegevens een briefadres geregistreerd is
                for (final PersoonAdres adres : moederDb.getAdressen()) {
                    if (adres.getStandaard().getSoort().getWaarde() == FunctieAdres.BRIEFADRES) {
                        objectenDieDeRegelOvertreden.add((PersoonBericht) moeder);
                    }
                }
            }
        }
        return objectenDieDeRegelOvertreden;
    }
}
