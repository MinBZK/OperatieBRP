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
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen.PlaatsenAfnIndTestUtil;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.verwijderen.VerwijderenAfnIndTestUtil;
import org.junit.Assert;
import org.junit.Test;

public class ControleerPersoonActionTest {

    private static final String TRANSITIE = "3d. Controle persoon mislukt (beeindigen)";
    private static final String ZOEK_PERSOON_ANTWOORD = "adHocZoekPersoonAntwoord";
    private static final String INPUT = "input";
    private static final String A_NUMMER_PERSOON_1 = "1234567890";
    private static final String BSN_GEVONDEN_PERSOON = "123456789";
    private static final String AFNEMER = "518010";
    private static final String GEVONDEN_PERSOON_ALS_HA01 = "00000000Ha01A000000000003801031011001012345678900120009123456789";

    private BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private ControleerPersoonAction subject = new ControleerPersoonAction(berichtenDao);

    @Test
    public void testGeenPersoonGevonden() {
        final AdHocZoekPersoonAntwoordBericht adHocZoekPersoonAntwoordBericht = new AdHocZoekPersoonAntwoordBericht();
        adHocZoekPersoonAntwoordBericht.setFoutreden(AdHocZoekAntwoordFoutReden.G);

        final Map<String, Object> parameters = new HashMap<>();
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER);
        parameters.put(INPUT, berichtenDao.bewaarBericht(ap01Bericht));
        parameters.put(ZOEK_PERSOON_ANTWOORD, berichtenDao.bewaarBericht(adHocZoekPersoonAntwoordBericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(TRANSITIE, result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_G, result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));

        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.PERSOON_BSN));
        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY));
    }

    @Test
    public void testMeerderePersonenGevonden() {
        final AdHocZoekPersoonAntwoordBericht adHocZoekPersoonAntwoordBericht = new AdHocZoekPersoonAntwoordBericht();
        adHocZoekPersoonAntwoordBericht.setFoutreden(AdHocZoekAntwoordFoutReden.U);

        final Map<String, Object> parameters = new HashMap<>();
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER);
        parameters.put(INPUT, berichtenDao.bewaarBericht(ap01Bericht));
        parameters.put(ZOEK_PERSOON_ANTWOORD, berichtenDao.bewaarBericht(adHocZoekPersoonAntwoordBericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(TRANSITIE, result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_U, result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));

        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.PERSOON_BSN));
        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY));
    }

    @Test
    public void testEenPersoonGevonden() {
        final AdHocZoekPersoonAntwoordBericht adHocZoekPersoonAntwoordBericht = new AdHocZoekPersoonAntwoordBericht();
        adHocZoekPersoonAntwoordBericht.setInhoud(GEVONDEN_PERSOON_ALS_HA01);

        final Map<String, Object> parameters = new HashMap<>();
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER, Long.valueOf(A_NUMMER_PERSOON_1));
        parameters.put(INPUT, berichtenDao.bewaarBericht(ap01Bericht));
        parameters.put(ZOEK_PERSOON_ANTWOORD, berichtenDao.bewaarBericht(adHocZoekPersoonAntwoordBericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));
        Assert.assertEquals(BSN_GEVONDEN_PERSOON, result.get(AfnemersIndicatieJbpmConstants.PERSOON_BSN));
        Assert.assertEquals(A_NUMMER_PERSOON_1, result.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY));
    }

    @Test
    public void testGeenPersoonGevondenVerwijderen() {
        final AdHocZoekPersoonAntwoordBericht adHocZoekPersoonAntwoordBericht = new AdHocZoekPersoonAntwoordBericht();
        adHocZoekPersoonAntwoordBericht.setFoutreden(AdHocZoekAntwoordFoutReden.G);

        final Map<String, Object> parameters = new HashMap<>();
        final Av01Bericht av01Bericht = VerwijderenAfnIndTestUtil.maakAv01Bericht(AFNEMER);
        parameters.put(INPUT, berichtenDao.bewaarBericht(av01Bericht));
        parameters.put(ZOEK_PERSOON_ANTWOORD, berichtenDao.bewaarBericht(adHocZoekPersoonAntwoordBericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(TRANSITIE, result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_G, result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));

        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.PERSOON_BSN));
        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY));
    }

    @Test
    public void testMeerderePersonenGevondenVerwijderen() {
        final AdHocZoekPersoonAntwoordBericht adHocZoekPersoonAntwoordBericht = new AdHocZoekPersoonAntwoordBericht();
        adHocZoekPersoonAntwoordBericht.setFoutreden(AdHocZoekAntwoordFoutReden.U);

        final Map<String, Object> parameters = new HashMap<>();
        final Av01Bericht av01Bericht = VerwijderenAfnIndTestUtil.maakAv01Bericht(AFNEMER);
        parameters.put(INPUT, berichtenDao.bewaarBericht(av01Bericht));
        parameters.put(ZOEK_PERSOON_ANTWOORD, berichtenDao.bewaarBericht(adHocZoekPersoonAntwoordBericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(TRANSITIE, result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_U, result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));

        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.PERSOON_BSN));
        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY));
    }

    @Test
    public void testEenPersoonGevondenVerwijderen() {
        final AdHocZoekPersoonAntwoordBericht adHocZoekPersoonAntwoordBericht = new AdHocZoekPersoonAntwoordBericht();
        adHocZoekPersoonAntwoordBericht.setInhoud(GEVONDEN_PERSOON_ALS_HA01);

        final Map<String, Object> parameters = new HashMap<>();
        final Av01Bericht av01Bericht = VerwijderenAfnIndTestUtil.maakAv01Bericht(AFNEMER, A_NUMMER_PERSOON_1);
        parameters.put(INPUT, berichtenDao.bewaarBericht(av01Bericht));
        parameters.put(ZOEK_PERSOON_ANTWOORD, berichtenDao.bewaarBericht(adHocZoekPersoonAntwoordBericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(null, result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));
        Assert.assertEquals(BSN_GEVONDEN_PERSOON, result.get(AfnemersIndicatieJbpmConstants.PERSOON_BSN));
        Assert.assertEquals(A_NUMMER_PERSOON_1, result.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY));
    }
}
