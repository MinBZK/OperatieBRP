/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004.adres;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test ControleerAntwoordAdresInhoudelijkAction.
 */
@RunWith(MockitoJUnitRunner.class)
public class ControleerAntwoordAdresInhoudelijkActionTest {

    @Mock
    private BerichtenDao berichtenDao;

    @InjectMocks
    private ControleerAntwoordAdresInhoudelijkAction subject;

    @Test
    public void correctBerichtMetGevondenResultaat() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("zoekPersonenOpAdresAntwoord", 1L);

        final AdHocZoekPersonenOpAdresAntwoordBericht antwoord = new AdHocZoekPersonenOpAdresAntwoordBericht();
        antwoord.setInhoud("persoonslijst");

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(antwoord);

        Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertTrue("Resultaat moet leeg zijn", resultaat.isEmpty());
    }

    @Test
    public void correctBerichtMetFoutreden() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("zoekPersonenOpAdresAntwoord", 1L);

        final AdHocZoekPersonenOpAdresAntwoordBericht antwoord = new AdHocZoekPersonenOpAdresAntwoordBericht();
        antwoord.setFoutreden(AdHocZoekAntwoordFoutReden.X);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(antwoord);

        Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertTrue("Resultaat moet transitie bevatten", resultaat.containsKey("transition"));
        Assert.assertEquals("Transitie moet functionele fout zijn", "3b. functionele fout", resultaat.get("transition"));
        Assert.assertTrue("Resultaat moet foutreden bevatten", resultaat.containsKey("xf01foutreden"));
        Assert.assertEquals("Foutreden moet X zijn", "X", resultaat.get("xf01foutreden"));
    }

    @Test
    public void correctBerichtMetTechnischeFoutreden() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("zoekPersonenOpAdresAntwoord", 1L);

        final AdHocZoekPersonenOpAdresAntwoordBericht antwoord = new AdHocZoekPersonenOpAdresAntwoordBericht();
        antwoord.setFoutreden(AdHocZoekAntwoordFoutReden.F);

        Mockito.when(berichtenDao.leesBericht(1L)).thenReturn(antwoord);

        Map<String, Object> resultaat = subject.execute(parameters);
        Assert.assertTrue("Resultaat moet transitie bevatten", resultaat.containsKey("transition"));
        Assert.assertEquals("Transitie moet functionele fout zijn", "3a. Technische fout", resultaat.get("transition"));
    }

    @Test(expected = IllegalStateException.class)
    public void geenBerichtDoorgegeven() {
        subject.execute(new HashMap<>());
    }
}
