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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
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


/**
 * Integratie test die controleert of de omzetting van een partnerschap in een huwelijk werkt.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class OmzettingPartnerschapInHuwelijkIntegratieTest
    extends AbstractBijhoudingServiceIntegrationTest
{

    private static final Logger LOG                   = LoggerFactory
        .getLogger();
    private static final int    RELATIE_ID            = 103;
    private static final int    INGESCHREVENE_BSN     = 309624617;
    private static final int    INGESCHREVENE_ID      = 103;
    private static final int    NIET_INGESCHREVENE_ID = 104;

    @Test
    public void testOmzetting() throws Exception {
        final String xmlBericht = "huwelijkpartnerschap_omzetting_partnerschap_in_huwelijk.xml";
        zetActieVoorBericht("registreerHuwelijkPartnerschap");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap/brp:omzettingGeregistreerdPartnerschapInHuwelijk/brp:acties/brp:registratieEindeHuwelijkGeregistreerdPartnerschap/brp:geregistreerdPartnerschap/brp:betrokkenheden/brp:partner[1]/brp:persoon/@brp:objectSleutel",
                103);
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap/brp:omzettingGeregistreerdPartnerschapInHuwelijk/brp:acties/brp:registratieEindeHuwelijkGeregistreerdPartnerschap/brp:geregistreerdPartnerschap/brp:betrokkenheden/brp:partner[2]/brp:persoon/@brp:objectSleutel",
                104);
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap/brp:omzettingGeregistreerdPartnerschapInHuwelijk/brp:acties/brp:registratieNaamgebruik/brp:persoon/@brp:objectSleutel",
                103
        );
        final Node antwoord = verzendBerichtNaarService(xmlBericht);
        Assert.assertNotNull(antwoord);

        final String xml = XmlUtils.toXmlString(antwoord);
        final Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        Assert.assertTrue("Registreer Huwelijk en/of Partnerschap root node bestaat niet",
            XmlUtils.isNodeAanwezig("//brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R", document));
        Assert.assertTrue("resultaat bestaat niet", XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking", document));

        controleerBerichtOpBijgehoudenPersonen(document);
        controleerDatabaseOpRelatieRecords();
        controleerDatabaseOpNieuweNietIngezetene();
        controleerDatabaseOpBetrokkenheidRecords();
        controleerDatabaseOpNieuweAanschrijvingIngeschrevene();
    }

    /**
     * Controleert of het antwoordbericht een enkele bijgehouden persoon heeft (namelijk alleen de ingeschrevene).
     *
     * @param document het antwoordbericht.
     * @throws XPathExpressionException x path expression exception
     */
    private void controleerBerichtOpBijgehoudenPersonen(final Document document) throws XPathExpressionException {
        Assert.assertEquals(Integer.valueOf(1), XmlUtils.getNodeCount(
            "//brp:omzettingGeregistreerdPartnerschapInHuwelijk/brp:bijgehoudenPersonen/brp:persoon", document));
        Assert.assertEquals(Integer.toString(INGESCHREVENE_BSN),
            XmlUtils.getNodeWaarde(
                "//brp:omzettingGeregistreerdPartnerschapInHuwelijk/brp:bijgehoudenPersonen/brp:persoon/brp:identificatienummers"
                    + "/brp:burgerservicenummer", document));
    }

    /**
     * Controleert of de partnerschap relatie in de database is aangepast met de einde informatie, dat er tevens
     * een historisch record is aangemaakt en dat de nieuwe relatie is aangemaakt.
     */
    private void controleerDatabaseOpRelatieRecords() {
        List<Map<String, Object>> relaties = jdbcTemplate.queryForList(
            "SELECT * FROM kern.his_relatie WHERE relatie = ? ORDER BY id", RELATIE_ID);

        Assert.assertEquals(2, relaties.size());

        // Test eerst de vervallen relatie (partnerschap) record
        Assert.assertNull(relaties.get(0).get("rdneinde"));
        Assert.assertNull(relaties.get(0).get("dateinde"));
        Assert.assertNull(relaties.get(0).get("gemeinde"));
        Assert.assertNull(relaties.get(0).get("landgebiedeinde"));
        Assert.assertNotNull(relaties.get(0).get("actieverval"));
        Assert.assertNotNull(relaties.get(0).get("tsverval"));

        // Test het nieuwe (beeindigde) relatie (partnerschap) record
        Assert.assertEquals(8, relaties.get(1).get("rdneinde"));
        Assert.assertEquals(20130830, relaties.get(1).get("dateinde"));
        Assert.assertEquals(3, relaties.get(1).get("gemeinde"));
        Assert.assertEquals(2, relaties.get(1).get("landgebiedeinde"));
        Assert.assertNotNull(relaties.get(1).get("actieinh"));
        Assert.assertNull(relaties.get(1).get("actieverval"));
        Assert.assertNull(relaties.get(1).get("tsverval"));

        // Test dat actie inhoud en actie verval (en de tijdstippen) gelijk zijn over de twee (partnerschap) records
        // heen.
        Assert.assertEquals(relaties.get(0).get("actieverval"), relaties.get(1).get("actieinh"));
        Assert.assertEquals(relaties.get(0).get("tsverval"), relaties.get(1).get("tsreg"));

        // Test dat er ook een nieuwe relatie is aangemaakt, namelijk het nieuwe huwelijk
        relaties = jdbcTemplate.queryForList(
            "SELECT * FROM kern.his_relatie WHERE relatie = ? ORDER BY id",
            bepaalRelatieIdVanNieuwHuwelijk());

        Assert.assertEquals(1, relaties.size());

        // Test het nieuwe huwelijk record
        Assert.assertEquals(20130830, relaties.get(0).get("dataanv"));
        Assert.assertEquals(3, relaties.get(0).get("gemaanv"));
        Assert.assertEquals(2, relaties.get(0).get("landgebiedaanv"));
        Assert.assertNotNull(relaties.get(0).get("actieinh"));
        Assert.assertNull(relaties.get(0).get("actieverval"));
        Assert.assertNull(relaties.get(0).get("tsverval"));
    }

    /**
     * Retourneert het id van het nieuw aangemaakte huwelijk.
     *
     * @return het id van het nieuw aangemaakte huwelijk.
     */
    private int bepaalRelatieIdVanNieuwHuwelijk() {
        return jdbcTemplate.queryForObject(
            "SELECT relatie FROM kern.betr WHERE pers = ? AND relatie <> ?", Integer.class, INGESCHREVENE_ID, RELATIE_ID);
    }

    /**
     * Controleert en valideert de nieuw aangemaakte niet ingezetene.
     */
    private void controleerDatabaseOpNieuweNietIngezetene() {
        final List<Map<String, Object>> personen = jdbcTemplate.queryForList(
            "SELECT * FROM kern.pers WHERE voornamen = ? AND id <> ?", "PartnerOmzetter2", NIET_INGESCHREVENE_ID);

        Assert.assertEquals(1, personen.size());

        Assert.assertEquals(SoortPersoon.NIET_INGESCHREVENE.ordinal(), personen.get(0).get("srt"));
        Assert.assertEquals("PartnerOmzetter2", personen.get(0).get("voornamen"));
        Assert.assertEquals(3, personen.get(0).get("landgebiedgeboorte"));
        Assert.assertEquals(Geslachtsaanduiding.VROUW.ordinal(), personen.get(0).get("geslachtsaand"));
    }

    /**
     * Controleert en valideert de aanwezigheid van alle betrokkenheid records in de database die gerelateerd zijn
     * aan de omzetting van het partnerschap naar huwelijk.
     */
    private void controleerDatabaseOpBetrokkenheidRecords() {
        final int nieuwHuwelijkId = bepaalRelatieIdVanNieuwHuwelijk();
        final List<Map<String, Object>> betrokkenheden = jdbcTemplate.queryForList(
            "SELECT * FROM kern.betr WHERE relatie = ? OR relatie = ? ORDER BY pers, relatie ASC",
            RELATIE_ID, nieuwHuwelijkId);

        Assert.assertEquals(4, betrokkenheden.size());

        // Test eerst ingeschrevene
        Assert.assertEquals(INGESCHREVENE_ID, betrokkenheden.get(0).get("pers"));
        Assert.assertEquals(RELATIE_ID, betrokkenheden.get(0).get("relatie"));
        Assert.assertEquals(SoortBetrokkenheid.PARTNER.ordinal(), betrokkenheden.get(0).get("rol"));
        Assert.assertEquals(INGESCHREVENE_ID, betrokkenheden.get(1).get("pers"));
        Assert.assertEquals(nieuwHuwelijkId, betrokkenheden.get(1).get("relatie"));
        Assert.assertEquals(SoortBetrokkenheid.PARTNER.ordinal(), betrokkenheden.get(1).get("rol"));

        // Test bestaande niet ingeschrevene
        Assert.assertEquals(NIET_INGESCHREVENE_ID, betrokkenheden.get(2).get("pers"));
        Assert.assertEquals(RELATIE_ID, betrokkenheden.get(2).get("relatie"));
        Assert.assertEquals(SoortBetrokkenheid.PARTNER.ordinal(), betrokkenheden.get(3).get("rol"));

        // Test nieuwe aangemaakte niet ingeschrevene
        Assert.assertNotNull(betrokkenheden.get(3).get("pers"));
        Assert.assertEquals(nieuwHuwelijkId, betrokkenheden.get(3).get("relatie"));
        Assert.assertEquals(SoortBetrokkenheid.PARTNER.ordinal(), betrokkenheden.get(3).get("rol"));
    }

    /**
     * Controleert en valideert de gewijzigde naamgebruik van de ingeschreven persoon.
     */
    private void controleerDatabaseOpNieuweAanschrijvingIngeschrevene() {
        final Map<String, Object> ingeschrevene = jdbcTemplate.queryForMap(
            "SELECT * FROM kern.pers WHERE id = ?", INGESCHREVENE_ID);

        Assert.assertNotNull(ingeschrevene);
        Assert.assertEquals("Niet Ingeschrevene GeslNaam", ingeschrevene.get("geslnaamstamnaamgebruik"));
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
