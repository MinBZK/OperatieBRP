/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import static org.junit.Assert.assertTrue;

import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContextImpl;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import support.AdministratieveHandelingTestBouwer;

public class LogAdministratieveHandelingStapTest {

    private final LogAdministratieveHandelingStap logAdministratieveHandelingStap = new LogAdministratieveHandelingStap();

    @Before
    public final void setup() {
        BasicConfigurator.configure();
    }

    @Test
    public final void testVoerStapUit() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingModel administratieveHandelingModel = AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

        context.setHuidigeAdministratieveHandeling(administratieveHandelingModel);
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat = new AdministratieveHandelingVerwerkingResultaat();

        final boolean resultaat = logAdministratieveHandelingStap.voerStapUit(new AdministratieveHandelingMutatie(
            administratieveHandelingModel.getID()), context, administratieveHandelingVerwerkingResultaat);

        assertTrue(administratieveHandelingVerwerkingResultaat.isSuccesvol());
        Assert.assertEquals(AbstractStap.DOORGAAN, resultaat);
    }

}
