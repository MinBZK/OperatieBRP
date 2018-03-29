/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.vrijbericht;

import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonMapper;
import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtAntwoord;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtAntwoordBericht;
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
public class VrijBerichtAntwoordMessageListenerTest {

    @Mock
    private TextMessage message;

    @Mock
    private VrijBerichtAntwoordNaarIscVerzender iscVerzender;

    @InjectMocks
    private VrijBerichtAntwoordMessageListener subject;

    @Test
    public void testGeslaag() throws Exception {
        final VrijBerichtAntwoord antwoord = new VrijBerichtAntwoord();
        antwoord.setGeslaagd(true);
        antwoord.setReferentienummer("REF-123");
        Mockito.when(message.getText()).thenReturn(JsonMapper.writer().writeValueAsString(antwoord));

        subject.onMessage(message);
        ArgumentCaptor<VrijBerichtAntwoordBericht> berichtArgumentCaptor = ArgumentCaptor.forClass(VrijBerichtAntwoordBericht.class);
        Mockito.verify(iscVerzender).verstuurVrijBerichtAntwoord(berichtArgumentCaptor.capture());

        VrijBerichtAntwoordBericht bericht = berichtArgumentCaptor.getValue();
        Assert.assertEquals("Ok", bericht.getStatus());
        Assert.assertEquals("REF-123", bericht.getReferentienummer());
    }

    @Test
    public void testNietGeslaag() throws Exception {
        final VrijBerichtAntwoord antwoord = new VrijBerichtAntwoord();
        antwoord.setGeslaagd(false);
        antwoord.setReferentienummer("REF-123");
        Mockito.when(message.getText()).thenReturn(JsonMapper.writer().writeValueAsString(antwoord));

        subject.onMessage(message);
        ArgumentCaptor<VrijBerichtAntwoordBericht> berichtArgumentCaptor = ArgumentCaptor.forClass(VrijBerichtAntwoordBericht.class);
        Mockito.verify(iscVerzender).verstuurVrijBerichtAntwoord(berichtArgumentCaptor.capture());

        VrijBerichtAntwoordBericht bericht = berichtArgumentCaptor.getValue();
        Assert.assertEquals("Fout", bericht.getStatus());
        Assert.assertEquals("REF-123", bericht.getReferentienummer());
    }
}
