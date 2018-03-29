/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners;


import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers.AntwoordHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TerugConversieMessageListenerMockTest {

    private AntwoordHandler<LeesUitBrpAntwoordBericht> leesUitBrpAntwoordHandler;

    private TerugConversieMessageListener subject;

    @Before
    public void setup() {
        leesUitBrpAntwoordHandler = Mockito.mock(AntwoordHandler.class);

        subject = new TerugConversieMessageListener(leesUitBrpAntwoordHandler);
    }

    @Test
    public void testLeesUitBrpAntwoordBericht() {
        LeesUitBrpAntwoordBericht bericht = new LeesUitBrpAntwoordBericht();
        bericht.setStatus(StatusType.OK);

        subject.verwerkBericht(bericht.format(), "msg-id", "corr-id");

        Mockito.verify(leesUitBrpAntwoordHandler).verwerk(Mockito.any(LeesUitBrpAntwoordBericht.class), Mockito.eq("msg-id"), Mockito.eq("corr-id"));
        Mockito.verifyNoMoreInteractions(leesUitBrpAntwoordHandler);
    }

    @Test
    public void testOnbekendBericht() throws BerichtInhoudException {
        subject.verwerkBericht("<blaat />", "msg-id", "corr-id");

        Mockito.verifyNoMoreInteractions(leesUitBrpAntwoordHandler);
    }

    @Test
    public void testException() {
        LeesUitBrpAntwoordBericht bericht = new LeesUitBrpAntwoordBericht();
        bericht.setStatus(StatusType.OK);

        Mockito.doThrow(IllegalStateException.class).when(leesUitBrpAntwoordHandler)
                .verwerk(Mockito.any(LeesUitBrpAntwoordBericht.class), Mockito.eq("msg-id"), Mockito.eq("corr-id"));

        try {
            subject.verwerkBericht(bericht.format(), "msg-id", "corr-id");
            Assert.fail("Exception expected");
        } catch (IllegalStateException e) {
            // Ok
        }

        Mockito.verify(leesUitBrpAntwoordHandler).verwerk(Mockito.any(LeesUitBrpAntwoordBericht.class), Mockito.eq("msg-id"), Mockito.eq("corr-id"));
        Mockito.verifyNoMoreInteractions(leesUitBrpAntwoordHandler);
    }
}
