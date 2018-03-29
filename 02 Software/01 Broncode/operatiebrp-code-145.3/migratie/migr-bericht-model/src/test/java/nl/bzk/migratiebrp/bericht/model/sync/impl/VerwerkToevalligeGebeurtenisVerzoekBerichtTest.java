/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.IOException;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkToevalligeGebeurtenisVerzoekType;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class VerwerkToevalligeGebeurtenisVerzoekBerichtTest {

    private static final String RECHTS_GEMEENTE = "0600";
    private static final String AKTE_NUMMER = "3QA1234";
    private static final String FEIT_GEMEENTE = "3333";
    private static final String
            TB02_BERICHT_INHOUD =
            "00180101100102400103100103200103300104100502400503100503200503300504100506100506200506300515100581100581200585103QA12340024101079011001012345678900240010Achternaam03100081970010103200042222033000400010410001M051520240015VrouwAchternaam03100081975123103200043333033000400010410001V06100081999010106200045555063000400011510001H8110004333381200073QA1234851000820150917";
    private static final Logger LOG = LoggerFactory.getLogger();

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(VerwerkToevalligeGebeurtenisVerzoekBerichtTest.class.getResourceAsStream("verwerkToevalligeGebeurtenisVerzoek.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkToevalligeGebeurtenisVerzoekBericht = (VerwerkToevalligeGebeurtenisVerzoekBericht) bericht;
        Assert.assertEquals(AKTE_NUMMER, verwerkToevalligeGebeurtenisVerzoekBericht.getAktenummer());
        Assert.assertEquals("VerwerkToevalligeGebeurtenisVerzoek", verwerkToevalligeGebeurtenisVerzoekBericht.getBerichtType());
        Assert.assertEquals(null, verwerkToevalligeGebeurtenisVerzoekBericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws BerichtInhoudException {
        final VerwerkToevalligeGebeurtenisVerzoekType verwerkToevalligeGebeurtenisVerzoekType = new VerwerkToevalligeGebeurtenisVerzoekType();
        verwerkToevalligeGebeurtenisVerzoekType.setAktenummer(AKTE_NUMMER);
        verwerkToevalligeGebeurtenisVerzoekType.setOntvangendeGemeente(RECHTS_GEMEENTE);
        verwerkToevalligeGebeurtenisVerzoekType.setTb02InhoudAlsTeletex(TB02_BERICHT_INHOUD);
        verwerkToevalligeGebeurtenisVerzoekType.setVerzendendeGemeente(FEIT_GEMEENTE);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkToevalligeGebeurtenisVerzoekBericht =
                new VerwerkToevalligeGebeurtenisVerzoekBericht(verwerkToevalligeGebeurtenisVerzoekType);
        final String geformat = verwerkToevalligeGebeurtenisVerzoekBericht.format();

        LOG.info("Geformat: {}", geformat);
        // controleer of geformat bericht weer geparsed kan worden
        final VerwerkToevalligeGebeurtenisVerzoekBericht format = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(geformat);
        Assert.assertEquals("VerwerkToevalligeGebeurtenisVerzoek", format.getBerichtType());
        Assert.assertNotNull("Aktenummer moet ongelijk aan null zijn.", format.getAktenummer());
        Assert.assertEquals("Aktenummer moet gelijk zijn.", AKTE_NUMMER, format.getAktenummer());
        Assert.assertNotNull("Tb02 teletex moet ongelijk aan null zijn.", format.getTb02InhoudAlsTeletex());
        Assert.assertEquals("Tb02 teletex moet gelijk zijn.", TB02_BERICHT_INHOUD, format.getTb02InhoudAlsTeletex());
        Assert.assertNotNull("Ontvangende gemeente moet ongelijk aan null zijn.", format.getOntvangendeGemeente());
        Assert.assertEquals("Ontvangende gemeente moet gelijk zijn.", RECHTS_GEMEENTE, format.getOntvangendeGemeente());
        Assert.assertNotNull("Verzendende gemeente moet ongelijk aan null zijn.", format.getVerzendendeGemeente());
        Assert.assertEquals("Verzendende gemeente moet gelijk zijn.", FEIT_GEMEENTE, format.getVerzendendeGemeente());
    }

    @Test
    public void testEquals() {
        final VerwerkToevalligeGebeurtenisVerzoekType verwerkToevalligeGebeurtenisVerzoekType = new VerwerkToevalligeGebeurtenisVerzoekType();
        verwerkToevalligeGebeurtenisVerzoekType.setAktenummer(null);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel =
                new VerwerkToevalligeGebeurtenisVerzoekBericht(verwerkToevalligeGebeurtenisVerzoekType);
        verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel.setMessageId(MessageIdGenerator.generateId());

        final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkToevalligeGebeurtenisVerzoekBerichtKopie =
                new VerwerkToevalligeGebeurtenisVerzoekBericht(verwerkToevalligeGebeurtenisVerzoekType);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkToevalligeGebeurtenisVerzoekBerichtObjectKopie =
                verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel;
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht = new BlokkeringInfoAntwoordBericht();

        verwerkToevalligeGebeurtenisVerzoekBerichtKopie.setMessageId(verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel.getMessageId());
        verwerkToevalligeGebeurtenisVerzoekBerichtKopie.setCorrelationId(verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel.getCorrelationId());

        Assert.assertTrue(verwerkToevalligeGebeurtenisVerzoekBerichtObjectKopie.equals(verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel));
        Assert.assertFalse(verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel.equals(blokkeringInfoAntwoordBericht));
        Assert.assertTrue(verwerkToevalligeGebeurtenisVerzoekBerichtKopie.equals(verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel));
        Assert.assertEquals(
                verwerkToevalligeGebeurtenisVerzoekBerichtObjectKopie.hashCode(),
                verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel.hashCode());
        Assert.assertEquals(verwerkToevalligeGebeurtenisVerzoekBerichtKopie.hashCode(), verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel.hashCode());
        Assert.assertEquals(verwerkToevalligeGebeurtenisVerzoekBerichtKopie.toString(), verwerkToevalligeGebeurtenisVerzoekBerichtOrigineel.toString());
    }

    @Test
    public void testSettersEnGetters() {
        final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkToevalligeGebeurtenisVerzoekBericht = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verwerkToevalligeGebeurtenisVerzoekBericht.setAktenummer(null);

        Assert.assertNull(verwerkToevalligeGebeurtenisVerzoekBericht.getAktenummer());
    }

    @Test
    public void tesBerichtSyntaxException() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(
                        VerwerkToevalligeGebeurtenisVerzoekBerichtTest.class.getResourceAsStream(
                                "verwerkToevalligeGebeurtenisVerzoekBerichtSyntaxException.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
