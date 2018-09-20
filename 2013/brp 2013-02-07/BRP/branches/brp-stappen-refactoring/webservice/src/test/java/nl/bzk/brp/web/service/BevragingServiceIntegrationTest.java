/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.utils.XmlUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class BevragingServiceIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void testVraagDetailsPersoon() throws Exception {
        Node antwoord = verzendBerichtNaarService("bevragen_VraagDetailsPersoon.xml");

        Assert.assertEquals("brp:ALG_GeefDetailsPersoon_VR", antwoord.getFirstChild().getNodeName());
        Assert.assertEquals("brp:stuurgegevens", antwoord.getFirstChild().getFirstChild().getNodeName());
        Assert.assertEquals("brp:resultaat", antwoord.getFirstChild().getFirstChild().getNextSibling().getNodeName());
        Assert.assertEquals("brp:antwoord", antwoord.getFirstChild().getFirstChild().getNextSibling().getNextSibling()
                                                .getNodeName());

        // TODO schema validatie: weer aanzetten wanneer de xsd's definitief zijn
        //XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()),
        //    "/xsd/BRP0100_Bevraging_Berichten.xsd");
    }


    @Ignore("Aanzetten wanneer de bindingen voor deze bevraging af zijn (zie SIERRA-452)")
    @Test
    public void testVraagPersonenOpAdresInclusiefBetrokkenhedenOnbekend() throws Exception {
        Node antwoord = verzendBerichtNaarService("bevragen_VraagPersonenOpAdresInclusiefBetrokkenheden_Onbekend.xml");
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);

        Assert.assertEquals("brp:bevragen_VraagPersonenOpAdresInclusiefBetrokkenheden_Antwoord", antwoord.getFirstChild()
                                                                                                     .getNodeName());
        Assert.assertEquals("brp:stuurgegevens", antwoord.getFirstChild().getFirstChild().getNodeName());
        Assert.assertEquals("brp:resultaat", antwoord.getFirstChild().getFirstChild().getNextSibling().getNodeName());

        Assert.assertEquals("Er zijn geen personen gevonden die voldoen aan de opgegeven criteria.", antwoord
            .getFirstChild().getFirstChild().getNextSibling().getNextSibling().getFirstChild().getFirstChild()
            .getNextSibling().getNextSibling().getLastChild().getTextContent());
        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD.getCode(), XmlUtils.getNodeWaarde("//resultaat/verwerkingCode",
            document));
        // we verwachten dat we een INFO met de melding ALG0001 (== algemene fout).
        Assert.assertEquals(SoortMelding.INFORMATIE.getCode(), XmlUtils.getNodeWaarde("//meldingen/melding/soortCode",
            document));
        Assert.assertEquals(MeldingCode.ALG0001.getNaam(), XmlUtils.getNodeWaarde("//meldingen/melding/regelCode",
            document));
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()),
            "/xsd/BRP_Bevraging_Berichten.xsd");
    }

    @Ignore("Aanzetten wanneer de bindingen voor deze bevraging af zijn (zie SIERRA-452)")
    @Test
    public void testVraagPersonenOpAdresInclusiefBetrokkenheden() throws Exception {
        Node antwoord = verzendBerichtNaarService("bevragen_VraagPersonenOpAdresInclusiefBetrokkenheden.xml");
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        Assert.assertTrue("bevragen_VraagPersonenOpAdresInclusiefBetrokkenheden_Antwoord root node bestaat niet",
            XmlUtils.isNodeAanwezig("//bevragen_VraagPersonenOpAdresInclusiefBetrokkenheden_Antwoord", document));
        Assert.assertTrue("resultaat node bestaat niet", XmlUtils.isNodeAanwezig("//resultaat", document));
        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD.getCode(), XmlUtils.getNodeWaarde("//resultaat/verwerkingCode",
            document));

        // willekeurige test waarden:
        Assert.assertEquals("123456782", XmlUtils.getNodeWaarde("//burgerservicenummer", document));
        Assert.assertEquals("18890426", XmlUtils.getNodeWaarde("//geboorte/brp:datum", document));
        Assert.assertEquals("New Yorkweg", XmlUtils.getNodeWaarde("//naamOpenbareRuimte", document));
        Assert.assertEquals("73", XmlUtils.getNodeWaarde("//huisnummer", document));
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()),
            "/xsd/BRP_Bevraging_Berichten.xsd");
    }

    @Override
    URL getWsdlUrl() throws MalformedURLException {
        return new URL("http://localhost:8181/brp/services/bevraging?wsdl");
    }

    @Override
    QName getServiceQName() {
        return new QName("http://www.bprbzk.nl/BRP/bevraging/service", "BevragingService");
    }

    @Override
    QName getPortName() {
        return new QName("http://www.bprbzk.nl/BRP/bevraging/service", "BevragingPort");
    }
}
