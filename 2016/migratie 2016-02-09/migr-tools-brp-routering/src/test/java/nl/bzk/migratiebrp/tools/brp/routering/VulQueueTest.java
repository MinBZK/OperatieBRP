/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.brp.routering;

import java.util.Random;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@Ignore("Duurt eeuwig; gebruikt voor handmatig testen van settings")
public class VulQueueTest {

	private GenericXmlApplicationContext testContext;
	
    @Before
    public void setup() {
        Main.main(null);
        testContext = new GenericXmlApplicationContext();
        testContext.load("classpath:test-context.xml");
        testContext.refresh();

    }

    @Test
    public void test() throws InterruptedException {
        Assert.assertNotNull(Main.getContext());

        JmsTemplate template = testContext.getBean(JmsTemplate.class);
        //template.setSessionTransacted(true);
        Destination archiefQueue = testContext.getBean("archiefQueue", Destination.class);
        
        for(int i = 0; i < 5000; i++) {
        	if(i % 100 == 0) {
        		System.out.println("Message: " + i);
        	}
        	final String testMessage = createTestMessage(i);
        	template.send(archiefQueue, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(testMessage);
				}
			});
        }
    }

    private String createTestMessage(int count) {
    	StringBuilder sb = new StringBuilder(8192);
    	sb.append("TestMessage; count=").append(count).append("\n\nRandom data:\n");
    	
    	for(int i = 0; i < 100; i++) {
    		sb.append("qwelkjads2qwelkjads2qwelkjads2qwelkjads2qwelkjads2qwelkjads2qwelkjads2erw\n");
    	}
		return sb.toString();
	}

	@After
    public void destroy() {
    	testContext.stop();
        Main.stop();
    }

}
