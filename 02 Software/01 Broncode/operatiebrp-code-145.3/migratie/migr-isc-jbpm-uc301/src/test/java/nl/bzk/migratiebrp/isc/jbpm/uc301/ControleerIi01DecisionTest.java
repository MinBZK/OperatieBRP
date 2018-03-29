/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.register.Rol;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.isc.jbpm.common.TestPartijService;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.register.client.PartijService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ControleerIi01DecisionTest {

    private ControleerIi01Decision subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        berichtenDao = new InMemoryBerichtenDao();

        final List<Partij> partijen = new ArrayList<>();
        partijen.add(new Partij("580001", "0001", intToDate(2009_01_01), Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("580002", "0002", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));

        final PartijService partijService = new TestPartijService(new PartijRegisterImpl(partijen));

        subject = new ControleerIi01Decision(berichtenDao, partijService);
    }

    private static Date intToDate(final int date) {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(Integer.toString(date));
        } catch (final ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    public void testOk() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronPartijCode("580002");
        ii01Bericht.setDoelPartijCode("580001");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER, "2342342432");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));

        Assert.assertEquals(null, subject.execute(parameters));
    }

    @Test
    public void testStelselNokBronNull() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronPartijCode("580003");
        ii01Bericht.setDoelPartijCode("580001");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER, "2342342432");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));

        Assert.assertEquals("2b. fout (nieuwe bijhouder is BRP)", subject.execute(parameters));
    }

    @Test
    public void testStelselNokBronBrp() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronPartijCode("580001");
        ii01Bericht.setDoelPartijCode("580001");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER, "2342342432");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));

        Assert.assertEquals("2b. fout (nieuwe bijhouder is BRP)", subject.execute(parameters));
    }

    @Test
    public void testStelselNokDoelNull() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronPartijCode("580002");
        ii01Bericht.setDoelPartijCode("580003");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER, "2342342432");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));

        Assert.assertEquals("2b. fout (nieuwe bijhouder is BRP)", subject.execute(parameters));
    }

    @Test
    public void testStelselNokDoelGba() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronPartijCode("580002");
        ii01Bericht.setDoelPartijCode("580002");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER, "2342342432");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));

        Assert.assertEquals("2b. fout (nieuwe bijhouder is BRP)", subject.execute(parameters));
    }

    @Test
    public void testCriteriaNok() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronPartijCode("580002");
        ii01Bericht.setDoelPartijCode("580001");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));

        Assert.assertEquals("2c. fout (zoekcriteria voldoen niet aan eisen)", subject.execute(parameters));
    }
}
