/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
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

@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class RegistreerKiesrechtIntegratieTest extends AbstractBijhoudingServiceIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * LET OP: Dit min bericht bevat het minimale XML bericht + de verplichte velden om het bericht door alle validatie
     * regels in de business laag te krijgen.
     */
    @Test
    public void testRegistratieKiesrechtMinBericht() throws Exception {
        String bestandsNaam = "verkiezingen_Registreer_Kiesrecht_MIN.xml";
        zetActieVoorBericht("registreerKiesrecht");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_vkzRegistreerKiesrecht/brp:wijzigingUitsluitingKiesrecht/brp:acties/brp:registratieUitsluitingKiesrecht/brp:persoon/@brp:objectSleutel",
                2
        );
        Node antwoord = verzendBerichtNaarService(bestandsNaam);
        Assert.assertNotNull(antwoord);
        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
            XmlUtils.getNodeWaarde(NODE_VERWERKING, document));
    }


    @Override
    Logger getLogger() {
        return LOG;
    }

    @Override
    String getWsdlPortType() {
        return "bhgVerkiezingen";
    }
}
