/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen;

import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AfnemerStappenVerwerkerTest {

    @Mock
    private LeveringautorisatieStappenOnderwerp leveringautorisatieStappenOnderwerp;

    @Mock
    private LeveringautorisatieVerwerkingResultaat afnemerVerwerkingResultaat;

    private final AfnemerStappenVerwerker afnemerStappenVerwerker = new AfnemerStappenVerwerker();

    @Test
    public final void testCreeerResultaat() {
        final LeveringautorisatieVerwerkingResultaat leveringautorisatieVerwerkingResultaat =
            afnemerStappenVerwerker.creeerResultaat(leveringautorisatieStappenOnderwerp, new LeveringsautorisatieVerwerkingContextImpl(null, null, null, null,
                null));

        Assert.assertNotNull(leveringautorisatieVerwerkingResultaat);
        Assert.assertEquals(leveringautorisatieVerwerkingResultaat.getMeldingen().size(), 0);
    }

}
