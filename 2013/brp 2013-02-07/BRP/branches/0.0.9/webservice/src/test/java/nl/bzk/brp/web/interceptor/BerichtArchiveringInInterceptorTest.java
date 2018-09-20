/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import nl.bzk.brp.business.dto.BerichtenIds;
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

/** Unit test class voor de {@link BerichtArchiveringInInterceptor} class. */
public class BerichtArchiveringInInterceptorTest {

    private static BerichtArchiveringInInterceptor interceptor;

    @Mock
    private ArchiveringService     archiveringService;
    private ArgumentCaptor<String> gearchiveerdBericht;

    @Test
    public void testBerichtArchiveringIn() throws UnsupportedEncodingException {
        Message bericht = BerichtArchiveringTestUtil.bouwInMessage(Integer.valueOf(200), "UTF-8",
            "text/xml", BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"),
            "<test>Dit is een test</test>");

        interceptor.handleMessage(bericht);
        Mockito.verify(archiveringService, Mockito.times(1)).archiveer(gearchiveerdBericht.capture());

        Assert.assertNotNull(gearchiveerdBericht.getValue());
        Assert.assertFalse(gearchiveerdBericht.getValue().trim().length() == 0);
        Assert.assertTrue(gearchiveerdBericht.getValue().contains("<test>Dit is een test</test>"));

        // Controleren of ids op context zijn geplaatst
        Assert.assertTrue(bericht.getExchange().containsKey(ArchiveringBericht.BERICHT_ARCHIVERING_IN_ID));
        Assert.assertNotNull(bericht.getExchange().get(ArchiveringBericht.BERICHT_ARCHIVERING_IN_ID));
        Assert.assertTrue(bericht.getExchange().containsKey(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID));
        Assert.assertNotNull(bericht.getExchange().get(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID));
    }

    @Test
    public void testBerichtArchiveringInMetOngeldigeXml() throws UnsupportedEncodingException {
        Message bericht = BerichtArchiveringTestUtil.bouwInMessage(Integer.valueOf(200), "UTF-8",
            "text/xml", BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"),
            "Dit is een test");

        interceptor.handleMessage(bericht);
        Mockito.verify(archiveringService, Mockito.times(1)).archiveer(gearchiveerdBericht.capture());

        Assert.assertNotNull(gearchiveerdBericht.getValue());
        Assert.assertFalse(gearchiveerdBericht.getValue().trim().length() == 0);
        Assert.assertTrue(gearchiveerdBericht.getValue().contains("Dit is een test"));

        // Controleren of ids op context zijn geplaatst
        Assert.assertTrue(bericht.getExchange().containsKey(ArchiveringBericht.BERICHT_ARCHIVERING_IN_ID));
        Assert.assertNotNull(bericht.getExchange().get(ArchiveringBericht.BERICHT_ARCHIVERING_IN_ID));
        Assert.assertTrue(bericht.getExchange().containsKey(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID));
        Assert.assertNotNull(bericht.getExchange().get(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID));
    }

    @Test
    public void testBerichtArchiveringInZonderInputStream() throws IOException {
        Message bericht = BerichtArchiveringTestUtil.bouwInMessage(Integer.valueOf(200), "UTF-8",
            "text/xml", BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"),
            "<test>Dit is een test</test>");
        bericht.removeContent(InputStream.class);
        bericht.setContent(InputStream.class, null);

        interceptor.handleMessage(bericht);
        Mockito.verify(archiveringService, Mockito.times(1)).archiveer(gearchiveerdBericht.capture());

        Assert.assertNotNull(gearchiveerdBericht.getValue());
        Assert.assertFalse(gearchiveerdBericht.getValue().trim().length() == 0);
        Assert.assertFalse(gearchiveerdBericht.getValue().contains("Payload"));

        // Controleren of ids op context zijn geplaatst
        Assert.assertTrue(bericht.getExchange().containsKey(ArchiveringBericht.BERICHT_ARCHIVERING_IN_ID));
        Assert.assertNotNull(bericht.getExchange().get(ArchiveringBericht.BERICHT_ARCHIVERING_IN_ID));
        Assert.assertTrue(bericht.getExchange().containsKey(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID));
        Assert.assertNotNull(bericht.getExchange().get(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID));
    }

    @Test
    public void testBerichtArchiveringInZonderRequestUrl() throws UnsupportedEncodingException {
        Message bericht = BerichtArchiveringTestUtil.bouwInMessage(Integer.valueOf(200), "UTF-8",
            "text/xml", BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"),
            "<test>Dit is een test</test>");
        bericht.remove(Message.REQUEST_URL);

        interceptor.handleMessage(bericht);
        Mockito.verify(archiveringService, Mockito.times(1)).archiveer(gearchiveerdBericht.capture());

        Assert.assertFalse(gearchiveerdBericht.getValue().contains("Adres:"));
    }

    @Test
    public void testAdresToevoegingMetQueryString() throws UnsupportedEncodingException {
        Message bericht = BerichtArchiveringTestUtil.bouwInMessage(Integer.valueOf(200), "UTF-8",
            "text/xml", BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"),
            "<test>Dit is een test</test>");
        bericht.put(Message.QUERY_STRING, "test");

        interceptor.handleMessage(bericht);
        Mockito.verify(archiveringService, Mockito.times(1)).archiveer(gearchiveerdBericht.capture());
        Assert.assertTrue(gearchiveerdBericht.getValue().contains(
            "http://localhost:8080/brp/services/bijhouding?test"));
    }


    /** Initialisatie methode die voor elke testmethode wordt aangeroepen en die de mocks initialiseert. */
    @Before
    public void initTest() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(archiveringService.archiveer(Mockito.anyString())).thenReturn(new BerichtenIds(1L, 2L));

        // Zet de Mock van de archiveringsservice in de interceptor
        ReflectionTestUtils.setField(interceptor, "archiveringService", archiveringService);

        // Initialiseer de "argument capturing" om het argument waarmee de archiveer service wordt aangeroepen te vangen
        gearchiveerdBericht = ArgumentCaptor.forClass(String.class);
    }

    /**
     * Methode die eenmalig voor deze class wordt aangeroepen en die de interceptor initialiseert. De interceptor wordt
     * maar eenmalig geinitialiseerd en niet per test, daar dit in de praktijk ook maar eenmalig is. Alle berichten
     * maken immers gebruik van dezelfde interceptor.
     */
    @BeforeClass
    public static void initClass() {
        interceptor = new BerichtArchiveringInInterceptor();
    }

}

