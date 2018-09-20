/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.kern.interceptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.TreeMap;

import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;

/**
 * Utility class voor alle tests die wat doen met BerichtArchivering. Deze utility class biedt onder andere methodes
 * voor het bouwen van een {@link org.apache.cxf.message.Message}.
 */
public final class BerichtArchiveringTestUtil {

    /**
     * Utility classes mogen niet worden geinstantieerd.
     * @throws IllegalAccessException indien deze utility class toch wordt geinstantieerd.
     */
    private BerichtArchiveringTestUtil() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * Bouwt een {@link org.apache.cxf.message.Message} instantie met de opgegeven waarden. Enkel waarden worden
     * overigens gezet op standaard
     * waarden.
     * @param responseCode de response code
     * @param encoding de encoding
     * @param contentType de content type
     * @param protocolHeaders de protocol headers
     * @param content de content
     * @return de message
     * @throws UnsupportedEncodingException de unsupported encoding exception
     */
    public static Message bouwInMessage(final Integer responseCode, final String encoding, final String contentType,
        final Object protocolHeaders, final String content) throws UnsupportedEncodingException
    {
        Message bericht = bouwMessage(responseCode, encoding, contentType, protocolHeaders);

        byte[] bytes;
        if (encoding == null || encoding.isEmpty()) {
            bytes = content.getBytes();
        } else {
            bytes = content.getBytes(encoding);
        }
        InputStream inputStream = new ByteArrayInputStream(bytes);
        bericht.setContent(InputStream.class, inputStream);

        return bericht;
    }

    /**
     * Bouwt een {@link org.apache.cxf.message.Message} instantie met de opgegeven waarden. Enkel waarden worden
     * overigens gezet op standaard
     * waarden.
     */
    public static Message bouwUitMessage(final Integer responseCode, final String encoding, final String contentType,
        final Object protocolHeaders) throws UnsupportedEncodingException
    {
        Message bericht = bouwMessage(responseCode, encoding, contentType, protocolHeaders);
        bericht.setContent(OutputStream.class, new ByteArrayOutputStream());

        return bericht;
    }

    private static Message bouwMessage(final Integer responseCode, final String encoding, final String contentType,
        final Object protocolHeaders) throws UnsupportedEncodingException
    {
        Message bericht = new MessageImpl();
        bericht.setExchange(new ExchangeImpl());

        voegToeAanMessage(bericht, Message.RESPONSE_CODE, responseCode);
        voegToeAanMessage(bericht, Message.ENCODING, encoding);
        voegToeAanMessage(bericht, Message.HTTP_REQUEST_METHOD, "POST");
        voegToeAanMessage(bericht, Message.CONTENT_TYPE, contentType);
        voegToeAanMessage(bericht, Message.PROTOCOL_HEADERS, protocolHeaders);
        voegToeAanMessage(bericht, Message.REQUEST_URL, "http://localhost:8080/brp/services/bijhouding");
        voegToeAanMessage(bericht, Message.QUERY_STRING, null);

        return bericht;
    }

    private static void voegToeAanMessage(final Message bericht, final String key, final Object waarde) {
        if (waarde != null) {
            bericht.put(key, waarde);
        }
    }

    /** Bouwt een map met de protocol headers, op basis van de opgegeven waarden en enkele standaard waarden. */
    public static TreeMap bouwProtocolHeaders(final String acceptEncoding, final String contentType) {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("accept-encoding", Arrays.asList(acceptEncoding));
        map.put("Content-Length", Arrays.asList("2049"));
        map.put("content-type", Arrays.asList(contentType));
        map.put("Host", Arrays.asList("localhost:8080"));
        map.put("SOAPAction",
            Arrays.asList("\"http://www.brp.bzak.nl/bijhouding/ws/webservice/BijhoudingPortType/bijhoudenRequest\""));
        map.put("User-Agent", Arrays.asList("Jakarta Commons-HttpClient/3.1"));
        return map;
    }

}
