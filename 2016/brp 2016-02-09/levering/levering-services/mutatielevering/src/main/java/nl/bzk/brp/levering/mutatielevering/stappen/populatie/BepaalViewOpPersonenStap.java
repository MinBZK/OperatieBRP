/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.business.toegang.populatie.PersoonViewFactory;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.perf4j.aop.Profiled;


/**
 * Deze stap bepaalt de delta voor de personen op de tijdstip registratie van de administratieve handelingen. Dit zorgt
 * er voor dat we de oude en de nieuwe
 * situatie in de berichten naar de afnemers kunnen versturen.
 * <p/>
 * brp.bedrijfsregel VR00061
 */
@Regels(Regel.VR00061)
public class BepaalViewOpPersonenStap extends AbstractAfnemerVerwerkingStap {

    /**
     * De Constante LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PersoonViewFactory  persoonViewFactory;

    @Override
    @Profiled(tag = "BepaalViewOpPersonenStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp,
            final LeveringsautorisatieVerwerkingContext context, final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        final Map<Integer, Populatie> persoonPopulatieCorrelatie = context.getTeLeverenPersoonIds();

        final List<PersoonHisVolledigView> bijgehoudenPersonenVolledigViews = context.getBijgehoudenPersoonViews();
        final Leveringinformatie leveringAutorisatie = onderwerp.getLeveringinformatie();

        final List<PersoonHisVolledigView> bijgehoudenPersonenViews = new ArrayList<>(bijgehoudenPersonenVolledigViews.size());

        final AdministratieveHandelingModel administratieveHandeling = context.getAdministratieveHandeling();

        for (final Map.Entry<Integer, Populatie> teLeverenPersoonCorrelatie : persoonPopulatieCorrelatie.entrySet()) {
            final Populatie populatie = teLeverenPersoonCorrelatie.getValue();
            final PersoonHisVolledigView persoonHisVolledigView =
                haalPersoonViewMetIdVanContext(bijgehoudenPersonenVolledigViews, teLeverenPersoonCorrelatie.getKey());
            final PersoonHisVolledigView persoonView =
                persoonViewFactory.voegPredikatenToe(persoonHisVolledigView, leveringAutorisatie, populatie, administratieveHandeling);
            bijgehoudenPersonenViews.add(persoonView);
        }

        context.setBijgehoudenPersoonViews(bijgehoudenPersonenViews);

        LOGGER.debug("Views bepaald voor " + bijgehoudenPersonenViews.size() + " personen.");

        return DOORGAAN;
    }

    /**
     * Haalt de persoon his volledig van de context op basis van de persoon id.
     *
     * @param bijgehoudenPersoonViews De bijgehouden persoon views van de context.
     * @param persoonId De persoon id.
     * @return persoonHisVolledigView
     */
    private PersoonHisVolledigView haalPersoonViewMetIdVanContext(final List<PersoonHisVolledigView> bijgehoudenPersoonViews,
        final Integer persoonId)
    {
        for (final PersoonHisVolledigView bijgehoudenPersoonView : bijgehoudenPersoonViews) {
            if (persoonId.equals(bijgehoudenPersoonView.getID())) {
                return bijgehoudenPersoonView;
            }
        }
        return null;
    }
}
