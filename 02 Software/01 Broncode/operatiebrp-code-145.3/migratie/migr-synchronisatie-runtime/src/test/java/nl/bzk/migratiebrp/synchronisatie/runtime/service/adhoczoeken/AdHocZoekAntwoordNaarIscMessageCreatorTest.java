/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.adhoczoeken;

import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonAntwoordBericht;
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
public class AdHocZoekAntwoordNaarIscMessageCreatorTest {

    @Mock
    private TextMessage message;

    @Mock
    private Session session;

    @Test
    public void testAanmaakMessage() throws Exception {
        final AdHocZoekPersoonAntwoordBericht bericht = new AdHocZoekPersoonAntwoordBericht();
        bericht.setFoutreden(AdHocZoekAntwoordFoutReden.X);
        bericht.setMessageId("messageId");
        bericht.setCorrelationId("correlatieId");
        final AdHocZoekAntwoordNaarIscMessageCreator creator = new AdHocZoekAntwoordNaarIscMessageCreator(bericht);
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
    public void testAanmaakMessageMetCode() throws Exception {
        MDCProcessor.startVerwerking("test").run(() -> {
            final AdHocZoekPersoonAntwoordBericht bericht = new AdHocZoekPersoonAntwoordBericht();
            bericht.setFoutreden(AdHocZoekAntwoordFoutReden.X);
            bericht.setMessageId("messageId");
            bericht.setCorrelationId("correlatieId");
            final AdHocZoekAntwoordNaarIscMessageCreator creator = new AdHocZoekAntwoordNaarIscMessageCreator(bericht);
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
        final AdHocZoekPersoonAntwoordBericht bericht = Mockito.mock(AdHocZoekPersoonAntwoordBericht.class);
        final AdHocZoekAntwoordNaarIscMessageCreator creator = new AdHocZoekAntwoordNaarIscMessageCreator(bericht);
        Mockito.doThrow(BerichtInhoudException.class).when(bericht).format();
        Assert.assertNull(creator.createMessage(session));
    }
}
