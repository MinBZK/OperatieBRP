/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0001;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.RelatieUtils;


/**
 * Een Ouderschap mag niet worden geregistreerd als op DatumAanvangGeldigheid daarvan het Kind twee Ouders krijgt
 * waartussen Verwantschap bestaat.
 *
 * @brp.bedrijfsregel BRBY0134
 */
@Named("BRBY0134")
public class BRBY0134 implements
    VoorActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    @Inject
    private BRBY0001 brby0001;

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0134;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView huidigeSituatie,
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie, final Actie actie,
        final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final List<Persoon> ouders = RelatieUtils.haalOudersUitRelatie(nieuweSituatie);

        /**
         * Volgens XSD moet het kind één of twee ouders hebben. Verwantschap kan er alleen zijn bij twee ouders die
         * ingeschrevenen zijn.
         */
        if (ouders.size() == 2 && zijnIngeschrevenen(ouders)) {
            final PersoonView ouder1 =
                bestaandeBetrokkenen.get(((PersoonBericht) ouders.get(0)).getIdentificerendeSleutel());
            final PersoonView ouder2 =
                bestaandeBetrokkenen.get(((PersoonBericht) ouders.get(1)).getIdentificerendeSleutel());

            /**
             * Bij sprake van verwantschap wordt eerste ouder teruggegeven als object dat de regel overtreedt.
             */
            if (brby0001.isErVerwantschap(ouder1, ouder2)) {
                objectenDieDeRegelOvertreden.add((PersoonBericht) ouders.get(0));
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of personen van het soort ingeschrevene zijn.
     *
     * @param personen personen
     * @return boolean
     */
    private boolean zijnIngeschrevenen(final List<Persoon> personen) {
        boolean zijnIngeschrevenen = true;
        for (final Persoon persoon : personen) {
            final PersoonBericht persoonBericht = (PersoonBericht) persoon;
            if (persoonBericht.getSoort().getWaarde() != SoortPersoon.INGESCHREVENE) {
                zijnIngeschrevenen = false;
            }
        }
        return zijnIngeschrevenen;
    }
}
