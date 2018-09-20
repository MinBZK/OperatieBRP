/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.RelatieUtils;

/**
 * Deze klasse bevat de definitie voor verwantschap.
 * <p/>
 *
 * @brp.bedrijfsregel BRBY0001
 */
@Named("BRBY0001")
public class BRBY0001 {

    /**
     * Is er sprake verwantschap tussen beide personen.
     *
     * @param persoon1 persoon 1
     * @param persoon2 persoon 2
     * @return true als er verwantschap is
     */
    public boolean isErVerwantschap(final PersoonView persoon1, final PersoonView persoon2) {
        boolean verwantschap = false;

        final Map<PersoonView, SoortBetrokkenheid> familiePersoon1 =
                maakLijstPersonenMetFamilieRechtelijkeBetrekking(persoon1);
        final Map<PersoonView, SoortBetrokkenheid> familiePersoon2 =
                maakLijstPersonenMetFamilieRechtelijkeBetrekking(persoon2);

        for (final Map.Entry<PersoonView, SoortBetrokkenheid> entry1 : familiePersoon1.entrySet()) {
            final PersoonView persoon = entry1.getKey();
            for (final Map.Entry<PersoonView, SoortBetrokkenheid> entry2 : familiePersoon2.entrySet()) {
                final PersoonView persoonHisVolledigView = entry2.getKey();

                /**
                 * Indien beiden ouder zijn in dezelfde familierechtelijkebetrekking, dan zijn ze niet
                 * verwant.
                 *
                 * Uitzondering:
                 * Een Familierechtelijke betrekking  waarin de twee personen beiden als Ouder zijn
                 * betrokken zijn niet verwant, dat zijn immers 'gewoon' de ouders van een Kind.
                 */
                if (persoonHisVolledigView.getID().equals(persoon.getID())
                        && !(SoortBetrokkenheid.OUDER == entry1.getValue()
                        && SoortBetrokkenheid.OUDER == entry2.getValue()))
                {
                    verwantschap = true;
                    /**
                     * Direct stoppen bij eerste verwante persoon, verwantschap is dan namelijk vastgesteld.
                     */
                    break;
                }
            }
        }

        return verwantschap;
    }

    /**
     * Maakt een lijst personen met familie rechtelijke betrekking.
     *
     * @param persoon de persoon
     * @return de set met personen waarmee een familierechtelijke betrekking is
     */
    private Map<PersoonView, SoortBetrokkenheid> maakLijstPersonenMetFamilieRechtelijkeBetrekking(
            final PersoonView persoon)
    {
        final Map<PersoonView, SoortBetrokkenheid> personen = new HashMap<>();

        if (persoon.getID() != null) {
            // De persoon zelf heeft zelf ook een betrokkenheid met de familierechtelijke betrekking.
            personen.put(persoon, null);

            final List<Persoon> kinderen = RelatieUtils.haalAlleKinderenUitPersoon(persoon);
            for (final Persoon kind : kinderen) {
                personen.put((PersoonView) kind, SoortBetrokkenheid.OUDER);
            }

            final Collection<Persoon> ouders = RelatieUtils.haalOudersUitKind(persoon);
            for (final Persoon ouder : ouders) {
                personen.put((PersoonView) ouder, SoortBetrokkenheid.KIND);
            }
        }
        return personen;
    }
}
