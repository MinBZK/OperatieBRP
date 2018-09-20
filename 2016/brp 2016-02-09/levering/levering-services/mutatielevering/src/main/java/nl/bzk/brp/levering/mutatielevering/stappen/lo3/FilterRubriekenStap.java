/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.lo3;

import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.gba.dataaccess.Lo3FilterRubriekRepository;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import org.perf4j.aop.Profiled;

/**
 * Stap om lo3 leveringsberichten te filtern obv de filterrubrieken zoals aangegeven bij de leveringsautorisatie.
 */
public class FilterRubriekenStap extends AbstractAfnemerVerwerkingStap {

    /**
     * De Constante LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private Lo3FilterRubriekRepository lo3FilterRubriekRepository;

    @Override
    @Profiled(tag = "FilterBerichtStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(
        final LeveringautorisatieStappenOnderwerp onderwerp,
        final LeveringsautorisatieVerwerkingContext context,
        final LeveringautorisatieVerwerkingResultaat resultaat)
    {

        boolean stapResultaat = DOORGAAN;

        // Bepaal Lo3 rubrieken voor filtering
        final List<String> lo3Filterrubrieken =
                lo3FilterRubriekRepository.haalLo3FilterRubriekenVoorDienstbundel(onderwerp.getLeveringinformatie().getDienst().getDienstbundel());

        final Iterator<SynchronisatieBericht> berichtenIterator = context.getLeveringBerichten().iterator();
        while (berichtenIterator.hasNext()) {
            final Bericht bericht = (Bericht) berichtenIterator.next();

            // Filter bericht
            if (!bericht.filterRubrieken(lo3Filterrubrieken)) {
                berichtenIterator.remove();
                LOGGER.info(
                    "Uitgaand LO3-bericht gebouwd voor administratieve handeling [{}] "
                            + "bevat geen inhoud. Uitgaand LO3-bericht wordt daarom niet verzonden.",
                    context.getAdministratieveHandeling().getID());
            }
        }

        // Controleer dat er uberhaupt nog te leveren berichten zjin
        if (context.getLeveringBerichten().isEmpty()) {
            LOGGER.info(
                "Geen uitgaande berichten meer om te verzenden voor administratieve handeling [{}]. "
                        + "Verwerking wordt gestopt.",
                context.getAdministratieveHandeling().getID());
            stapResultaat = STOPPEN;
        }

        return stapResultaat;
    }

}
