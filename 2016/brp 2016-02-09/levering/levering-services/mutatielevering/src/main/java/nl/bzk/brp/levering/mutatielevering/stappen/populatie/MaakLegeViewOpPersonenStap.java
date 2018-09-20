/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.business.toegang.populatie.PersoonViewFactory;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import org.perf4j.aop.Profiled;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Deze stap maakt lege views voor de personen. <p/>
 */
public class MaakLegeViewOpPersonenStap extends AbstractAfnemerVerwerkingStap {

    /**
     * De Constante LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String MUTATIELEVERING_AH = "Mutatielevering-AH-";

    @Inject
    private PersoonViewFactory persoonViewFactory;

    @Override
    @Profiled(tag = "MaakLegeViewOpPersonenStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp, final LeveringsautorisatieVerwerkingContext context,
        final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        wijzigThreadName(onderwerp);

        final Map<Integer, Populatie> persoonPopulatieCorrelatie = context.getTeLeverenPersoonIds();
        final List<PersoonHisVolledig> bijgehoudenPersonenVolledig = context.getBijgehoudenPersonenVolledig();
        final List<PersoonHisVolledigView> bijgehoudenPersonenViews = new ArrayList<>(bijgehoudenPersonenVolledig.size());
        for (final Map.Entry<Integer, Populatie> teLeverenPersoonCorrelatie : persoonPopulatieCorrelatie.entrySet()) {
            final PersoonHisVolledig persoon = haalPersoonViewMetIdVanContext(bijgehoudenPersonenVolledig, teLeverenPersoonCorrelatie.getKey());
            bijgehoudenPersonenViews.add(persoonViewFactory.maakLegeView(persoon));
        }
        context.setBijgehoudenPersoonViews(bijgehoudenPersonenViews);
        LOGGER.debug("Lege views gemaakt voor " + bijgehoudenPersonenViews.size() + " personen.");
        return DOORGAAN;
    }

    /**
     * Wijzigt de naam van de huidige thread.
     *
     * @param onderwerp stappen onderwerp
     */
    private void wijzigThreadName(final LeveringautorisatieStappenOnderwerp onderwerp) {
        final Leveringinformatie leveringinformatie = onderwerp.getLeveringinformatie();
        final Dienst dienst = leveringinformatie.getDienst();
        Leveringsautorisatie leveringsautorisatie = leveringinformatie.getToegangLeveringsautorisatie().getLeveringsautorisatie();
        Thread.currentThread().setName(MUTATIELEVERING_AH + onderwerp.getAdministratieveHandelingId() + "-ABO-" + leveringsautorisatie.getID()
            + "-TOEG-" + leveringinformatie.getToegangLeveringsautorisatie().getID() + "-DIE-" + dienst.getID());
    }

    /**
     * Haalt de persoon his volledig van de context op basis van de persoon id.
     *
     * @param bijgehoudenPersonen De bijgehouden personen van de context.
     * @param persoonId           De persoon id.
     * @return persoonhisvolledig
     */
    private PersoonHisVolledig haalPersoonViewMetIdVanContext(final List<PersoonHisVolledig> bijgehoudenPersonen, final Integer persoonId) {
        for (final PersoonHisVolledig bijgehoudenPersoon : bijgehoudenPersonen) {
            if (persoonId.equals(bijgehoudenPersoon.getID())) {
                return bijgehoudenPersoon;
            }
        }
        return null;
    }

    @Override
    public final void voerNabewerkingStapUit(final LeveringautorisatieStappenOnderwerp onderwerp, final LeveringsautorisatieVerwerkingContext context,
        final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        context.setBijgehoudenPersoonViews(Collections.<PersoonHisVolledigView>emptyList());
        Thread.currentThread().setName(MUTATIELEVERING_AH + onderwerp.getAdministratieveHandelingId());
    }
}
