/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.init.runtime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore("Niet voor testuitvoer, alleen lokaal gebruiken om een bericht op de queue te zetten")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:runtime-test-beans.xml")
public class SyncRequestQueueTest {

    @Inject
    private JmsTemplate jmsTemplate;

    @Test
    public void test() {
        final byte[] lg01 = readResourceAsBytes("Simpel01-8172387435.txt");
        final String lg01Bericht = new String(lg01);// GBACharacterSet.convertTeletexByteArrayToString(lg01);
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                return session.createTextMessage(lg01Bericht);
            }
        });
    }

    private byte[] readResourceAsBytes(final String resource) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final InputStream is = SyncRequestQueueTest.class.getResourceAsStream(resource);
        final byte[] buffer = new byte[4096];
        int length;

        try {
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return baos.toByteArray();
    }
}
