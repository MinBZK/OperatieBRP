/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.interceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import nl.bzk.brp.business.service.ArchiveringService;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test class voor de {@link BerichtArchiveringUitInterceptor} class. */
public class BerichtArchiveringUitInterceptorTest {

    private static BerichtArchiveringUitInterceptor interceptor;

    @Mock
    private ArchiveringService     archiveringService;
    private ArgumentCaptor<Long>   gearchiveerdBerichtId;
    private ArgumentCaptor<String> gearchiveerdBericht;

    @Test
    public void testBerichtArchiveringUitZonderAdres() throws IOException {
        Message bericht = BerichtArchiveringTestUtil.bouwUitMessage(Integer.valueOf(200), "UTF-8",
            "text/xml", BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"));
        bericht.getExchange().put(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID, 2L);

        handelBerichtAfMetContent(bericht, "<test>Dit is een test</test>");

        Mockito.verify(archiveringService, Mockito.times(1)).werkDataBij(gearchiveerdBerichtId.capture(),
            gearchiveerdBericht.capture());

        Assert.assertNotNull(gearchiveerdBerichtId.getValue());
        Assert.assertEquals(Long.valueOf(2), gearchiveerdBerichtId.getValue());
        Assert.assertNotNull(gearchiveerdBericht.getValue());
        Assert.assertFalse(gearchiveerdBericht.getValue().trim().length() == 0);
        Assert.assertTrue(gearchiveerdBericht.getValue().contains("<test>Dit is een test</test>"));
    }

    @Test
    public void testBerichtArchiveringUitMetAdres() throws IOException {
        Message bericht = BerichtArchiveringTestUtil.bouwUitMessage(Integer.valueOf(200), "UTF-8",
            "text/xml", BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"));
        bericht.put(Message.ENDPOINT_ADDRESS, "ENDPOINT_ADRESS");
        bericht.getExchange().put(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID, 2L);

        handelBerichtAfMetContent(bericht, "<test>Dit is een andere test</test>");

        Mockito.verify(archiveringService, Mockito.times(1)).werkDataBij(gearchiveerdBerichtId.capture(),
            gearchiveerdBericht.capture());

        Assert.assertTrue(gearchiveerdBericht.getValue().contains("ENDPOINT_ADRESS"));
        Assert.assertTrue(gearchiveerdBericht.getValue().contains("<test>Dit is een andere test</test>"));
    }

    private void handelBerichtAfMetContent(final Message bericht, final String content) {
        interceptor.handleMessage(bericht);

        OutputStream outputStream = bericht.getContent(OutputStream.class);
        if (outputStream != null) {
            PrintWriter writer = new PrintWriter(outputStream);
            writer.write(content);
            writer.close();
        }
    }

    @Test
    public void testBerichtArchiveringUitMetOngeldigeXml() throws IOException {
        Message bericht = BerichtArchiveringTestUtil.bouwUitMessage(Integer.valueOf(200), "UTF-8",
            "text/xml", BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"));
        bericht.getExchange().put(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID, 2L);

        handelBerichtAfMetContent(bericht, "Test");

        Mockito.verify(archiveringService, Mockito.times(1)).werkDataBij(gearchiveerdBerichtId.capture(),
            gearchiveerdBericht.capture());

        Assert.assertNotNull(gearchiveerdBerichtId.getValue());
        Assert.assertEquals(Long.valueOf(2), gearchiveerdBerichtId.getValue());
        Assert.assertNotNull(gearchiveerdBericht.getValue());
        Assert.assertFalse(gearchiveerdBericht.getValue().trim().length() == 0);
        Assert.assertTrue(gearchiveerdBericht.getValue().contains("Test"));
    }

    @Test
    public void testBerichtArchiveringUitZonderOutputStream() throws UnsupportedEncodingException {
        Message bericht = BerichtArchiveringTestUtil.bouwUitMessage(Integer.valueOf(200), "UTF-8",
            "text/xml", BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"));
        bericht.getExchange().put(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID, 2L);
        bericht.removeContent(OutputStream.class);

        handelBerichtAfMetContent(bericht, "<test>Test</test>");
        Mockito.verifyZeroInteractions(archiveringService);
    }

    /** Initialisatie methode die voor elke testmethode wordt aangeroepen en die de mocks initialiseert. */
    @Before
    public void initTest() {
        MockitoAnnotations.initMocks(this);

        // Zet de Mock van de archiveringsservice in de interceptor
        ReflectionTestUtils.setField(interceptor, "archiveringService", archiveringService);

        // Initialiseer de "argument capturing" om het argumenten waarmee de archiveer service wordt aangeroepen te
        // vangen
        gearchiveerdBerichtId = ArgumentCaptor.forClass(Long.class);
        gearchiveerdBericht = ArgumentCaptor.forClass(String.class);
    }

    /**
     * Methode die eenmalig voor deze class wordt aangeroepen en die de interceptor initialiseert. De interceptor wordt
     * maar eenmalig geinitialiseerd en niet per test, daar dit in de praktijk ook maar eenmalig is. Alle berichten
     * maken immers gebruik van dezelfde interceptor.
     */
    @BeforeClass
    public static void initClass() {
        interceptor = new BerichtArchiveringUitInterceptor();
    }

}
