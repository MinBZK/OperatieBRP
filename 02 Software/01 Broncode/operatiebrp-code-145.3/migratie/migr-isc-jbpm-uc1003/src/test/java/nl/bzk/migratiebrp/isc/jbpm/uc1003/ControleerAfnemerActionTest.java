/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ap01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Av01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen.PlaatsenAfnIndTestUtil;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.verwijderen.VerwijderenAfnIndTestUtil;
import nl.bzk.migratiebrp.register.client.PartijService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ControleerAfnemerActionTest {

    private static final String AFNEMER = "059901";
    private static final String INPUT = "input";
    private static final String ONGELDIGE_AFNEMER = "518010";
    private static final String FOUT = "1d. Ongeldige afnemer (beeindigen)";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";
    private static final String CENTRALE_VOORZIENING = "199902";
    private BerichtenDao berichtenDao;
    private PartijService partijRegisterService = Mockito.mock(PartijService.class);
    private PartijRegister partijRegister = Mockito.mock(PartijRegister.class);
    private ControleerAfnemerAction subject;
    private final Partij afnemerPartij = Mockito.mock(Partij.class);
    private final Partij ongeldigeartij = Mockito.mock(Partij.class);
    private final Partij centraleVoorzieningPartij = Mockito.mock(Partij.class);

    @Before
    public void setup() {
        berichtenDao = new InMemoryBerichtenDao();
        Mockito.when(afnemerPartij.isAfnemer()).thenReturn(true);
        Mockito.when(afnemerPartij.isCentraleVoorziening()).thenReturn(false);
        Mockito.when(ongeldigeartij.isAfnemer()).thenReturn(false);
        Mockito.when(ongeldigeartij.isCentraleVoorziening()).thenReturn(false);
        Mockito.when(centraleVoorzieningPartij.isAfnemer()).thenReturn(false);
        Mockito.when(centraleVoorzieningPartij.isCentraleVoorziening()).thenReturn(true);
        Mockito.when(partijRegisterService.geefRegister()).thenReturn(partijRegister);
        Mockito.when(partijRegister.zoekPartijOpPartijCode(AFNEMER)).thenReturn(afnemerPartij);
        Mockito.when(partijRegister.zoekPartijOpPartijCode(ONGELDIGE_AFNEMER)).thenReturn(ongeldigeartij);
        Mockito.when(partijRegister.zoekPartijOpPartijCode(CENTRALE_VOORZIENING)).thenReturn(centraleVoorzieningPartij);
        subject = new ControleerAfnemerAction(berichtenDao, partijRegisterService);
    }

    @Test
    public void testGevonden() {
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(INPUT, berichtenDao.bewaarBericht(ap01Bericht));
        final Map<String, Object> result = subject.execute(parameters);
    }

    @Test
    public void testGevondenVerwijderen() {
        final Av01Bericht av01Bericht = VerwijderenAfnIndTestUtil.maakAv01Bericht(AFNEMER);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(INPUT, berichtenDao.bewaarBericht(av01Bericht));
        final Map<String, Object> result = subject.execute(parameters);
    }

    @Test
    public void testGeenAfnemer() {
        final Av01Bericht av01Bericht = VerwijderenAfnIndTestUtil.maakAv01Bericht(ONGELDIGE_AFNEMER);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(INPUT, berichtenDao.bewaarBericht(av01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertNotNull(result.get(FOUTMELDING_VARIABELE));
        Assert.assertNotNull(result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));
        Assert.assertEquals(result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY), AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_X);
        Assert.assertNotNull(result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals(result.get(SpringActionHandler.TRANSITION_RESULT), FOUT);

    }

    @Test
    public void testNietAanCentraleVoorziening() {
        final Av01Bericht av01Bericht = VerwijderenAfnIndTestUtil.maakAv01Bericht(AFNEMER);
        av01Bericht.setDoelPartijCode(ONGELDIGE_AFNEMER);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(INPUT, berichtenDao.bewaarBericht(av01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertNotNull(result.get(FOUTMELDING_VARIABELE));
        Assert.assertNotNull(result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));
        Assert.assertEquals(result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY), AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_X);
        Assert.assertNotNull(result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals(result.get(SpringActionHandler.TRANSITION_RESULT), FOUT);

    }
}
