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
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen.PlaatsenAfnIndTestUtil;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.verwijderen.VerwijderenAfnIndTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class ControleerPersoonActionTest {

    private static final String TRANSITIE = "3d. Controle persoon mislukt (beeindigen)";
    private static final String ZOEK_PERSOON_ANTWOORD = "zoekPersoonAntwoord";
    private static final String INPUT = "input";
    private static final String A_NUMMER_PERSOON_1 = "1234567890";
    private static final String BIJHOUDINGS_GEMEENTE = "0518";
    private static final String AFNEMER = "518010";
    private ControleerPersoonAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new ControleerPersoonAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testGeenPersoonGevonden() {
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoord = new ZoekPersoonAntwoordBericht();
        zoekPersoonAntwoord.setStatus(StatusType.OK);
        zoekPersoonAntwoord.setResultaat(ZoekPersoonResultaatType.GEEN);

        final Map<String, Object> parameters = new HashMap<>();
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER);
        parameters.put(INPUT, berichtenDao.bewaarBericht(ap01Bericht));
        parameters.put(ZOEK_PERSOON_ANTWOORD, berichtenDao.bewaarBericht(zoekPersoonAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(TRANSITIE, result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_G, result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));

        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.PERSOONID_KEY));
        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY));
    }

    @Test
    public void testMeerderePersonenGevonden() {
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoord = new ZoekPersoonAntwoordBericht();
        zoekPersoonAntwoord.setStatus(StatusType.OK);
        zoekPersoonAntwoord.setResultaat(ZoekPersoonResultaatType.MEERDERE);

        final Map<String, Object> parameters = new HashMap<>();
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER);
        parameters.put(INPUT, berichtenDao.bewaarBericht(ap01Bericht));
        parameters.put(ZOEK_PERSOON_ANTWOORD, berichtenDao.bewaarBericht(zoekPersoonAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(TRANSITIE, result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_U, result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));

        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.PERSOONID_KEY));
        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY));
    }

    @Test
    public void testEenPersoonGevonden() {
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoord = new ZoekPersoonAntwoordBericht();
        zoekPersoonAntwoord.setStatus(StatusType.OK);
        zoekPersoonAntwoord.setResultaat(ZoekPersoonResultaatType.GEVONDEN);
        zoekPersoonAntwoord.setAnummer(A_NUMMER_PERSOON_1);
        zoekPersoonAntwoord.setPersoonId(1);
        zoekPersoonAntwoord.setGemeente(BIJHOUDINGS_GEMEENTE);

        final Map<String, Object> parameters = new HashMap<>();
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER);
        parameters.put(INPUT, berichtenDao.bewaarBericht(ap01Bericht));
        parameters.put(ZOEK_PERSOON_ANTWOORD, berichtenDao.bewaarBericht(zoekPersoonAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(null, result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));
        Assert.assertEquals(Integer.valueOf(1), result.get(AfnemersIndicatieJbpmConstants.PERSOONID_KEY));
        Assert.assertEquals(A_NUMMER_PERSOON_1, result.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY));
    }

    @Test
    public void testEenPersoonGevondenVerhuisd() {
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoord = new ZoekPersoonAntwoordBericht();
        zoekPersoonAntwoord.setStatus(StatusType.OK);
        zoekPersoonAntwoord.setResultaat(ZoekPersoonResultaatType.GEVONDEN);
        zoekPersoonAntwoord.setAnummer(A_NUMMER_PERSOON_1);
        zoekPersoonAntwoord.setPersoonId(1);
        zoekPersoonAntwoord.setGemeente("0123");

        final Map<String, Object> parameters = new HashMap<>();
        final Ap01Bericht ap01Bericht = PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER);
        parameters.put(INPUT, berichtenDao.bewaarBericht(ap01Bericht));
        parameters.put(ZOEK_PERSOON_ANTWOORD, berichtenDao.bewaarBericht(zoekPersoonAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        // Verhuizen zou geen invloed moeten hebben op het plaatsen.
        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));
        Assert.assertEquals(A_NUMMER_PERSOON_1, result.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY));
    }

    @Test
    public void testGeenPersoonGevondenVerwijderen() {
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoord = new ZoekPersoonAntwoordBericht();
        zoekPersoonAntwoord.setStatus(StatusType.OK);
        zoekPersoonAntwoord.setResultaat(ZoekPersoonResultaatType.GEEN);

        final Map<String, Object> parameters = new HashMap<>();
        final Av01Bericht av01Bericht = VerwijderenAfnIndTestUtil.maakAv01Bericht(AFNEMER);
        parameters.put(INPUT, berichtenDao.bewaarBericht(av01Bericht));
        parameters.put(ZOEK_PERSOON_ANTWOORD, berichtenDao.bewaarBericht(zoekPersoonAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(TRANSITIE, result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_G, result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));

        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.PERSOONID_KEY));
        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY));
    }

    @Test
    public void testMeerderePersonenGevondenVerwijderen() {
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoord = new ZoekPersoonAntwoordBericht();
        zoekPersoonAntwoord.setStatus(StatusType.OK);
        zoekPersoonAntwoord.setResultaat(ZoekPersoonResultaatType.MEERDERE);

        final Map<String, Object> parameters = new HashMap<>();
        final Av01Bericht av01Bericht = VerwijderenAfnIndTestUtil.maakAv01Bericht(AFNEMER);
        parameters.put(INPUT, berichtenDao.bewaarBericht(av01Bericht));
        parameters.put(ZOEK_PERSOON_ANTWOORD, berichtenDao.bewaarBericht(zoekPersoonAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(TRANSITIE, result.get(SpringActionHandler.TRANSITION_RESULT));
        Assert.assertEquals(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_U, result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));

        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.PERSOONID_KEY));
        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY));
    }

    @Test
    public void testEenPersoonGevondenVerwijderen() {
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoord = new ZoekPersoonAntwoordBericht();
        zoekPersoonAntwoord.setStatus(StatusType.OK);
        zoekPersoonAntwoord.setResultaat(ZoekPersoonResultaatType.GEVONDEN);
        zoekPersoonAntwoord.setAnummer(A_NUMMER_PERSOON_1);
        zoekPersoonAntwoord.setPersoonId(1);
        zoekPersoonAntwoord.setGemeente(BIJHOUDINGS_GEMEENTE);

        final Map<String, Object> parameters = new HashMap<>();
        final Av01Bericht av01Bericht = VerwijderenAfnIndTestUtil.maakAv01Bericht(AFNEMER);
        parameters.put(INPUT, berichtenDao.bewaarBericht(av01Bericht));
        parameters.put(ZOEK_PERSOON_ANTWOORD, berichtenDao.bewaarBericht(zoekPersoonAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(null, result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));
        Assert.assertEquals(Integer.valueOf(1), result.get(AfnemersIndicatieJbpmConstants.PERSOONID_KEY));
        Assert.assertEquals(A_NUMMER_PERSOON_1, result.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY));
    }

    @Test
    public void testEenPersoonGevondenVerhuisdVerwijderen() {
        // Verhuizen zou geen invloed moeten hebben op het plaatsen.
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoord = new ZoekPersoonAntwoordBericht();
        zoekPersoonAntwoord.setStatus(StatusType.OK);
        zoekPersoonAntwoord.setResultaat(ZoekPersoonResultaatType.GEVONDEN);
        zoekPersoonAntwoord.setAnummer(A_NUMMER_PERSOON_1);
        zoekPersoonAntwoord.setPersoonId(1);
        zoekPersoonAntwoord.setGemeente("0123");

        final Map<String, Object> parameters = new HashMap<>();
        final Av01Bericht av01Bericht = VerwijderenAfnIndTestUtil.maakAv01Bericht(AFNEMER);
        parameters.put(INPUT, berichtenDao.bewaarBericht(av01Bericht));
        parameters.put(ZOEK_PERSOON_ANTWOORD, berichtenDao.bewaarBericht(zoekPersoonAntwoord));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertNull(result.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY));
        Assert.assertEquals(A_NUMMER_PERSOON_1, result.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY));
    }
}
