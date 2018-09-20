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
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class BevragingKandidaatVaderIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void testVraagKandidaatVaderMoederOnbekend() throws Exception {
        Node antwoord = verzendBerichtNaarService("bevragen_VraagKandidaatVader_MoederOnbekend.xml");

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);

        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_FOUTIEF.getCode(), XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerkingCode",
              document));
        // we verwachten dat we een INFO met de melding ALG0001 (== algemene fout).
        Assert.assertEquals(SoortMelding.FOUT.getCode(), XmlUtils.getNodeWaarde("//brp:meldingen/brp:melding/brp:soortCode",
              document));
        Assert.assertEquals(MeldingCode.ALG0001.getNaam(), XmlUtils.getNodeWaarde("//brp:meldingen/brp:melding/brp:regelCode",
              document));

        Assert.assertEquals("BSN is onbekend.", XmlUtils.getNodeWaarde("//brp:meldingen/brp:melding/brp:melding",
              document));
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()),
                "/xsd/BRP0100_Bevraging_Berichten.xsd");
    }

    @Test
    public void testVraagKandidaatVaderMoederIsGeenVrouw() throws Exception {
        Node antwoord = verzendBerichtNaarService("bevragen_VraagKandidaatVader_MoederIsGeenVrouw.xml");

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);

        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_FOUTIEF.getCode(), XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerkingCode",
              document));
        // we verwachten dat we een INFO met de melding ALG0001 (== algemene fout).
        Assert.assertEquals(SoortMelding.FOUT.getCode(), XmlUtils.getNodeWaarde("//brp:meldingen/brp:melding/brp:soortCode",
              document));
        Assert.assertEquals(MeldingCode.ALG0001.getNaam(), XmlUtils.getNodeWaarde("//brp:meldingen/brp:melding/brp:regelCode",
              document));
        Assert.assertEquals("De persoon is niet van het vrouwelijk geslacht.", XmlUtils.getNodeWaarde("//brp:meldingen/brp:melding/brp:melding",
              document));
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()),
                "/xsd/BRP0100_Bevraging_Berichten.xsd");
    }

    @Test
    public void testVraagKandidaatVader295157537() throws Exception {
        Node antwoord = verzendBerichtNaarService("bevragen_VraagKandidaatVader234567891.xml");
        String xml = XmlUtils.toXmlString(antwoord);

        Document document = XmlUtils.bouwDocument(xml);
        Assert.assertTrue("bevragen_VraagKandidaatVader_Antwoord root node bestaat niet",
                XmlUtils.isNodeAanwezig("//brp:ALG_BepaalKandidaatVader_ViR", document));
        Assert.assertTrue("resultaat node bestaat niet", XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_FOUTIEF.getCode(), XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerkingCode",
                document));
        // bsn is onbekend in de database.
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()),
                "/xsd/BRP0100_Bevraging_Berichten.xsd");
    }

    @Test
    public void testVraagKandidaatVaderOngetrouwd() throws Exception {
        Node antwoord = verzendBerichtNaarService("bevragen_VraagKandidaatVader089444917.xml");
        String xml = XmlUtils.toXmlString(antwoord);

        Document document = XmlUtils.bouwDocument(xml);
        Assert.assertEquals("Kandidaat-vader kan niet worden bepaald.", XmlUtils.getNodeWaarde("//brp:meldingen/brp:melding/brp:melding",
                document));
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()),
                "/xsd/BRP0100_Bevraging_Berichten.xsd");
    }

    @Test
    public void testVraagKandidaatVaderNietIngezetene() throws Exception {
        // de vrouw met BSN =224302450 is 'getrouwd' met een niet ingezetene.
        // Deze functionaliteit moet nog geimplementeerd worden
        Node antwoord = verzendBerichtNaarService("bevragen_VraagKandidaatVaderNietIngezetene.xml");
        String xml = XmlUtils.toXmlString(antwoord);
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()),
                "/xsd/BRP0100_Bevraging_Berichten.xsd");
    }

    @Test
    public void testVraagKandidaatVaderOverledene() throws Exception {
        // de vrouw met BSN =224302450 is 'getrouwd' met vader die enkele weken geleden is overleden.
        // Deze functionaliteit moet nog geimplementeerd worden
        Node antwoord = verzendBerichtNaarService("bevragen_VraagKandidaatVaderOverledene.xml");
        String xml = XmlUtils.toXmlString(antwoord);
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()),
                "/xsd/BRP0100_Bevraging_Berichten.xsd");
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
