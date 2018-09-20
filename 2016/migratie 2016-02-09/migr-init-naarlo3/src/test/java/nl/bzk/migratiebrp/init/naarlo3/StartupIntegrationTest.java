/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarlo3;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;

public class StartupIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void test() throws InterruptedException, JMSException {
        // Setup
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(brpDataSource);
        jdbcTemplate.execute("insert into kern.pers(srt,anr) values (1, 1234567890)");

        // Execute
        Main.main(new String[] {});

        // Verify
        final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setReceiveTimeout(5000);
        final TextMessage message = (TextMessage) jmsTemplate.receive("init.vulling.naarlo3");
        Assert.assertNotNull(message);
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                            + "<leesUitBrpVerzoek xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\">"
                            + "<aNummer>1234567890</aNummer><antwoordFormaat>Lo3XML</antwoordFormaat>"
                            + "</leesUitBrpVerzoek>", message.getText());

    }
}
