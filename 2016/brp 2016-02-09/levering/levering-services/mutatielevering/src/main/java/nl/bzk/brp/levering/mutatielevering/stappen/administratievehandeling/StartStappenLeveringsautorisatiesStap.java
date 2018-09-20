/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.mutatielevering.service.bericht.AfleverService;
import nl.bzk.brp.levering.mutatielevering.stappen.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.perf4j.aop.Profiled;


/**
 * Deze stap start het stappenmechanisme dat voor iedere leveringsautorisatie een levering doet als dit nodig is.
 */
public class StartStappenLeveringsautorisatiesStap extends AbstractAdministratieveHandelingVerwerkingStap {

    /**
     * De Constante LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    @Named(value = "brpAfleverService")
    private AfleverService brpAfleverService;

    @Inject
    @Named(value = "LO3AfleverService")
    private AfleverService lo3AfleverService;

    @Override
    @Profiled(tag = "StartStappenLeveringsautorisatiesStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp,
        final AdministratieveHandelingVerwerkingContext context,
        final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        if (brpAfleverService != null) {
            LOGGER.debug("Brp levering");
            final LeveringautorisatieVerwerkingResultaat verwerkingResultaat =
                brpAfleverService.leverBerichten(context.getHuidigeAdministratieveHandeling(), context.getBijgehoudenPersonenVolledig(),
                    context.getLeveringPopulatieMap(), context.getBijgehoudenPersonenAttributenMap(),
                    context.getPersoonOnderzoekenMap());
            resultaat.voegMeldingenToe(verwerkingResultaat.getMeldingen());
        }

        if (lo3AfleverService != null) {
            LOGGER.debug("Lo3 levering");
            final LeveringautorisatieVerwerkingResultaat verwerkingResultaat =
                lo3AfleverService.leverBerichten(context.getHuidigeAdministratieveHandeling(),
                    context.getBijgehoudenPersonenVolledig(), context.getLeveringPopulatieMap(), null, null);
            resultaat.voegMeldingenToe(verwerkingResultaat.getMeldingen());
        }

        if (resultaat.isSuccesvol()) {
            LOGGER.debug("Verwerking leveringsautorisatiesstappen is geslaagd.");
            return DOORGAAN;
        }

        LOGGER.debug("Verwerking leveringsautorisatiesstappen is mislukt.");
        return STOPPEN;
    }
}
