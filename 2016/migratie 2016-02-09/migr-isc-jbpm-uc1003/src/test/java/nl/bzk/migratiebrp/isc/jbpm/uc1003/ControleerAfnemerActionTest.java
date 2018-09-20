/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ap01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Av01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Autorisatie;
import nl.bzk.migratiebrp.bericht.model.sync.register.AutorisatieRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.AutorisatieRegisterImpl;
import nl.bzk.migratiebrp.isc.jbpm.common.TestAutorisatieService;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen.PlaatsenAfnIndTestUtil;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.verwijderen.VerwijderenAfnIndTestUtil;
import nl.bzk.migratiebrp.register.client.AutorisatieService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class ControleerAfnemerActionTest {

    private static final String AFNEMER = "580001";
    private static final String TRANSITIE = "1d. Ongeldige afnemer (beeindigen)";
    private static final String INPUT = "input";
    private static final String ONGELDIGE_AFNEMER = "518010";
    private ControleerAfnemerAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new ControleerAfnemerAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);

        final List<Autorisatie> autorisaties = new ArrayList<>();
        autorisaties.add(new Autorisatie("580001", 100034, 200001, 200002, null, null));
        autorisaties.add(new Autorisatie("580002", 100035, null, null, 200003, null));
        final AutorisatieRegister autorisatieRegister = new AutorisatieRegisterImpl(autorisaties);
        final AutorisatieService autorisatieService = new TestAutorisatieService(autorisatieRegister);

        ReflectionTestUtils.setField(subject, "autorisatieService", autorisatieService);

    }

    @Test
    public void testNietGevonden() {
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht(ONGELDIGE_AFNEMER);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(INPUT, berichtenDao.bewaarBericht(ap01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(TRANSITIE, result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_X, result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));
    }

    @Test
    public void testGevonden() {
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(INPUT, berichtenDao.bewaarBericht(ap01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(100034, result.get(AfnemersIndicatieJbpmConstants.TOEGANGLEVERINGSAUTORISATIEID_KEY));
        Assert.assertEquals(200001, result.get(AfnemersIndicatieJbpmConstants.PLAATSEN_DIENSTID_KEY));
    }

    @Test
    public void testNietGevondenVerwijderen() {
        final Av01Bericht av01Bericht = VerwijderenAfnIndTestUtil.maakAv01Bericht(ONGELDIGE_AFNEMER);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(INPUT, berichtenDao.bewaarBericht(av01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(TRANSITIE, result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_X, result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));
    }

    @Test
    public void testGevondenVerwijderen() {
        final Av01Bericht av01Bericht = VerwijderenAfnIndTestUtil.maakAv01Bericht(AFNEMER);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(INPUT, berichtenDao.bewaarBericht(av01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(100034, result.get(AfnemersIndicatieJbpmConstants.TOEGANGLEVERINGSAUTORISATIEID_KEY));
        Assert.assertEquals(200002, result.get(AfnemersIndicatieJbpmConstants.VERWIJDEREN_DIENSTID_KEY));
    }

}
