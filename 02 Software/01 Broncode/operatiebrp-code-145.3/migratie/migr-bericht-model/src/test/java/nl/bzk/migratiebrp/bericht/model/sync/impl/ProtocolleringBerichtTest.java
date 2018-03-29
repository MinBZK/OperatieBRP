/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class ProtocolleringBerichtTest {
    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testProtocolleringBericht() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        bericht.addProtocollering(new Protocollering(1));

        bericht.setMessageId(MessageIdGenerator.generateId());

        final String xml = bericht.format();
        final SyncBericht parsed = factory.getBericht(xml);

        Assert.assertEquals(ProtocolleringBericht.class, parsed.getClass());
        parsed.setMessageId(bericht.getMessageId());
        Assert.assertEquals(bericht, parsed);
    }

    @Test
    public void testActiviteitId() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        bericht.addProtocollering(new Protocollering(1));
        Assert.assertEquals(1, bericht.getProtocollering().get(0).getActiviteitId());
    }

    @Test
    public void testPersoonId() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        bericht.addProtocollering(new Protocollering(1));
        Assert.assertNull(bericht.getProtocollering().get(0).getPersoonId());
    }

    @Test
    public void testGevuldePersoonId() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        final Protocollering protocollering = new Protocollering(1);
        bericht.addProtocollering(protocollering);
        protocollering.setPersoonId(2L);

        Assert.assertEquals(Long.valueOf(2), bericht.getProtocollering().get(0).getPersoonId());
    }

    @Test
    public void testNadereBijhoudingsaardCode() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        bericht.addProtocollering(new Protocollering(1));
        Assert.assertEquals(null, bericht.getProtocollering().get(0).getNadereBijhoudingsaardCode());
    }

    @Test
    public void testGevuldeNadereBijhoudingsaardCode() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        final Protocollering protocollering = new Protocollering(1);
        bericht.addProtocollering(protocollering);
        protocollering.setNadereBijhoudingsaardCode("F");

        Assert.assertEquals("F", bericht.getProtocollering().get(0).getNadereBijhoudingsaardCode());
    }

    @Test
    public void testToegangLeveringsautorisatieId() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        bericht.addProtocollering(new Protocollering(1));
        Assert.assertEquals(null, bericht.getProtocollering().get(0).getToegangLeveringsautorisatieId());
    }

    @Test
    public void testGevuldeToegangLeveringsautorisatieId() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        final Protocollering protocollering = new Protocollering(1);
        bericht.addProtocollering(protocollering);
        protocollering.setToegangLeveringsautorisatieId(2);
        Assert.assertEquals(Integer.valueOf(2), bericht.getProtocollering().get(0).getToegangLeveringsautorisatieId());
    }

    @Test
    public void testToegangLeveringsautorisatieCount() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        bericht.addProtocollering(new Protocollering(1));
        Assert.assertEquals(0, bericht.getProtocollering().get(0).getToegangLeveringsautorisatieCount());
    }

    @Test
    public void testGevuldeToegangLeveringsautorisatieCount() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        final Protocollering protocollering = new Protocollering(1);
        bericht.addProtocollering(protocollering);
        protocollering.setToegangLeveringsautorisatieCount(13);
        Assert.assertEquals(13, bericht.getProtocollering().get(0).getToegangLeveringsautorisatieCount());
    }

    @Test
    public void testDienstId() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        bericht.addProtocollering(new Protocollering(1));
        Assert.assertEquals(null, bericht.getProtocollering().get(0).getDienstId());
    }

    @Test
    public void testGevuldeDienstId() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        final Protocollering protocollering = new Protocollering(1);
        bericht.addProtocollering(protocollering);
        protocollering.setDienstId(2);
        Assert.assertEquals(Integer.valueOf(2), bericht.getProtocollering().get(0).getDienstId());
    }

    @Test
    public void testStartTijdstip() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        bericht.addProtocollering(new Protocollering(1));
        Assert.assertEquals(null, bericht.getProtocollering().get(0).getStartTijdstip());
    }

    @Test
    public void testGevuldeStartTijdstip() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        final Protocollering protocollering = new Protocollering(1);
        bericht.addProtocollering(protocollering);
        protocollering.setStartTijdstip(LocalDateTime.of(2006, 12, 1, 12, 0, 0));
        Assert.assertEquals(2006, bericht.getProtocollering().get(0).getStartTijdstip().getYear());
        Assert.assertEquals(Month.DECEMBER, bericht.getProtocollering().get(0).getStartTijdstip().getMonth());
        Assert.assertEquals(1, bericht.getProtocollering().get(0).getStartTijdstip().getDayOfMonth());
        Assert.assertEquals(12, bericht.getProtocollering().get(0).getStartTijdstip().getHour());
        Assert.assertEquals(0, bericht.getProtocollering().get(0).getStartTijdstip().getMinute());
        Assert.assertEquals(0, bericht.getProtocollering().get(0).getStartTijdstip().getSecond());
    }

    @Test
    public void testLaatsteActieTijdstip() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        bericht.addProtocollering(new Protocollering(1));
        Assert.assertEquals(null, bericht.getProtocollering().get(0).getLaatsteActieTijdstip());
    }

    @Test
    public void testGevuldeLaatsteActieTijdstip() {
        final ProtocolleringBericht bericht = new ProtocolleringBericht();
        final Protocollering protocollering = new Protocollering(1);
        bericht.addProtocollering(protocollering);
        protocollering.setLaatsteActieTijdstip(LocalDateTime.of(2006, 12, 1, 12, 0, 0));
        Assert.assertEquals(2006, bericht.getProtocollering().get(0).getLaatsteActieTijdstip().getYear());
        Assert.assertEquals(Month.DECEMBER, bericht.getProtocollering().get(0).getLaatsteActieTijdstip().getMonth());
        Assert.assertEquals(1, bericht.getProtocollering().get(0).getLaatsteActieTijdstip().getDayOfMonth());
        Assert.assertEquals(12, bericht.getProtocollering().get(0).getLaatsteActieTijdstip().getHour());
        Assert.assertEquals(0, bericht.getProtocollering().get(0).getLaatsteActieTijdstip().getMinute());
        Assert.assertEquals(0, bericht.getProtocollering().get(0).getLaatsteActieTijdstip().getSecond());
    }

    @Test
    public void testVertaal() throws IOException {
        final String berichtOrigineel = IOUtils.toString(this.getClass().getResourceAsStream("protocolleringBericht.xml"));
        final SyncBericht bericht = factory.getBericht(berichtOrigineel);

        final ProtocolleringBericht protocolleringBericht = (ProtocolleringBericht) bericht;
        Assert.assertEquals(1, protocolleringBericht.getProtocollering().get(0).getActiviteitId());
        Assert.assertEquals("Protocollering", protocolleringBericht.getBerichtType());
    }

    @Test
    public void tesBerichtSyntaxException() throws IOException {
        final String berichtOrigineel = IOUtils.toString(this.getClass().getResourceAsStream("protocolleringBerichtSyntaxException.xml"));

        final SyncBericht syncBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(syncBericht instanceof OngeldigBericht);
        Assert.assertNotNull(syncBericht.getGerelateerdeInformatie());
        Assert.assertNotNull(syncBericht.getGerelateerdeInformatie().getAdministratieveHandelingIds());
        Assert.assertEquals(0, syncBericht.getGerelateerdeInformatie().getAdministratieveHandelingIds().size());
        Assert.assertNotNull(syncBericht.getGerelateerdeInformatie().getaNummers());
        Assert.assertEquals(0, syncBericht.getGerelateerdeInformatie().getaNummers().size());
        Assert.assertNotNull(syncBericht.getGerelateerdeInformatie().getPartijen());
        Assert.assertEquals(0, syncBericht.getGerelateerdeInformatie().getPartijen().size());
    }
}
