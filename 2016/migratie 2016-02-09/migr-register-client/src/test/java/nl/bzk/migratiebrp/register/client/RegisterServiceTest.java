/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client;

import java.util.concurrent.TimeUnit;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class RegisterServiceTest extends AbstractIntegrationTest {

    public RegisterServiceTest() {
        super("classpath:register-client-gemeente.xml");
    }

    @Autowired
    private GemeenteServiceImpl subject;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Test
    public void test() throws JMSException {
        ReflectionTestUtils.setField(subject, "timeoutUnit", TimeUnit.MILLISECONDS);
        try {
            subject.geefRegister();
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            // Expectd
        }

        final Connection connection = connectionFactory.createConnection();
        final Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        final TextMessage message4 =
                session.createTextMessage("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n"
                                          + "<leesGemeenteRegisterAntwoord xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\">\r\n"
                                          + "    <status>Ok</status>\r\n"
                                          + "        <gemeenteRegister>\r\n"
                                          + "            <gemeente><partijCode>580599</partijCode><gemeenteCode>0599</gemeenteCode></gemeente>\r\n"
                                          + "            <gemeente><partijCode>580429</partijCode><gemeenteCode>0429</gemeenteCode></gemeente>\r\n"
                                          + "            <gemeente><partijCode>580699</partijCode><gemeenteCode>0699</gemeenteCode><datumBrp>20090101</datumBrp></gemeente>\r\n"
                                          + "            <gemeente><partijCode>580717</partijCode><gemeenteCode>0717</gemeenteCode></gemeente>\r\n"
                                          + "        </gemeenteRegister>\r\n"
                                          + "</leesGemeenteRegisterAntwoord>");
        final TextMessage message5 =
                session.createTextMessage("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n"
                                          + "<leesGemeenteRegisterAntwoord xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\">\r\n"
                                          + "    <status>Ok</status>\r\n"
                                          + "        <gemeenteRegister>\r\n"
                                          + "            <gemeente><partijCode>580599</partijCode><gemeenteCode>0599</gemeenteCode></gemeente>\r\n"
                                          + "            <gemeente><partijCode>580429</partijCode><gemeenteCode>0429</gemeenteCode></gemeente>\r\n"
                                          + "            <gemeente><partijCode>580699</partijCode><gemeenteCode>0699</gemeenteCode><datumBrp>20090101</datumBrp></gemeente>\r\n"
                                          + "            <gemeente><partijCode>580717</partijCode><gemeenteCode>0717</gemeenteCode></gemeente>\r\n"
                                          + "            <gemeente><partijCode>580718</partijCode><gemeenteCode>0718</gemeenteCode></gemeente>\r\n"
                                          + "        </gemeenteRegister>\r\n"
                                          + "</leesGemeenteRegisterAntwoord>");
        final TextMessage message6 =
                session.createTextMessage("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n"
                                          + "<leesGemeenteRegisterAntwoord xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\">\r\n"
                                          + "    <status>Ok</status>\r\n"
                                          + "        <gemeenteRegister>\r\n"
                                          + "            <gemeente><partijCode>580599</partijCode><gemeenteCode>0599</gemeenteCode></gemeente>\r\n"
                                          + "            <gemeente><partijCode>580429</partijCode><gemeenteCode>0429</gemeenteCode></gemeente>\r\n"
                                          + "            <gemeente><partijCode>580699</partijCode><gemeenteCode>0699</gemeenteCode><datumBrp>20090101</datumBrp></gemeente>\r\n"
                                          + "            <gemeente><partijCode>580717</partijCode><gemeenteCode>0717</gemeenteCode></gemeente>\r\n"
                                          + "            <gemeente><partijCode>580718</partijCode><gemeenteCode>0718</gemeenteCode></gemeente>\r\n"
                                          + "            <gemeente><partijCode>580719</partijCode><gemeenteCode>0719</gemeenteCode></gemeente>\r\n"
                                          + "        </gemeenteRegister>\r\n"
                                          + "</leesGemeenteRegisterAntwoord>");

        session.close();
        connection.close();

        subject.onMessage(message4);

        final GemeenteRegister reg1 = subject.geefRegister();
        Assert.assertNotNull(reg1);
        Assert.assertEquals(4, reg1.geefAlleGemeenten().size());
        final GemeenteRegister reg2 = subject.geefRegister();
        Assert.assertNotNull(reg2);
        Assert.assertEquals(4, reg2.geefAlleGemeenten().size());

        subject.onMessage(message5);

        final GemeenteRegister reg3 = subject.geefRegister();
        Assert.assertNotNull(reg3);
        Assert.assertEquals(5, reg3.geefAlleGemeenten().size());

        subject.clearRegister();

        ReflectionTestUtils.setField(subject, "timeoutUnit", TimeUnit.MILLISECONDS);
        try {
            subject.geefRegister();
            Assert.fail();
        } catch (final IllegalArgumentException e) {
            // Expectd
        }

        subject.onMessage(message6);

        final GemeenteRegister reg4 = subject.geefRegister();
        Assert.assertNotNull(reg4);
        Assert.assertEquals(6, reg4.geefAlleGemeenten().size());

    }
}
