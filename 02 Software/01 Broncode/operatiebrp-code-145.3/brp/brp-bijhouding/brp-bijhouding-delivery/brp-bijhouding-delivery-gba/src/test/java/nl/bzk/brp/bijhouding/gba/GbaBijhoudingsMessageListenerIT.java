/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.gba;

import java.io.StringReader;
import javax.inject.Inject;
import javax.xml.transform.stream.StreamSource;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingAntwoordBerichtParser;
import nl.bzk.brp.domain.internbericht.bijhouding.GbaBijhoudingBerichtQueue;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class GbaBijhoudingsMessageListenerIT extends AbstractIT {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private ObjectSleutelService objectSleutelService;

    @Test
    public void testIntegratie() throws Exception {
        dbUnitInsert("PersoonTestData.xml");

        String verzoek = IOUtils.toString(this.getClass().getResourceAsStream("testBericht.xml"), "UTF-8");
        verzoek = verzoek.replace("OBJECT_SLEUTEL_1", objectSleutelService.maakPersoonObjectSleutel(-5001, 0).maskeren());
        verzoek = verzoek.replace("OBJECT_SLEUTEL_2", objectSleutelService.maakPersoonObjectSleutel(-5002, 0).maskeren());
        LOG.info("Verzoek: {}", verzoek);

        LOG.info("\n\n\nFIND ME: BEFORE PUT\n\n");

        putMessage(GbaBijhoudingBerichtQueue.VERZOEK.getQueueNaam(), verzoek, "REF-0001");

        LOG.info("\n\n\nFIND ME: EXPECT ANTWOORD\n\n");
        final String antwoord = expectMessage(GbaBijhoudingBerichtQueue.ANTWOORD.getQueueNaam());
        LOG.info("Antwoord: {}", antwoord);
        LOG.info("\n\n\nFIND ME: PARSE ANTWOORD\n\n");
        final BijhoudingAntwoordBericht antwoordBericht = new BijhoudingAntwoordBerichtParser().parse(new StreamSource(new StringReader(antwoord)));
        Assert.assertNotNull(antwoordBericht);

        LOG.info("\n\n\nFIND ME: EXPECT NOTIFICATIE\n\n");
        final String notificatie = expectMessage("NotificatieBerichten");
        LOG.info("Notificatie: {}", notificatie);
        Assert.assertNotNull(notificatie);

        dbUnitExpect("HuwelijkExpected.xml");
    }

}
