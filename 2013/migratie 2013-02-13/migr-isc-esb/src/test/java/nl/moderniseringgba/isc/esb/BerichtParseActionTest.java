/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

import nl.moderniseringgba.isc.esb.message.Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf01Bericht;
import nl.moderniseringgba.isc.jbpm.berichten.BerichtenDao;

import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.Properties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class BerichtParseActionTest {

    // Subject
    private BerichtParseAction subject;

    // Dependencies
    private ConfigTree configTree;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        configTree = Mockito.mock(ConfigTree.class);
        berichtenDao = Mockito.mock(BerichtenDao.class);

        subject = new BerichtParseAction(configTree);
        subject.setBerichtenDao(berichtenDao);
    }

    @Test
    public void test() throws Exception {
        // Config
        Mockito.when(configTree.getAttribute("kanaal")).thenReturn("VOSPG");

        // Expect
        final Message message = Mockito.mock(Message.class);
        final Properties properties = Mockito.mock(Properties.class);
        final Body body = Mockito.mock(Body.class);

        Mockito.when(message.getProperties()).thenReturn(properties);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_BERICHT)).thenReturn(42L);
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_MESSAGE_ID)).thenReturn("M00000000002");
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_CORRELATIE_ID)).thenReturn("M00000000001");
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_BRON_GEMEENTE)).thenReturn("0513");
        Mockito.when(properties.getProperty(EsbConstants.PROPERTY_DOEL_GEMEENTE)).thenReturn("0612");

        Mockito.when(message.getBody()).thenReturn(body);
        Mockito.when(body.get()).thenReturn("00000000Pf0100000");

        // Execute
        subject.initialise();
        final Message result = subject.process(message);
        Assert.assertSame(message, result);

        // Verify
        Mockito.verify(berichtenDao).updateNaam(42L, "Pf01");
        Mockito.verify(berichtenDao).updateGemeenten(42L, "0513", "0612");
        Mockito.verify(berichtenDao).updateHerhaling(42L, null);

        final ArgumentCaptor<Bericht> argument = ArgumentCaptor.forClass(Bericht.class);
        Mockito.verify(body).add(argument.capture());
        final Bericht bericht = argument.getValue();

        Assert.assertNotNull(bericht);
        Assert.assertEquals("Pf01", bericht.getBerichtType());
        Assert.assertEquals("M00000000002", bericht.getMessageId());
        Assert.assertEquals("M00000000001", bericht.getCorrelationId());
        Assert.assertTrue(bericht instanceof Lo3Bericht);
        Assert.assertEquals("0513", ((Lo3Bericht) bericht).getBronGemeente());
        Assert.assertEquals("0612", ((Lo3Bericht) bericht).getDoelGemeente());
        Assert.assertTrue(bericht instanceof Pf01Bericht);
    }

    @Test
    public void testNoOps() throws Exception {
        final Message message = Mockito.mock(Message.class);
        final Throwable throwable = Mockito.mock(Throwable.class);

        subject.processSuccess(message);
        subject.processException(message, throwable);
        subject.destroy();
    }
}
