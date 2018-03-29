/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.queue;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.junit.Test;

public class DriverTest extends AbstractIT {

    private static final String MESSAGE_DATA = "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;" +
            "ad;lgij;alfjav;o 34vt4u4q3[vqtu9vj34[g4j4j3'44gj;ga;wnhlaigl4tlih3l4H4L;H4;;H;HT;H34;H48;o4hf;oho;4hHo;";

    @Test
    public void test() throws JMSException {
        final Driver driver = Drivers.createDriver("hornetq", "localhost", Integer.parseInt(getPortProperties().getProperty("test.hornetq.port")), false);

        // Should show no messages
        driver.list("init.vulling.naarbrp");
        driver.count("init.vulling.naarbrp");

        // Fill queue
        for (int kilo = 1; kilo <= 75; kilo++) {
            executeInProducer("init.vulling.naarbrp", ((session, producer) -> {
                for (int i = 1; i <= 1_000; i++) {
                    producer.send(session.createTextMessage("Test message " + i + "; data=" ));
                }
            }));
            System.out.println(kilo + "000 messages sent.");
        }

        // Should show lots of messages
        driver.count("init.vulling.naarbrp");
    }

    private void executeInProducer(final String queue, MessageProducerCallback callback) throws JMSException {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            producer = session.createProducer(session.createQueue(queue));
            callback.execute(session, producer);
            session.commit();
        } finally {
            if (producer != null) {
                try {
                    producer.close();
                } catch (JMSException e) {
                    // Ignore
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    // Ignore
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    // Ignore
                }
            }
        }
    }

    @FunctionalInterface
    private interface MessageProducerCallback {
        void execute(Session session, MessageProducer producer) throws JMSException;
    }

}
