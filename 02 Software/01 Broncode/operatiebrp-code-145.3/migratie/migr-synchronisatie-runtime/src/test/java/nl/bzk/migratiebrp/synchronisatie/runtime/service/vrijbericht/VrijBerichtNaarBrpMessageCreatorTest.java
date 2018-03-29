/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.vrijbericht;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtOpdracht;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor de MessagCreator VrijBericht naar BRP
 */
@RunWith(MockitoJUnitRunner.class)
public class VrijBerichtNaarBrpMessageCreatorTest {

    @Mock
    private TextMessage message;

    @Mock
    private Session session;

    @Test
    public void testAanmaakMessage() throws Exception {
        final VrijBerichtOpdracht vraag = new VrijBerichtOpdracht();
        final VrijBerichtNaarBrpMessageCreator creator = new VrijBerichtNaarBrpMessageCreator(vraag, "verzoek");

        Mockito.when(session.createTextMessage(Mockito.anyString())).thenReturn(message);
        Mockito.when(message.getText()).thenReturn("BerichtInhoud");

        Message antwoord = creator.createMessage(session);

        final ArgumentCaptor<String> berichtReferentieSleutel = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> berichtReferentieWaarde = ArgumentCaptor.forClass(String.class);
        Mockito.verify(message, Mockito.atLeastOnce()).setStringProperty(berichtReferentieSleutel.capture(), berichtReferentieWaarde.capture());
        Assert.assertTrue(antwoord instanceof TextMessage);
        Assert.assertTrue(berichtReferentieSleutel.getAllValues().contains("iscBerichtReferentie"));
        Assert.assertTrue(berichtReferentieWaarde.getAllValues().contains("verzoek"));
    }

    @Test(expected = JMSException.class)
    public void testExceptie() throws Exception {
        final VrijBerichtOpdracht vraag = new VrijBerichtOpdracht();
        final VrijBerichtNaarBrpMessageCreator creator = new VrijBerichtNaarBrpMessageCreator(vraag, "verzoek");
        Mockito.doThrow(JsonProcessingException.class).when(session).createTextMessage(Mockito.anyString());

        creator.createMessage(session);
    }
}