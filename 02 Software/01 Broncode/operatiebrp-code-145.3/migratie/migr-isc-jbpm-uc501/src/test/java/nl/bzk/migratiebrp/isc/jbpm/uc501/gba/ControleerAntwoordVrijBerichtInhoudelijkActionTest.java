/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc501.gba;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test ControleerAntwoordVrijBerichtInhoudelijkAction.
 */
@RunWith(MockitoJUnitRunner.class)
public class ControleerAntwoordVrijBerichtInhoudelijkActionTest {

    @Mock
    private BerichtenDao berichtenDao;

    @InjectMocks
    private ControleerAntwoordVrijBerichtInhoudelijkAction subject;

    @Test
    public void correctBericht() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("vrijBerichtAntwoordBericht", 1L);

        final VrijBerichtAntwoordBericht antwoord = new VrijBerichtAntwoordBericht();
        antwoord.setStatus(true);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(antwoord);

        Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertTrue("Resultaat moet leeg zijn", resultaat.isEmpty());
    }

    @Test
    public void incorrectBericht() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("vrijBerichtAntwoordBericht", 1L);

        final VrijBerichtAntwoordBericht antwoord = new VrijBerichtAntwoordBericht();
        antwoord.setStatus(false);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(antwoord);

        Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertTrue("Resultaat moet transitie bevatten", resultaat.containsKey("transition"));
        Assert.assertEquals("Transitie moet functionele fout zijn", "3b. Functionele fout", resultaat.get("transition"));
    }

    @Test(expected = IllegalStateException.class)
    public void geenBerichtDoorgegeven() {
        subject.execute(new HashMap<>());
    }
}
