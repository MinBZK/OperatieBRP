/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import javax.xml.xpath.XPathExpressionException;
import nl.bzk.brp.model.GeslachtsnaamcomponentDto;
import nl.bzk.brp.model.PersoonDto;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.utils.XmlUtils;
import org.junit.Ignore;
import org.junit.Test;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@Ignore
public class ActualiseerAfstammingIntegrationTest extends AbstractBijhoudingServiceIntegrationTest {

    private static final Logger LOG = LoggerFactory
            .getLogger();

    @Test
    public void testVaststellingVaderschapDoorRechter() throws Exception {
        final String xmlBericht = "afstamming_ActualiseerAfstamming_vaststellingVaderschapDoorRechter.xml";
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsActualiseerAfstamming/brp:vaststellingOuderschap/brp:acties/brp:registratieOuder/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:kind/brp:persoon/@brp:objectSleutel",
                1005
        );
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsActualiseerAfstamming/brp:vaststellingOuderschap/brp:acties/brp:registratieOuder/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:persoon/@brp:objectSleutel",
                3
        );
        final Node antwoord = verzendBerichtNaarService(xmlBericht);
        assertNotNull(antwoord);

        final String xml = XmlUtils.toXmlString(antwoord);
        final Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        assertTrue(
            "Actualiseer Afstamming root node bestaat niet", XmlUtils
                .isNodeAanwezig("//brp:bhg_afsActualiseerAfstamming_R",
                    document));
        assertTrue("resultaat bestaat niet",
            XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking",
                document));

        //Controleer de persoon in de database en haal gelijk de persoon op
        final PersoonDto persoonDto = controleerEnHaalPersoonUitBericht(xmlBericht);

        final Integer kindId = persoonDto.getId();

        //Controleer de geslachtsnaamcomponent in de database
        final GeslachtsnaamcomponentDto geslnaamcompDto = new GeslachtsnaamcomponentDto();
        geslnaamcompDto.setAdelijkeTitelCode("B");
        geslnaamcompDto.setVoorVoegsel("van");
        geslnaamcompDto.setScheidingsteken("-");
        geslnaamcompDto.setNaam("brrrrrrr");
        controleerGeslachtsnaamComponenten(kindId, 0, geslnaamcompDto);

        // Test Relatie en betrokkenheid
        final List<Map<String, Object>> betrokkenheden = jdbcTemplate
                .queryForList("SELECT * FROM kern.betr WHERE pers = ?", kindId);
        assertFalse(betrokkenheden.isEmpty());
        assertEquals(2, betrokkenheden.size());
        final Integer relatieId = (Integer) betrokkenheden.get(0).get("relatie");
        assertNotNull(relatieId);

        final List<Map<String, Object>> betrokk = jdbcTemplate
                .queryForList("SELECT * FROM kern.betr WHERE relatie = ?",
                        relatieId);
        //Kind had eerst alleen 1 ouder, na deze actie heeft kind er nu 2. Inclusief zichzelf is het 3.
        assertEquals(3, betrokk.size());

        //Controleer bsn's in het antwoord bericht
        controleerBsnInAntwoordBericht(document, "089444917", persoonDto.getBsn());
    }

    private void controleerBsnInAntwoordBericht(final Document document, final String... burgerservicenummers)
        throws XPathExpressionException
    {
        for (int i = 0; i < burgerservicenummers.length; i++) {
            final int xmlIndex = i + 1;
            assertEquals(
                burgerservicenummers[i],
                XmlUtils.getNodeWaarde(
                    "//brp:bijgehoudenPersonen/brp:persoon[" + xmlIndex
                        + "]/brp:identificatienummers/brp:burgerservicenummer",
                    document));
        }
    }

    private void controleerGeslachtsnaamComponenten(final int persoonId, final int geslcompIndex,
        final GeslachtsnaamcomponentDto geslachtsnaamcomponent)
    {
        //Controleer Geslachtsnaamcomponenten
        final List<GeslachtsnaamcomponentDto> geslachtscomponenten = GeslachtsnaamcomponentDto
                .getGeslachtsnaamcomponentA(jdbcTemplate, persoonId);
        assertTrue("Kan geen naamscomponenten vinden",
            !geslachtscomponenten.isEmpty());
        final GeslachtsnaamcomponentDto gc = geslachtscomponenten.get(geslcompIndex);

        assertEquals(geslachtsnaamcomponent.getAdelijkeTitelCode(), gc.getAdelijkeTitelCode());
        assertEquals(geslachtsnaamcomponent.getPredicaatCode(), gc.getPredicaatCode());
        assertEquals(geslachtsnaamcomponent.getScheidingsteken(), gc.getScheidingsteken());
        assertEquals(geslachtsnaamcomponent.getVoorVoegsel(), gc.getVoorVoegsel());
        assertEquals(geslachtsnaamcomponent.getNaam(), gc.getNaam());
    }

    private PersoonDto controleerEnHaalPersoonUitBericht(final String bericht) throws Exception {
        //Controleer database
        final Document orgAanvraag = XmlUtils
                .bouwDocumentVanBestand(
                        getInputStream(bericht));
        final String bsn = XmlUtils
                .getNodeWaarde(
                        "//brp:kind/brp:persoon/@brp:objectSleutel",
                        orgAanvraag);

        final List<PersoonDto> personen = PersoonDto.persoonMetBsnBestaatInALaag(
            jdbcTemplate, bsn);

        assertTrue("Kan geen persoon vinden", personen.size() > 0);
        assertEquals(bsn, personen.get(0).getBsn());

        return personen.get(0);
    }


    @Override
    Logger getLogger() {
        return LOG;
    }

    @Override
    String getWsdlPortType() {
        return "bhgAfstamming";
    }
}
