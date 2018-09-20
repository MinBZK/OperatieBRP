/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen;

import nl.bzk.brp.business.stappen.verwerker.AbstractVerwerker;
import nl.bzk.brp.business.stappen.verwerker.StappenVerwerker;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.AfnemerVerwerkingStap;

/**
 * Dit is de klasse die de stappenverwerking voor de afnemers verzorgd.
 */
public class AfnemerStappenVerwerker extends AbstractVerwerker<LeveringautorisatieStappenOnderwerp, LeveringsautorisatieVerwerkingContext, LeveringautorisatieVerwerkingResultaat,
    AfnemerVerwerkingStap> implements StappenVerwerker<LeveringautorisatieStappenOnderwerp, LeveringsautorisatieVerwerkingContext, LeveringautorisatieVerwerkingResultaat>
{
    @Override
    protected final LeveringautorisatieVerwerkingResultaat creeerResultaat(final LeveringautorisatieStappenOnderwerp onderwerp, final LeveringsautorisatieVerwerkingContext context) {
        return new LeveringautorisatieVerwerkingResultaat(null);
    }
}
