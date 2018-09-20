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

/**
 * Integratie test voor bericht; DVM_RegistreerMededelingVerzoek_B.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class RegistreerMededelingVerzoekIntegratieTest extends AbstractBijhoudingServiceIntegrationTest {

    private static final Logger LOG = LoggerFactory
            .getLogger();

    @Override
    Logger getLogger() {
        return LOG;
    }

    @Override
    String getWsdlPortType() {
        return "bhgDocumentVerzoekMededeling";
    }

    @Test
    public void testMededelingGezagsRegister() throws Exception {
        String xmlBericht = "DocumentVerzoekMededeling_RegistreerMededelingVerzoek.xml";
        zetActieVoorBericht("registreerMededelingVerzoek");
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_dvmRegistreerMededelingVerzoek/brp:wijzigingGezag/brp:acties/brp:registratieGezag/brp:persoon/@brp:objectSleutel",
                4002
        );
        voegObjectSleutelXPathMetBijbehorendePersoonIdToe(
                "/brp:bhg_dvmRegistreerMededelingVerzoek/brp:wijzigingGezag/brp:acties/brp:registratieGezag/brp:persoon/brp:betrokkenheden/brp:kind/brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:persoon/@brp:objectSleutel",
                4001
        );
        Node antwoord = verzendBerichtNaarService(xmlBericht);
        Assert.assertNotNull(antwoord);

        String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        printResponse(document);

        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(),
                XmlUtils.getNodeWaarde("//brp:resultaat/brp:verwerking",
                                       document));
    }
}
