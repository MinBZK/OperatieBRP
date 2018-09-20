/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.xpath.XPathExpressionException;
import nl.bzk.brp.model.GeslachtsnaamcomponentDto;
import nl.bzk.brp.model.NationaliteitDto;
import nl.bzk.brp.model.PersoonDto;
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

// XSD is nog niet duidelijk of het klopt, GertJan gaat nog overleggen of FamBetr en KindBetrok
// technische ID nodig heeft. IntegratieTest alvast ingechecked ter voorbereiding
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class RegistreerAdoptieIntegratieTest extends AbstractBijhoudingServiceIntegrationTest {
    private static final Logger LOG = LoggerFactory
            .getLogger();

    @SuppressWarnings("deprecation")
    @Test
    public void testRegistratieAdoptie() throws Exception {
        String xmlBericht = "afstamming_RegistratieAdoptie_Bijhouding.xml";
        zetActieVoorBericht("registreerAdoptie");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerAdoptie/brp:adoptieIngezetene/brp:acties/brp:registratieOuder/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:kind/brp:persoon/@brp:objectSleutel",
                1001
        );
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerAdoptie/brp:adoptieIngezetene/brp:acties/brp:registratieOuder/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder[1]/brp:persoon/@brp:objectSleutel",
                1002
        );
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerAdoptie/brp:adoptieIngezetene/brp:acties/brp:registratieOuder/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder[2]/brp:persoon/@brp:objectSleutel",
                1003
        );
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_afsRegistreerAdoptie/brp:adoptieIngezetene/brp:acties/brp:registratieNationaliteit/brp:persoon/@brp:objectSleutel",
                1001
        );
        Node antwoord =
                verzendBerichtNaarService(xmlBericht);
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        Assert.assertTrue(
                "InschrijvingAangifteGeboorte root node bestaat niet", XmlUtils
                .isNodeAanwezig("//brp:bhg_afsRegistreerAdoptie_R",
                                document));
        Assert.assertTrue("resultaat bestaat niet",
                XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
                XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking",
                                       document));

        //Controleer de persoon in de database en haal gelijk de persoon op
        PersoonDto persoon = controleerEnHaalPersoonUitBericht(xmlBericht);
        Integer kindId = persoon.getId();

        //Controleer de geslachtsnaamcomponent in de database
        GeslachtsnaamcomponentDto geslnaamcompDto = new GeslachtsnaamcomponentDto();
        geslnaamcompDto.setAdelijkeTitelCode(null);
        geslnaamcompDto.setScheidingsteken("/");
        geslnaamcompDto.setVoorVoegsel("van");
        geslnaamcompDto.setNaam("Kant");
        controllerGeslachtsnaamComponenten(kindId, 0, geslnaamcompDto);

        //Controleer de relatie en de betrokkenheden
        List<Map<String, Object>> betrokkenheden = jdbcTemplate
                .queryForList("SELECT * FROM kern.betr WHERE pers = ?", kindId);
        Assert.assertFalse(betrokkenheden.isEmpty());
        Assert.assertEquals(1, betrokkenheden.size());
        Integer relatieId = (Integer) betrokkenheden.get(0).get("relatie");
        Assert.assertNotNull(relatieId);

        //Bestaande ouders
        Map<String, Object> controleWaarden = new HashMap<>();
        controleWaarden.put("rol", SoortBetrokkenheid.OUDER.ordinal());
        controleWaarden.put("indOuder", Boolean.TRUE);
        controleWaarden.put("dataanv", null);
        controleerBetrokkenhedenInRelatie(relatieId, 5, 1, controleWaarden);

        controleWaarden = new HashMap<>();
        controleWaarden.put("rol", SoortBetrokkenheid.OUDER.ordinal());
        controleWaarden.put("indOuder", Boolean.TRUE);
        controleWaarden.put("dataanv", null);
        controleerBetrokkenhedenInRelatie(relatieId, 5, 2, controleWaarden);

        //Nieuwe ouders
        controleWaarden = new HashMap<>();
        controleWaarden.put("rol", SoortBetrokkenheid.OUDER.ordinal());
        controleWaarden.put("indOuder", Boolean.TRUE);
        controleWaarden.put("dataanv", null);
        controleerBetrokkenhedenInRelatie(relatieId, 5, 1002, controleWaarden);

        controleWaarden = new HashMap<>();
        controleWaarden.put("rol", SoortBetrokkenheid.OUDER.ordinal());
        controleWaarden.put("indOuder", Boolean.TRUE);
        controleWaarden.put("dataanv", null);
        controleerBetrokkenhedenInRelatie(relatieId, 5, 1003, controleWaarden);

        //Kind
        controleWaarden = new HashMap<>();
        controleWaarden.put("rol", SoortBetrokkenheid.KIND.ordinal());
        controleWaarden.put("indOuder", null);
        controleWaarden.put("dataanv", null);
        controleerBetrokkenhedenInRelatie(relatieId, 5, kindId, controleWaarden);

        controleerNationaliteiten(kindId, 2);

        //Controleer bsn's in het antwoord bericht
        controleerBsnInAntwoordeBericht(document, "162651533", "173047580", persoon.getBsn());
    }

    private PersoonDto controleerEnHaalPersoonUitBericht(final String bericht) throws Exception {
        //Controleer database
        Document orgAanvraag = XmlUtils
                .bouwDocumentVanBestand(
                        getInputStream(bericht));
        String bsn = XmlUtils
                .getAttribuutWaarde("//brp:kind/brp:persoon", "brp:objectSleutel",
                                    orgAanvraag);

        List<PersoonDto> personen = PersoonDto.persoonMetBsnBestaatInALaag(
            jdbcTemplate, bsn);

        Assert.assertTrue("Kan geen persoon vinden", personen.size() > 0);
        Assert.assertEquals(bsn, personen.get(0).getBsn());

        return personen.get(0);
    }

    private void controleerNationaliteiten(final Integer persoonId, final Integer... nationaliteiten) {
        //Controleer nationaliteit
        List<NationaliteitDto> nations = NationaliteitDto
                .getPersoonNationaliteitLevelA(jdbcTemplate, persoonId);
        Assert.assertTrue("Kan geen naamscomponenten vinden",
                !nations.isEmpty());

        Assert.assertEquals(nationaliteiten.length, nations.size());

        for (int i = 0; i < nationaliteiten.length; i++) {
            Assert.assertEquals(nationaliteiten[i], nations.get(i).getNation());
        }
    }

    private void controllerGeslachtsnaamComponenten(final int persoonId, final int geslcompIndex,
                                                    final GeslachtsnaamcomponentDto geslachtsnaamcomponent)
    {
        //Controleer Geslachtsnaamcomponenten
        List<GeslachtsnaamcomponentDto> geslachtscomponenten = GeslachtsnaamcomponentDto
                .getGeslachtsnaamcomponentA(jdbcTemplate, persoonId);
        Assert.assertTrue("Kan geen naamscomponenten vinden",
                !geslachtscomponenten.isEmpty());
        GeslachtsnaamcomponentDto gc = geslachtscomponenten.get(geslcompIndex);

        Assert.assertEquals(geslachtsnaamcomponent.getAdelijkeTitelCode(), gc.getAdelijkeTitelCode());
        Assert.assertEquals(geslachtsnaamcomponent.getPredicaatCode(), gc.getPredicaatCode());
        Assert.assertEquals(geslachtsnaamcomponent.getScheidingsteken(), gc.getScheidingsteken());
        Assert.assertEquals(geslachtsnaamcomponent.getVoorVoegsel(), gc.getVoorVoegsel());
        Assert.assertEquals(geslachtsnaamcomponent.getNaam(), gc.getNaam());
    }

    @SuppressWarnings("deprecation")
    private void controleerBetrokkenhedenInRelatie(final Integer relatieId, final int aantalBetrokkenheden,
                                                    final Integer persoonId, final Map<String, Object> values)
    {
        List<Map<String, Object>> betrokkenheden = jdbcTemplate
                .queryForList("SELECT * FROM kern.betr WHERE relatie = ?",
                        relatieId);
        Assert.assertFalse(betrokkenheden.isEmpty());
        Assert.assertEquals(aantalBetrokkenheden, betrokkenheden.size());

        Map<String, Object> betrokkenheid = getBetrokkenheid(betrokkenheden, persoonId);

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            LOG.info("Controleer Betrokkenheden, assert key: " + entry.getKey() + " value: " + entry.getValue());
            Assert.assertEquals(entry.getValue(),
                    betrokkenheid.get(entry.getKey()));
        }
    }

    private Map<String, Object> getBetrokkenheid(final List<Map<String, Object>> betrokkenhedenRelatie,
                                                 final Integer persoonId)
    {
        for (Map<String, Object> betrokkenheid : betrokkenhedenRelatie) {
            Integer betrokkeneId = (Integer) betrokkenheid.get("pers");
            if (betrokkeneId.equals(persoonId)
                    || betrokkeneId.equals(persoonId))
            {
                return betrokkenheid;
            }
        }

        return null;
    }

    private void controleerBsnInAntwoordeBericht(final Document document, final String... burgerservicenummers)
        throws XPathExpressionException
    {
        for (int i = 0; i < burgerservicenummers.length; i++) {
            int xmlIndex = i + 1;
            Assert.assertEquals(
                    burgerservicenummers[i],
                    XmlUtils.getNodeWaarde(
                            "//brp:bijgehoudenPersonen/brp:persoon[" + xmlIndex
                                    + "]/brp:identificatienummers/brp:burgerservicenummer",
                            document));
        }
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
