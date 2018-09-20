/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.PersoonDto;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.utils.XmlUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class RegistreerOverlijdenIntegratieTest extends AbstractBijhoudingServiceIntegrationTest {

    private static final Logger LOG = LoggerFactory
        .getLogger();

    @SuppressWarnings("deprecation")
    @Test
    public void testRegistreerOverlijden() throws Exception {
        zetActieVoorBericht("registreerOverlijden");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_ovlRegistreerOverlijden/brp:overlijdenInNederland/brp:acties/brp:registratieOverlijden/brp:persoon/@brp:objectSleutel",
                1004
        );
        Node antwoord = verzendBerichtNaarService("registratieOverlijden.xml");
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);

        printResponse(document);
        Assert.assertTrue("Overlijden root node bestaat niet", XmlUtils
            .isNodeAanwezig("//brp:bhg_ovlRegistreerOverlijden_R", document));
        Assert.assertTrue("resultaat bestaat niet",
            XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking",
                                   document));

        String bsnMan = "100484347";

        PersoonDto persoonMan = PersoonDto.persoonMetBsnBestaatInALaag(
            jdbcTemplate, bsnMan).get(0);
        Assert.assertNotNull("Kan geen persoon vinden", persoonMan);
        Assert.assertEquals(bsnMan, persoonMan.getBsn());

        // Test Relatie en betrokkenheid
        List<Map<String, Object>> resultaten = jdbcTemplate
            .queryForList("SELECT * FROM kern.pers WHERE pers.id = ?",
                persoonMan.getId());
        Assert.assertEquals(1, resultaten.size());
        Map<String, Object> res = resultaten.get(0);
        Assert.assertEquals(20130101, res.get("datoverlijden"));
        Assert.assertEquals(6, res.get("gemoverlijden"));
        Assert.assertEquals("'s-Gravenhage", res.get("wplnaamoverlijden"));
        Assert.assertEquals(null, res.get("blplaatsoverlijden"));
        Assert.assertEquals(null, res.get("blregiooverlijden"));
        Assert.assertEquals(2, res.get("landgebiedoverlijden"));
        Assert.assertEquals(null, res.get("omslocoverlijden"));

        resultaten = jdbcTemplate.queryForList(
            "SELECT * FROM kern.his_persbijhouding WHERE pers = ?",
            persoonMan.getId());
        Assert.assertEquals(3, resultaten.size());

        //Zoek het actuele C-Laag record op:
        for (Map<String, Object> rij : resultaten) {
            if (rij.get("tsverval") == null
                    && rij.get("dateindegel") == null)
            {
                res = rij;
                break;
            }
        }

        Assert.assertEquals(4, res.get("naderebijhaard"));
        Assert.assertEquals(20130101, res.get("dataanvgel"));

        resultaten = jdbcTemplate.queryForList(
            "SELECT * FROM kern.his_persoverlijden WHERE pers = ?",
            persoonMan.getId());
        Assert.assertEquals(1, resultaten.size());
        res = resultaten.get(0);
        Assert.assertEquals(20130101, res.get("datoverlijden"));
        Assert.assertEquals(6, res.get("gemoverlijden"));
        Assert.assertEquals("'s-Gravenhage", res.get("wplnaamoverlijden"));
        Assert.assertEquals(null, res.get("blplaatsoverlijden"));
        Assert.assertEquals(null, res.get("blregiooverlijden"));
        Assert.assertEquals(2, res.get("landgebiedoverlijden"));
        Assert.assertEquals(null, res.get("omslocoverlijden"));
    }

    @Override
    Logger getLogger() {
        return LOG;
    }

    @Override
    String getWsdlPortType() {
        return "bhgOverlijden";
    }
}
