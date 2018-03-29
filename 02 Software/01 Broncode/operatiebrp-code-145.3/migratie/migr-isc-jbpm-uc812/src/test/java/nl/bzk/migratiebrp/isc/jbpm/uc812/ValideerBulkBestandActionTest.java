/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc812;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc812Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.register.Rol;
import nl.bzk.migratiebrp.isc.jbpm.common.TestPartijService;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.register.client.PartijService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        berichtenDao = new InMemoryBerichtenDao();

        final List<Partij> partijen = new ArrayList<>();
        partijen.add(new Partij("059901", "0599", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("069901", "0699", intToDate(), Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("071701", "0717", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));

        final PartijService partijService = new TestPartijService(new PartijRegisterImpl(partijen));

        service = new ValideerBulkBestandAction(berichtenDao, partijService);
    }

    private static Date intToDate() {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(Integer.toString(20090101));
        } catch (final ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    public void happyFlow() throws Exception {

        final String aNummer = "1607306145";
        final String doelPartijCode = "0599";

        final String bulkSynchronisatievraag = doelPartijCode + "," + aNummer + "\n";

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
        final String doelPartijCode = "0599";

        final String bulkSynchronisatievraag = doelPartijCode + "," + aNummer + "\n";

        final Uc812Bericht uc812Bericht = new Uc812Bericht();
        uc812Bericht.setBulkSynchronisatievraag(bulkSynchronisatievraag);
        final Long uc812BerichtId = berichtenDao.bewaarBericht(uc812Bericht);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(PARAMETER_INPUT, uc812BerichtId);

        final Map<String, Object> result = service.execute(parameters);
        assertNotNull(result);
        assertNotNull(result.get(PARAMETER_FOUTAFHANDELINGMELDING));
        Assert.assertEquals(1, ((String) result.get(PARAMETER_FOUTAFHANDELINGMELDING)).split("\n").length);
        assertNotNull(result.get(PARAMETER_FOUTPAD));
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
        final String doelPartijCode = "0599";
        final String doelPartijCodeOngeldigeLengte = "600";
        final String doelPartijCodeNonGba = "0699";
        final String doelPartijCodeNietBestaand = "0600";

        final String bulkSynchronisatievraag =
                doelPartijCode
                        + ","
                        + aNummer
                        + "\n"
                        + doelPartijCode
                        + ","
                        + aNummer2
                        + "\n"
                        + doelPartijCode
                        + ","
                        + aNummerOngeldig
                        + "\n"
                        + doelPartijCodeNonGba
                        + ","
                        + aNummer
                        + "\n"
                        + doelPartijCodeNonGba
                        + ","
                        + aNummer2
                        + "\n"
                        + doelPartijCodeNietBestaand
                        + ","
                        + aNummerOngeldig
                        + "\n"
                        + doelPartijCode
                        + ","
                        + negatiefANummerOngeldig
                        + "\n"
                        + doelPartijCodeOngeldigeLengte
                        + ","
                        + "\n"
                        + doelPartijCodeOngeldigeLengte
                        + ","
                        + aNummer
                        + "\n"
                        + doelPartijCode
                        + ","
                        + aNummerOngeldigeLengte
                        + "\n"
                        + doelPartijCode
                        + ","
                        + aNummerNull
                        + "\n"
                        + doelPartijCode
                        + ","
                        + aNummerNietAlleenCijfers
                        + "\n";

        final Uc812Bericht uc812Bericht = new Uc812Bericht();
        uc812Bericht.setBulkSynchronisatievraag(bulkSynchronisatievraag);
        final Long uc812BerichtId = berichtenDao.bewaarBericht(uc812Bericht);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(PARAMETER_INPUT, uc812BerichtId);

        final Map<String, Object> result = service.execute(parameters);
        assertNotNull(result);
        assertNotNull(result.get(PARAMETER_FOUTAFHANDELINGMELDING));
        Assert.assertEquals(11, ((String) result.get(PARAMETER_FOUTAFHANDELINGMELDING)).split("\n").length);
        assertNotNull(result.get(PARAMETER_FOUTPAD));
        Assert.assertEquals(FOUTPAD, result.get(PARAMETER_FOUTPAD));
    }

}
