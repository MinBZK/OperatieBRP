/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.test.dal.AbstractDBUnitUtil;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.register.client.PartijService;
import nl.bzk.migratiebrp.synchronisatie.runtime.Main.Modus;
import org.dbunit.DatabaseUnitException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SynchronisatiePartijRegisterIT extends AbstractIT {

    @Autowired
    private PartijService partijService;

    public SynchronisatiePartijRegisterIT() {
        super(Modus.SYNCHRONISATIE);
    }

    @Before
    public void setup() throws DatabaseUnitException, SQLException {
        AbstractDBUnitUtil dbunit = databaseContext.getAutowireCapableBeanFactory().getBean(AbstractDBUnitUtil.class);
        dbunit.setInMemory();
        dbunit.truncate(dbunit.createConnection(), this.getClass(), "partijregister-cleanup.xml");
        dbunit.insert(dbunit.createConnection(), this.getClass(), "partijregister-fixtures.xml");
    }

    @Test
    public void partijService() throws InterruptedException {
        partijService.refreshRegister();
        final PartijRegister register = partijService.geefRegister();
        assertNotNull(register);
        assertEquals(true, register.geefAllePartijen().size() > 0);
    }

    @Test
    public void leesPartijregister() throws DatabaseUnitException, SQLException, InterruptedException, JMSException {
        String leesPartijregisterXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<leesPartijRegisterVerzoek xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\"/>";
        putMessage(PARTIJ_VERZOEK_QUEUE, leesPartijregisterXml, UUID.randomUUID().toString());

        Message message = expectPartijregisterMessage();
        String xmlResponse = ((TextMessage) message).getText();

        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<leesPartijRegisterAntwoord xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\">"
                + "     <status>Ok</status>"
                + "     <partijRegister>"
                + "         <partij>"
                + "             <partijCode>999950</partijCode>"
                + "             <rollen>BIJHOUDINGSORGAAN_COLLEGE</rollen>"
                + "             <rollen>BIJHOUDINGSORGAAN_MINISTER</rollen>"
                + "         </partij>"
                + "     </partijRegister>"
                + "</leesPartijRegisterAntwoord>";
        assertEquals(expected.replaceAll("\\s\\s+", ""), xmlResponse);
    }
}
