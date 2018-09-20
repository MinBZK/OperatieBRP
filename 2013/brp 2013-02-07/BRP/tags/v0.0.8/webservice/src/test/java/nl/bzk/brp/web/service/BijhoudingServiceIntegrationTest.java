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

import nl.bzk.brp.web.bijhouding.VerwerkingsResultaat;
import org.apache.ws.security.WSSecurityException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


/** Testen tegen live webservices, gedeployed in een embedded Jetty Server. */
@RunWith(SpringJUnit4ClassRunner.class)
public class BijhoudingServiceIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void testVerhuizing() throws WSSecurityException, IOException, TransformerConfigurationException,
        SAXException, SOAPException, KeyStoreException, ParserConfigurationException,
        MarshalException, XMLSignatureException
    {
        zetActieVoorBericht("verhuizing");
        Node antwoord = verzendBerichtNaarService("migratie_Verhuizing_Bijhouding_v0001_MAX.xml");

        Assert.assertEquals("migratie_Verhuizing_BijhoudingResponse_v0001", antwoord.getFirstChild().getNodeName());
        Node stuurgegevens = antwoord.getFirstChild().getFirstChild();
        Assert.assertEquals("stuurgegevens", stuurgegevens.getNodeName());

        Node resultaat = stuurgegevens.getNextSibling();
        Assert.assertEquals("resultaat", resultaat.getNodeName());
        Assert.assertEquals(VerwerkingsResultaat.GOED.getCode(), resultaat.getFirstChild().getTextContent());
    }

    @Test
    public void testInschrijvingGeboorte() throws WSSecurityException, IOException, TransformerConfigurationException,
        SAXException, SOAPException, KeyStoreException, ParserConfigurationException,
        MarshalException, XMLSignatureException
    {
        zetActieVoorBericht("inschrijvingGeboorte");
        Node antwoord = verzendBerichtNaarService("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001.xml");

        Assert.assertEquals("afstamming_InschrijvingAangifteGeboorte_BijhoudingResponse_v0001",
            antwoord.getFirstChild().getNodeName());
        Node stuurgegevens = antwoord.getFirstChild().getFirstChild();
        Assert.assertEquals("stuurgegevens", stuurgegevens.getNodeName());

        Node resultaat = stuurgegevens.getNextSibling();
        Assert.assertEquals("resultaat", resultaat.getNodeName());
        Assert.assertEquals(VerwerkingsResultaat.GOED.getCode(), resultaat.getFirstChild().getTextContent());
    }

    @Override
    URL getWsdlUrl() throws MalformedURLException {
        return new URL("http://localhost:8181/brp/services/bijhouding?wsdl");
    }

    @Override
    QName getServiceQName() {
        return new QName("http://www.brp.bzk.nl/bijhouding/ws/service", "BijhoudingService");
    }

    @Override
    QName getPortName() {
        return new QName("http://www.brp.bzk.nl/bijhouding/ws/service", "BijhoudingPort");
    }
}
