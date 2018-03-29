/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.vrijbericht;

import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtAntwoordBericht;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class VrijBerichtAntwoordNaarIscMessageCreatorTest {

    @Mock
    private TextMessage message;

    @Mock
    private Session session;

    @Test
    public void testAanmaakGeslaagd() throws Exception {
        final VrijBerichtAntwoordBericht bericht = new VrijBerichtAntwoordBericht();
        bericht.setStatus(true);
        bericht.setMessageId("messageId");
        bericht.setCorrelationId("correlatieId");
        final VrijBerichtAntwoordNaarIscMessageCreator creator = new VrijBerichtAntwoordNaarIscMessageCreator(bericht);
        Mockito.when(session.createTextMessage(Mockito.anyString())).thenReturn(message);

        final Message message = creator.createMessage(session);
        final ArgumentCaptor<String> berichtReferentieSleutel = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> berichtReferentieWaarde = ArgumentCaptor.forClass(String.class);
        Mockito.verify(message, Mockito.times(2)).setStringProperty(berichtReferentieSleutel.capture(), berichtReferentieWaarde.capture());

        Assert.assertTrue(berichtReferentieSleutel.getAllValues().contains("iscBerichtReferentie"));
        Assert.assertTrue(berichtReferentieSleutel.getAllValues().contains("iscCorrelatieReferentie"));
        Assert.assertTrue(berichtReferentieWaarde.getAllValues().contains("messageId"));
        Assert.assertTrue(berichtReferentieWaarde.getAllValues().contains("correlatieId"));
    }

    @Test
    public void testAanmaakNietGeslaagd() throws Exception {
        MDCProcessor.startVerwerking("test").run(() -> {
            final VrijBerichtAntwoordBericht bericht = new VrijBerichtAntwoordBericht();
            bericht.setStatus(false);
            bericht.setMessageId("messageId");
            bericht.setCorrelationId("correlatieId");
            final VrijBerichtAntwoordNaarIscMessageCreator creator = new VrijBerichtAntwoordNaarIscMessageCreator(bericht);
            Mockito.when(session.createTextMessage(Mockito.anyString())).thenReturn(message);

            final Message message = creator.createMessage(session);
            final ArgumentCaptor<String> berichtReferentieSleutel = ArgumentCaptor.forClass(String.class);
            final ArgumentCaptor<String> berichtReferentieWaarde = ArgumentCaptor.forClass(String.class);
            Mockito.verify(message, Mockito.times(3)).setStringProperty(berichtReferentieSleutel.capture(), berichtReferentieWaarde.capture());

            Assert.assertTrue(berichtReferentieSleutel.getAllValues().contains("iscBerichtReferentie"));
            Assert.assertTrue(berichtReferentieSleutel.getAllValues().contains("iscCorrelatieReferentie"));
            Assert.assertTrue(berichtReferentieSleutel.getAllValues().contains("verwerking_code"));
            Assert.assertTrue(berichtReferentieWaarde.getAllValues().contains("messageId"));
            Assert.assertTrue(berichtReferentieWaarde.getAllValues().contains("correlatieId"));
            Assert.assertTrue(berichtReferentieWaarde.getAllValues().contains("test"));
        });
    }

    @Test
    public void testBericht() throws Exception {
        final VrijBerichtAntwoordBericht bericht = Mockito.mock(VrijBerichtAntwoordBericht.class);
        final VrijBerichtAntwoordNaarIscMessageCreator creator = new VrijBerichtAntwoordNaarIscMessageCreator(bericht);
        Mockito.doThrow(BerichtInhoudException.class).when(bericht).format();
        Assert.assertNull(creator.createMessage(session));
    }
}
