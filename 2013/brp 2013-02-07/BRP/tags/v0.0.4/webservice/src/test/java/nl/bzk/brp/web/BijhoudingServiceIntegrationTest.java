/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import java.io.IOException;
import java.security.KeyStoreException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerConfigurationException;

import nl.bzk.brp.binding.ResultaatCode;
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
    public void testVerhuizing()
        throws WSSecurityException, IOException, TransformerConfigurationException, SAXException, SOAPException,
        KeyStoreException, ParserConfigurationException
    {
        Node antwoord = verzendBerichtNaarService("verhuizing_minimaal.xml");

        Assert.assertEquals("BerichtResultaat", antwoord.getFirstChild().getNodeName());
        Node resultaat = antwoord.getFirstChild().getFirstChild();
        Assert.assertEquals("resultaat", resultaat.getNodeName());
        Assert.assertEquals(ResultaatCode.GOED.getNaam(), resultaat.getTextContent());
        Assert.assertEquals("meldingen", resultaat.getNextSibling().getNodeName());
        Assert.assertFalse(resultaat.getNextSibling().hasChildNodes());
    }

}
