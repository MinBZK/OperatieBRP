/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.util.Arrays;
import javax.inject.Inject;
import nl.bzk.brp.locking.BrpLocker;
import nl.bzk.brp.locking.LockingElement;
import nl.bzk.brp.locking.LockingMode;
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
public class BrpLockingIntegratieTest extends AbstractBijhoudingServiceIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BrpLocker brpLocker;

    @Test
    public void testAlleBetrokkenenWordtGelockt() throws Exception {

        //Lock de id's van de betrokken ouders: kind: 1001 en ouder1: 1002, ouder2: 1003
        brpLocker.lock(Arrays.asList(1001, 1002, 1003), LockingElement.PERSOON, LockingMode.SHARED, 30);

        //Verstuur de bijhouding:
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
        Node antwoord = verzendBerichtNaarService(xmlBericht);

        final String xml = XmlUtils.toXmlString(antwoord);
        Document document = XmlUtils.bouwDocument(xml);
        final String nodeWaarde = XmlUtils.getNodeWaarde("//brp:melding/brp:melding", document);

        Assert.assertTrue(
                nodeWaarde.matches("De personen in bericht met ID [0-9]* worden"
                                           + " gelijktijdig door een ander bericht geraadpleegd of gemuteerd")
        );

        //Unlock de betrokken ouders en kind
        brpLocker.unLock();
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
