/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.relateren.utils;

import java.util.ArrayList;
import java.util.List;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.brp.relateren.service.bericht.RelateerPersoonBericht;
import org.apache.activemq.ActiveMQConnectionFactory;

 public class TestSender {

    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageProducer producer = null;

    public TestSender() {

    }

    public void sendMessage() {

        try {
            factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("Relateren");
            producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage();
            List<Integer> ids = new ArrayList<>();
            ids.add(12345);
            RelateerPersoonBericht bericht = new RelateerPersoonBericht(ids);
            message.setText(bericht.writeValueAsString());
            producer.send(message);
            System.out.println("Sent: " + message.getText());

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestSender sender = new TestSender();
        sender.sendMessage();
    }

}
