/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.register.client.GemeenteService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 */
public class ControleerTb02BerichtActionTest {

    private final Tb02Factory tb02Factory = new Tb02Factory();
    private ControleerTb02BerichtAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setUp() throws Exception {
        subject = new ControleerTb02BerichtAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
        GemeenteService gemeenteService = tb02Factory.getGemeenteRegister();
        ReflectionTestUtils.setField(subject, "gemeenteRegisterService", gemeenteService);
    }

    @Test
    public void testExecuteMetCorrecteRelatieSluiting() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING)));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertTrue("Resultaatmap moet leeg zijn", result.isEmpty());
    }

    @Test
    public void testExecuteMetCorrecteRelatieOntbinding() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(tb02Factory.maakTb02Bericht(Tb02Factory.Soort.ONTBINDING)));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertTrue("Resultaatmap moet leeg zijn", result.isEmpty());
    }

    @Test
    public void testExecuteMetInCorrecteRelatieOntbinding() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(tb02Factory.maakTb02Bericht(Tb02Factory.Soort.ONTBINDING_INCORRECT)));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertFalse("Resultaatmap moet niet leeg zijn", result.isEmpty());
    }

    @Test
    public void testExecuteMetCorrecteRelatieOmzetting() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(tb02Factory.maakTb02Bericht(Tb02Factory.Soort.OMZETTING)));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertTrue("Resultaatmap moet leeg zijn", result.isEmpty());
    }

    @Test
    public void testExecuteMetInCorrecteRelatieOmzetting() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(tb02Factory.maakTb02Bericht(Tb02Factory.Soort.OMZETTING_INCORRECT)));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertFalse("Resultaatmap moet niet leeg zijn", result.isEmpty());
    }

    @Test
    public void testExecuteMetOnbekendeAkte() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "9QQ5432");
        parameters.put("input", berichtenDao.bewaarBericht(bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void testExecuteMetFouteCategorieen() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QB5432");
        parameters.put("input", berichtenDao.bewaarBericht(bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void testExecuteMetFouteBronGemeente() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QA5432");
        bericht.setBronGemeente("2222");
        parameters.put("input", berichtenDao.bewaarBericht(bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void testExecuteMetGewijzigdeCategorieen() throws Exception {
        final Map<String, Object> parameters = new HashMap<>();
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.OMZETTING_INCORRECT);
        bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, "3QH5432");
        parameters.put("input", berichtenDao.bewaarBericht(bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void testToonTb02Bericht() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING);
        System.out.println(bericht.format());
    }
}
