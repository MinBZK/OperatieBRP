/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.lo3;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.perf4j.aop.Profiled;


/**
 * LO3 levert voor elke bijgehouden persoon een bericht.
 */
public class MaakBerichtenStap extends AbstractAfnemerVerwerkingStap {

    /**
     * De Constante LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private BerichtFactory      berichtFactory;

    @Override
    @Profiled(tag = "BepaalBerichtenStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp,
            final LeveringsautorisatieVerwerkingContext context, final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        final List<PersoonHisVolledig> personen = context.getBijgehoudenPersonenVolledig();
        final Leveringinformatie leveringAutorisatie = onderwerp.getLeveringinformatie();
        final Map<Integer, Populatie> populatieMap = context.getTeLeverenPersoonIds();
        final AdministratieveHandelingModel administratieveHandeling = context.getAdministratieveHandeling();

        final List<SynchronisatieBericht> berichten =
                berichtFactory.maakBerichten(personen, leveringAutorisatie, populatieMap, administratieveHandeling);

        context.setLeveringBerichten(berichten);

        if (!berichten.isEmpty()) {
            // fix voor probleem met ZetBerichtOpQueue
            context.setBijgehoudenPersoonViews(Arrays.asList(new PersoonHisVolledigView(personen.get(0),
                    new HistorieVanafPredikaat(null))));
        }

        LOGGER.debug(berichten.size() + " berichten gemaakt.");

        return DOORGAAN;
    }
}
