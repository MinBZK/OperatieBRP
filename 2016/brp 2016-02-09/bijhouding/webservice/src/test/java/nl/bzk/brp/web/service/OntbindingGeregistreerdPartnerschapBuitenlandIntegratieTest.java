/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.util.List;
import java.util.Map;
import javax.xml.xpath.XPathExpressionException;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.utils.XmlUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/** Integratie test die controleert of de ontbinding van een partnerschap in het buitenland werkt. */
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class OntbindingGeregistreerdPartnerschapBuitenlandIntegratieTest
    extends AbstractBijhoudingServiceIntegrationTest
{

    private static final Logger LOG          = LoggerFactory
        .getLogger();
    private static final int    RELATIE_ID   = 101;
    private static final int    PARTNER1_BSN = 100000009;
    private static final int    PARTNER2_BSN = 100000011;

    @Test
    public void testOntbinding() throws Exception {
        String xmlBericht = "huwelijkpartnerschap_ontbinding_buitenland.xml";
        zetActieVoorBericht("registreerHuwelijkPartnerschap");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap/brp:beeindigingGeregistreerdPartnerschapInBuitenland/brp:acties/brp:registratieEindeHuwelijkGeregistreerdPartnerschap/brp:geregistreerdPartnerschap/brp:betrokkenheden/brp:partner[1]/brp:persoon/@brp:objectSleutel",
                101
        );
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap/brp:beeindigingGeregistreerdPartnerschapInBuitenland/brp:acties/brp:registratieEindeHuwelijkGeregistreerdPartnerschap/brp:geregistreerdPartnerschap/brp:betrokkenheden/brp:partner[2]/brp:persoon/@brp:objectSleutel",
                102
        );
        Node antwoord = verzendBerichtNaarService(xmlBericht);
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        Assert.assertTrue("Registreer Huwelijk en/of Partnerschap root node bestaat niet",
            XmlUtils.isNodeAanwezig("//brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R", document));
        Assert.assertTrue("resultaat bestaat niet", XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking", document));

        controleerBerichtOpBijgehoudenPersonen(document);
        controleerDatabaseOpBeeindigingPartnerschap();
    }

    /**
     * Controleert of het antwoordbericht twee bijgehouden personen heeft.
     *
     * @param document het antwoordbericht.
     */
    private void controleerBerichtOpBijgehoudenPersonen(final Document document) throws XPathExpressionException {
        Assert.assertEquals(Integer.valueOf(2), XmlUtils.getNodeCount(
            "//brp:beeindigingGeregistreerdPartnerschapInBuitenland/brp:bijgehoudenPersonen/brp:persoon", document));
    }

    /**
     * Controleert of de partnerschap relatie in de database is aangepast met de einde informatie en dat er tevens
     * een historisch record is aangemaakt.
     */
    private void controleerDatabaseOpBeeindigingPartnerschap() {
        // Test dat personen nog steeds maar een enkele betrokkenheid hebben
        controleerEnkeleBetrokkenheidNaarRelatie(PARTNER1_BSN, RELATIE_ID);
        controleerEnkeleBetrokkenheidNaarRelatie(PARTNER2_BSN, RELATIE_ID);

        // Test dat relatie waarnaar betrokkenheid verwijst einde informatie bevat
        controleerEindeInformatieRelatie(RELATIE_ID);
    }

    /**
     * Controleert dat er slechts een enkele betrokkenheid is voor de persoon met opgegeven bsn en dat die
     * betrokkenheid van de rol Partner is en verwijst naar opgegeven relatie.
     *
     * @param bsn het bsn van de persoon die gecontroleerd moet worden.
     * @param relatieId het id van de relatie waarnaar de betrokkenheid zou moeten verwijzen.
     */
    private void controleerEnkeleBetrokkenheidNaarRelatie(final int bsn, final int relatieId) {
        List<Map<String, Object>> betrokkenheden = jdbcTemplate.queryForList(
            "SELECT b.* FROM kern.betr b, kern.pers p WHERE b.pers = p.id AND p.bsn = ?", bsn);
        Assert.assertEquals(1, betrokkenheden.size());
        Assert.assertEquals(relatieId, betrokkenheden.get(0).get("relatie"));
        Assert.assertEquals(SoortBetrokkenheid.PARTNER.ordinal(), betrokkenheden.get(0).get("rol"));
    }

    /**
     * Controleert of de relatie in de database met de opgegeven id ook echt de verstuurde einde informatie bevat.
     *
     * @param relatieId de id van de te controleren relatie.
     */
    private void controleerEindeInformatieRelatie(final int relatieId) {
        List<Map<String, Object>> relaties =
            jdbcTemplate.queryForList("SELECT * FROM kern.relatie WHERE id = ?", relatieId);
        Assert.assertEquals(1, relaties.size());
        Assert.assertEquals(6, relaties.get(0).get("rdneinde"));
        Assert.assertEquals(20130830, relaties.get(0).get("dateinde"));
        Assert.assertEquals("Tokio", relaties.get(0).get("blplaatseinde"));
        Assert.assertEquals(5, relaties.get(0).get("landgebiedeinde"));
    }


    @Override
    Logger getLogger() {
        return LOG;
    }

    @Override
    String getWsdlPortType() {
        return "bhgHuwelijkGeregistreerdPartnerschap";
    }
}
