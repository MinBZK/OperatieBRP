/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.stap;

import static junit.framework.Assert.assertTrue;

import nl.bzk.brp.levering.mutatieverwerker.AdministratieveHandelingTestBouwer;
import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

public class LogAdministratieveHandelingStapTest {

    private LogAdministratieveHandelingStap logAdministratieveHandelingStap = new LogAdministratieveHandelingStap();

    @Before
    public void setup() {
        BasicConfigurator.configure();
    }

    @Test
    public void testVoerStapUit() {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContext();
        final AdministratieveHandelingModel administratieveHandelingModel =
                AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

        context.setHuidigeAdministratieveHandeling(administratieveHandelingModel);
        final AdministratieveHandelingVerwerkingResultaat administratieveHandelingVerwerkingResultaat =
                new AdministratieveHandelingVerwerkingResultaat();

        logAdministratieveHandelingStap.voerStapUit(new AdministratieveHandelingMutatie(
                administratieveHandelingModel.getID(), null), context,
                administratieveHandelingVerwerkingResultaat);

        assertTrue(administratieveHandelingVerwerkingResultaat.isSuccesvol());
    }

}
