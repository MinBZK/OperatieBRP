/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.adhoczoeken;

import com.mockrunner.mock.jms.MockBytesMessage;
import java.io.IOException;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonMapper;
import nl.bzk.brp.gba.domain.bevraging.Adresantwoord;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresAntwoordBericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.exception.ServiceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor de messagehandlertest.
 */
@RunWith(MockitoJUnitRunner.class)
public class AdresvraagAntwoordMessageListenerTest {

    @Mock
    private TextMessage message;

    @Mock
    private AdHocZoekenNaarIscVerzender iscVerzender;

    @InjectMocks
    private AdresvraagAntwoordMessageListener subject;

    @Test
    public void testGevonden() throws Exception {
        final Adresantwoord antwoord = new Adresantwoord();
        antwoord.setInhoud("00000000Xa01000270101701100101234567890");
        Mockito.when(message.getText()).thenReturn(JsonMapper.writer().writeValueAsString(antwoord));

        subject.onMessage(message);
        ArgumentCaptor<AdHocZoekPersonenOpAdresAntwoordBericht> berichtArgumentCaptor = ArgumentCaptor.forClass(AdHocZoekPersonenOpAdresAntwoordBericht.class);
        Mockito.verify(iscVerzender).verstuurAdHocZoekenAntwoord(berichtArgumentCaptor.capture());

        AdHocZoekPersonenOpAdresAntwoordBericht bericht = berichtArgumentCaptor.getValue();
        Assert.assertEquals("00000000Xa01000270101701100101234567890", bericht.getInhoud());
    }

    @Test
    public void testGevondenNietActueel() throws Exception {
        final Adresantwoord antwoord = new Adresantwoord();
        antwoord.setInhoud("00000000Xa01000270101701100101234567890");
        Mockito.when(message.getText()).thenReturn(JsonMapper.writer().writeValueAsString(antwoord));

        subject.onMessage(message);
        ArgumentCaptor<AdHocZoekPersonenOpAdresAntwoordBericht> berichtArgumentCaptor = ArgumentCaptor.forClass(AdHocZoekPersonenOpAdresAntwoordBericht.class);
        Mockito.verify(iscVerzender).verstuurAdHocZoekenAntwoord(berichtArgumentCaptor.capture());

        AdHocZoekPersonenOpAdresAntwoordBericht bericht = berichtArgumentCaptor.getValue();
        Assert.assertEquals("00000000Xa01000270101701100101234567890", bericht.getInhoud());
    }

    @Test
    public void testFoutredenX() throws Exception {
        final Adresantwoord antwoord = new Adresantwoord();
        antwoord.setFoutreden("X");
        Mockito.when(message.getText()).thenReturn(JsonMapper.writer().writeValueAsString(antwoord));

        subject.onMessage(message);
        ArgumentCaptor<AdHocZoekPersonenOpAdresAntwoordBericht> berichtArgumentCaptor = ArgumentCaptor.forClass(AdHocZoekPersonenOpAdresAntwoordBericht.class);
        Mockito.verify(iscVerzender).verstuurAdHocZoekenAntwoord(berichtArgumentCaptor.capture());

        AdHocZoekPersonenOpAdresAntwoordBericht bericht = berichtArgumentCaptor.getValue();
        Assert.assertEquals(AdHocZoekAntwoordFoutReden.X, bericht.getFoutreden());
    }

    @Test
    public void testFoutredenXJMSExceptionOpVerwerkingscode() throws Exception {
        Mockito.doThrow(JMSException.class).when(message).getStringProperty("verwerking_code");
        final Adresantwoord antwoord = new Adresantwoord();
        antwoord.setFoutreden("X");
        Mockito.when(message.getText()).thenReturn(JsonMapper.writer().writeValueAsString(antwoord));

        subject.onMessage(message);
        ArgumentCaptor<AdHocZoekPersonenOpAdresAntwoordBericht> berichtArgumentCaptor = ArgumentCaptor.forClass(AdHocZoekPersonenOpAdresAntwoordBericht.class);
        Mockito.verify(iscVerzender).verstuurAdHocZoekenAntwoord(berichtArgumentCaptor.capture());

        AdHocZoekPersonenOpAdresAntwoordBericht bericht = berichtArgumentCaptor.getValue();
        Assert.assertEquals(AdHocZoekAntwoordFoutReden.X, bericht.getFoutreden());
    }

    @Test(expected = ServiceException.class)
    public void testGeenTextMessage() throws Exception {
        subject.onMessage(new MockBytesMessage());
    }

    @Test(expected = ServiceException.class)
    public void testFoutredenXJMSException() throws Exception {
        final Adresantwoord antwoord = new Adresantwoord();
        antwoord.setFoutreden("X");
        Mockito.when(message.getText()).thenReturn(JsonMapper.writer().writeValueAsString(antwoord));
        Mockito.doThrow(JMSException.class).when(iscVerzender).verstuurAdHocZoekenAntwoord(Mockito.any());
        subject.onMessage(message);
    }

    @Test(expected = ServiceException.class)
    public void testFoutredenXIOException() throws Exception {
        final Adresantwoord antwoord = new Adresantwoord();
        antwoord.setFoutreden("X");
        Mockito.when(message.getText()).thenReturn(JsonMapper.writer().writeValueAsString(antwoord));
        Mockito.doThrow(IOException.class).when(iscVerzender).verstuurAdHocZoekenAntwoord(Mockito.any());
        subject.onMessage(message);
    }

    @Test
    public void testFoutredenOnbekend() throws Exception {

    }

}
