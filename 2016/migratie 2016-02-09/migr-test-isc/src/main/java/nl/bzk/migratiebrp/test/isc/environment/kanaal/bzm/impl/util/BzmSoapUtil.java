/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.impl.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * SOAP Utilities.
 */
public final class BzmSoapUtil {
    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Private constructor.
     */
    private BzmSoapUtil() {

    }

    /**
     * Pakt het xmlBody bericht in een SOAP Envelop en geeft deze terug als SOAPMessage.
     * 
     * @param xmlBody
     *            String
     * @return message SOAPMessage
     */
    public static SOAPMessage maakSOAPBericht(final String xmlBody) {
        try {
            final MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
            final SOAPMessage soapMessage = messageFactory.createMessage();
            final Document bodyDocument = buildDocument(xmlBody);
            if (bodyDocument != null) {
                soapMessage.getSOAPBody().addDocument(bodyDocument);
                return soapMessage;
            }
        } catch (final SOAPException e) {
            LOG.error("Kan SOAP bericht niet aanmaken", e);
        }
        return null;
    }

    private static Document buildDocument(final String xmlBody) {
        final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;
        Document document = null;
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
            document = docBuilder.parse(new InputSource(new StringReader(xmlBody)));
        } catch (final ParserConfigurationException pce) {
            LOG.error("Kan DocumentBuilder niet aanmaken", pce);
        } catch (final
            SAXException
            | IOException e)
        {
            LOG.error("Kan string bericht niet parsen naar xml", e);
        }
        return document;
    }

    /**
     * Pakt het SOAP response uit en geeft alleen de SOAPBody terug.
     * 
     * @param response
     *            SOAPMessage
     * @return body String
     */
    public static String getSOAPResultaat(final SOAPMessage response) {
        String strBody = null;
        try {
            final Document doc = response.getSOAPBody().extractContentAsDocument();
            final Source source = new DOMSource(doc);
            final StringWriter stringWriter = new StringWriter();
            final Result result = new StreamResult(stringWriter);
            final TransformerFactory factory = TransformerFactory.newInstance();
            final Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(source, result);
            strBody = stringWriter.getBuffer().toString();
        } catch (final
            SOAPException
            | TransformerException e)
        {
            LOG.error("Fout bij het parsen van de SOAPBody naar String: " + e.getMessage());
        }

        return strBody;
    }

    /**
     * Parsed gegeven wsdlUrl naar een URL.
     * 
     * @param wsdlUrl
     *            String
     * @return url URL
     */
    public static URL getWsdlUrl(final String wsdlUrl) {
        URL url = null;
        try {
            url = new URL(wsdlUrl);
        } catch (final MalformedURLException e) {
            LOG.info("URL niet valide: " + wsdlUrl, e.getMessage());
        }
        return url;
    }
}
