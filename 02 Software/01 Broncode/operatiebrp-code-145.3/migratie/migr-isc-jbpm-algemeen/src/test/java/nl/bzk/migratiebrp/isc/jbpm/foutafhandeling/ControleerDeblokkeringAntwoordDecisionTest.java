/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Test;

public class ControleerDeblokkeringAntwoordDecisionTest {

    private BerichtenDao berichtenDao  = new InMemoryBerichtenDao();
    private ControleerDeblokkeringAntwoordDecision subject  = new ControleerDeblokkeringAntwoordDecision(berichtenDao);

    @Test
    public void testOk() {
        final DeblokkeringAntwoordBericht antwoord = new DeblokkeringAntwoordBericht();
        antwoord.setStatus(StatusType.OK);

        final Map<String, Object> params = new HashMap<>();
        params.put(FoutafhandelingConstants.BERICHT_ANTWOORD_DEBLOKKERING, berichtenDao.bewaarBericht(antwoord));

        Assert.assertEquals(null, subject.execute(params));
    }

    @Test
    public void testNok() {
        final DeblokkeringAntwoordBericht antwoord = new DeblokkeringAntwoordBericht();
        antwoord.setStatus(StatusType.FOUT);

        final Map<String, Object> params = new HashMap<>();
        params.put(FoutafhandelingConstants.BERICHT_ANTWOORD_DEBLOKKERING, berichtenDao.bewaarBericht(antwoord));

        Assert.assertEquals("fout", subject.execute(params));
    }
}
