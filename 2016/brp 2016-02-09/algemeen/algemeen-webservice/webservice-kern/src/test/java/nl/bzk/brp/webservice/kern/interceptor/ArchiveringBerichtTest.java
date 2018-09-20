/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.kern.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;
import javax.xml.transform.TransformerException;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.junit.Assert;
import org.junit.Test;

/** Unit test class voor de {@link ArchiveringBericht} class. */
public class ArchiveringBerichtTest {

    @Test
    public void testConstructorOpZettenVelden() throws UnsupportedEncodingException {
        final ArchiveringBericht bericht =
            new ArchiveringBericht(BerichtArchiveringTestUtil.bouwInMessage(200, "UTF-8", "text/xml",
                BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"),
                "<test>Test</test>"), "Test");

        Assert.assertNull(bericht.getAdres());
        Assert.assertNotNull(bericht.getPayload());
        Assert.assertEquals(0, bericht.getPayload().length());
        Assert.assertEquals("Test", bericht.getKop());

        Assert.assertEquals("text/xml", bericht.getContentType());
        Assert.assertEquals("UTF-8", bericht.getEncoding());
        Assert.assertEquals("POST", bericht.getHttpMethode());
        Assert.assertEquals(Integer.valueOf(200), bericht.getResponseCode());
        Assert.assertNotNull(bericht.getHeader());
        Assert.assertTrue(((Map) bericht.getHeader()).containsValue(Arrays.asList("gzip,deflate")));
        Assert.assertTrue(((Map) bericht.getHeader()).containsValue(Arrays.asList("text/xml;charset=UTF-8")));
    }

    @Test
    public void testConstructorOpLegeVeldenIndienLeegInBericht() {
        final Message message = new MessageImpl();
        final ArchiveringBericht bericht = new ArchiveringBericht(message, "Test");

        Assert.assertNull(bericht.getAdres());
        Assert.assertNotNull(bericht.getPayload());
        Assert.assertEquals(0, bericht.getPayload().length());
        Assert.assertEquals("Test", bericht.getKop());

        Assert.assertNull(bericht.getContentType());
        Assert.assertNull(bericht.getEncoding());
        Assert.assertNull(bericht.getHttpMethode());
        Assert.assertNull(bericht.getResponseCode());
        Assert.assertNull(bericht.getHeader());
    }

    @Test
    public void testZettenVanAdres() throws UnsupportedEncodingException {
        final ArchiveringBericht bericht =
            new ArchiveringBericht(BerichtArchiveringTestUtil.bouwInMessage(200, "UTF-8", "text/xml",
                BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"),
                "<test>Test</test>"), "Test");

        Assert.assertNull(bericht.getAdres());
        bericht.setAdres("test");
        Assert.assertEquals("test", bericht.getAdres());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorMetNullVoorBericht() {
        new ArchiveringBericht(null, "Test");
    }

    @Test
    public void testGeenExceptiesInToStringMetGevuldeWaarden() throws UnsupportedEncodingException {
        final ArchiveringBericht bericht =
            new ArchiveringBericht(BerichtArchiveringTestUtil.bouwInMessage(200, "UTF-8", "text/xml",
                BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"),
                "<test>Test</test>"), "Test");

        Assert.assertNotNull(bericht.toString());
    }

    @Test
    public void testGeenExceptiesInToStringMetNullWaarden() throws UnsupportedEncodingException {
        final Message message = new MessageImpl();
        final ArchiveringBericht bericht = new ArchiveringBericht(message, "Test");

        Assert.assertNotNull(bericht.toString());
    }

    @Test
    public void testGeenExceptiesInToStringZonderNullWaarden() throws UnsupportedEncodingException {
        final Message message = new MessageImpl();
        message.put(Message.PROTOCOL_HEADERS, "HTTP");
        message.put(Message.HTTP_REQUEST_METHOD, "get");
        message.put(Message.RESPONSE_CODE, 404);
        message.put(Message.CONTENT_TYPE, "txt");
        message.put(Message.ENCODING, "ASCII");
        final ArchiveringBericht bericht = new ArchiveringBericht(message, "Test");
        bericht.setAdres("localhost");

        Assert.assertNotNull(bericht.toString());
    }

    @Test
    public void testGeenExceptiesInToStringMetLegeWaarden() throws UnsupportedEncodingException {
        final Message message = new MessageImpl();
        message.put(Message.PROTOCOL_HEADERS, "");
        message.put(Message.HTTP_REQUEST_METHOD, "");
        message.put(Message.RESPONSE_CODE, 404);
        message.put(Message.CONTENT_TYPE, "");
        message.put(Message.ENCODING, "");
        final ArchiveringBericht bericht = new ArchiveringBericht(message, "Test");
        bericht.setAdres("");

        Assert.assertNotNull(bericht.toString());
    }

    @Test
    public void testToStringZonderPayload() throws IOException, TransformerException {
        final ArchiveringBericht bericht =
            new ArchiveringBericht(BerichtArchiveringTestUtil.bouwInMessage(200, "UTF-8", "text/xml",
                BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"),
                ""), null);
        bericht.setAdres("http://localhost:8080/brp/services/bijhouding");

        final CachedOutputStream outputStream = new CachedOutputStream();
        bericht.vulPayload(outputStream);

        Assert.assertTrue(bericht.toString().contains("http://localhost:8080/brp/services/bijhouding"));
        Assert.assertTrue(bericht.toString().contains("200"));
        Assert.assertTrue(bericht.toString().contains("UTF-8"));
        Assert.assertTrue(bericht.toString().contains("POST"));
        Assert.assertTrue(bericht.toString().contains("text/xml"));
        Assert.assertTrue(bericht.toString().contains("gzip,deflate"));
        Assert.assertTrue(bericht.toString().contains("text/xml;charset=UTF-8"));
        Assert.assertFalse(bericht.toString().contains("Payload"));
    }

    @Test
    public void testToStringMetPayload() throws IOException, TransformerException {
        final ArchiveringBericht bericht =
            new ArchiveringBericht(BerichtArchiveringTestUtil.bouwInMessage(200, "UTF-8", "text/xml",
                BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"),
                "<test>Test Payload</test>"), null);
        bericht.setAdres("http://localhost:8080/brp/services/bijhouding");

        final CachedOutputStream outputStream = new CachedOutputStream();
        outputStream.write("<test>Test Payload</test>".getBytes());
        bericht.vulPayload(outputStream);

        Assert.assertTrue(bericht.toString().contains("http://localhost:8080/brp/services/bijhouding"));
        Assert.assertTrue(bericht.toString().contains("200"));
        Assert.assertTrue(bericht.toString().contains("UTF-8"));
        Assert.assertTrue(bericht.toString().contains("POST"));
        Assert.assertTrue(bericht.toString().contains("text/xml"));
        Assert.assertTrue(bericht.toString().contains("gzip,deflate"));
        Assert.assertTrue(bericht.toString().contains("text/xml;charset=UTF-8"));
        Assert.assertTrue(bericht.toString().contains("Test Payload"));
    }

    @Test
    public void testVulPayloadMetXml() throws IOException, TransformerException {
        final ArchiveringBericht bericht =
            new ArchiveringBericht(BerichtArchiveringTestUtil.bouwInMessage(200, "UTF-8", "text/xml",
                BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"),
                "<test>Test</test>"), "Test");

        final CachedOutputStream outputStream = new CachedOutputStream();
        outputStream.write("<test>Test</test>".getBytes());
        bericht.vulPayload(outputStream);

        Assert.assertTrue(bericht.getPayload().toString().contains("<test>Test</test>"));
        Assert.assertTrue(bericht.getPayload().toString().startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
    }

    @Test
    public void testVulPayloadMetNietXmlMetEncoding() throws IOException, TransformerException {
        final ArchiveringBericht bericht =
            new ArchiveringBericht(BerichtArchiveringTestUtil.bouwInMessage(200, "UTF-8", "text/plain",
                BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/plain;charset=UTF-8"),
                "Test"), "Test");

        final CachedOutputStream outputStream = new CachedOutputStream();
        outputStream.write("Test".getBytes());
        bericht.vulPayload(outputStream);

        Assert.assertEquals("Test", bericht.getPayload().toString());
    }

    @Test
    public void testVulPayloadMetNietXmlZonderEncoding() throws IOException, TransformerException {
        final ArchiveringBericht bericht =
            new ArchiveringBericht(BerichtArchiveringTestUtil.bouwInMessage(200, null, "text/plain",
                BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/plain;charset=UTF-8"),
                "Test"), "Test");

        final CachedOutputStream outputStream = new CachedOutputStream();
        outputStream.write("Test".getBytes());
        bericht.vulPayload(outputStream);

        Assert.assertEquals("Test", bericht.getPayload().toString());
    }

    @Test
    public void testVulPayloadMetXmlZonderContentType() throws IOException, TransformerException {
        final ArchiveringBericht bericht =
            new ArchiveringBericht(BerichtArchiveringTestUtil.bouwInMessage(200, "UTF-8", null,
                BerichtArchiveringTestUtil.bouwProtocolHeaders("gzip,deflate", "text/xml;charset=UTF-8"),
                "<test>Test</test>"), "Test");

        final CachedOutputStream outputStream = new CachedOutputStream();
        outputStream.write("<test>Test</test>".getBytes());
        bericht.vulPayload(outputStream);

        Assert.assertEquals("<test>Test</test>", bericht.getPayload().toString());
    }

}
