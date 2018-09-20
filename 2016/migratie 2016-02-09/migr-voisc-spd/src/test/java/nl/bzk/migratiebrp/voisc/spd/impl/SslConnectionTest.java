/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import javax.net.ssl.SSLSocket;
import junit.framework.Assert;
import nl.bzk.migratiebrp.voisc.spd.constants.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.exception.ConnectionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.core.io.Resource;

@RunWith(MockitoJUnitRunner.class)
public class SslConnectionTest {

    private static final String LENGTE_VAN_DE_BERICHTEN_NIET_GELIJK = "Lengte van de berichten niet gelijk";

    private static final String MELDING_8007 = "[MELDING-8007]:";

    private static final String MELDING_GEEN_IO_EXCEPTION_VERWACHT = "Geen IOException verwacht";

    @Mock
    private SSLSocket socket;

    @Mock
    private OutputStream out;

    @Mock
    private InputStream in;

    @Mock
    protected Resource truststore;

    @Mock
    protected Resource keystore;

    @InjectMocks
    private SslConnectionImpl conn;

    @Test
    public void getSetHostname() {
        final String hostname = "hostname";
        conn.setHostname(hostname);
        Assert.assertEquals(hostname, conn.getHostname());
    }

    @Test
    public void getSetPortNumber() {
        final int portNumber = 1212;
        conn.setPortNumber(portNumber);
        Assert.assertTrue(portNumber == conn.getPortNumber());
    }

    @Test
    public void readMoreThenSpdLengthField() {
        try {
            Mockito.when(socket.isClosed()).thenReturn(false);
            Mockito.when(in.read(Matchers.any(byte[].class), Matchers.anyInt(), Matchers.anyInt())).thenReturn(6);
            conn.read();
        } catch (final ConnectionException ce) {
            Assert.assertTrue(ce.getMessage().startsWith(MELDING_8007));
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }

    @Test
    public void readLessThenZero() {
        try {
            Mockito.when(socket.isClosed()).thenReturn(false);
            Mockito.when(in.read(Matchers.any(byte[].class), Matchers.anyInt(), Matchers.anyInt())).thenReturn(-1);
            conn.read();
        } catch (final ConnectionException ce) {
            Assert.assertTrue(ce.getMessage().startsWith(MELDING_8007));
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }

    @Test
    public void readIOException() {
        try {
            Mockito.when(socket.isClosed()).thenReturn(false);
            Mockito.when(in.read(Matchers.any(byte[].class), Matchers.anyInt(), Matchers.anyInt())).thenThrow(new IOException());

            conn.read();
            Assert.fail("Er wordt een ConnectionException verwacht");
        } catch (final ConnectionException ce) {
            Assert.assertTrue(ce.getMessage().startsWith(MELDING_8007));
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }

    @Test
    public void read() {
        try {
            final int lengthSize = 5;
            final int mesgSize = 11;
            final byte[] buffer = new byte[SpdConstants.MAX_SPD_LEN];
            final String bericht = "00011testbericht" + SpdConstants.TERMINATOR;
            System.arraycopy(bericht.getBytes(), 0, buffer, 0, bericht.length());
            Mockito.when(socket.isClosed()).thenReturn(false);
            Mockito.when(in.read(Matchers.any(byte[].class), Matchers.anyInt(), Matchers.anyInt())).thenAnswer(new Answer<Integer>() {

                @Override
                public Integer answer(final InvocationOnMock invocation) {
                    final int offset = (Integer) invocation.getArguments()[1];
                    final byte[] buf = (byte[]) invocation.getArguments()[0];
                    int aantalRead = 0;
                    String value = "";
                    if (offset == 0) {
                        value = bericht.substring(0, lengthSize);
                    } else if (offset == 5) {
                        value = bericht.substring(offset, offset + mesgSize);
                    } else {
                        value = SpdConstants.TERMINATOR;
                    }
                    aantalRead = value.length();
                    System.arraycopy(value.getBytes(), 0, buf, offset, aantalRead);
                    return aantalRead;
                }
            });
            final Message mesg = conn.read();
            Assert.assertNotNull(mesg);
            Assert.assertTrue("Berichten zijn niet gelijk", Arrays.equals(buffer, mesg.getMessageFromMailbox()));
            Assert.assertEquals(LENGTE_VAN_DE_BERICHTEN_NIET_GELIJK, bericht.length() - SpdConstants.TERMINATOR.length(), mesg.getLength());
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }

    @Test
    public void readLengthInSteps() {
        try {
            final int lengthSize = 2;
            final int mesgSize = 11;
            final byte[] buffer = new byte[SpdConstants.MAX_SPD_LEN];
            final String bericht = "00011testbericht" + SpdConstants.TERMINATOR;
            System.arraycopy(bericht.getBytes(), 0, buffer, 0, bericht.length());
            Mockito.when(socket.isClosed()).thenReturn(false);
            Mockito.when(in.read(Matchers.any(byte[].class), Matchers.anyInt(), Matchers.anyInt())).thenAnswer(new Answer<Integer>() {
                @Override
                public Integer answer(final InvocationOnMock invocation) {
                    final int offset = (Integer) invocation.getArguments()[1];
                    final byte[] buf = (byte[]) invocation.getArguments()[0];
                    int aantalRead = 0;
                    String value = "";
                    if (offset == 0) {
                        value = bericht.substring(0, lengthSize);
                    } else if (offset == 2) {
                        value = bericht.substring(offset, offset + 3);
                    } else if (offset == 5) {
                        value = bericht.substring(offset, offset + mesgSize);
                    } else {
                        value = SpdConstants.TERMINATOR;
                    }
                    aantalRead = value.length();
                    System.arraycopy(value.getBytes(), 0, buf, offset, aantalRead);
                    return aantalRead;
                }
            });
            final Message mesg = conn.read();
            Assert.assertNotNull(mesg);
            Assert.assertTrue("Berichten zijn niet gelijk", Arrays.equals(buffer, mesg.getMessageFromMailbox()));
            Assert.assertEquals(LENGTE_VAN_DE_BERICHTEN_NIET_GELIJK, bericht.length() - SpdConstants.TERMINATOR.length(), mesg.getLength());
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }

    @Test
    public void readLengthLengthNotANumber() {
        final byte[] buffer = new byte[SpdConstants.MAX_SPD_LEN];
        try {
            final String bericht = "0000A";
            System.arraycopy(bericht.getBytes(), 0, buffer, 0, bericht.length());
            Mockito.when(socket.isClosed()).thenReturn(false);
            Mockito.when(in.read(Matchers.any(byte[].class), Matchers.anyInt(), Matchers.anyInt())).thenAnswer(new Answer<Integer>() {

                @Override
                public Integer answer(final InvocationOnMock invocation) {
                    final int offset = (Integer) invocation.getArguments()[1];
                    final byte[] buf = (byte[]) invocation.getArguments()[0];
                    int aantalRead = 0;
                    aantalRead = bericht.length();
                    System.arraycopy(bericht.getBytes(), 0, buf, offset, aantalRead);
                    return aantalRead;
                }
            });
            final Message mesg = conn.read();
            Assert.assertNotNull(mesg);
            Assert.assertTrue("Bericht is niet leeg", Arrays.equals(buffer, mesg.getMessageFromMailbox()));
            Assert.assertEquals(LENGTE_VAN_DE_BERICHTEN_NIET_GELIJK, -1, mesg.getLength());
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }

    @Test
    public void readLengthReadInputIOException() {
        final int lengthSize = 5;
        final byte[] buffer = new byte[SpdConstants.MAX_SPD_LEN];
        final String bericht = "00011testbericht" + SpdConstants.TERMINATOR;
        System.arraycopy(bericht.getBytes(), 0, buffer, 0, bericht.length());
        try {
            Mockito.when(socket.isClosed()).thenReturn(false);
            Mockito.when(in.read(Matchers.any(byte[].class), Matchers.anyInt(), Matchers.anyInt())).thenAnswer(new Answer<Integer>() {

                @Override
                public Integer answer(final InvocationOnMock invocation) throws IOException {
                    final int offset = (Integer) invocation.getArguments()[1];
                    final byte[] buf = (byte[]) invocation.getArguments()[0];
                    int aantalRead = 0;
                    String value = "";
                    if (offset == 0) {
                        value = bericht.substring(0, lengthSize);
                    } else if (offset == 5) {
                        throw new IOException();
                    }
                    aantalRead = value.length();
                    System.arraycopy(value.getBytes(), 0, buf, offset, aantalRead);
                    return aantalRead;
                }
            });
            final Message mesg = conn.read();
            Assert.assertNotNull(mesg);
            Assert.assertTrue("Bericht is niet leeg", Arrays.equals(buffer, mesg.getMessageFromMailbox()));
            Assert.assertEquals(LENGTE_VAN_DE_BERICHTEN_NIET_GELIJK, -1, mesg.getLength());
        } catch (final ConnectionException ce) {
            Assert.assertTrue(ce.getMessage().startsWith(MELDING_8007));
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }

    @Test
    public void write() {
        final byte[] buffer = new byte[SpdConstants.MAX_SPD_LEN];
        final Message mesg = new Message();
        mesg.setMessageFromMailbox(buffer);

        try {
            Mockito.when(socket.isClosed()).thenReturn(false);

            conn.write(mesg);
            Mockito.verify(out, Mockito.times(1)).write(buffer);
            Mockito.verify(out, Mockito.times(1)).flush();
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }

    @Test
    public void writeIOException() {
        final byte[] buffer = new byte[SpdConstants.MAX_SPD_LEN];
        final Message mesg = new Message();
        mesg.setMessageFromMailbox(buffer);

        try {
            Mockito.when(socket.isClosed()).thenReturn(false);
            Mockito.doThrow(new IOException()).when(out).write(buffer);
            conn.write(mesg);
            Mockito.verify(out, Mockito.times(1)).write(buffer);
        } catch (final ConnectionException ce) {
            Assert.assertTrue(ce.getMessage().startsWith(MELDING_8007));
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }

    @Test
    public void disconnect() {
        conn.disconnect();
        try {
            Mockito.verify(socket, Mockito.times(1)).close();
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }

    @Test
    public void disconnectIOException() {
        try {
            Mockito.doThrow(new IOException()).when(socket).close();
            conn.disconnect();
            Mockito.verify(socket, Mockito.times(1)).close();
        } catch (final ConnectionException ce) {
            Assert.assertTrue(ce.getMessage().startsWith("[MELDING-8005]:"));
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }
}
