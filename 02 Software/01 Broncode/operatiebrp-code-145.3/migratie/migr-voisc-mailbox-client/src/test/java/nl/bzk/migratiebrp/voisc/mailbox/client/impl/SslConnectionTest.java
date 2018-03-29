/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.mailbox.client.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import javax.net.ssl.SSLSocket;
import nl.bzk.migratiebrp.voisc.mailbox.client.exception.ConnectionException;
import nl.bzk.migratiebrp.voisc.spd.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.exception.MessagesCodes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@RunWith(MockitoJUnitRunner.class)
public class SslConnectionTest {

    private static final String LENGTE_VAN_DE_BERICHTEN_NIET_GELIJK = "Lengte van de berichten niet gelijk";

    private static final String MELDING_8007 = "[MELDING-8007]:";

    private static final String MELDING_GEEN_IO_EXCEPTION_VERWACHT = "Geen IOException verwacht";

    private static final String TERMINATOR = "00000";
    @Spy
    protected Resource truststore = new ClassPathResource("keystores/truststore.jks");
    @Spy
    protected Resource keystore = new ClassPathResource("keystores/keystore.jks");
    @Mock
    private SSLSocket socket;
    @Mock
    private OutputStream out;
    @Mock
    private InputStream in;
    @InjectMocks
    private SslConnectionImpl conn;

    @Before
    public void setup() throws IOException, URISyntaxException {
        // Mockito.when(keystore.getFile()).thenReturn(Paths.get(ClassLoader.getSystemResource("keystores/keystore.jks").toURI()).toFile());
        // Mockito.when(truststore.getFile()).thenReturn(Paths.get(ClassLoader.getSystemResource("keystores/truststore.jks").toURI()).toFile());
    }

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
            final String bericht = "00011testbericht" + TERMINATOR;
            System.arraycopy(bericht.getBytes(), 0, buffer, 0, bericht.length());
            Mockito.when(socket.isClosed()).thenReturn(false);
            Mockito.when(in.read(Matchers.any(byte[].class), Matchers.anyInt(), Matchers.anyInt())).thenAnswer(new Answer<Integer>() {

                @Override
                public Integer answer(final InvocationOnMock invocation) {
                    final int offset = (Integer) invocation.getArguments()[1];
                    final byte[] buf = (byte[]) invocation.getArguments()[0];
                    int aantalRead;
                    String value;
                    if (offset == 0) {
                        value = bericht.substring(0, lengthSize);
                    } else if (offset == 5) {
                        value = bericht.substring(offset, offset + mesgSize);
                    } else {
                        value = TERMINATOR;
                    }
                    aantalRead = value.length();
                    System.arraycopy(value.getBytes(), 0, buf, offset, aantalRead);
                    return aantalRead;
                }
            });
            final byte[] actual = conn.read();
            Assert.assertNotNull(actual);
            Assert.assertTrue("Berichten zijn niet gelijk", Arrays.equals(Arrays.copyOf(buffer, actual.length), actual));
            Assert.assertEquals(LENGTE_VAN_DE_BERICHTEN_NIET_GELIJK, bericht.length(), actual.length);
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
            final String bericht = "00011testbericht" + TERMINATOR;
            System.arraycopy(bericht.getBytes(), 0, buffer, 0, bericht.length());
            Mockito.when(socket.isClosed()).thenReturn(false);
            Mockito.when(in.read(Matchers.any(byte[].class), Matchers.anyInt(), Matchers.anyInt())).thenAnswer(new Answer<Integer>() {
                @Override
                public Integer answer(final InvocationOnMock invocation) {
                    final int offset = (Integer) invocation.getArguments()[1];
                    final byte[] buf = (byte[]) invocation.getArguments()[0];
                    int aantalRead;
                    String value;
                    if (offset == 0) {
                        value = bericht.substring(0, lengthSize);
                    } else if (offset == 2) {
                        value = bericht.substring(offset, offset + 3);
                    } else if (offset == 5) {
                        value = bericht.substring(offset, offset + mesgSize);
                    } else {
                        value = TERMINATOR;
                    }
                    aantalRead = value.length();
                    System.arraycopy(value.getBytes(), 0, buf, offset, aantalRead);
                    return aantalRead;
                }
            });
            final byte[] actual = conn.read();
            Assert.assertNotNull(actual);
            Assert.assertTrue("Berichten zijn niet gelijk", Arrays.equals(Arrays.copyOf(buffer, actual.length), actual));
            Assert.assertEquals(LENGTE_VAN_DE_BERICHTEN_NIET_GELIJK, bericht.length(), actual.length);
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
                    int aantalRead;
                    aantalRead = bericht.length();
                    System.arraycopy(bericht.getBytes(), 0, buf, offset, aantalRead);
                    return aantalRead;
                }
            });
            final byte[] actual = conn.read();
            Assert.assertNotNull(actual);
            Assert.assertEquals(LENGTE_VAN_DE_BERICHTEN_NIET_GELIJK, 0, actual.length);
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }

    @Test
    public void readLengthReadInputIOException() {
        final int lengthSize = 5;
        final byte[] buffer = new byte[SpdConstants.MAX_SPD_LEN];
        final String bericht = "00011testbericht" + TERMINATOR;
        System.arraycopy(bericht.getBytes(), 0, buffer, 0, bericht.length());
        try {
            Mockito.when(socket.isClosed()).thenReturn(false);
            Mockito.when(in.read(Matchers.any(byte[].class), Matchers.anyInt(), Matchers.anyInt())).thenAnswer(new Answer<Integer>() {

                @Override
                public Integer answer(final InvocationOnMock invocation) throws IOException {
                    final int offset = (Integer) invocation.getArguments()[1];
                    final byte[] buf = (byte[]) invocation.getArguments()[0];
                    int aantalRead;
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
            final byte[] actual = conn.read();
            Assert.assertNotNull(actual);
            Assert.assertEquals(LENGTE_VAN_DE_BERICHTEN_NIET_GELIJK, 0, actual.length);

        } catch (final ConnectionException ce) {
            Assert.assertTrue(ce.getMessage().startsWith(MELDING_8007));
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }

    @Test
    public void write() {
        final byte[] buffer = new byte[SpdConstants.MAX_SPD_LEN];

        try {
            Mockito.when(socket.isClosed()).thenReturn(false);

            conn.write(buffer);
            Mockito.verify(out, Mockito.times(1)).write(buffer);
            Mockito.verify(out, Mockito.times(1)).flush();
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }

    @Test
    public void writeIOException() {
        final byte[] buffer = new byte[SpdConstants.MAX_SPD_LEN];

        try {
            Mockito.when(socket.isClosed()).thenReturn(false);
            Mockito.doThrow(new IOException()).when(out).write(buffer);
            conn.write(buffer);
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
            assertThat(ce.getMessage(), startsWith("[MELDING-" + MessagesCodes.ERRMSG_VOSPG_SSL_CONNECTION_ERROR.getErrorCode() + "]:"));
        } catch (final IOException e) {
            Assert.fail(MELDING_GEEN_IO_EXCEPTION_VERWACHT);
        }
    }

    @Test
    public void connectMissingKeystore() {
        keystore = new ClassPathResource("does_not_exist.jks");
        MockitoAnnotations.initMocks(this);
        try {
            conn.connect();
        } catch (ConnectionException ex) {
            assertThat(ex.getMessage(), startsWith("[MELDING-" + MessagesCodes.ERRMSG_SSL_MISSING_KEYSTORE.getErrorCode() + "]:"));
        }
    }

    @Test
    public void connectMissingTruststore() {
        truststore = new ClassPathResource("does_not_exist.jks");
        MockitoAnnotations.initMocks(this);
        conn.setKeystorePassword("changeit");
        conn.setKeyPassword("changeit");
        try {
            conn.connect();
        } catch (ConnectionException ex) {
            ex.printStackTrace();
            assertThat(
                    ex.getMessage(),
                    startsWith("[MELDING-" + MessagesCodes.ERRMSG_SSL_MISSING_KEYSTORE.getErrorCode()
                            + "]:  Keystore kan niet gevonden worden: does_not_exist.jks"));
        }
    }

    @Test(expected = ConnectionException.class)
    public void connectWrongKeystorePassword() {
        conn.setKeystorePassword("wrong_password");
        conn.setKeyPassword("changeit");
        conn.setTrustStorePassword("changeit");
        conn.connect();
    }

    @Test(expected = ConnectionException.class)
    public void connectNoKeystorePassword() {
        conn.setKeyPassword("changeit");
        conn.setTrustStorePassword("changeit");
        conn.connect();
    }

    @Test
    public void connectWrongKeystorePasswordShouldFailWithCorrectMessage() {
        conn.setKeystorePassword("wrong_password");
        conn.setKeyPassword("changeit");
        conn.setTrustStorePassword("changeit");
        try {
            conn.connect();
        } catch (ConnectionException ex) {
            assertThat(
                    ex.getMessage(),
                    startsWith("[MELDING-" + MessagesCodes.ERRMSG_SSL_INCORRECT_KEYSTORE_PASSWORD.getErrorCode()
                            + "]:  Incorrect wachtwoord voor keystore: keystore.jks"));
        }
    }

    @Test(expected = ConnectionException.class)
    public void connectWrongSslPassword() {
        conn.setKeystorePassword("changeit");
        conn.setTrustStorePassword("changeit");
        conn.setKeyPassword("wrong_password");
        conn.connect();
    }

    @Test(expected = ConnectionException.class)
    public void connectNoSslPassword() {
        conn.setKeystorePassword("changeit");
        conn.setTrustStorePassword("changeit");
        conn.connect();
    }

    @Test
    public void connectWrongSslPasswordShouldFailWithCorrectMessage() {
        conn.setKeystorePassword("changeit");
        conn.setKeyPassword("wrong_password");
        conn.setTrustStorePassword("changeit");
        try {
            conn.connect();
        } catch (ConnectionException ex) {
            assertThat(ex.getMessage(), startsWith("[MELDING-" + MessagesCodes.ERRMSG_VOSPG_SSL_INCORRECT_CERT_PASSWORD.getErrorCode() + "]:"));
        }
    }

    @Test(expected = ConnectionException.class)
    public void connectWrongTruststorePassword() {
        conn.setKeystorePassword("changeit");
        conn.setKeyPassword("changeit");
        conn.setTrustStorePassword("wrong_password");
        conn.connect();
    }

    @Test(expected = ConnectionException.class)
    public void connectNoTruststorePassword() {
        conn.setKeystorePassword("changeit");
        conn.setKeyPassword("changeit");
        conn.connect();
    }

    @Test
    public void connectWrongTruststorePasswordShouldFailWithCorrectMessage() {
        conn.setKeystorePassword("changeit");
        conn.setKeyPassword("changeit");
        conn.setTrustStorePassword("wrong_password");
        try {
            conn.connect();
        } catch (ConnectionException ex) {
            assertThat(
                    ex.getMessage(),
                    startsWith("[MELDING-" + MessagesCodes.ERRMSG_SSL_INCORRECT_KEYSTORE_PASSWORD.getErrorCode()
                            + "]:  Incorrect wachtwoord voor keystore: truststore.jks"));
        }
    }
}
