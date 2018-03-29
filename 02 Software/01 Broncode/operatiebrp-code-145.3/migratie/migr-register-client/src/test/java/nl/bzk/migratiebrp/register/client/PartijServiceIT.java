/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

public class PartijServiceIT extends AbstractIT {

    public PartijServiceIT() {
        super("classpath:test-partijregister-dummy.xml", "classpath:register-client-partij.xml");
    }

    @Autowired
    private PartijService subject;

    @Test
    public void test() {
        Assert.assertNull(subject.getLaatsteBericht());
        Assert.assertNull(subject.getLaatsteOntvangst());
        Assert.assertNull(subject.getRegisterAsString());

        subject.refreshRegister();

        Assert.assertNotNull(subject.geefRegister());
        Assert.assertNotNull(subject.getLaatsteBericht());
        Assert.assertNotNull(subject.getLaatsteOntvangst());
        Assert.assertNotNull(subject.getRegisterAsString());

        subject.clearRegister();

        Assert.assertNull(subject.getLaatsteBericht());
        Assert.assertNull(subject.getLaatsteOntvangst());
        Assert.assertNull(subject.getRegisterAsString());

        Assert.assertNotNull(subject.geefRegister());
    }

    @Test
    public void ongeldigRegister() throws JMSException {
        TextMessage message = Mockito.mock(TextMessage.class);
        Mockito.when(message.getText()).thenReturn("<blaat />");

        ((MessageListener)subject).onMessage(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ongeldigMessageType() throws JMSException {
        BytesMessage message = Mockito.mock(BytesMessage.class);

        ((MessageListener)subject).onMessage(message);
    }
}
