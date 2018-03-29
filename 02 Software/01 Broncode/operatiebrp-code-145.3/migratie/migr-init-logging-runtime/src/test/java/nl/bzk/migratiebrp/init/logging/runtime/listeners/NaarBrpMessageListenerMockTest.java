/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners;


import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatieAntwoordRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieAntwoordRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringAntwoord;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers.AntwoordHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class NaarBrpMessageListenerMockTest {

    private AntwoordHandler<SynchroniseerNaarBrpAntwoordBericht> synchroniseerNaarBrpAntwoordHandler;
    private AntwoordHandler<AutorisatieAntwoordBericht> autorisatieAntwoordHandler;
    private AntwoordHandler<AfnemersindicatiesAntwoordBericht> afnemersindicatieAntwoordHandler;
    private AntwoordHandler<ProtocolleringAntwoordBericht> protocolleringAntwoordHandler;

    private NaarBrpMessageListener subject;

    @Before
    public void setup() {
        synchroniseerNaarBrpAntwoordHandler = Mockito.mock(AntwoordHandler.class);
        autorisatieAntwoordHandler = Mockito.mock(AntwoordHandler.class);
        afnemersindicatieAntwoordHandler = Mockito.mock(AntwoordHandler.class);
        protocolleringAntwoordHandler = Mockito.mock(AntwoordHandler.class);

        subject = new NaarBrpMessageListener(synchroniseerNaarBrpAntwoordHandler, autorisatieAntwoordHandler, afnemersindicatieAntwoordHandler,
                protocolleringAntwoordHandler);
    }

    @Test
    public void testSynchroniseerNaarBrpAntwoordBericht() {
        SynchroniseerNaarBrpAntwoordBericht bericht = new SynchroniseerNaarBrpAntwoordBericht();
        bericht.setStatus(StatusType.TOEGEVOEGD);

        subject.verwerkBericht(bericht.format(), "msg-id", "corr-id");

        Mockito.verify(synchroniseerNaarBrpAntwoordHandler).verwerk(Mockito.any(SynchroniseerNaarBrpAntwoordBericht.class), Mockito.eq("msg-id"), Mockito.eq("corr-id"));
        Mockito.verifyNoMoreInteractions(synchroniseerNaarBrpAntwoordHandler, autorisatieAntwoordHandler, afnemersindicatieAntwoordHandler,
                protocolleringAntwoordHandler);
    }

    @Test
    public void testAutorisatieAntwoordBericht() throws BerichtInhoudException {
        AutorisatieAntwoordBericht bericht = new AutorisatieAntwoordBericht();
        AutorisatieAntwoordRecordType record = new AutorisatieAntwoordRecordType();
        record.setAutorisatieId(1);
        record.setStatus(StatusType.TOEGEVOEGD);
        bericht.getAutorisatieTabelRegels().add(record);

        subject.verwerkBericht(bericht.format(), "msg-id", "corr-id");

        Mockito.verify(autorisatieAntwoordHandler).verwerk(Mockito.any(AutorisatieAntwoordBericht.class), Mockito.eq("msg-id"), Mockito.eq("corr-id"));
        Mockito.verifyNoMoreInteractions(synchroniseerNaarBrpAntwoordHandler, autorisatieAntwoordHandler, afnemersindicatieAntwoordHandler,
                protocolleringAntwoordHandler);
    }

    @Test
    public void testAfnemersindicatiesAntwoordBericht() throws BerichtInhoudException {
        AfnemersindicatiesAntwoordBericht bericht = new AfnemersindicatiesAntwoordBericht();
        AfnemersindicatieAntwoordRecordType record = new AfnemersindicatieAntwoordRecordType();
        record.setStapelNummer(1);
        record.setStatus(StatusType.TOEGEVOEGD);
        bericht.getAfnemersindicaties().add(record);

        subject.verwerkBericht(bericht.format(), "msg-id", "corr-id");

        Mockito.verify(afnemersindicatieAntwoordHandler).verwerk(Mockito.any(AfnemersindicatiesAntwoordBericht.class), Mockito.eq("msg-id"), Mockito.eq("corr-id"));
        Mockito.verifyNoMoreInteractions(synchroniseerNaarBrpAntwoordHandler, autorisatieAntwoordHandler, afnemersindicatieAntwoordHandler,
                protocolleringAntwoordHandler);
    }

    @Test
    public void testProtocolleringAntwoordBericht() throws BerichtInhoudException {
        ProtocolleringAntwoordBericht bericht = new ProtocolleringAntwoordBericht();
        ProtocolleringAntwoord record = new ProtocolleringAntwoord(1);
        record.setStatus(StatusType.OK);
        bericht.addProtocolleringAntwoord(record);

        subject.verwerkBericht(bericht.format(), "msg-id", "corr-id");

        Mockito.verify(protocolleringAntwoordHandler).verwerk(Mockito.any(ProtocolleringAntwoordBericht.class), Mockito.eq("msg-id"), Mockito.eq("corr-id"));
        Mockito.verifyNoMoreInteractions(synchroniseerNaarBrpAntwoordHandler, autorisatieAntwoordHandler, afnemersindicatieAntwoordHandler,
                protocolleringAntwoordHandler);
    }

    @Test
    public void testOnbekendBericht() throws BerichtInhoudException {
        subject.verwerkBericht("<blaat />", "msg-id", "corr-id");

        Mockito.verifyNoMoreInteractions(synchroniseerNaarBrpAntwoordHandler, autorisatieAntwoordHandler, afnemersindicatieAntwoordHandler,
                protocolleringAntwoordHandler);
    }


    @Test
    public void testException() {
        SynchroniseerNaarBrpAntwoordBericht bericht = new SynchroniseerNaarBrpAntwoordBericht();
        bericht.setStatus(StatusType.TOEGEVOEGD);

        Mockito.doThrow(IllegalStateException.class).when(synchroniseerNaarBrpAntwoordHandler)
                .verwerk(Mockito.any(SynchroniseerNaarBrpAntwoordBericht.class), Mockito.eq("msg-id"), Mockito.eq("corr-id"));

        try {
            subject.verwerkBericht(bericht.format(), "msg-id", "corr-id");
            Assert.fail("Exception expected");
        } catch (IllegalStateException e) {
            // Ok
        }

        Mockito.verify(synchroniseerNaarBrpAntwoordHandler).verwerk(Mockito.any(SynchroniseerNaarBrpAntwoordBericht.class), Mockito.eq("msg-id"), Mockito.eq("corr-id"));
        Mockito.verifyNoMoreInteractions(synchroniseerNaarBrpAntwoordHandler, autorisatieAntwoordHandler, afnemersindicatieAntwoordHandler,
                protocolleringAntwoordHandler);
    }
}
