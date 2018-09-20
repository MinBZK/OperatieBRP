/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStoreException;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerConfigurationException;

import org.apache.ws.security.WSSecurityException;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class BevragingServiceIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void testBevraging() throws WSSecurityException, IOException, TransformerConfigurationException,
            SAXException, SOAPException, KeyStoreException, ParserConfigurationException,
            MarshalException, XMLSignatureException
    {
        Node antwoord = verzendBerichtNaarService("bevraging.xml");

        Assert.assertEquals("OpvragenPersoonResultaat", antwoord.getFirstChild().getNodeName());
        Node aantal = antwoord.getFirstChild().getFirstChild();
        Assert.assertEquals("aantal", aantal.getNodeName());
        Assert.assertEquals("persoon",
                antwoord.getFirstChild().getFirstChild().getNextSibling().getNodeName());
    }

    @Override
    URL getWsdlUrl() throws MalformedURLException {
        return new URL("http://localhost:8181/brp/services/bevraging?wsdl");
    }

    @Override
    QName getServiceQName() {
        return new QName("http://www.brp.bzk.nl/bevraging/ws/service", "BevragingService");
    }

    @Override
    QName getPortName() {
        return new QName("http://www.brp.bzk.nl/bevraging/ws/service", "BevragingPort");
    }
}
