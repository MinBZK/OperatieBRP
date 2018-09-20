/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox.impl;

import static org.junit.Assert.fail;

import java.net.ConnectException;
import java.sql.SQLException;

import javax.persistence.EntityExistsException;

import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.LogboekRegel;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;
import nl.moderniseringgba.isc.voisc.entities.StatusEnum;
import nl.moderniseringgba.isc.voisc.exceptions.VoaException;
import nl.moderniseringgba.isc.voisc.exceptions.VoaRuntimeException;
import nl.moderniseringgba.isc.voisc.repository.MailboxRepository;
import nl.moderniseringgba.isc.voisc.repository.VoaRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class VoiscDbProxyImplTest {

    private static final String GEMEENTECODE = "1904";
    private static final String MAILBOX_NR = "1904010";
    private static final int DISPATCH_NR = 1;
    private static final long BERICHT_ID = 10;

    @Mock
    private VoaRepository voaRepository;

    @Mock
    private MailboxRepository mailboxRepository;

    @InjectMocks
    private VoiscDbProxyImpl proxy;

    private Bericht bericht;
    private Mailbox mailbox;

    @Before
    public void setUp() {
        proxy.setDatabaseReconnects(1);
        proxy.setEmergencySleepTime(1);

        bericht = new Bericht();
        bericht.setDispatchSequenceNumber(DISPATCH_NR);
        bericht.setId(BERICHT_ID);

        mailbox = new Mailbox();
        mailbox.setGemeentecode(GEMEENTECODE);
        mailbox.setMailboxnr(MAILBOX_NR);
    }

    @Test
    public void saveTestSucces() {
        try {
            proxy.saveBericht(bericht, false, false);
            proxy.saveBericht(bericht, false, true);
            proxy.saveBericht(bericht, true, false);
            proxy.saveBericht(bericht, true, true);
            Mockito.verify(voaRepository, Mockito.times(4)).save(bericht);
        } catch (final VoaException e) {
            fail("Geen VoaException verwacht.");
        }
    }

    @Test
    public void saveEntityExistException() {
        try {
            Mockito.doThrow(new EntityExistsException()).when(voaRepository).save(bericht);
            proxy.saveBericht(bericht, true, true);
        } catch (final VoaRuntimeException e) {
            fail("Er zou geen VoaRuntimeException gegooid moeten worden.");
        } catch (final VoaException e) {
            Assert.assertTrue(e.getMessage().startsWith("[MELDING-8121]:"));
        }
    }

    @Test
    public void saveConnectException() {
        try {
            Mockito.doAnswer(new Answer<Object>() {

                @Override
                public Object answer(final InvocationOnMock invocation) throws ConnectException {
                    throw new ConnectException("");
                }

            }).when(voaRepository).save(bericht);
            proxy.saveBericht(bericht, true, true);
            fail("Er wordt een VoaException verwacht aangezien de verbinding eruit ligt");
        } catch (final VoaRuntimeException e) {
            fail("Er zou geen VoaRuntimeException gegooid moeten worden.");
        } catch (final VoaException e) {
            Assert.assertTrue(e.getMessage().startsWith("[MELDING-8109]:"));
        }
        Mockito.verify(voaRepository, Mockito.times(2)).save(bericht);
    }

    @Test
    public void saveSqlException() {
        try {
            Mockito.doAnswer(new Answer<Object>() {

                @Override
                public Object answer(final InvocationOnMock invocation) throws SQLException {
                    throw new SQLException();
                }

            }).when(voaRepository).save(bericht);
            proxy.saveBericht(bericht, true, true);
            fail("Er wordt een VoaException verwacht aangezien er een SQLException wordt gegooid");
        } catch (final VoaRuntimeException e) {
            fail("Er zou geen VoaRuntimeException gegooid moeten worden.");
        } catch (final VoaException e) {
            Assert.assertTrue(e.getMessage().startsWith("[MELDING-8109]:"));
        }
        Mockito.verify(voaRepository, Mockito.times(2)).save(bericht);
    }

    @Test
    public void saveNestedSqlException() {
        try {
            Mockito.doAnswer(new Answer<Object>() {

                @Override
                public Object answer(final InvocationOnMock invocation) throws SQLException {
                    final EntityExistsException s1 = new EntityExistsException("Inner Exception");
                    throw new SQLException(s1);
                }

            }).when(voaRepository).save(bericht);
            proxy.saveBericht(bericht, true, true);
            fail("Er wordt een VoaException verwacht aangezien er een SQLException wordt gegooid");
        } catch (final VoaRuntimeException e) {
            fail("Er zou geen VoaRuntimeException gegooid moeten worden.");
        } catch (final VoaException e) {
            Assert.assertTrue(e.getMessage().startsWith("[MELDING-8121]:"));
            Assert.assertTrue(e.getMessage().contains("Inner Exception"));

        }
        Mockito.verify(voaRepository, Mockito.times(1)).save(bericht);
    }

    @Test
    public void saveSqlExceptionNoReconnectUitgaandBericht() {
        try {
            Mockito.doAnswer(new Answer<Object>() {

                @Override
                public Object answer(final InvocationOnMock invocation) throws SQLException {
                    throw new SQLException();
                }

            }).when(voaRepository).save(bericht);
            proxy.saveBericht(bericht, false, false);
            fail("Er wordt een VoaException verwacht aangezien er een SQLException wordt gegooid");
        } catch (final VoaRuntimeException e) {
            fail("Er zou geen VoaRuntimeException gegooid moeten worden.");
        } catch (final VoaException e) {
            Assert.assertTrue(e.getMessage().startsWith("[MELDING-8109]:"));
        }
        Mockito.verify(voaRepository, Mockito.times(1)).save(bericht);
    }

    @Test
    public void saveSqlExceptionReconnectUitgaandBericht() {
        try {
            Mockito.doAnswer(new Answer<Object>() {

                @Override
                public Object answer(final InvocationOnMock invocation) throws SQLException {
                    throw new SQLException();
                }

            }).when(voaRepository).save(bericht);
            proxy.saveBericht(bericht, true, false);
            fail("Er wordt een VoaException verwacht aangezien er een SQLException wordt gegooid");
        } catch (final VoaRuntimeException e) {
            fail("Er zou geen VoaRuntimeException gegooid moeten worden.");
        } catch (final VoaException e) {
            Assert.assertTrue(e.getMessage().startsWith("[MELDING-8109]:"));
        }
        Mockito.verify(voaRepository, Mockito.times(2)).save(bericht);
    }

    @Test
    public void saveSqlExceptionReconnectDbBackOnline() {
        try {
            Mockito.doAnswer(new Answer<Object>() {

                @Override
                public Object answer(final InvocationOnMock invocation) throws SQLException {
                    if (bericht.getId() == BERICHT_ID) {
                        bericht.setId(11L);
                        throw new SQLException();
                    }
                    return null;
                }

            }).when(voaRepository).save(bericht);
            proxy.saveBericht(bericht, true, false);
            Mockito.verify(voaRepository, Mockito.times(2)).save(bericht);
        } catch (final VoaRuntimeException e) {
            fail("Er zou geen VoaRuntimeException gegooid moeten worden.");
        } catch (final VoaException e) {
            fail("er zou geen VoaException gegooid moeten worden.");
        }
    }

    @Test
    public void saveSqlExceptionReconnectDbBackOnlineInkomend() {
        try {
            Mockito.doAnswer(new Answer<Object>() {

                @Override
                public Object answer(final InvocationOnMock invocation) throws SQLException {
                    if (bericht.getDispatchSequenceNumber() == DISPATCH_NR) {
                        bericht.setDispatchSequenceNumber(11);
                        throw new SQLException();
                    }
                    return null;
                }

            }).when(voaRepository).save(bericht);
            proxy.saveBericht(bericht, true, true);
            Mockito.verify(voaRepository, Mockito.times(2)).save(bericht);
        } catch (final VoaRuntimeException e) {
            fail("Er zou geen VoaRuntimeException gegooid moeten worden.");
        } catch (final VoaException e) {
            fail("er zou geen VoaException gegooid moeten worden.");
        }
    }

    @Test
    public void saveLogboekRegel() {
        final LogboekRegel regel = new LogboekRegel();
        regel.setAantalOntvangenNOK(2);
        regel.setAantalOntvangenOK(3);
        regel.setAantalVerzondenNOK(2);
        regel.setAantalVerzondenOK(4);

        proxy.saveLogboekRegel(regel);
        Mockito.verify(mailboxRepository, Mockito.timeout(1)).saveLogboekEntry(regel);

        Assert.assertNotNull(regel.getEindDatumTijd());
        Assert.assertTrue(regel.getAantalOntvangen() == 5);
        Assert.assertTrue(regel.getAantalVerzonden() == 6);
    }

    @Test
    public void saveMailbox() {
        proxy.saveMailbox(mailbox);
        Mockito.verify(mailboxRepository, Mockito.times(1)).save(mailbox);
    }

    @Test
    public void getBerichtByDispatchSequenceNumber() {
        proxy.getBerichtByDispatchSequenceNumber(DISPATCH_NR);
        Mockito.verify(voaRepository, Mockito.times(1)).getBerichtByDispatchSeqNr(DISPATCH_NR);
    }

    @Test
    public void getBerichtByEref() {
        final String eref = "4321";
        proxy.getBerichtByEref(eref);
        Mockito.verify(voaRepository, Mockito.times(1)).getBerichtByEref(eref);
    }

    @Test
    public void getMailboxByGemeentecode() {
        proxy.getMailboxByGemeentecode(GEMEENTECODE);
        Mockito.verify(mailboxRepository, Mockito.times(1)).getMailboxByGemeentecode(GEMEENTECODE);
    }

    @Test
    public void getMailboxByNummer() {
        proxy.getMailboxByNummer(MAILBOX_NR);
        Mockito.verify(mailboxRepository, Mockito.times(1)).getMailboxByNummer(MAILBOX_NR);
    }

    @Test
    public void getBerichtToSendMBS() {
        proxy.getBerichtToSendMBS(MAILBOX_NR);
        Mockito.verify(voaRepository, Mockito.times(1)).getBerichtToSendMBS(MAILBOX_NR);
    }

    @Test
    public void getBerichtToSendQueue() {
        final int limit = 171;
        proxy.getBerichtToSendQueue(limit);
        Mockito.verify(voaRepository, Mockito.times(1)).getBerichtToSendQueue(limit);
    }

    @Test
    public void getBerichtByEsbMessageId() {
        final String correlationId = "12345";
        proxy.getBerichtByEsbMessageId(correlationId);
        Mockito.verify(voaRepository, Mockito.times(1)).getBerichtByEsbMessageId(correlationId);
    }

    @Test
    public void sendErrorBericht() {
        final String esbMesgId = "12345";
        final String foutMelding = "foutmelding";
        bericht.setEsbMessageId(esbMesgId);
        try {
            Mockito.doAnswer(new Answer<Object>() {

                @Override
                public Object answer(final InvocationOnMock invocation) {
                    final Bericht errorBericht = (Bericht) invocation.getArguments()[0];
                    Assert.assertNotNull(bericht);
                    Assert.assertEquals(Bericht.AANDUIDING_IN_UIT_IN, errorBericht.getAanduidingInUit());
                    Assert.assertEquals(StatusEnum.MAILBOX_RECEIVED, errorBericht.getStatus());
                    Assert.assertEquals(foutMelding, errorBericht.getBerichtInhoud());
                    Assert.assertEquals(esbMesgId, errorBericht.getEsbCorrelationId());

                    return null;
                }
            }).when(voaRepository).save(Matchers.any(Bericht.class));
            proxy.sendEsbErrorBericht(bericht, foutMelding);
        } catch (final VoaException e) {
            fail("VoaException niet verwacht");
        }
    }

    @Test
    public void sendErrorBerichtGeenBericht() {
        final String esbMesgId = "12345";
        final String foutMelding = "foutmelding";
        bericht.setEsbMessageId(esbMesgId);
        try {
            Mockito.doAnswer(new Answer<Object>() {

                @Override
                public Object answer(final InvocationOnMock invocation) {
                    final Bericht errorBericht = (Bericht) invocation.getArguments()[0];
                    Assert.assertNotNull(bericht);
                    Assert.assertEquals(Bericht.AANDUIDING_IN_UIT_IN, errorBericht.getAanduidingInUit());
                    Assert.assertEquals(StatusEnum.MAILBOX_RECEIVED, errorBericht.getStatus());
                    Assert.assertEquals(foutMelding, errorBericht.getBerichtInhoud());
                    Assert.assertNull(errorBericht.getEsbCorrelationId());

                    return null;
                }
            }).when(voaRepository).save(Matchers.any(Bericht.class));
            proxy.sendEsbErrorBericht(null, foutMelding);
        } catch (final VoaException e) {
            fail("VoaException niet verwacht");
        }
    }
}
