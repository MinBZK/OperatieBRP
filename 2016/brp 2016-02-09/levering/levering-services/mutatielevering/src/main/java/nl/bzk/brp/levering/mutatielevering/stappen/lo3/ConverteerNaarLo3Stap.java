/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.lo3;

import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.mutatielevering.Lo3LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.perf4j.aop.Profiled;


/**
 * Bepaalt het maximaal te leveren LO3 bericht (door de conversie van BRP naar LO3 uit te voeren).
 */
public class ConverteerNaarLo3Stap extends AbstractAfnemerVerwerkingStap {

    /**
     * De Constante LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    @Profiled(tag = "ConverteerNaarLo3Stap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp,
        final LeveringsautorisatieVerwerkingContext context, final LeveringautorisatieVerwerkingResultaat resultaat)
    {

        boolean stapResultaat = DOORGAAN;

        if (context instanceof Lo3LeveringsautorisatieVerwerkingContext) {
            final Lo3LeveringsautorisatieVerwerkingContext lo3LeveringsautorisatieVerwerking =
                (Lo3LeveringsautorisatieVerwerkingContext) context;
            for (final SynchronisatieBericht bericht : context.getLeveringBerichten()) {
                ((Bericht) bericht).converteerNaarLo3(lo3LeveringsautorisatieVerwerking.getConversieCache());
            }

        } else {
            LOGGER.error("Ongeldige context voor ConverteerNaarLo3Stap. "
                + "Instantie van Lo3LeveringsautorisatieVerwerkingContext verwacht.");
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001,
                "Bepalen maximale bericht (en converteren naar lo3) is mislukt."));
            stapResultaat = STOPPEN;
        }

        return stapResultaat;
    }

}
