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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ArchiveerInBrpVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RichtingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SysteemType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZenderType;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class ArchiveringVerzoekBerichtTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String CORRELATION_ID = "correlatie-123";

    private static final String CROSS_REFERENTIENUMMER = "cross-ref-321";

    private static final String DATA = "00000000LG0102001010100101234567890";

    private static final String MESSAGE_ID = "message-id-53456";

    private static final String ONTVANGENDE_AFNEMER_CODE = "19441";

    private static final String ONTVANGENDE_GEMEENTE_CODE = "0599";

    private static final SysteemType ONTVANGENDE_SYSTEEM = SysteemType.MIGRATIEVOORZIENINGEN;

    private static final String REFERENTIENUMMER = "referentie-52452";

    private static final RichtingType RICHTING = RichtingType.INGAAND;

    private static final String SOORT_BERICHT = "Lg01";

    private static final Date TIJDSTIP_ONTVANGST = Calendar.getInstance().getTime();

    private static final Date TIJDSTIP_VERZENDING = Calendar.getInstance().getTime();

    private static final String ZENDENDE_AFNEMER_CODE = "0235";

    private static final String ZENDENDE_GEMEENTE_CODE = "0600";

    private static final SysteemType ZENDENDE_SYSTEEM = SysteemType.GBA_NETWERK;

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testVertaal() throws IOException {
        final String berichtOrigineel = IOUtils.toString(ArchiveringVerzoekBerichtTest.class.getResourceAsStream("archiveringVerzoekBericht.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final ArchiveringVerzoekBericht archiveringBericht = (ArchiveringVerzoekBericht) bericht;
        assertEquals(null, archiveringBericht.getStartCyclus());
        assertEquals("ArchiveerInBrpVerzoek", archiveringBericht.getBerichtType());
    }

    @Test
    public void testFormat() throws BerichtInhoudException, DatatypeConfigurationException {
        final ArchiveerInBrpVerzoekType archiveringVerzoekType = new ArchiveerInBrpVerzoekType();
        final ZenderType ontvanger = new ZenderType();
        ontvanger.setGemeente(ONTVANGENDE_GEMEENTE_CODE);
        ontvanger.setSysteem(ONTVANGENDE_SYSTEEM);
        final ZenderType zender = new ZenderType();
        zender.setGemeente(ZENDENDE_GEMEENTE_CODE);
        zender.setSysteem(ONTVANGENDE_SYSTEEM);
        final GregorianCalendar tijdstipOntvanstCalendar = new GregorianCalendar();
        tijdstipOntvanstCalendar.setTime(TIJDSTIP_ONTVANGST);
        final GregorianCalendar tijdstipVerzendingCalendar = new GregorianCalendar();
        tijdstipVerzendingCalendar.setTime(TIJDSTIP_VERZENDING);
        final XMLGregorianCalendar tijdstipOntvangst = DatatypeFactory.newInstance().newXMLGregorianCalendar(tijdstipOntvanstCalendar);
        final XMLGregorianCalendar tijdstipVerzending = DatatypeFactory.newInstance().newXMLGregorianCalendar(tijdstipVerzendingCalendar);

        archiveringVerzoekType.setCrossReferentienummer(CROSS_REFERENTIENUMMER);
        archiveringVerzoekType.setData(DATA);
        archiveringVerzoekType.setOntvanger(ontvanger);
        archiveringVerzoekType.setReferentienummer(REFERENTIENUMMER);
        archiveringVerzoekType.setRichting(RICHTING);
        archiveringVerzoekType.setSoortBericht(SOORT_BERICHT);
        archiveringVerzoekType.setTijdstipOntvangst(tijdstipOntvangst);
        archiveringVerzoekType.setTijdstipVerzending(tijdstipVerzending);
        archiveringVerzoekType.setZender(zender);

        final ArchiveringVerzoekBericht archiveringVerzoekBericht = new ArchiveringVerzoekBericht(archiveringVerzoekType);
        final String geformat = archiveringVerzoekBericht.format();

        LOG.info("Geformat: {}", geformat);
        // controleer of geformat bericht weer geparsed kan worden
        final ArchiveringVerzoekBericht format = (ArchiveringVerzoekBericht) factory.getBericht(geformat);
        assertEquals("ArchiveerInBrpVerzoek", format.getBerichtType());
    }

    @Test
    public void testDatumConversie() throws DatatypeConfigurationException {
        final ArchiveringVerzoekBericht archiveringVerzoekBericht = new ArchiveringVerzoekBericht();

        Assert.assertNull(archiveringVerzoekBericht.getTijdstipOntvangst());
        Assert.assertNull(archiveringVerzoekBericht.getTijdstipVerzending());
        archiveringVerzoekBericht.setTijdstipOntvangst(null);
        archiveringVerzoekBericht.setTijdstipVerzending(null);

    }

    @Test
    public void testOntvangendeEnZendendeSysteem() {
        final ArchiveringVerzoekBericht archiveringVerzoekBericht = new ArchiveringVerzoekBericht();

        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeSysteem());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeSysteem());

        archiveringVerzoekBericht.setOntvangendeSysteem(null);
        archiveringVerzoekBericht.setZendendeSysteem(null);

        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeSysteem());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeSysteem());

        archiveringVerzoekBericht.setOntvangendeSysteem(SysteemType.GBA_NETWERK);
        archiveringVerzoekBericht.setZendendeSysteem(SysteemType.MIGRATIEVOORZIENINGEN);

        Assert.assertEquals(SysteemType.GBA_NETWERK, archiveringVerzoekBericht.getOntvangendeSysteem());
        Assert.assertEquals(SysteemType.MIGRATIEVOORZIENINGEN, archiveringVerzoekBericht.getZendendeSysteem());
    }

    @Test
    public void testAfnemerOverschrijfGemeente() {
        final ArchiveringVerzoekBericht archiveringVerzoekBericht = new ArchiveringVerzoekBericht();

        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeAfnemerCode());
        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeGemeenteCode());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeAfnemerCode());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeGemeenteCode());

        archiveringVerzoekBericht.setOntvangendeAfnemerCode(null);
        archiveringVerzoekBericht.setOntvangendeGemeenteCode(null);
        archiveringVerzoekBericht.setZendendeAfnemerCode(null);
        archiveringVerzoekBericht.setZendendeGemeenteCode(null);

        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeAfnemerCode());
        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeGemeenteCode());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeAfnemerCode());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeGemeenteCode());

        archiveringVerzoekBericht.setOntvangendeAfnemerCode(ONTVANGENDE_AFNEMER_CODE);
        archiveringVerzoekBericht.setOntvangendeGemeenteCode(ONTVANGENDE_GEMEENTE_CODE);
        archiveringVerzoekBericht.setZendendeAfnemerCode(ZENDENDE_AFNEMER_CODE);
        archiveringVerzoekBericht.setZendendeGemeenteCode(ZENDENDE_GEMEENTE_CODE);

        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeAfnemerCode());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeAfnemerCode());
        Assert.assertEquals(ONTVANGENDE_GEMEENTE_CODE, archiveringVerzoekBericht.getOntvangendeGemeenteCode());
        Assert.assertEquals(ZENDENDE_GEMEENTE_CODE, archiveringVerzoekBericht.getZendendeGemeenteCode());

        archiveringVerzoekBericht.setOntvangendeAfnemerCode(ONTVANGENDE_AFNEMER_CODE);
        archiveringVerzoekBericht.setZendendeAfnemerCode(ZENDENDE_AFNEMER_CODE);

        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeGemeenteCode());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeGemeenteCode());
        Assert.assertEquals(ONTVANGENDE_AFNEMER_CODE, archiveringVerzoekBericht.getOntvangendeAfnemerCode());
        Assert.assertEquals(ZENDENDE_AFNEMER_CODE, archiveringVerzoekBericht.getZendendeAfnemerCode());
    }

    @Test
    public void testGemeenteOverschrijfAfnemer() {
        final ArchiveringVerzoekBericht archiveringVerzoekBericht = new ArchiveringVerzoekBericht();

        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeAfnemerCode());
        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeGemeenteCode());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeAfnemerCode());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeGemeenteCode());

        archiveringVerzoekBericht.setOntvangendeGemeenteCode(null);
        archiveringVerzoekBericht.setOntvangendeAfnemerCode(null);
        archiveringVerzoekBericht.setZendendeGemeenteCode(null);
        archiveringVerzoekBericht.setZendendeAfnemerCode(null);

        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeAfnemerCode());
        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeGemeenteCode());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeAfnemerCode());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeGemeenteCode());

        archiveringVerzoekBericht.setOntvangendeGemeenteCode(ONTVANGENDE_GEMEENTE_CODE);
        archiveringVerzoekBericht.setOntvangendeAfnemerCode(ONTVANGENDE_AFNEMER_CODE);
        archiveringVerzoekBericht.setZendendeGemeenteCode(ZENDENDE_GEMEENTE_CODE);
        archiveringVerzoekBericht.setZendendeAfnemerCode(ZENDENDE_AFNEMER_CODE);

        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeGemeenteCode());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeGemeenteCode());
        Assert.assertEquals(ONTVANGENDE_AFNEMER_CODE, archiveringVerzoekBericht.getOntvangendeAfnemerCode());
        Assert.assertEquals(ZENDENDE_AFNEMER_CODE, archiveringVerzoekBericht.getZendendeAfnemerCode());

        archiveringVerzoekBericht.setOntvangendeGemeenteCode(ONTVANGENDE_GEMEENTE_CODE);
        archiveringVerzoekBericht.setZendendeGemeenteCode(ZENDENDE_GEMEENTE_CODE);

        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeAfnemerCode());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeAfnemerCode());
        Assert.assertEquals(ONTVANGENDE_GEMEENTE_CODE, archiveringVerzoekBericht.getOntvangendeGemeenteCode());
        Assert.assertEquals(ZENDENDE_GEMEENTE_CODE, archiveringVerzoekBericht.getZendendeGemeenteCode());
    }

    @Test
    public void testEquals() throws DatatypeConfigurationException {
        final ArchiveerInBrpVerzoekType archiveringVerzoekType = new ArchiveerInBrpVerzoekType();
        final ZenderType ontvanger = new ZenderType();
        ontvanger.setGemeente(ONTVANGENDE_GEMEENTE_CODE);
        ontvanger.setSysteem(ONTVANGENDE_SYSTEEM);
        final ZenderType zender = new ZenderType();
        zender.setGemeente(ZENDENDE_GEMEENTE_CODE);
        zender.setSysteem(ONTVANGENDE_SYSTEEM);
        final GregorianCalendar tijdstipOntvanstCalendar = new GregorianCalendar();
        tijdstipOntvanstCalendar.setTime(TIJDSTIP_ONTVANGST);
        final GregorianCalendar tijdstipVerzendingCalendar = new GregorianCalendar();
        tijdstipVerzendingCalendar.setTime(TIJDSTIP_VERZENDING);
        final XMLGregorianCalendar tijdstipOntvangst = DatatypeFactory.newInstance().newXMLGregorianCalendar(tijdstipOntvanstCalendar);
        final XMLGregorianCalendar tijdstipVerzending = DatatypeFactory.newInstance().newXMLGregorianCalendar(tijdstipVerzendingCalendar);

        archiveringVerzoekType.setCrossReferentienummer(CROSS_REFERENTIENUMMER);
        archiveringVerzoekType.setData(DATA);
        archiveringVerzoekType.setOntvanger(ontvanger);
        archiveringVerzoekType.setReferentienummer(REFERENTIENUMMER);
        archiveringVerzoekType.setRichting(RICHTING);
        archiveringVerzoekType.setSoortBericht(SOORT_BERICHT);
        archiveringVerzoekType.setTijdstipOntvangst(tijdstipOntvangst);
        archiveringVerzoekType.setTijdstipVerzending(tijdstipVerzending);
        archiveringVerzoekType.setZender(zender);

        final ArchiveringVerzoekBericht archiveringVerzoekBerichtOrigineel = new ArchiveringVerzoekBericht(archiveringVerzoekType);
        archiveringVerzoekBerichtOrigineel.setMessageId(MessageIdGenerator.generateId());

        final ArchiveringVerzoekBericht archiveringVerzoekBerichtKopie = new ArchiveringVerzoekBericht(archiveringVerzoekType);
        final ArchiveringVerzoekBericht archiveringVerzoekBerichtObjectKopie = archiveringVerzoekBerichtOrigineel;
        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = new BlokkeringAntwoordBericht();

        archiveringVerzoekBerichtKopie.setMessageId(archiveringVerzoekBerichtOrigineel.getMessageId());
        archiveringVerzoekBerichtKopie.setCorrelationId(archiveringVerzoekBerichtOrigineel.getCorrelationId());

        assertTrue(archiveringVerzoekBerichtObjectKopie.equals(archiveringVerzoekBerichtOrigineel));
        assertFalse(archiveringVerzoekBerichtOrigineel.equals(blokkeringAntwoordBericht));
        assertTrue(archiveringVerzoekBerichtKopie.equals(archiveringVerzoekBerichtOrigineel));
        assertEquals(archiveringVerzoekBerichtObjectKopie.hashCode(), archiveringVerzoekBerichtOrigineel.hashCode());
        assertEquals(archiveringVerzoekBerichtKopie.hashCode(), archiveringVerzoekBerichtOrigineel.hashCode());
        assertEquals(archiveringVerzoekBerichtKopie.toString(), archiveringVerzoekBerichtOrigineel.toString());
    }

    @Test
    public void testSettersEnGetters() {
        final ArchiveringVerzoekBericht archiveringVerzoekBericht = new ArchiveringVerzoekBericht();
        archiveringVerzoekBericht.setCorrelationId(CORRELATION_ID);
        archiveringVerzoekBericht.setCrossReferentienummer(CROSS_REFERENTIENUMMER);
        archiveringVerzoekBericht.setData(DATA);
        archiveringVerzoekBericht.setMessageId(MESSAGE_ID);
        archiveringVerzoekBericht.setOntvangendeAfnemerCode(ONTVANGENDE_AFNEMER_CODE);
        archiveringVerzoekBericht.setOntvangendeGemeenteCode(ONTVANGENDE_GEMEENTE_CODE);
        archiveringVerzoekBericht.setOntvangendeSysteem(ONTVANGENDE_SYSTEEM);
        archiveringVerzoekBericht.setReferentienummer(REFERENTIENUMMER);
        archiveringVerzoekBericht.setRichting(RICHTING);
        archiveringVerzoekBericht.setSoortBericht(SOORT_BERICHT);
        archiveringVerzoekBericht.setTijdstipOntvangst(TIJDSTIP_ONTVANGST);
        archiveringVerzoekBericht.setTijdstipVerzending(TIJDSTIP_VERZENDING);
        archiveringVerzoekBericht.setZendendeAfnemerCode(ZENDENDE_AFNEMER_CODE);
        archiveringVerzoekBericht.setZendendeGemeenteCode(ZENDENDE_GEMEENTE_CODE);
        archiveringVerzoekBericht.setZendendeSysteem(ZENDENDE_SYSTEEM);

        Assert.assertEquals(CORRELATION_ID, archiveringVerzoekBericht.getCorrelationId());
        Assert.assertEquals(CROSS_REFERENTIENUMMER, archiveringVerzoekBericht.getCrossReferentienummer());
        Assert.assertEquals(DATA, archiveringVerzoekBericht.getData());
        Assert.assertEquals(MESSAGE_ID, archiveringVerzoekBericht.getMessageId());
        Assert.assertNull(archiveringVerzoekBericht.getOntvangendeAfnemerCode());
        Assert.assertEquals(ONTVANGENDE_GEMEENTE_CODE, archiveringVerzoekBericht.getOntvangendeGemeenteCode());
        Assert.assertEquals(ONTVANGENDE_SYSTEEM, archiveringVerzoekBericht.getOntvangendeSysteem());
        Assert.assertEquals(REFERENTIENUMMER, archiveringVerzoekBericht.getReferentienummer());
        Assert.assertEquals(RICHTING, archiveringVerzoekBericht.getRichting());
        Assert.assertEquals(SOORT_BERICHT, archiveringVerzoekBericht.getSoortBericht());
        Assert.assertEquals(TIJDSTIP_ONTVANGST, archiveringVerzoekBericht.getTijdstipOntvangst());
        Assert.assertEquals(TIJDSTIP_VERZENDING, archiveringVerzoekBericht.getTijdstipVerzending());
        Assert.assertNull(archiveringVerzoekBericht.getZendendeAfnemerCode());
        Assert.assertEquals(ZENDENDE_GEMEENTE_CODE, archiveringVerzoekBericht.getZendendeGemeenteCode());
        Assert.assertEquals(ZENDENDE_SYSTEEM, archiveringVerzoekBericht.getZendendeSysteem());
    }

    @Test
    public void tesBerichtSyntaxException() throws IOException {
        final String berichtOrigineel =
                IOUtils.toString(ArchiveringVerzoekBerichtTest.class.getResourceAsStream("blokkeringVerzoekBerichtSyntaxExceptionBericht.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
        Assert.assertNotNull(((OngeldigBericht) syncBericht).getGerelateerdeInformatie());
        Assert.assertNotNull(((OngeldigBericht) syncBericht).getGerelateerdeInformatie().getAdministratieveHandelingIds());
        Assert.assertEquals(0, ((OngeldigBericht) syncBericht).getGerelateerdeInformatie().getAdministratieveHandelingIds().size());
        Assert.assertNotNull(((OngeldigBericht) syncBericht).getGerelateerdeInformatie().getaNummers());
        Assert.assertEquals(0, ((OngeldigBericht) syncBericht).getGerelateerdeInformatie().getaNummers().size());
        Assert.assertNotNull(((OngeldigBericht) syncBericht).getGerelateerdeInformatie().getPartijen());
        Assert.assertEquals(0, ((OngeldigBericht) syncBericht).getGerelateerdeInformatie().getPartijen().size());
    }

}
