/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AnummerWijzigingNotificatieType;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class AnummerWijzigingNotificatieTest {

    private static final Long OUD_A_NUMMER = Long.valueOf("1234567890");
    private static final Long NIEUW_A_NUMMER = Long.valueOf("6474673563");
    private static final BigInteger DATUM_INGANG = new BigInteger("20150101");
    private static final GerelateerdeInformatie GERELATEERDE_INFORMATIE = new GerelateerdeInformatie(null, null, Arrays.asList(
        NIEUW_A_NUMMER.toString(),
        OUD_A_NUMMER.toString()));
    private static final String BRON_GEMEENTE = "0600";

    private static final Logger LOG = LoggerFactory.getLogger();

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws IOException {
        final String berichtOrigineel = IOUtils.toString(AnummerWijzigingNotificatieTest.class.getResourceAsStream("anummerWijzigingNotificatie.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final AnummerWijzigingNotificatie anummerWijzigingNotificatie = (AnummerWijzigingNotificatie) bericht;
        assertEquals(OUD_A_NUMMER, anummerWijzigingNotificatie.getOudAnummer());
        assertEquals(NIEUW_A_NUMMER, anummerWijzigingNotificatie.getNieuwAnummer());
        assertEquals("AnummerWijzigingNotificatie", anummerWijzigingNotificatie.getBerichtType());
        assertEquals("uc311", anummerWijzigingNotificatie.getStartCyclus());
    }

    @Test
    public void testFormat() throws BerichtInhoudException {
        final AnummerWijzigingNotificatieType anummerWijzigingNotificatieType = new AnummerWijzigingNotificatieType();
        anummerWijzigingNotificatieType.setOudANummer(OUD_A_NUMMER.toString());
        anummerWijzigingNotificatieType.setNieuwANummer(NIEUW_A_NUMMER.toString());
        anummerWijzigingNotificatieType.setBronGemeente(BRON_GEMEENTE);
        anummerWijzigingNotificatieType.setDatumIngangGeldigheid(DATUM_INGANG);

        final AnummerWijzigingNotificatie anummerWijzigingNotificatie = new AnummerWijzigingNotificatie(anummerWijzigingNotificatieType);
        final String geformat = anummerWijzigingNotificatie.format();

        LOG.info("Geformat: {}", geformat);
        // controleer of geformat bericht weer geparsed kan worden
        final AnummerWijzigingNotificatie format = (AnummerWijzigingNotificatie) factory.getBericht(geformat);
        assertEquals("AnummerWijzigingNotificatie", format.getBerichtType());
        assertEquals(OUD_A_NUMMER, format.getOudAnummer());
        assertEquals(NIEUW_A_NUMMER, format.getNieuwAnummer());
        assertEquals(BRON_GEMEENTE, format.getBronGemeente());
        assertEquals(DATUM_INGANG, BigInteger.valueOf(format.getDatumIngangGeldigheid()));
    }

    @Test
    public void testEquals() {
        final AnummerWijzigingNotificatieType anummerWijzigingNotificatieType = new AnummerWijzigingNotificatieType();
        anummerWijzigingNotificatieType.setOudANummer(OUD_A_NUMMER.toString());
        anummerWijzigingNotificatieType.setNieuwANummer(NIEUW_A_NUMMER.toString());
        anummerWijzigingNotificatieType.setDatumIngangGeldigheid(DATUM_INGANG);
        anummerWijzigingNotificatieType.setBronGemeente(BRON_GEMEENTE);
        final AnummerWijzigingNotificatie anummerWijzigingNotificatieOrigineel = new AnummerWijzigingNotificatie(anummerWijzigingNotificatieType);
        anummerWijzigingNotificatieOrigineel.setMessageId(MessageIdGenerator.generateId());

        final AnummerWijzigingNotificatie anummerWijzigingNotificatieKopie = new AnummerWijzigingNotificatie(anummerWijzigingNotificatieType);
        final AnummerWijzigingNotificatie anummerWijzigingNotificatieObjectKopie = anummerWijzigingNotificatieOrigineel;
        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht = new BlokkeringInfoAntwoordBericht();

        anummerWijzigingNotificatieKopie.setMessageId(anummerWijzigingNotificatieOrigineel.getMessageId());
        anummerWijzigingNotificatieKopie.setCorrelationId(anummerWijzigingNotificatieOrigineel.getCorrelationId());

        assertTrue(anummerWijzigingNotificatieObjectKopie.equals(anummerWijzigingNotificatieOrigineel));
        assertFalse(anummerWijzigingNotificatieOrigineel.equals(blokkeringInfoAntwoordBericht));
        assertTrue(anummerWijzigingNotificatieKopie.equals(anummerWijzigingNotificatieOrigineel));
        assertEquals(anummerWijzigingNotificatieObjectKopie.hashCode(), anummerWijzigingNotificatieOrigineel.hashCode());
        assertEquals(anummerWijzigingNotificatieKopie.hashCode(), anummerWijzigingNotificatieOrigineel.hashCode());
        assertEquals(anummerWijzigingNotificatieKopie.toString(), anummerWijzigingNotificatieOrigineel.toString());
    }

    @Test
    public void testSettersEnGetters() {
        final AnummerWijzigingNotificatie anummerWijzigingNotificatie = new AnummerWijzigingNotificatie();
        anummerWijzigingNotificatie.setOudAnummer(OUD_A_NUMMER);
        anummerWijzigingNotificatie.setNieuwAnummer(NIEUW_A_NUMMER);
        anummerWijzigingNotificatie.setBronGemeente(BRON_GEMEENTE);
        anummerWijzigingNotificatie.setDatumIngangGeldigheid(DATUM_INGANG.intValue());

        assertEquals(OUD_A_NUMMER, anummerWijzigingNotificatie.getOudAnummer());
        assertEquals(NIEUW_A_NUMMER, anummerWijzigingNotificatie.getNieuwAnummer());
        assertEquals(BRON_GEMEENTE, anummerWijzigingNotificatie.getBronGemeente());
        assertEquals(DATUM_INGANG, BigInteger.valueOf(anummerWijzigingNotificatie.getDatumIngangGeldigheid()));
        assertEquals(GERELATEERDE_INFORMATIE.getaNummers().get(0), anummerWijzigingNotificatie.getGerelateerdeInformatie().getaNummers().get(0));
        assertEquals(GERELATEERDE_INFORMATIE.getaNummers().get(1), anummerWijzigingNotificatie.getGerelateerdeInformatie().getaNummers().get(1));
    }

    @Test
    public void tesBerichtSyntaxException() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(AnummerWijzigingNotificatieTest.class.getResourceAsStream("anummerWijzigingNotificatieBerichtSyntaxException.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
    }

}
