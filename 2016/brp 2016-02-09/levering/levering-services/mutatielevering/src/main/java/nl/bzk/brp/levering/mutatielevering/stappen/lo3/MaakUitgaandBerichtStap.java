/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.lo3;

import java.util.ArrayList;
import java.util.List;

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
 * Maak uitgaand bericht.
 */
public class MaakUitgaandBerichtStap extends AbstractAfnemerVerwerkingStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    @Profiled(tag = "MaakUitgaandBerichtStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp,
            final LeveringsautorisatieVerwerkingContext context, final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        final List<String> result = new ArrayList<>();

        for (final SynchronisatieBericht synchronisatieBericht : context.getLeveringBerichten()) {
            result.add(((Bericht) synchronisatieBericht).maakUitgaandBericht());
        }

        context.setUitgaandePlatteTekstBerichten(result);

        LOGGER.debug(result.size() + " berichten gemaakt.");

        return DOORGAAN;
    }
}
