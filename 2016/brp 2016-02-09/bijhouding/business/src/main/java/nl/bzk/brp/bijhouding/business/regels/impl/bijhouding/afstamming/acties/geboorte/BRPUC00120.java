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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import nl.bzk.brp.util.RelatieUtils;


/**
 * De DatumAanvangGeldigheid van het actuele Adres van de Persoon wiens Ouderschap met de indicatie
 * OuderUitWieHetKindIsVoortgekomen door de Actie
 * RegistratieGeboorte wordt geregistreerd, mag niet liggen na DatumGeboorte van het Kind.
 *
 * @brp.bedrijfsregel BRPUC00120
 */
@Named("BRPUC00120")
public class BRPUC00120 implements
        VoorActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRPUC00120;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView huidigeSituatie,
            final FamilierechtelijkeBetrekkingBericht nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final Persoon kind = RelatieUtils.haalKindUitRelatie(nieuweSituatie);
        final DatumEvtDeelsOnbekendAttribuut datumGeboorteKind = kind.getGeboorte().getDatumGeboorte();

        final PersoonBericht ouwkiv = (PersoonBericht) RelatieUtils.haalMoederUitRelatie(nieuweSituatie);

        /**
         * Deze bedrijfregel is afhankelijk van bedrijfregel BRBY0168, waarin gecheckt wordt dat er precies één
         * ouwkiv (ouder uit wie kind is voortgekomen) aanwezig is. Bij het ontbreken van een ouwkiv zal BRBY0168 een
         * melding tonen en hoeft deze bedrijfsregel dus niets ondernemen. Ook als er geen bestaande betrokkene
         * wordt gevonden hoeven we niks te doen, want dat pakt BRBY0169 al voor ons op.
         */
        if (ouwkiv != null) {
            final PersoonView ouwkivView = bestaandeBetrokkenen.get(ouwkiv.getIdentificerendeSleutel());
            if (ouwkivView != null && !ouwkivView.getAdressen().isEmpty()) {
                final PersoonAdres adresOuwkiv = ouwkivView.getAdressen().iterator().next();
                final DatumEvtDeelsOnbekendAttribuut datumAanvangAdreshoudingAdresOuwkiv =
                    adresOuwkiv.getStandaard().getDatumAanvangAdreshouding();

                final KindBericht kindBetrokkenheid = nieuweSituatie.getKindBetrokkenheid();
                if (datumGeboorteKind.voorDatumSoepel(datumAanvangAdreshoudingAdresOuwkiv) && kindBetrokkenheid != null) {

                    /**
                     * In de trunk wordt de geboortegroep gemeld met het veld datumgeboorte.
                     * In de huidige vorm zijn dit geen objecten van het type BerichtEntiteit.
                     */
                    objectenDieDeRegelOvertreden.add(kindBetrokkenheid.getPersoon());
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }
}
