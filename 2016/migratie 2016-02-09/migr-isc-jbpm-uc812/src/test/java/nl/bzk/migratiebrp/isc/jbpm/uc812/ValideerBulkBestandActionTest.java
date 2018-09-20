/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc812;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc812Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Gemeente;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegisterImpl;
import nl.bzk.migratiebrp.isc.jbpm.common.TestGemeenteService;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.register.client.GemeenteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test de happy flow.
 */
public class ValideerBulkBestandActionTest {

    private static final String FOUTPAD = "3b. Fout";
    private static final String PARAMETER_INPUT = "input";
    private static final String PARAMETER_FOUTAFHANDELINGMELDING = "foutafhandelingFoutmelding";
    private static final String PARAMETER_FOUTPAD = SpringActionHandler.TRANSITION_RESULT;

    private ValideerBulkBestandAction service;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        service = new ValideerBulkBestandAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(service, "berichtenDao", berichtenDao);

        final List<Gemeente> gemeenten = new ArrayList<>();
        gemeenten.add(new Gemeente("0599", "580599", null));
        gemeenten.add(new Gemeente("0699", "580699", intToDate(20090101)));
        gemeenten.add(new Gemeente("0717", "580717", null));

        final GemeenteService gemeenteService = new TestGemeenteService(new GemeenteRegisterImpl(gemeenten));

        ReflectionTestUtils.setField(service, "gemeenteService", gemeenteService);
    }

    private static Date intToDate(final int date) {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(Integer.toString(date));
        } catch (final ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    public void happyFlow() throws Exception {

        final String aNummer = "1607306145";
        final String doelGemeente = "0599";

        final String bulkSynchronisatievraag = doelGemeente + "," + aNummer + "\n";

        final Uc812Bericht uc812Bericht = new Uc812Bericht();
        uc812Bericht.setBulkSynchronisatievraag(bulkSynchronisatievraag);
        final Long uc812BerichtId = berichtenDao.bewaarBericht(uc812Bericht);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(PARAMETER_INPUT, uc812BerichtId);

        Assert.assertNull(service.execute(parameters));
    }

    @Test
    public void badFlow() throws Exception {

        final String aNummer = "160730615";
        final String doelGemeente = "0599";

        final String bulkSynchronisatievraag = doelGemeente + "," + aNummer + "\n";

        final Uc812Bericht uc812Bericht = new Uc812Bericht();
        uc812Bericht.setBulkSynchronisatievraag(bulkSynchronisatievraag);
        final Long uc812BerichtId = berichtenDao.bewaarBericht(uc812Bericht);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(PARAMETER_INPUT, uc812BerichtId);

        final Map<String, Object> result = service.execute(parameters);
        Assert.assertNotNull(result.get(PARAMETER_FOUTAFHANDELINGMELDING));
        Assert.assertEquals(1, ((String) result.get(PARAMETER_FOUTAFHANDELINGMELDING)).split("\n").length);
        Assert.assertNotNull(result.get(PARAMETER_FOUTPAD));
        Assert.assertEquals(FOUTPAD, result.get(PARAMETER_FOUTPAD));
    }

    @Test
    public void badFlowMeerdere() throws Exception {

        final String aNummer = "1607306145";
        final String aNummer2 = "1718206305";
        final String aNummerOngeldigeLengte = "123123123";
        final String aNummerOngeldig = "1231231235";
        final String aNummerNull = null;
        final String aNummerNietAlleenCijfers = "123test234";
        final String negatiefANummerOngeldig = "-123123124";
        final String doelGemeente = "0599";
        final String doelGemeenteOngeldigeLengte = "600";
        final String doelGemeenteNonGba = "0699";
        final String doelGemeenteNietBestaand = "0600";

        final String bulkSynchronisatievraag =
                doelGemeente
                        + ","
                        + aNummer
                        + "\n"
                        + doelGemeente
                        + ","
                        + aNummer2
                        + "\n"
                        + doelGemeente
                        + ","
                        + aNummerOngeldig
                        + "\n"
                        + doelGemeenteNonGba
                        + ","
                        + aNummer
                        + "\n"
                        + doelGemeenteNonGba
                        + ","
                        + aNummer2
                        + "\n"
                        + doelGemeenteNietBestaand
                        + ","
                        + aNummerOngeldig
                        + "\n"
                        + doelGemeente
                        + ","
                        + negatiefANummerOngeldig
                        + "\n"
                        + doelGemeenteOngeldigeLengte
                        + ","
                        + "\n"
                        + doelGemeenteOngeldigeLengte
                        + ","
                        + aNummer
                        + "\n"
                        + doelGemeente
                        + ","
                        + aNummerOngeldigeLengte
                        + "\n"
                        + doelGemeente
                        + ","
                        + aNummerNull
                        + "\n"
                        + doelGemeente
                        + ","
                        + aNummerNietAlleenCijfers
                        + "\n";

        final Uc812Bericht uc812Bericht = new Uc812Bericht();
        uc812Bericht.setBulkSynchronisatievraag(bulkSynchronisatievraag);
        final Long uc812BerichtId = berichtenDao.bewaarBericht(uc812Bericht);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(PARAMETER_INPUT, uc812BerichtId);

        final Map<String, Object> result = service.execute(parameters);
        Assert.assertNotNull(result.get(PARAMETER_FOUTAFHANDELINGMELDING));
        Assert.assertEquals(11, ((String) result.get(PARAMETER_FOUTAFHANDELINGMELDING)).split("\n").length);
        Assert.assertNotNull(result.get(PARAMETER_FOUTPAD));
        Assert.assertEquals(FOUTPAD, result.get(PARAMETER_FOUTPAD));
    }

}
