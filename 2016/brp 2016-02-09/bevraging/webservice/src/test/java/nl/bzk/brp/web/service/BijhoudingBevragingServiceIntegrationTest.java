/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.utils.SchemaUtils;
import nl.bzk.brp.utils.XmlUtils;
import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@Category(OverslaanBijInMemoryDatabase.class)
@Ignore
public class BijhoudingBevragingServiceIntegrationTest extends AbstractBevragingIntegrationTest {

    private static final String      BRP_STUURGEGEVENS            = "brp:stuurgegevens";
    private static final String      BRP_RESULTAAT                = "brp:resultaat";
    private static final String      BRP_RESULTAAT_BRP_VERWERKING = "//brp:resultaat/brp:verwerking";
    private final        SchemaUtils schemaUtils                  = new SchemaUtils();

    protected String getSchemaBestand() {
        return schemaUtils.getXsdBijhoudingBevragingBerichten();
    }

    @Test
    public final void testGeefDetailsPersoon() throws Exception {
        final Node antwoord = verzendBerichtNaarService("bevragen_VraagDetailsPersoon.xml");

        Assert.assertEquals("brp:bhg_bvgGeefDetailsPersoon_R", antwoord.getFirstChild().getNodeName());
        Assert.assertEquals(BRP_STUURGEGEVENS, antwoord.getFirstChild().getFirstChild().getNodeName());
        Assert.assertEquals(BRP_RESULTAAT, antwoord.getFirstChild().getFirstChild().getNextSibling().getNodeName());
        Assert.assertEquals("brp:personen", antwoord.getFirstChild().getFirstChild().getNextSibling().getNextSibling()
                .getNodeName());

        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()), getSchemaBestand());
    }

    @Test
    public final void testGeefPersonenOpAdresMetBetrokkenhedenOnbekend() throws Exception {
        final Node antwoord = verzendBerichtNaarService("bevragen_VraagPersonenOpAdresInclusiefBetrokkenheden_Onbekend.xml");
        final String xml = XmlUtils.toXmlString(antwoord);
        final Document document = XmlUtils.bouwDocument(xml);

        Assert.assertEquals("brp:bhg_bvgGeefPersonenOpAdresMetBetrokkenheden_R", antwoord.getFirstChild()
                .getNodeName());
        Assert.assertEquals(BRP_STUURGEGEVENS, antwoord.getFirstChild().getFirstChild().getNodeName());
        Assert.assertEquals(BRP_RESULTAAT, antwoord.getFirstChild().getFirstChild().getNextSibling().getNodeName());

        Assert.assertTrue(xml.contains("Er zijn geen personen gevonden die voldoen aan de opgegeven criteria."));
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(), XmlUtils
                .getNodeWaarde(BRP_RESULTAAT_BRP_VERWERKING,
                    document));
        // we verwachten dat we een INFO met de melding ALG0001 (== algemene fout).
        Assert.assertEquals(
                SoortMelding.INFORMATIE.getNaam(), XmlUtils.getNodeWaarde("//brp:meldingen/brp:melding/brp:soortNaam",
                document));
        Assert.assertEquals(Regel.ALG0001.getCode(), XmlUtils.getNodeWaarde("//brp:meldingen/brp:melding/brp:regelCode",
            document));
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()), getSchemaBestand());
    }

    @Test
    public final void testGeefgPersonenOpAdresMetBetrokkenheden() throws Exception {
        final Node antwoord = verzendBerichtNaarService("bevragen_VraagPersonenOpAdresInclusiefBetrokkenheden.xml");
        final String xml = XmlUtils.toXmlString(antwoord);
        final Document document = XmlUtils.bouwDocument(xml);
        Assert.assertTrue("bevragen_VraagPersonenOpAdresInclusiefBetrokkenheden_Antwoord root node bestaat niet",
                XmlUtils.isNodeAanwezig("//brp:bhg_bvgGeefPersonenOpAdresMetBetrokkenheden_R", document));
        Assert.assertTrue("resultaat node bestaat niet", XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
                XmlUtils.getNodeWaarde(BRP_RESULTAAT_BRP_VERWERKING, document));

        // willekeurige test waarden:
        Assert.assertEquals("123456782", XmlUtils.getNodeWaarde("//brp:burgerservicenummer", document));
        Assert.assertEquals("1889-04-26", XmlUtils.getNodeWaarde("//brp:geboorte/brp:datum", document));
        Assert.assertEquals("New Yorkweg", XmlUtils.getNodeWaarde("//brp:naamOpenbareRuimte", document));
        Assert.assertEquals("73", XmlUtils.getNodeWaarde("//brp:huisnummer", document));
        // schema validatie
        printResponse(document);
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()), getSchemaBestand());
    }
}
